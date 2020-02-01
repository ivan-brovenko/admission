package ua.nure.brovenko.SummaryTask4.command.entrant;


import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.dao.ApplicationDAO;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.dao.UniverDAO;
import ua.nure.brovenko.SummaryTask4.entity.Application;
import ua.nure.brovenko.SummaryTask4.entity.Department;
import ua.nure.brovenko.SummaryTask4.entity.Univer;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Command for showing to entrant necessary information for student account
 * @author Ivan Brovenko
 */
public class AccountCommand extends EntrantCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Sorting attribute
     */
    private final static String ATTRIBUTE_SORTING = "s";

    /**
     * Department list attribute
     */
    private final static String ATTRIBUTE_DEPARTMENTS = "departments";

    /**
     * Parameter for sorting by name
     */
    private final static String PARAMETER_NAME = "name";

    /**
     * Parameter for sorting by all places
     */
    private final static String PARAMETER_ALL = "all";

    /**
     * Parameter for sorting by free places
     */
    private final static String PARAMETER_FREE = "free";

    /**
     * Marks list attribute
     */
    private final static String ATTRIBUTE_MARKS = "marks";

    /**
     * Admission role for entrant
     */
    private final static String ATTRIBUTE_ADMISSION_ROLE = "admis";

    /**
     * Path to client account
     */
    private final static String PATH_TO_CLIENT_ACCOUNT = "path.page.client.account";

    /**
     * List of univers
     */
    private final static String ATTRIBUTE_UNIVERS = "univers";

    /**
     * Final application attribute
     */
    private final static String ATTRIBUTE_APPLICATION = "application";

    /**
     * Execute Account command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LoginLogic.SESSION_VAR);
        List<Integer> marks = new ArrayList<>();
        setUnivers(request);
        sortedDepartments(request, response);
        
        for (int i = 100; i < 201; i++) {
            marks.add(i);
        }
        request.setAttribute(ATTRIBUTE_MARKS, marks);
        
        if(user.getRole().equals(ATTRIBUTE_ADMISSION_ROLE)){
            setApplication(request,session);
        }

        return PathManager.INSTANCE.getString(PATH_TO_CLIENT_ACCOUNT);
    }

    /**
     * Method for sorting departments using sql queries
     * only for students who are still not in the final admission
     * @param request request to read the command
     * @param response response to the command
     */
    private void sortedDepartments(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        try {
            List<Department> list = departmentDAO.findAllDepartments();

            if (request.getParameter(ATTRIBUTE_SORTING) == null) {
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_NAME)) {
                Collections.sort(list, new Comparator<Department>() {
                    @Override
                    public int compare(Department o1, Department o2) {
                        if(o1.getName().compareTo(o2.getName())>0){
                            return 1;
                        } else if(o1.getName().compareTo(o2.getName())<0){
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_ALL)) {
                Collections.sort(list, new Comparator<Department>() {
                    @Override
                    public int compare(Department o1, Department o2) {
                        if(o1.getAllPlaces()>o2.getAllPlaces()){
                            return 1;
                        } else if(o1.getAllPlaces()<o2.getAllPlaces()){
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_FREE)) {
                Collections.sort(list, new Comparator<Department>() {
                    @Override
                    public int compare(Department o1, Department o2) {
                        if(o1.getFreePlaces()>o2.getFreePlaces()){
                            return 1;
                        } else if(o1.getFreePlaces()<o2.getFreePlaces()){
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_NAME + "Desc")) {
                Collections.sort(list, (a,b)->{
                    if(a.getName().compareTo(b.getName())>0){
                        return -1;
                    } else if(a.getName().compareTo(b.getName())<0){
                        return 1;
                    } else {
                        return 0;
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_ALL + "Desc")) {
                Collections.sort(list, (o1,o2)->{
                        if (o1.getAllPlaces() > o2.getAllPlaces()) {
                            return -1;
                        } else if (o1.getAllPlaces() < o2.getAllPlaces()) {
                            return 1;
                        } else {
                            return 0;
                        }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_FREE + "Desc")) {
                Collections.sort(list, (o1,o2)->{
                        if (o1.getFreePlaces() > o2.getFreePlaces()) {
                            return -1;
                        } else if (o1.getFreePlaces() < o2.getFreePlaces()) {
                            return 1;
                        } else {
                            return 0;
                        }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, list);
            }
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
    }

    /**
     * Method for showing universities to an entrant
     * only for entrants who are still not in the final admission
     * @param request request to read the command
     */
    private void setUnivers(HttpServletRequest request) throws CommandException {
        UniverDAO univerDAO = new UniverDAO();
        
        try {
            List<Univer> univers = univerDAO.findAllUnivers();
            request.setAttribute(ATTRIBUTE_UNIVERS, univers);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(), e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
    }

    /**
     * Method for showing application only for entrants who got to the final admission
     * @param request request to read the command
     * @param session  session to get user from
     */
    private void setApplication(HttpServletRequest request,HttpSession session) throws CommandException {
        ApplicationDAO applicationDAO = new ApplicationDAO();
        Application application = null;
        User entrant = (User) session.getAttribute(LoginLogic.SESSION_VAR);

        try {
            application = applicationDAO.findFinalApplication(entrant.getId());
            String educationForm = applicationDAO.getEducationForm(entrant.getId());
            application.setPrice(educationForm);
            request.setAttribute(ATTRIBUTE_APPLICATION,application);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
    }
}


