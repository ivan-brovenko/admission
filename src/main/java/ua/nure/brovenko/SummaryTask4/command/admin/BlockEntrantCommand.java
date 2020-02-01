package ua.nure.brovenko.SummaryTask4.command.admin;


import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.EmailManager;
import ua.nure.brovenko.SummaryTask4.resources.MailSender;
import ua.nure.brovenko.SummaryTask4.resources.MessageManager;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for blocking entrants
 * @author Ivan Brovenko
 */
public class BlockEntrantCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

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
     * Subject
     */
    private final String SUBJECT = MessageManager.INSTANCE.getMessage("mail.subject");

    /**
     * Message
     */
    private final static String MESSAGE = MessageManager.INSTANCE.getMessage("mail.block.entrant");

    /**
     * Id attribute
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Attribute for notification
     */
    private final static String ATTRIBUTE_NOTIF_NO_INTERNET = "noInternet";

    /**
     * Path redirect to entrants page
     */
    private final static String PATH_REDIRECT_TO_ENTRANTS = "path.page.redirect.entrantr.management";

    /**
     * Key for no internet notification
     */
    private final static String KEY_NOTIF_NO_INTERNET = "error.auth.no.internet";

    /**
     * Execute BlockEntrant command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getParameter(ATTRIBUTE_ID) != null) {
            int entrantId = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));
            UserDAO userDAO = new UserDAO();
            MailSender mailSender = new MailSender();
            User user = null;
            HttpSession session = request.getSession();

            try {
                user = userDAO.findUserById(entrantId);

                try {
                    mailSender.sendEmail(user.getEmail(), FROM_EMAIL, USERNAME, PASSWORD, SUBJECT, MESSAGE);
                } catch (MessagingException e) {
                    logger.error(e.getMessage(), e);
                    session.setAttribute(ATTRIBUTE_NOTIF_NO_INTERNET, MessageManager.INSTANCE.getMessage(KEY_NOTIF_NO_INTERNET));
                    return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
                }
                userDAO.blockEntrant(entrantId);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(), e);
                throw new CommandException(e.getMessage(),e);
            }
            session.removeAttribute(ATTRIBUTE_NOTIF_NO_INTERNET);
        }

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANTS);
    }
}
