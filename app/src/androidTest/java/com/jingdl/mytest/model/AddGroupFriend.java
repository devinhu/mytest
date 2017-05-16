package com.jingdl.mytest.model;

import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 添加群组里面的人
 * Created by devin on 17/5/11.
 */

public class AddGroupFriend extends Common {

    private final String TAG = AddGroupFriend.class.getSimpleName();

    public AddGroupFriend(UiDevice device) {
        super(device);
    }

    /**
     * 添加附近的人
     */
    public void done(){
        try {
            mDevice.findObject(new UiSelector().resourceId("com.tencent.mm:id/f_")).click();
            waittime();

            mDevice.findObject(new UiSelector().text("发起群聊")).clickAndWaitForNewWindow();
            waittime();

            mDevice.findObject(new UiSelector().text("选择一个群")).click();
            waittime();

            UiScrollable uiScrollable = new UiScrollable(new UiSelector().className(ListView.class));
            UiCollection collection = new UiCollection(new UiSelector().className(ListView.class));
            UiSelector uiSelector = new UiSelector().resourceId("com.tencent.mm:id/b6e").text("【农场合伙】牦牛001群");
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
            uiScrollable.scrollIntoView(uiSelector);
            mDevice.findObject(uiSelector).click();
            waittime();

            Log.e(TAG, "聊天信息");
            mDevice.findObject(new UiSelector().description("聊天信息")).click();
            waittime();


            UiObject all = mDevice.findObject(new UiSelector().description("查看全部群成员"));
            if(all.exists()){
                UiObject allobject = uiScrollable.getChildByText(new UiSelector().className(TextView.class), "查看全部群成员" , true);
                allobject.clickAndWaitForNewWindow();

                UiScrollable scrollable1 = new UiScrollable(new UiSelector().className(GridView.class));
                UiCollection collection1 = new UiCollection(new UiSelector().className(GridView.class));
                UiSelector uiSelector1 = new UiSelector().resourceId("com.tencent.mm:id/a_1");
                onItemClick1(scrollable1, collection1, uiSelector1);
            }else{
                UiScrollable scrollable1 = new UiScrollable(new UiSelector().className(ListView.class));
                UiCollection collection1 = new UiCollection(new UiSelector().className(ListView.class));
                UiSelector uiSelector1 = new UiSelector().resourceId("com.tencent.mm:id/cak");
                onItemClick1(scrollable1, collection1, uiSelector1);
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
}
