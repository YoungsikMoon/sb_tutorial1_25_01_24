package com.sbs.tutorial1.boundedContext.member.service;

import com.sbs.tutorial1.boundedContext.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service //스프링부트가 해당 클래스를 서비스로 인식
// @Component // @Component가 붙은 클래스는 Ioc 컨테이너에 의해 생사소멸 관리된다.
// @Component 대신 @Service는 같은 의미
// 가독성 때문에 Service라고 표현
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public RsData tryLogin(String username, String password) {

    Member member = memberRepository.findByUsername(username).orElse(null);

    if(member == null){
      return RsData.of("F-3", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
    }

    if(!member.getPassword().equals(password)){
      return RsData.of("F-4", "비밀번호가 일치하지 않습니다.");
    }

    return RsData.of("S-1", "%s님 환영합니다".formatted(username), member);
  }

  public Member findById(long id) {
    return memberRepository.findById(id).orElse(null);
  }
}

