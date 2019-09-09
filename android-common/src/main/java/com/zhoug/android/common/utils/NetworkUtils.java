package com.zhoug.android.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;


/**
 * 网络管理工具类
 * @author zhougan
 * @date 2016/6/24
 */
public class NetworkUtils {
    private static final String TAG = "NetworkUtils";


    /**
     * 无连接
     */
    public static final int STATE_NONE = -1;

    /**
     * 移动网络,非wifi网络
     */
    public static final int STATE_MOBILE = 1;

    /**
     * wifi网络
     */
    public static final int STATE_WIFI = 2;


    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        //获取网络连接管理
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取当前连接的网络信息，判断网络是否可用
            if (manager != null) {
                 NetworkInfo info = manager.getActiveNetworkInfo();
                //不连接网络时info=null
                if (info != null) {
                    return info.isAvailable() && info.isConnected();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * 判断网络是否可用，并返回网络类型
     *
     * @param context
     * @return {@link #STATE_WIFI,#STATE_MOBILE,#STATE_NONE}
     */
    public static int getNetWorkState(Context context) {
        //获取网络连接管理
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                //wifi网络
                NetworkInfo wifiNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //移动网络
                NetworkInfo mobileNetworkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiNetworkInfo != null && wifiNetworkInfo.isAvailable() && wifiNetworkInfo.isConnected()) {
                    return STATE_WIFI;
                } else if (mobileNetworkInfo != null && mobileNetworkInfo.isAvailable() && mobileNetworkInfo.isConnected()) {
                    return STATE_MOBILE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return STATE_NONE;

    }

    /**
     * 移动网络是否可用
     * @param context
     * @return
     */
    public static boolean isMobileAvailable(Context context) {
        //获取网络连接管理
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                //移动网络
                NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
               return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * wifi是否可用
     * @param context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        //获取网络连接管理
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                //移动网络
                NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * GPS定位是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager==null){
            return false;
        }
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
//        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i(TAG, "isGPSOpen: gps=" + gps);
        return gps;
    }

    /**
     * 打开网络设置
     *
     * @param context
     */
    public static void openSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        context.startActivity(intent);
    }


    /**
     * 注册实时监听网络变化的广播
     * @param context
     * @param onNetworkChangeListener 网络变化实时监听器
     * @return
     */
    public static NetWorkBroadcastReceiver registerReceiver(Context context,OnNetworkChangeListener onNetworkChangeListener){
        NetWorkBroadcastReceiver netWorkBroadcastReceiver=new NetWorkBroadcastReceiver();
        netWorkBroadcastReceiver.setOnNetworkChangeListener(onNetworkChangeListener);
        IntentFilter filter=new IntentFilter();
        filter.addAction(NetWorkBroadcastReceiver.ACTION_CONNECTIVITY);
        context.registerReceiver(netWorkBroadcastReceiver, filter);
        Log.d(TAG, "registerReceiver:注册NetWorkBroadcastReceiver");
        return netWorkBroadcastReceiver;
    }

    /**
     * 取消注册实时监听网络变化的广播
     * @param context
     * @param receiver
     */
    public static void unregisterReceiver(Context context,NetWorkBroadcastReceiver receiver){
        if(receiver!=null){
            context.unregisterReceiver(receiver);
            Log.d(TAG, "unregisterReceiver:取消注册NetWorkBroadcastReceiver");
        }
    }

    /**
     * 实时监听网络变化的广播,7.0以后只能动态注册
     */
    public static class NetWorkBroadcastReceiver extends BroadcastReceiver{
        //监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        public static final String ACTION_CONNECTIVITY="android.net.conn.CONNECTIVITY_CHANGE";
        // 监听wifi的打开与关闭，与wifi的连接无关
        public static final String ACTION_WIFI_STATE_CHANGED="android.net.wifi.WIFI_STATE_CHANGED";

        /**
         * 网络变化监听接口
         */
        public OnNetworkChangeListener onNetworkChangeListener;

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null ){
                if(ACTION_CONNECTIVITY.equals(intent.getAction())){
                    if(onNetworkChangeListener!=null){
                        onNetworkChangeListener.onChangeListener(NetworkUtils.getNetWorkState(context));
                    }
                }
            }
        }

        public OnNetworkChangeListener getOnNetworkChangeListener() {
            return onNetworkChangeListener;
        }

        public void setOnNetworkChangeListener(OnNetworkChangeListener onNetworkChangeListener) {
            this.onNetworkChangeListener = onNetworkChangeListener;
        }
    }

    /**
     * 自定义网络变化监听接口
     */
    public interface OnNetworkChangeListener {
        void onChangeListener(int state);
    }
}
