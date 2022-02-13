package com.czetsuyatech.web.security.exceptions;

public class InvalidIdTokenException extends RuntimeException {

  public InvalidIdTokenException(String msg) {
    super(msg);
  }
}
