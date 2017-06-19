package com.qq.singleangelvip.contact.sms;

/**
 * Created by singl on 2017/3/31.
 */
public interface SmsListener {

    /**
     * Invoked when an incoming sms is intercepted.
     *
     * @param sms intercepted.
     */
    public void onSmsSent(Sms sms);

    /**
     * Invoked when an outgoing sms is intercepted.
     *
     * @param sms
     */
    public void onSmsReceived(Sms sms);

}
