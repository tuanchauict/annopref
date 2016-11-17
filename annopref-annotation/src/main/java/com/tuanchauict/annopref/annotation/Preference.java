package com.tuanchauict.annopref.annotation;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;


@Target(value = TYPE)
public @interface Preference {
    String prefix() default "";
    boolean autoPrefix() default false;
    boolean antiHack() default false;
    EnumType type() default EnumType.STATIC;
}
