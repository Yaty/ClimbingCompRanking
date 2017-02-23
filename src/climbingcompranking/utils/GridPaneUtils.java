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
package climbingcompranking.utils;

import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class GridPaneUtils {
    public static int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }
    
    public static void alignChildsInCenter(GridPane pane) {
        for(int i = 0 ; i < pane.getChildren().size() ; i++) {
            GridPane.setHalignment(pane.getChildren().get(i), HPos.CENTER);
        }
    }
    
    public static void sizingRowsHeightEqually(GridPane pane) {
        final int heightPerRowInPourcent = 100 / GridPaneUtils.getRowCount(pane);
        for(int i = 0 ; i < GridPaneUtils.getRowCount(pane) ; i++) {
            if(pane.getChildren().get(i).isManaged()) {
                RowConstraints rowConstraint = new RowConstraints();
                rowConstraint.setPercentHeight(heightPerRowInPourcent);
                pane.getRowConstraints().add(rowConstraint);
            }
        }
    }

    public static int getColumnCount(GridPane pane) {
        int numColumns = pane.getColumnConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer columnIndex = GridPane.getColumnIndex(child);
                if(columnIndex != null){
                    numColumns = Math.max(numColumns,columnIndex+1);
                }
            }
        }
        return numColumns;
    }
}
