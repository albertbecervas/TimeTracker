package com.ds.timetracker.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AppSharedPreferences {

    private static AppSharedPreferences instance = null;

    private SharedPreferences mPrefs;

    private AppSharedPreferences(Context context) {
        mPrefs = context.getSharedPreferences("SHARED", Context.MODE_PRIVATE);
    }

    public static AppSharedPreferences getInstance(Context context) {
        if (instance == null) return new AppSharedPreferences(context);
        return instance;
    }

    void saveItems(ArrayList<Object> items) {
        String gson = new Gson().toJson(items);
        mPrefs.edit()
                .putString("gson", gson)
                .apply();
    }

    public ArrayList<Object> getItems() {
        String gsonString = mPrefs.getString("gson", null);
        ArrayList<Object> list;
        list = new Gson().fromJson(gsonString, new TypeToken<ArrayList<Object>>() {
        }.getType());
        if (list == null) return new ArrayList<>();
        return list;
    }
}
