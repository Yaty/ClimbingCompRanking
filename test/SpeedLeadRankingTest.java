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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class SpeedLeadRankingTest {
    private static Competition competition;
    private static Climber c1, c2, c3;
    
    @BeforeClass
    public static void init() {
        competition = new Competition(Competition.CompetitionType.SPEED_AND_LEAD);
        c1 = new Climber(0, "Hugo", "Da Roit", Category.SENIOR, competition.getCompetitionType());
        c2 = new Climber(1, "Pierre", "Laguette", Category.SENIOR, competition.getCompetitionType());
        c3 = new Climber(2, "Thomas", "Paillette", Category.SENIOR, competition.getCompetitionType());
        competition.getClimbers().add(c1);
        competition.getClimbers().add(c2);
        competition.getClimbers().add(c3);        
    }
    
    @Test
    public void simpleTest() {
        c1.getLeadScore().setFullScore(50, true); // 1
        c2.getLeadScore().setFullScore(40, true); // 2
        c3.getLeadScore().setFullScore(30, true); // 3

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
    }

    @Test
    public void exAequoTest() {
        c1.getLeadScore().setFullScore(50, true); // 1
        c2.getLeadScore().setFullScore(50, true); // 1
        c3.getLeadScore().setFullScore(30, true); // 3

        c1.getSpeedScore().setSpeed(10); // 1 -> 2
        c2.getSpeedScore().setSpeed(10); // 1 -> 2
        c3.getSpeedScore().setSpeed(12); // 3 -> 6

        String ranking = competition.rank(RankType.TEXT);
        String[] lines = ranking.split("\n");

        String[] wantedRanking =
        {
            c1.getFullName() + " 1",
            c2.getFullName() + " 1",
            c3.getFullName() + " 3",
        };

        assertArrayEquals(wantedRanking, lines);
    }    
    
}
