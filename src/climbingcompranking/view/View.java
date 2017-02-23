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

import climbingcompranking.controller.Controller;
import climbingcompranking.utils.I18n;
import climbingcompranking.utils.Observable;
import climbingcompranking.utils.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class View {
    private final Controller controller;
    private final Stage primaryStage;
    private Scene scene;
    private GridPane root;
    private Screen screen;

    public View(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        this.setupStage();
        this.screen = new MainMenu(this);
    }
    
    private void setupStage() {
        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);
        primaryStage.setTitle(I18n.MENU.getString("Title"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void setScreen(Screen screen) {
        this.screen = screen;
    }
    
    public Scene getScene() {
        return scene;
    }

    public void setRoot(GridPane gridPane) {
        this.root = gridPane;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public GridPane getRoot() {
        return root;
    }

    public Stage getStage() {
        return primaryStage;
    }
    
    public Controller getController() {
        return controller;
    }
}
