package com.sbs.tutorial1.boundedContext.member.service;

import com.sbs.tutorial1.boundedContext.base.rsData.RsData;
import org.springframework.stereotype.Service;

@Service //스프링부트가 해당 클래스를 서비스로 인식
public class MemberService {
  public RsData tryLogin(String username, String password) {

    if(!username.equals("user1")){
      return RsData.of("F-3", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
    }

    if(!password.equals("1234")){
      return RsData.of("F-4", "비밀번호가 일치하지 않습니다.");
    }

    return RsData.of("S-1", "%s님 환영합니다".formatted(username));
  }
}

