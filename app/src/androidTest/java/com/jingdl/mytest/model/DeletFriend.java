package com.jingdl.mytest.model;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 清除僵死粉
 * Created by devin on 17/5/11.
 */

public class DeletFriend extends Common {

    private final String TAG = DeletFriend.class.getSimpleName();

    public DeletFriend(UiDevice device) {
        super(device);
    }

    /**
     * 添加附近的人
     */
    public void done(){
        try {
            Log.e(TAG, "点击通讯录");
            mDevice.findObject(new UiSelector().text("通讯录")).click();
            waittime();


            UiScrollable uiScrollable = new UiScrollable(new UiSelector().className(ListView.class));
            UiCollection collection = new UiCollection(new UiSelector().className(ListView.class));
            UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/i_");
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

                    targetObject.click();
                    waittime();


                    Log.e(TAG, "发消息");
                    mDevice.findObject(new UiSelector().text("发消息")).clickAndWaitForNewWindow();
                    waittime();

                    Log.e(TAG, "点击＋号");
                    mDevice.findObject(new UiSelector().resourceId("com.tencent.mm:id/a3g")).click();
                    waittime();

                    Log.e(TAG, "转账");
                    mDevice.findObject(new UiSelector().className(TextView.class).text("转账")).clickAndWaitForNewWindow();
                    waittime();

                    Log.e(TAG, "输入金额");
                    UiObject editText = mDevice.findObject(new UiSelector().className(EditText.class));
                    editText.setText("0.01");
                    waittime();

                    Log.e(TAG, "转账");
                    mDevice.findObject(new UiSelector().className(Button.class).text("转账")).clickAndWaitForNewWindow();
                    waittime();

                    Log.e(TAG, "你不是收款方的好友");
                    if(mDevice.findObject(new UiSelector().text("你不是收款方的好友")).exists()){
                        mDevice.findObject(new UiSelector().text("确定")).click();
                        waittime();

                        mDevice.pressBack();
                        waittime();

                        mDevice.findObject(new UiSelector().className(TextView.class).text("聊天信息")).clickAndWaitForNewWindow();
                        waittime();

                        mDevice.findObject(new UiSelector().resourceId("com.tencent.mm:id/cak")).click();
                        waittime();

                        mDevice.findObject(new UiSelector().className(TextView.class).text("更多")).clickAndWaitForNewWindow();
                        waittime();

                        mDevice.findObject(new UiSelector().className(TextView.class).text("删除")).clickAndWaitForNewWindow();
                        waittime();
                        continue;

                    }else{
                        mDevice.pressBack();
                        waittime();

                        mDevice.pressBack();
                        waittime();

                        mDevice.pressBack();
                        waittime();

                        mDevice.pressBack();
                        waittime();

                        mDevice.pressBack();
                        waittime();

                        mDevice.findObject(new UiSelector().text("通讯录")).click();
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
     * @param uiScrollable
     * @param collection
     * @param uiSelector
     */
    private void onItemClick1(UiScrollable uiScrollable, UiCollection collection, UiSelector uiSelector) {
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
                            onItemClick1(uiScrollable, collection, uiSelector);
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

                    //如果有提示，由于对方的隐私设置，你无法通过群聊将其添加至通讯录。则返回
                    if(mDevice.findObject(new UiSelector().className(TextView.class).text("提示")).exists()){
                        mDevice.pressBack();
                        waittime();
                        continue;
                    }


                    Log.e(TAG, "添加到通讯录");
                    mDevice.findObject(new UiSelector().text("添加到通讯录")).click();
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
                    onItemClick1(uiScrollable, collection, uiSelector);
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
