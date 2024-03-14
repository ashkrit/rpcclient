package org.rpc.http;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface XQuery {
    String value();

    boolean encoded() default false;
}
