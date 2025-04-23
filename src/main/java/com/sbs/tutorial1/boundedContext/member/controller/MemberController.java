package com.sbs.tutorial1.boundedContext.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

  @GetMapping("/login")
  @ResponseBody
  public Map<String, Object> login(String username, String password){
    Map<String, Object> rsData = new LinkedHashMap<>();
    rsData.put("resultCode", "S-1");
    rsData.put("msg", "%s님 환영합니다".formatted(username));

    return rsData;
  }
  
}
