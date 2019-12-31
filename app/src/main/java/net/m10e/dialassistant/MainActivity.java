package net.m10e.dialassistant;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private LinearLayout grantPermissionsLayout;
    private LinearLayout chooseDefault;
    private LinearLayout grantPermissionsLayoutXiaomi;
    private EditText phoneNumber;
    private Preferences preferences;
    private Switch playSound;
    private DefaultChooser defaultChooser;
    private Globals globals = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = new Preferences(this);

        setContentView(R.layout.activity_main);
        grantPermissionsLayout = findViewById(R.id.grant_permissions);
        chooseDefault = findViewById(R.id.choose_default);
        grantPermissionsLayoutXiaomi = findViewById(R.id.grant_permissions_xiaomi);

        phoneNumber = findViewById(R.id.phone_number);
        phoneNumber.setText(preferences.getString(Preferences.PHONE_NUMBER, ""));

        playSound = findViewById(R.id.play_sound);
        playSound.setChecked(preferences.getBoolean(Preferences.PLAY_SOUND, true));

        defaultChooser = new DefaultChooser(this);

        setupPhoneNumberWidget();
        checkVendor();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPermissions();
        checkDefault();
    }

    public void onClickRequestPermissions(View v)
    {
        requestPermissions();
    }

    public void onClickEditNumber(View v) {
        phoneNumber.setEnabled(true);
        phoneNumber.requestFocus();
        phoneNumber.setSelection(phoneNumber.getText().length());

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        v.setVisibility(View.GONE);

        Button done = findViewById(R.id.done_number);
        done.setVisibility(View.VISIBLE);
    }

    public void onClickDoneNumber(View v) {
        phoneNumber.setEnabled(false);

        v.setVisibility(View.GONE);

        Button edit = findViewById(R.id.edit_number);
        edit.setVisibility(View.VISIBLE);
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    private void checkPermissions() {
        boolean permissionGranted = isPermissionGranted();
        Log.i(TAG, "permission granted: " + permissionGranted);

        if (permissionGranted) {
            grantPermissionsLayout.setVisibility(View.GONE);
        } else {
            grantPermissionsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void checkDefault() {
        boolean isDefault = defaultChooser.isDefault();
        Log.i(TAG, "default: " + isDefault);

        if (isDefault) {
            chooseDefault.setVisibility(View.GONE);
        } else {
            chooseDefault.setVisibility(View.VISIBLE);
        }
    }

    private void checkVendor() {
        String manufacturer = Build.MANUFACTURER;
        Log.i(TAG, "manufacturer: " + manufacturer);

        if (manufacturer.equalsIgnoreCase("xiaomi")) {
            grantPermissionsLayoutXiaomi.setVisibility(View.VISIBLE);
        } else {
            grantPermissionsLayoutXiaomi.setVisibility(View.GONE);
        }
    }

    private void requestPermissions() {
        Log.i(TAG, "requesting permission");

        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 0);
        }
    }

    private void setupPhoneNumberWidget() {
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                preferences.putString(Preferences.PHONE_NUMBER, s.toString());
            }
        });
    }

    public void onClickPlaySound(View v) {
        preferences.putBoolean(Preferences.PLAY_SOUND, playSound.isChecked());
    }

    public void onClickVendor(View v) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        intent.putExtra("extra_pkgname", getPackageName());
        startActivity(intent);
    }

    public void onClickDefault(View v) {
        globals.setDefaultChosen(false);
        defaultChooser.showDefaultChooser();
    }
}
