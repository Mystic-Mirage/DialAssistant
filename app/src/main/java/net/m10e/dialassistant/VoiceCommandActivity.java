package net.m10e.dialassistant;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class VoiceCommandActivity extends Activity {
    private final String TAG = getClass().getSimpleName();
    private Preferences preferences;
    private final static Globals globals = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        preferences = new Preferences(this);

        actionCall();
        finish();
    }

    private void actionCall() {
        if (!globals.getDefaultChosen()) {
            globals.setDefaultChosen(true);
            return;
        }

        String phoneNumber = getNumber();

        if (phoneNumber.isEmpty()) {
            playSound(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE);
        } else {
            playSound(ToneGenerator.TONE_CDMA_ALERT_NETWORK_LITE);

            Uri uri = Uri.fromParts("tel", phoneNumber, null);

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(uri);

            Log.i(TAG, Uri.decode(uri.toString()));
            startActivity(intent);
        }
    }

    private String getNumber() {
        return preferences.getString(Preferences.PHONE_NUMBER, "");
    }

    private void playSound(int tone) {
        if (preferences.getBoolean(Preferences.PLAY_SOUND, true)) {
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen.startTone(tone, 400);
            SystemClock.sleep(1000);
        }
    }
}
