package com.tuanchauict.annopref.sample.pref;

import com.tuanchauict.annopref.annotation.Field;
import com.tuanchauict.annopref.annotation.FloatValue;
import com.tuanchauict.annopref.annotation.Preference;
import com.tuanchauict.annopref.annotation.StringValue;

import java.util.List;
import java.util.Set;

/**
 * Created by tuanchauict on 11/16/16.
 */
@Preference(prefix = "FOO")
public class Foo {
    @Field("foo")
    private int intPref;
    @FloatValue(0.3f)
    private float floatPref;
    @StringValue("default value for String")
    private String stringPref;
    private List<Integer> listInt;
    private Set<Integer> setInt;
    private List<String> listString;
    private Set<String> setString;
}
