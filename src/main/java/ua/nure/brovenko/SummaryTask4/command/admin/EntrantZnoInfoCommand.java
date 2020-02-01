package ua.nure.brovenko.SummaryTask4.command.admin;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.AtestatDAO;
import ua.nure.brovenko.SummaryTask4.dao.ZnoSubjectDAO;
import ua.nure.brovenko.SummaryTask4.entity.Atestat;
import ua.nure.brovenko.SummaryTask4.entity.Subject;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for watching entrant zno subjects
 * @author Ivan Brovenko
 */
public class EntrantZnoInfoCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Attribute id
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Attribute list of subjects
     */
    private final static String ATTRIBUTE_SUBJECTS = "subjects";

    /**
     * Path to entrant zno page
     */
    private final static String PATH_TO_ENTRANT_ZNO_PAGE = "path.page.admin.entrant.zno";


    /**
     * Execute EntrantZnoInfo command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getParameter(ATTRIBUTE_ID) != null) {
            int id = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));
            ZnoSubjectDAO znoSubjectDAO = new ZnoSubjectDAO();
            List<Subject> subjects = null;

            try {
                subjects = znoSubjectDAO.findZnoSubjectsByUserId(id);
                request.setAttribute(ATTRIBUTE_SUBJECTS, subjects);

            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_TO_ENTRANT_ZNO_PAGE);
    }
}
