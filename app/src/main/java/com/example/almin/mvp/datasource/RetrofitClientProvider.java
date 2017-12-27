package com.example.almin.mvp.datasource;

import com.example.almin.mvp.datasource.apiinteractor.UserApiInteractor;
import com.example.almin.mvp.datasource.apiinteractor.UserApiInteractor.UserApiService;
import com.almin.retrofitlibrary.interceptor.ConnectivityInterceptor;
import com.almin.retrofitlibrary.RetrofitConfiguration;
import com.almin.retrofitlibrary.errorhandlecomponent.RxErrorHandlingCallAdapterFactory;
import com.almin.retrofitlibrary.interceptor.DynamicBaseUrlInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by almin on 2017/12/25.
 */

public class RetrofitClientProvider {
    private static RetrofitClientProvider sRetrofitClientProvider;
    private RetrofitConfiguration mRetrofitConfiguration;
    private Retrofit mRetrofit;
    private UserApiService mUserApiService;

    public static RetrofitClientProvider getInstance(RetrofitConfiguration retrofitConfiguration){
        if(sRetrofitClientProvider == null){
            sRetrofitClientProvider = new RetrofitClientProvider(retrofitConfiguration);
        }
        return sRetrofitClientProvider;
    }

    private RetrofitClientProvider(RetrofitConfiguration retrofitConfiguration){
        this.mRetrofitConfiguration = retrofitConfiguration;
        initRetrofit();
        initService();
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
    }

    private void initService() {
        mUserApiService = mRetrofit.create(UserApiService.class);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(mRetrofitConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS);
        builder.readTimeout(mRetrofitConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS);

        builder.addInterceptor(new ConnectivityInterceptor(mRetrofitConfiguration));

        DynamicBaseUrlInterceptor dynamicBaseUrlInterceptor = new DynamicBaseUrlInterceptor();
        dynamicBaseUrlInterceptor.registerProcessor(new UserApiInteractor.UserApiProcessor());
        builder.addInterceptor(new DynamicBaseUrlInterceptor());

//        if(retrofitConfiguration.getServicesHost().contains("localhost")) {
//            builder.hostnameVerifier((s, sslSession) -> true);
//        }
        return builder.build();
    }

    private String getBaseUrl() {
        return null;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public UserApiService getUserApiService(){
        return mUserApiService;
    }

    protected static RequestBody createCustomJsonRequestBody(String json){
        return RequestBody.create(MediaType.parse("application/json"), json);
    }
}
