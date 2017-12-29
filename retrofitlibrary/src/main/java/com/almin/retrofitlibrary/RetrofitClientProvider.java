package com.almin.retrofitlibrary;

import android.support.annotation.CallSuper;

import com.almin.retrofitlibrary.errorhandlecomponent.RxErrorHandlingCallAdapterFactory;
import com.almin.retrofitlibrary.interceptor.ConnectivityInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public abstract class RetrofitClientProvider {
    private RetrofitConfiguration mRetrofitConfiguration;
    private Retrofit mRetrofit;

    public void init(RetrofitConfiguration retrofitConfiguration){
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

    protected abstract String getBaseUrl();

    protected abstract void initService();

    @CallSuper
    protected void initHttpClientConfig(OkHttpClient.Builder builder){
        builder.connectTimeout(mRetrofitConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS);
        builder.readTimeout(mRetrofitConfiguration.getConnectTimeout(), TimeUnit.MILLISECONDS);
    }

    @CallSuper
    protected void addInterceptor(OkHttpClient.Builder builder){
        builder.addInterceptor(new ConnectivityInterceptor(mRetrofitConfiguration));
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initHttpClientConfig(builder);
        addInterceptor(builder);

//        if(retrofitConfiguration.getServicesHost().contains("localhost")) {
//            builder.hostnameVerifier((s, sslSession) -> true);
//        }
        return builder.build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    protected static RequestBody createCustomJsonRequestBody(String json){
        return RequestBody.create(MediaType.parse("application/json"), json);
    }
}