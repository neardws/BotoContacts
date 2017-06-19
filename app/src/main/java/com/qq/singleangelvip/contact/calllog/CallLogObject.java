package com.qq.singleangelvip.contact.calllog;

/**
 * Created by singl on 2017/3/31.
 */
public interface CallLogObject {

    String getNumber();

    void setNumber(String number);

    int getType();

    void setType(int type);

    long getDate();

    void setDate(long date);

    int getDuration();

    void setDuration(int duration);

    String getCoolDuration();

    String getContactName();

}