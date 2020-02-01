package ua.nure.brovenko.SummaryTask4.command.entrant;

import org.apache.commons.io.FileUtils;
import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.dao.AtestatDAO;
import ua.nure.brovenko.SummaryTask4.dao.DepartmentDAO;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import ua.nure.brovenko.SummaryTask4.resources.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Command for registration chosen department
 * and adding entrant's marks to database
 * @author Ivan Brovenko
 */
public class FinalRegistrantionCommand extends EntrantCommand {

    /**
     * Enum that contains subject's number for each zno subject
     */
    private final SubjectIdConstants id = SubjectIdConstants.INSTANCE;

    /**
     * Enum that contains attributes for subjects
     */
    private final AtestatAttributeConstants atConst = AtestatAttributeConstants.INSTANSE;

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Logger notification
     */
    private final static String DATA_ERASED_NOTIF = "data is erased";

    /**
     * Priority attribute
     */
    private final static String ATTRIBUTE_PRIORITY = "priority";

    /**
     * Role entrant
     */
    private final static String ROLE_ENTRANT = "entrant";

    /**
     * Wrong priority attribute
     */
    private final static String ATTRIBUTE_WRONG_PRIORITY = "wrongPriority";

    /**
     * Key for wrong priority notification
     */
    private final static String KEY_WRONG_PRIORITY = "error.account.duplicate.priority";

    /**
     * Path redirect to account
     */
    private final static String PATH_REDIRECT_ENTRANT_ACCOUNT = "path.page.client.redirect.account";

    /**
     * Path redirect to congrats page
     */
    private final static String PATH_REDIRECT_CONGRATS = "path.page.client.redirect.congrats";

    /**
     * Duplicate atestat attribute
     */
    private final static String DUPLICATE_ATESTAT = "duplicateAtestat";

    /**
     * Key for duplicate atestat
     */
    private final static String KEY_DUPLICATE_ATESTAT = "error.account.duplicate.atestat";

    /**
     * No file uploaded attribute
     */
    private final static String ATTRIBUTE_NO_FILE_UPLOADED = "noFileUploaded";

    /**
     * Key for no file uploaded
     */
    private final static String KEY_NO_FILE_UPLOADED = "error.account.no.file";

    /**
     * Uniber id attribute
     */
    private final static String ATTRIBUTE_UNIVER_ID = "univers";

    /**
     * Department id attribute
     */
    private final static String ATTRIBUTE_DEPARTMENT_ID = "department";

    /**
     * File name attribute
     */
    private final static String ATTRIBUTE_FILE_NAME = "fileName";

    /**
     * Key for upload location path
     */
    private final static String KEY_UPLOAD_LOCATION = "upload.location";

    /**
     * Key for archive location path
     */
    private final static String KEY_ARCHIVE_LOACATION = "archive.location";

