package com.tuanchauict.annopref.compiler.datastructure;

import com.tuanchauict.annopref.compiler.Utils;

import javax.lang.model.element.VariableElement;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class PreferenceField {
    private final VariableElement mVariableElement;
    private final String mCustomName;

    public PreferenceField(VariableElement typeElement, String customName) {
        mVariableElement = typeElement;
        mCustomName = customName;
    }

    public String getType(){
        return mVariableElement.asType().toString();
    }

    public String getFieldName(){
        return mVariableElement.getSimpleName().toString();
    }

    public String getPrefName(){
        return Utils.isEmpty(mCustomName) ? mVariableElement.getSimpleName().toString() : mCustomName;
    }

    public String getMethodName(){
        return Utils.firstUpperCase(getFieldName());
    }

    public VariableElement getVariableElement() {
        return mVariableElement;
    }
}
