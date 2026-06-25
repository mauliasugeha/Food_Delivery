//package com.example.order_management.controller;
//
//import com.example.order_management.dto.LoginRequest;
//import com.example.order_management.dto.LoginResponse;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import jakarta.validation.Valid;
//import com.example.order_management.security.JwtUtil;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//
//        if (request.getEmail() == null || request.getPassword() == null) {
//            return ResponseEntity.badRequest().body(Map.of(
//                "email", "Email wajib diisi",
//                "password", "Password wajib diisi"
//            ));
//        }
//
//        if (!request.getEmail().equals("admin@gmail.com") || !request.getPassword().equals("admin123")) {
//            return ResponseEntity.status(401).body("Unauthorized");
//        }
//
//        String token = JwtUtil.generateToken(request.getEmail());
//
//        return ResponseEntity.ok(Map.of("token", token));
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout() {
//        return ResponseEntity.ok("Logout berhasil");
//    }
//}