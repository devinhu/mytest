/*
    ShengDao Android Client, BaseApplication
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.jingdl.mytest;

import android.app.Application;
import com.youzan.sdk.YouzanSDK;


/**
 * [系统Application类，设置全局变量以及初始化组件]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-17
 **/
public class BaseApplication extends Application {

    private final String tag = BaseApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化有赞SDK
        YouzanSDK.init(this, "demo");
    }
}
