package com.example.almin.mvp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.library.contract.AbstractContract.Presenter;

/**
 * Created by almin on 2017/12/7.
 */

public abstract class AbstractFragment extends LifecycleFragment {
    private Presenter mPresenter;

    @NonNull
    protected abstract Presenter createPresenter();
    protected abstract void initView(View view, Bundle savedInstanceState);
    protected abstract void initData();


    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bindPresenter();
    }

    @CallSuper
    @SuppressWarnings("unchecked")
    protected final void bindPresenter(){
        mPresenter = createPresenter();
        if(mPresenter == null){
            throw new IllegalArgumentException("Presenter can not be null!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getLayoutResource() > 0){
            return inflater.inflate(getLayoutResource(),container,false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected Presenter getPresenter(){
        return mPresenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //after view init finished
        initData();
    }

    @CallSuper
    @Override
    public void onDetach() {
        //do something to avoid memory leak
        getPresenter().detach();
        super.onDetach();
    }

    public void showSpinner() {

    }

    public void hideSpinner() {

    }

    public void showToast(String msg) {
        final Activity activity = getActivity();
        if(activity!=null){
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
        }
    }

    public void showSnackBar(String msg) {

    }

    public void goBack() {
        final Activity activity = getActivity();
        if(activity!=null){
            activity.onBackPressed();
        }
    }
}
