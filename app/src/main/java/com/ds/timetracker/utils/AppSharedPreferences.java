package com.ds.timetracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class AppSharedPreferences {

    private static AppSharedPreferences instance;

    private SharedPreferences mPrefs;

    private AppSharedPreferences(Context context) {
        mPrefs = context.getSharedPreferences("TimeTracker", Context.MODE_PRIVATE);
    }

    public static AppSharedPreferences getInstance(Context context) {
        if (instance == null) instance = new AppSharedPreferences(context);
        return instance;
    }

    public void setLocale(String locale){
        mPrefs.edit()
                .putString("locale",locale)
                .apply();
    }

    public String getLocale(){
        return mPrefs.getString("locale", "en");
    }

    public void setClockInterval(int seconds){
        mPrefs.edit()
                .putInt("seconds", seconds)
                .apply();
    }

    public int getClockSeconds(){
        return mPrefs.getInt("seconds", 1);
    }
}
