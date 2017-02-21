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
}
