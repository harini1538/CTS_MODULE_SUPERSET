package com.example.jwt.controller;

import com.example.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request) {
        // Get the Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }

        // Decode Base64 encoded username:password
        String base64Credentials = authHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodedBytes, StandardCharsets.UTF_8);

        // Split username and password
        final String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            return ResponseEntity.status(400).body("Invalid Basic authentication token");
        }

        String username = values[0];
        String password = values[1];

        // Hardcoded check (You can replace with DB or in-memory auth check)
        if ("user".equals(username) && "pwd".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
        } else {
            return ResponseEntity.status(403).body("Invalid credentials");
        }
    }
}
