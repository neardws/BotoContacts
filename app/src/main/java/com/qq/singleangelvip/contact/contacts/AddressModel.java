package com.qq.singleangelvip.contact.contacts;

/**
 * Created by singl on 2017/4/2.
 */
public class AddressModel {
    private String Address;
    private String addressType;

    public AddressModel(){
        this.Address = null; this.addressType = null;
    }
    public void setAddress(String address,String addressType){
        this.Address = address;
        this.addressType = addressType;
    }
    public String getAddress(){
        return this.Address;
    }

    public String getAddressType(){
        return  this.addressType;
    }
}