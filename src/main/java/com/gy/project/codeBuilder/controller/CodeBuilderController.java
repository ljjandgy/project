package com.gy.project.codeBuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: GY
 * @Date: 2018/11/7 10:01
 */
@Controller
@RequestMapping("/code")
public class CodeBuilderController {

    @RequestMapping("/view")
    public String view(){
        return "/codeBuilder/codeBuilder.html";
    }

    @RequestMapping("/list")
    public Object getList(){
        return null;
    }
}
