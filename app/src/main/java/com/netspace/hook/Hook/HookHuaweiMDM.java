package com.netspace.hook.Hook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class HookHuaweiMDM {
    public static void hookHuaweiMDM(ClassLoader realClassLoader){
        XposedHelpers.findAndHookMethod("com.netspace.myipad.MyiPadApplication", realClassLoader, "isActive", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return true;
            }
        });
    }
}
