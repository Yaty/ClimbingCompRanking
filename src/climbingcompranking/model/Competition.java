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
package climbingcompranking.model;

import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.ranking.RankType;
import climbingcompranking.model.ranking.RankingGenerator;
import java.util.ArrayList;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class Competition {
    private String name;
    private final ArrayList<Climber> climbers;
    public enum CompetitionType {SPEED, LEAD, BOULDERING, SPEED_AND_LEAD, SPEED_AND_BOULDERING, LEAD_AND_BOULDERING, COMBINED};
    private final CompetitionType compType;
    
    public Competition(CompetitionType compType) {
        this.compType = compType;
        climbers = new ArrayList<>();
    }
    
    public CompetitionType getCompetitionType() {
        return compType;
    }
    
    public ArrayList<Climber> getClimbers() {
        return climbers;
    }
    
    public String rank(RankType rankType) {
        switch(rankType) {
            case TEXT:
                RankingGenerator generator = new RankingGenerator(climbers);
                return generator.getRankingInString(compType);
            case PDF:
                throw new UnsupportedOperationException("Not supported yet.");
            case JPG:
                throw new UnsupportedOperationException("Not supported yet.");
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
        for(Climber climber : climbers)
            str.append(climber.toString()).append("\n");
        return str.toString();
    }
}
