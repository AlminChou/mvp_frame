package com.almin.retrofitlibrary.interceptor;

import android.support.annotation.NonNull;


import com.almin.retrofitlibrary.RetrofitConfiguration;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by almin on 2017/12/12.
 */

public class ConnectivityInterceptor implements Interceptor {

    private RetrofitConfiguration retrofitConfiguration;

    public ConnectivityInterceptor(RetrofitConfiguration retrofitConfiguration) {
        this.retrofitConfiguration = retrofitConfiguration;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!retrofitConfiguration.isNetworkAvailable()) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }


    public static class NoConnectivityException extends IOException {
        @Override
        public String getMessage() {
            return "No connectivity exception";
        }

    }

}
