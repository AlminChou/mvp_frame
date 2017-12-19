package com.example.almin.mvp.datasource;

import com.example.almin.mvp.AppConfiguration;
import com.example.almin.mvp.datasource.local.SharedPreferencesHelper;
import com.example.library.contract.LoginContract;
import com.example.library.model.UserProfile;
import com.example.almin.mvp.datasource.local.repository.UserProfileRepository;
import com.example.library.retrofit.UserApiServiceRetrofit;
import com.example.library.retrofit.component.HttpResultSubscriber;
import com.example.library.retrofit.service.UserApiService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;


import io.reactivex.functions.Function;

/**
 * Created by almin on 2017/12/13.
 */

public class LoginDataSourceImpl extends BaseDataSourceImpl implements LoginContract.DataSource {
    private UserProfileRepository mRepository;
    private UserApiService mUserApiService;

    public LoginDataSourceImpl(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        super(lifecycleProvider);
        mRepository = UserProfileRepository.getsInstance();
        mUserApiService = UserApiServiceRetrofit.getInstance(AppConfiguration.getInstance()).getService();
    }

    @Override
    public void login(String email, String password, HttpResultSubscriber<UserProfile> httpResultSubscriber) {
        mUserApiService.login(email,password)
                .compose(getCommonApiComposer())
                .subscribe(httpResultSubscriber);
    }

    @Override
    public void forgotPassword(String email, HttpResultSubscriber<UserProfile> httpResultSubscriber) {
        mUserApiService.forgot(email)
                .map(new Function<String, UserProfile>() {
                    @Override
                    public UserProfile apply(String json) throws Exception {
                        // json response
                        return null;
                    }
                })
                .compose(getCommonApiComposer())
                .subscribe(httpResultSubscriber);
    }

    @Override
    public void saveUserProfileToRepo(UserProfile userProfile) {
        mRepository.updateUserProfile(userProfile);
    }

    @Override
    public void saveUserProfileToDB(UserProfile userProfile) {

    }

    @Override
    public void saveLoginTagToSp(boolean isLogin) {
        SharedPreferencesHelper.getInstance().updateLoginTag(isLogin);
    }
}
