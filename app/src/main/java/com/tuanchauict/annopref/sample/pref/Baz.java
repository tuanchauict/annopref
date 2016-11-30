package com.tuanchauict.annopref.sample.pref;

import com.tuanchauict.annopref.annotation.BooleanValue;
import com.tuanchauict.annopref.annotation.DoubleValue;
import com.tuanchauict.annopref.annotation.Field;
import com.tuanchauict.annopref.annotation.FloatValue;
import com.tuanchauict.annopref.annotation.IntValue;
import com.tuanchauict.annopref.annotation.LongValue;
import com.tuanchauict.annopref.annotation.Preference;
import com.tuanchauict.annopref.annotation.StringValue;

import java.util.List;

/**
 * Created by tuanchauict on 11/17/16.
 */
@Preference(antiHack = false, prefix = "abc")
public class Baz {
    @BooleanValue(true)
    @IntValue(1)
    private boolean baz;
    @Field("int")
    @IntValue({1,2,3})
    private List<Integer> listInt;
    @LongValue({1,2,3})
    private List<Long> listLong;
    @FloatValue({1,2,3})
    List<Float> listFloat;
    @DoubleValue({1,2,3})
    List<Double> listDouble;
    @StringValue({"1", "2", "3"})
    List<String> listString;
}
