package com.network.management.controller;

import com.network.management.vo.RegistryVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yusheng
 */
@RestController
public class LoginController {

    @PostMapping("/registry")
    public String registry(@RequestBody RegistryVo registryVo){
        registryVo.check();
        return "SUCCESS";
    }
}
