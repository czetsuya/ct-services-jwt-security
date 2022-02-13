package com.czetsuyatech.web.security.jwt;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CtSecurityContext implements Serializable {

  private String tokenString;
  private String idTokenString;
}
