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

import climbingcompranking.model.Competition;
import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.climber.exceptions.InvalidScoreException;
import climbingcompranking.model.ranking.RankType;
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
        BoulderingRankingTest.competition = new Competition(Competition.CompetitionType.BOULDERING);
        BoulderingRankingTest.c1 = new Climber(0, "Hugo", "Da Roit", Category.SENIOR, competition.getCompetitionType());
        BoulderingRankingTest.c2 = new Climber(1, "Pierre", "Laguette", Category.SENIOR, competition.getCompetitionType());
        BoulderingRankingTest.c3 = new Climber(2, "Thomas", "Paillette", Category.SENIOR, competition.getCompetitionType());
        competition.getClimbers().add(c1);
        competition.getClimbers().add(c2);
        competition.getClimbers().add(c3);
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
