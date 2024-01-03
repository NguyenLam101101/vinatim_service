package com.example.recruitment2.Config;

import com.example.recruitment2.Service.JWTProvider;
import com.example.recruitment2.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    JWTProvider jwtProvider;
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException{
        if(! request.getRequestURI().startsWith("/public")){
            String token = request.getHeader("Authorization");
            String userId;
            try {
                userId = jwtProvider.getUserIdFromToken(token);
                UserDetails userDetails = userService.loadUserByUsername(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, userDetails.getPassword());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        chain.doFilter(request, response);
    }
}
