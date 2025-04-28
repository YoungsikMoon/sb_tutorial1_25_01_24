package com.sbs.tutorial1.base.initData;

import com.sbs.tutorial1.boundedContext.article.service.ArticleService;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration // 컴포넌트와 비슷한 역할
@Profile({"dev", "test"})
// production(운영 환경, 라이브 서버)
// NotProdd : 개발환경, 테스트 환경
public class NotProd {
  @Bean
  CommandLineRunner initData(MemberService memberService, ArticleService articleService){
    return args -> {

      for (int i = 1; i <= 3; i++) {
        memberService.join("user" + i, "1234");
      }

      String[] usernames = {"love", "test", "giving", "dance", "spring", "summer", "winter"};
      String[] passwords = {"2222", "3333", "4444", "5555", "6666", "7777", "8888"};
      for (int i = 0; i < usernames.length; i++) {
        memberService.join(usernames[i], passwords[i]);
      }

      for (int i = 1; i <= 10; i++) {
        articleService.write("제목" + i, "내용" + i);
      }

    };
  }

}
