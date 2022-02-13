package com.czetsuyatech.web.security.identity;

import com.czetsuyatech.web.security.jwt.CtSecurityContext;

public interface OidcCtAccount extends CtAccount {

  CtSecurityContext getCtSecurityContext();
}
