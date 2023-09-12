package filter;

import connection.SingleHibernateEM;
import model.Usuario;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class FilterAuth implements Filter {

    @Inject
    private SingleHibernateEM singleHibernateEM;

    public void init(FilterConfig config) throws ServletException {
        singleHibernateEM.getEM();
    }

    public void destroy() {
        singleHibernateEM.getEM().close();
        singleHibernateEM.getFactory().close();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        try{

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            boolean isUserLogged = (((Usuario)req.getSession().getAttribute("usuarioLogado")) != null);
            boolean isLoginPage = (req.getRequestURI().endsWith("index.xhtml") || req.getRequestURI().endsWith(req.getContextPath() + "/"));
            boolean isRegisterPage = req.getRequestURI().endsWith("register.xhtml");
            boolean isServletFacesRequest = req.getRequestURI().endsWith("javax.faces.resource/jsf.js.xhtml");

            if (!isUserLogged && (!isLoginPage && !isRegisterPage && !isServletFacesRequest)){
                String urlEscolhida = req.getServletPath().substring(1); // Recebendo a URL escolhida sem "/" urlEscolhida.xhtml
                req.setAttribute("urlEscoliha",urlEscolhida);
                req.getRequestDispatcher("index.xhtml").forward(request,response);
                return;
            }else if (isUserLogged && (isLoginPage || isRegisterPage)) {
                resp.sendRedirect(req.getContextPath() + "/home.xhtml");
                return;
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        chain.doFilter(request, response);
        System.out.println("Filter Funcionando...");
    }
}
