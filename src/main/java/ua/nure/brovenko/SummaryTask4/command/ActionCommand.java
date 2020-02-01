package ua.nure.brovenko.SummaryTask4.command;


import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface to execute action commands and checking whether
 * current user has the rights to execute this command
 * @author Ivan Brovenko
 */
public interface ActionCommand {

    /**
     * Method for executing command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp page
     * @throws CommandException if something goes wrong
     */
    String execute(HttpServletRequest request,HttpServletResponse response) throws CommandException;

    /**
     * Method for checking user rights
     * @param user user object who's rights should be checked
     * @return boolean value
     */
    boolean checkAccess(User user);
}
