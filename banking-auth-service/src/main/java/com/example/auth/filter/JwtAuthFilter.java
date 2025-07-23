
 package com.example.auth.filter;

import com.example.auth.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

                @Component
                public class JwtAuthFilter extends OncePerRequestFilter {

                    @Autowired
                    private UserDetailsService userDetailsService;

                    @Autowired
                    private JwtUtil jwtUtil;

                    @Override
                    protected boolean shouldNotFilter(HttpServletRequest request) {
                        String path = request.getServletPath();
                        // Allow login and register endpoints without filtering
                        return path.equals("/auth/login") || path.equals("/auth/register");
                    }

                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                            throws ServletException, IOException {

                        final String authHeader = request.getHeader("Authorization");
                        String jwt = null;
                        String username = null;

                        try {
                            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                                jwt = authHeader.substring(7);
                                username = jwtUtil.extractUsername(jwt);
                            }

                            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                                if (jwtUtil.validateToken(jwt, userDetails)) {
                                    UsernamePasswordAuthenticationToken authToken =
                                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                    SecurityContextHolder.getContext().setAuthentication(authToken);
                                } else {
                                    // Token is invalid
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                                    return;
                                }
                            }

                            // Proceed with the filter chain
                            filterChain.doFilter(request, response);

                        } catch (Exception ex) {
                            // Any exception while processing JWT
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\": \"Invalid or expired token\"}");
                        }
                    }
                }

