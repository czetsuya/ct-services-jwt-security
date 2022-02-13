package com.czetsuyatech.web.security.identity;

import java.security.Principal;
import java.util.Set;

public interface CtAccount {

  Principal getPrincipal();

  Set<String> getRoles();
}
