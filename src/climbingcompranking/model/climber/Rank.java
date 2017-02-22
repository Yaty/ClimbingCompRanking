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
package climbingcompranking.model.climber;

import climbingcompranking.model.Competition.CompetitionType;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class Rank {
    private int leadRank, speedRank, boulderingRank;
    private int overallRank;
    private boolean exaequo;
    
    public Rank() {
        exaequo = false;
        leadRank = speedRank = boulderingRank = 0;
    }
    
    public Rank(int leadRank, int speedRank, int boulderingRank) {
        this.leadRank = leadRank;
        this.speedRank = speedRank;
        this.boulderingRank = boulderingRank;
        this.exaequo = false;
    }

    public int getLeadRank() {
        return leadRank;
    }

    public int getSpeedRank() {
        return speedRank;
    }

    public int getBoulderingRank() {
        return boulderingRank;
    }

    public void setLeadRank(int leadRank) {
        this.leadRank = leadRank;
    }

    public void setSpeedRank(int speedRank) {
        this.speedRank = speedRank;
    }

    public void setBoulderingRank(int boulderingRank) {
        this.boulderingRank = boulderingRank;
    }    
    
    public int getTotalPoints(CompetitionType compType) {
        switch(compType) {
            case BOULDERING:
                return boulderingRank;
            case LEAD:
                return leadRank;
            case SPEED:
                return speedRank;
            case SPEED_AND_LEAD:            
                return speedRank + leadRank;
            case SPEED_AND_BOULDERING:
                return speedRank + boulderingRank;
            case LEAD_AND_BOULDERING:
                return leadRank + boulderingRank;
            case COMBINED:
                return leadRank + boulderingRank + speedRank;
            default:
                return -6666; // compType is final in Competition so there is non way we reach this instruction                
        }
    }

    public int getOverallRank() {
        return overallRank;
    }

    public void setOverallRank(int overallRank) {
        this.overallRank = overallRank;
    }

    public boolean isExaequo() {
        return exaequo;
    }

    public void setExaequo(boolean exaequo) {
        this.exaequo = exaequo;
    }

}
