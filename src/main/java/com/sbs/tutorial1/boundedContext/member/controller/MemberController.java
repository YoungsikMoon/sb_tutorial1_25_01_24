package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.boundedContext.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/member")
@AllArgsConstructor //필드 주입, 생성자 주입 안해도 private final 하면 알아서 컨테이너에 빈 등록
public class MemberController {

  private final MemberService memberService;

  // 필드 주입
  /*
  @Autowired
  private MemberService memberService;
  */

  /*
  // 생성자 주입
  private final MemberService memberService;
  public MemberController(MemberService memberService){
    this.memberService = memberService;
  }
   */

  @GetMapping("/me")
  @ResponseBody
  public RsData showMe(HttpServletRequest req, HttpServletResponse resp){
    long loginedMemberId = 0;

    if(req.getCookies() != null){
      loginedMemberId = Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("loginedMemberId"))
          .map(Cookie::getValue)
          .mapToLong(Long::parseLong)
          .findFirst()
          .orElse(0);
    }
    boolean isLogined = loginedMemberId > 0;

    if(!isLogined){
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }

    Member member = memberService.findById(loginedMemberId);
    return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(member.getUsername()));
  }

  @GetMapping("/login")
  @ResponseBody
  public RsData login(String username, String password, HttpServletResponse resp){

    if(username == null){
      return RsData.of("F-1", "로그인 아이디를 입력해주세요.");
    }

    if(password == null){
      return RsData.of("F-2", "로그인 비밀번호를 입력해주세요.");
    }

    RsData rsData = memberService.tryLogin(username, password);

    if(rsData.isSuccess()){
      // 쿠키
      long memberId = (long) rsData.getData();
      resp.addCookie(new Cookie("loginedMemberId", memberId + ""));
    }

    return rsData;
  }

  @GetMapping("/logout")
  @ResponseBody
  public RsData logout(HttpServletRequest req, HttpServletResponse resp){
    if(req.getCookies() != null){
      Arrays.stream(req.getCookies())
            .filter(cookie -> cookie.getName().equals("loginedMemberId"))
            .forEach(cookie -> {
              cookie.setMaxAge(0); //쿠키만료
              resp.addCookie(cookie); //만료된 쿠키 추가
            });
    }
    return RsData.of("S-1", "로그아웃 되었습니다.");
  }
  
}
