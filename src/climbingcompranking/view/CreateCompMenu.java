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

import climbingcompranking.model.Competition;
import climbingcompranking.model.Competition.CompetitionType;
import climbingcompranking.model.climber.Category;
import climbingcompranking.utils.GridPaneUtils;
import climbingcompranking.utils.I18n;
import climbingcompranking.utils.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class CreateCompMenu extends Screen {
    private final ObservableList<ClimberView> climberList;
    
    public CreateCompMenu(View view) {
        super(view);
        climberList = FXCollections.observableArrayList();
        view.setRoot(new GridPane());
        view.setScene(new Scene(view.getRoot()));
        view.getStage().setScene(view.getScene());
        setUp();
    }

    @Override
    public void setUp() {
        view.getRoot().setGridLinesVisible(true); // debug
        
        /* Column constraints */
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(20);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(20);
        view.getRoot().getColumnConstraints().addAll(column1, column2, column3, column4, column5);
        
        // Row constraints
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(5);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(10);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(55);
        view.getRoot().getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);
                
        // First label
        Label createCompLabel = new Label(I18n.MENU.getString("CreateCompLabel"));
        
        // Choose the competition type
        Label choiceCompTypeLabel = new Label(I18n.MENU.getString("ChooseCompType") + " :");
        ChoiceBox choiceCompType = new ChoiceBox();
        choiceCompType.getItems().addAll((Object[]) Competition.CompetitionType.names());
        choiceCompType.getSelectionModel().selectFirst();
        
        // Competition name
        Label choiceCompNameLabel = new Label(I18n.MENU.getString("ChooseCompName") + " :");
        TextField compNameField = new TextField();
        
        // Add climbers
        Label addClimberLabel = new Label(I18n.MENU.getString("AddClimber") + " (" + I18n.MENU.getString("Optional") + ") :");
        
        TextField clubField = new TextField();
        clubField.promptTextProperty().set(I18n.MENU.getString("ClubName"));
        TextField lastnameField = new TextField();
        lastnameField.promptTextProperty().set(I18n.MENU.getString("Lastname"));
        TextField nameField = new TextField();
        nameField.promptTextProperty().set(I18n.MENU.getString("Name"));
        ChoiceBox categoryField = new ChoiceBox();
        categoryField.getItems().addAll((Object[]) Category.getNames());
        categoryField.getSelectionModel().selectFirst();
        
        Button addClimberButton = new Button(I18n.MENU.getString("AddClimber"));        
        
        TableView<ClimberView> climbers = new TableView<>();
        climbers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        climbers.setItems(climberList);
        climbers.setRowFactory((TableView<ClimberView> tableView) -> {
            final TableRow<ClimberView> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem removeMenuItem = new MenuItem(I18n.MENU.getString("Remove"));
            removeMenuItem.setOnAction((ActionEvent event) -> {
                climbers.getItems().remove(row.getItem());
            });
            contextMenu.getItems().add(removeMenuItem);
            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row;  
        });
        
        TableColumn idCol = new TableColumn("ID");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        idCol.setVisible(false);
        
        TableColumn clubCol = new TableColumn(I18n.MODEL.getString("Club"));
        clubCol.setCellValueFactory(new PropertyValueFactory("clubname"));
        clubCol.setStyle("-fx-alignment: CENTER;");
        
        TableColumn lastnameCol = new TableColumn(I18n.MODEL.getString("Lastname"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory("lastname"));
        lastnameCol.setStyle("-fx-alignment: CENTER;");
        
        TableColumn nameCol = new TableColumn(I18n.MODEL.getString("Name"));
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.setStyle("-fx-alignment: CENTER;");
        
        TableColumn cateCol = new TableColumn(I18n.MODEL.getString("Category"));
        cateCol.setCellValueFactory(new PropertyValueFactory("category"));
        cateCol.setStyle("-fx-alignment: CENTER;");
        
        climbers.getColumns().addAll(idCol, clubCol, lastnameCol, nameCol, cateCol);
        
        addClimberButton.setOnAction((ActionEvent event) -> {
            String name = nameField.getText();
            String lastname = lastnameField.getText();
            String clubname = clubField.getText();
            String category = (String) categoryField.getSelectionModel().getSelectedItem();
            int id = 0;
            if(climbers.getItems().size() > 0)
                id = climbers.getItems().get(climbers.getItems().size()-1).getId() + 1;
            if(name.isEmpty() || lastname.isEmpty() || category == null || Category.getCategoryByName(category) == null) return;
            climberList.add(new ClimberView(id, clubname, lastname, name, category));
        });
        
        // Create comp button
        Button createComp = new Button(I18n.MENU.getString("AddComp"));
        createComp.setOnAction((ActionEvent event) -> {
            CompetitionType competType = Competition.CompetitionType.nameToCompType((String) choiceCompType.getSelectionModel().getSelectedItem());
            String compName = compNameField.getText();
            if(competType == null || compName.isEmpty()) return; // Can't create a competition without a comp type and a name
            if(view.getController().createAComp(competType, compName, climbers.getItems())) {
                CompetitionMenu compMenu = new CompetitionMenu(view);
                view.getController().setScreen(compMenu);
                view.setScreen(compMenu);
            }
        });
        
        // Adding all elements to the gridpane
        view.getRoot().add(createCompLabel, 0, 0, 5, 1);
        view.getRoot().add(choiceCompTypeLabel, 0, 1);
        view.getRoot().add(choiceCompType, 1, 1);
        view.getRoot().add(choiceCompNameLabel, 3, 1);
        view.getRoot().add(compNameField, 4, 1);
        view.getRoot().add(createComp, 2, 2);
        view.getRoot().add(addClimberLabel, 0, 3, 5, 1);
        view.getRoot().add(clubField, 0, 4);
        view.getRoot().add(lastnameField, 1, 4);
        view.getRoot().add(nameField, 2, 4);
        view.getRoot().add(categoryField, 3, 4);
        view.getRoot().add(addClimberButton, 4, 4);
        view.getRoot().add(climbers, 0, 5, 5, 1);        
        
        int rowSizePx = (int) (view.getStage().getWidth()/GridPaneUtils.getColumnCount(view.getRoot()));
        compNameField.setMaxWidth(0.9 * rowSizePx);
        clubField.setMaxWidth(0.9 * rowSizePx);
        lastnameField.setMaxWidth(0.9 * rowSizePx);
        nameField.setMaxWidth(0.9 * rowSizePx);
        GridPaneUtils.alignChildsInCenter(view.getRoot());
    }

    @Override
    public void update(Observable o, Object arg) {}
    
}
