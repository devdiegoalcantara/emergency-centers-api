package br.com.emergency.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class DummyAuthFilter extends OncePerRequestFilter {
    private static final String API_KEY = "supersecret";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/swagger") && !request.getRequestURI().startsWith("/v3/api-docs")) {
            String token = request.getHeader("X-API-KEY");
            if (!API_KEY.equals(token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("{\"error\":\"Unauthorized access\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
