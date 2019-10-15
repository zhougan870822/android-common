package com.zhoug.android.common.utils;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhoug.android.common.R;

/**
 * Toast 工具 可以在主线程和子线程中使用
 *  支持改变文字颜色和背景颜色
 * @Author HK-LJJ
 * @Date 2019/10/14
 * @Description TODO
 */
public class ToastUtils {
    private static final String TAG = ">>>ToastUtils";

    private static Application _app;

    /**
     * 文字颜色
     */
    private static int msgColor = Color.parseColor("#FFFFFFFF");
    /**
     * 背景颜色
     */
    private static int bgColor = Color.parseColor("#99000000");
    /**
     * 显示位置
     */
    private static int gravity = Gravity.BOTTOM;
    /**
     * 相对于显示位置的x偏移量
     */
    private static int xOffset = 0;
    /**
     * 相对于显示位置的y偏移量
     */
    private static int yOffset = 100;
    private static final String NULL = "null";
    private static Toast mToast;

    /**
     * 显示
     * @param msg
     */
    public static void showShort(CharSequence msg){
        show(msg,Toast.LENGTH_SHORT);
    }

    /**
     *
     * @param msg
     */
    public static void showLong(CharSequence msg){
        show(msg,Toast.LENGTH_LONG);
    }

    private static void show(CharSequence msg, int duration) {
        ThreadUtils.runMainThread(() -> {
            cancel();
            mToast=createToast(msg);
            setBg();
            mToast.setGravity(gravity, xOffset, yOffset);
            mToast.setDuration(duration);
            mToast.show();
        });
    }

    /**
     * 使用布局文件创建Toast
     * @param msg
     * @return
     */
    private static Toast createToast(CharSequence msg) {
        View view = LayoutInflater.from(getApp()).inflate(R.layout.common_toast_message, null);
        Toast toast = new Toast(getApp());
        toast.setView(view);

        TextView textView=view.findViewById(R.id.tv_message);
        textView.setTextColor(msgColor);
        textView.setText(msg==null ? NULL : msg);
        return toast;
    }

    /**
     * 设置圆角背景
     */
    private static void setBg(){
        if(mToast!=null){
            View view = mToast.getView();
            if(view!=null){
                GradientDrawable drawable=new GradientDrawable();
                AppUtils.measureView(view);
                int height = view.getMeasuredHeight();
                drawable.setCornerRadius(height>0 ?height/2:20);
                drawable.setColor(bgColor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(drawable);
                }else{
                    view.setBackgroundDrawable(drawable);
                }
            }
        }
    }



    private static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


    private static Application getApp() {
        if (_app == null) {
            synchronized (ToastUtils.class) {
                if (_app == null) {
                    _app = AppUtils.getApplicationByReflect();
                }
            }
        }
        return _app;
    }

    public static void setMsgColor(int msgColor) {
        ToastUtils.msgColor = msgColor;
    }

    public static void setBgColor(int bgColor) {
        ToastUtils.bgColor = bgColor;
    }

    public static void setGravity(int gravity) {
        ToastUtils.gravity = gravity;
    }

    public static void setOffset(int xOffset,int yOffset) {
        if(xOffset!=-1){
            ToastUtils.xOffset = xOffset;
        }
        if(yOffset!=-1){
            ToastUtils.yOffset = yOffset;
        }
    }
}
