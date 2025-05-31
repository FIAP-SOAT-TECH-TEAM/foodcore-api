package com.soat.fiap.food.core.api.config;

import com.soat.fiap.food.core.api.shared.exception.JwtException;
import com.soat.fiap.food.core.api.shared.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                jwtService.validateToken(token);
                String username = jwtService.extractUsername(token);
                List<GrantedAuthority> authorities = jwtService.extractRoles(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String json = String.format("""
                    {
                        "timestamp": "%s",
                        "status": 403,
                        "error": "Acesso negado",
                        "message": "%s",
                        "path": "%s"
                    }
                    """, LocalDateTime.now(), e.getMessage(), request.getRequestURI());

                response.getWriter().write(json);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
