package com.qq.singleangelvip.contact.contactselector;

import java.net.PortUnreachableException;

/**
 * Created by singl on 2017/4/1.
 */

public class SortModel {
    private Long _ID;
    private String name; // 显示的数据
    private String sortLetters; // 显示数据拼音的首字母
    private String phoneNumber;

    public Long get_ID(){ return _ID;}

    public void set_ID(Long _ID){ this._ID = _ID; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getPhoneNumber(){return phoneNumber;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}
}
