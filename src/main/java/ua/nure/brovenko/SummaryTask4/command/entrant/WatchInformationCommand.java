package ua.nure.brovenko.SummaryTask4.command.entrant;


import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.dao.AtestatDAO;
import ua.nure.brovenko.SummaryTask4.dao.ZnoSubjectDAO;
import ua.nure.brovenko.SummaryTask4.entity.Atestat;
import ua.nure.brovenko.SummaryTask4.entity.Subject;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for showing information with final certificate number and
 * final test results to an entrant
 * @author Brovenko Ivan
 */
public class WatchInformationCommand extends EntrantCommand {

    /**
     * Logger
     */
    private final static Logger logger = Logger.getRootLogger();

    /**
     * Path to entrant information
     */
    private final static String PATH_TO_ENTRANT_INFORMATION = "path.page.client.reg.information";

    /**
     * Subject list attribute
     */
    private final static String ATTRIBUTE_SUBJECT_LIST = "subjects";

    /**
     * Certificate attribute
     */
    private final static String ATTRIBUTE_ATESTAT = "atestat";

    /**
     * Execute WatchInformation command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(LoginLogic.SESSION_VAR);
        ZnoSubjectDAO znoSubjectDAO = new ZnoSubjectDAO();
        List<Subject> subjects = null;
        AtestatDAO atestatDAO = new AtestatDAO();
        Atestat atestat = null;

        try {
            subjects = znoSubjectDAO.findZnoSubjectsByUserId(user.getId());
            request.setAttribute(ATTRIBUTE_SUBJECT_LIST,subjects);
            atestat = atestatDAO.getAtestatByEntrantId(user.getId());
            request.setAttribute(ATTRIBUTE_ATESTAT,atestat);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
        
        return PathManager.INSTANCE.getString(PATH_TO_ENTRANT_INFORMATION);
    }
}
