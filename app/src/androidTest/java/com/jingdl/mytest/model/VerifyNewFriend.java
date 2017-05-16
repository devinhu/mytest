package com.jingdl.mytest.model;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 添加新的朋友
 * Created by devin on 17/5/11.
 */

public class VerifyNewFriend extends Common {

    private final String TAG = VerifyNewFriend.class.getSimpleName();

    public VerifyNewFriend(UiDevice device) {
        super(device);
    }

    /**
     * 添加附近的人
     */
    public void done(){
        try {
            mDevice.findObject(new UiSelector().text("通讯录")).click();
            waittime();

            mDevice.findObject(new UiSelector().text("新的朋友")).click();
            waittime();

            UiScrollable uiScrollable = new UiScrollable(new UiSelector().className(ListView.class));
            UiCollection collection = new UiCollection(new UiSelector().className(ListView.class));
            UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/aw0");
            onItemClick(uiScrollable, collection, uiSelector);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     *
     * @param uiScrollable
     * @param collection
     * @param uiSelector
     */
    private void onItemClick(UiScrollable uiScrollable, UiCollection collection, UiSelector uiSelector) {
        try {
            int index = 0;
            int size = collection.getChildCount(uiSelector);
            if(size > 0){
                for(index=0; index<size ;index++){

                    UiObject targetObject = collection.getChildByInstance(uiSelector, index);
                    if(!targetObject.exists()){
                        waittime();
                        if(uiScrollable.scrollForward()){
                            waittime();
                            onItemClick(uiScrollable, collection, uiSelector);
                            return;
                        }
                    }

                    targetObject.clickAndWaitForNewWindow();
                    waittime();


                    UiObject yanzheng = mDevice.findObject(new UiSelector().text("去验证"));
                    if(yanzheng.exists()){
                        yanzheng.clickAndWaitForNewWindow();
                    }


                    UiObject pass =  mDevice.findObject(new UiSelector().text("通过验证"));
                    if(pass.exists()){
                        pass.clickAndWaitForNewWindow();
                        waittime();
                    }


                    mDevice.findObject(new UiSelector().text("完成")).clickAndWaitForNewWindow();
                    waittime();


                    if(mDevice.findObject(new UiSelector().text("朋友验证")).exists()){
                        mDevice.pressBack();
                        waittime();
                    }

                    if(mDevice.findObject(new UiSelector().text("详细资料")).exists()){
                        mDevice.pressBack();
                        waittime();
                        continue;
                    }
                }
            }

            //如果没有或者最后一条则滑动下一页
            if(size == 0 || index==size){
                waittime();
                if(uiScrollable.scrollForward()){
                    waittime();
                    onItemClick(uiScrollable, collection, uiSelector);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }
}
