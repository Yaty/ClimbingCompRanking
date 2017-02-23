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
package climbingcompranking.model;

import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.ranking.RankType;
import climbingcompranking.model.ranking.RankingGenerator;
import climbingcompranking.utils.I18n;
import climbingcompranking.utils.Observable;
import climbingcompranking.utils.Observer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class Competition implements Observable {
    private final String name;
    private final HashMap<Category, ArrayList<Climber>> climbers;
    public enum CompetitionType {
        SPEED(I18n.MODEL.getString("Speed")),
        LEAD(I18n.MODEL.getString("Lead")), 
        BOULDERING(I18n.MODEL.getString("Bouldering")),
        SPEED_AND_LEAD(I18n.MODEL.getString("SpeedLead")),
        SPEED_AND_BOULDERING(I18n.MODEL.getString("SpeedBouldering")),
        LEAD_AND_BOULDERING(I18n.MODEL.getString("LeadBouldering")),
        COMBINED(I18n.MODEL.getString("Combined"));
        
        public static String[] names() {
            CompetitionType[] comps = CompetitionType.values();
            String[] names = new String[comps.length];
            for(int i = 0 ; i < comps.length ; i++)
                names[i] = comps[i].name;
            return names;
        }

        public static CompetitionType nameToCompType(String string) {
            CompetitionType[] comps = CompetitionType.values();
            for(CompetitionType comp : comps)
                if(comp.name.equals(string))
                    return comp;
            return null;
        }
        
        public final String name;
        private CompetitionType(String name) {
            this.name = name;
        }
    };
    private final CompetitionType compType;
    private ArrayList<Observer> observers;
    
    public Competition(CompetitionType compType, String name) {
        this.compType = compType;
        climbers = new HashMap<>();
        this.name = name;
    }
    
    public CompetitionType getCompetitionType() {
        return compType;
    }
    
    public HashMap<Category, ArrayList<Climber>> getClimbers() {
        return climbers;
    }
    
    public String rank(RankType rankType) {
        RankingGenerator generator = new RankingGenerator(climbers);
        switch(rankType) {
            case TEXT:
                return generator.getRankingInString(compType);
            case PDF:
                generator.createRankingPDF(name, compType);
                return "PDF Created";
            default:
                throw new UnsupportedOperationException(rankType.name() + " is not supported yet.");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Competition name : ").append(name).append("\n");
        str.append("Competition type : ").append(compType).append("\n");
        str.append("List of climbers registered : \n");
        for(ArrayList<Climber> climberCategory : climbers.values()) {
            for(Climber climber : climberCategory) {
                str.append(climber.toString()).append("\n");
            }
        }
        return str.toString();
    }
    
    public void addClimber(Climber climber) {
        climbers.get(climber.getCategory()).add(climber);
        notifyObserver(this, name);
    }
    
    public void addClimber(int id, String name, String lastname, Category category, String clubName) {
        climbers.get(category).add(new Climber(id, name, lastname, category, compType, clubName));
        notifyObserver(this, this.name);
    }
    
    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver() {
        observers.clear();
    }

    @Override
    public void notifyObserver(Observable o, Object arg) {
        for(Observer obs : observers)
            obs.update(o, arg);
    }
}
