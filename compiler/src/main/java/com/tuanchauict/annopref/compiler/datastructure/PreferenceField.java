package com.tuanchauict.annopref.compiler.datastructure;

import com.tuanchauict.annopref.compiler.Utils;

import javax.lang.model.element.VariableElement;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class PreferenceField {
    private final VariableElement mTypeElement;
    private final String mCustomName;

    public PreferenceField(VariableElement typeElement, String customName) {
        mTypeElement = typeElement;
        mCustomName = customName;
    }

    public String getType(){
        return mTypeElement.asType().toString();
    }

    public String getFieldName(){
        return mTypeElement.getSimpleName().toString();
    }

    public String getPrefName(){
        return Utils.isEmpty(mCustomName) ? mTypeElement.getSimpleName().toString() : mCustomName;
    }

    public String getMethodName(){
        return Utils.firstUpperCase(getFieldName());
    }
}
