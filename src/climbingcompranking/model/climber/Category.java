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
}