    /**
     * Execute FinalRegistration command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException{
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LoginLogic.SESSION_VAR);
        UserDAO userDAO = new UserDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        User newUser = null;
        if(request.getParameter(ATTRIBUTE_PRIORITY) != null) {
            int priority = Integer.parseInt(request.getParameter(ATTRIBUTE_PRIORITY));


            try {
                if (user.getRole().equals(ROLE_ENTRANT)) {
                    int number = Integer.parseInt((String) session.getAttribute(atConst.NUMBER_ATT));
                    departmentDAO.setAtestatNumber(number, user.getId());
                    setAtestatMarks(departmentDAO, session, number);
                    departmentDAO.setAverageMark(number);
                    setZnoMarks(departmentDAO, session, user);
                    departmentDAO.setFinalMark(user.getId());
                    copyAtestatFile(session, user.getId());
                }

                if (checkPriority(user.getId(), priority, departmentDAO)) {
                    setDepartment(priority, departmentDAO, request, user);
                } else {
                    session.setAttribute(ATTRIBUTE_WRONG_PRIORITY, MessageManager.INSTANCE.getMessage(KEY_WRONG_PRIORITY));
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_ENTRANT_ACCOUNT);
                }

                userDAO.updateEntrantRole(user.getId());
                newUser = userDAO.findByLoginAndPassword(user.getEmail(), user.getPassword());
                session.setAttribute(LoginLogic.SESSION_VAR, newUser);

            } catch (DAOTechnicalException e) {

                try {
                    departmentDAO.eraseEntrantSubjectData(user.getId());
                    logger.info(DATA_ERASED_NOTIF);
                } catch (DAOLogicalException e1) {
                    logger.error(e1.getMessage(), e1);
                    throw new CommandException(e.getMessage(),e);
                } catch (DAOTechnicalException e1) {
                    logger.error(e.getMessage(), e);
                    throw new CommandException(e.getMessage(),e);
                }
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);

            } catch (DAOLogicalException e) {
                try {
                    departmentDAO.eraseEntrantSubjectData(user.getId());
                    logger.info(DATA_ERASED_NOTIF);
                } catch (DAOLogicalException e2) {
                    logger.error(e2.getMessage(), e2);
                    throw new CommandException(e.getMessage(),e);
                } catch (DAOTechnicalException e2) {
                    logger.error(e2.getMessage(), e2);
                    throw new CommandException(e.getMessage(),e);
                }
                logger.error(e.getMessage(), e);
                session.setAttribute(DUPLICATE_ATESTAT, MessageManager.INSTANCE.getMessage(KEY_DUPLICATE_ATESTAT));

                return PathManager.INSTANCE.getString(PATH_REDIRECT_ENTRANT_ACCOUNT);
            } catch (IOException e) {

                try {
                    departmentDAO.eraseEntrantSubjectData(user.getId());
                    logger.info(DATA_ERASED_NOTIF);
                } catch (DAOLogicalException e1) {
                    logger.error(e.getMessage(), e);
                    throw new CommandException(e.getMessage(),e);
                } catch (DAOTechnicalException e1) {
                    logger.error(e.getMessage(), e);
                    throw new CommandException(e.getMessage(),e);
                }

                logger.error(e.getMessage(), e);
                session.setAttribute(ATTRIBUTE_NO_FILE_UPLOADED, MessageManager.INSTANCE.getMessage(KEY_NO_FILE_UPLOADED));
                return PathManager.INSTANCE.getString(PATH_REDIRECT_ENTRANT_ACCOUNT);
            }
            session.removeAttribute(ATTRIBUTE_WRONG_PRIORITY);
            session.removeAttribute(DUPLICATE_ATESTAT);
        }
        return PathManager.INSTANCE.getString(PATH_REDIRECT_CONGRATS);
    }

    /**
     * Method for adding entrant's final certificate marks to database
     * @param departmentDAO dau for setting marks for certificate
     * @param session session for getting certificate marks from
     * @param number atestat id numver
     * @throws DAOLogicalException if something goes wrong with data
     * @throws DAOTechnicalException if something goes wrong with technical part
     */
    private void setAtestatMarks(DepartmentDAO departmentDAO, HttpSession session, int number) throws DAOLogicalException, DAOTechnicalException {
            int ukrMark = Integer.parseInt((String) session.getAttribute(atConst.UKR_MOVA));
            int ukrLisMark = Integer.parseInt((String) session.getAttribute(atConst.UKR_LIT));
            int rusMark = Integer.parseInt((String) session.getAttribute(atConst.RUSSIAN));
            int rusLitMark = Integer.parseInt((String) session.getAttribute(atConst.RUSSIAN_LIT));
            int englMark = Integer.parseInt((String) session.getAttribute(atConst.ENGLISH));
            int algMark = Integer.parseInt((String) session.getAttribute(atConst.ALGEBRA));
            int informMark = Integer.parseInt((String) session.getAttribute(atConst.INFORM));
            int geometryMark = Integer.parseInt((String) session.getAttribute(atConst.GEOMETRY));
            int histhoryMark = Integer.parseInt((String) session.getAttribute(atConst.HISTORY));
            int phizicsMark = Integer.parseInt((String) session.getAttribute(atConst.PHISICS));
            int sportMark = Integer.parseInt((String) session.getAttribute(atConst.SPORT));

            departmentDAO.setMarks(number, id.UKRAINIAN, ukrMark);
            departmentDAO.setMarks(number, id.UKR_LIT, ukrLisMark);
            departmentDAO.setMarks(number, id.RUSSIAN, rusMark);
            departmentDAO.setMarks(number, id.RUS_LIT, rusLitMark);
            departmentDAO.setMarks(number, id.ENGLISH, englMark);
            departmentDAO.setMarks(number, id.ALGEBRA, algMark);
            departmentDAO.setMarks(number, id.INFORM, informMark);
            departmentDAO.setMarks(number, id.GEOMETRY, geometryMark);
            departmentDAO.setMarks(number, id.HISTORY, histhoryMark);
            departmentDAO.setMarks(number, id.PHISICS, phizicsMark);
            departmentDAO.setMarks(number, id.FIZ_RA, sportMark);
    }

