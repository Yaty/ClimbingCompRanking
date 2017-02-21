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

import climbingcompranking.model.Competition.CompetitionType;
import climbingcompranking.model.climber.Climber;
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
    
    private void updateLeadScore() {
        climbers.sort((Climber c1, Climber c2) -> {
            int score = Float.compare(c1.getLeadScore().getFullScoreInFloat(), c2.getLeadScore().getFullScoreInFloat());
            if(score == 0) {
                c1.setExaqueo(true);
                c2.setExaqueo(true);
            }
            return -score;
        });

        // Creating a ranking
        int counter = 1, skip = 0;
        for(Climber climber : climbers) {
            if(climber.isExaqueo()) {
                climber.getRank().setLeadRank(counter);
                skip++;
            } else {
                climber.getRank().setLeadRank(counter++ + skip);
                skip = 0;
            }
            climber.setExaqueo(false);
        }
    }
    
    private void updateSpeedScore() {
        climbers.sort((Climber c1, Climber c2) -> {
            int score = Float.compare(c1.getSpeedScore().getSpeed(), c2.getSpeedScore().getSpeed());
             if(score == 0) {
                c1.setExaqueo(true);
                c2.setExaqueo(true);
            }
            return score;
        });

        // Creating a ranking
        int counter = 1, skip = 0;
        for(Climber climber : climbers) {
            if(climber.isExaqueo()) {
                climber.getRank().setSpeedRank(counter);
                skip++;
            } else {
                climber.getRank().setSpeedRank(counter++ + skip);
                skip = 0;
            }
            climber.setExaqueo(false);
        }
    }
    
    private void updateBoulderingScore() {
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
        
         // Update the rank of the climbers
        int counter = 1, skip = 0;
        for(Climber climber : climbers) {
            if(climber.isExaqueo()) {
                climber.getRank().setBoulderingRank(counter);
                skip++;
            } else {
                climber.getRank().setBoulderingRank(counter++ + skip);
                skip = 0;
            }
            climber.setExaqueo(false);
        }
    }
    
    // Beurk, refactor needed
    private void doTheRanking(CompetitionType compType) {     
        // First we have to reset rank exaequo values
        // We will recalculate them after
        for(Climber climber : climbers) {
            climber.setExaqueo(false);
            climber.getRank().setExaequo(false);
        }
        
        // Then we update scores
        switch(compType) {
            case BOULDERING:
                updateBoulderingScore();
                break;
            case LEAD:
                updateLeadScore();
                break;
            case SPEED:
                updateSpeedScore();
                break;
            case LEAD_AND_BOULDERING:
                updateLeadScore();
                updateBoulderingScore();
                break;
            case SPEED_AND_BOULDERING:
                updateSpeedScore();
                updateBoulderingScore();
                break;
            case SPEED_AND_LEAD:
                updateSpeedScore();
                updateLeadScore();
                break;
            case COMBINED:
                updateBoulderingScore();
                updateLeadScore();
                updateSpeedScore();
                break;
        }
        
        // Sorting the climbers list with the total points
        climbers.sort((Climber c1, Climber c2) -> {
            int score = Integer.compare(c1.getRank().getTotalPoints(compType), c2.getRank().getTotalPoints(compType));
            if(score == 0) {
                c1.getRank().setExaequo(true);
                c2.getRank().setExaequo(true);
            }
            return score;
        });
        
        // And finally update the overall ranking of each climber
        int skip = 0, counter = 1;
        for(int i = 0 ; i < climbers.size() ; i++) {
            Rank rank = climbers.get(i).getRank();
            if(rank.isExaequo()) {
                rank.setOverallRank(counter);
                skip++;
            } else {
                rank.setOverallRank(counter++ + skip);
                skip = 0;
            }
        }
    }

    public String getRankingInString(CompetitionType compType) {
        StringBuilder str = new StringBuilder();
        doTheRanking(compType);
        for(Climber climber : climbers)
            str.append(climber.getFullName()).append(' ').append(climber.getRank().getOverallRank()).append("\n");
        return str.toString();
    }
    
}
