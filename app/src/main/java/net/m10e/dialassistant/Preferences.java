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

    boolean getPlaySound() {
        return preferences.getBoolean(Preferences.PLAY_SOUND, true);
    }

    String getPhoneNumber() {
        return preferences.getString(Preferences.PHONE_NUMBER, "");
    }

    void setPlaySound(Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Preferences.PLAY_SOUND, value);
        editor.apply();
    }

    void setPhoneNumber(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Preferences.PHONE_NUMBER, value);
        editor.apply();
    }
}
