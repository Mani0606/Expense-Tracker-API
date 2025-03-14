package com.personal.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.project.config.UserUtil;
import com.personal.project.model.Users;
import com.personal.project.service.Jwtservice;
import com.personal.project.service.UsersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/personal/users")
@CrossOrigin(origins = "*")
public class UsersController {

    @Autowired
    private UsersService user_service;

    @Autowired
    private Jwtservice jwt_service;
    private final AuthenticationManager am;

    UsersController(AuthenticationManager am) {
        this.am = am;
    }

    private String name = null;

    @PostMapping("/login")
    public String postMethodName(@RequestBody Users user, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
                user.getPassword());
        Authentication authentication = am.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            String tokens = jwt_service.tokengeneration(user.getEmail());
            Cookie cookie = new Cookie("jwt", tokens);
            cookie.setHttpOnly(true); // Prevent access from JavaScript
            cookie.setSecure(true); // Only send over HTTPS
            cookie.setPath("/"); // Accessible throughout the application
            cookie.setMaxAge(24 * 60 * 60); // 1 day expiration
            response.addCookie(cookie);
            return tokens;
        } else {
            return "Failed";
        }
    }

    @GetMapping("/hello")
    public String getMethodName() {

        this.name = UserUtil.getLoggedInUserEmail();

        if (this.name != null) {
            return name;
        }

        return "Hello";
    }

    @PostMapping("/signup")
    public Users postMethodName(@RequestBody Users user) {
        System.out.println("I am Here");
        return user_service.saveUser(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutmethod(HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

}
