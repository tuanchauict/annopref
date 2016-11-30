package com.tuanchauict.annopref.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by tuanchauict on 11/29/16.
 */
@Target(value = ElementType.FIELD)
public @interface FloatValue {
    float[] value();
}
