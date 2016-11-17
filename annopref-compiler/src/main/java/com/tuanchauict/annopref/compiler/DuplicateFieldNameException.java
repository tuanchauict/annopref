package com.tuanchauict.annopref.compiler;

import com.tuanchauict.annopref.compiler.datastructure.PreferenceField;

/**
 * Created by tuanchauict on 11/17/16.
 */

public class DuplicateFieldNameException extends Exception {
    PreferenceField field1;
    PreferenceField field2;

    public DuplicateFieldNameException(PreferenceField field1, PreferenceField field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
}
