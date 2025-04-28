package com.sbs.tutorial1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;

@Component
@RequestScope // 이 객체는 매 요청마다 생성
@AllArgsConstructor
public class Rq {
  private final HttpServletRequest req;
  private final HttpServletResponse resp;

  public void setCookie(String name, long value) {
    setCookie(name, value + "");
  }

  private void setCookie(String name, String value) {
    resp.addCookie(new Cookie(name, value));
  }

  // 쿠키 삭제
  public boolean removedCookie(String name) {
    Cookie cookie = Arrays.stream(req.getCookies())
        .filter(c -> c.getName().equals(name))
        .findFirst()
        .orElse(null);
    if(cookie != null){
      cookie.setMaxAge(0);
      resp.addCookie(cookie);
      return true;
    }
    return false;
  }

  public long getCookieAsLong(String name, long defaultValue) {
    String value = getCookie(name, null);
    if(value == null) return defaultValue;

    try {
      return Long.parseLong(value);
    }catch (NumberFormatException e){
      return defaultValue;
    }
 }

  private String getCookie(String name, String defaultValue) {
    if(req.getCookies() == null) return defaultValue;

    return Arrays.stream(req.getCookies())
        .filter(cookie -> cookie.getName().equals(name))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(defaultValue);
  }

  public void setSession(String name, long value) {
    HttpSession session = req.getSession();
    session.setAttribute(name, value);
  }

  public long getSessionAsLong(String name, long defaultValue) {
    try {
      long value = (long) req.getSession().getAttribute(name);
      return value;
    }catch (Exception e){
      return defaultValue;
    }
  }

  public String getSessionAsStr(String name, String defaultValue) {
    try {
      String value = (String) req.getSession().getAttribute(name);
      if (value == null) return defaultValue;
      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public boolean removedSession(String name) {
    HttpSession session = req.getSession();

    // 세션 불러왔는데 없으면 false
    if(session.getAttribute(name) == null) return false;

    session.removeAttribute(name);
    return true;
  }

  // 세션에 저장된 모든 정보를 Map으로 반환
  public Map<String, Object> getSessionDebugInfo() {
    HttpSession session = req.getSession(false); // 세션이 없으면 null 반환
    Map<String, Object> sessionInfo = new LinkedHashMap<>();

    if (session == null) {
      sessionInfo.put("세션 존재 여부", false);
      return sessionInfo;
    }

    // 세션 기본 정보 (한국어 키)
    sessionInfo.put("세션 ID", session.getId());
    sessionInfo.put("생성 시각", new java.util.Date(session.getCreationTime()));
    sessionInfo.put("마지막 접근 시각", new java.util.Date(session.getLastAccessedTime()));
    sessionInfo.put("최대 유효 시간(초)", session.getMaxInactiveInterval());
    sessionInfo.put("새 세션 여부", session.isNew());

    // 세션에 저장된 속성들 (한국어로)
    Map<String, Object> attributes = new LinkedHashMap<>();
    Enumeration<String> names = session.getAttributeNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      Object value = session.getAttribute(name);
      attributes.put(name, value);
    }
    sessionInfo.put("저장된 값(Attributes)", attributes);

    return sessionInfo;
  }

  public boolean isLogined() {
    long logindMemberId = getSessionAsLong("loginedMemberId", 0);
    return logindMemberId > 0;
  }

  public boolean isLogout(){
    return !isLogined();
  }

  public long getLoginedMember() {
    return getSessionAsLong("loginedMemberId", 0);
  }
}
