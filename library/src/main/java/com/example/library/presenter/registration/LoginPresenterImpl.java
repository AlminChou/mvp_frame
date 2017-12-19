package com.example.library.presenter.registration;

import com.example.library.contract.LoginContract;
import com.example.library.model.UserProfile;
import com.example.library.retrofit.component.HttpResultSubscriber;
import com.example.library.retrofit.component.RetrofitException;
import com.example.library.utils.TextUtils;

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
