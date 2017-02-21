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
package climbingcompranking.model.climber;

import climbingcompranking.model.Competition;
import climbingcompranking.model.Competition.CompetitionType;
import climbingcompranking.model.climber.score.BoulderingScore;
import climbingcompranking.model.climber.score.LeadScore;
import climbingcompranking.model.climber.score.SpeedScore;
import climbingcompranking.model.ranking.Rank;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class Climber {
    private final int id;
    private final String name, lastname;
    private LeadScore leadScore;
    private SpeedScore speedScore;
    private BoulderingScore boulderingScore;
    private final Category category;
    private boolean exaqueo;
    private final Rank rank;
    
    /**
     * Create a climber without scores
     * @param id
     * @param name
     * @param lastname
     * @param category
     * @param compType
     */
    public Climber(int id, String name, String lastname, Category category, CompetitionType compType) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.category = category;
        this.exaqueo = false;
        this.rank = new Rank();
        
        switch(compType) {
            case BOULDERING:
                boulderingScore = new BoulderingScore();
                break;
            case LEAD:
                leadScore = new LeadScore();
                break;
            case SPEED:
                speedScore = new SpeedScore();
                break;
            case LEAD_AND_BOULDERING:
                leadScore = new LeadScore();
                boulderingScore = new BoulderingScore();
                break;
            case SPEED_AND_BOULDERING:
                speedScore = new SpeedScore();
                boulderingScore = new BoulderingScore();
                break;
            case SPEED_AND_LEAD:
                speedScore = new SpeedScore();
                leadScore = new LeadScore();
                break;
            case COMBINED:
                speedScore = new SpeedScore();
                leadScore = new LeadScore();
                boulderingScore = new BoulderingScore();
                break;
        }
    }

    /**
     * Used by clone()
     * @param id
     * @param name
     * @param lastname
     * @param leadScore
     * @param speedScore
     * @param boulderingScore
     * @param category
     */
    private Climber(int id, String name, String lastname, LeadScore leadScore, SpeedScore speedScore, BoulderingScore boulderingScore, Category category, boolean exaqueo, Rank rank) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.leadScore = leadScore;
        this.speedScore = speedScore;
        this.boulderingScore = boulderingScore;
        this.category = category;
        this.exaqueo = exaqueo;
        this.rank = rank;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    @Override
    public String toString() {
        return lastname + " " + name;
    }
    
    public int getID() {
        return id;
    }
    
    public String getFullName() {
        return lastname + ' ' + name;
    }
    
    @Override
    public Climber clone() throws CloneNotSupportedException {
        return new Climber(id, name, lastname, leadScore, speedScore, boulderingScore, category, exaqueo, rank);
    }

    public LeadScore getLeadScore() {
        return leadScore;
    }

    public SpeedScore getSpeedScore() {
        return speedScore;
    }

    public BoulderingScore getBoulderingScore() {
        return boulderingScore;
    }

    public boolean isExaqueo() {
        return exaqueo;
    }

    public void setExaqueo(boolean exaqueo) {
        this.exaqueo = exaqueo;
    }
    
    public void reset() {
        boulderingScore.reset();
        exaqueo = false;
        leadScore.reset();
        speedScore.reset();
    }
    
}
