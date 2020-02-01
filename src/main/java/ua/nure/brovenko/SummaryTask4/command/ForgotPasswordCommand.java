package ua.nure.brovenko.SummaryTask4.command;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.encriptor.PasswordEncriptor;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Command for repairing password
 * @author Ivan Brovenko
 */
public class ForgotPasswordCommand implements ActionCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Admin email
     */
    private final static String FROM_EMAIL = EmailManager.INSTANCE.getString("from_email");

    /**
     * Admin email password
     */
    private final static String PASSWORD = EmailManager.INSTANCE.getString("password");

    /**
     * Admin email username
     */
    private final static String USERNAME = EmailManager.INSTANCE.getString("username");

    /**
     * Message subject
     */
    private final String SUBJECT = MessageManager.INSTANCE.getMessage("mail.subject");

    /**
     * Message
     */
    private final String MESSAGE = MessageManager.INSTANCE.getMessage("mail.forgot.pass");

    /**
     * Mail sender
     */
    private final MailSender mailSender = new MailSender();

    /**
     * Forgot password attribute
     */
    private final static String ATTRIBUTE_FORGOT_PASSWORD = "forgotLogin";

    /**
     * Error message attribute
     */
    private final static String ATTRIBUTE_ERROR_LOGIN = "errorLoginPassMessage";

    /**
     * Path to login page
     */
    private final static String PATH_TO_LOGIN_PAGE = "path.page.login";

    /**
     * Path redirect to login page
     */
    private final static String PATH_REDIRECT_TO_LOGIN = "path.page.redirect.login";

    /**
     * Key for message error notification
     */
    private final static String KEY_MESSAGE_ERROR = "notif.account.messages.error";

    /**
     * Key for login forgot notification
     */
    private final static String KEY_LOGIN_FORGOT_NOTIF = "login.forgot.notif";

    /**
     * Key for message sent notification
     */
    private final static String KEY_MESSAGE_SENT = "notif.account.messages.sent";

    /**
     * Execute Forgot password command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserDAO userDAO = new UserDAO();
        List<String> emails = null;
        RandomStringGenerator rsg = new RandomStringGenerator();
        HttpSession session = request.getSession();

        if(request.getParameter(ATTRIBUTE_FORGOT_PASSWORD)!= null){
            String userEmail = request.getParameter(ATTRIBUTE_FORGOT_PASSWORD);
            try {
                emails = userDAO.findEntrantsEmailsAnyRole();
                String newPassword = rsg.getSaltString();

                if(emails.contains(userEmail)){
                    try {
                        mailSender.sendEmail(userEmail,FROM_EMAIL,USERNAME,PASSWORD,SUBJECT,MESSAGE+newPassword);
                    } catch (MessagingException e) {
                        logger.error(e.getMessage(),e);
                        request.setAttribute(ATTRIBUTE_ERROR_LOGIN,MessageManager.INSTANCE.getMessage(KEY_MESSAGE_ERROR));
                        return PathManager.INSTANCE.getString(PATH_TO_LOGIN_PAGE);
                    }
                    String hesh=PasswordEncriptor.hashMD5(newPassword);
                    userDAO.updateEntrantPassword(hesh,userEmail);
                } else {
                    request.setAttribute(ATTRIBUTE_ERROR_LOGIN,MessageManager.INSTANCE.getMessage(KEY_LOGIN_FORGOT_NOTIF));
                    return PathManager.INSTANCE.getString(PATH_TO_LOGIN_PAGE);
                }
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            }
        }
        session.setAttribute(ATTRIBUTE_ERROR_LOGIN,MessageManager.INSTANCE.getMessage(KEY_MESSAGE_SENT));

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_LOGIN);
    }

    @Override
    public boolean checkAccess(User user) {
        return true;
    }
}
