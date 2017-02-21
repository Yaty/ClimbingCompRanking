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
package climbingcompranking.controller;

import climbingcompranking.model.Competition;
import climbingcompranking.model.ranking.RankType;
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
