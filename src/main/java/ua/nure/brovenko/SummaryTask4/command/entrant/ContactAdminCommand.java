package ua.nure.brovenko.SummaryTask4.command.entrant;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import ua.nure.brovenko.SummaryTask4.resources.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for writing emails to admin
 * @author Ivan Brovenko
 */
public class ContactAdminCommand extends EntrantCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Admin email
     */
    private final static String FROM_EMAIL = EmailManager.INSTANCE.getString("from_email");

    /**
     * Attribute password
     */
    private final static String ATTRIBUTE_PASSWORD = "pass";

    /**
     * Attribute message
     */
    private final static String ATTRIBUTE_MESSAGE = "message";

    /**
     * Attribute message subject
     */
    private final static String ATTRIBUTE_SUBJECT = "subject";

    /**
     * Attribute for notification if message was sent
     */
    private final static String ATTRIBUTE_NOTIF_GOOD = "notifGood";

    /**
     * Attribute for notification if message wasn't sent
     */
    private final static String ATTRIBUTE_NOTIF_WRONG = "notifWrong";

    /**
     * Key for error message
     */
    private final static String KEY_NOTIF_WRONG = "notif.account.messages.error";

    /**
     * Key for congrats message
     */
    private final static String KEY_NOTIF_GOOD = "notif.account.messages.sent";

    /**
     * Path to client command
     */
    private final static String PATH_TO_CONTACT_ADMIN = "path.page.client.contact.admin";

    /**
     * Path redirect to client command
     */
    private final static String PATH_REDIRECT_TO_CONTACT_ADMIN = "path.page.client.contact.redirect";

    /**
     * Execute ContactAdmin command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        if(request.getParameter(ATTRIBUTE_PASSWORD) != null && !request.getParameter(ATTRIBUTE_PASSWORD).equals("") &&
                request.getParameter(ATTRIBUTE_MESSAGE) != null && !request.getParameter(ATTRIBUTE_MESSAGE).equals("") &&
                request.getParameter(ATTRIBUTE_SUBJECT) != null && !request.getParameter(ATTRIBUTE_SUBJECT).equals("")){
            HttpSession session = request.getSession();
            User entrant = (User) session.getAttribute(LoginLogic.SESSION_VAR);
            MailSender mailSender = new MailSender();
            String password = request.getParameter(ATTRIBUTE_PASSWORD);
            String subject = request.getParameter(ATTRIBUTE_SUBJECT);
            String message = request.getParameter(ATTRIBUTE_MESSAGE);

            try {
                mailSender.sendEmail(FROM_EMAIL,entrant.getEmail(),entrant.getEmail(),password,subject,message);
            } catch (MessagingException e) {
                logger.error(e.getMessage(),e);
                request.setAttribute(ATTRIBUTE_NOTIF_WRONG,MessageManager.INSTANCE.getMessage(KEY_NOTIF_WRONG));
                return PathManager.INSTANCE.getString(PATH_TO_CONTACT_ADMIN);

            }
                session.setAttribute(ATTRIBUTE_NOTIF_GOOD,MessageManager.INSTANCE.getMessage(KEY_NOTIF_GOOD));
            return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_CONTACT_ADMIN);
        }

        return PathManager.INSTANCE.getString(PATH_TO_CONTACT_ADMIN);
    }
}
