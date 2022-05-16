package com.netspace.hook.Hook;

import android.content.Context;
import android.content.DialogInterface;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class HookCancelDialog {
    public static void hookCancelDialog(ClassLoader realClassLoader){
        XposedHelpers.findAndHookMethod("com.netspace.library.utilities.Utilities", realClassLoader, "showAlertMessageMustClick",
                Context.class,String.class,String.class, DialogInterface.OnClickListener.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return null;
                    }
                });
        XposedHelpers.findAndHookMethod("android.app.Dialog", realClassLoader, "setCancelable", boolean.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return param.thisObject;
            }
        });
    }
}
