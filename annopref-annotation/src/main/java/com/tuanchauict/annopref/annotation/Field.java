package com.tuanchauict.annopref.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by tuanchauict on 11/16/16.
 */
@Target(value = ElementType.FIELD)
public @interface Field {
    String name();
}
