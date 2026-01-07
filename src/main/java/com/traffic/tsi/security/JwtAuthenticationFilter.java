package com.traffic.tsi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; // <--- CRITICAL IMPORT
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // <--- THIS ANNOTATION IS REQUIRED!
// (If missing, Spring ignores the class and dependencies stay null)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final com.traffic.tsi.services.CustomUserDetailsService userDetailsService;

    // CONSTRUCTOR INJECTION
    // This forces Spring to fill in the 'jwtTokenProvider' when the app starts
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   com.traffic.tsi.services.CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Get Token
        String token = getTokenFromRequest(request);

        // 2. Validate Token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

            // 3. Get Username (Email) from Token
            String email = jwtTokenProvider.getUsernameFromJWT(token);

            // 4. Load User Details
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 5. Set Authentication in Context
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}