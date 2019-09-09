package com.zhoug.android.common.utils;

import android.content.Context;

/**
 * 发送短信工具
 */
public class SmsUtils {

    /**
     * 调用系统发短信
     * @param phoneNumber
     * @param message
     */
    public static void sendSms(Context context, String phoneNumber, String message) {
        IntentUtils.sendSms(context, phoneNumber, message);
    }



}
