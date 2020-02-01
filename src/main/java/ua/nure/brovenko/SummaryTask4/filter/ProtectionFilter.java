package ua.nure.brovenko.SummaryTask4.filter;

import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.resources.PathManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Protection filter
 * @author Ivan Brovenko
 */
@WebFilter(filterName = "ProtectionFilter")
public class ProtectionFilter implements Filter {

    /**
     * Logger
     */
    private Logger logger=Logger.getRootLogger();

    /**
     * Destroy method
     */
    public void destroy() {}

    /**
     * Protects from direct jsp page viewing
     * @param req request
     * @param resp response
     * @param chain filter chain
     * @throws ServletException if something goes wrong
     * @throws IOException if something goes wrong
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        response.setStatus(404);
        logger.error("Someone tried to access jsp file directly");
        request.getRequestDispatcher(PathManager.INSTANCE.getString("path.error404")).forward(request,response);
    }

    /**
     * Init method
     * @param config filter config
     * @throws ServletException if something goes wrong
     */
    public void init(FilterConfig config) throws ServletException {}

}
