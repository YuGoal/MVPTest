package com.wjhl.mvptest;

import android.app.Application;


/**
 * Created by lenovo on 2016/8/31.
 */

public class MyApplication extends Application {
    public static MyApplication myApplication;

    public static Application getContext() {
        return myApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }
}
