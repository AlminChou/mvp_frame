package com.example.library.retrofit.component;

import com.example.library.model.UserProfile;

/**
 * Created by almin on 2017/12/12.
 */

public interface RetrofitConfiguration {
    UserProfile getUserProfile();
    long getConnectTimeout();
    String getServicesHost();
    boolean isNetworkAvailable();
}
