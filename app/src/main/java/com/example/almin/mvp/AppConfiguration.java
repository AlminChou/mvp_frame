package com.example.almin.mvp;

import com.example.library.model.UserProfile;
import com.example.library.retrofit.component.RetrofitConfiguration;

/**
 * Created by almin on 2017/12/14.
 */

public class AppConfiguration implements RetrofitConfiguration {

    private static AppConfiguration instance;

    public static AppConfiguration getInstance(){
        if(instance == null){
            instance = new AppConfiguration();
        }
        return instance;
    }



    @Override
    public UserProfile getUserProfile() {
        return null;
    }

    @Override
    public long getConnectTimeout() {
        return 0;
    }

    @Override
    public String getServicesHost() {
        return null;
    }

    @Override
    public boolean isNetworkAvailable() {
        return false;
    }

    public void init() {
        // load build config from file
    }
}
