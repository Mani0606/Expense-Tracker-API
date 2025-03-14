package com.personal.project.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.personal.project.service.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService service;

    @GetMapping("/balance")
    public int total_balance() {
        return service.balance();
    }

}
