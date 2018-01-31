package com.example.almin.mvp.datasource.network;

import com.almin.retrofitlibrary.RetrofitClientProvider;
import com.almin.retrofitlibrary.interceptor.DynamicBaseUrlInterceptor;
import com.example.almin.mvp.datasource.apiinteractor.UserApiInteractor;

import okhttp3.OkHttpClient;

/**
 * Created by Almin on 2017/12/29.
 */

public class RetrofitManager extends RetrofitClientProvider {
    private static final String BASE_URL = "";
    private UserApiInteractor.UserApiService mUserApiService;

    public static RetrofitManager getInstance(){
        return InstanceHolder.sRetrofitManager;
    }

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    protected void addInterceptor(OkHttpClient.Builder builder) {
        super.addInterceptor(builder);
        DynamicBaseUrlInterceptor dynamicBaseUrlInterceptor = new DynamicBaseUrlInterceptor("");
        dynamicBaseUrlInterceptor.registerProcessor(new UserApiInteractor.UserApiProcessor());
        builder.addInterceptor(dynamicBaseUrlInterceptor);
    }

    @Override
    protected void initService() {
        mUserApiService = getRetrofit().create(UserApiInteractor.UserApiService.class);
    }

    public UserApiInteractor.UserApiService getUserApiService() {
        return mUserApiService;
    }


    private static class InstanceHolder{
        private static RetrofitManager sRetrofitManager = new RetrofitManager();
    }
}
