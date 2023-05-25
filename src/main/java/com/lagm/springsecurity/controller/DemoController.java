package com.lagm.springsecurity.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {
//    @GetMapping("/demo")
//    public String showDemo() {
//        return "Hello world from Demo!";
//    }
//
//    @GetMapping("/admin")
//    public String admin() {
//        return "Hello ADMIN";
//    }
//
//    @GetMapping("/dba")
//    public String dba() {
//        return "Hello DBA";
//    }
//
//    @PostMapping("/add")
//    public String add() {
//        return "Hello POST User";
//    }
//
//    @GetMapping("/add")
//    public String add2() {
//        return "Hello GET User";
//    }

    // preAuthorize
    @GetMapping("/preAuth1")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String preAuth1() {
        return "Hello world from preAuth1!";
    }

    @GetMapping("/preAuth2/{param}")
    @PreAuthorize("""
        #param == authentication.principal.username and hasAuthority('ROLE_ADMIN')
        """)
    public String preAuth2(@PathVariable("param") String param) {
        return  param + " - Hello World from PreAuth2!";
    }

    @GetMapping("/preAuth3/{param}")
    @PreAuthorize("@conditionEvaluator.canPreAuth3(#param, authentication)")
    public String preAuth3(@PathVariable("param") String param) {
        return  param + " - Hello World from PreAuth3!";
    }

    // postAuthorize
    @GetMapping("/postAuth1")
    @PostAuthorize("returnObject != 'postAuth1'")
    public String preAuth3() {
        System.out.println("**postAuht1**");
        return  "postAuth1";
    }

    // preFilter
    @GetMapping("/preFilter1")
    @PreFilter("filterObject.startsWith(authentication.principal.username)")
    public String preFilter1(@RequestBody List<String> values) {
        System.out.println("Values: " + values);
        return  "preFilter1";
    }

    // postFilter
    // The return type must be a collection, can't be an inmutable collection
    @GetMapping("/postFilter1")
    @PostFilter("filterObject.startsWith(authentication.principal.username)")
    public List<String> postFilter1() {
        // List<String> values = List.of("admin - 123", "admin - 45", "user - 123", "user - 456");
        List<String> values = new ArrayList<>();
        values.add("admin - 123");
        values.add("admin - 456");
        values.add("user - 123");
        values.add("user - 456");
        System.out.println("Values: " + values);
        return values;
    }
}
