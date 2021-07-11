package com.example.rewritedvisachecker;

import android.app.Application;

import com.example.rewritedvisachecker.data.PreferenceManager;

public class App extends Application {

    public static PreferenceManager preferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        preferenceManager = new PreferenceManager(this);
    }

}
