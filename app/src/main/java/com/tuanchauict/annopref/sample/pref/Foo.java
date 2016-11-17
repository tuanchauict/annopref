package com.tuanchauict.annopref.sample.pref;

import com.tuanchauict.annopref.annotation.EnumType;
import com.tuanchauict.annopref.annotation.Field;
import com.tuanchauict.annopref.annotation.Preference;

/**
 * Created by tuanchauict on 11/16/16.
 */
@Preference(prefix = "FOO")
public class Foo {
    @Field(name = "foo")
    private int intPref;
    private float floatPref;
    private String stringPref;
}
