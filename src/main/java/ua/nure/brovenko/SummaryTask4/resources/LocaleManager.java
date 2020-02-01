package ua.nure.brovenko.SummaryTask4.resources;


import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Locale manager
 * @author Ivan Brovenko
 */
public class LocaleManager {

    /**
     * Logger
     */
    private static Logger logger = Logger.getRootLogger();

    /**
     * Method for setting locale
     * @param request request
     */
    public static void set(HttpServletRequest request){
        String language = request.getParameter("language");
        Locale locale = Locale.getDefault();

        if(!language.equals("")) {
            locale = new Locale(language);
            logger.info("Language changed to "+language);
        }

        Locale.setDefault(locale);
        request.setAttribute("lang",locale.getLanguage().toUpperCase());



    }

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage().toUpperCase();
    }
}
