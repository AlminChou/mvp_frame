package com.example.almin.mvp.ui.base;

import android.support.v4.app.Fragment;

/**
 * Created by almin on 2017/12/11.
 */

public abstract class BaseFragment extends Fragment {

    protected int getLayoutResource(){
        return 0;
    }

    protected String getTransactionTag(){
        return getClass().getSimpleName();
    }

    public int getEnterAnimation() {
        return 0;
    }

    public int getExitAnimation() {
        return 0;
    }

    public int getPopEnterAnimation() {
        return 0;
    }

    public int getPopExitAnimation() {
        return 0;
    }

    public boolean enableAnimation() {
        return true;
    }

    public boolean isPageCanGoBack() {
        return true;
    }

    public void onHandleGoBack(){

    }
}
