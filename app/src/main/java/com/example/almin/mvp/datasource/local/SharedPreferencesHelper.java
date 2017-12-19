package com.example.almin.mvp.datasource.local;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by almin on 2017/12/13.
 */

public class SharedPreferencesHelper {
    private static final String PREF_REGISTRATION_KEY = "registration";
    private static SharedPreferencesHelper instance;
    private Context mContext;


    public static SharedPreferencesHelper getInstance() {
        if(instance==null){
            instance = new SharedPreferencesHelper();
        }
        return instance;
    }

    private SharedPreferencesHelper(){
    }

    public void init(Context context){
        // avoid memory leak , should use getApplicationContext()
        mContext = context.getApplicationContext();
    }

    private SharedPreferences getSharedPreferences(String name){
        return mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void updateLoginTag(boolean isLogin) {
        getSharedPreferences(PREF_REGISTRATION_KEY).edit().putBoolean("islogin",isLogin).apply();
    }

}
