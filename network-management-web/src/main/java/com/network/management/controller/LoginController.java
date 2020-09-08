package com.network.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yusheng
 */
@RestController
public class LoginController {


    @PostMapping("/registry")
    public String registry(){
        return "SUCCESS";
    }
}
