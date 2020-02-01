package ua.nure.brovenko.SummaryTask4.command.admin;


import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.dao.AdmissionDAO;
import ua.nure.brovenko.SummaryTask4.entity.Admission;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.exception.DAOLogicalException;
import ua.nure.brovenko.SummaryTask4.exception.DAOTechnicalException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Command for showing entrants to admin
 * sorted by final marks
 * @author Ivan Brovenko
 */
public class EntrantManagementCommand extends AdminCommand {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Attribute admission
     */
    private final static String ATTRIBUTE_ADMISSIONS_LIST = "admiss";

    /**
     * Path to entrants page
     */
    private final static String PATH_TO_ENTRANTS_PAGE = "path.page.admin.entrants.managenent";

    /**
     * Execute EntrantManagement command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        AdmissionDAO admissionDAO = new AdmissionDAO();
        List<Admission> admissions = null;

        try {
            admissions = admissionDAO.findAllEntrants();
            Collections.sort(admissions, new Comparator<Admission>() {
                @Override
                public int compare(Admission o1, Admission o2) {
                    if(o1.getFinalMark()>o2.getFinalMark()){
                        return -1;
                    }else if(o1.getFinalMark()<o2.getFinalMark()){
                        return 1;
                    }else {
                        return 0;
                    }
                }
            });
            request.setAttribute(ATTRIBUTE_ADMISSIONS_LIST,admissions);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }

        return PathManager.INSTANCE.getString(PATH_TO_ENTRANTS_PAGE);
    }
}
