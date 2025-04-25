package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.boundedContext.base.rq.Rq;
import com.sbs.tutorial1.boundedContext.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/member")
@AllArgsConstructor //필드 주입, 생성자 주입 안해도 private final 하면 알아서 컨테이너에 빈 등록
public class MemberController {

  private final MemberService memberService;
  private final Rq rq;

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

  @GetMapping("/login")
  @ResponseBody
  public String showlogin(){
    if(rq.isLogined()){
      return """
          <h1>이미 로그인 되어 있습니다.</h1>
          """;
    }
    return """
        <h1> 로그인 </h1>
        <form method="POST" action="login">
            <div><input type="text" name="username" placeholder="username 을 입력하세요"/></div>
            <div><input type="password" name="password" placeholder="password 를 입력하세요"></div>
            <button type="submit">로그인</button>
        </form>
        """; //action="login" 하면 login 메서드가 실행된다.
  }

  @PostMapping("/login")
  @ResponseBody
  public RsData login(String username, String password){

    if(username == null){
      return RsData.of("F-1", "로그인 아이디를 입력해주세요.");
    }

    if(password == null){
      return RsData.of("F-2", "로그인 비밀번호를 입력해주세요.");
    }

    RsData rsData = memberService.tryLogin(username, password);

    if(rsData.isSuccess()){
      Member member = (Member) rsData.getData();
//      rq.setCookie("loginedMemberId", member.getId());
      rq.setSession("loginedMemberId", member.getId());
    }
    return rsData;
  }

  @GetMapping("/logout")
  @ResponseBody
  public RsData logout(){

//    boolean cookieRemoved = rq.removedCookie("loginedMemberId");
//    boolean sessionRemoved = rq.removedSession("loginedMemberId");
//
//    if(!sessionRemoved){
//      return RsData.of("F-1", "이미 로그아웃 상태입니다.");
//    }
//
//    return RsData.of("S-1", "로그아웃 되었습니다.");


    if(rq.isLogout()){
      return RsData.of("F-1", "이미 로그아웃 상태입니다.");
    }

    rq.removedSession("loginedMemberId");

    return RsData.of("S-1", "로그아웃 되었습니다.");
  }

  @GetMapping("/me")
  @ResponseBody
  public RsData showMe(){

//    long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0);
    long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);

    boolean isLogined = loginedMemberId > 0;

    if(!isLogined){
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }


    Member member = memberService.findById(loginedMemberId);
    return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(member.getUsername()));
  }

  @GetMapping("/session-info")
  @ResponseBody
  public Map<String, Object> showSessionDebugInfo() {
    return rq.getSessionDebugInfo();
  }

}
