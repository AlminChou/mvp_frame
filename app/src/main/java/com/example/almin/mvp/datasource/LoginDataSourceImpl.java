package com.example.almin.mvp.datasource;

import com.almin.retrofitlibrary.callback.HttpResultSubscriber;
import com.example.almin.mvp.datasource.local.SharedPreferencesHelper;
import com.example.almin.mvp.contract.LoginContract;
import com.example.almin.mvp.datasource.apiinteractor.UserApiInteractor;
import com.example.almin.mvp.datasource.model.UserProfile;
import com.example.almin.mvp.datasource.local.repository.UserProfileRepository;
import com.example.almin.mvp.datasource.network.RetrofitManager;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;


import io.reactivex.functions.Function;

/**
 * Created by almin on 2017/12/13.
 */

public class LoginDataSourceImpl extends BaseDataSourceImpl implements LoginContract.DataSource {
    private UserProfileRepository mRepository;
    private UserApiInteractor.UserApiService mUserApiService;

    public LoginDataSourceImpl(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        super(lifecycleProvider);
        mRepository = UserProfileRepository.getsInstance();
        mUserApiService = RetrofitManager.getInstance().getUserApiService();
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
