package com.czetsuyatech.web.security;

import com.czetsuyatech.web.security.method.CtMethodSecurityConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {
    CtWebSecurityConfiguration.class,
    CtMethodSecurityConfiguration.class,
    CtSecurityAutoConfiguration.class
})
public @interface EnableCtSecurity {

}
