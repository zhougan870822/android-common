package com.zhoug.android.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class IntentUtils {
    private static final String TAG = "IntentUtils";

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param authority
     * @param minitype  "audio/*" "video/*"
     * @return
     */
    public static Intent getReadFileProvideIntent(Context context, String path, String authority, String minitype) {
        if (minitype == null) {
            minitype = "*/*";
        }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
            Uri contentUri = FileProvider.getUriForFile(context, authority, new File(path));
            intent.setDataAndType(contentUri, minitype);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), minitype);
        }
        return intent;
    }

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getReadFileProvideIntent(Context context, String path, String minitype) {
        return getReadFileProvideIntent(context, path, context.getPackageName() + ".fileProvider", minitype);


    }

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param authority
     * @param minitype  "audio/*" "video/*"
     * @return
     */
    public static Intent getWriteFileProvideIntent(Context context, String path, String authority, String minitype) {
        if (minitype == null) {
            minitype = "*/*";
        }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//授予临时权限别忘了
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, authority, new File(path));
            intent.setDataAndType(contentUri, minitype);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(path)), minitype);
        }
        return intent;
    }

    /**
     * 调用第三方app打开文件的Intent
     *
     * @param context
     * @param path
     * @param minitype "audio/*" "video/*"
     * @return
     */
    public static Intent getWriteFileProvideIntent(Context context, String path, String minitype) {
        return getWriteFileProvideIntent(context, path, context.getPackageName() + ".fileProvider", minitype);
    }


    /**
     * 调用系统打电话,必须在manifest.xml中配置权限
     * <uses-permission android:name="android.permission.CALL_PHONE"/>
     * 且动态请求权限
     * eg:{
     * if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
     * IntentUtils.callPhone(MainActivity.this, "10086");
     * else
     * ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1001);
     * <p>
     * }
     *
     * @param context
     * @param phone
     */
    @SuppressLint("MissingPermission")
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 调用系统发短信
     *
     * @param phoneNumber
     * @param message
     */
    public static void sendSms(Context context, String phoneNumber, String message) {
//        if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (message != null) {
            intent.putExtra("sms_body", message);
        }
        context.startActivity(intent);
//        }
    }

    /**
     * 获取系统图片列表的Intent
     * @return
     */
    public static Intent getPickImageIntent() {
        return getPickIntent("image/*");
    }

    /**
     * 获取系统视频列表的Intent
     * @return
     */
    public static Intent getPickVideoIntent() {
        return getPickIntent("video/*");
    }

    /**
     * 获取系统音频列表的Intent
     * @return
     */
    public static Intent getPickAudioIntent() {
        return getPickIntent("audio/*");
    }

    /**
     * 获取选择文件的Intent :
     * 直接显示文件列表
     * @param mimeType {图片:"image/*";视频:"video/*";音频:"audio/*";星/星}
     * @return
     */
    public static Intent getPickIntent(String mimeType) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(mimeType);
        return intent;
    }


    /**
     * 获取选择文件的Intent
     * 优点:功能强大可以切换目录,可以使用文件管理器
     * 缺点:可以选择非指定类型的文件,选择的结果需要类型判断,
     * @param mimeType {图片:"image/*";视频:"video/*";音频:"audio/*";星/星}
     *
     * @return
     */
    public static Intent getContentIntent(String mimeType) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mimeType);
        return intent;
    }


}
