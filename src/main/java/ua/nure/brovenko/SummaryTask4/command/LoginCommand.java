package ua.nure.brovenko.SummaryTask4.command;

import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.AuthenticationLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.AuthenticationTechnicalException;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.*;
import ua.nure.brovenko.SummaryTask4.resources.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command that allows user to log in
 * @author Ivan Brovenko
 */
public class LoginCommand implements ActionCommand {

    /**
     * Name for login field
     */
    private static final String PARAM_NAME_LOGIN = "login";

    /**
     * Name for password field
     */
    private static final String PARAM_NAME_PASSWORD = "mypassword";

    /**
     * Admin role
     */
    private static final String ADMIN_ROLE = "admin";

    /**
     * Entrant role
     */
    private static final String ENTRANT_ROLE = "entrant";

    /**
     * Entrant that has already submitted an application role
     */
    private static final String REG_ENTRANT_ROLE = "reg_entrant";

    /**
     * Entrant that got to admission role
     */
    private static final String ADMIS_ENTRANT_ROLE = "admis";

    /**
     * Entrant that were blocked role
     */
    private static final String BLOCKED_USER = "blocked";

    /**
     * Logger
     */
    private static final Logger logger = Logger.getRootLogger();

    /**
     * Path to login page
     */
    private static final String PATH_TO_LOGIN_PAGE = "path.page.login";

    /**
     * Path to main page
     */
    private static final String PATH_TO_MAIN_PAGE = "path.page.main";

    /**
     * Blocked login notification attribute
     */
    private static final String ATTRIBUTE_BLOCKED_LOGIN_NOTIF = "blockedLoginMessage";

    /**
     * Login error message attribute
     */
    private static final String ATTRIBUTE_ERROR_LOGIN_MESSAGE = "errorLoginPassMessage";

    /**
     * Key for blocked login notification
     */
    private static final String KEY_BLOCKED_LOGIN = "error.auth.blocked";

    /**
     * Key for invalid password notification
     */
    private static final String KEY_INVALID_PASSWORD = "error.auth.invalid_login_pass";

    /**
     * Execute Login command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request,HttpServletResponse response) throws CommandException{
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);

        if(login != null && pass != null){

            try{
                User user = LoginLogic.checkLogin(login,pass);
                HttpSession session = request.getSession();
                session.setAttribute(ua.nure.brovenko.SummaryTask4.logic.LoginLogic.SESSION_VAR, user);
                request.setAttribute("user",login);

                if(user.getRole().equals(ADMIN_ROLE)){
                    logger.info("Authentication susses by login: "+login);
                    return PathManager.INSTANCE.getString(PATH_TO_MAIN_PAGE);
                }else if(user.getRole().equals(ADMIS_ENTRANT_ROLE) || user.getRole().equals(ENTRANT_ROLE) || user.getRole().equals(REG_ENTRANT_ROLE)){
                    logger.info("Authentication susses by login: "+login);
                    return PathManager.INSTANCE.getString(PATH_TO_MAIN_PAGE);
                }else if(user.getRole().equals(BLOCKED_USER)){
                    request.setAttribute(ATTRIBUTE_BLOCKED_LOGIN_NOTIF,MessageManager.INSTANCE.getMessage(KEY_BLOCKED_LOGIN));
                    return PathManager.INSTANCE.getString(PATH_TO_LOGIN_PAGE);
                }

            }catch (AuthenticationLogicalException e){
                request.setAttribute(ATTRIBUTE_ERROR_LOGIN_MESSAGE, MessageManager.INSTANCE.getMessage(KEY_INVALID_PASSWORD));
                logger.error("Authentication failed by login: "+login);
                return PathManager.INSTANCE.getString(PATH_TO_LOGIN_PAGE);
            } catch (AuthenticationTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            }

        }

        return PathManager.INSTANCE.getString(PATH_TO_LOGIN_PAGE);
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
