package com.tuanchauict.annopref.compiler;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tuanchauict on 11/17/16.
 */

public class Constants {
    final static String BOOLEAN_TYPE = "boolean";

    final static Set<String> NUMBER_TYPES = new HashSet<>(Arrays.asList(
            "int",
            "long",
            "float"
    ));

    final static String STRING_TYPE = "java.lang.String";

    final static Set<String> LIST_TYPES = new HashSet<>(Arrays.asList(
            "java.util.List<java.lang.Integer>",
            "java.util.List<java.lang.Long>",
            "java.util.List<java.lang.Float>",
//            "java.util.List<java.lang.Double>",
            "java.util.List<java.lang.String>"
    ));

    final static Set<String> SET_TYPES = new HashSet<>(Arrays.asList(
            "java.util.Set<java.lang.Integer>",
            "java.util.Set<java.lang.Long>",
            "java.util.Set<java.lang.Float>",
//            "java.util.Set<java.lang.Double>",
            "java.util.Set<java.lang.String>"
    ));

    final static Set<String> ATOMIC_CLASS_TYPES = new HashSet<>(Arrays.asList(
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Float",
//            "java.lang.Double",
            "java.lang.String"
    ));


    final static Set<String> SUPPORT_TYPES = new HashSet<>();

    static {
        SUPPORT_TYPES.addAll(NUMBER_TYPES);
        SUPPORT_TYPES.add(STRING_TYPE);
        SUPPORT_TYPES.add(BOOLEAN_TYPE);
        SUPPORT_TYPES.addAll(LIST_TYPES);
        SUPPORT_TYPES.addAll(SET_TYPES);
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

            return "null";
        }
    });

    static Map<String, String> METHOD_OF_TYPE = Maps.asMap(SUPPORT_TYPES, new Function<String, String>() {
        @Override
        public String apply(String s) {
            if (NUMBER_TYPES.contains(s) || BOOLEAN_TYPE.equals(s))
                return Utils.firstUpperCase(s);
            if (STRING_TYPE.equals(s))
                return "String";
            switch (s) {
                case "java.util.List<java.lang.Integer>":
                    return "IntegerList";
                case "java.util.List<java.lang.Long>":
                    return "LongList";
                case "java.util.List<java.lang.Float>":
                    return "FloatList";
                case "java.util.List<java.lang.Double>":
                    return "DoubleList";
                case "java.util.List<java.lang.String>":
                    return "StringList";

                case "java.util.Set<java.lang.Integer>":
                    return "IntegerSet";
                case "java.util.Set<java.lang.Long>":
                    return "LongSet";
                case "java.util.Set<java.lang.Float>":
                    return "FloatSet";
                case "java.util.Set<java.lang.Double>":
                    return "DoubleSet";
                case "java.util.Set<java.lang.String>":
                    return "StringSet";
            }

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

                case "java.util.List<java.lang.Integer>":
                    return ParameterizedTypeName.get(List.class, Integer.class);
                case "java.util.List<java.lang.Long>":
                    return ParameterizedTypeName.get(List.class, Long.class);
                case "java.util.List<java.lang.Float>":
                    return ParameterizedTypeName.get(List.class, Float.class);
                case "java.util.List<java.lang.Double>":
                    return ParameterizedTypeName.get(List.class, Double.class);
                case "java.util.List<java.lang.String>":
                    return ParameterizedTypeName.get(List.class, String.class);

                case "java.util.Set<java.lang.Integer>":
                    return ParameterizedTypeName.get(Set.class, Integer.class);
                case "java.util.Set<java.lang.Long>":
                    return ParameterizedTypeName.get(Set.class, Long.class);
                case "java.util.Set<java.lang.Float>":
                    return ParameterizedTypeName.get(Set.class, Float.class);
                case "java.util.Set<java.lang.Double>":
                    return ParameterizedTypeName.get(Set.class, Double.class);
                case "java.util.Set<java.lang.String>":
                    return ParameterizedTypeName.get(Set.class, String.class);


            }
            return null;
        }
    });
}
