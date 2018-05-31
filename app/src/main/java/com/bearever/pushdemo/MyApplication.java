package com.bearever.pushdemo;

import android.app.Application;

import com.bearever.push.PushTargetManager;

/**
 * Created by luoming on 2018/5/28.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PushTargetManager.getInstance().init(this);
    }
}
