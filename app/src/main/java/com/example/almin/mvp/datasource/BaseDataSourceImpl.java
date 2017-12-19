package com.example.almin.mvp.datasource;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.ObservableTransformer;


/**
 * Created by almin on 2017/12/12.
 */

public abstract class BaseDataSourceImpl {

    private LifecycleProvider<ActivityEvent> mLifecycleProvider;

    public BaseDataSourceImpl(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.mLifecycleProvider = lifecycleProvider;
    }

    protected ObservableTransformer getDestroyComposer() {
        return mLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY);
    }

    protected <T> ObservableTransformer<T, T> getCommonApiComposer() {
        return upstream ->
                upstream.compose(RxSchedulerHelper.io_main())
                        .compose(mLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));
    }

}