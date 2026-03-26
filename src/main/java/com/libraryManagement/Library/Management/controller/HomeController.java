package com.libraryManagement.Library.Management.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String greet(){
        return "Hello from Library";
    }

    public static class Book {
    }
}
