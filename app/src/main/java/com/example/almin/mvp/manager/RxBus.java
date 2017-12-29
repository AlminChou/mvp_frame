package com.example.almin.mvp.manager;

import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Created by almin on 2017/12/27.
 */

public class RxBus {
    private Subject<Object> mPublishSubject;

    private RxBus() {
        mPublishSubject = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        return InstanceHolder.sRxBus;
    }

    public <T> Observable<T> subscribeOfType(Class<T> cls) {
        return mPublishSubject.ofType(cls);
    }

    public void post(Object obj) {
        mPublishSubject.onNext(obj);
    }


    private static class InstanceHolder{
        private static RxBus sRxBus = new RxBus();
    }
}
