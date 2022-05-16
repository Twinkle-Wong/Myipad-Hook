package com.netspace.hook.Hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookLenovoMDM {
    public static void hookLenovoMDM(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.netspace.myipad.MyiPadApplication", classLoader, "onCreate", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.setBooleanField(param.thisObject, "mbNeedMDM", false);
            }
        });
    }
}
