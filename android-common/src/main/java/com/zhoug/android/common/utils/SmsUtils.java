package com.zhoug.android.common.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * 短信工具
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

    /**
     * 直接发送短信,后台悄悄调用,现在大部分手机都会提示用户密码验证等操作防止恶意发送短信
     * 可以监控发送状态和对方接收状态
     * @param phoneNumber
     * @param message
     */
   /* public static void sendSms(Context context,String phoneNumber,String message ){
        SmsManager smsManager=SmsManager.getDefault();
        //拆分内容，有长度限制
        ArrayList<String> divideMessage = smsManager.divideMessage(message);
        for(String msg : divideMessage){
            //参数1:目标电话
            // 参数2:发送者的电话,
            // 参数3:短信内容
            // 参数4:处理发送是否成功,
            // 参数5:处理用户是否接受成功
            Intent intent1=new Intent("SENT_SMS_ACTION");
            Intent intent2=new Intent("DELIVERED_SMS_ACTION");
            smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
        }

    }*/

}
