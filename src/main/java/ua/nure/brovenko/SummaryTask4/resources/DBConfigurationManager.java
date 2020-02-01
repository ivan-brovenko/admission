package ua.nure.brovenko.SummaryTask4.resources;

import java.util.ResourceBundle;

/**
 * Database configuration manager with database properties
 * @author Ivan Brovenko
 */
public enum DBConfigurationManager {
    INSTANCE;

    /**
     * Resource bundle name
     */
    private static final String BUNDLE_NAME = "database";

    /**
     * Database driver name
     */
    public static final String DATABASE_DRIVER_NAME = "driver_name";

    /**
     * Database connection url
     */
    public static final String DATABASE_CONNECTION_URL = "url";

    /**
     * Database username
     */
    public static final String DATABASE_USERNAME = "username";

    /**
     * Database password
     */
    public static final String DATABASE_PASSWORD = "password";

    /**
     * Resource bundle object
     */
    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Getter for resourse bundle
     * @param key key string
     * @return paramener string
     */
    public String getString(String key) {
        return bundle.getString(key);
    }
}
