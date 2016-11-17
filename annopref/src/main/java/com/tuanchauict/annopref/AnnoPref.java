package com.tuanchauict.annopref;

import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class AnnoPref {
    private static SharedPreferences sSharedPreferences;

    public static void init(SharedPreferences sharedPreferences) {
        sSharedPreferences = sharedPreferences;
    }

    public static boolean containsKey(String key) {
        return sSharedPreferences.contains(key);
    }

    public static void putBoolean(String property, boolean value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putBoolean(property, value);
        editor.apply();
    }

    public static boolean getBoolean(String property, boolean defaultValue) {
        return sSharedPreferences.getBoolean(property, defaultValue);
    }

    public static void putInt(String property, int value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putInt(property, value);
        editor.apply();
    }

    public static int getInt(String property, int defaultValue) {
        return sSharedPreferences.getInt(property, defaultValue);
    }

    public static void putLong(String property, long value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putLong(property, value);
        editor.apply();
    }

    public static long getLong(String property, long defaultValue) {
        return sSharedPreferences.getLong(property, defaultValue);
    }

    public static void putFloat(String property, float value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putFloat(property, value);
        editor.apply();
    }

    public static float getFloat(String property, float defaultValue) {
        return sSharedPreferences.getFloat(property, defaultValue);
    }

    public static void putString(String property, String value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putString(property, value);
        editor.apply();
    }

    public static String getString(String property, String defaultValue) {
        return sSharedPreferences.getString(property, defaultValue);
    }

    public static void putStringSet(String property, Set<String> value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.putStringSet(property, value);
        editor.apply();
    }

    public static Set<String> getStringSet(String property, Set<String> defaultValue) {
        return sSharedPreferences.getStringSet(property, defaultValue);
    }

    public static void putIntegerList(String property, List<Integer> arr) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : arr) {
            sb.append(i).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        putString(property, sb.toString());
    }

    public static List<Integer> getIntegerList(String property, List<Integer> defaultValue) {
        if (!containsKey(property)) {
            return defaultValue;
        }
        try {
            String str = getString(property, null);
            if (str == null) return null;
            String[] arr = str.split(",");
            ArrayList<Integer> result = new ArrayList<>(arr.length);
            for (String s : arr) {
                result.add(Integer.parseInt(s));
            }
            return result;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void putStringMap(String property, Map<String, String> value) {
        SharedPreferences.Editor editor = sSharedPreferences.edit();

        if (value != null) {
            JSONObject obj = new JSONObject();
            for (String key : value.keySet()) {
                try {
                    obj.put(String.valueOf(key), value.get(key));
                } catch (JSONException ignored) {
                }
            }
            editor.putString(property, obj.toString());
        } else {
            editor.putString(property, null);
        }

        editor.apply();
    }

    public static Map<String, String> getStringMap(String property, Map<String, String> defaultValue) {
        String str = AnnoPref.sSharedPreferences.getString(property, null);
        if (TextUtils.isEmpty(str))
            return defaultValue;

        try {
            JSONObject obj = new JSONObject(str);
            Iterator<String> iterator = obj.keys();
            Map<String, String> map = new HashMap<>();
            while (iterator.hasNext()) {
                String key = iterator.next();
                try {
                    map.put(key, obj.getString(key));
                } catch (JSONException ignored) {
                }
            }
            return map;
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    public static void putStringList(String property, List<String> value) {
        SharedPreferences.Editor editor = AnnoPref.sSharedPreferences.edit();

        if (value != null) {
            JSONArray arr = new JSONArray();
            for (String s : value) {
                arr.put(s);
            }
            editor.putString(property, arr.toString());
        } else {
            editor.putString(property, null);
        }

        editor.apply();
    }

    public static List<String> getStringList(String property, List<String> defaultValue) {
        String str = sSharedPreferences.getString(property, null);
        if (TextUtils.isEmpty(str))
            return defaultValue;

        try {
            JSONArray arr = new JSONArray(str);
            List<String> slist = new ArrayList<>(arr.length());
            for (int i = 0; i < arr.length(); i++) {
                slist.add(arr.getString(i));
            }
            return slist;
        } catch (JSONException e) {
            return defaultValue;
        }
    }
}
