package com.viaversion.viaversion.libs.gson.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface SerializedName {
    String value();
    
    String[] alternate() default {};
}
