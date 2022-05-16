package com.netspace.hook.Hook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class HookDisableUpdate {
    public static void hookDisableUpdate(ClassLoader realClassLoader) {
        XposedHelpers.findAndHookMethod("com.netspace.library.utilities.MyiUpdate2", realClassLoader, "run", XC_MethodReplacement.returnConstant(null));
    }
}
