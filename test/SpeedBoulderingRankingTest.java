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
public class SpeedBoulderingRankingTest {
    private static Competition competition;
    private static Climber c1, c2, c3;
    
    @BeforeClass
    public static void init() {
        competition = Competition.getInstance();
        Competition.compType = Competition.CompetitionType.SPEED_AND_BOULDERING;
        c1 = new Climber(0, "Hugo", "Da Roit", Category.SENIOR);
        c2 = new Climber(1, "Pierre", "Laguette", Category.SENIOR);
        c3 = new Climber(2, "Thomas", "Paillette", Category.SENIOR);
        competition.addClimber(c1);
        competition.addClimber(c2);
        competition.addClimber(c3);        
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
