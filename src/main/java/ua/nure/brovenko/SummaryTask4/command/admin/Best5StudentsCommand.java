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
import java.util.List;


public class Best5StudentsCommand extends AdminCommand {
    private final Logger logger = Logger.getRootLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<Admission> admissions = null;
        AdmissionDAO admissionDAO = new AdmissionDAO();
        try {
            admissions = admissionDAO.findBest5Entrants();
            request.setAttribute("admisList",admissions);
        } catch (DAOTechnicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        } catch (DAOLogicalException e) {
            logger.error(e.getMessage(),e);
            throw new CommandException(e.getMessage(),e);
        }
        return PathManager.INSTANCE.getString("path.page.best.5");
    }
}
