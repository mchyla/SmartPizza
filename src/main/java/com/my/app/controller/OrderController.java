package com.my.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Marcin on 30/12/2016.
 */
@Controller
public class OrderController {

    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping("/order")
    public String index(){
        return "BANAN";
    }
}
