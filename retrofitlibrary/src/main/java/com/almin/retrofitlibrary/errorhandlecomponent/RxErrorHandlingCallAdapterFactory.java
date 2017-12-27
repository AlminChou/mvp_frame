package com.almin.retrofitlibrary.errorhandlecomponent;

import com.almin.retrofitlibrary.interceptor.ConnectivityInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.CallAdapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by almin on 2017/12/12.
 */

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter {
        private final Retrofit retrofit;
        private final CallAdapter wrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Observable adapt(Call call) {
            return ((Observable) wrapped.adapt(call)).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                @Override
                public ObservableSource apply(Throwable throwable) throws Exception {
                    return Observable.error(asRetrofitException(throwable));
                }

            });
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            if (throwable instanceof HttpException) {
                // We had non-200 http error
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
            } else if (throwable instanceof ConnectivityInterceptor.NoConnectivityException) {
                // No connection
                return RetrofitException.noConnectivityError((IOException) throwable);
            } else if (throwable instanceof IOException) {
                // A network error happened
                return RetrofitException.networkError((IOException) throwable);
            } else {
                // We don't know what happened. We need to simply convert to an unknown error
                return RetrofitException.unexpectedError(throwable);
            }
        }
    }
}