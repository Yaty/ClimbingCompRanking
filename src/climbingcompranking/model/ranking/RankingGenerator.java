/*
 * Copyright 2017 Hugo Da Roit - contact@hdaroit.fr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package climbingcompranking.model.ranking;

import climbingcompranking.model.Competition;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.climber.exceptions.CompetitionTypeUninitializedException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class RankingGenerator {
    private ArrayList<Climber> climbers;

    public RankingGenerator(ArrayList<Climber> climbers) {
        this.climbers = new ArrayList<>(); // It's a deep copy
        for(Climber climber : climbers) {
            try {
                this.climbers.add(climber.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RankingGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Beurk, refactor needed
    private ArrayList<Rank> doTheRanking() {
        ArrayList<Rank> ranks = new ArrayList<>();
        int skip = 0, counter = 1;
        
        /*
         -score -> the more you have the better it is
         +score -> the less you have the better it is
        */
        
        switch(Competition.compType) {
            case BOULDERING:
                climbers.sort((Climber c1, Climber c2) -> {
                    // first we sort by the number of tops
                    int score = Integer.compare(c1.getBoulderingScore().getNumberOfTop(), c2.getBoulderingScore().getNumberOfTop());
                    if(score != 0) return -score;
                    
                    // if it's equal we look a the number of tries to get those tops
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToTop(), c2.getBoulderingScore().getNumberOfTryToTop());
                    if(score != 0) return score;
                    
                    // if it's again equal we look at the number of bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfBonus(), c2.getBoulderingScore().getNumberOfBonus());
                    if(score != 0) return -score;
                    
                    // and finally we compare the number of tries to get those bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToBonus(), c2.getBoulderingScore().getNumberOfTryToBonus());
                    if(score == 0) { // then those climbers are ex-aequo
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, 0, 0, counter));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, 0, 0, counter++ + skip));
                        skip = 0;
                    }
                }
                
                break;
            case LEAD:
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getLeadScore().getFullScoreInFloat(), c2.getLeadScore().getFullScoreInFloat());
                    if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return -score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, counter, 0, 0));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, counter++ + skip, 0, 0));
                        skip = 0;
                    }
                }
                
                break;
            case SPEED:
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getSpeedScore().getSpeed(), c2.getSpeedScore().getSpeed());
                     if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, 0, counter, 0));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, 0, counter++ + skip, 0));
                        skip = 0;
                    }
                }
                
                break;
            case LEAD_AND_BOULDERING:
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getLeadScore().getFullScoreInFloat(), c2.getLeadScore().getFullScoreInFloat());
                    if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return -score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, counter, 0, 0));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, counter++ + skip, 0, 0));
                        skip = 0;
                    }
                    climber.setExaqueo(false); // reset
                }
                
                
                climbers.sort((Climber c1, Climber c2) -> {
                    // first we sort by the number of tops
                    int score = Integer.compare(c1.getBoulderingScore().getNumberOfTop(), c2.getBoulderingScore().getNumberOfTop());
                    if(score != 0) return -score;
                    
                    // if it's equal we look a the number of tries to get those tops
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToTop(), c2.getBoulderingScore().getNumberOfTryToTop());
                    if(score != 0) return score;
                    
                    // if it's again equal we look at the number of bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfBonus(), c2.getBoulderingScore().getNumberOfBonus());
                    if(score != 0) return -score;
                    
                    // and finally we compare the number of tries to get those bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToBonus(), c2.getBoulderingScore().getNumberOfTryToBonus());
                    if(score == 0) { // then those climbers are ex-aequo
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                counter = 1;
                skip = 0;
                // Creating a ranking
                for(Climber climber : climbers) {
                    Rank rank = getClimberRank(ranks, climber);
                    if(climber.isExaqueo()) {
                        rank.setBoulderingRank(counter);
                        skip++;
                    } else {
                        rank.setBoulderingRank(counter++ + skip);
                        skip = 0;
                    }
                }
                
                break;
            case SPEED_AND_BOULDERING:
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getSpeedScore().getSpeed(), c2.getSpeedScore().getSpeed());
                     if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, 0, counter, 0));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, 0, counter++ + skip, 0));
                        skip = 0;
                    }
                    climber.setExaqueo(false); // reset for bouldering
                }

                climbers.sort((Climber c1, Climber c2) -> {
                    // first we sort by the number of tops
                    int score = Integer.compare(c1.getBoulderingScore().getNumberOfTop(), c2.getBoulderingScore().getNumberOfTop());
                    if(score != 0) return -score;
                    
                    // if it's equal we look a the number of tries to get those tops
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToTop(), c2.getBoulderingScore().getNumberOfTryToTop());
                    if(score != 0) return score;
                    
                    // if it's again equal we look at the number of bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfBonus(), c2.getBoulderingScore().getNumberOfBonus());
                    if(score != 0) return -score;
                    
                    // and finally we compare the number of tries to get those bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToBonus(), c2.getBoulderingScore().getNumberOfTryToBonus());
                    if(score == 0) { // then those climbers are ex-aequo
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                counter = 1;
                skip = 0;
                // Creating a ranking
                for(Climber climber : climbers) {
                    Rank rank = getClimberRank(ranks, climber);
                    if(climber.isExaqueo()) {
                        rank.setBoulderingRank(counter);
                        skip++;
                    } else {
                        rank.setBoulderingRank(counter++ + skip);
                        skip = 0;
                    }
                }
                break;
            case SPEED_AND_LEAD:
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getSpeedScore().getSpeed(), c2.getSpeedScore().getSpeed());
                     if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, 0, counter, 0));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, 0, counter++ + skip, 0));
                        skip = 0;
                    }
                    climber.setExaqueo(false); // reset for bouldering
                }
                
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getLeadScore().getFullScoreInFloat(), c2.getLeadScore().getFullScoreInFloat());
                    if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return -score;
                });
                counter = 1;
                skip = 0;
                // Creating a ranking
                for(Climber climber : climbers) {
                    Rank rank = getClimberRank(ranks, climber);
                    if(climber.isExaqueo()) {
                        rank.setLeadRank(counter);
                        skip++;
                    } else {
                        rank.setLeadRank(counter++ + skip);
                        skip = 0;
                    }
                }
                break;
            case COMBINED:
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getSpeedScore().getSpeed(), c2.getSpeedScore().getSpeed());
                     if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                
                // Creating a ranking
                for(Climber climber : climbers) {
                    if(climber.isExaqueo()) {
                        ranks.add(new Rank(climber, 0, counter, 0));
                        skip++;
                    } else {
                        ranks.add(new Rank(climber, 0, counter++ + skip, 0));
                        skip = 0;
                    }
                    climber.setExaqueo(false); // reset for bouldering
                }
                
                climbers.sort((Climber c1, Climber c2) -> {
                    int score = Float.compare(c1.getLeadScore().getFullScoreInFloat(), c2.getLeadScore().getFullScoreInFloat());
                    if(score == 0) {
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return -score;
                });
                counter = 1;
                skip = 0;
                // Creating a ranking
                for(Climber climber : climbers) {
                    Rank rank = getClimberRank(ranks, climber);
                    if(climber.isExaqueo()) {
                        rank.setLeadRank(counter);
                        skip++;
                    } else {
                        rank.setLeadRank(counter++ + skip);
                        skip = 0;
                    }
                }
                climbers.sort((Climber c1, Climber c2) -> {
                    // first we sort by the number of tops
                    int score = Integer.compare(c1.getBoulderingScore().getNumberOfTop(), c2.getBoulderingScore().getNumberOfTop());
                    if(score != 0) return -score;
                    
                    // if it's equal we look a the number of tries to get those tops
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToTop(), c2.getBoulderingScore().getNumberOfTryToTop());
                    if(score != 0) return score;
                    
                    // if it's again equal we look at the number of bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfBonus(), c2.getBoulderingScore().getNumberOfBonus());
                    if(score != 0) return -score;
                    
                    // and finally we compare the number of tries to get those bonus
                    score = Integer.compare(c1.getBoulderingScore().getNumberOfTryToBonus(), c2.getBoulderingScore().getNumberOfTryToBonus());
                    if(score == 0) { // then those climbers are ex-aequo
                        c1.setExaqueo(true);
                        c2.setExaqueo(true);
                    }
                    return score;
                });
                counter = 1;
                skip = 0;
                // Creating a ranking
                for(Climber climber : climbers) {
                    Rank rank = getClimberRank(ranks, climber);
                    if(climber.isExaqueo()) {
                        rank.setBoulderingRank(counter);
                        skip++;
                    } else {
                        rank.setBoulderingRank(counter++ + skip);
                        skip = 0;
                    }
                }
                break;
        }
        
        ranks.sort((Rank r1, Rank r2) -> {
            try {
                int score = Integer.compare(r1.getTotalPoints(), r2.getTotalPoints());
                if(score == 0) {
                    r1.setExaequo(true);
                    r2.setExaequo(true);
                }
                return score;
            } catch (CompetitionTypeUninitializedException ex) {
                Logger.getLogger(RankingGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        });
        
        counter = 1;
        skip = 0;
        for(Rank rank : ranks) {
            if(rank.isExaequo()) {
                rank.setOverallRank(counter);
                skip++;
            } else {
                rank.setOverallRank(counter++ + skip);
                skip = 0;
            }
        }
        
        return ranks;
    }

    public String getRankingInString() {
        StringBuilder str = new StringBuilder();
        ArrayList<Rank> ranks = doTheRanking();
        for(Rank rank : ranks)
            str.append(rank.getClimberFullName()).append(' ').append(rank.getOverallRank()).append("\n");
        return str.toString();
    }

    private Rank getClimberRank(ArrayList<Rank> ranks, Climber climber) {
        for(Rank rank : ranks) {
            if(rank.getClimber().equals(climber))
                return rank;
        }
        return null;
    }

    
}
