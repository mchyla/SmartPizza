package com.my.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Marcin on 24.11.2016.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "redirect:/index.html";
    }
}
