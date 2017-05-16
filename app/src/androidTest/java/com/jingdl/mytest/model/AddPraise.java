package com.jingdl.mytest.model;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 点赞
 * Created by devin on 17/5/11.
 */

public class AddPraise extends Common {

    public AddPraise(UiDevice device) {
        super(device);
    }

    /**
     * 添加附近的人
     */
    public void done(){
        try {
            Log.e(TAG, "点击发现");
            mDevice.findObject(new UiSelector().text("发现")).click();
            waittime();

            Log.e(TAG, "点击朋友圈");
            if(mDevice.findObject(new UiSelector().text("朋友圈")).clickAndWaitForNewWindow()){
                waittime();

                UiScrollable uiScrollable = new UiScrollable(new UiSelector().className(ListView.class));
                UiCollection collection = new UiCollection(new UiSelector().className(ListView.class));
                UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/clj");
                onItemClick(uiScrollable, collection, uiSelector);
            }
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

                    UiObject object = collection.getChildByInstance(uiSelector, index);
                    if(!object.exists()){
                        waittime();
                        if(uiScrollable.scrollForward()){
                            waittime();
                            onItemClick(uiScrollable, collection, uiSelector);
                            return;
                        }
                    }

                    object.click();
                    waittime();

                    UiObject quxiao = mDevice.findObject(new UiSelector().className(TextView.class).text("取消"));
                    if(quxiao.exists()){
                        collection.getChildByInstance(uiSelector, index).click();
                        waittime();
                    }else{
                        mDevice.findObject(new UiSelector().className(TextView.class).text("赞")).click();
                        waittime();
                    }
                }
            }

            //如果没有或者最后一条则滑动下一页
            if(size == 0 || index==size){
                waittime();
                if(uiScrollable.scrollForward()){
                    waittime();
                    onItemClick(uiScrollable, collection, uiSelector);
                    return;
                }
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

}
