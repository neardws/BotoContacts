package com.qq.singleangelvip.contact.contacts;

import java.util.ArrayList;

/**
 * Created by singl on 2017/4/2.
 */
public class ContactModel {
    private String _ID;
    private String rawContactId;
    private String name;  //名字
    private String nickName; //昵称
    private String im;   //通讯账号
    private String note;   //备注
    private String company; //公司名字
    private PhoneModel phoneMain; //主要电话
    private PhoneModel phoneMobile; //移动电话
    private PhoneModel phoneHome; //家庭电话
    private PhoneModel phoneWork;//工作电话
    private EmailModel emailMain;  //主要邮箱
    private EmailModel emailMobile; //手机邮箱
    private EmailModel emailHome; //家用邮箱
    private EmailModel emailWork; //工作邮箱
    private AddressModel addressMain; //主要地址
    private AddressModel addressHome; //家庭地址
    private AddressModel addressWork;  //工作地址
    private AddressModel addressOther;  //其他地址
    private ArrayList<PhoneModel> phoneModels;
    private ArrayList<EmailModel> emailModels;
    private ArrayList<AddressModel> addressModels;

    public ContactModel(){
        this._ID = null;
        this.rawContactId = null;
        this.name = null;
        this.nickName = null;
        this.im = null;
        this.note = null;
        this.company = null;
        phoneMain = new PhoneModel();
        phoneMobile = new PhoneModel();
        phoneHome = new PhoneModel();
        phoneWork = new PhoneModel();
        emailHome = new EmailModel();
        emailMain = new EmailModel();
        emailMobile = new EmailModel();
        emailWork = new EmailModel();
        addressHome = new AddressModel();
        addressWork = new AddressModel();
        addressMain = new AddressModel();
        addressOther = new AddressModel();
        phoneModels = new ArrayList<>();
        emailModels = new ArrayList<>();
        addressModels = new ArrayList<>();
    }

    public void put(String id,String value){
        if (!(value.equals("")))
        {
            switch (id){
                case "id":
                    this._ID = value;
                    break;
                case "rawContactId":
                    this.rawContactId = value;
                    break;
                case "name":
                    this.name = value;
                    break;
                case "nickName":
                    this.nickName = value;
                    break;
                case "company":
                    this.company = value;
                    break;
                case "im":
                    this.im = value;
                    break;
                case "note":
                    this.note = value;
                    break;
                case "phoneMain":
                    if (!isInPhoneList(value)){
                        this.phoneMain.setPhoneNumber(value,"主要");
                        phoneModels.add(phoneMain);
                    }
                    break;
                case "phoneWork":
                    if (!isInPhoneList(value)){
                        this.phoneWork.setPhoneNumber(value,"工作");
                        phoneModels.add(phoneWork);
                    }
                    break;
                case "phoneHome":
                    if (!isInPhoneList(value)){
                        this.phoneHome.setPhoneNumber(value,"住宅");
                        phoneModels.add(phoneHome);
                    }
                    break;
                case "phoneMobile":
                    if (!isInPhoneList(value)){
                        this.phoneMobile.setPhoneNumber(value,"移动");
                        phoneModels.add(phoneMobile);
                    }
                    break;
                case "emailMain":
                    if (!isInEmailList(value)){
                        this.emailMain.setEmailAddress(value,"主要");
                        emailModels.add(emailMain);
                    }
                    break;
                case "emailWork":
                    if (!isInEmailList(value)){
                        this.emailWork.setEmailAddress(value,"工作");
                        emailModels.add(emailWork);
                    }
                    break;
                case "emailHome":
                    if (!isInEmailList(value)){
                        this.emailHome.setEmailAddress(value,"住宅");
                        emailModels.add(emailHome);
                    }
                    break;
                case "emailMobile":
                    if (!isInEmailList(value)){
                        this.emailMobile.setEmailAddress(value,"移动");
                        emailModels.add(emailMobile);
                    }
                    break;
                case "addressMain":
                    if (!isInAddressList(value)){
                        this.addressMain.setAddress(value,"主要");
                        addressModels.add(addressMain);
                    }
                    break;
                case "addressHome":
                    if (!isInAddressList(value)){
                        this.addressHome.setAddress(value,"住宅");
                        addressModels.add(addressHome);
                    }
                    break;
                case "addressWork":
                    if (!isInAddressList(value)){
                        this.addressWork.setAddress(value,"公司");
                        addressModels.add(addressWork);
                    }
                    break;
                case "addressOther":
                    if (!isInAddressList(value)){
                        this.addressOther.setAddress(value,"其他");
                        addressModels.add(addressOther);
                    }
                    break;
            }
        }
    }
    public String get(String id) {
        String str = null;
        switch (id) {
            case "id":
                str = this._ID;
                break;
            case "rawContactId":
                str = this.rawContactId;
                break;
            case "name":
                str = this.name;
                break;
            case "nickName":
                str = this.nickName;
                break;
            case "company":
                str = this.company;
                break;
            case "im":
                str = this.im;
                break;
            case "note":
                str = this.note;
                break;
            case "phoneMain":
                str = this.phoneMain.getPhoneNumber();
                break;
            case "phoneWork":
                str = this.phoneWork.getPhoneNumber();
                break;
            case "phoneHome":
                str = this.phoneHome.getPhoneNumber();
                break;
            case "phoneMobile":
                str = this.phoneMobile.getPhoneNumber();
                break;
            case "emailMain":
                str = this.emailMain.getEmailAddress();
                break;
            case "emailWork":
                str = this.emailWork.getEmailAddress();
                break;
            case "emailHome":
                str = this.emailHome.getEmailAddress();
                break;
            case "emailMobile":
                str = this.emailMobile.getEmailAddress();
                break;
            case "addressMain":
                str = this.addressMain.getAddress();
                break;
            case "addressWork":
                str = this.addressWork.getAddress();
                break;
            case "addressHome":
                str = this.addressHome.getAddress();
                break;
            case "addressOther":
                str = this.addressOther.getAddress();
                break;
            default:
                break;
        }
        return str;
    }

    public ArrayList<PhoneModel> getPhoneModels(){
        return this.phoneModels;
    }
    public ArrayList<EmailModel> getEmailModels(){
        return this.emailModels;
    }
    public ArrayList<AddressModel> getAddressModels(){
        return this.addressModels;
    }

    public void setPhoneModels(ArrayList<PhoneModel> phoneModels){
        this.phoneModels = phoneModels;
    }
    public void setEmailModels(ArrayList<EmailModel> emailModels){
        this.emailModels = emailModels;
    }
    public void setAddressModels(ArrayList<AddressModel> addressModels){
        this.addressModels = addressModels;
    }
    public boolean isInPhoneList(String phoneNum){
        ArrayList<PhoneModel> phoneModels = getPhoneModels();
        for (PhoneModel phoneModel:phoneModels){
            if (phoneModel.getPhoneNumber().equals(phoneNum))
                return true;
        }
        return false;
    }
    public boolean isInEmailList(String emailData){
        ArrayList<EmailModel> emailModels = getEmailModels();
        for (EmailModel emailModel:emailModels){
            if (emailModel.getEmailAddress().equals(emailData))
                return true;
        }
        return false;
    }
    public boolean isInAddressList(String addressData){
        ArrayList<AddressModel> addressModels = getAddressModels();
        for (AddressModel addressModel:addressModels){
            if (addressModel.getAddress().equals(addressData))
                return true;
        }
        return false;
    }

}