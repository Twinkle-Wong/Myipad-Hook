package com.netspace.hook.Hook;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

public class HookUsageUpload {
    public static void hookUsageUpload(ClassLoader realClassLoader) throws ClassNotFoundException {
        Class<?> imServiceClz = Class.forName("com.netspace.library.im.IMService", true, realClassLoader);
        XposedHelpers.findAndHookMethod(imServiceClz, "reportBasicFields", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.setBooleanField(param.thisObject, "mbBasicStatusReported", true);
                return null;
            }
        });
        XposedHelpers.findAndHookMethod("com.netspace.library.threads.UsageDataUploadThread", realClassLoader, "run", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return null;
            }
        });
        XposedHelpers.findAndHookMethod("com.netspace.library.threads.UsageDataUploadThread", realClassLoader, "startUpload", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return null;
            }
        });
        XposedHelpers.findAndHookMethod("com.netspace.library.struct.UserInfo", realClassLoader, "UserScore", String.class, String.class, XC_MethodReplacement.returnConstant(null));
        XposedHelpers.findAndHookMethod("com.netspace.myipad.im.handles.everyone.Status", realClassLoader, "getStatusJson", XC_MethodReplacement.returnConstant("{}"));
        XposedHelpers.findAndHookMethod("com.netspace.myipad.im.handles.everyone.Status", realClassLoader, "getStatus", XC_MethodReplacement.returnConstant(""));
        XposedHelpers.findAndHookMethod("com.netspace.myipad.im.WmIMThread", realClassLoader, "updateStatus", String.class, String.class, XC_MethodReplacement.returnConstant(null));
    }
}