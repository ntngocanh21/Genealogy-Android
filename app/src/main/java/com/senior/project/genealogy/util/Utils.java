package com.senior.project.genealogy.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lorence on 15/11/2018.
 *
 */

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
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";
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
        final String USERNAME_PATTERN = "^[a-zA-Z0-9._-]{6,}$";
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

}
