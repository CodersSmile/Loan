package com.example.loan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class RateSPManager {
    private static final int DAYS_DELAY = 259200000;
    private static final int LAUNCH_TIMES = 1;
    private static final String LAUNCH_TIMES_KEY = "launch_times_key";
    private static final String NEVER_ASK_KEY = "never_ask_key";
    private static final String TIME_KEY = "time_key";

    private static SharedPreferences getSP(Context c) {
        return c.getSharedPreferences("preferences", 0);
    }

    public static void neverAskAgain(Context c) {
        getSP(c).edit().putBoolean(NEVER_ASK_KEY, true).apply();
    }

    private static boolean isNeverAskAgain(Context c) {
        return getSP(c).getBoolean(NEVER_ASK_KEY, false);
    }

    public static void updateTime(Context c) {
        getSP(c).edit().putLong(TIME_KEY, System.currentTimeMillis() + 259200000).apply();
    }

    private static boolean isTimeValid(Context c) {
        SharedPreferences sp = getSP(c);
        Long time = Long.valueOf(sp.getLong(TIME_KEY, 0));
        if (time.longValue() == 0) {
            updateTime(c);
            time = Long.valueOf(sp.getLong(TIME_KEY, 0));
        }
        return time.longValue() < System.currentTimeMillis();
    }

    public static void updateLaunchTimes(Context c) {
        getSP(c).edit().putInt(LAUNCH_TIMES_KEY, 0).apply();
    }

    public static void updateLaunchTimes(Context c, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            SharedPreferences sp = getSP(c);
            sp.edit().putInt(LAUNCH_TIMES_KEY, sp.getInt(LAUNCH_TIMES_KEY, 0) + 1).apply();
        }
    }

    private static boolean isLaunchTimesValid(Context c) {
        int launchTimes = getSP(c).getInt(LAUNCH_TIMES_KEY, 0);
        if (launchTimes <= 0 || launchTimes % 1 != 0) {
            return false;
        }
        return true;
    }

    public static boolean canShowDialog(Context c) {
        return !isNeverAskAgain(c) && (isTimeValid(c) || isLaunchTimesValid(c));
    }
}
