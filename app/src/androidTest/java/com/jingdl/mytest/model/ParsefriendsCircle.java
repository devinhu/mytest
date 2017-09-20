package com.jingdl.mytest.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by devin on 17/7/10.
 */

public class ParsefriendsCircle extends Common {

    public ParsefriendsCircle(UiDevice device) {
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


                UiSelector sss = new UiSelector().resourceId("com.tencent.mm:id/agw").textContains("芳方");
                if(uiScrollable.scrollIntoView(sss)){
                    waittime();

                    UiSelector dd = new UiSelector().resourceId("com.tencent.mm:id/cts");
                    String content = mDevice.findObject(dd).getText();

                    mDevice.findObject(new UiSelector().className(View.class).description("图片")).click();
                    waittime();

                    UiObject layout = mDevice.findObject(new UiSelector().resourceId("com.tencent.mm:id/cqc"));
                    UiObject gallery = mDevice.findObject(new UiSelector().className("android.widget.Gallery"));
                    swipeLeftOnGallery(gallery, 0, layout.getChildCount(), content);

                    mDevice.pressBack();
                    mDevice.pressBack();
                }

                //onItemClick(uiScrollable, collection, uiSelector);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void swipeLeftOnGallery(UiObject gallery, int index, int count, String content){
        try {

            longclick(gallery);

            mDevice.findObject(new UiSelector().text("发送给朋友")).click();

            mDevice.findObject(new UiSelector().text("文件传输助手")).clickAndWaitForNewWindow();

            if(index==0){
                UiObject edit = mDevice.findObject(new UiSelector().className(EditText.class).text("给朋友留言"));
                edit.setText(content);
            }
            mDevice.findObject(new UiSelector().text("发送")).click();

            if(index<=count-2){
                index = index + 1;
                gallery.swipeLeft(2);
                swipeLeftOnGallery(gallery, index, count, null);
            }

        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }



    private void ss(){
        ArrayList<Uri> uriArray = new ArrayList<Uri>();//存放图片URI
        ArrayList<Long> origIdArray = new ArrayList<Long>();//存放图片ID

        ContentResolver mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
        String[] projection = {MediaStore.Images.Media._ID};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor c = MediaStore.Images.Media.query(
                mContentResolver,
                uri,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_ADDED + " desc");

        int columnIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

        int i = 0;
        while (c.moveToNext() && i < c.getCount()) {
            long origId = c.getLong(columnIndex);
                uriArray.add(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(origId))
            );

            origIdArray.add(origId);
            c.moveToPosition(i);
            i++;
        }
        c.close();

        for(Uri sss : uriArray){
            Log.e("TAG", "sss.getPath()====" + sss.getPath());
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


                    UiObject quxiao = mDevice.findObject(new UiSelector().resourceId("com.tencent.mm:id/agw").textContains("熊先生港代01"));
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
