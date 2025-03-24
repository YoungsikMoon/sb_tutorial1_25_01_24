package com.sbs.tutorial1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    int num;

    public HomeController(){
        num=-1;
    }

    @GetMapping("/home/main1")
    @ResponseBody
    public String showHome() {
        return "어서오세요.";
    }

    @GetMapping("/home/main2")
    @ResponseBody
    public String showHome2(){
        return "환영합니다.";
    }

    @GetMapping("/home/main3")
    @ResponseBody
    public String showHome3(){
        return "반갑습니다.";
    }


    @GetMapping("/home/increase")
    @ResponseBody
    public int showIncrease(){
        num++;
        return num;
    }


    @GetMapping("/home/plus")
    @ResponseBody
    public int showPlus(int a, @RequestParam(defaultValue = "0") int b){
        return a+b;
    }
}
