package com.ds.timetracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

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
//
//    public void saveItems(ArrayList<Item> items) {
//
////        RuntimeTypeAdapterFactory<Item> typeAdapterFactory = RuntimeTypeAdapterFactory
////                .of(Item.class,"type")
////                .registerSubtype(Project.class, "project")
////                .registerSubtype(Task.class,"task");
////
////        final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
//
//        String json = new Gson().toJson(items);
//
//        mPrefs.edit()
//                .putString("items", json)
//                .apply();
//    }
//
//    public ArrayList<Item> getItems() {
//        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
//
////        RuntimeTypeAdapterFactory<Item> typeAdapterFactory = RuntimeTypeAdapterFactory
////                .of(Item.class,"type")
////                .registerSubtype(Project.class, "project")
////                .registerSubtype(Task.class,"task");
//
//        String json = mPrefs.getString("items", "");
//
//        if (json.equals("")) return new ArrayList<>();
//
////        final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeAdapterFactory).create();
//
//        return new Gson().fromJson(json, type);
//    }
//
//    public void resetItemsTree() {
//        mPrefs.edit()
//                .clear()
//                .apply();
//    }
}
