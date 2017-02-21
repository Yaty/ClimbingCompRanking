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
import climbingcompranking.model.Competition;
import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.ranking.RankType;
import climbingcompranking.utils.I18n;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class RankingWithMultipleCategories {
    private static Competition competition;
    private static Climber c1, c2, c3, c4, c5, c6;
    
    @BeforeClass
    public static void init() {
        competition = new Competition(Competition.CompetitionType.LEAD);
        c1 = new Climber(0, "a", "a", Category.SENIOR, competition.getCompetitionType());
        c2 = new Climber(1, "b", "b", Category.SENIOR, competition.getCompetitionType());
        c3 = new Climber(2, "c", "c", Category.SENIOR, competition.getCompetitionType());
        c4 = new Climber(3, "d", "d", Category.VETERAN, competition.getCompetitionType());
        c5 = new Climber(4, "e", "e", Category.VETERAN, competition.getCompetitionType());
        c6 = new Climber(5, "f", "f", Category.VETERAN, competition.getCompetitionType());
        ArrayList<Climber> seniorClimbers = new ArrayList<>();
        seniorClimbers.add(c1);
        seniorClimbers.add(c2);
        seniorClimbers.add(c3);
        
        ArrayList<Climber> vetClimbers = new ArrayList<>();
        vetClimbers.add(c4);
        vetClimbers.add(c5);
        vetClimbers.add(c6);
        
        competition.getClimbers().put(Category.SENIOR, seniorClimbers);  
        competition.getClimbers().put(Category.VETERAN, vetClimbers);
    }
    
    @Test
    public void simpleTest() {
        c1.getLeadScore().setFullScore(10, false);
        c2.getLeadScore().setFullScore(9, false);
        c3.getLeadScore().setFullScore(8, false);
        
        c4.getLeadScore().setFullScore(10, false);
        c5.getLeadScore().setFullScore(9, false);
        c6.getLeadScore().setFullScore(8, false);
        
        String ranking = competition.rank(RankType.TEXT);
        String[] lines = ranking.split("\n");
        
        String[] wantedRanking =
        {
            I18n.MODEL.getString("Senior"),
            c1.getFullName() + " 1",
            c2.getFullName() + " 2",
            c3.getFullName() + " 3",
            I18n.MODEL.getString("Veteran"),
            c4.getFullName() + " 1",
            c5.getFullName() + " 2",
            c6.getFullName() + " 3",
        };

        assertArrayEquals(wantedRanking, lines);        
    }
    
    @Test
    public void exaequoTest1() {
        c1.getLeadScore().setFullScore(10, false);
        c2.getLeadScore().setFullScore(10, false);
        c3.getLeadScore().setFullScore(8, false);
        
        c4.getLeadScore().setFullScore(10, false);
        c5.getLeadScore().setFullScore(10, false);
        c6.getLeadScore().setFullScore(8, false);
        
        String ranking = competition.rank(RankType.TEXT);
        String[] lines = ranking.split("\n");
        
        String[] wantedRanking =
        {
            I18n.MODEL.getString("Senior"),
            c1.getFullName() + " 1",
            c2.getFullName() + " 1",
            c3.getFullName() + " 3",
            I18n.MODEL.getString("Veteran"),
            c4.getFullName() + " 1",
            c5.getFullName() + " 1",
            c6.getFullName() + " 3",
        };

        assertArrayEquals(wantedRanking, lines);          
    }
}
