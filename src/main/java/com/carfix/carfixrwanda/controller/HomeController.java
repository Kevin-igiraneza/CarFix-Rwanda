package com.carfix.carfixrwanda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {



    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/mechanic-dashboard")
    public String mechanicDashboard() {
        return "mechanic-dashboard";
    }


}