    /**
     * Method for getting zno marks from session and adding them to database
     * @param departmentDAO dao for adding zno marks to database
     * @param session session for getting zno marks
     * @param user entrant who's zno results we adding to database
     * @throws DAOLogicalException if something goes wrong with data
     * @throws DAOTechnicalException if something goes wrong with technical part
     */
    private void setZnoMarks(DepartmentDAO departmentDAO,HttpSession session,User user) throws DAOLogicalException, DAOTechnicalException {
        int subjectId1 = Integer.parseInt((String)session.getAttribute(atConst.ZNO_SUBJECT_1));
        int subjectId2 = Integer.parseInt((String)session.getAttribute(atConst.ZNO_SUBJECT_2));
        int subjectId3 = Integer.parseInt((String) session.getAttribute(atConst.ZNO_SUBJECT_3));
        int result1 = Integer.parseInt((String) session.getAttribute(atConst.ZNO_RESULT_1));
        int result2 = Integer.parseInt((String) session.getAttribute(atConst.ZNO_RESULT_2));
        int result3 = Integer.parseInt((String) session.getAttribute(atConst.ZNO_RESULT_3));

        departmentDAO.setZnoResultrForEntrant(user.getId(),subjectId1,result1);
        departmentDAO.setZnoResultrForEntrant(user.getId(),subjectId2,result2);
        departmentDAO.setZnoResultrForEntrant(user.getId(),subjectId3,result3);
    }

    /**
     * Method for binding entrant and department he chose
     * @param priority department's priority
     * @param departmentDAO dau for adding chosen department to database
     * @param request request to get attributes from
     * @param user entrant we should bind the department to
     * @throws DAOLogicalException if something goes wrong with data
     * @throws DAOTechnicalException if something gees wrong with technical part
     */
    private void setDepartment(int priority,DepartmentDAO departmentDAO,HttpServletRequest request,User user) throws DAOLogicalException, DAOTechnicalException {
        int univerId = Integer.parseInt(request.getParameter(ATTRIBUTE_UNIVER_ID));
        int departmentId = Integer.parseInt(request.getParameter(ATTRIBUTE_DEPARTMENT_ID));
        
        departmentDAO.setDepartmentForEntrant(departmentId,user.getId(),priority,univerId);
    }

    /**
     * Method for checking whether entrant already submitted for chosen priority
     * @param entrantId entrant id
     * @param priority department priority
     * @param departmentDAO dao for getting all the priorities
     * @return boolean value
     */
    private boolean checkPriority(int entrantId,int priority,DepartmentDAO departmentDAO) throws CommandException {
        List<Integer> priorities = null;
        try {
            priorities = departmentDAO.getAllPrioritiesForEntrant(entrantId);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage());
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
        
        if(priorities.contains(priority)){
            return false;
        }
        
        return true;
    }

    private void copyAtestatFile(HttpSession session,int userId) throws DAOLogicalException, DAOTechnicalException, IOException {
        File file = new File(PathManager.INSTANCE.getString(KEY_UPLOAD_LOCATION)+"\\"+(String)session.getAttribute(ATTRIBUTE_FILE_NAME));
        File archive = new File(PathManager.INSTANCE.getString(KEY_ARCHIVE_LOACATION)+"\\"+(String)session.getAttribute(ATTRIBUTE_FILE_NAME));

        FileUtils.copyFile(file,archive);
        AtestatDAO atestatDAO = new AtestatDAO();
        atestatDAO.insertScan((String)session.getAttribute(ATTRIBUTE_FILE_NAME),userId);
    }
}
