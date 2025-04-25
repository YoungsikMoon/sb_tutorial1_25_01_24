package com.sbs.tutorial1.boundedContext.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {

  private final String resultCode;
  private final String msg;
  private final Object data;

  public static RsData of(String resultCode, String msg){
    return new RsData(resultCode, msg, null);
  }

  public static RsData of(String resultCode, String msg, Object data){
    return new RsData(resultCode, msg, data);
  }

  // jackson 라이브러리에 의해 자동으로 success 키가 자동으로 생성됨.
  public boolean isSuccess() {
    return resultCode.startsWith("S-");
  }
}
