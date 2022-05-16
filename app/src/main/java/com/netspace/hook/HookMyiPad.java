package com.netspace.hook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.netspace.hook.Hook.HookAllowAllPermission;
import com.netspace.hook.Hook.HookBreakNetwork;
import com.netspace.hook.Hook.HookCancelDialog;
import com.netspace.hook.Hook.HookDisableUpdate;
import com.netspace.hook.Hook.HookHardware;
import com.netspace.hook.Hook.HookHuaweiMDM;
import com.netspace.hook.Hook.HookLenovoMDM;
import com.netspace.hook.Hook.HookLockScreen;
import com.netspace.hook.Hook.HookOuterPDF;
import com.netspace.hook.Hook.HookPreferencesUI;
import com.netspace.hook.Hook.HookSSL;
import com.netspace.hook.Hook.HookUsageUpload;
import com.netspace.hook.Hook.HookVersion;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class HookMyiPad implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Exception {
        if (!lpparam.packageName.contains("com.netspace")) {
            return;
        }
        XposedHelpers.findAndHookMethod("android.app.Instrumentation", lpparam.classLoader, "newApplication", ClassLoader.class, String.class, Context.class, new AppCreateHookCallback());
    }

    public class AppCreateHookCallback extends XC_MethodHook {
        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            Application realApplication = (Application) param.getResult();
            ClassLoader realClassLoader = realApplication.getClassLoader();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(realApplication);
            String appName = realApplication.getClass().getName();
            if (!appName.contains("com.netspace")) {
                return;
            }
            HookHuaweiMDM.hookHuaweiMDM(realClassLoader);
            HookLenovoMDM.hookLenovoMDM(realClassLoader);
            if (sharedPreferences.getBoolean("fake_version", true)) {
                HookVersion.hookVersionName(realClassLoader,sharedPreferences.getString("fake_version_name", "5.2.352444"));
            }
            if (sharedPreferences.getBoolean("fake_hardware", true)) {
                HookHardware.hookHardware(realClassLoader);
            }
            //hook UI 选项
            HookPreferencesUI.hookPreferencesUI("com.netspace.library.activity.WifiConfigActivity", realClassLoader);
            if (sharedPreferences.getBoolean("myipad_settings", true)) {
                HookPreferencesUI.hookPreferencesUI("com.netspace.myipad.SettingsActivity",realClassLoader);
            }
            if (sharedPreferences.getBoolean("myipad_calendar", false)) {
                HookPreferencesUI.hookPreferencesUI("com.netspace.myipad.plugins.schedule.ui.ScheduleViewActivity",realClassLoader);
            }
            if (sharedPreferences.getBoolean("custom", false)) {
                HookPreferencesUI.hookPreferencesUI(sharedPreferences.getString("custom_activity_name",sharedPreferences.getString("custom_activity_name","")),realClassLoader);
            }
            //基础功能
            if (sharedPreferences.getBoolean("disable_auto_update", true)) {
                HookDisableUpdate.hookDisableUpdate(realClassLoader);
            }
            if (sharedPreferences.getBoolean("break_network", true)) {
                HookBreakNetwork.hookBreakNetwork(realClassLoader);
            }
            if (sharedPreferences.getBoolean("cancel_dialog", false)) {
                HookCancelDialog.hookCancelDialog(realClassLoader);
            }
            if (sharedPreferences.getBoolean("outer_pdf", false)) {
                HookOuterPDF.hookOuterPDF(realClassLoader,realApplication);
            }
            //高级功能
            if (sharedPreferences.getBoolean("disable_lock_screen", true)) {
                HookLockScreen.hookLockScreen(realClassLoader);
            }
            if (sharedPreferences.getBoolean("disable_ssl_pinning", true)) {
                HookSSL.hookSSL(realClassLoader);
            }
            if (sharedPreferences.getBoolean("disable_usage_upload", true)) {
                HookUsageUpload.hookUsageUpload(realClassLoader);
            }
            if (sharedPreferences.getBoolean("allow_all_permissions", true)) {
                HookAllowAllPermission.hookAllowAllPermission(realClassLoader);
            }
        }
    }
}
