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
    VETERAN_MALE(I18n.MODEL.getString("VeteranM")),
    VETERAN_FEMALE(I18n.MODEL.getString("VeteranF")),
    SENIOR_MALE(I18n.MODEL.getString("SeniorM")),
    SENIOR_FEMALE(I18n.MODEL.getString("SeniorF")),
    JUNIOR_MALE(I18n.MODEL.getString("JuniorM")),
    JUNIOR_FEMALE(I18n.MODEL.getString("JuniorF")),
    YOUTHA_MALE(I18n.MODEL.getString("YouthAM")), // Cadet
    YOUTHA_FEMALE(I18n.MODEL.getString("YouthAF")), // Cadet
    YOUTHB_MALE(I18n.MODEL.getString("YouthBM")), // Minime
    YOUTHB_FEMALE(I18n.MODEL.getString("YouthBF")), // Minime
    YOUTHC_MALE(I18n.MODEL.getString("YouthCM")), // Benjamin
    YOUTHC_FEMALE(I18n.MODEL.getString("YouthCF")), // Benjamin
    YOUTHD_MALE(I18n.MODEL.getString("YouthDM")), // Poussin
    YOUTHD_FEMALE(I18n.MODEL.getString("YouthDF")), // Poussin
    YOUTHE_MALE(I18n.MODEL.getString("YouthEM")), // Microbe
    YOUTHE_FEMALE(I18n.MODEL.getString("YouthEF")), // Microbe
    YOUTHF_MALE(I18n.MODEL.getString("YouthFM")),  // Moustique
    YOUTHF_FEMALE(I18n.MODEL.getString("YouthFF")); // Moustique
    
    private final String name;
    
    private Category (String name) {
        this.name = name;
    }
    
    public String getCategoryName() {
        return name;
    }
}
