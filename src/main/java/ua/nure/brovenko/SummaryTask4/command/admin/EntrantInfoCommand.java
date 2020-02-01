package ua.nure.brovenko.SummaryTask4.command.admin;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.AtestatDAO;
import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.entity.Atestat;
import ua.nure.brovenko.SummaryTask4.entity.Entrant;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for showing information about current entrant
 * @author Ivan Brovenko
 */
public class EntrantInfoCommand extends AdminCommand{

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Attribute id
     */
    private final static String ATTRIBUTE_ID = "id";

    /**
     * Entrant attribute
     */
    private final static String ATTRIBUTE_ENTRANT = "ent";

    /**
     * Certificate attribute
     */
    private final static String ATTRIBUTE_CERTIFICATE = "atestat";

    /**
     * Path to entrant info page
     */
    private final static String PATH_TO_ENTRANT_INFO_PAGE = "path.page.admin.entrants.info";

    /**
     * Execute EntrantInfo command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        if(request.getParameter(ATTRIBUTE_ID) != null){
            int id = Integer.parseInt(request.getParameter(ATTRIBUTE_ID));
            UserDAO userDAO = new UserDAO();
            AtestatDAO atestatDAO = new AtestatDAO();
            Atestat atestat = null;
            Entrant entrant = null;

            try {
                entrant = userDAO.findInfoForEntrantById(id);
                request.setAttribute(ATTRIBUTE_ENTRANT,entrant);
                atestat = atestatDAO.getAtestatByEntrantId(id);
                request.setAttribute(ATTRIBUTE_CERTIFICATE,atestat);
            } catch (DAOTechnicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            } catch (DAOLogicalException e) {
                logger.error(e.getMessage(),e);
                throw new CommandException(e.getMessage(),e);
            }
        }

        return PathManager.INSTANCE.getString(PATH_TO_ENTRANT_INFO_PAGE);
    }
}
