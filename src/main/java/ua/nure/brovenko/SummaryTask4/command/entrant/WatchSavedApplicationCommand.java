package ua.nure.brovenko.SummaryTask4.command.entrant;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.dao.ApplicationDAO;
import ua.nure.brovenko.SummaryTask4.dao.AtestatDAO;
import ua.nure.brovenko.SummaryTask4.entity.Application;
import ua.nure.brovenko.SummaryTask4.entity.Atestat;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for showing zno applications to an entrant 
 * @author Ivan Brovenko
 */
public class WatchSavedApplicationCommand extends EntrantCommand {

    /**
     * Logger
     */
    private final Logger logger=Logger.getRootLogger();

    /**
     * Application list attribute
     */
    private final static String ATTRIBUTE_APPLICATION_LIST = "applications";

    /**
     * Certificate attribute
     */
    private final static String ATTRIBUTE_ATESTAT = "atestat";

    /**
     * Path to saved applications
     */
    private final static String PATH_TO_SAVED_APPLICATIONS = "path.page.client.reg.saved.applications";

    /**
     * Execute WatchSavedApplication command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        AtestatDAO atestatDAO = new AtestatDAO();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(LoginLogic.SESSION_VAR);
        List<Application> applications = null;
        Atestat atestat = null;
        
        try {
            applications = applicationDAO.findDepartmentsForUser(user.getId());
            request.setAttribute(ATTRIBUTE_APPLICATION_LIST,applications);
            atestat = atestatDAO.getAtestatByEntrantId(user.getId());
            request.setAttribute(ATTRIBUTE_ATESTAT,atestat);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
        
        return PathManager.INSTANCE.getString(PATH_TO_SAVED_APPLICATIONS);
    }
}
