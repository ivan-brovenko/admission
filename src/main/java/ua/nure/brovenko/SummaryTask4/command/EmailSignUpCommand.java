package ua.nure.brovenko.SummaryTask4.command;

import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for showing to an entrant page with
 * information that he should check his email
 * @author Ivan Brovenko
 */
public class EmailSignUpCommand implements ActionCommand {

    /**
     * Path to email confirm page
     */
    private final static String PATH_TO_EMAIL_CONFIRM_PAGE = "path.page.email.confirm";

    /**
     * Execute EmailSignUp command
     * @param request request to read the command
     * @param response response to the command
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return PathManager.INSTANCE.getString(PATH_TO_EMAIL_CONFIRM_PAGE);
    }

    /**
     * Method for checking whether user has necessary rights
     * to execute a command
     * For this command always returns true
     * @param user user object who's rights should be checked
     * @return boolean value
     */
    @Override
    public boolean checkAccess(User user) {
        return true;
    }
}
