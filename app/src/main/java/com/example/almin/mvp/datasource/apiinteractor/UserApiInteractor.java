package com.example.almin.mvp.datasource.apiinteractor;

import com.almin.retrofitlibrary.urlprocessor.DefaultUrlProcessor;
import com.example.almin.mvp.datasource.model.UserProfile;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.almin.retrofitlibrary.urlprocessor.UrlProcessor.MATCH_HEADER;

/**
 * Created by almin on 2017/12/26.
 */

public class UserApiInteractor{


    public interface UserApiService {

        @Headers({MATCH_HEADER+UserApiProcessor.MATCH_KEY})
        @POST("login")
        Call<String> fetchToken();

        @Headers({MATCH_HEADER+UserApiProcessor.MATCH_KEY})
        @POST("login")
        Observable<UserProfile> login(@Body String email, @Body String password);

        @POST("forget/{email}")
        Observable<String> forgot(@Path("email") String email);
    }


    public static class UserApiProcessor extends DefaultUrlProcessor {
        private static final String MATCH_KEY = "user_api";

        @Override
        public String getMatchKey() {
            return MATCH_KEY;
        }

        @Override
        public void addParameter(HttpUrl.Builder builder) {
            super.addParameter(builder);
        }

        @Override
        public void addHeader(Request.Builder builder) {
            super.addHeader(builder);
        }
    }
}
