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
package climbingcompranking.view;

import climbingcompranking.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public abstract class Screen implements Observer {
    protected View view;
    
    public Screen(View view) {
        this.view = view;
        view.setRoot(new GridPane());
        view.setScene(new Scene(view.getRoot()));
        view.getStage().setScene(view.getScene());
    }
    
    public abstract void setUp();
}
