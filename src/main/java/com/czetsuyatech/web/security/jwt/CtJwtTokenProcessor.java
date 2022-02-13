package com.czetsuyatech.web.security.jwt;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface CtJwtTokenProcessor {

  Authentication getAuthentication(HttpServletRequest request) throws Exception;
}