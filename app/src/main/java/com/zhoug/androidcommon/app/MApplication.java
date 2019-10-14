package com.zhoug.androidcommon.app;

import android.app.Application;
import android.util.Log;

/**
 * @Author HK-LJJ
 * @Date 2019/10/14
 * @Description TODO
 */
public class MApplication extends Application {
    private static final String TAG = "MApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate:");
    }

}
