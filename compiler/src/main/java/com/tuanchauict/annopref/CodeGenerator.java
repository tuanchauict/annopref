package com.tuanchauict.annopref;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.tuanchauict.annopref.datastructure.PreferenceClass;
import com.tuanchauict.annopref.datastructure.PreferenceField;

import java.lang.reflect.Type;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;
import javax.lang.model.element.Modifier;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class CodeGenerator {

    static String sufix = "Pref";

    public static TypeSpec generateClass(PreferenceClass cls) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(cls.getClassName() + "Pref")
                .addModifiers(Modifier.PUBLIC);

        boolean singleton = cls.isSingleton();
        boolean antiHack = cls.isAntiHack();
        String prefix = cls.getPrefix();

        List<PreferenceField> fields = cls.getFields();
        for (PreferenceField field : fields) {

        }

        return builder.build();
    }


    private static MethodSpec makeGetMethod(boolean singleton, boolean antiHack, String prefix,
                                            PreferenceField field) throws UnsupportedDataTypeException {


        MethodSpec.Builder builder = MethodSpec.methodBuilder("get" + field.getMethodName())
                .addModifiers(Modifier.PUBLIC, singleton ? null : Modifier.STATIC)
                .addStatement("");

        TypeName typeName = typeNameOf(field.getType());
        if(typeName != null){
            builder.returns(typeName);
        } else {
            builder.returns(typeOf(field.getType()));
        }

        return builder.build();
    }




    private static Type typeOf(String type) throws UnsupportedDataTypeException {
        switch (type) {

        }
        throw new UnsupportedDataTypeException();
    }

    private static TypeName typeNameOf(String type){
        switch (type){
            case "int":
                return TypeName.INT;
            case "float":
                return TypeName.FLOAT;
            case "double":
                return TypeName.DOUBLE;

        }
        return null;
    }
}
