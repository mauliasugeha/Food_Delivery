//package com.example.order_management.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//
//public class JwtUtil {
//
//    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";
//    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
//
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 jam
//                .signWith(key)
//                .compact();
//    }
//
//    public static String extractUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}