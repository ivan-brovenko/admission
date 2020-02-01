package ua.nure.brovenko.SummaryTask4.command.admin;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * command for unblocking entrants
 * @author Ivan Brovenko
 */
public class UnblockEntrantCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Id attribute
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Path to blacklist
     */
    private final static String PATH_TO_BLACK_LIST = "path.page.admin.blacklist";

    /**
     * Execute UnblockEntrant command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserDAO userDAO = new UserDAO();

        if(request.getParameter(ATTRIBUTE_ID) != null){
            int id = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));

            try {
                userDAO.unblockUserById(id);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_TO_BLACK_LIST);
    }
}
