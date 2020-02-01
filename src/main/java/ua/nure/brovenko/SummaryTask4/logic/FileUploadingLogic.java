package ua.nure.brovenko.SummaryTask4.logic;

import ua.nure.brovenko.SummaryTask4.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * File uploading login
 * @author Ivan Brovenko
 */
public class FileUploadingLogic {

    /**
     * Session var for certificate file
     */
    public static final String  SESSION_VAR_FILE = "_file";

    /**
     * Method that sets file uploading state
     * @param request request for reading the command
     * @return boolean type object
     */
    public static Boolean state(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object ob = session.getAttribute(SESSION_VAR_FILE);
        return (ob != null && ob.getClass().equals(Boolean.class)) ? (Boolean) ob : null;
    }
}
