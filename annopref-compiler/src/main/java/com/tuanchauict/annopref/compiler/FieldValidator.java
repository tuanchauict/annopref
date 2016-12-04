package com.tuanchauict.annopref.compiler;

import com.tuanchauict.annopref.annotation.Ignore;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class FieldValidator {
    static boolean isValid(VariableElement variableElement) {
        if (variableElement.getAnnotation(Ignore.class) != null) {
            return true;
        }
        TypeMirror mirror = variableElement.asType();

        return Constants.SUPPORT_TYPES.contains(mirror.toString());
    }
}
