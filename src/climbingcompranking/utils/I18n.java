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

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resource Bundle helper.
 *
 * <p>
 * Each module has its own resource bundle file for i18n strings. Always read
 * default locale from <code>Locale.getDefault()</code>.
 *
 * <p>
 * <pre>
 *      I18n.MODULE_NAME.getString("stringKey");
 * </pre>
 *
 * @see ResourceBundle
 *
 * @author Cem Ikta
 */
public enum I18n {

    /**
     * MENU
     */
    MENU("Menu"),
    MODEL("Model");

    private final ResourceBundle resourceBundle;
    private static final String DEFAULT_LOCATION = "climbingcompranking/utils/resources/i18n/";
    private final static Logger LOGGER = Logger.getLogger(I18n.class.getName());
    private I18n(String bundleFile) {
        resourceBundle = ResourceBundle.getBundle(DEFAULT_LOCATION + bundleFile);
    }

    /**
     * Gets a string for the given key from resource bundle.
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return "err#";
        }
    }

}
