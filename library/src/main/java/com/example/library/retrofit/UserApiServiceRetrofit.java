package com.example.library.retrofit;

import com.example.library.retrofit.component.RetrofitConfiguration;
import com.example.library.retrofit.service.UserApiService;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by almin on 2017/12/13.
 */

public class UserApiServiceRetrofit extends AbstractAPIServiceRetrofit {
    private static UserApiServiceRetrofit instance;
    private UserApiService service;

    protected UserApiServiceRetrofit(RetrofitConfiguration retrofitConfiguration) {
        super(retrofitConfiguration);
    }

    public static UserApiServiceRetrofit getInstance(RetrofitConfiguration retrofitConfiguration) {
        if (instance == null) {
            synchronized (UserApiService.class) {
                if (instance == null) {
                    instance = new UserApiServiceRetrofit(retrofitConfiguration);
                }
            }
        }
        return instance;
    }

    @Override
    protected String getHost() {
        return getConfiguration().getServicesHost();
    }

    @Override
    protected HttpUrl addParameter(HttpUrl.Builder builder) {
        return super.addParameter(builder);
    }

    @Override
    protected void addHeader(Request.Builder builder) {
        super.addHeader(builder);
        builder.addHeader("","");
    }

    @Override
    protected void initAPIService() {
         service = getRetrofit().create(UserApiService.class);
    }

    public UserApiService getService() {
        return service;
    }
}
