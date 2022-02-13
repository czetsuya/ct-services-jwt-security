package com.czetsuyatech.web.security.identity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

@Data
public class CtRole implements GrantedAuthority {

  private String role;

  public CtRole(String role) {
    Assert.notNull(role, "role cannot be null");
    this.role = role;
  }

  @Override
  public String getAuthority() {
    return role;
  }
}
