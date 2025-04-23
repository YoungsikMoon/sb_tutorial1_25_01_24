package com.sbs.tutorial1.boundedContext.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
  private static long lastId;
  private final long id;
  private String username;
  private String password;

  static {
    lastId = 0;
  }

  public Member(String username, String password) {
    this(++lastId, username, password);
  }
}
