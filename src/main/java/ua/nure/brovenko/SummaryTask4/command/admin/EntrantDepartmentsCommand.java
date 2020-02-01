package ua.nure.brovenko.SummaryTask4.command.admin;


import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.ApplicationDAO;
import ua.nure.brovenko.SummaryTask4.entity.Application;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for showing departments for current entrant
 * @author Ivan Brovenko
 */
public class EntrantDepartmentsCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Attribute departments list
     */
    private final static String ATTRIBUTE_DEPARTMENTS_LIST = "deps";

    /**
     * Attribute id
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Path to entrant departments
     */
    private final static String PATH_TO_ENTRANT_DEPARTMENTS = "path.page.admin.entrants.departments";

    /**
     * Execute EntrantDepartment command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getParameter(ATTRIBUTE_ID) != null) {
            int entrantId = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));
            ApplicationDAO applicationDAO = new ApplicationDAO();

            try {
                List<Application> departments = applicationDAO.findDepartmentsForUser(entrantId);
                request.setAttribute(ATTRIBUTE_DEPARTMENTS_LIST, departments);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_TO_ENTRANT_DEPARTMENTS);
    }
}
