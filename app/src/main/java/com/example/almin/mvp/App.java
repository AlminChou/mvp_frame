package com.example.almin.mvp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.almin.mvp.datasource.local.SharedPreferencesHelper;
import com.example.almin.mvp.manager.UserProfileManager;


/**
 * Created by almin on 2017/12/11.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        UserProfileManager.getInstance().init();
        SharedPreferencesHelper.getInstance().init(this);
        AppConfiguration.getInstance().init();
    }

    public static App getApplication(Context context) {
        return (App) context.getApplicationContext();
    }
}
