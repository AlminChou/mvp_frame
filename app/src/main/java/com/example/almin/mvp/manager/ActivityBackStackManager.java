package com.example.almin.mvp.manager;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class ActivityBackStackManager {

    private static final ActivityBackStackManager ourInstance = new ActivityBackStackManager();

    private LinkedList<BackStack> mBackStacks = new LinkedList<>();

    public static ActivityBackStackManager getInstance() {
        return ourInstance;
    }

    private ActivityBackStackManager() {
    }

    public void register(Activity activity){
        BackStack backStack = new BackStack();
        backStack.weakReference = new WeakReference<>(activity);
        if(!mBackStacks.contains(backStack)){
            mBackStacks.addFirst(backStack);
        }
    }

    public void unRegister(Activity activity){
        BackStack backStack = new BackStack();
        backStack.weakReference = new WeakReference<>(activity);
        mBackStacks.remove(backStack);
    }

    @NonNull
    public LinkedList<BackStack> getActivityBackStacks(){
        return mBackStacks;
    }

    public static class BackStack{
        public WeakReference<? extends Activity> weakReference;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BackStack backStack = (BackStack) o;

            if(weakReference != null && backStack.weakReference != null){
                Activity activity =  weakReference.get();
                if(activity !=  null){
                    return activity.equals(backStack.weakReference.get());
                }
            }

            return false;
        }

        @Override
        public int hashCode() {
            if(weakReference != null){
               Activity activity =  weakReference.get();
                if(activity !=  null){
                    return activity.hashCode();
                }
            }

            return 0;
        }
    }
}