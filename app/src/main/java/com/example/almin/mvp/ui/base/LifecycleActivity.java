package com.example.almin.mvp.ui.base;

import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by almin on 2017/12/12.
 */

public abstract class LifecycleActivity extends AppCompatActivity implements LifecycleProvider<ActivityEvent> {

    private final PublishSubject<ActivityEvent> mLifecycleSubject = PublishSubject.create();


    @Override
    public Observable<ActivityEvent> lifecycle() {
        return mLifecycleSubject.hide();
    }

    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return null;
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mLifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

    @CallSuper
    @Override
    protected void onStop() {
        mLifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

}
