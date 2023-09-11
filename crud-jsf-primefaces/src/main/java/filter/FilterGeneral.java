package filter;

import connection.JPAUtil;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class FilterGeneral implements Filter {

    @Inject
    private JPAUtil jpaUtil;

    public void init(FilterConfig config) throws ServletException {
       jpaUtil.getEM();
    }

    public void destroy() {
        jpaUtil.getEM().close();
        jpaUtil.getFactory().close();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
    }
}
