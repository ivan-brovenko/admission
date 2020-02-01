package ua.nure.brovenko.SummaryTask4.command.admin;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.AdmissionDAO;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.entity.Department;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.MessageManager;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for adding entrants to admission
 * @author Ivan Brovenko
 */
public class AddToAdmissionCommand extends AdminCommand{

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Attribute that occurs if an entrant doesn't get in
     */
    private final static String NO_UNIVER_ATTRIBUTE = "noUniver";

    /**
     * Attribute id
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Path redirect to entrants
     */
    private final static String PATH_REDIRECT_TO_ENTRANTS = "path.page.redirect.entrantr.management";

    /**
     * Notification key
     */
    private final static String KEY_NOTIF_PTU = "entrants.notif.ptu";

    /**
     * Free education form
     */
    private final static String FREE_EDUCATION = "free";

    /**
     * Paid education form
     */
    private final static String PAID_EDUCATION = "paid";

    /**
     * Execute AddToAdmission command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AdmissionDAO admissionDAO = new AdmissionDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        UserDAO userDAO = new UserDAO();
        List<Integer> priorities;
        HttpSession session = request.getSession();

        if(request.getParameter(ATTRIBUTE_ID)!= null) {
            int entrantId = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));

            try {
                priorities = departmentDAO.getAllPrioritiesForEntrant(entrantId);

                if (priorities.contains(1) && checkDepartment(1, entrantId, departmentDAO, admissionDAO, userDAO)) {
                    session.removeAttribute(NO_UNIVER_ATTRIBUTE);
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                } else if (priorities.contains(2) && checkDepartment(2, entrantId, departmentDAO, admissionDAO, userDAO)) {
                    session.removeAttribute(NO_UNIVER_ATTRIBUTE);
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                } else if (priorities.contains(3) && checkDepartment(3, entrantId, departmentDAO, admissionDAO, userDAO)) {
                    session.removeAttribute(NO_UNIVER_ATTRIBUTE);
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                } else if (priorities.contains(4) && checkDepartment(4, entrantId, departmentDAO, admissionDAO, userDAO)) {
                    session.removeAttribute(NO_UNIVER_ATTRIBUTE);
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                } else if (priorities.contains(5) && checkDepartment(5, entrantId, departmentDAO, admissionDAO, userDAO)) {
                    session.removeAttribute(NO_UNIVER_ATTRIBUTE);
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                } else {
                    session.setAttribute(NO_UNIVER_ATTRIBUTE, MessageManager.INSTANCE.getMessage(KEY_NOTIF_PTU));
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                }

            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
    }

    /**
     * Method for adding entrants to free education form
     * @param priority entrant's priority for current department
     * @param admissionDAO dao for adding to the admission
     * @param userDAO dao for changing user role
     * @param departmentDAO dao for updating free place quantity
     * @param entrantId entrant id
     * @param department department object
     */
    private void addToFree(int priority,AdmissionDAO admissionDAO,UserDAO userDAO,DepartmentDAO departmentDAO,int entrantId,Department department) throws CommandException {
        try {
            admissionDAO.addToFinalAdmission(entrantId,priority,FREE_EDUCATION);
            userDAO.setAdmissionRole(entrantId);
            departmentDAO.updateFreePlaces(department.getFreePlaces() - 1,department.getAllPlaces() - 1, department.getId());
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
    }

    /**
     * Method for adding entrants to paid education form
     * @param priority entrant's priority for current department
     * @param userDAO dao for changing user role
     * @param admissionDAO dao for adding to the admission
     * @param departmentDAO dao for updating place quantity
     * @param entrantId entrant id
     * @param department department object
     */
    private void addToPaid(int priority,UserDAO userDAO,AdmissionDAO admissionDAO,DepartmentDAO departmentDAO,int entrantId,Department department) throws CommandException {
        try {
            admissionDAO.addToFinalAdmission(entrantId,priority,PAID_EDUCATION);
            userDAO.setAdmissionRole(entrantId);
            departmentDAO.updateAllPlaces(department.getAllPlaces() - 1,department.getId());
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
    }

    /**
     * Method for checking whether department has free places or paid places
     * adn adding entrant to free or paid education form
     * @param priority entrant's priority for current department
     * @param entrantId entrant id
     * @param departmentDAO dao for updating free or all place quantity
     * @param admissionDAO dao for adding to the admission
     * @param userDAO dao for changing user role
     * @return boolean value
     * @throws DAOTechnicalException if something goes wrong
     * @throws DAOLogicalException if something goes wrong
     */
    private boolean checkDepartment(int priority,int entrantId,DepartmentDAO departmentDAO,AdmissionDAO admissionDAO,UserDAO userDAO) throws DAOTechnicalException, DAOLogicalException, CommandException {
        Department department = departmentDAO.getDepartmentWithPriorityForEntrant(priority,entrantId);

        if(department.getFreePlaces()>0){
            addToFree(priority,admissionDAO,userDAO,departmentDAO,entrantId,department);

            return true;
        }else if(department.getAllPlaces()>0){
            addToPaid(priority,userDAO,admissionDAO,departmentDAO,entrantId,department);
            return true;
        }else {
            return false;
        }
    }
}
