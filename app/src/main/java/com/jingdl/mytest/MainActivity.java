package com.jingdl.mytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.web.plugin.YouzanBrowser;
import com.youzan.sdk.web.plugin.YouzanWebClient;

import java.io.File;

public class MainActivity extends Activity {

    private YouzanBrowser webview;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview = (YouzanBrowser) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setWebViewClient(new YouzanWebClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }
        });

        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG", "x, y坐标＝" + event.getX() + "f, " + event.getY() + "f");
                        break;
                }
                return false;
            }
        });


        webview.loadUrl("http://shdwyx.kjdashi.com");
        sentUserInfo();

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击在线
                onPerformClick(517.5208f, 1266.303f);

                //点击全选
                onPerformClick(107.90009f, 1428.2186f);

                //发朋友圈
                onPerformClick(954.1166f, 422.7423f);

                sentText();


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }


    public void sentUserInfo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.evaluateJavascript("javascript:document.getElementById('name').value = '18806080003';", null);
                webview.evaluateJavascript("javascript:document.getElementById('pwd').value = 'huxinwu123';", null);
            }
        }, 1000);
    }

    public void sentText() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.evaluateJavascript("javascript:document.getElementById('friends_application').value = 'dmeo12222';", null);

                onPerformClick(358.6679f, 1717.0681f);

            }
        }, 3000);
    }

    /**
     * 模拟屏幕x,y坐标点击
     *
     * @param x
     * @param y
     */
    public void onPerformClick(final float x, final float y) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long downTime = SystemClock.uptimeMillis();
                MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0);
                MotionEvent upEvent = MotionEvent.obtain(downTime, SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0);

                webview.onTouchEvent(downEvent);
                webview.onTouchEvent(upEvent);
                downEvent.recycle();
                upEvent.recycle();
            }
        }, 1000);
    }

    /**
     * 处理WebView上传文件
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (webview.isReceiveFileForWebView(requestCode, data)) {
            return;
        }
    }

}
