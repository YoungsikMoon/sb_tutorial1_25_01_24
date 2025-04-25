package com.sbs.tutorial1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Getter
@Setter
@Entity
public class Article {
  @Id //primary key
  @GeneratedValue(strategy = IDENTITY) //auto increment
  private long id;
  private LocalDate createdDate; //데이터 생성 날짜
  private LocalDate modifiedDate; //데이터 수정 날짜
  private String title;
  private String content;
}

