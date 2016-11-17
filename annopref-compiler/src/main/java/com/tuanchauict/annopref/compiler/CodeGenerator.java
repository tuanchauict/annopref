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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.activation.UnsupportedDataTypeException;
import javax.lang.model.element.Modifier;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class CodeGenerator {

    private static final String SUFFIX = "Pref";
    private static final ClassName ANNOPREF = ClassName.get("com.tuanchauict.annopref", "AnnoPref");

    public static TypeSpec generateClass(PreferenceClass cls, HashMap<String, PreferenceField> currentFieldNames) throws UnsupportedDataTypeException, DuplicateFieldNameException {

        String clsName = cls.getClassName() + SUFFIX;

        TypeSpec.Builder builder = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC);

        boolean singleton = cls.isSingleton();
        boolean antiHack = cls.isAntiHack();
        String prefix = cls.getPrefix();

        if (singleton) {
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
            String fieldName = getPrefKey(antiHack, prefix, field.getPrefName());
            if (currentFieldNames.keySet().contains(fieldName)) {
                throw new DuplicateFieldNameException(currentFieldNames.get(fieldName), field);
            }
            currentFieldNames.put(fieldName, field);
            builder.addMethod(makeGetMethod(singleton, fieldName, field));
            builder.addMethod(makeGetWithDefaultMethod(singleton, fieldName, field));
            builder.addMethod(makeSetMethod(singleton, fieldName, field));
            builder.addMethod(makeHasMethod(singleton, fieldName, field));
        }

        return builder.build();
    }


    private static MethodSpec makeGetMethod(boolean singleton, String fieldName,
                                            PreferenceField field) throws UnsupportedDataTypeException {
        MethodSpec.Builder builder = makeGenericMethod(singleton, "get" + field.getMethodName());
        String type = field.getType();
        TypeName typeName = typeNameOf(type);
        builder.returns(typeName);
        builder.addStatement("return $T.get$L($S, $L)",
                ANNOPREF,
                methodNameOfType(type),
                fieldName,
                defaultOf(type)
        );

        return builder.build();
    }

    private static MethodSpec makeGetWithDefaultMethod(boolean singleton, String fieldName, PreferenceField field) throws UnsupportedDataTypeException {
        MethodSpec.Builder builder = makeGenericMethod(singleton, "get" + field.getMethodName());
        String type = field.getType();
        TypeName typeName = typeNameOf(type);
        if (typeName != null) {
            builder.addParameter(typeName, "defaultValue");
            builder.returns(typeName);
            builder.addStatement("return $T.get$L($S, $L)",
                    ANNOPREF,
                    methodNameOfType(type),
                    fieldName,
                    "defaultValue"
            );
        }

        return builder.build();
    }

    private static MethodSpec makeSetMethod(boolean singleton, String fieldName,
                                            PreferenceField field) throws UnsupportedDataTypeException {

        MethodSpec.Builder builder = makeGenericMethod(singleton, "set" + field.getMethodName());
        String type = field.getType();
        TypeName typeName = typeNameOf(field.getType());
        builder.addParameter(typeName, field.getFieldName());
        builder.addStatement("$T.put$L($S, $L)",
                ANNOPREF,
                methodNameOfType(type),
                fieldName,
                field.getFieldName()
        );

        return builder.build();
    }

    private static MethodSpec makeHasMethod(boolean singleton, String fieldName,
                                            PreferenceField field) {

        MethodSpec.Builder builder = makeGenericMethod(singleton, "has" + field.getMethodName())
                .addStatement("return $T.containsKey($S)", ANNOPREF, fieldName)
                .returns(TypeName.BOOLEAN);

        return builder.build();
    }

    private static MethodSpec.Builder makeGenericMethod(boolean singleton, String methodName) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC);
        if (!singleton)
            builder.addModifiers(Modifier.STATIC);

        return builder;
    }

    private static TypeName typeNameOf(String type) throws UnsupportedDataTypeException {
        TypeName result = Constants.TYPENAME_OF_TYPE.get(type);
        if (result == null)
            throw new UnsupportedDataTypeException();
        return result;
    }

    private static String methodNameOfType(String type) throws UnsupportedDataTypeException {
        String result = Constants.METHOD_OF_TYPE.get(type);
        if (result == null)
            throw new UnsupportedDataTypeException();
        return result;
    }

    private static String defaultOf(String type) throws UnsupportedDataTypeException {
        String result = Constants.DEFAULT_VALUES.get(type);
        if (result == null)
            throw new UnsupportedDataTypeException();
        return result;
    }

    private static String getPrefKey(boolean antiHack, String prefix, String name) {
        if (antiHack) {
            return Utils.md5(prefix + "_" + name);
        } else {
            return prefix + "_" + name;
        }
    }
}
