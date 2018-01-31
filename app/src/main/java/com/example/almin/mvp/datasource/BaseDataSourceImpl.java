package com.example.almin.mvp.datasource;

import com.almin.retrofitlibrary.RxSchedulerHelper;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;


/**
 * Created by almin on 2017/12/12.
 */

public abstract class BaseDataSourceImpl {
    private List<Disposable> mDisposables;

    private LifecycleProvider<ActivityEvent> mLifecycleProvider;

    public BaseDataSourceImpl(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.mLifecycleProvider = lifecycleProvider;
        mDisposables = new ArrayList<>();
    }

    protected ObservableTransformer getDestroyComposer() {
        return mLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY);
    }

    protected <T> ObservableTransformer<T, T> getCommonApiComposer() {
        return upstream ->
                upstream.compose(RxSchedulerHelper.io_main())
                        .compose(mLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));
    }


    protected void subscribeSubject(Disposable disposable) {
        if(!mDisposables.contains(disposable)){
            mDisposables.add(disposable);
        }
    }

    public void unSubscribeSubjects() {
        for(Disposable disposable : mDisposables){
            disposable.dispose();
        }
        mDisposables.clear();
    }
}