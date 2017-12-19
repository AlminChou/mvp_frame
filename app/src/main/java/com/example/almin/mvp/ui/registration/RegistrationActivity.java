package com.example.almin.mvp.ui.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.almin.mvp.R;
import com.example.almin.mvp.navigator.RegistrationNavigator;
import com.example.almin.mvp.ui.base.AbstractActivity;
import com.example.almin.mvp.ui.main.MainActivity;
import com.example.almin.mvp.ui.news.NewsActivity;

public class RegistrationActivity extends AbstractActivity implements RegistrationNavigator{

    public static void start(Activity activity) {

    }

    @Override
    public int getContainerResId() {
        return R.id.fl_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        navigateToLoginPage();
    }

    @Override
    public void navigateToSignUpPage() {

    }

    @Override
    public void navigateToLoginPage() {
        replaceFragment(LoginFragment.newInstance());
    }

    @Override
    public void navigateToHomePage() {
        NewsActivity.start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
