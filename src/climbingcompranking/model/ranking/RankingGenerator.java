/* 
 * Copyright (C) 2017
 * Mail : Hugo Da Roit - contact@hdaroit.fr
 * GitHub : https://github.com/Yaty
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package climbingcompranking.model.ranking;

import climbingcompranking.model.Competition.CompetitionType;
import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.utils.I18n;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class RankingGenerator {
    private HashMap<Category, ArrayList<Climber>> climbers;

    public RankingGenerator(HashMap<Category, ArrayList<Climber>> climbers) {
        this.climbers = new HashMap<>();
        
        for (Map.Entry<Category, ArrayList<Climber>> entry : climbers.entrySet()) {
            ArrayList<Climber> climbersCopy = new ArrayList<>();
            for(Climber climber : entry.getValue()) {
                try {
                    climbersCopy.add(climber.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(RankingGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.climbers.put(entry.getKey(), climbersCopy);
        }
    }
    
    public HashMap<Category, ArrayList<Climber>> getClimbers() {
        return climbers;
    }
    
    private void updateLeadScore() {
        for(ArrayList<Climber> climbers : climbers.values()) {
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
    }
    
    private void updateSpeedScore() {
        for(ArrayList<Climber> climbers : climbers.values()) {
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
    }
    
    private void updateBoulderingScore() {
        for(ArrayList<Climber> climbers : climbers.values()) {
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
    }
    
    // Beurk, refactor needed
    private void doTheRanking(CompetitionType compType) {     
        // First we have to reset rank exaequo values
        // We will recalculate them after
        for(ArrayList<Climber> climbers : climbers.values()) {
            for(Climber climber : climbers) {
                climber.setExaqueo(false);
                climber.getRank().setExaequo(false);
            }
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
        for(ArrayList<Climber> climbers : climbers.values()) {
            climbers.sort((Climber c1, Climber c2) -> {
                int score = Integer.compare(c1.getRank().getTotalPoints(compType), c2.getRank().getTotalPoints(compType));
                if(score == 0) {
                    c1.getRank().setExaequo(true);
                    c2.getRank().setExaequo(true);
                }
                return score;
            });
        }
        
        // And finally update the overall ranking of each climber
        for(ArrayList<Climber> climbers : climbers.values()) {
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
    }

    public String getRankingInString(CompetitionType compType) {
        if(climbers.isEmpty()) return I18n.MODEL.getString("NoClimbers");
        StringBuilder str = new StringBuilder();
        doTheRanking(compType);
        for(Map.Entry<Category, ArrayList<Climber>> climbers : climbers.entrySet()) {
            str.append(climbers.getKey().getCategoryName()).append("\n");
            for(Climber climber : climbers.getValue())
                str.append(climber.getFullName()).append(' ').append(climber.getRank().getOverallRank()).append("\n");
        }
        return str.toString();
    }
    
}
