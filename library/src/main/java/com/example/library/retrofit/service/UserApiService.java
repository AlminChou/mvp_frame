package com.example.library.retrofit.service;

import com.example.library.model.UserProfile;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by almin on 2017/12/12.
 */

public interface UserApiService {

    @Headers("Content-Type:application/json")
    @POST("login")
    Observable<UserProfile> login(@Body String email,@Body String password);

    @POST("forget/{email}")
    Observable<String> forgot(@Path("email") String email);
}
