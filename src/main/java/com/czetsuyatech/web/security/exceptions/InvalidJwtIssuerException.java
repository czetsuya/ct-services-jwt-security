package com.czetsuyatech.web.security.exceptions;


public class InvalidJwtIssuerException extends RuntimeException {

  public InvalidJwtIssuerException(String msg) {
    super(msg);
  }
}
