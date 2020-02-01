package ua.nure.brovenko.SummaryTask4.command.entrant;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.entity.Department;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.AtestatAttributeConstants;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for adding final certificate marks into the session
 * @author Ivan Brovenko
 */
public class RegAtestatCommand extends EntrantCommand{
    
    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Enum for getting certificate attributes
     */
    private final AtestatAttributeConstants atConst = AtestatAttributeConstants.INSTANSE;

    /**
     * List of departments attribute
     */
    private final static String ATTRIBUTE_DEPARTMENT_LIST = "departments";

    /**
     * Path redirect to entrant account
     */
    private final static String PATH_REDIRECT_ACCOUNT = "path.page.client.redirect.account";

    /**
     * Execute RegAtestat command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp file
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Department> list = null;

        setSessoinAtributes(request,session,atConst.NUMBER_ATT);
        setSessoinAtributes(request,session,atConst.UKR_MOVA);
        setSessoinAtributes(request,session,atConst.UKR_LIT);
        setSessoinAtributes(request,session,atConst.RUSSIAN);
        setSessoinAtributes(request,session,atConst.RUSSIAN_LIT);
        setSessoinAtributes(request,session,atConst.ENGLISH);
        setSessoinAtributes(request,session,atConst.ALGEBRA);
        setSessoinAtributes(request,session,atConst.INFORM);
        setSessoinAtributes(request,session,atConst.GEOMETRY);
        setSessoinAtributes(request,session,atConst.HISTORY);
        setSessoinAtributes(request,session,atConst.SPORT);
        setSessoinAtributes(request,session,atConst.PHISICS);

        try {
            list = departmentDAO.findAllDepartments();
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
        request.setAttribute(ATTRIBUTE_DEPARTMENT_LIST, list);

        return PathManager.INSTANCE.getString(PATH_REDIRECT_ACCOUNT);
    }

    /**
     * Method for faster setting attribute to a session
     * @param request request to get attributes from
     * @param session session to set attribute for
     * @param parameter attribute name
     */
    private void setSessoinAtributes(HttpServletRequest request,HttpSession session,String parameter){
        session.setAttribute(parameter,request.getParameter(parameter));
    }
}
