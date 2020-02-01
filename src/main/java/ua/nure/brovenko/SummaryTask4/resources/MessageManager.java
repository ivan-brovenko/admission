package ua.nure.brovenko.SummaryTask4.resources;


import java.util.ResourceBundle;

/**
 * Message manager
 * @author Ivan Brovenko
 */
public enum MessageManager {
    INSTANCE;

    /**
     * Resource bundle name
     */
    private static final String BUNDLE_NAME = "lan";

    /**
     * Resource bundle object
     */
    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     * Getter for resourse bundle
     * @param key key string
     * @return paramener string
     */
    public synchronized String getMessage(String key) {return bundle.getString(key);}

}
