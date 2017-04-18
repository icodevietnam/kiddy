package com.coursework.app.iDiscovery;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebChromeClient;

import com.coursework.app.R;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

public class ListEventWebViewActivity extends CordovaActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        super.init();
        launchUrl = "file:///android_asset/www/listEvent.html";
        loadUrl(launchUrl);
    }

    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView)findViewById(R.id.cordovaWebView);
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
