package com.zhoug.android.common.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 日志打印工具
 */
public class LogUtils {
    private static final String TAG = "LogUtils";
    public static final int RELEASE=1;
    public static final int DEBUG=5;
    private static int status=RELEASE;
    private static Context appContext;

    /**
     * 初始化
     * 请在Application中调用
     * @param context
     */
    public static void init(Context context){
        appContext=context;
    }

    /**
     * 是否开启debug模式
     * @param debug
     */
    public static void setDebug(boolean debug ){
        if(debug){
            status=DEBUG;
        }else {
            status=RELEASE;
        }
    }

    public static void i(String TAG,String msg){
        if(status>=RELEASE){
            Log.i(TAG, msg);
        }
    }

    public static void d(String TAG,String msg){
        if(status>=DEBUG){
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG,String msg){
        if(status>=RELEASE){
            Log.e(TAG, msg);
        }
    }

    public static void toast(String msg){
        if(appContext==null){
            LogUtils.e(TAG, "没有初始化,需要在自定义的Application中调用初始化方法LogUtils.init()");
            return;
        }
        if (!StringUtils.isEmpty(msg)) {
            Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void toastCenter(String msg){
        if(appContext==null){
            LogUtils.e(TAG, "没有初始化,需要在自定义的Application中调用初始化方法LogUtils.init()");
            return;
        }
        if (!StringUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    public static void toastLong(String msg){
        if(appContext==null){
            LogUtils.e(TAG, "没有初始化,需要在自定义的Application中调用初始化方法LogUtils.init()");
            return;
        }
        if (!StringUtils.isEmpty(msg)) {
            Toast.makeText(appContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static void toastLongCenter(String msg){
        if(appContext==null){
            LogUtils.e(TAG, "没有初始化,需要在自定义的Application中调用初始化方法LogUtils.init()");
            return;
        }
        if (!StringUtils.isEmpty(msg)) {
            Toast toast = Toast.makeText(appContext, msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
