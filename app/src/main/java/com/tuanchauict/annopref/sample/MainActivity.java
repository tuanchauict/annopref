package com.tuanchauict.annopref.sample;

import android.app.Activity;
import android.os.Bundle;

import com.tuanchauict.annopref.AnnoPref;
import com.tuanchauict.annopref.sample.pref.BazPref;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnnoPref.init(getPreferences(MODE_PRIVATE));
    }
}
