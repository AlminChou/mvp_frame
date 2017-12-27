package com.almin.retrofitlibrary.urlprocessor;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class DefaultUrlProcessor implements UrlProcessor {
    private static final String MATCH_KEY = "default";

    @Override
    public String getMatchKey() {
        return MATCH_KEY;
    }

    @Override
    public String getBaseUrl() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void addParameter(HttpUrl.Builder builder) {
    }

    @Override
    public void addHeader(Request.Builder builder) {
        builder.addHeader("Accept", "application/json");
        builder.addHeader("Content-Type", "application/json");
        builder.addHeader("User-Agent", "");
    }

    @Override
    public HttpUrl wrapUrl(HttpUrl baseUrl, HttpUrl url) {
        if (null == baseUrl){
            return url;
        }

        HttpUrl.Builder builder = url.newBuilder();
        addParameter(builder);
        return builder.scheme(baseUrl.scheme())
                .host(baseUrl.host())
                .port(baseUrl.port())
                .build();
    }
}