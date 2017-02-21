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
package climbingcompranking.controller;

import climbingcompranking.model.Competition;
import climbingcompranking.model.Rank;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.model.ranking.RankType;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class ButtonsController {
    private Competition comp;
    
    public EventHandler<ActionEvent> infosButton = (ActionEvent event) -> {
        System.out.println("Infos.");
    };
    
    public EventHandler<ActionEvent> quitButton = (ActionEvent event) -> {
        Platform.exit();
        System.exit(0);
    };
    
    public EventHandler<ActionEvent> createCompButton = (ActionEvent event) -> {
        System.out.println("Create comp.");
    };
    
    public void loadCompButton(String compName) {
        comp = CompetitionLoader.loadComp(compName);
        System.out.println(comp);
        System.out.println("Rank : ");
        System.out.println(comp.rank(RankType.TEXT));
    }
}
