package com.posturstuff.config;

import com.posturstuff.service.JwtService;
import com.posturstuff.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

       String authHeader = request.getHeader("Authorization");
       String token = null;
       String username = null;

       if(authHeader != null && authHeader.startsWith("Bearer ")) {
           token = authHeader.substring(7);
           username = jwtService.extractUsername(token);
       }

       if(authHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);
           if(jwtService.validateToken(token, userDetails)) {
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }
       filterChain.doFilter(request, response);
    }

}
