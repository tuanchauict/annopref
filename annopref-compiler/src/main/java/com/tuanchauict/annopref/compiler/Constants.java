package com.tuanchauict.annopref.compiler;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tuanchauict on 11/17/16.
 */

public class Constants {
    static String BOOLEAN_TYPE = "boolean";
    static Set<String> NUMBER_TYPES = new HashSet<>(Arrays.asList(
            "int",
            "long",
            "float",
            "double"
    ));
    static String STRING_TYPE = "java.lang.String";

    static Set<String> SUPPORT_TYPES = new HashSet<>();

    static {
        SUPPORT_TYPES.addAll(NUMBER_TYPES);
        SUPPORT_TYPES.add(STRING_TYPE);
        SUPPORT_TYPES.add(BOOLEAN_TYPE);
    }

    static Map<String, String> DEFAULT_VALUES = Maps.asMap(SUPPORT_TYPES, new Function<String, String>() {
        @Override
        public String apply(String s) {
            if (NUMBER_TYPES.contains(s))
                return "0";
            if (STRING_TYPE.equals(s))
                return "null";
            if (BOOLEAN_TYPE.equals(s))
                return "false";

            return null;
        }
    });

    static Map<String, String> METHOD_OF_TYPE = Maps.asMap(SUPPORT_TYPES, new Function<String, String>() {
        @Override
        public String apply(String s) {
            if (NUMBER_TYPES.contains(s) || BOOLEAN_TYPE.equals(s))
                return Utils.firstUpperCase(s);
            if (STRING_TYPE.equals(s))
                return "String";
            return null;
        }
    });

    static Map<String, TypeName> TYPENAME_OF_TYPE = Maps.asMap(SUPPORT_TYPES, new Function<String, TypeName>() {
        @Override
        public TypeName apply(String s) {
            switch (s) {
                case "boolean":
                    return TypeName.BOOLEAN;
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
    });
}
