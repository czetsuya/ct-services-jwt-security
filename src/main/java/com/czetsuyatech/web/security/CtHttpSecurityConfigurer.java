package com.czetsuyatech.web.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface CtHttpSecurityConfigurer {

  void configure(HttpSecurity http) throws Exception;
}
