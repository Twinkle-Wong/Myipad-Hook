package com.netspace.hook.Hook;

import android.content.Context;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class HookBreakNetwork {
    public static void hookBreakNetwork(ClassLoader realClassLoader){
        XposedHelpers.findAndHookMethod("com.netspace.library.utilities.Utilities", realClassLoader, "getWifiIP", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return "127.0.0.1";
            }
        });
        XposedHelpers.findAndHookMethod("com.netspace.library.utilities.Utilities", realClassLoader, "getMacAddr", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return "16:08:12:34:56:78";
            }
        });
        XposedHelpers.findAndHookMethod("com.netspace.library.utilities.Utilities", realClassLoader, "getWifiSSID", Context.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return "RYZZX";
            }
        });
        XposedHelpers.findAndHookMethod("com.netspace.library.utilities.Utilities", realClassLoader, "getWifiBSSID", Context.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return "00:00:00:00:00:00";
            }
        });
    }
}
