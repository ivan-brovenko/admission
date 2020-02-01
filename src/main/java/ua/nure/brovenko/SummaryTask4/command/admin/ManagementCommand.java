package ua.nure.brovenko.SummaryTask4.command.admin;


import ua.nure.brovenko.SummaryTask4.command.AdminCommand;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for showing management page
 * @author Ivan Brovenko
 */
public class ManagementCommand extends AdminCommand {

    /**
     * Path to management
     */
    private final static String PATH_TO_MANAGEMENT = "path.page.admin.manager";

    /**
     * Execute Management command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return PathManager.INSTANCE.getString(PATH_TO_MANAGEMENT);
    }
}
