package com.jingdl.mytest.model;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 添加附近的人
 * Created by devin on 17/5/11.
 */

public class AddNearbyFriend extends Common {

    private final String TAG = AddNearbyFriend.class.getSimpleName();

    public AddNearbyFriend(UiDevice device) {
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

            Log.e(TAG, "点击附近的人");
            if(mDevice.findObject(new UiSelector().text("附近的人")).clickAndWaitForNewWindow()){

                UiObject querybutton = mDevice.findObject(new UiSelector().text("查看附近的人"));
                if(querybutton.exists()){
                    Log.e(TAG, "查看附近的人");
                    querybutton.click();
                    waittime();
                }

                if(!waitForLoading()){

                    Log.e(TAG, "更多");
                    mDevice.findObject(new UiSelector().description("更多")).click();
                    waittime();

                    Log.e(TAG, "只看女生");
                    mDevice.findObject(new UiSelector().text("只看女生")).click();
                    waittime(5000);

                    UiScrollable uiScrollable = new UiScrollable(new UiSelector().className(ListView.class));
                    UiCollection collection = new UiCollection(new UiSelector().className(ListView.class));
                    UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/am3");
                    onItemClick(uiScrollable, collection, uiSelector);

                }
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

                    UiObject targetObject = collection.getChildByInstance(uiSelector, index);
                    if(!targetObject.exists()){
                        waittime();
                        if(uiScrollable.scrollForward()){
                            waittime();
                            onItemClick(uiScrollable, collection, uiSelector);
                            return;
                        }
                    }

                    targetObject.click();
                    waittime();


                    //如果已经是好友啦，这时候不是打招呼而是发消息
                    if(mDevice.findObject(new UiSelector().text("发消息")).exists()){
                        mDevice.pressBack();
                        waittime();
                        continue;
                    }

                    Log.e(TAG, "打招呼");
                    mDevice.findObject(new UiSelector().text("打招呼")).click();
                    waittime();

                    Log.e(TAG, "输入打招呼文字");
                    UiObject editText = mDevice.findObject(new UiSelector().className(EditText.class));
                    editText.clearTextField();
                    editText.setText("你好");
                    waittime();

                    //点击发送
                    Log.e(TAG, "发送");
                    mDevice.findObject(new UiSelector().text("发送")).clickAndWaitForNewWindow();
                    waittime();

                    //从详细资料页面返回
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
                    return;
                }
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    /**
     *
     * @return
     */
    private boolean waitForLoading(){
        UiObject loading = mDevice.findObject(new UiSelector().text("正在确定你的位置"));
        if(loading.exists()){
            Log.e(TAG, "正在确定你的位置");
            waittime();
        }else{
            Log.e(TAG, "已经确定你的位置");
            waittime();
            return false;
        }
        return waitForLoading();
    }
}
