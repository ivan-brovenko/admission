package ua.nure.brovenko.SummaryTask4.command.admin;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.FinalAdmissionDAO;
import ua.nure.brovenko.SummaryTask4.entity.FinalAdmission;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for showing all final admissions
 * @author Ivan Brovenko
 */
public class FinalAdmissionCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Attribute admission list
     */
    private final static String ATTRIBUTE_ADMISSION_LIST = "adm";

    /**
     * Path to final admission page
     */
    private final static String PATH_TO_FINAL_ADMISSION = "path.page.admin.final.admission";

    /**
     * Execute FinalAdmission command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        FinalAdmissionDAO finalAdmissionDAO = new FinalAdmissionDAO();

        try {
            List<FinalAdmission> finalAdmissions = finalAdmissionDAO.findAllFinalAdmissions();
            request.setAttribute(ATTRIBUTE_ADMISSION_LIST,finalAdmissions);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }

        return PathManager.INSTANCE.getString(PATH_TO_FINAL_ADMISSION);
    }
}
