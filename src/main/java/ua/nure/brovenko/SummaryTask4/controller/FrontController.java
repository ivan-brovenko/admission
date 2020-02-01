package ua.nure.brovenko.SummaryTask4.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.MissingResourceException;


import ua.nure.brovenko.SummaryTask4.command.ActionCommand;
import ua.nure.brovenko.SummaryTask4.command.ActionFactory;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.LocaleManager;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

/**
 * Front controller
 * @author Ivan Brovenko
 */

public class FrontController extends HttpServlet {

    /**
     * Path to error page
     */
    private String errorPagePath;

    /**
     * Action factory
     */
    private ActionFactory actionFactory = new ActionFactory();

    /**
     * Logger
     */
    Logger logger =Logger.getRootLogger();

    /**
     * Init method
     * @throws ServletException if something goes wrong
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        try{
            errorPagePath = PathManager.INSTANCE.getString("path.error500");
        } catch (MissingResourceException e){
            logger.error(e.getMessage());
        }
    }

    /**
     * Post method
     * @param request request to read the command
     * @param response response to the command
     * @throws ServletException if something goes wrong
     * @throws IOException if something goes wrong
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this) {
            processRequest(request, response);
        }
    }

    /**
     * Get method
     * @param request request to read the command
     * @param response response to the command
     * @throws ServletException if something goes wrong
     * @throws IOException if something goes wrong
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized (this) {
            processRequest(request, response);
        }
    }

    /**
     * Process request method for execution all the commands
     * @param request request to read the command
     * @param response response to the command
     * @throws ServletException if something goes wrong
     * @throws IOException if something goes wrong
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LocaleManager.set(request);
        ActionCommand command = actionFactory.getCommand(request);
        String page = null;
        
        try {
            logger.info("Command:" + command.getClass().getSimpleName());
            page = command.execute(request,response);
            if (page != null) {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException e) {
            if(errorPagePath != null){
                request.getRequestDispatcher(errorPagePath).forward(request,response);
                logger.error(e.getMessage(),e);
            }
        }
    }
}


