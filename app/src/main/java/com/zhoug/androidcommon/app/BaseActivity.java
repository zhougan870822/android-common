package com.zhoug.androidcommon.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhoug.android.common.utils.StatusBarUtils;

/**
 * @Author HK-LJJ
 * @Date 2019/11/5
 * @Description TODO
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId());
        setImmersive1(true);
        onCreateView(savedInstanceState);
    }

    protected void setImmersive1(boolean addStatusPlaceView){
        StatusBarUtils.setImmersive(this,addStatusPlaceView);

    }

    protected abstract @LayoutRes int contentViewId();
    protected abstract void onCreateView(@Nullable Bundle savedInstanceState);
}
