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

import climbingcompranking.controller.ButtonsController;
import climbingcompranking.utils.GridPaneUtils;
import climbingcompranking.utils.I18n;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class MainMenu {
    private final Stage primaryStage;
    private final Scene scene;
    private final GridPane root;
    private final ButtonsController buttonsCtrl;
    
    public MainMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.root = new GridPane();
        this.scene = new Scene(root);
        this.buttonsCtrl = new ButtonsController();
        setupStage();
        setupMainMenu();
    }

    private void setupStage() {
        primaryStage.setWidth(1080);
        primaryStage.setHeight(720);
        primaryStage.setTitle(I18n.MENU.getString("Title"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupMainMenu() {
        root.getChildren().clear();
        
        root.setGridLinesVisible(true); // debug
        
        /* Column constraints */
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(25);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(25);
        root.getColumnConstraints().addAll(
            column1,
            column2,
            column3
        );
        
        /* Buttons, images, labels ... */
        Image climberImg = new Image("climbingcompranking/utils/resources/images/climber.jpg", scene.getWidth()/2, scene.getHeight(), false, true);
        ImageView climberImgView = new ImageView(climberImg);
        Label welcome = new Label(I18n.MENU.getString("Welcome") + ' ' + primaryStage.getTitle() + " !");
        Button createCompButton = new Button(I18n.MENU.getString("AddComp"));
        createCompButton.setOnAction(buttonsCtrl.createCompButton);
        ChoiceBox cb = new ChoiceBox();
        cb.getItems().addAll(
            "comp1",
            "comp2",
            "comp3"
        );
        cb.getSelectionModel().select(0);
        Button loadComp = new Button(I18n.MENU.getString("LoadComp"));
        loadComp.setOnAction(event -> buttonsCtrl.loadCompButton(cb.getSelectionModel().getSelectedItem().toString()));
        Button infos = new Button(I18n.MENU.getString("Infos"));
        infos.setOnAction(buttonsCtrl.infosButton);
        Button exit = new Button(I18n.MENU.getString("Exit"));
        exit.setOnAction(buttonsCtrl.quitButton);
        
        /* Adding nodes to the gridpane */
        root.add(welcome, 1, 0, 2, 1);
        root.add(createCompButton, 1, 1, 2, 1);
        root.add(cb, 1, 2);
        root.add(loadComp, 2, 2);
        root.add(infos, 1, 3, 2, 1);
        root.add(exit, 1, 4, 2, 1);
        root.add(climberImgView, 0, 0, 1, GridPaneUtils.getRowCount(root)); // must be the last element to add
        
        /* Style */
        GridPaneUtils.alignChildsInCenter(root);
        GridPaneUtils.sizingRowsHeightEqually(root);
    }
    
}
