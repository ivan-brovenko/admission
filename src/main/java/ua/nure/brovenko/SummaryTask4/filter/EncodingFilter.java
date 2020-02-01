package ua.nure.brovenko.SummaryTask4.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Encoding filter
 * @author Ivan Brovenko
 */
@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {

    /**
     * Encoding constant
     */
    private final static String ENCODING = "UTF-8";

    /**
     * Destroy method
     */
    public void destroy() {}

    /**
     * Sets correct encoding
     * @param req request
     * @param resp response
     * @param chain filter chain
     * @throws ServletException if something goes wrong
     * @throws IOException if something goes wrong
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String encoding = req.getCharacterEncoding();
        if(ENCODING.equals(encoding)){
            req.setCharacterEncoding(ENCODING);
        }
        chain.doFilter(req,resp);

    }

    /**
     * Init method
     * @param config filter config
     * @throws ServletException if something goes wrong
     */
    public void init(FilterConfig config) throws ServletException {}

}
