package org.apache.http.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface GuardedBy {
    String value();
}
