package com.example.wbtest;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class AndroidtoJs extends Object {

    private MainActivity context;

    public AndroidtoJs(MainActivity wb){
        this.context = wb;
    }
    @JavascriptInterface
    public void replace(String url) {
        System.out.println("call method" +url);
        Log.i(AndroidtoJs.class.toString(), "replace url is: "+url);
        this.context.reloadSite(url);
    }
}