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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public class ClimberView {
    private final StringProperty name, lastname, clubname, category;
    private final IntegerProperty id;
    
    public ClimberView(int id, String clubname, String lastname, String name, String category) {
        this.name = new SimpleStringProperty(name);
        this.lastname = new SimpleStringProperty(lastname);
        this.clubname = new SimpleStringProperty(clubname);
        this.id = new SimpleIntegerProperty(id);
        this.category = new SimpleStringProperty(category);
    }
    
    public int getId() {
        return id.get();
    }
    
    public String getName() {
        return name.get();
    }
    
    public String getLastname() {
        return lastname.get();
    }
    
    public String getCategory() {
        return category.get();
    }
    
    public String getClubname() {
        return clubname.get();
    }
}
