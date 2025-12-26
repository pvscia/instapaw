package com.example.instapaws.config;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.instapaws.model.User;
import com.example.instapaws.repository.UserRepository;
import com.example.instapaws.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
    	
    	String path = request.getServletPath();

        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateJwtToken(token)) {
            sendUnauthorized(response, "Invalid or expired token");
            return;
        }

        Long userId = jwtUtil.getIdFromJwtToken(token);
        User user = userRepository.findById(userId)
                .orElse(null);
        
        if (user == null) {
            sendUnauthorized(response, "User not found");
            return;
        }

        UsernamePasswordAuthenticationToken authentication =
        	    new UsernamePasswordAuthenticationToken(
        	        user,
        	        null,
        	        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        	    );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("""
            {
              "error": "Unauthorized",
              "message": "%s"
            }
        """.formatted(message));
    }
}
