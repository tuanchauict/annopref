package com.tuanchauict.annopref.compiler;

import com.tuanchauict.annopref.compiler.datastructure.PreferenceField;

/**
 * Created by tuanchauict on 11/17/16.
 */

public class UnsupportedTypeException extends Exception {
    public PreferenceField field;

    public UnsupportedTypeException(PreferenceField field) {
        this.field = field;
    }
}
