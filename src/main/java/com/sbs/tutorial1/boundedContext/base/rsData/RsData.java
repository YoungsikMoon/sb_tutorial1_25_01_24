package com.sbs.tutorial1.boundedContext.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {

  private final String resultCode;
  private final String msg;

  public static RsData of(String resultCode, String msg){
    return new RsData(resultCode, msg);
  }
}
