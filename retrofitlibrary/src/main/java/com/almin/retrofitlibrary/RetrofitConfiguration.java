package com.almin.retrofitlibrary;


/**
 * Created by almin on 2017/12/12.
 */

public interface RetrofitConfiguration {
    long getConnectTimeout();
    String getServicesHost();
    boolean isNetworkAvailable();
}
