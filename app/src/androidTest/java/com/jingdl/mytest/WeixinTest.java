package com.jingdl.mytest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jingdl.mytest.model.AddGroupFriend;
import com.jingdl.mytest.model.AddNearbyFriend;
import com.jingdl.mytest.model.AddPraise;
import com.jingdl.mytest.model.DeletFriend;
import com.jingdl.mytest.model.ParsefriendsCircle;
import com.jingdl.mytest.model.VerifyNearbyFriend;
import com.jingdl.mytest.model.VerifyNewFriend;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WeixinTest {

    private UiDevice mDevice;

    @Test
    public void addNearbyFriends() throws Exception {
        AddNearbyFriend model = new AddNearbyFriend(mDevice);
        model.done();
    }

    @Test
    public void VerifyNearbyFriend() throws Exception {
        VerifyNearbyFriend model = new VerifyNearbyFriend(mDevice);
        model.done();
    }

    @Test
    public void VerifyNewFriend() throws Exception {
        VerifyNewFriend model = new VerifyNewFriend(mDevice);
        model.done();
    }

    @Test
    public void addpraise() throws Exception {
        AddPraise model = new AddPraise(mDevice);
        model.done();
    }

    @Test
    public void addGroupFriend() throws Exception {
        AddGroupFriend model = new AddGroupFriend(mDevice);
        model.done();
    }

    @Test
    public void deletFriend() throws Exception {
        DeletFriend model = new DeletFriend(mDevice);
        model.done();
    }

    @Test
    public void parsefriendsCircle() throws Exception {
        ParsefriendsCircle model = new ParsefriendsCircle(mDevice);
        model.done();
    }

    @After
    public void tearDown() throws Exception {
        mDevice.pressHome();
    }

    @Before
    public void setUp() throws Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
    }

}
