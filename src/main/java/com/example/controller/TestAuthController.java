package com.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("denyAll()")
@RequestMapping("/auth")
public @RestController class TestAuthController {

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/get")
    public String helloGet(){
        return "Hello World - GET";
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/post")
    public String helloPost(){
        return "Hello World - POST";
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/put")
    public String helloPut(){
        return "Hello World - PUT";
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/delete")
    public String helloDelete(){
        return "Hello World - DELETE";
    }

}
