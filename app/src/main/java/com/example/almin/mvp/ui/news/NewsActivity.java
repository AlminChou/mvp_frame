package com.example.almin.mvp.ui.news;

import android.app.Activity;
import android.content.Intent;

import com.example.almin.mvp.ui.base.AbstractActivity;

/**
 * Created by almin on 2017/12/13.
 */

public class NewsActivity extends AbstractActivity{


    public static void start(Activity activity) {
        Intent intent = new Intent(activity,NewsActivity.class);
        activity.startActivity(intent);
    }


}
