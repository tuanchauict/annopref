package com.tuanchauict.annopref.compiler.datastructure;

import com.tuanchauict.annopref.annotation.BooleanValue;
import com.tuanchauict.annopref.annotation.DoubleValue;
import com.tuanchauict.annopref.annotation.FloatValue;
import com.tuanchauict.annopref.annotation.IntValue;
import com.tuanchauict.annopref.annotation.LongValue;
import com.tuanchauict.annopref.annotation.StringValue;
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

    public String getType() {
        return mVariableElement.asType().toString();
    }

    public String getFieldName() {
        return mVariableElement.getSimpleName().toString();
    }

    public String getPrefName() {
        return Utils.isEmpty(mCustomName) ? mVariableElement.getSimpleName().toString() : mCustomName;
    }

    public String getMethodName() {
        return Utils.firstUpperCase(getFieldName());
    }

    public VariableElement getVariableElement() {
        return mVariableElement;
    }

    public String getSeriallizedDefaultValue() {
        switch (getType()) {
            case "boolean":
                BooleanValue bdv = mVariableElement.getAnnotation(BooleanValue.class);
                return bdv != null ? bdv.value() + "" : "false";
            case "int":
                IntValue idv = mVariableElement.getAnnotation(IntValue.class);
                return idv != null ? idv.value() + "" : "0";
            case "long":
                LongValue ldv = mVariableElement.getAnnotation(LongValue.class);
                return ldv != null ? ldv.value() + "" : "0L";
            case "float":
                FloatValue fdv = mVariableElement.getAnnotation(FloatValue.class);
                return fdv != null ? fdv.value() + "" : "0F";
            case "double":
                DoubleValue ddv = mVariableElement.getAnnotation(DoubleValue.class);
                return ddv != null ? ddv.value() + "" : "0.0";
            case "java.lang.String":
                StringValue sdv = mVariableElement.getAnnotation(StringValue.class);
                return sdv != null ? "\"" + sdv.value() + "\"" : "null";
        }

        return "null";
    }


}
