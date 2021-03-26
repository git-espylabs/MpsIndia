package com.espy.mps.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public abstract class BasePreference {

    /**
     * Write setting.
     */

    protected static void writeSetting(Context context, String prefName, String keyName, String keyValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        if (keyValue == null)
            editor.remove(keyName);
        else
            editor.putString(keyName, keyValue);

        editor.apply();
    }

    protected static void writeSetting(Context context, String prefName, String keyName, Integer keyValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt(keyName, keyValue);
        editor.apply();
    }

    protected static void writeSettingLong(Context context, String prefName, String keyName, long keyValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putLong(keyName, keyValue);
        editor.apply();
    }

    protected static void writeSetting(Context context, String prefName, String keyName, boolean keyValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(keyName, keyValue);
        editor.apply();
    }

    protected static void writeSetting(Context context, String prefName, String keyName, Object obj) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        String json = gson.toJson(obj);
        editor.putString(keyName,json);
        editor.apply();
    }


    /**
     * Gets the setting.
     */
    protected static String getSetting(Context context, String prefName, String keyName, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        return settings.getString(keyName, defaultValue);
    }

    protected static Integer getSetting(Context context, String prefName, String keyName, Integer defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        return settings.getInt(keyName, defaultValue);
    }

    protected static long getSettingLong(Context context, String prefName, String keyName, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        return settings.getLong(keyName, defaultValue);
    }

    protected static boolean getSetting(Context context, String prefName, String keyName, boolean defaultValue) {
        if (context != null) {
            SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

            return settings.getBoolean(keyName, defaultValue);
        } else {
            return false;
        }
    }

    protected static HashMap<String, String> getSettingMap(Context context, String prefName, String keyName, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = settings.getString(keyName,"");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> obj = gson.fromJson(json, type);
        return obj;
    }

}
