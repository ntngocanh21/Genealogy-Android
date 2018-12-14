package com.senior.project.genealogy.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.senior.project.genealogy.app.GenealogyApplication;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static long sLastClickTime = 0;

    public static void hiddenKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        /*
         *  English uppercase characters (A – Z)
         *  English lowercase characters (a – z)
         *  Base 10 digits (0 – 9)
         *  Non-alphanumeric (For example: !, $, #, or %)
         *  Unicode characters
         */
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,36}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidUsername(String username) {
        /*
         *  English uppercase characters (A – Z)
         *  English lowercase characters (a – z)
         *  Base 10 digits (0 – 9)
         *  Length >=6
         *  Unicode characters
         */
        Pattern pattern;
        Matcher matcher;
        final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{6,15}$";
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - sLastClickTime < Constants.DOUBLE_CLICK_TIME_DELTA) {
            sLastClickTime = clickTime;
            return true;
        }
        sLastClickTime = clickTime;
        return false;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        return Settings.Secure.getString(GenealogyApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }

    public static String convertStringToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}
