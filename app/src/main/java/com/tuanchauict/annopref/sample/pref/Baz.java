package com.tuanchauict.annopref.sample.pref;

import com.tuanchauict.annopref.annotation.BooleanValue;
import com.tuanchauict.annopref.annotation.IntValue;
import com.tuanchauict.annopref.annotation.Preference;

/**
 * Created by tuanchauict on 11/17/16.
 */
@Preference(antiHack = true)
public class Baz {
    @BooleanValue(true)
    @IntValue(1)
    private boolean baz;
}
