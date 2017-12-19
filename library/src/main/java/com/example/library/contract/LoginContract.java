package com.example.library.contract;

import com.example.library.model.UserProfile;
import com.example.library.retrofit.component.HttpResultSubscriber;

/**
 * Created by almin on 2017/12/7.
 */

public interface LoginContract {

    interface ViewRenderer extends AbstractContract.ViewRenderer{
        void alertInvalidInput(String message);
        void onLoginSuccess();
        void onLoginFailed();
        void navigateToHomePage();
    }

    interface Presenter extends AbstractContract.Presenter{
        void login(String email, String password);
        void forgotPassword(String email);
    }

    interface DataSource{
        void login(String email, String password, HttpResultSubscriber<UserProfile> httpResultSubscriber);
        void forgotPassword(String email, HttpResultSubscriber<UserProfile> httpResultSubscriber);

        // define demo  : Repo(Repository)  DB(Database)  Sp(SharedPreferences)
        void saveUserProfileToRepo(UserProfile userProfile);
        void saveUserProfileToDB(UserProfile userProfile);
        void saveLoginTagToSp(boolean isLogin);
    }
}
