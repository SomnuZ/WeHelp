package com.wmzl.wehelp;

import android.app.Application;

import com.thinkland.sdk.android.JuheSDKInitializer;

import as.leap.LASConfig;
import as.leap.LASHelpCenter;

/**
 * Created by leo on 15-7-7.
 */
public class App extends Application {
    public static final String APP_ID = "55c07bc260b2b854ff13083e";
    public static final String API_KEY = "QjB2bnd5eFkwUXMzUXhkTzU0MEJnZw";

    @Override
    public void onCreate() {
        super.onCreate();

        if (APP_ID.startsWith("Replace") || API_KEY.startsWith("Replace")) {
            throw new IllegalArgumentException("Please replace with your app id and api key first before" +
                    "using LAS SDK.");
        }

        LASConfig.setLogLevel(LASConfig.LOG_LEVEL_VERBOSE);
        LASConfig.initialize(this, APP_ID, API_KEY);

        LASHelpCenter.allowAlertNewMessage(false);


        JuheSDKInitializer.initialize(getApplicationContext());

    }
}
