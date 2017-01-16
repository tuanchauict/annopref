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
                return ldv != null ? ldv.value()[0] + "L" : "0L";
            case "float":
                FloatValue fdv = mVariableElement.getAnnotation(FloatValue.class);
                return fdv != null ? fdv.value()[0] + "F" : "0F";
            case "double":
                DoubleValue ddv = mVariableElement.getAnnotation(DoubleValue.class);
                return ddv != null ? ddv.value()[0] + "" : "0.0";
            case "java.lang.String":
                StringValue sdv = mVariableElement.getAnnotation(StringValue.class);
                return sdv != null ? "\"" + sdv.value()[0] + "\"" : "null";
            case "java.util.List<java.lang.Integer>":
                IntValue idvs = mVariableElement.getAnnotation(IntValue.class);
                return idvs == null ? "null"
                        : "java.util.Arrays.asList(" + valuesToString(idvs.value()) + ")";
            case "java.util.List<java.lang.Long>":
                LongValue ldvs = mVariableElement.getAnnotation(LongValue.class);
                return ldvs == null ? "null"
                        : "java.util.Arrays.asList(" + valuesToString(ldvs.value()) + ")";
            case "java.util.List<java.lang.Float>":
                FloatValue fdvs = mVariableElement.getAnnotation(FloatValue.class);
                return fdvs == null ? "null"
                        : "java.util.Arrays.asList(" + valuesToString(fdvs.value()) + ")";
            case "java.util.List<java.lang.Double>":
                DoubleValue ddvs = mVariableElement.getAnnotation(DoubleValue.class);
                return ddvs == null ? "null"
                        : "java.util.Arrays.asList(" + valuesToString(ddvs.value()) + ")";
            case "java.util.List<java.lang.String>":
                StringValue sdvs = mVariableElement.getAnnotation(StringValue.class);
                return sdvs == null ? "null"
                        : "java.util.Arrays.asList(" + valuesToString(sdvs.value()) + ")";
            case "java.util.Set<java.lang.Integer>":
                IntValue sidvs = mVariableElement.getAnnotation(IntValue.class);
                return sidvs == null ? "null"
                        : "new java.util.HashSet<>(java.util.Arrays.asList(" + valuesToString(sidvs.value())+"))";
            case "java.util.Set<java.lang.Long>":
                LongValue sldvs = mVariableElement.getAnnotation(LongValue.class);
                return sldvs == null ? "null"
                        : "new java.util.HashSet<>(java.util.Arrays.asList(" + valuesToString(sldvs.value())+"))";
            case "java.util.Set<java.lang.Float>":
                FloatValue sfdvs = mVariableElement.getAnnotation(FloatValue.class);
                return sfdvs == null ? "null"
                        : "new java.util.HashSet<>(java.util.Arrays.asList(" + valuesToString(sfdvs.value())+"))";
            case "java.util.Set<java.lang.Double>":
                DoubleValue sddvs = mVariableElement.getAnnotation(DoubleValue.class);
                return sddvs == null ? "null"
                        : "new java.util.HashSet<>(java.util.Arrays.asList(" + valuesToString(sddvs.value())+"))";
            case "java.util.Set<java.lang.String>":
                StringValue ssdvs = mVariableElement.getAnnotation(StringValue.class);
                return ssdvs == null ? "null"
                        : "new java.util.HashSet<>(java.util.Arrays.asList(" + valuesToString(ssdvs.value())+"))";
        }

        return "null";
    }

    private String valuesToString(int[] values) {
        List<String> l = new ArrayList<>();
        for (int val : values) {
            l.add(Integer.toString(val));
        }
        return Joiner.on(',').join(l);
    }

    private String valuesToString(long[] values) {
        List<String> l = new ArrayList<>();
        for (long val : values) {
            l.add(Long.toString(val) + "L");
        }
        return Joiner.on(',').join(l);
    }

    private String valuesToString(float[] values) {
        List<String> l = new ArrayList<>();
        for (float val : values) {
            l.add(Float.toString(val) + "F");
        }
        return Joiner.on(',').join(l);
    }

    private String valuesToString(double[] values) {
        List<String> l = new ArrayList<>();
        for (double val : values) {
            l.add(Double.toString(val));
        }
        return Joiner.on(',').join(l);
    }

    private String valuesToString(String[] values) {
        List<String> l = new ArrayList<>();
        for (String val : values) {
            l.add("\"" + val + "\"");
        }
        return Joiner.on(',').join(l);
    }
}
