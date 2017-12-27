package com.almin.retrofitlibrary.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.almin.retrofitlibrary.urlprocessor.DefaultUrlProcessor;
import com.almin.retrofitlibrary.urlprocessor.UrlProcessor;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by almin on 2017/12/25.
 */

public class DynamicBaseUrlInterceptor implements Interceptor {
    private static final String MATCH_HEADER_KEY = "base_url_key";
    private static final String MATCH_KEY_DEFAULT = "default";
    private HashMap<String,UrlProcessor> mUrlProcessorList = new HashMap<>();

    public DynamicBaseUrlInterceptor(){
        init();
    }

    private void init() {
        registerProcessor(new DefaultUrlProcessor());
    }

    public void registerProcessor(UrlProcessor urlProcessor) {
        mUrlProcessorList.put(urlProcessor.getMatchKey(),urlProcessor);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        UrlProcessor urlProcessor;

        String baseUrlKey = obtainBaseUrlKeyFromHeaders(request);
        HttpUrl baseHttpUrl = null;

        System.out.println("baseurlkey : "+baseUrlKey);

        if(!TextUtils.isEmpty(baseUrlKey)){
            //remove the header key after obtain value
            requestBuilder.removeHeader(MATCH_HEADER_KEY);
            baseHttpUrl = HttpUrl.parse(baseUrlKey);
            urlProcessor = getUrlProcessor(baseUrlKey);
            if(urlProcessor==null){
                urlProcessor = getUrlProcessor(MATCH_KEY_DEFAULT);
            }
        }else{
            urlProcessor = getUrlProcessor(MATCH_KEY_DEFAULT);
        }

        urlProcessor.addHeader(requestBuilder);

        if (null == baseHttpUrl) {
            throw new InvalidUrlException(baseUrlKey);
        }

        HttpUrl requestHttpUrl = urlProcessor.wrapUrl(baseHttpUrl,request.url());
        if(requestHttpUrl != null){
            requestBuilder.url(requestHttpUrl);
        }
        System.out.println("  url :  "+requestHttpUrl.toString());
        return chain.proceed(requestBuilder.build());
    }


    private String obtainBaseUrlKeyFromHeaders(Request request) {
        List<String> headers = request.headers(MATCH_HEADER_KEY);
        if (headers == null || headers.size() == 0){
            return null;
        }
        if (headers.size() > 1){
            throw new IllegalArgumentException("Duplicate baseUrl key definition in the headers");
        }
        return request.header(MATCH_HEADER_KEY);
    }

    private UrlProcessor getUrlProcessor(String matchKey){
        return mUrlProcessorList.get(matchKey);
    }

    private static class InvalidUrlException extends RuntimeException {

        private InvalidUrlException(String url) {
            super("You've configured an invalid url : " + (TextUtils.isEmpty(url) ? "EMPTY_OR_NULL_URL" : url));
        }
    }
}
