package com.tuanchauict.annopref.compiler.datastructure;

import com.google.common.base.Joiner;
import com.tuanchauict.annopref.annotation.BooleanValue;
import com.tuanchauict.annopref.annotation.DoubleValue;
import com.tuanchauict.annopref.annotation.FloatValue;
import com.tuanchauict.annopref.annotation.IntValue;
import com.tuanchauict.annopref.annotation.LongValue;
import com.tuanchauict.annopref.annotation.StringValue;
import com.tuanchauict.annopref.compiler.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public String getSerializedDefaultValue() {
        switch (getType()) {
            case "boolean":
                BooleanValue bdv = mVariableElement.getAnnotation(BooleanValue.class);
                return bdv != null ? bdv.value() + "" : "false";
            case "int":
                IntValue idv = mVariableElement.getAnnotation(IntValue.class);
                return idv != null ? idv.value()[0] + "" : "0";
            case "long":
                LongValue ldv = mVariableElement.getAnnotation(LongValue.class);
                return ldv != null ? ldv.value()[0] + "" : "0L";
            case "float":
                FloatValue fdv = mVariableElement.getAnnotation(FloatValue.class);
                return fdv != null ? fdv.value()[0] + "" : "0F";
            case "double":
                DoubleValue ddv = mVariableElement.getAnnotation(DoubleValue.class);
                return ddv != null ? ddv.value()[0] + "" : "0.0";
            case "java.lang.String":
                StringValue sdv = mVariableElement.getAnnotation(StringValue.class);
                return sdv != null ? "\"" + sdv.value()[0] + "\"" : "null";
            case "java.util.List<java.lang.Integer>":
                IntValue idvs = mVariableElement.getAnnotation(IntValue.class);
                return idvs == null ? "null"
                        : "asList(" + intsToString(idvs.value()) + ")";
            case "java.util.List<java.lang.Long>":
                LongValue ldvs = mVariableElement.getAnnotation(LongValue.class);
                return ldvs == null ? "null"
                        : "asList(" + longsToString(ldvs.value()) + ")";
            case "java.util.List<java.lang.Float>":
                FloatValue fdvs = mVariableElement.getAnnotation(FloatValue.class);
                return fdvs == null ? "null"
                        : "asList(" + floatsToString(fdvs.value()) + ")";
            case "java.util.List<java.lang.Double>":
                DoubleValue ddvs = mVariableElement.getAnnotation(DoubleValue.class);
                return ddvs == null ? "null"
                        : "asList(" + doublesToString(ddvs.value()) + ")";
            case "java.util.List<java.lang.String>":
                StringValue sdvs = mVariableElement.getAnnotation(StringValue.class);
                return sdvs == null ? "null"
                        : "asList(" + stringsToString(sdvs.value()) + ")";
        }

        return "null";
    }

    private String intsToString(int[] values) {
        List<String> l = new ArrayList<>();
        for (int val : values) {
            l.add(Integer.toString(val));
        }
        return Joiner.on(',').join(l);
    }

    private String longsToString(long[] values) {
        List<String> l = new ArrayList<>();
        for (long val : values) {
            l.add(Long.toString(val) + "L");
        }
        return Joiner.on(',').join(l);
    }

    private String floatsToString(float[] values) {
        List<String> l = new ArrayList<>();
        for (float val : values) {
            l.add(Float.toString(val) + "F");
        }
        return Joiner.on(',').join(l);
    }

    private String doublesToString(double[] values) {
        List<String> l = new ArrayList<>();
        for (double val : values) {
            l.add(Double.toString(val));
        }
        return Joiner.on(',').join(l);
    }

    private String stringsToString(String[] values) {
        List<String> l = new ArrayList<>();
        for (String val : values) {
            l.add("\"" + val + "\"");
        }
        return Joiner.on(',').join(l);
    }
}
