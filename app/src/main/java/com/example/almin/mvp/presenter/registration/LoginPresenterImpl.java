package com.example.almin.mvp.presenter.registration;

import com.almin.retrofitlibrary.callback.HttpResultSubscriber;
import com.example.almin.mvp.contract.LoginContract;
import com.example.almin.mvp.datasource.model.UserProfile;
import com.almin.retrofitlibrary.errorhandlecomponent.RetrofitException;
import com.example.almin.mvp.utils.TextUtils;

/**
 * Created by almin on 2017/12/7.
 */

public class LoginPresenterImpl implements LoginContract.Presenter {
    private LoginContract.ViewRenderer viewRenderer;
    private LoginContract.DataSource dataSource;

    public LoginPresenterImpl(LoginContract.ViewRenderer viewRenderer, LoginContract.DataSource dataSource){
        this.viewRenderer = viewRenderer;
        this.dataSource = dataSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void detach() {

    }

    @Override
    public void login(String email, String password) {
        if(TextUtils.isTextEmptyOrNull(email)){
            viewRenderer.alertInvalidInput("email is invalid");
            return;
        }


        viewRenderer.showSpinner();
        dataSource.login(email,password, new HttpResultSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile userProfile) {

                dataSource.saveUserProfileToRepo(userProfile);
                dataSource.saveLoginTagToSp(true);

                viewRenderer.onLoginSuccess();
                viewRenderer.navigateToHomePage();

            }

            @Override
            protected void onError(RetrofitException retrofitException) {
                viewRenderer.onLoginFailed();
            }

            @Override
            public void onFinal() {
                viewRenderer.hideSpinner();
            }
        });

    }

    @Override
    public void forgotPassword(String email) {

    }
}
