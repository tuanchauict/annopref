package com.tuanchauict.annopref.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tuanchauict.annopref.compiler.datastructure.PreferenceClass;
import com.tuanchauict.annopref.compiler.datastructure.PreferenceField;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.lang.model.element.Modifier;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class CodeGenerator {

    private static final String SUFFIX = "Pref";
    private static final ClassName ANNOPREF = ClassName.get("com.tuanchauict.annopref", "AnnoPref");

    public static TypeSpec generateClass(PreferenceClass cls) throws UnsupportedDataTypeException {

        String clsName = cls.getClassName() + SUFFIX;

        TypeSpec.Builder builder = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC);

        boolean singleton = cls.isSingleton();
        boolean antiHack = cls.isAntiHack();
        String prefix = cls.getPrefix();

        if(singleton){
            ClassName singletonType = ClassName.get("", clsName);
            FieldSpec.Builder field = FieldSpec.builder(singletonType, "sInstance", Modifier.STATIC, Modifier.PRIVATE);
            builder.addField(field.build());

            MethodSpec.Builder method = MethodSpec.methodBuilder("getInstance")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(singletonType)
                    .addStatement("if (sInstance == null) sInstance = new $L()", clsName)
                    .addStatement("return sInstance");
            builder.addMethod(method.build());
        }

        List<PreferenceField> fields = cls.getFields();
        for (PreferenceField field : fields) {
            System.out.println(field.getFieldName() + " : " + field.getType());
            builder.addMethod(makeGetMethod(singleton, antiHack, prefix, field));
            builder.addMethod(makeGetWithDefaultMethod(singleton, antiHack, prefix, field));
            builder.addMethod(makeSetMethod(singleton, antiHack, prefix, field));
            builder.addMethod(makeHasMethod(singleton, antiHack, prefix, field));
        }

        return builder.build();
    }


    private static MethodSpec makeGetMethod(boolean singleton, boolean antiHack, String prefix,
                                            PreferenceField field) throws UnsupportedDataTypeException {
        MethodSpec.Builder builder = makeGenericMethod(singleton, "get" + field.getMethodName());
        String key = getPrefKey(antiHack, prefix, field.getPrefName());
        String type = field.getType();
        TypeName typeName = typeNameOf(type);
        System.out.println(typeName);
        if (typeName != null) {
            builder.returns(typeName);
            builder.addStatement("return $T.get$L($S, $L)",
                    ANNOPREF,
                    methodNameOfType(type),
                    key,
                    defaultOf(type)
            );
        } else {
            builder.returns(typeOf(type));
            builder.addStatement("return $T.get$L($S, $L)",
                    ANNOPREF,
                    methodNameOfType(type),
                    key,
                    "null"
            );
        }

        return builder.build();
    }

    private static MethodSpec makeGetWithDefaultMethod(boolean singleton, boolean antiHack,
                                                       String prefix, PreferenceField field) throws UnsupportedDataTypeException {
        MethodSpec.Builder builder = makeGenericMethod(singleton, "get" + field.getMethodName());
        String key = getPrefKey(antiHack, prefix, field.getPrefName());
        String type = field.getType();
        TypeName typeName = typeNameOf(type);
        System.out.println(typeName);
        if (typeName != null) {
            builder.addParameter(typeName, "defaultValue");
            builder.returns(typeName);
            builder.addStatement("return $T.get$L($S, $L)",
                    ANNOPREF,
                    methodNameOfType(type),
                    key,
                    "defaultValue"
            );
        } else {
            Type t = typeOf(type);
            builder.returns(t);
            builder.addParameter(t, "defaultValue");
            builder.addStatement("return $T.get$L($S, $L)",
                    ANNOPREF,
                    methodNameOfType(type),
                    key,
                    "defaultValue"
            );
        }

        return builder.build();
    }

    private static MethodSpec makeSetMethod(boolean singleton, boolean antiHack, String prefix,
                                            PreferenceField field) throws UnsupportedDataTypeException {

        MethodSpec.Builder builder = makeGenericMethod(singleton, "set" + field.getMethodName());
        String type = field.getType();
        String key = getPrefKey(antiHack, prefix, field.getPrefName());
        TypeName typeName = typeNameOf(field.getType());
        if (typeName != null) {
            builder.addParameter(typeName, field.getFieldName());
            builder.addStatement("$T.put$L($S, $L)",
                    ANNOPREF,
                    methodNameOfType(type),
                    key,
                    field.getFieldName()
            );
        } else {
            builder.addParameter(typeOf(field.getType()), field.getFieldName());
        }

        return builder.build();
    }

    private static MethodSpec makeHasMethod(boolean singleton, boolean antiHack, String prefix,
                                            PreferenceField field) {

        MethodSpec.Builder builder = makeGenericMethod(singleton, "has" + field.getMethodName())
                .addStatement("return $T.containsKey($S)", ANNOPREF, getPrefKey(antiHack, prefix, field.getPrefName()))
                .returns(TypeName.BOOLEAN);

        return builder.build();
    }

    private static MethodSpec.Builder makeGenericMethod(boolean singleton, String methodName) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC);
        if(!singleton)
            builder.addModifiers(Modifier.STATIC);

        return builder;
    }


    private static Type typeOf(String type) throws UnsupportedDataTypeException {
        switch (type) {
//            case "java.util.List<java.lang.Integer>":
//                return List.class;
        }


        throw new UnsupportedDataTypeException();
    }

    private static TypeName typeNameOf(String type) {
        System.out.println("typeNameOf: " + type);
        switch (type) {
            case "int":
                return TypeName.INT;
            case "long":
                return TypeName.LONG;
            case "float":
                return TypeName.FLOAT;
            case "double":
                return TypeName.DOUBLE;
            case "java.lang.String":
                return ClassName.get(String.class);

        }
        return null;
    }

    private static String methodNameOfType(String type){
        switch (type){
            case "int":
                return "Int";
            case "long":
                return "Long";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "java.lang.String":
                return "String";
            case "java.util.List<java.lang.Integer>":
                return "IntegerList";
        }
        return null;
    }

    private static String defaultOf(String type) {
        switch (type) {
            case "int":
                return "0";
            case "long":
                return "0";
            case "float":
                return "0";
            case "double":
                return "0";
            case "java.lang.String":
                return "null";
        }
        return "null";
    }

    private static String getPrefKey(boolean antiHack, String prefix, String name) {
        if (antiHack) {
            return Utils.md5(prefix + "_" + name);
        } else {
            return prefix + "_" + name;
        }
    }
}
