package cz.fi.muni.pa165.restapi.config;

import cz.fi.muni.pa165.restapi.controllers.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/rest/hotels/*"})
public class AuthFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AdminAuthFilter.class);

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response401(response);
            return;
        }

        token = token.split(" ")[1];
        if (token == null) {
            response401(response);
            return;
        }

        JwtTokenUtils jwtTokenUtils = WebApplicationContextUtils.getWebApplicationContext(r.getServletContext())
                .getBean(JwtTokenUtils.class);

        try {
            if (!jwtTokenUtils.isTokenValid(token)) {
                response401(response);
                return;
            }
            if (!jwtTokenUtils.getRole(token).equals("USER") && !jwtTokenUtils.getRole(token).equals("ADMIN")) {
                response401(response);
                return;
            }
        } catch (IllegalArgumentException e) {
            response401(response);
            return;
        }

        chain.doFilter(request, response);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type email and password\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1></body></html>");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}