package net.m10e.dialassistant;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

class DefaultChooser {
    private Context context;
    private ComponentName componentName;
    private String packageName;
    private PackageManager packageManager;
    private static final Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);

    DefaultChooser(Context context) {
        this.context = context;
        componentName = new ComponentName(context, FakeVoiceCommandActivity.class);
        packageName = context.getPackageName();
        packageManager = context.getPackageManager();
    }

    private String getDefault() {
        ResolveInfo rInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return rInfo.activityInfo.packageName;
    }

    boolean isDefault() {
        return getDefault().equals(packageName);
    }

    void showDefaultChooser() {
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        context.startActivity(intent);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
    }
}
