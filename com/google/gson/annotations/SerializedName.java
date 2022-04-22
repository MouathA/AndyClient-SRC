package com.google.gson.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface SerializedName {
    String value();
}
