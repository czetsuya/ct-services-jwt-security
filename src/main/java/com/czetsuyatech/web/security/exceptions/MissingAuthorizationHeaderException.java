package com.czetsuyatech.web.security.exceptions;

public class MissingAuthorizationHeaderException extends RuntimeException {

  public MissingAuthorizationHeaderException(String msg) {
    super(msg);
  }
}
