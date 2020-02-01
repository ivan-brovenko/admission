package ua.nure.brovenko.SummaryTask4.command.admin;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.FinalAdmissionDAO;
import ua.nure.brovenko.SummaryTask4.entity.FinalAdmission;
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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Command for sending congratulation message
 * to students who got in
 * @author Ivan Brovenko
 */
public class SendEmailCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Subject for congratulation message
     */
    private final String SUBJECT = MessageManager.INSTANCE.getMessage("mail.subject");

    /**
     * Message
     */
    private final String MESSAGE = MessageManager.INSTANCE.getMessage("mail.message1");

    /**
     * Department where entrant got in
     */
    private final String DEPARTMENT = MessageManager.INSTANCE.getMessage("mail.message2");

    /**
     * Entrant's priority for the department he got in
     */
    private final String PRIORITY = MessageManager.INSTANCE.getMessage("mail.message3");

    /**
     * Education form for chosen department f means free, nf-means paid
     */
    private final String EDUCATION_FORM = MessageManager.INSTANCE.getMessage("mail.message4");

    /**
     * End of the the message
     */
    private final String END = MessageManager.INSTANCE.getMessage("mail.message5");

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
     * Path redirect to final admission page
     */
    private final static String PATH_REDIRECT_TO_FINAL_ADMISSION = "path.page.admin.redirect.final.admission";

    /**
     *
     * Attribute message notification
     */
    private final static String ATTRIBUTE_MESSAGE_NOTIF = "messagesSent";

    /**
     * Key for error notification
     */
    private final static String KEY_MESSAGE_ERROR = "notif.account.messages.error";

    /**
     * Key for successful sending notification
     */
    private final static String KEY_MESSAGE_SENT = "notif.account.messages.sent";

    /**
     * Execute SendEmail command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        FinalAdmissionDAO finalAdmissionDAO = new FinalAdmissionDAO();

        try {
            List<FinalAdmission> finalAdmissions = finalAdmissionDAO.findAllFinalAdmissions();
            MailSender mailSender = new MailSender();
            for(FinalAdmission f:finalAdmissions){
                mailSender.sendEmail(f.getEmail(),FROM_EMAIL,USERNAME,PASSWORD,SUBJECT,MESSAGE + f.getUneiveName() +
                        DEPARTMENT + f.getDepartmentName() + PRIORITY + f.getPriority() + EDUCATION_FORM + f.getPrice() + END);
            }
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (MessagingException e) {
            logger.error(e.getMessage(),e);
            session.setAttribute(ATTRIBUTE_MESSAGE_NOTIF,MessageManager.INSTANCE.getMessage(KEY_MESSAGE_ERROR));
            return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_FINAL_ADMISSION);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
        session.setAttribute(ATTRIBUTE_MESSAGE_NOTIF, MessageManager.INSTANCE.getMessage(KEY_MESSAGE_SENT));

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_FINAL_ADMISSION);
    }
}
