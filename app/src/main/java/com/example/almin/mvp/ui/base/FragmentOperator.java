package com.example.almin.mvp.ui.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import java.util.List;

public interface FragmentOperator {
    AbstractFragment getCurrentFragment();
    AbstractFragment getPreviousFragment();
    boolean popBackStackImmediate();
    boolean popBackStackImmediate(String name, int flags);
    void replaceFragment(AbstractFragment fragment);
    @IdRes int getContainerResId();
    List<Fragment> getFragments();
}