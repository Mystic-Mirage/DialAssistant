package net.m10e.dialassistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class Preferences {
    private final SharedPreferences preferences;
    static final String PHONE_NUMBER = "phone_number";
    static final String PLAY_SOUND = "play_sound";

    Preferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    void putBoolean(String name, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    void putString(String name, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.apply();
    }
}
