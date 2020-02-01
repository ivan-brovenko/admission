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
 * Command for updating departments
 * @author Ivan Brovenko
 */
public class UpdateDepartmentCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Id attribute
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Attribute update name
     */
    private final static String ATTRIBUTE_UPDATE_NAME = "upName";

    /**
     * Attribute update all places quantity
     */
    private final static String ATTRIBUTE_UPDATE_ALL = "upAll";

    /**
     * Attribute update free places quantity
     */
    private final static String ATTRIBUTE_UPDATE_FREE = "upFree";

    /**
     * Attribute duplicate name notification
     */
    private final static String ATTRIBUTE_DUPLICATE_NAME_NOTIF = "duplicateName";

    /**
     * Attribute old id
     */
    private final static String ATTRIBUTE_OLD_ID = "oldId";

    /**
     * Key for duplicate name notification
     */
    private final static String KEY_DUPLICATE_NAME_NOTIF = "update.departments.notif";

    /**
     * Path to update departments page
     */
    private final static String PATH_TO_UPDATE_PAGE = "path.page.admin.department.update";

    /**
     * Path redirect to departments
     */
    private final static String PATH_REDIRECT_TO_DEPARTMENTS = "path.page.admin.redirect.management";

    /**
     * Execute UpdateDepartment command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        if(request.getParameter(ATTRIBUTE_ID) != null && !request.getParameter(ATTRIBUTE_ID).equals("") &&
                request.getParameter(ATTRIBUTE_UPDATE_NAME) !=null &&
                request.getParameter(ATTRIBUTE_UPDATE_ALL) !=null &&
                request.getParameter(ATTRIBUTE_UPDATE_FREE) !=null){
            int id = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));
            String name = request.getParameter(ATTRIBUTE_UPDATE_NAME);
            int places = Integer.parseInt(request.getParameter(ATTRIBUTE_UPDATE_ALL));
            int free = Integer.parseInt(request.getParameter(ATTRIBUTE_UPDATE_FREE));

            try {
                departmentDAO.updateDepartmentById(id,name,places,free);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(),e);
                request.setAttribute(ATTRIBUTE_DUPLICATE_NAME_NOTIF, MessageManager.INSTANCE.getMessage(KEY_DUPLICATE_NAME_NOTIF));
                request.setAttribute(ATTRIBUTE_OLD_ID,request.getParameter(ATTRIBUTE_ID));

                return PathManager.INSTANCE.getString(PATH_TO_UPDATE_PAGE);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_DEPARTMENTS);
    }
}
