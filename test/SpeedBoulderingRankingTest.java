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
import climbingcompranking.utils.exceptions.InvalidScoreException;
import climbingcompranking.model.ranking.RankType;
import climbingcompranking.utils.I18n;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class SpeedBoulderingRankingTest {
    private static Competition competition;
    private static Climber c1, c2, c3;
    
    @BeforeClass
    public static void init() {
        competition = new Competition(Competition.CompetitionType.SPEED_AND_BOULDERING, "comp1");
        c1 = new Climber(0, "a", "a", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
        c2 = new Climber(1, "b", "b", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
        c3 = new Climber(2, "c", "c", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
        ArrayList<Climber> climbers = new ArrayList<>();
        climbers.add(c1);
        climbers.add(c2);
        climbers.add(c3);
        competition.getClimbers().put(Category.SENIOR_MALE, climbers);       
    }
    
    @Test
    public void simpleTest() {
        try {
            c1.getBoulderingScore().setScore(3, 3, 3, 3); // 1
            c2.getBoulderingScore().setScore(2, 2, 2, 2); // 2
            c3.getBoulderingScore().setScore(1, 1, 1, 1); // 3
            
            c1.getSpeedScore().setSpeed(10); // 1 -> 2
            c2.getSpeedScore().setSpeed(11); // 2 -> 4
            c3.getSpeedScore().setSpeed(12); // 3 -> 6
            
            String ranking = competition.rank(RankType.TEXT);
            String[] lines = ranking.split("\n");

            String[] wantedRanking =
            {
                I18n.MODEL.getString("SeniorM"),
                c1.getFullName() + " 1",
                c2.getFullName() + " 2",
                c3.getFullName() + " 3",
            };

            assertArrayEquals(wantedRanking, lines);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(LeadBoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void exAequo1() {
        try {
            c1.getBoulderingScore().setScore(3, 3, 3, 3); // 1
            c2.getBoulderingScore().setScore(3, 3, 3, 3); // 1
            c3.getBoulderingScore().setScore(1, 1, 1, 1); // 3
            
            c1.getSpeedScore().setSpeed(10.521f); // 1 -> 2
            c2.getSpeedScore().setSpeed(10.521f); // 1 -> 2
            c3.getSpeedScore().setSpeed(12.5215f); // 3 -> 6
            
            String ranking = competition.rank(RankType.TEXT);
            String[] lines = ranking.split("\n");

            String[] wantedRanking =
            {
                I18n.MODEL.getString("SeniorM"),
                c1.getFullName() + " 1",
                c2.getFullName() + " 1",
                c3.getFullName() + " 3",
            };

            assertArrayEquals(wantedRanking, lines);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(LeadBoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
