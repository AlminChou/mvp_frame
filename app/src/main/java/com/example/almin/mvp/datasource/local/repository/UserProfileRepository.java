package com.example.almin.mvp.datasource.local.repository;

import com.example.almin.mvp.datasource.local.db.UserDataBaseHelper;
import com.example.almin.mvp.listener.Consumer;
import com.example.almin.mvp.datasource.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almin on 2017/12/13.
 */

public class UserProfileRepository {

    private static UserProfileRepository sInstance;
    private UserProfile userProfile;
    private List<Consumer<UserProfile>> consumerList = new ArrayList<>();


    private UserDataBaseHelper userDataBaseHelper;

    public static UserProfileRepository getsInstance(){
        if(sInstance == null){
            sInstance = new UserProfileRepository();
        }
        return sInstance;
    }

    private UserProfileRepository(){
    }


    public void updateUserProfile(UserProfile userProfile){
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile(){
        return this.userProfile;
    }


    // replace eventbus --------------------------------------------------------

    public void registerConsumer(Consumer<UserProfile> consumer){
        if(!consumerList.contains(consumer)){
            consumerList.add(consumer);
        }
    }

    public void unRegisterConsumer(Consumer<UserProfile> consumer){
        consumerList.remove(consumer);
    }

    @SuppressWarnings("unchecked")
    public void postNotify(){
        for(Consumer consumer : consumerList){
            consumer.accept(this.userProfile);
        }
    }
}
