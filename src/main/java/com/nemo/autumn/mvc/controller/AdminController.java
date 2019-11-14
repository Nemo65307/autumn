package com.nemo.autumn.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/users-list", method = RequestMethod.GET)
    public String usersList() {
        return "admin";
    }

}