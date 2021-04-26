package com.example.rewritedvisachecker.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class PreferenceManager {

    private final SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final Context context;

    public PreferenceManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("com.example.myprofile", Context.MODE_PRIVATE);

    }

    public void setIdU() {
        String uuid = UUID.randomUUID().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("idgenerated", true);
        editor.putString("uniqueId", uuid);
        editor.commit();

    }

    public String getIdU() {
        context.getSharedPreferences(
                "com.example.myprofile", Context.MODE_PRIVATE);
        return prefs.getString("uniqueId", "");
    }

    public boolean getIdGenerator() {
        context.getSharedPreferences(
                "com.example.myprofile", Context.MODE_PRIVATE);
        return prefs.getBoolean("idgenerated", false);
    }

}
