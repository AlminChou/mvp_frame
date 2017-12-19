package com.example.almin.mvp.ui.registration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.almin.mvp.R;
import com.example.almin.mvp.datasource.LoginDataSourceImpl;
import com.example.almin.mvp.navigator.RegistrationNavigator;
import com.example.almin.mvp.ui.base.AbstractFragment;
import com.example.library.contract.AbstractContract.Presenter;
import com.example.library.contract.LoginContract;
import com.example.library.presenter.registration.LoginPresenterImpl;

/**
 * Created by almin on 2017/12/7.
 */

public class LoginFragment extends AbstractFragment implements LoginContract.ViewRenderer{
    private RegistrationNavigator mNavigator;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof RegistrationNavigator){
            mNavigator = (RegistrationNavigator) context;
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @NonNull
    @Override
    protected Presenter createPresenter() {
        return new LoginPresenterImpl(this,new LoginDataSourceImpl(this));
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        view.findViewById()
    }

    @Override
    protected void initData() {

    }

    @Override
    public void alertInvalidInput(String message) {
        // dialog show message
    }

    @Override
    public void onLoginSuccess() {
        //mTvError.setVisible(View.GONE);
    }

    @Override
    public void onLoginFailed() {
        // do some ui change about error, such like :
        // mTvError.setVisible(View.VISIBLE);
    }

    @Override
    public void navigateToHomePage() {
        mNavigator.navigateToHomePage();
    }
}
