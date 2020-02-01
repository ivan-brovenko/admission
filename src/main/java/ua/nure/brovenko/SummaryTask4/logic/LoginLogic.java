package ua.nure.brovenko.SummaryTask4.logic;




import ua.nure.brovenko.SummaryTask4.dao.UserDAO;
import ua.nure.brovenko.SummaryTask4.encriptor.PasswordEncriptor;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Login logic
 * @author Ivan Brovenko
 */
public class LoginLogic {

    /**
     * Session var for user
     */
    public static final String SESSION_VAR = "_user";

    /**
     * Method for finding user by login and password
     * @param login login
     * @param password password
     * @return user object or null in case there's no matching login and password
     * @throws AuthenticationLogicalException if something goes wrong
     * @throws AuthenticationTechnicalException if something goes wrong
     */
    public static User checkLogin(String login,String password) throws AuthenticationLogicalException, AuthenticationTechnicalException {

        if(login != null && password != null){
            UserDAO dao = new UserDAO();

            try {
                String hesh = PasswordEncriptor.hashMD5(password);
                User user = dao.findByLoginAndPassword(login, hesh);
                return user;

            } catch (DAOLogicalException e) {
                throw new AuthenticationLogicalException(e);
            } catch (DAOTechnicalException e) {
                throw new AuthenticationTechnicalException(e);
            }
            
        }

        return null;
    }

    /**
     * Logout method
     * @param session current session
     */
    public static void logout(HttpSession session){
        session.invalidate();
    }

    /**
     * Method for getting user from the current session
     * @param request request to ger session from
     * @return user object or null
     */
    public static User user(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object ob = session.getAttribute(SESSION_VAR);
        return (ob != null && ob.getClass().equals(User.class)) ? (User) ob : null;
    }

}
