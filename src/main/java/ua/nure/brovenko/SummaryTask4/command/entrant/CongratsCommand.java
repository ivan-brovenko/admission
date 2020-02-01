package ua.nure.brovenko.SummaryTask4.command.entrant;


import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for showing congratulations on successful submission an application
 * @author Ivan Brovenko
 */
public class CongratsCommand extends EntrantCommand {

    /**
     * Path to client congrats page
     */
    private final static String PATH_TO_CLIENT_CONGRATS_PAGE = "path.page.client.congrats";

    /**
     * Execute Congrats command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp file
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return PathManager.INSTANCE.getString(PATH_TO_CLIENT_CONGRATS_PAGE);
    }
}
