package com.network.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yusheng
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "Hello,World";
    }
}
