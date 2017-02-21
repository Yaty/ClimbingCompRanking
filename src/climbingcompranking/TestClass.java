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
package climbingcompranking;

import climbingcompranking.model.Competition;
import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.ranking.RankType;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class TestClass {
    
    /*
        TEST DONE :
        - Lead ranking -> basic case
        - Bouldering ranking -> it works but didn't tested all cases
        - Speed ranking -> basic case
    */
    
    
    
    public static void main(String[] args) {
        Competition comp = Competition.getInstance();
        Competition.compType = Competition.CompetitionType.SPEED;
        
        Climber hugo = new Climber(0, "Hugo", "Da Roit", Category.SENIOR);
        Climber pierre = new Climber(1, "Pierre", "Laguette", Category.SENIOR);
        Climber thomas = new Climber(2, "Thomas", "Paillette", Category.SENIOR);
        
        // Speed test
        hugo.getSpeedScore().setSpeed(8.321f);
        pierre.getSpeedScore().setSpeed(8.321f);
        thomas.getSpeedScore().setSpeed(3.3595f);
        
        
        // Lead test
        /*
        hugo.getBoulderingScore().setNumberOfTop(4);
        hugo.getBoulderingScore().setNumberOfTryToTop(10);
        hugo.getBoulderingScore().setNumberOfBonus(4);
        hugo.getBoulderingScore().setNumberOfTryToBonus(4);
        
        pierre.getBoulderingScore().setNumberOfTop(4);
        pierre.getBoulderingScore().setNumberOfTryToTop(11);
        pierre.getBoulderingScore().setNumberOfBonus(4);
        pierre.getBoulderingScore().setNumberOfTryToBonus(4);
        
        thomas.getBoulderingScore().setNumberOfTop(4);
        thomas.getBoulderingScore().setNumberOfTryToTop(11);
        thomas.getBoulderingScore().setNumberOfBonus(3);
        thomas.getBoulderingScore().setNumberOfTryToBonus(4);*/

        comp.addClimber(hugo);
        comp.addClimber(pierre);
        comp.addClimber(thomas);
        
        System.out.println(comp.rank(RankType.TEXT));
    }
}
