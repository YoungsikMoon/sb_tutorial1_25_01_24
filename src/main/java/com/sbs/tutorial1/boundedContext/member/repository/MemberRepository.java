package com.sbs.tutorial1.boundedContext.member.repository;

import com.sbs.tutorial1.boundedContext.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

  private List<Member> members;

  public MemberRepository() {
    members = new ArrayList<>();

    members.add(new Member("user1", "1234"));
    members.add(new Member("user2", "1234"));
    members.add(new Member("user3", "1234"));
    members.add(new Member("love", "2222"));
    members.add(new Member("test", "123456"));
    members.add(new Member("giving", "23456"));
    members.add(new Member("dance", "3452"));
    members.add(new Member("spring", "14110"));
    members.add(new Member("summer", "8899"));
    members.add(new Member("winter", "331122"));
  }

  public Member findByUsername(String username) {
    return members.stream()
        .filter(member -> member.getUsername().equals(username))
        .findFirst()
        .orElse(null);
  }
}
