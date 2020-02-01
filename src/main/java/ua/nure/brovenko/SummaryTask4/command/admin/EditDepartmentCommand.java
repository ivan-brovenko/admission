package ua.nure.brovenko.SummaryTask4.command.admin;


import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for resetting request parameters
 * for update command on pressing edit key
 * @author Ivan Brovenko
 */
public class EditDepartmentCommand extends AdminCommand{

    /**
     * Old department id
     */
    private final static String OLD_ID = "oldId";

    /**
     * Old department name
     */
    private final static String OLD_NAME = "oldName";

    /**
     * Old all places quantity
     */
    private final static String OLD_ALL_PLACES = "oldAll";

    /**
     * Old free places quantity
     */
    private final static String OLD_FREE_PLACES = "oldFree";

    /**
     * New id
     */
    private final static String NEW_ID = "id";

    /**
     * New department name
     */
    private final static String NEW_NAME = "name";

    /**
     * New all places quantity
     */
    private final static String NEW_ALL_PLACES = "all";

    /**
     * New free places quantity
     */
    private final static String NEW_FREE_PLACES = "free";

    /**
     * Path to update page
     */
    private final static String PATH_TO_UPDATE_PAGE = "path.page.admin.department.update";

    /**
     * Execute EditDepartment command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.setAttribute(OLD_ID,request.getParameter(NEW_ID));
        request.setAttribute(OLD_NAME,request.getParameter(NEW_NAME));
        request.setAttribute(OLD_ALL_PLACES,request.getParameter(NEW_ALL_PLACES));
        request.setAttribute(OLD_FREE_PLACES,request.getParameter(NEW_FREE_PLACES));

        return PathManager.INSTANCE.getString(PATH_TO_UPDATE_PAGE);
    }
}
