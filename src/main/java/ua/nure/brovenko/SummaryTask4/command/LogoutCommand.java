package ua.nure.brovenko.SummaryTask4.command;

import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command that allows user to log out
 */
public class LogoutCommand implements ActionCommand{

    /**
     * Path to main page
     */
    private final static String PATH_TO_MAIN_PAGE = "path.page.main";

    /**
     * Execute log out command
     * @param request request to read the command
     * @param response response to the command
     * @return path to main jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(LoginLogic.SESSION_VAR);

        if (user != null){
            LoginLogic.logout(request.getSession());
        }

        return PathManager.INSTANCE.getString(PATH_TO_MAIN_PAGE);
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
