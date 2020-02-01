package ua.nure.brovenko.SummaryTask4.command.entrant;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.NotImageException;
import ua.nure.brovenko.SummaryTask4.logic.FileUploadingLogic;
import ua.nure.brovenko.SummaryTask4.resources.FileUploader;
import ua.nure.brovenko.SummaryTask4.resources.MessageManager;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for uploading certificate scan
 * @author Ivan Brovenko
 */
public class FileUploadingCommand extends EntrantCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Path redirect to entrant account
     */
    private final static String PATH_REDIRECT_ACCOUNT = "path.page.client.redirect.account";

    /**
     * No file uploaded attribute
     */
    private final static String ATTRIBUTE_NO_FILE_UPLOADED = "noFileUploaded";

    /**
     * Key for no file uploaded
     */
    private final static String KEY_NO_FILE_UPLOADED = "error.account.no.file";

    /**
     * File name attribute
     */
    private final static String ATTRIBUTE_FILE_NAME = "fileName";

    /**
     * Execute File
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp file
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        FileUploader fileUploader = new FileUploader();
        String fileName = null;

        try {
            fileName = fileUploader.upload(request);
        } catch (NotImageException e) {
            logger.error(e.getMessage(),e);
            session.setAttribute(ATTRIBUTE_NO_FILE_UPLOADED, MessageManager.INSTANCE.getMessage(KEY_NO_FILE_UPLOADED));
            return PathManager.INSTANCE.getString(PATH_REDIRECT_ACCOUNT);
        }

        session.setAttribute(ATTRIBUTE_FILE_NAME,fileName);
        session.setAttribute(FileUploadingLogic.SESSION_VAR_FILE,true);
        session.removeAttribute(ATTRIBUTE_NO_FILE_UPLOADED);

        return PathManager.INSTANCE.getString(PATH_REDIRECT_ACCOUNT);
    }
}
