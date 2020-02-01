package ua.nure.brovenko.SummaryTask4.command.entrant;


import ua.nure.brovenko.SummaryTask4.command.EntrantCommand;
import ua.nure.brovenko.SummaryTask4.exception.CommandException;
import ua.nure.brovenko.SummaryTask4.resources.AtestatAttributeConstants;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for saving final test (ZNO) results into the session
 * @author Ivan Brovenko
 */
public class SaveZnoCommand extends EntrantCommand{

    /**
     * Enum for getting certificate and zno attributes
     */
    private final AtestatAttributeConstants atConst = AtestatAttributeConstants.INSTANSE;

    /**
     * Path redirect to entrant account
     */
    private final static String PATH_REDIRECT_TO_ENTRANT_ACCOUNT = "path.page.client.redirect.account";

    /**
     * Execute SaveZno command
     * @param request request to read the command
     * @param response response to the command
     * @return path to jsp file
     * @throws CommandException if something goes wrong
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();

        setSessoinAtributes(request,session,atConst.ZNO_SUBJECT_1);
        setSessoinAtributes(request,session,atConst.ZNO_SUBJECT_2);
        setSessoinAtributes(request,session,atConst.ZNO_SUBJECT_3);
        setSessoinAtributes(request,session,atConst.ZNO_RESULT_1);
        setSessoinAtributes(request,session,atConst.ZNO_RESULT_2);
        setSessoinAtributes(request,session,atConst.ZNO_RESULT_3);

        return PathManager.INSTANCE.getString(PATH_REDIRECT_TO_ENTRANT_ACCOUNT);
    }

    /**
     * Method for faster resetting attributes from a request to a session
     * @param request request to get attribute from
     * @param session session for setting attributes
     * @param parameter parameter name
     */
    private void setSessoinAtributes(HttpServletRequest request,HttpSession session,String parameter){
        session.setAttribute(parameter,request.getParameter(parameter));
    }
}
