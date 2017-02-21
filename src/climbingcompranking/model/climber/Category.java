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
package climbingcompranking.model.climber;

import climbingcompranking.utils.I18n;

/**
 *
 * @author Hugo Da Roit - contact@hdaroit.fr
 */
public enum Category {
    VETERAN(I18n.MODEL.getString("Veteran")),
    SENIOR(I18n.MODEL.getString("Senior")),
    JUNIOR(I18n.MODEL.getString("Junior")),
    YOUTHA(I18n.MODEL.getString("YouthA")), // Cadet
    YOUTHB(I18n.MODEL.getString("YouthB")), // Minime
    YOUTHC(I18n.MODEL.getString("YouthC")), // Benjamin
    YOUTHD(I18n.MODEL.getString("YouthD")), // Poussin
    YOUTHE(I18n.MODEL.getString("YouthE")), // Microbe
    YOUTHF(I18n.MODEL.getString("YouthF")); // Moustique
    
    private final String name;
    
    private Category (String name) {
        this.name = name;
    }
    
    public String getCategoryName() {
        return name;
    }
}
