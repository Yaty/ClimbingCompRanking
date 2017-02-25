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
import climbingcompranking.model.climber.Category;
import climbingcompranking.model.climber.Climber;
import climbingcompranking.view.ClimberView;
import climbingcompranking.view.Screen;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class Controller {
    private Competition competition; // model

    public boolean createAComp(Competition.CompetitionType competType, String compName, ObservableList<ClimberView> climbers) {
        competition = new Competition(competType, compName);
        for(ClimberView climber : climbers)
            competition.addClimber(climber.getId(), climber.getName(), climber.getLastname(), Category.getCategoryByName(climber.getCategory()), climber.getClubname());
        return true;
    }

    public void setScreen(Screen compMenu) {
        competition.removeObserver();
        competition.addObserver(compMenu);
    }

    public boolean addCategory(String name) {
        Category cate = Category.getCategoryByName(name);
        if(cate == null) return false;
        competition.getClimbers().put(cate, new ArrayList<>());
        return true;
    }

    public void removeCategory(String text) {
        competition.getClimbers().remove(Category.getCategoryByName(text));
    }
}
