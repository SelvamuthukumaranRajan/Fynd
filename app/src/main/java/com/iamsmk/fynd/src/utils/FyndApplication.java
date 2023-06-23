package com.iamsmk.fynd.src.utils;

import android.app.Application;


public class FyndApplication extends Application {


    private static FyndApplication _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static synchronized FyndApplication getInstance() {
        return _instance;
    }

}
