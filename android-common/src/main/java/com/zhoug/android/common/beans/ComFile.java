package com.zhoug.android.common.beans;

import android.net.Uri;

/**
 * 自定义文件
 */
public class ComFile {
    /**
     * 文件名包含路径
     */
    private String name;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件uri
     */
    private Uri uri;


    public ComFile() {
    }

    public ComFile(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public ComFile(String name, long size, Uri uri) {
        this.name = name;
        this.size = size;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "{"+
                "name:"+name+
                ",size:"+size+
                ",uri:"+uri+
                "}";
    }
}
