package org.apache.http.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
public @interface NotThreadSafe {
}
