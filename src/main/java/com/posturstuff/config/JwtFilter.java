package com.posturstuff.config;

import com.posturstuff.exception.user.UserNotFoundException;
import com.posturstuff.model.UserPrincipal;
import com.posturstuff.service.JwtService;
import com.posturstuff.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String path = request.getServletPath();
       List<String> excludedPaths = List.of("/error", "/posturstuff-api/user/register", "/posturstuff-api/user/login");

       if(excludedPaths.contains(path)) {
           filterChain.doFilter(request, response);
           return;
       }

       String token = extractTokenFromCookies(request);
       String id = null;

       try {
           if(token != null) {
               id = jwtService.extractId(token);
           }

           if(SecurityContextHolder.getContext().getAuthentication() == null) {
               UserPrincipal userDetails = (UserPrincipal) context.getBean(UserDetailsServiceImpl.class).loadById(id);
               if(jwtService.validateToken(token, userDetails)) {
                   UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(authToken);
               }
           }
           filterChain.doFilter(request, response);
           // handling these exceptions here since it won't reach Controller Advice
       } catch(ExpiredJwtException ex) {
            writeErrorResponse(response, "TOKEN_EXPIRED", ex.getMessage());
       } catch(SignatureException ex) {
           writeErrorResponse(response, "BAD_SIGNATURE", ex.getMessage());
       } catch(UserNotFoundException ex) {
           writeErrorResponse(response, "NOT_FOUND", ex.getMessage());
       }

    }

    private void writeErrorResponse(HttpServletResponse response, String error, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String jsonResponse = String.format("{\"error\": \"%s\", \"message\":\"%s\"}", error, message);
        response.getWriter().write(jsonResponse);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("jwt")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
