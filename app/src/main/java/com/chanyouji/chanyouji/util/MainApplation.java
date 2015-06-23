package com.chanyouji.chanyouji.util;

import android.app.Application;
import android.util.Log;

/**
 * Created by annuo on 2015/6/16.
 */
public class MainApplation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestManger.createInstance(getApplicationContext());
        Log.d("nihao", "nihao");
    }
}
