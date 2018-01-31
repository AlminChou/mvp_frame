package com.example.almin.mvp.datasource.network;

import com.almin.retrofitlibrary.errorhandlecomponent.RetrofitException;
import com.almin.retrofitlibrary.interceptor.ConnectivityInterceptor;
import com.example.almin.mvp.AppConfiguration;
import com.example.almin.mvp.datasource.apiinteractor.UserApiInteractor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Almin on 2018/1/17.
 */

public class TokenProxyHandler implements InvocationHandler {

    private final static String TAG = "Token_Proxy";

    private final static String TOKEN_JSON_KEY = "token";

    private Object mProxyObject;

    private final TokenOperator mTokenOperator;


    public TokenProxyHandler(Object proxyObject, final TokenOperator tokenOperator) {
        mProxyObject = proxyObject;
        mTokenOperator = tokenOperator;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just("").flatMap(object -> {
            try {
                try {
                    return (Observable<?>) method.invoke(mProxyObject, args);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }).retryWhen(new Function<Observable<? extends Throwable>, Observable<?>>() {
            private int retryTime = 1;

            @Override
            public Observable<?> apply(Observable<? extends Throwable> observable) {
                return observable.flatMap(throwable -> {
                    if (retryTime > 0 && throwable instanceof RetrofitException
                            && ((((RetrofitException) throwable).getKind() == RetrofitException.Kind.TOKEN))) {
                        retryTime--;
                        return refreshTokenWhenTokenInvalid(throwable);
                    }
                    return Observable.error(throwable);
                });
            }
        });
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     * @param throwable
     */
    private Observable<?> refreshTokenWhenTokenInvalid(Throwable throwable) throws IOException, JSONException {
        synchronized(TokenProxyHandler.class){
            if(!isEnableRefreshToken()){
                return Observable.just(true);
            }else {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(10, TimeUnit.SECONDS);
                builder.addInterceptor(new ConnectivityInterceptor(AppConfiguration.getInstance()));

                Retrofit retrofit = new Retrofit.Builder()
                        .client(builder.build())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://xxxxxxx")
                        .build();


                Call<String> call = retrofit.create(UserApiInteractor.UserApiService.class).fetchToken();

                Response<String> repos  = call.execute();

                if(repos!=null){
                    mTokenOperator.updateUpdateTokenLastTime(new Date().getTime());
                    JSONObject jsonObject = new JSONObject(repos.body());
                    String token = jsonObject.optString(TOKEN_JSON_KEY);

                    // update token

                    mTokenOperator.updateToken(token);
                    return Observable.just(true);
                }else{
                    return Observable.error(throwable);
                }
            }
        }
    }

    // more than 5 minutes
    private boolean isEnableRefreshToken(){
        return ((new Date().getTime() - mTokenOperator.getLastUpdateTokenTime())/(1000*60)) > 5;
    }

    public interface TokenOperator{
        void updateToken(String token);
        long getLastUpdateTokenTime();
        void updateUpdateTokenLastTime(long updateTokenLastTime);
    }

}