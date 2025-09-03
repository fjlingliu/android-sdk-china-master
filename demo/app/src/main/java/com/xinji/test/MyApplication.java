package com.xinji.test;

import android.app.Application;

import com.xinji.sdk.entity.type.XJPlatformType;
import com.xinji.sdk.util.XJGame;


public class MyApplication extends Application {
    public static XJPlatformType sPlatformType;

    @Override
    public void onCreate() {
        super.onCreate();
        XJGame.setDebugMode(true);
        /**
         *  todo XJPlatformType.XJ_PLATFORM_TYPE_LL 根据运营提供的体系类型设置
         */
        XJGame.onCreate(this, XJPlatformType.XJ_PLATFORM_TYPE_LL);
    }


}
