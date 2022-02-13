package com.czetsuyatech.web.security.method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CtMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

  private final CtMethodSecurityExpressionHandler methodSecurityExpressionHandler;

  public CtMethodSecurityConfiguration(
      @Autowired(
          required = false) CtMethodSecurityExpressionHandler methodSecurityExpressionHandler) {

    this.methodSecurityExpressionHandler = methodSecurityExpressionHandler;
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {

    return new SecurityExpressionHandler(methodSecurityExpressionHandler);
  }

  private static class SecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final CtMethodSecurityExpressionHandler methodSecurityExpressionHandler;

    public SecurityExpressionHandler(
        CtMethodSecurityExpressionHandler methodSecurityExpressionHandler) {

      this.methodSecurityExpressionHandler = methodSecurityExpressionHandler;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
        Authentication authentication,
        MethodInvocation invocation) {

      return methodSecurityExpressionHandler == null
          ? new DefaultMethodSecurityExpressionRoot(authentication) :
          methodSecurityExpressionHandler.getExpressionRoot(authentication);
    }
  }
}