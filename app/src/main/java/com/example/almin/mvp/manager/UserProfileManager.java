package com.example.almin.mvp.manager;


import com.example.library.model.UserProfile;
import com.example.almin.mvp.datasource.local.repository.UserProfileRepository;

/**
 * Created by almin on 2017/12/12.
 */

public class UserProfileManager {
    private static UserProfileManager instance;
    private UserProfile userProfile;

    private UserProfileManager(){
    }

    public static UserProfileManager getInstance() {
        if(instance == null){
            instance = new UserProfileManager();
        }
        return instance;
    }


    public void init() {
        //load from cache
        userProfile = new UserProfile();
        UserProfileRepository.getsInstance().updateUserProfile(userProfile);
    }

    public UserProfile getUserProfile(){
        return userProfile;
    }

    public boolean isNeedLogin(){
        return userProfile == null;
    }
}
