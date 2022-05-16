package com.netspace.hook.Hook;

import android.app.Activity;
import android.os.Bundle;


import com.netspace.hook.PreferenceUI;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookPreferencesUI {
    public static void hookPreferencesUI(String activityClzName, ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> activityClz = Class.forName(activityClzName, true, classLoader);
        XposedHelpers.findAndHookMethod(activityClz, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Activity activity = (Activity) param.thisObject;
                activity.getFragmentManager().beginTransaction().add(new PreferenceUI(), "pref").commit();
            }
        });
    }
}
