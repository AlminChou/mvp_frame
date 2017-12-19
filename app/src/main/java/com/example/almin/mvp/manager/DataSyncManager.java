package com.example.almin.mvp.manager;

import java.lang.reflect.ParameterizedType;

/**
 * Created by almin on 2017/12/13.
 */

public class DataSyncManager {
    private static DataSyncManager instance;



    public abstract class OnRefreshDataListener<T> {
        public final Class<?> getSyncedObjectClass(){
            return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        public void onRefresh(T t){}
    }
}
