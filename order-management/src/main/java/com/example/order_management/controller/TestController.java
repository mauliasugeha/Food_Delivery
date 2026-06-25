package com.example.order_management.controller;
import com.example.order_management.dto.LoginRequest;
import com.example.order_management.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
//import com.example.order_management.security.JwtUtil;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Access granted";
    }
}