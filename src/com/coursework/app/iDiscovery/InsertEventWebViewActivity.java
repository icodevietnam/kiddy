package com.coursework.app.iDiscovery;

import android.graphics.Color;
import android.os.Bundle;

import com.coursework.app.R;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

public class InsertEventWebViewActivity extends CordovaActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_1);
        super.init();
        launchUrl = "file:///android_asset/www/insertEvent.html";
        loadUrl(launchUrl);
    }

    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView)findViewById(R.id.cordovaWebView1);
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }

    @Override
    protected void createViews() {
        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            // Background of activity:
            appView.getView().setBackgroundColor(backgroundColor);
        }
        appView.getView().requestFocusFromTouch();
    }
}
