package ua.nure.brovenko.SummaryTask4.command.admin;


import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.MessageManager;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for adding new department
 * @author Ivan Brovenko
 */
public class AddDepartmentCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Error message if current id is occupied
     */
    private static final String OCCUPIED_ID_ERROR = "id is occupied";

    /**
     * Id parameter
     */
    private static final String DEPARTMENT_ID_PARAMETER = "id";

    /**
     * Department name parameter
     */
    private static final String DEPARTMENT_NAME_PARAMETER = "name";

    /**
     * All places parameter
     */
    private static final String ALL_PLACES_PARAMETER = "all";

    /**
     * Free places parameter
     */
    private static final String FREE_PLACES_PARAMETER = "free";

    /**
     * Duplicate id attribute
     */
    private static final String DUPLICATE_ID_ATTRIBUTE = "duplicateId";

    /**
     * Path redirect to management
     */
    private static final String PATH_REDIRECT_TO_MANAGEMENT = "path.page.admin.redirect.management";

    /**
     * Notification key
     */
    private static final String KEY_NOTIFICATION = "departments.notif1";

    /**
     * Execute AddToDepartment command
     * @param request request to read the command
     * @param response response to the command
     * @return path to a jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        HttpSession session=request.getSession();

        if(request.getParameter(DEPARTMENT_ID_PARAMETER)!= null && request.getParameter(DEPARTMENT_NAME_PARAMETER)!= null &&
                request.getParameter(ALL_PLACES_PARAMETER)!= null && request.getParameter(FREE_PLACES_PARAMETER)!= null){

            int id = Integer.parseInt(request.getParameter(DEPARTMENT_ID_PARAMETER));
            String name = request.getParameter(DEPARTMENT_NAME_PARAMETER);
            int places = Integer.parseInt(request.getParameter(ALL_PLACES_PARAMETER));
            int freePlaces = Integer.parseInt(request.getParameter(FREE_PLACES_PARAMETER));

            try {
                departmentDAO.addDepartment(id,name,places,freePlaces);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(OCCUPIED_ID_ERROR);
                session.setAttribute(DUPLICATE_ID_ATTRIBUTE, MessageManager.INSTANCE.getMessage(KEY_NOTIFICATION));
                return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_MANAGEMENT);
            }

        }
        session.removeAttribute(DUPLICATE_ID_ATTRIBUTE);

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_MANAGEMENT);
    }
}
