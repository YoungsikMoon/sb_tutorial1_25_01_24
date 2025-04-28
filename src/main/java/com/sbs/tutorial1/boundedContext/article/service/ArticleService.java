package com.sbs.tutorial1.boundedContext.article.service;

import com.sbs.tutorial1.boundedContext.article.entity.Article;
import com.sbs.tutorial1.boundedContext.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;

  public Article write(String subject, String content) {
    Article article = Article.builder()
        .subject(subject)
        .content(content)
        .build();

    articleRepository.save(article);

    return article;
  }

  public List<Article> findAllByOrderByIdDesc() {
    return articleRepository.findAllByOrderByIdDesc();
  }
}
