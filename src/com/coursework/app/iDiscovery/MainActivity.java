/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.coursework.app.iDiscovery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.coursework.app.R;

import org.apache.cordova.*;

public class MainActivity extends CordovaActivity
{
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        //loadUrl(launchUrl);
        setContentView(R.layout.activity_main);
        Button btnBirdPage = (Button)this.findViewById(R.id.btnKiddyPage);
        Button btnManageBirdPage = (Button)this.findViewById(R.id.btnManageKiddyPage);
        Button btnPhoneGap = (Button)this.findViewById(R.id.btnPhoneGap);
        btnBirdPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddIDiscoveryActivity.class);
                startActivity(intent);
            }
        });

        btnManageBirdPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListIDiscoveryActivity.class);
                startActivity(intent);
            }
        });

        btnPhoneGap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });


    }
}
