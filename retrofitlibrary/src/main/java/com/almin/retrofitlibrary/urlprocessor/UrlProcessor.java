package com.almin.retrofitlibrary.urlprocessor;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by almin on 2017/12/25.
 */

public interface UrlProcessor {
    String MATCH_HEADER = "base_url_key:";

    String getMatchKey();

    String getBaseUrl();

    String getScheme();

    String getHost();

    void addParameter(HttpUrl.Builder builder);

    void addHeader(Request.Builder builder);

    HttpUrl wrapUrl(HttpUrl baseUrl, HttpUrl url);
}
