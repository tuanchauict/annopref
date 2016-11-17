package com.tuanchauict.annopref.sample.pref;

import com.tuanchauict.annopref.annotation.EnumType;
import com.tuanchauict.annopref.annotation.Field;
import com.tuanchauict.annopref.annotation.Preference;

/**
 * Created by tuanchauict on 11/16/16.
 */
@Preference(prefix = "FOO")
public class Foo {
    private int intPref;
    @Field(name = "customFloatName")
    private float floatPref;
    private String stringPref;
}
