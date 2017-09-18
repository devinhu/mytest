package com.jingdl.mytest;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends Activity {

    String commond = "am instrument -w -r   -e debug false -e class com.jingdl.mytest.WeixinTest#addGroupFriend com.jingdl.mytest.test/android.support.test.runner.AndroidJUnitRunner";

    private WebView webview;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView)findViewById(R.id.webview);
        webview.setLongClickable(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webview.loadUrl(url);
                return false;
            }
        });

        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAG", "x, y坐标＝" + event.getX() + "f, " + event.getY()+"f");
                        break;
                }
                return false;
            }
        });

        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView)v).getHitTestResult();
                if (null == result){
                    return false;
                }

                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE){
                    return false;
                }

                if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {
                    pasteText(MainActivity.this);
                }

                Log.e("TAG", "type＝" + type + "result=" + result.getExtra());


                // 这里可以拦截很多类型，我们只处理图片类型就可以了
                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // TODO
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项

                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        webview.loadUrl("http://shdwyx.kjdashi.com");



        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPerformClick(517.5208f, 1266.303f);

                onPerformClick(107.90009f, 1428.2186f);

                //发朋友圈
                onPerformClick(954.1166f, 422.7423f);

                onPerformClick(318.7049f, 1078.4008f);

                performLongClick(339.6855f, 1121.3784f);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webview.canGoBack()){
            webview.goBack();
        }else {
            finish();
        }
    }

    /**
     * 模拟屏幕x,y坐标点击
     * @param x
     * @param y
     */
    public void onPerformClick(final float x, final float y){
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
     * 模拟屏幕x,y坐标点击
     * @param x
     * @param y
     */
    public void performLongClick(final float x, final float y){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.performLongClick(x, y);
                copyText("ssssssssss");
            }
        }, 1000);
    }

    /**
     * 模拟键盘点击
     * @param keycode
     */
    public void setKeyPress(int keycode){
        try{
            String keyCommand = "input keyevent " + keycode;
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(keyCommand);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void copyText(String text){
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String pasteText(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionMode actionMode = super.startActionMode(callback);
        return actionMode;
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        ActionMode actionMode = super.startActionMode(callback, type);
        return actionMode;
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        Log.e("TAG", "type＝onActionModeStarted");
        super.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        Log.e("TAG", "type＝onActionModeFinished");
        super.onActionModeFinished(mode);
    }
}
