package com.netspace.hook.Hook;

import android.content.pm.PackageInfo;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookVersion {
    public static void hookVersionName(ClassLoader classLoader, final String fakeVersionName) {
        XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, "getPackageInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                PackageInfo pi = (PackageInfo) param.getResult();
                if (pi.packageName.contains("com.netspace")) {
                    pi.versionName = fakeVersionName;
                }
            }
        });
    }
}
