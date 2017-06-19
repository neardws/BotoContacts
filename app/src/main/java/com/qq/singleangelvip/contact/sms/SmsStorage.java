package com.qq.singleangelvip.contact.sms;

/**
 * Created by singl on 2017/3/31.
 */
interface SmsStorage {


    void updateLastSmsIntercepted(int smsId);

    int getLastSmsIntercepted();

    boolean isFirstSmsIntercepted();
}