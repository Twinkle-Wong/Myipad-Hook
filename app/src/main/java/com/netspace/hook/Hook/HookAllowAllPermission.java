package com.netspace.hook.Hook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class HookAllowAllPermission {
    public static void hookAllowAllPermission(ClassLoader classLoader) {
        XposedHelpers.findAndHookMethod("com.netspace.library.struct.UserInfo", classLoader, "checkPermission", String.class, XC_MethodReplacement.returnConstant(true));
    }

}
