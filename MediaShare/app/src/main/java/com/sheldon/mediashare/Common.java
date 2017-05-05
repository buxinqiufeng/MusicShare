package com.sheldon.mediashare;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by sheldon on 2017/4/15.
 */

public class Common {
    public static final String ACTION_START_ALLINONE = "android.sheldon.mediashare.action.START_ALLINONE";
    public static final int MIN_MUSIC_SIZE = 1000*1000;//设置最小歌曲大小为1M.

    public static final boolean MOCK_MODE = true;

    public static Intent generateIntentForService(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        Intent eintent = new Intent(Common.getExplicitIntent(context,intent));
        return eintent;
    }

    private static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}
