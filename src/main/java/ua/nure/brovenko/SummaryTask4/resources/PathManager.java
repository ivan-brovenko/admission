package ua.nure.brovenko.SummaryTask4.resources;

import java.util.ResourceBundle;

/**
 * Performs path reading from property file
 * @author Ivan Brovenko
 */
public enum PathManager {
    INSTANCE;

    /**
     * Resource bundle name
     */
    private static final String BUNDLE_NAME = "path";

    /**
     * Resource bundle object
     */
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Getter for resourse bundle
     * @param key key string
     * @return paramener string
     */
    public synchronized String getString(String key) {
        return bundle.getString(key);
    }
}
