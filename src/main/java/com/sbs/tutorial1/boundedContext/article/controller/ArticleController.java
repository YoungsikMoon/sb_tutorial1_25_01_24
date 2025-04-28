package com.sbs.tutorial1.boundedContext.article.controller;

import com.sbs.tutorial1.boundedContext.article.entity.Article;
import com.sbs.tutorial1.boundedContext.article.repository.ArticleRepository;
import com.sbs.tutorial1.boundedContext.base.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor //필드 중에서 final 붙은 필드 속성을 인자값으로 받는 생성자 메서드 생성
public class ArticleController {
    private final ArticleRepository articleRepository;

    @GetMapping("/write")
    @ResponseBody
    public RsData write(String subject, String content){
        Article article = Article.builder()
            .createdDate(LocalDateTime.now())
            .modifiedDate(LocalDateTime.now())
            .subject(subject)
            .content(content)
            .build();

        // Article article = new Article(subject, content);

        articleRepository.save(article);

        return RsData.of("S-1", "%d번 글이 생성되었습니다.".formatted(article.getId()), article);
    }
}
