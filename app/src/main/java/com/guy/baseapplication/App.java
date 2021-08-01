package com.guy.baseapplication;

import android.app.Application;

import com.guy.baseapplication.room.MinDataHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        MinDataHelper.initHelper(this);

    }
}