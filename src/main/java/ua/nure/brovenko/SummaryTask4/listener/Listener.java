package ua.nure.brovenko.SummaryTask4.listener;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.dao.AtestatDAO;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Application listener
 * @author Ivan Brovenko
 */
public class Listener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * ContextInitialized method
     * @param servletContextEvent servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
    }

    /**
     * ContextDestroyed method
     * @param servletContextEvent servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        File upload = new File(PathManager.INSTANCE.getString("upload.location"));
        File[] listOfFiles1 = upload.listFiles();

        for (int i = 0; i < listOfFiles1.length; i++) {
            if (listOfFiles1[i].isFile()) {
                listOfFiles1[i].delete();
            }
        }

        File folder = new File(PathManager.INSTANCE.getString("archive.location"));
        File[] listOfFiles = folder.listFiles();
        AtestatDAO atestatDAO=new AtestatDAO();
        try {
            List<String> scans=atestatDAO.findAllScans();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(!scans.contains(listOfFiles[i].getName())){
                        listOfFiles[i].delete();
                    }
                }
            }
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * RequestDestroyed method
     * @param servletRequestEvent servlet request event
     */
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {}

    /**
     * RequestInitialized method
     * @param servletRequestEvent servlet request event
     */
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest request=servletRequestEvent.getServletRequest();

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
    }

    /**
     * AttributeAdded method
     * @param httpSessionBindingEvent event
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {}

    /**
     * AttributeRemoved method
     * @param httpSessionBindingEvent event
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {}

    /**
     * AttributeReplaced method
     * @param httpSessionBindingEvent event
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {}

    /**
     * SessionCreated method
     * @param httpSessionEvent event
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {}

    /**
     * SessionDestroyed method
     * @param httpSessionEvent event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session=httpSessionEvent.getSession();
        File file=new File(PathManager.INSTANCE.getString("upload.location")+"\\"+(String)session.getAttribute("fileName"));
        if(file.exists()){
            file.delete();
        }
    }
}
