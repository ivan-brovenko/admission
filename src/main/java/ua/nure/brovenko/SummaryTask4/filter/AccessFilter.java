package ua.nure.brovenko.SummaryTask4.filter;

import ua.nure.brovenko.SummaryTask4.command.ActionCommand;
import ua.nure.brovenko.SummaryTask4.command.ActionFactory;
import ua.nure.brovenko.SummaryTask4.entity.User;
import ua.nure.brovenko.SummaryTask4.logic.LoginLogic;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command access filter
 * @author Ivan Brovenko
 */
@WebFilter(filterName = "AccessFilter")
public class AccessFilter implements Filter {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Destroy method
     */
    public void destroy() {}

    /**
     * Checks the user role before executing command
     * If user doesn't have required role a 403 error
     * page will be displayed
     * @param req request
     * @param resp response
     * @param chain filter chain
     * @throws ServletException if something goes wrong
     * @throws IOException if something goes wrong
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response =(HttpServletResponse) resp;
        ActionFactory actionFactory = new ActionFactory();
        ActionCommand actionCommand = actionFactory.getCommand(request);
        User user = LoginLogic.user(request);

        if(actionCommand.checkAccess(user)){
            chain.doFilter(req, resp);
        }else{
            response.setStatus(403);
            logger.error(String.format("Access denied"));
            request.getRequestDispatcher(PathManager.INSTANCE.getString("path.error403")).forward(req, resp);
        }
    }

    /**
     * Init method
     * @param config filter config
     * @throws ServletException if something goes wrong
     */
    public void init(FilterConfig config) throws ServletException {}

}
