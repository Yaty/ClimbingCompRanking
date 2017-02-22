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
public class BoulderingRankingTest {
    private static Competition competition;
    private static Climber c1, c2, c3;
    
    @BeforeClass
    public static void init() {
        BoulderingRankingTest.competition = new Competition(Competition.CompetitionType.BOULDERING, "comp1");
        BoulderingRankingTest.c1 = new Climber(0, "a", "a", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
        BoulderingRankingTest.c2 = new Climber(1, "b", "b", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
        BoulderingRankingTest.c3 = new Climber(2, "c", "c", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
        ArrayList<Climber> climbers = new ArrayList<>();
        climbers.add(c1);
        climbers.add(c2);
        climbers.add(c3);
        competition.getClimbers().put(Category.SENIOR_MALE, climbers);
    }
    
    @Test
    public void simpleRankingTest() {
        try {
            c1.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 1st
            c2.getBoulderingScore().setScore(4, 5, 4, 4); // Should be 2nd
            c3.getBoulderingScore().setScore(3, 4, 3, 15); // Should be 3rd
            
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
            Logger.getLogger(BoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void exAequoTest() {
        try {
            c1.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 1st
            c2.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 1nd
            c3.getBoulderingScore().setScore(3, 4, 3, 15); // Should be 3rd
            
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
            Logger.getLogger(BoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void rankByTopNumberTest() {
        try {
            c1.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 1st
            c2.getBoulderingScore().setScore(3, 4, 4, 4); // Should be 2nd
            c3.getBoulderingScore().setScore(3, 4, 3, 15); // Should be 3rd
            
            String ranking = competition.rank(RankType.TEXT);
            String[] lines = ranking.split("\n");
            
            String[] wantedRanking =
            {
                I18n.MODEL.getString("SeniorM"),
                c1.getFullName() + " 1",
                c2.getFullName() + " 2",
                c3.getFullName() + " 3"
            };
            
            assertArrayEquals(wantedRanking, lines);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(BoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @Test
    public void rankByTopTryNumbersTest() {
        try {
            c1.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 1st
            c2.getBoulderingScore().setScore(4, 5, 4, 4); // Should be 2nd
            c3.getBoulderingScore().setScore(3, 4, 3, 15); // Should be 3rd
            
            String ranking = competition.rank(RankType.TEXT);
            String[] lines = ranking.split("\n");
            
            String[] wantedRanking =
            {
                I18n.MODEL.getString("SeniorM"),
                c1.getFullName() + " 1",
                c2.getFullName() + " 2",
                c3.getFullName() + " 3"
            };
                    
            assertArrayEquals(wantedRanking, lines);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(BoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void rankByBonusTest() {
        try {
            c1.getBoulderingScore().setScore(4, 4, 5, 5); // Should be 1st
            c2.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 2nd
            c3.getBoulderingScore().setScore(3, 4, 3, 15); // Should be 3rd
            
            String ranking = competition.rank(RankType.TEXT);
            String[] lines = ranking.split("\n");
            
            String[] wantedRanking =
            {
                I18n.MODEL.getString("SeniorM"),
                c1.getFullName() + " 1",
                c2.getFullName() + " 2",
                c3.getFullName() + " 3"
            };
                    
            assertArrayEquals(wantedRanking, lines);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(BoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void rankByBonusTriesTest() {
        try {
            c1.getBoulderingScore().setScore(4, 4, 4, 4); // Should be 1st
            c2.getBoulderingScore().setScore(4, 4, 4, 5); // Should be 2nd
            c3.getBoulderingScore().setScore(3, 4, 3, 15); // Should be 3rd
            
            String ranking = competition.rank(RankType.TEXT);
            String[] lines = ranking.split("\n");            
            
            String[] wantedRanking =
            {
                I18n.MODEL.getString("SeniorM"),
                c1.getFullName() + " 1",
                c2.getFullName() + " 2",
                c3.getFullName() + " 3"
            };
                    
            assertArrayEquals(wantedRanking, lines);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(BoulderingRankingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test(expected=InvalidScoreException.class)
    public void invalidScoreTest1() throws InvalidScoreException {
        c1.getBoulderingScore().setScore(5, 4, 1, 1);
    }
    
    @Test (expected = InvalidScoreException.class)
    public void invalidScoreTest2() throws InvalidScoreException {
        c1.getBoulderingScore().setScore(5, 5, 2, 1);
    }
    
    @Test (expected = InvalidScoreException.class)
    public void invalidScoreTest3() throws InvalidScoreException {
        c1.getBoulderingScore().setScore(10, 10, 5, 5);
    }
}
