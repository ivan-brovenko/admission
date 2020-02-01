package ua.nure.brovenko.SummaryTask4.command.admin;


import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for deleting departments
 * @author Ivan Brovenko
 */
public class DeleteDepartmentCommand extends AdminCommand{

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Id attribute
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Path redirect to department page
     */
    private final static String PATH_REDIRECT_DEPARTMENTS = "path.page.admin.redirect.management";

    /**
     * Execute DeleteDepartment command
     * @param request request to read command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        if(!request.getParameter(ATTRIBUTE_ID).equals("")){
            int id = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));
            try {
                departmentDAO.deleteDepartmentById(id);

            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_REDIRECT_DEPARTMENTS);
    }
}
