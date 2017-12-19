package com.example.almin.mvp.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.example.almin.mvp.manager.ActivityBackStackManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by almin on 2017/12/7.
 */

public abstract class AbstractActivity extends LifecycleActivity implements FragmentOperator{
    private static final int ACTION_ADD = 1;
    private static final int ACTION_REPLACE = 2;
    private FragmentManager mFragmentManager;
    protected boolean mIsFromBackground;

    protected String getTransactionTag(){
        return getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        ActivityBackStackManager.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        mIsFromBackground = true;
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsFromBackground = false;
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        ActivityBackStackManager.getInstance().unRegister(this);
    }

    @Override
    public int getContainerResId() {
        return -1;
    }

    public void replaceFragment(@NonNull AbstractFragment fragment) {
        operateFragmentTransaction(fragment,ACTION_REPLACE,true,true);
    }

    public void replaceFragmentAllowingStateLoss(@NonNull AbstractFragment fragment) {
        operateFragmentTransaction(fragment,ACTION_REPLACE,true,true);
    }

    private void operateFragmentTransaction(AbstractFragment fragment, int action, boolean executePending, boolean addToBackStack) {
        final int containId = getContainerResId();
        if (containId > 0) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            if (fragment.enableAnimation()) {
                transaction.setCustomAnimations(fragment.getEnterAnimation(), fragment.getExitAnimation(),
                        fragment.getPopEnterAnimation(), fragment.getPopExitAnimation());
            }

            if (ACTION_ADD == action) {
                transaction.add(containId, fragment, fragment.getTransactionTag());
            } else if (ACTION_REPLACE == action) {
                transaction.replace(containId, fragment, fragment.getTransactionTag());
            }

            if(addToBackStack) {
                transaction.addToBackStack(fragment.getTransactionTag());
            }
            transaction.commitAllowingStateLoss();
            if (executePending) {
                mFragmentManager.executePendingTransactions();
            }
        }
    }

    @Override
    public AbstractFragment getCurrentFragment(){
        return (AbstractFragment) mFragmentManager.findFragmentById(getContainerResId());
    }

    @Override
    public List<Fragment> getFragments() {
        int count = mFragmentManager.getBackStackEntryCount();
        List<Fragment> fragments = new ArrayList<>(count);
        Fragment tempFragment;
        FragmentManager.BackStackEntry tempBackStackEntry;
        for (int i = 0; i < count; i++){
            tempBackStackEntry= mFragmentManager.getBackStackEntryAt(i);
            tempFragment = mFragmentManager.findFragmentByTag(tempBackStackEntry.getName());
            fragments.add(tempFragment);
        }
        return fragments;
    }

    @Override
    public AbstractFragment getPreviousFragment() {
        AbstractFragment previousFragment = null;
        int count = mFragmentManager.getBackStackEntryCount();
        if(count >= 2){
            FragmentManager.BackStackEntry backStackEntry = mFragmentManager.getBackStackEntryAt(count - 2);
            previousFragment = (AbstractFragment) mFragmentManager.findFragmentByTag(backStackEntry.getName());
        }
        return previousFragment;
    }

    @Override
    public boolean popBackStackImmediate() {
        if (mFragmentManager.getBackStackEntryCount() < 1){
            return false;
        }
        try {
            return mFragmentManager.popBackStackImmediate();
        }catch (IllegalStateException e){
            return false;
        }
    }

    @Override
    public boolean popBackStackImmediate(String name, int flags) {
        if (mFragmentManager.getBackStackEntryCount() < 1){
            return false;
        }
        try {
            return mFragmentManager.popBackStackImmediate(name, flags);
        }catch (IllegalStateException e){
            return false;
        }
    }

    protected int getFragmentCountToFinish() {
        return 1;
    }

    @Override
    public void onBackPressed() {
        AbstractFragment currentFragment = (AbstractFragment) mFragmentManager.findFragmentById(getContainerResId());
        final int count = mFragmentManager.getBackStackEntryCount();
        if(count > getFragmentCountToFinish()){
            if(currentFragment != null && currentFragment.isPageCanGoBack()){
                mFragmentManager.popBackStackImmediate();
                currentFragment = (AbstractFragment) mFragmentManager.findFragmentById(getContainerResId());
                if(currentFragment != null){
                    currentFragment.onHandleGoBack();
                }
            }
        }else {
            onActivityFinish();
        }
    }

    protected void onActivityFinish(){
        finish();
        overridePendingTransition(0,0);
    }

}
