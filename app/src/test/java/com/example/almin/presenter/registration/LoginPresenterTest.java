package com.example.almin.presenter.registration;

import com.almin.retrofitlibrary.callback.HttpResultSubscriber;
import com.example.almin.mvp.contract.LoginContract;
import com.example.almin.mvp.datasource.model.UserProfile;
import com.example.almin.mvp.presenter.registration.LoginPresenterImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by almin on 2017/12/25.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginContract.ViewRenderer viewRenderer;

    @Mock
    private LoginContract.DataSource dataSource;

    @Captor
    private ArgumentCaptor<HttpResultSubscriber<UserProfile>> loginHttpResultSubscriber;

    @Test
    public void testLoginSuccess(){
        LoginContract.Presenter presenter = new LoginPresenterImpl(viewRenderer,dataSource);

        UserProfile userProfile = new UserProfile();

        presenter.login("fddf@mail.com","11232323");
        verify(viewRenderer,never()).alertInvalidInput(eq("email is invalid"));
        verify(viewRenderer).showSpinner();

        verify(dataSource).login(eq("fddf@mail.com"),eq("11232323"),loginHttpResultSubscriber.capture());
        loginHttpResultSubscriber.getValue().onNext(userProfile);

        verify(dataSource).saveLoginTagToSp(eq(true));
        verify(viewRenderer).onLoginSuccess();
        verify(viewRenderer).hideSpinner();
    }

    private LoginContract.DataSource mockDataSource = new LoginContract.DataSource() {
        @Override
        public void login(String email, String password, HttpResultSubscriber<UserProfile> httpResultSubscriber) {

        }

        @Override
        public void forgotPassword(String email, HttpResultSubscriber<UserProfile> httpResultSubscriber) {

        }

        @Override
        public void saveUserProfileToRepo(UserProfile userProfile) {

        }

        @Override
        public void saveUserProfileToDB(UserProfile userProfile) {

        }

        @Override
        public void saveLoginTagToSp(boolean isLogin) {

        }
    };
}
