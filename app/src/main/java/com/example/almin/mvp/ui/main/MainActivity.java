package com.example.almin.mvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.almin.retrofitlibrary.interceptor.DynamicBaseUrlInterceptor;
import com.example.almin.mvp.manager.UserProfileManager;
import com.example.almin.mvp.ui.base.AbstractActivity;
import com.example.almin.mvp.ui.news.NewsActivity;
import com.example.almin.mvp.ui.registration.RegistrationActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by almin on 2017/12/13.
 */

public class MainActivity extends AbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(UserProfileManager.getInstance().isNeedLogin()){
            RegistrationActivity.start(this);
        }else{
            NewsActivity.start(this);
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
