package com.example.lohit.hw4;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Lohith and Brian on 03,June,2018
 */
public class GeoCalculatorApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
