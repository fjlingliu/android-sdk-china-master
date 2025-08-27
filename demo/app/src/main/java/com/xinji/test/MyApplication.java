package com.xinji.test;

import android.app.Application;

import com.xinji.union.UnionGame;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        XJGame.setDebug(true);
//        XJGame.onCreate(this);
        UnionGame.getInstance().onApplicationCreate(this);
    }
}
