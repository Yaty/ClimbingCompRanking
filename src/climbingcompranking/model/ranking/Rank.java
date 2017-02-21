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
