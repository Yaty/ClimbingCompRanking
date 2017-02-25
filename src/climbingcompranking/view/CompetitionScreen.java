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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class CompetitionScreen extends Screen {
    private CompetitionType compType;
    private String compName;
    private final ObservableList<ClimberView> climbers;
    private TabPane tabPane;

    public CompetitionScreen(View view, CompetitionType competType, String compName, ObservableList<ClimberView> items) {
        super(view);
        this.compType = competType;
        this.compName = compName;
        this.climbers = items;
        setUp();
    }

        // private final String name;
        // HashMap<Category, ArrayList<Climber>> climbers;

        // CompetitionType compType;
        // ArrayList<Observer> observers;    
    
    @Override
    public void setUp() {
        view.getRoot().setGridLinesVisible(true); // debug
        
        /* Column constraints */
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(47);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(47);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(6);
        view.getRoot().getColumnConstraints().addAll(column1, column2, column3);
        
        // Row constraints
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(7);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(93);
        view.getRoot().getRowConstraints().addAll(row1, row2);
        
        Label compNameLabel = new Label(compName);
        Label compTypeLabel = new Label(compType.getName());
        
        Button plusBtn = new Button("+");
        plusBtn.setOnAction((ActionEvent event) -> {
            List<String> choices = new ArrayList<>();
            choices.addAll(Arrays.asList(Category.getNames()));
            if(choices.isEmpty()) return;
            
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle(I18n.MENU.getString("AddTab"));
            dialog.setHeaderText(I18n.MENU.getString("AddTabDialogHeader"));
            dialog.setContentText(I18n.MENU.getString("AddTabDialogText")); 
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                if(!doesThisTabExist(name) && view.getController().addCategory(name))
                    addTab(name);
                else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle(I18n.MENU.getString("Error"));
                    alert.setContentText(I18n.MENU.getString("CategoryAlreadyExist"));
                    alert.showAndWait();                    
                }
            });
        });
        
        tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();
        ArrayList<String> categories = getCategories();
        for(String categorie : categories)
            addTab(categorie);
        if(tabPane.getTabs().isEmpty()) {
            Text placeHolder = new Text(I18n.MENU.getString("AddTabToSee"));
            placeHolder.setFont( Font.font( null, FontWeight.NORMAL, 16 ) );
            BooleanBinding bb = Bindings.isEmpty( tabPane.getTabs() );
            placeHolder.visibleProperty().bind( bb );
            placeHolder.managedProperty().bind( bb ); 
            VBox box = new VBox(placeHolder, tabPane);
            box.setAlignment(Pos.CENTER);
            view.getRoot().add(box, 0, 1, 2, 1);
        }
        
        // bind to take available space
        borderPane.prefHeightProperty().bind(view.getScene().heightProperty());
        borderPane.prefWidthProperty().bind(view.getScene().widthProperty());
        borderPane.setCenter(tabPane);
        
        // Adding nodes
        view.getRoot().add(plusBtn, 2, 1);
        view.getRoot().add(compNameLabel, 0, 0);
        view.getRoot().add(compTypeLabel, 1, 0);
        view.getRoot().add(borderPane, 0, 1, 2, 1);
        
        GridPaneUtils.alignChildsInCenter(view.getRoot());
    }
    
    private void addTab(String text) {
        Tab tab = new Tab();
        tab.setText(text);
        tab.setOnCloseRequest((Event event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(I18n.MENU.getString("ConfirmDialogTitle"));
            alert.setContentText(I18n.MENU.getString("CloseTabAlertText"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                view.getController().removeCategory(text);
            } else
                event.consume(); // = do not close !
        });
        GridPane pane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100);
        pane.getColumnConstraints().add(col1);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(7);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(93);
        pane.getRowConstraints().addAll(row1, row2);
        
        //pane.setGridLinesVisible(true);
        Button plusBtn = new Button("+");
        GridPane.setMargin(plusBtn, new Insets(2, 5, 2, 5));
        plusBtn.setOnAction((ActionEvent event) -> {
            // Add climber here
        });
        TableView<ClimberView> climbersTable = new TableView<>();
        climbersTable.setItems(climbers);
        climbersTable.setRowFactory((TableView<ClimberView> tableView) -> {
            final TableRow<ClimberView> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem removeMenuItem = new MenuItem(I18n.MENU.getString("Remove"));
            removeMenuItem.setOnAction((ActionEvent event) -> {
                climbersTable.getItems().remove(row.getItem());
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
        
        TableColumn idCol = new TableColumn("LOL");
        //idCol.setCellValueFactory(new PropertyValueFactory("id"));
        
        climbersTable.getColumns().addAll(idCol);        
        pane.add(plusBtn, 0, 0);
        pane.add(climbersTable, 0, 1);
        pane.setAlignment(Pos.CENTER);
        tab.setContent(pane);
        tabPane.getTabs().add(tab);         
    }
    
    private ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        for(ClimberView climber : climbers)
            if(!categories.contains(climber.getCategory()))
                categories.add(climber.getCategory());
        return categories;
    }

    @Override
    public void update(Observable o) {
        Competition comp = (Competition) o;
        // And here we update everything (:/)
        
    }

    private boolean doesThisTabExist(String name) {
        for(Tab tab : tabPane.getTabs())
            if(tab.getText().equals(name))
                return true;
        return false;
    }
    
}
