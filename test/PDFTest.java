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
import climbingcompranking.utils.exceptions.InvalidScoreException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class PDFTest {
    private static Competition competition;
    private static Climber c1, c2, c3, c4, c5, c6;
    
    @BeforeClass
    public static void init() {
        try {
            competition = new Competition(Competition.CompetitionType.LEAD_AND_BOULDERING, "COMP1");
            c1 = new Climber(0, "a", "a", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
            c2 = new Climber(1, "b", "b", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
            c3 = new Climber(2, "c", "c", Category.SENIOR_MALE, competition.getCompetitionType(), "cpao");
            c1.getLeadScore().setFullScore(10, true);
            c2.getLeadScore().setFullScore(10, true);
            c3.getLeadScore().setFullScore(5, true);
            c1.getBoulderingScore().setScore(4, 4, 4, 4);
            c2.getBoulderingScore().setScore(4, 4, 4, 4);
            c3.getBoulderingScore().setScore(2, 4, 4, 4);
            ArrayList<Climber> seniors = new ArrayList<>();
            seniors.add(c1);
            seniors.add(c2);
            seniors.add(c3);
            competition.getClimbers().put(Category.SENIOR_MALE, seniors);
            
            c4 = new Climber(3, "d", "d", Category.VETERAN_MALE, competition.getCompetitionType(), "cpao");
            c5 = new Climber(4, "e", "e", Category.VETERAN_MALE, competition.getCompetitionType(), "cpao");
            c6 = new Climber(5, "f", "f", Category.VETERAN_MALE, competition.getCompetitionType(), "cpao");
            c4.getLeadScore().setFullScore(10, true);
            c5.getLeadScore().setFullScore(8, true);
            c6.getLeadScore().setFullScore(7, true);
            c4.getBoulderingScore().setScore(4, 4, 4, 4);
            c5.getBoulderingScore().setScore(3, 4, 4, 4);
            c6.getBoulderingScore().setScore(2, 4, 4, 4);
            ArrayList<Climber> veterans = new ArrayList<>();
            veterans.add(c4);
            veterans.add(c5);
            veterans.add(c6);      
            competition.getClimbers().put(Category.VETERAN_MALE, veterans);
        } catch (InvalidScoreException ex) {
            Logger.getLogger(PDFTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void generatePDF() {
        competition.rank(RankType.PDF);
        // We shall read the PDF to be sure everything is in place
        // For now see the pdf with your eye :)
    }
}
