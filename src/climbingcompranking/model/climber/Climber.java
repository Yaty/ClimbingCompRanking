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
import climbingcompranking.model.climber.score.BoulderingScore;
import climbingcompranking.model.climber.score.LeadScore;
import climbingcompranking.model.climber.score.SpeedScore;

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
    private final String clubName;
    private final Rank rank;
    
    /**
     * Create a climber without scores
     * @param id
     * @param name
     * @param lastname
     * @param category
     * @param compType
     */
    public Climber(int id, String name, String lastname, Category category, CompetitionType compType, String clubName) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.category = category;
        this.rank = new Rank();
        this.clubName = clubName;
        
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
    
    public Rank getRank() {
        return rank;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public String getClubName() {
        return clubName;
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

    public LeadScore getLeadScore() {
        return leadScore;
    }

    public SpeedScore getSpeedScore() {
        return speedScore;
    }

    public BoulderingScore getBoulderingScore() {
        return boulderingScore;
    }
    
}
