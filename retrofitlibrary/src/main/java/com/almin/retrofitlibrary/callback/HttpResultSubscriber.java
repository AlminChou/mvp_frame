package com.almin.retrofitlibrary.callback;

import android.support.annotation.NonNull;

import com.almin.retrofitlibrary.errorhandlecomponent.RetrofitException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by almin on 2017/12/12.
 */

public abstract class HttpResultSubscriber<T> implements Observer<T> {
    private Disposable disposable;

    public abstract void onSuccess(T t);

    protected void onError(RetrofitException retrofitException){
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
        onFinal();
    }



    //will add handle for 401（authorization）
    @Override
    public void onError(Throwable throwable) {
        if(throwable instanceof RetrofitException){
            RetrofitException exception=(RetrofitException)throwable;
            if(exception.getKind() == RetrofitException.Kind.NOCONNECTIVITY) {
                onNoConnectivityError();
            }else{
                onError((RetrofitException) throwable);
            }
        }
        onFinal();
    }

    /**
     * The {@link Observable} will not call this method if it calls {@link #onError}.
     */
    @Override
    public void onComplete() {
        // don't use this
    }

    public void onFinal(){
    }

    public void onNoConnectivityError(){
    }


    public void cancel() {
        disposable.dispose();
    }

    public boolean isRunning() {
        return disposable.isDisposed();
    }

}
