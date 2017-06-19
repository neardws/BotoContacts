package com.qq.singleangelvip.contact.contacts;

/**
 * Created by singl on 2017/4/2.
 */
public class PhoneModel {
    private String phoneNumber;
    private String phoneType;
    public PhoneModel(){this.phoneNumber = null;
                        this.phoneType = null;}

    public void setPhoneNumber(String phoneNumber,String type){
        this.phoneNumber = phoneNumber;
        this.phoneType = type;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getPhoneType() {
        return phoneType;
    }
}