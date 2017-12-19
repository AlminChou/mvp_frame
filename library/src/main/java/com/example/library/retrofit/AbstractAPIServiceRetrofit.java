package com.example.library.retrofit;

import com.example.library.model.UserProfile;
import com.example.library.retrofit.component.ConnectivityInterceptor;
import com.example.library.retrofit.component.RetrofitConfiguration;
import com.example.library.retrofit.component.RxErrorHandlingCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by almin on 2017/12/12.
 */

public abstract class AbstractAPIServiceRetrofit {

    protected static final String QUERY_PARAMETER_API_KEY = "apikey";
    protected static final String HEADER_PARAMETER_XXX = "xxx";

    private Retrofit retrofit;

    private RetrofitConfiguration retrofitConfiguration;

    protected AbstractAPIServiceRetrofit(RetrofitConfiguration retrofitConfiguration) {
        this.retrofitConfiguration = retrofitConfiguration;
        this.retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .build();
        initAPIService();
    }

    protected String getBaseUrl() {
        return String.format("%1$s://%2$s/", getScheme(), getHost());
    }

    protected String getScheme() {
        return getSchemeOfHttp();
    }

    protected String getSchemeOfHttp() {
        return "http";
    }

    protected String getSchemeOfHttps() {
        return "https";
    }

    protected Retrofit getRetrofit() {
        return retrofit;
    }

    protected abstract String getHost();
    protected abstract void initAPIService();

    protected OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(getConfiguration().getConnectTimeout(), TimeUnit.MILLISECONDS);
        builder.readTimeout(getConfiguration().getConnectTimeout(), TimeUnit.MILLISECONDS);

        builder.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            addHeader(requestBuilder);
            HttpUrl httpUrl = addParameter(chain.request().url().newBuilder());
            if(httpUrl != null){
                requestBuilder.url(httpUrl);
            }
            return chain.proceed(requestBuilder.build());
        });

        builder.addInterceptor(new ConnectivityInterceptor(retrofitConfiguration));

//        if(retrofitConfiguration.getServicesHost().contains("localhost")) {
//            builder.hostnameVerifier((s, sslSession) -> true);
//        }
        return builder.build();
    }


    protected <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    protected HttpUrl addParameter(HttpUrl.Builder builder){
        return null;
    }

    protected void addHeader(Request.Builder builder){
        builder.addHeader("Accept", "application/json");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("User-Agent","");
    }

    protected UserProfile getUserProfile(){
        return retrofitConfiguration.getUserProfile();
    }

    protected RetrofitConfiguration getConfiguration() {
        return retrofitConfiguration;
    }

    protected static RequestBody createCustomJsonRequestBody(String json){
        return RequestBody.create(MediaType.parse("application/json"), json);
    }

}