package ua.nure.brovenko.SummaryTask4.command.admin;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for showing blocked users
 * @author Ivan Brovenko
 */
public class BlackListCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Blocked list attribute
     */
    private final static String ATTRIBUTE_BLOCKED_LIST = "blockList";

    /**
     * Path to black list
     */
    private final static String PATH_BLACKLIST = "path.page.admin.blacklist";

    /**
     * Execute BlackList command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserDAO userDAO = new UserDAO();

        try {
            List<User> blockedUsers = userDAO.findBlockedUsers();
            request.setAttribute(ATTRIBUTE_BLOCKED_LIST,blockedUsers);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }

        return PathManager.INSTANCE.getString(PATH_BLACKLIST);
    }
}
