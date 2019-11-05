package com.zhoug.androidcommon.app;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.zhoug.android.common.utils.StatusBarUtils;

public class StatusChangeActivity extends BaseActivity {
    private static final String TAG = "StatusChangeActivity";

    @Override
    protected int contentViewId() {
        return R.layout.activity_status_change;
    }

    protected void setImmersive1(boolean addStatusPlaceView){
        //状态栏透明,且布局占用状态栏
        StatusBarUtils.transparentStatusBar(this);
        //不给状态栏预留位置
        StatusBarUtils.setFitsSystemWindows(this,false );
        if(addStatusPlaceView){
            //添加状态栏占位
            StatusBarUtils.addStatusPlaceView(this,Color.parseColor("#88ff00ff"));
        }

    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        StatusBarUtils.transparentStatusBar(this);
        StatusBarUtils.setFitsSystemWindows(this,false);
        findViewById(R.id.btn1).setOnClickListener(v->{
            StatusBarUtils.addStatusPlaceView(this,Color.parseColor("#88ff00ff"));

//            StatusBarUtils.addStatusPlaceView(this, Color.parseColor("#ff00ff"));

        });

        findViewById(R.id.btn2).setOnClickListener(v->{
            StatusBarUtils.removeStatusPlaceView(this);

        });

        findViewById(R.id.btn3).setOnClickListener(v->{
            StatusBarUtils.hideNavigationBar(this,true );


        });
        findViewById(R.id.btn4).setOnClickListener(v->{
            StatusBarUtils.hideNavigationBar(this,false );


        });
        findViewById(R.id.btn5).setOnClickListener(v->{


        });

        findViewById(R.id.btn6).setOnClickListener(v->{
            int requestedOrientation = getRequestedOrientation();
            Log.d(TAG, "onCreateView:requestedOrientation="+requestedOrientation);
            if(requestedOrientation!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                Log.d(TAG, "onCreateView:横屏");
            }else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                Log.d(TAG, "onCreateView:竖屏");
            }
        });


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Log.d(TAG, "onConfigurationChanged:竖屏");
            StatusBarUtils.addStatusPlaceView(this);
            StatusBarUtils.hideStatusBar(this,false );
            StatusBarUtils.hideNavigationBar(this,false );

        }else{
            Log.d(TAG, "onConfigurationChanged:横屏");
            StatusBarUtils.removeStatusPlaceView(this);
            StatusBarUtils.hideStatusBar(this,true );
            StatusBarUtils.hideNavigationBar(this,true );

        }

    }

    @Override
    public void onBackPressed() {
        if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            super.onBackPressed();
        }
    }
}
