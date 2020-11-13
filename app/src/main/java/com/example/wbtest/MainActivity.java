package com.example.wbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView wb;
    private boolean mClearHistory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        wb= (WebView)findViewById(R.id.wv_webview);
        wb.getSettings().setJavaScriptEnabled(true);//设置可以响应JS

        wb.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
                        wb.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(MailTo.isMailTo(url)){
                    MailTo mt = MailTo.parse(url);
                    // build intent and start new activity
                    return true;
                }
                else {

                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                if (mClearHistory)
                {
                    mClearHistory = false;
                    wb.clearHistory();
                }
                super.onPageFinished(view, url);
            }

        });

        wb.addJavascriptInterface(new AndroidtoJs(this), "qxb");

         wb.loadUrl("http://10.0.0.106:8082/#/");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if(event.getAction() == KeyEvent.ACTION_DOWN) {
            switch(keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if(wb.canGoBack()){
                        wb.goBack();
                        return true;
                    }
                    break;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @JavascriptInterface
    public void reloadSite(final String url){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mClearHistory = true;
                wb.loadUrl(url);
            }
        });
    }
}
