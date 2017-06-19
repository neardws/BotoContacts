package com.qq.singleangelvip.contact.contacts;

/**
 * Created by singl on 2017/4/2.
 */
public class EmailModel {
    private String emailAddress;
    private String emailType;
    public EmailModel(){ this.emailAddress = null;
                        this.emailType = null;}

    public void setEmailAddress(String emailAddress,String emailType){
        this.emailAddress = emailAddress;
        this.emailType = emailType;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public String getEmailType(){
        return this.emailType;
    }
}