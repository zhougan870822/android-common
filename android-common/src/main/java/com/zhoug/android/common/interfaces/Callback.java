package com.zhoug.android.common.interfaces;

/**
 * 回掉
 * @param <T>
 */
public interface Callback<T> {
    int CODE_SUCESS=100;
    int CODE_FAILURE=101;
    void Callback(int code,T data);
}
