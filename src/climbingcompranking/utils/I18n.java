/*
 * Customers Java Swing Application Demo
 *
 * Copyright(c) 2013, devsniper.com
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
