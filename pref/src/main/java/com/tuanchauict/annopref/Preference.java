package com.tuanchauict.annopref;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(value = TYPE)
public @interface Preference {
    String prefix() default "";
    boolean antiHack() default false;
    EnumType type() default EnumType.STATIC;
}
