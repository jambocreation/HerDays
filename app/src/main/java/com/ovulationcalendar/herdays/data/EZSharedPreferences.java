package com.ovulationcalendar.herdays.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mleano on 3/10/2016.
 */
public class EZSharedPreferences {

    private static String TAG = EZSharedPreferences.class.getSimpleName();

    private static final String USER_PREFERENCES = "Her Days";

    public static final String KEY_SETUP = "Setup";
    public static final String KEY_DATE = "LastMonthPeriod";
    public static final String KEY_CYCLE = "CycleLength";
    public static final String KEY_MENS = "MenstrualLength";
    public static final String KEY_TIPS = "Tips";

    public static final String NOTIFY_BEFORE_PERIOD = "BeforePeriod";
    public static final String NOTIFY_ON_PERIOD = "OnPeriod";
    public static final String NOTIFY_FERTILE = "Fertile";
    public static final String NOTIFY_COUNT = "NotifyCount";

    public static SharedPreferences getSharedPref(Context ctx) {
        return ctx.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    //====================================
    // G E T T E R
    //====================================

    public static int getSetup(Context ctx) {
        return getSharedPref(ctx).getInt(KEY_SETUP, 0);
    }

    public static String getLastMonth(Context ctx) {
        return getSharedPref(ctx).getString(KEY_DATE, null);
    }

    public static String getCycle(Context ctx) {
        return getSharedPref(ctx).getString(KEY_CYCLE, null);
    }

    public static boolean isTipShown(Context ctx) {
        return getSharedPref(ctx).getBoolean(KEY_TIPS, false);
    }

    public static String getBeforePeriod(Context ctx) {
        return getSharedPref(ctx).getString(NOTIFY_BEFORE_PERIOD, null);
    }

    public static String getOnPeriod(Context ctx) {
        return getSharedPref(ctx).getString(NOTIFY_ON_PERIOD, null);
    }

    public static String getNotifyFertile(Context ctx) {
        return getSharedPref(ctx).getString(NOTIFY_FERTILE, null);
    }

    public static int getNotifyCount(Context ctx) {
        return getSharedPref(ctx).getInt(NOTIFY_COUNT, 0);
    }
    //====================================
    // S E T T E R
    //====================================

    public static void setInformation(Context ctx, HashMap<String, String> map) {
        SharedPreferences.Editor editor = getSharedPref(ctx).edit();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
            Log.i(TAG, entry.getKey() + " = " + entry.getValue());
        }

        editor.putInt(KEY_SETUP, 1);

        editor.apply();
    }

    public static void setBeforePeriod(Context ctx, String date) {
        SharedPreferences.Editor edit = getSharedPref(ctx).edit();
        edit.putString(NOTIFY_BEFORE_PERIOD, date);
        edit.apply();
    }

    public static void setOnPeriod(Context ctx, String date) {
        SharedPreferences.Editor edit = getSharedPref(ctx).edit();
        edit.putString(NOTIFY_ON_PERIOD, date);
        edit.apply();
    }

    public static void setFertile(Context ctx, String date) {
        SharedPreferences.Editor edit = getSharedPref(ctx).edit();
        edit.putString(NOTIFY_FERTILE, date);
        edit.apply();
    }

    public static void setNotifyCount(Context ctx, int count) {
        SharedPreferences.Editor edit = getSharedPref(ctx).edit();
        edit.putInt(NOTIFY_COUNT, count);
        edit.apply();
    }

    public static void setTipShown(Context ctx, boolean bool) {
        SharedPreferences.Editor edit = getSharedPref(ctx).edit();
        edit.putBoolean(KEY_TIPS, bool);
        edit.apply();
    }

}
