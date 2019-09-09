package com.zhoug.android.common.beans;

/**
 * 联系人
 */
public class Contacts {
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话,可能有多个
     */
    private String telPhone;

    /**
     * 电话号码分割线
     */
    public static final String separator=",";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    @Override
    public String toString() {
        return "{"
                +"name:"+name
                +",telPhone:"+telPhone+
                "}";
    }
}
