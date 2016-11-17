package com.tuanchauict.annopref.sample.pref;

import com.tuanchauict.annopref.annotation.EnumType;
import com.tuanchauict.annopref.annotation.Field;
import com.tuanchauict.annopref.annotation.Ignore;
import com.tuanchauict.annopref.annotation.Preference;

import java.util.List;

/**
 * Created by tuanchauict on 11/16/16.
 */
@Preference(
        prefix = "FOO",
        antiHack = false,
        type = EnumType.SINGLETON
)
public class Foo {
    private int intPref;
    @Field(name = "conheo")
    private float floatPref;
    private String stringPref;
//    private List<Integer> integerListPref;
}
