package com.example.bankcards.util.masking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Masked {

    MaskType type() default MaskType.CUSTOM;

    int visiblePrefix() default 0;

    int visibleSuffix() default 4;

    char maskChar() default '*';

}