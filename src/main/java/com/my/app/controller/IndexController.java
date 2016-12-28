package com.my.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Marcin on 24.11.2016.
 */
@Controller
public class IndexController {

    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping("/")
    public String index(){
        return "redirect:/index.html";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/dupa")
    public String test(){
        return "test";
    }


}
