//package com.example.order_management.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collections;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//
//            try {
//                String username = JwtUtil.extractUsername(token);
//
//                UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(
//                        username,
//                        null,
//                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//                    );
//
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}