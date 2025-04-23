package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.boundedContext.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/member")
public class MemberController {

  // 필드 주입
  /*
  @Autowired
  private MemberService memberService;
  */

  private final MemberService memberService;

  // 생성자 주입
  public MemberController(MemberService memberService){
    this.memberService = memberService;
  }

  @GetMapping("/login")
  @ResponseBody
  public RsData login(String username, String password){

    if(username == null){
      return RsData.of("F-1", "로그인 아이디를 입력해주세요.");
    }

    if(password == null){
      return RsData.of("F-2", "로그인 비밀번호를 입력해주세요.");
    }

    return memberService.tryLogin(username, password);
  }
  
}
