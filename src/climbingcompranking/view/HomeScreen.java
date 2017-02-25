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

import climbingcompranking.utils.GridPaneUtils;
import climbingcompranking.utils.I18n;
import climbingcompranking.utils.Observable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class HomeScreen extends Screen {
    
    public HomeScreen(View view) {
        super(view);
        setUp();
    }

    @Override
    public void setUp() {
        view.getRoot().setGridLinesVisible(true); // debug
        
        /* Column constraints */
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(25);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(25);
        view.getRoot().getColumnConstraints().addAll(
            column1,
            column2,
            column3
        );
        
        /* Buttons, images, labels ... */
        Image climberImg = new Image("climbingcompranking/utils/resources/images/climber.jpg", view.getScene().getWidth()/2, view.getScene().getHeight(), false, true);
        ImageView climberImgView = new ImageView(climberImg);
        Label welcome = new Label(I18n.MENU.getString("Welcome") + ' ' + view.getStage().getTitle() + " !");
        Button createCompButton = new Button(I18n.MENU.getString("AddComp"));
        createCompButton.setOnAction((ActionEvent event) -> {
            view.setScreen(new CreateCompScreen(view));
        });
        Button loadCompButton = new Button(I18n.MENU.getString("LoadComp"));
        loadCompButton.setOnAction((ActionEvent event) -> {
            
        });
        Button infosButton = new Button(I18n.MENU.getString("Infos"));
        infosButton.setOnAction((ActionEvent event) -> {
            
        });
        Button exit = new Button(I18n.MENU.getString("Exit"));
        exit.setOnAction((ActionEvent event) -> {
            Platform.exit();
            System.exit(0);   
        });
        
        /* Adding nodes to the gridpane */
        view.getRoot().add(welcome, 1, 0, 2, 1);
        view.getRoot().add(createCompButton, 1, 1, 2, 1);
        view.getRoot().add(loadCompButton, 1, 2, 2, 1);
        view.getRoot().add(infosButton, 1, 3, 2, 1);
        view.getRoot().add(exit, 1, 4, 2, 1);
        view.getRoot().add(climberImgView, 0, 0, 1, GridPaneUtils.getRowCount(view.getRoot())); // must be the last element to add
        
        /* Style */
        GridPaneUtils.alignChildsInCenter(view.getRoot());
        GridPaneUtils.sizingRowsHeightEqually(view.getRoot());
    }

    @Override
    public void update(Observable o) {}
    
}
