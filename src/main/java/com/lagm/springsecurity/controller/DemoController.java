package com.lagm.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public String showDemo() {
        return "Hello world from Demo!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello ADMIN";
    }

    @GetMapping("/dba")
    public String dba() {
        return "Hello DBA";
    }

    @PostMapping("/add")
    public String add() {
        return "Hello POST User";
    }

    @GetMapping("/add")
    public String add2() {
        return "Hello GET User";
    }
}
