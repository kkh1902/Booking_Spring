package com.example.springCrud.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/")
    public String List() {
        return "board/list.html";
    }

    @GetMapping("/post")
    public String write() {
        return "board/list.html";
    }
}
