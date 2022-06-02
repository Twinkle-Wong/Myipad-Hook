package com.netspace.hook;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PreferenceUI extends PreferenceFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent xmlIntent = new Intent();
        ComponentName component = new ComponentName("com.netspace.hook", "com.netspace.hook.MainActivity");
        xmlIntent.setComponent(component);
        addPreferencesFromIntent(xmlIntent);

        //跳转至项目地址
        final Preference github = findPreference("git");
        github.setOnPreferenceClickListener(preference -> {
            Intent url = new Intent();
            url.setAction(Intent.ACTION_VIEW);
            url.setData(Uri.parse("https://github.com/HexoCustomPass/Myipad-Hook"));
            startActivity(url);
            return true;
        });
        //激活超级管理员
        final Preference su = findPreference("su");
        su.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "正在请求激活... 激活成功后设备将重启", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> CommandUtils.runRootCommand("reboot"), 2000);

            return true;
        });
        //跳转至lsp
        final Preference lsp = findPreference("lsp");
        lsp.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "正在跳转至LSP", Toast.LENGTH_SHORT).show();
            StartActivity("org.lsposed.manager","org.lsposed.manager.ui.activity.MainActivity");
            return true;
        });
        //跳转至edxp
        final Preference edxp = findPreference("edxp");
        edxp.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "正在跳转至EDXP", Toast.LENGTH_SHORT).show();
            StartActivity("org.meowcat.edxposed.manager","org.meowcat.edxposed.manager.WelcomeActivity");
            return true;
        });
        //重启应用
        final Preference restartAppPreference = findPreference("restart_app");
        restartAppPreference.setOnPreferenceClickListener(preference -> {
            Toast.makeText(getActivity(), "应用即将重启", Toast.LENGTH_SHORT).show();
            restartApp(getActivity().getApplication(), 100);
            return true;
        });
        //打开终端
        final Preference enter_terminal = findPreference("terminal");
        enter_terminal.setOnPreferenceClickListener(preference -> {
            StartActivity("com.netspace.myipad","jackpal.androidterm.Term");
            return false;
        });
        //一键禁用
        final Preference disable = findPreference("disable");
        disable.setOnPreferenceClickListener(preference -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("你确定要禁用所有软件吗？");
            builder.setMessage("除了少年派和本模块将在确定后被停用，需要手动恢复!");
            builder.setPositiveButton("确定", (dialog, whichButton) -> {
                List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
                for (int i = 0; i < packages.size(); i++) {
                    PackageInfo packageInfo = packages.get(i);
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !"com.netspace.myipad".equals(packageInfo.applicationInfo.packageName) && !"com.netspace.hook".equals(packageInfo.applicationInfo.packageName)) {
                        CommandUtils.runRootCommand("pm disable " + packageInfo.applicationInfo.packageName);
                    }
                }
            });
            builder.show();
            return false;
        });
        //一键禁用
        final Preference enable = findPreference("enable");
        enable.setOnPreferenceClickListener(preference -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("恢复应用");
            builder.setMessage("点击确定恢复所有被停用的应用!");
            builder.setPositiveButton("确定", (dialog, whichButton) -> {
                List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
                for (int i = 0; i < packages.size(); i++) {
                    PackageInfo packageInfo = packages.get(i);
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !"com.netspace.myipad".equals(packageInfo.applicationInfo.packageName) && !"com.netspace.hook".equals(packageInfo.applicationInfo.packageName)) {
                        CommandUtils.runRootCommand("pm enable " + packageInfo.applicationInfo.packageName);
                    }
                }
            });
            builder.show();
            return false;
        });
        //一键卸载
        final Preference uninstall = findPreference("uninstall");
        uninstall.setOnPreferenceClickListener(preference -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("你确定要卸载所有软件吗？");
            builder.setPositiveButton("确定", (dialog, whichButton) -> {
                List<PackageInfo> packages = getActivity().getPackageManager().getInstalledPackages(0);
                for (int i = 0; i < packages.size(); i++) {
                    PackageInfo packageInfo = packages.get(i);
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !"com.netspace.myipad".equals(packageInfo.applicationInfo.packageName)) {
                        CommandUtils.runRootCommand("pm uninstall " + packageInfo.applicationInfo.packageName);
                    }
                }
            });
            builder.show();
            return false;
        });
        //执行命令
        final Preference exec_cmd = findPreference("cmd");
        exec_cmd.setOnPreferenceClickListener(preference -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText editText = new EditText(getActivity());
            editText.setHint("注：所有命令均以root身份执行");
            builder.setView(editText);
            builder.setPositiveButton("执行", (dialog, whichButton) -> {
                try {
                    CommandUtils.runRootCommand(editText.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "输入命令有误或无法执行", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
            return false;
        });
        //清除账号
        final Preference CleanAccountPreference = findPreference("clean_account");
        CleanAccountPreference.setOnPreferenceClickListener(preference -> {
            try {
                CommandUtils.runRootCommand("cd /sdcard/Android/data/");
                new Handler().postDelayed(() -> CommandUtils.runRootCommand("rm -rf .com.netspace.myipad.config"), 200);
                new Handler().postDelayed(() -> Toast.makeText(getActivity(), "账号或许已被清除，少年派会在两秒后重启", Toast.LENGTH_SHORT).show(), 600);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "清除账号失败！", Toast.LENGTH_SHORT).show();
            }
            new Handler().postDelayed(() -> restartApp(getActivity().getApplication(), 300), 2000);
            return false;
        });
        //自定义hook界面
        final CheckBoxPreference CustomUI = (CheckBoxPreference) findPreference("custom");
        CustomUI.setOnPreferenceChangeListener((preference, object) -> {
            if (!((boolean) object)) {
                return true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请根据提示对应输入你要HOOK的活动名");
            final EditText ActivityName = new EditText(getActivity());
            builder.setView(ActivityName);
            builder.setPositiveButton("OK", (dialog, whichButton) -> {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPreferences.edit().putString("custom_activity_name", ActivityName.getText().toString()).apply();
            });
            builder.setCancelable(true);
            builder.show();
            return true;
        });
        //伪装版本信息
        final CheckBoxPreference fakeVersionPreference = (CheckBoxPreference) findPreference("fake_version");
        fakeVersionPreference.setOnPreferenceChangeListener((preference, object) -> {
            if (!((boolean) object)) {
                return true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请根据提示输入所需版本号");
            final EditText FakeVersionName = new EditText(getActivity());
            FakeVersionName.setHint("5.2.3.52444");
            builder.setView(FakeVersionName);
            builder.setPositiveButton("确定", (dialog, whichButton) -> {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPreferences.edit().putString("fake_version_name", FakeVersionName.getText().toString()).apply();
            });
            builder.show();
            return true;
        });
    }
    //重启少年派以实现更改
    public static void restartApp(Application app , long time) {
        new Handler().postDelayed(() -> {
            Intent LaunchIntent = app.getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            app.startActivity(LaunchIntent);
            Process.killProcess(Process.myPid());
        }, time);

    }
    //启动活动
    public void StartActivity(String PackageName, String ActivityName) {
        ComponentName componentName = new ComponentName(PackageName, ActivityName);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setComponent(componentName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "启动异常", Toast.LENGTH_SHORT).show();
        }
    }

}
