package com.khasanah.features.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.khasanah.features.utils.Constant.PREFS_KEY;

public class PrefsUtil {

    public static void savePrefs(Context context,String serializedData){
        SharedPreferences preferencesReader =
                context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesReader.edit();
        editor.putString(PREFS_KEY, serializedData);
        editor.commit();
    }

    public static String getPrefs(Context context){
        SharedPreferences preferencesReader =
                context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        String serializedDataFromPreference = preferencesReader.getString(PREFS_KEY, null);
        return serializedDataFromPreference;
    }
}
