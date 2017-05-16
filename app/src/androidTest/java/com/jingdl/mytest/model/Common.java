package com.jingdl.mytest.model;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.util.Log;
import java.util.Random;

/**
 * Created by devin on 17/5/11.
 */

public class Common {

    protected UiDevice mDevice;
    protected final String TAG = Common.class.getSimpleName();
    protected static final String BASIC_SAMPLE_PACKAGE = "com.tencent.mm";

    /**
     * 构造方法，启动微信
     * @param device
     */
    public Common(UiDevice device) {
        Log.e(TAG, "启动微信");
        this.mDevice = device;
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        waittime();
    }

    /**
     * 等待方法
     */
    public void waittime(){
        int max=2000;
        int min=1000;
        Random random = new Random();
        int time = random.nextInt(max)%(max-min+1) + min;
        waittime(time);
    }

    /**
     * 等待方法
     * @param time
     */
    public void waittime(long time){
        Log.e(TAG, "waittime="+time);
        mDevice.wait(Until.hasObject(By.pkg(mDevice.getLauncherPackageName()).depth(0)), time);
    }
}
