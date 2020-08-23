package ru.devolek.testtask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/add/cupboard")
    public String addCupboard(){
        return "cupboard/add";
    }

    @GetMapping("/add/inventory")
    public String addInventory(){
        return "inventory/add";
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "/**/{path:[^.]*}")
    public String all() {
        return "forward:/";
    }
}
