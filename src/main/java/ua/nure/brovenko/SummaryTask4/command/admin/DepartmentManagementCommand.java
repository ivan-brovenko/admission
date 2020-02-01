package ua.nure.brovenko.SummaryTask4.command.admin;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.entity.Department;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Command for showing all the departments adn sorting them
 * by name or by free places or by all places quantity
 * @author Ivan Brovenko
 */
public class DepartmentManagementCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Sorting attribute
     */
    private final static String ATTRIBUTE_SORTING = "s";

    /**
     * Departments attribute
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
     * Parameter for sorting by number descend
     */
    private final static String PARAMETER_NUMBER_DESC = "numbDesc";

    /**
     * Path to departments page
     */
    private final static String PATH_TO_DEPARTMENTS = "path.page.admin.manager.department";

    /**
     * Execute DepartmentManagement command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Department> departments = null;

        try {
            departments = departmentDAO.findAllDepartments();

            if(request.getParameter(ATTRIBUTE_SORTING) == null){
                request.setAttribute(ATTRIBUTE_DEPARTMENTS,departments);
            } else if(request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_NAME)){
                    Collections.sort(departments, new Comparator<Department>() {
                        @Override
                        public int compare(Department o1, Department o2) {
                            if(o1.getName().compareTo(o2.getName())>0){
                                return 1;
                            }else if(o1.getName().compareTo(o2.getName())<0){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    request.setAttribute(ATTRIBUTE_DEPARTMENTS,departments);
                }else if(request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_ALL)){
                    Collections.sort(departments, new Comparator<Department>() {
                        @Override
                        public int compare(Department o1, Department o2) {
                            if(o1.getAllPlaces()>o2.getAllPlaces()){
                                return 1;
                            }else if(o1.getAllPlaces()<o2.getAllPlaces()){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    request.setAttribute(ATTRIBUTE_DEPARTMENTS,departments);
                }else if(request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_FREE)){
                    Collections.sort(departments, new Comparator<Department>() {
                        @Override
                        public int compare(Department o1, Department o2) {
                            if(o1.getFreePlaces()>o2.getFreePlaces()){
                                return 1;
                            }else if(o1.getFreePlaces()<o2.getFreePlaces()){
                                return -1;
                            }else {
                                return 0;
                            }
                        }
                    });
                    request.setAttribute(ATTRIBUTE_DEPARTMENTS,departments);
                } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_NAME + "Desc")) {
                Collections.sort(departments, (a,b)->{
                    if(a.getName().compareTo(b.getName())>0){
                        return -1;
                    } else if(a.getName().compareTo(b.getName())<0){
                        return 1;
                    } else {
                        return 0;
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, departments);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_ALL + "Desc")) {
                Collections.sort(departments, (o1,o2)->{
                    if (o1.getAllPlaces() > o2.getAllPlaces()) {
                        return -1;
                    } else if (o1.getAllPlaces() < o2.getAllPlaces()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, departments);
            } else if (request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_FREE + "Desc")) {
                Collections.sort(departments, (o1,o2)->{
                    if (o1.getFreePlaces() > o2.getFreePlaces()) {
                        return -1;
                    } else if (o1.getFreePlaces() < o2.getFreePlaces()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, departments);
            } else if(request.getParameter(ATTRIBUTE_SORTING).equals(PARAMETER_NUMBER_DESC)){
                   Collections.sort(departments,(o1,o2)->{
                       if(o1.getId()>o2.getId()){
                           return -1;
                       } else if(o1.getId()<o2.getId()){
                           return 1;
                       } else {
                           return 0;
                       }
                   });
                request.setAttribute(ATTRIBUTE_DEPARTMENTS, departments);
            }
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }

        return PathManager.INSTANCE.getString(PATH_TO_DEPARTMENTS);
    }
}
