package ua.nure.brovenko.SummaryTask4.resources;


import java.util.ResourceBundle;

/**
 * Email manager
 * @author Ivan Brovenko
 */
public enum EmailManager {
    INSTANCE;

    /**
     * Resource bundle name
     */
    private final static String BUNDLE_NAME = "email";

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
