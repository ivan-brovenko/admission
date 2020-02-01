package ua.nure.brovenko.SummaryTask4.command;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.encriptor.PasswordEncriptor;
import ua.nure.brovenko.SummaryTask4.entity.Entrant;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.EmailManager;
import ua.nure.brovenko.SummaryTask4.resources.MailSender;
import ua.nure.brovenko.SummaryTask4.resources.MessageManager;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command that allows new entrant to sign up
 * @author Ivan Brovenko
 */
public class SignUpCommand implements ActionCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Message subject
     */
    private final String SUBJECT = MessageManager.INSTANCE.getMessage("mail.subject");

    /**
     * Message
     */
    private final static String MESSAGE = MessageManager.INSTANCE.getMessage("mail.confirm.reg.message");

    /**
     * Link user should confirm registration by
     */
    private final static String PATH = EmailManager.INSTANCE.getString("path");

    /**
     * Admin email
     */
    private final static String FROM_EMAIL = EmailManager.INSTANCE.getString("from_email");

    /**
     * Admin email username
     */
    private final static String USERNAME = EmailManager.INSTANCE.getString("username");

    /**
     * Admin email password
     */
    private final static String PASSWORD = EmailManager.INSTANCE.getString("password");

    /**
     * First name attribute
     */
    private final static String ATTRIBUTE_FIRST_NAME = "firstName";

    /**
     * Last name attribute
     */
    private final static String ATTRIBUTE_LAST_NAME = "lastName";

    /**
     * Patronymic attribute
     */
    private final static String ATTRIBUTE_PATRONYMIC = "patronymic";

    /**
     * Email attribute
     */
    private final static String ATTRIBUTE_EMAIL = "email";

    /**
     * City attribute
     */
    private final static String ATTRIBUTE_CITY = "city";

    /**
     * Region attribute
     */
    private final static String ATTRIBUTE_REGION = "region";

    /**
     * School name attribute
     */
    private final static String ATTRIBUTE_SCHOOL_NAME = "schoolName";

    /**
     * Password attribute
     */
    private final static String ATTRIBUTE_PASSWORD = "password";

    /**
     * Entrant attribute
     */
    private final static String ATTRIBUTE_ENTRANT = "_entrant";

    /**
     * Path to registration page
     */
    private final static String PATH_TO_REGISTRATION_PAGE = "path.page.reg";

    /**
     * Path to error page
     */
    private final static String PATH_TO_BROWSER_ERROR_PAGE = "path.page.error.browser";

    /**
     * Path to success page
     */
    private final static String PATH_TO_SUCCESS_PAGE = "path.page.reg.success";

    /**
     * Path redirect to confirm email page
     */
    private final static String PATH_REDIRECT_TO_EMAIL_CONFIRM_PAGE = "path.page.redirect.email.confirm";

    /**
     * Duplicate login attribute
     */
    private final static String ATRIBUTE_DUPLICATE_LOGIN = "duplicateLogin";

    /**
     * Flag attribute
     */
    private final static String FLAG = "conf";

    /**
     * Key for duplicate login notification
     */
    private final static String KEY_DUPLICATI_LOGIN = "error.auth.dublicate.login";

    /**
     * Key for no internet notification
     */
    private final static String KEY_NO_INTERNET = "error.auth.no.internet";

    /**
     * Show login attribute
     */
    private final static String ATTRIBUTE_SHOW_LOGIN = "showlog";

    /**
     * Show password attribute
     */
    private final static String ATTRIBUTE_SHOW_PASSWORD = "showpass";

    /**
     * Path to error in case repeated link registration
     */
    private final static String PATH_TO_ERROR_500 = "path.error500";


    /**
     * Execute sign up command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if(request.getParameter(ATTRIBUTE_FIRST_NAME) != null && request.getParameter(ATTRIBUTE_LAST_NAME) != null &&
                request.getParameter(ATTRIBUTE_EMAIL) != null && request.getParameter(ATTRIBUTE_PASSWORD) != null
                && request.getParameter(ATTRIBUTE_PATRONYMIC) != null && request.getParameter(ATTRIBUTE_SCHOOL_NAME) != null
                && request.getParameter(ATTRIBUTE_CITY) != null && request.getParameter(ATTRIBUTE_REGION) != null &&
                !request.getParameter(ATTRIBUTE_FIRST_NAME).equals("") && !request.getParameter(ATTRIBUTE_LAST_NAME).equals("") &&
                !request.getParameter(ATTRIBUTE_EMAIL).equals("") && !request.getParameter(ATTRIBUTE_PASSWORD).equals("") &&
                !request.getParameter(ATTRIBUTE_PATRONYMIC).equals("") && !request.getParameter(ATTRIBUTE_SCHOOL_NAME).equals("") &&
                !request.getParameter(ATTRIBUTE_CITY).equals("") && !request.getParameter(ATTRIBUTE_REGION).equals("") &&
                request.getParameter(ATTRIBUTE_FIRST_NAME).length()<30 && request.getParameter(ATTRIBUTE_LAST_NAME).length()<30 &&
                request.getParameter(ATTRIBUTE_EMAIL).length()<30 && request.getParameter(ATTRIBUTE_PATRONYMIC).length()<30 &&
                request.getParameter(ATTRIBUTE_SCHOOL_NAME).length()<30 && request.getParameter(ATTRIBUTE_REGION).length()<30 &&
                request.getParameter(ATTRIBUTE_CITY).length()<30) {
            Entrant entrant = new Entrant();
            HttpSession session = request.getSession();
            MailSender mailSender = new MailSender();

            entrant.setFirstName(request.getParameter(ATTRIBUTE_FIRST_NAME));
            entrant.setLastName(request.getParameter(ATTRIBUTE_LAST_NAME));
            entrant.setPatronymic(request.getParameter(ATTRIBUTE_PATRONYMIC));
            entrant.setEmail(request.getParameter(ATTRIBUTE_EMAIL));
            entrant.setCity(request.getParameter(ATTRIBUTE_CITY));
            entrant.setRegion(request.getParameter(ATTRIBUTE_REGION));
            entrant.setSchool(request.getParameter(ATTRIBUTE_SCHOOL_NAME));
            entrant.setPassword(request.getParameter(ATTRIBUTE_PASSWORD));
            String email=request.getParameter(ATTRIBUTE_EMAIL);

            if(checkUserDuplicate(email)){
                request.setAttribute(ATRIBUTE_DUPLICATE_LOGIN, MessageManager.INSTANCE.getMessage(KEY_DUPLICATI_LOGIN));
                return PathManager.INSTANCE.getString(PATH_TO_REGISTRATION_PAGE);
            }

            session.setAttribute(ATTRIBUTE_ENTRANT,entrant);

            try {
                mailSender.sendEmail(email,FROM_EMAIL,USERNAME,PASSWORD,SUBJECT,MESSAGE + System.lineSeparator() + request.getRequestURL() + PATH);
            } catch (MessagingException e) {
                logger.error(e.getMessage(),e);
                request.setAttribute(ATRIBUTE_DUPLICATE_LOGIN,MessageManager.INSTANCE.getMessage(KEY_NO_INTERNET));
                return PathManager.INSTANCE.getString(PATH_TO_REGISTRATION_PAGE);
            }

            return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_EMAIL_CONFIRM_PAGE);

        }else if(request.getParameter(FLAG) != null && request.getParameter(FLAG).equals("true")){
                UserDAO userDAO = new UserDAO();
                HttpSession session = request.getSession();
                Entrant entrant= (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

                if(entrant != null) {
                    request.setAttribute(ATTRIBUTE_SHOW_LOGIN, entrant.getEmail());
                    request.setAttribute(ATTRIBUTE_SHOW_PASSWORD, entrant.getPassword());
                    String hesh = PasswordEncriptor.hashMD5(entrant.getPassword());
                    entrant.setPassword(hesh);

                    try {
                        userDAO.insertEntrant(entrant);
                    } catch (DAOTechnicalException e) {
                        logger.error(e.getMessage(),e);
                        throw new CommandException(e.getMessage(),e);
                    } catch (DAOLogicalException e) {
                        logger.error(e.getMessage(),e);
                        throw new CommandException(e.getMessage(),e);
                    }

                    return PathManager.INSTANCE.getString(PATH_TO_SUCCESS_PAGE);
                }

                return PathManager.INSTANCE.getString(PATH_TO_BROWSER_ERROR_PAGE);
        }

        return PathManager.INSTANCE.getString(PATH_TO_REGISTRATION_PAGE);
    }

    /**
     * Method that checks whether there's another user with this email
     * @param email email entrant want to sign up
     * @return boolean value
     */
    private boolean checkUserDuplicate(String email) throws CommandException {
        UserDAO userDAO = new UserDAO();

        try {
            List<User> users = userDAO.findAllUsers();

            for(User user:users){
                if(user.getEmail() != null && user.getPassword() != null && user.getEmail().equals(email)){
                    return true;
                }
            }
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }

        return false;
    }

    /**
     * Method for checking whether user has necessary rights
     * to execute a command
     * For this command always returns true
     * @param user user object who's rights should be checked
     * @return boolean value
     */
    @Override
    public boolean checkAccess(User user) {
        return true;
    }
}
