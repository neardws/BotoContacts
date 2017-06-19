package com.qq.singleangelvip.contact.contacts;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by near on 2017/1/16.
 */

public class ContactHelper {
    private static final Uri CONTACT_URI = ContactsContract.Contacts.CONTENT_URI;
    private static final Uri CONTACT_LOOK_UP_URI = ContactsContract.Contacts.CONTENT_LOOKUP_URI;
    private static final Uri RAW_CONTACT_URI = ContactsContract.RawContacts.CONTENT_URI;
    private static final Uri PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static final Uri EMAIL_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    private static final Uri ADDRESS_URI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
    private static final Uri DATA_URI = ContactsContract.Data.CONTENT_URI;

    private static final String ACCOUNT_NAME = ContactsContract.RawContacts.ACCOUNT_NAME;
    private static final String ACCOUNT_TYPE = ContactsContract.RawContacts.ACCOUNT_TYPE;

    private static final String MIMETYPE = ContactsContract.Data.MIMETYPE;
    private static final String COMPANY_ITEM_TYPE = ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE;
    private static final String ADDRESS_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE;
    private static final String EMAIL_ITEM_TYPE = ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE;
    private static final String NAME_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE;
    private static final String PHONE_ITEM_TYPE = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
    private static final String NICK_NAME_ITEM_TYPE = ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE;
    private static final String IM_ITEM_TYPE = ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE;
    private static final String NOTE_ITEM_TYPE = ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE;
    //private static final String WEBSITE_ITEM_TYPE = ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE;

    private static final String GIVEN_NAME = ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME;
    private static final String NICK_NAME = ContactsContract.CommonDataKinds.Nickname.NAME;
    private static final String COMPANY = ContactsContract.CommonDataKinds.Organization.COMPANY;
    //private static final String IM = ContactsContract.CommonDataKinds.Im.
    private static final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private static final String IM = ContactsContract.CommonDataKinds.Im.DATA;
    private static final String NOTE = ContactsContract.CommonDataKinds.Note.NOTE;

    private static final String DISPLAY_STRUCTURED_NAME = ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
    private static final String CONTACT_ID = ContactsContract.Data.CONTACT_ID;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private static final String _ID = ContactsContract.Contacts._ID;
    private static final String RAW_CONTACT_ID = ContactsContract.RawContacts._ID;

    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE;
    private static final int PHONE_TYPE_HOME = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
    private static final int PHONE_TYPE_MOBILE = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
    private static final int PHONE_TYPE_WORK = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
    private static final int PHONE_TYPE_MAIN = ContactsContract.CommonDataKinds.Phone.TYPE_MAIN;

    private static final String EMAIL_DATA = ContactsContract.CommonDataKinds.Email.DATA;
    private static final String EMAIL_TYPE = ContactsContract.CommonDataKinds.Email.TYPE;
    private static final int EMAIL_TYPE_HOME = ContactsContract.CommonDataKinds.Email.TYPE_HOME;
    private static final int EMAIL_TYPE_WORK = ContactsContract.CommonDataKinds.Email.TYPE_WORK;
    private static final int EMAIL_TYPE_MAIN = ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM;
    private static final int EMAIL_TYPE_MOBILE = ContactsContract.CommonDataKinds.Email.TYPE_MOBILE;

    private static final String ADDRESS_DATA = ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS;
    private static final String ADDRESS_TYPE = ContactsContract.CommonDataKinds.StructuredPostal.TYPE;
    private static final int ADDRESS_TYPE_MAIN = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM;
    private static final int ADDRESS_TYPE_HOME = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME;
    private static final int ADDRESS_TYPE_WORK = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK;
    private static final int ADDRESS_TYPE_OTHER = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER;

    private static final String AUTHORITY = ContactsContract.AUTHORITY;


    /**
     * 显示出所有联系人的名字，应用于通讯录列表
     * @param contentResolver
     * @return 存储ContactModel的线性表
     */
    public static ArrayList<ContactModel> fetchAllContact(ContentResolver contentResolver){
        ArrayList<ContactModel> contactModels = new ArrayList<>();
        Cursor cursor = contentResolver.query(CONTACT_URI,null,null,null,null);
        while (cursor.moveToNext()) {
            ContactModel contactModel = new ContactModel();//保存联系人对象的实例
            //获取_ID
            String id = cursor.getString(cursor.getColumnIndex(_ID));
            contactModel.put("id", id);
            ContactModel contactModel1 = infoContact(contentResolver, Long.valueOf(id));
            contactModels.add(contactModel1);
        }
        cursor.close();
        return contactModels;
    }

    /**
     *
     * @param contentResolver
     * @param contactModels
     * @return
     */
    public static boolean addAllContact(Context context,ContentResolver contentResolver,ArrayList<ContactModel> contactModels){
        boolean success = true;
        for (ContactModel contactModel: contactModels){
            if (addContact(context,contentResolver,contactModel)){
            }else
                success = false;
        }
        return success;
    }

    /**
     * 根据联系人的_ID信息查询所有信息
     * @param contentResolver
     * @return
     */
    public static ContactModel infoContact(ContentResolver contentResolver,Long _ID){
        ContactModel contactModel = new ContactModel();
        //从一个Cursor获取所有的信息
        Cursor contactInfoCursor = contentResolver.query(
                android.provider.ContactsContract.Data.CONTENT_URI,
                new String[]{android.provider.ContactsContract.Data.CONTACT_ID,
                        android.provider.ContactsContract.Data.MIMETYPE,
                        android.provider.ContactsContract.Data.DATA1
                },
                android.provider.ContactsContract.Data.CONTACT_ID+"="+_ID, null, null);
        while(contactInfoCursor.moveToNext()) {
            String mimetype = contactInfoCursor.getString(
                    contactInfoCursor.getColumnIndex(android.provider.ContactsContract.Data.MIMETYPE));
            String value = contactInfoCursor.getString(
                    contactInfoCursor.getColumnIndex(ContactsContract.Data.DATA1));
            if(mimetype.contains("/name")){
                contactModel.put("name",value);
            } else if (mimetype.contains("/nickname")){
                contactModel.put("nickName",value);
            } else if(mimetype.contains("/im")){
                contactModel.put("im",value);
            } else if(mimetype.contains("/email")) {
               // contactModel.put("emailMain",value);
            } else if(mimetype.contains("/phone")) {
             //   contactModel.put("phoneMain",value);
            } else if(mimetype.contains("/photo")) {
              //  System.out.println("照片="+value);
            } else if(mimetype.contains("/group")) {
               // System.out.println("组="+value);
            } else if (mimetype.contains("/note")){
                contactModel.put("note",value);
            }
        }
        contactInfoCursor.close();


        //查询==公司名字==类型的数据操作.Organization.COMPANY  ContactsContract.Data.CONTENT_URI
        String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] orgWhereParams = new String[]{String.valueOf(_ID),
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
        Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, orgWhere, orgWhereParams, null);
        if (orgCur.moveToFirst()) {
            //组织名 (公司名字)
            String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
            contactModel.put("company",company);
            //职位
            String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));

        }
        orgCur.close();

        //查询==地址==类型的数据操作.StructuredPostal.TYPE_WORK
        Cursor address = contentResolver.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + _ID,
                null, null);
        while (address.moveToNext())
        {
            String strAddress = address.getString(address.getColumnIndex(
                    ContactsContract.CommonDataKinds.StructuredPostal.DATA));
            int addType = address.getInt(address.getColumnIndex(ADDRESS_TYPE));
            switch (addType){
                case ADDRESS_TYPE_MAIN:
                    contactModel.put("addressMain",strAddress);
                    break;
                case ADDRESS_TYPE_HOME:
                    contactModel.put("addressHome",strAddress);
                    break;
                case ADDRESS_TYPE_OTHER:
                    contactModel.put("addressOther",strAddress);
                    break;
                case ADDRESS_TYPE_WORK:
                    contactModel.put("addressWork",strAddress);
                    break;
            }
        }
        address.close();

        //查询==电话==类型的数据操作
        Cursor phone = contentResolver.query(PHONE_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + _ID,
                null, null);
        while (phone.moveToNext())
        {
            String strPhone = phone.getString(phone.getColumnIndex(PHONE_NUMBER));
            int phoneType = phone.getInt(phone.getColumnIndex(PHONE_TYPE));
            switch (phoneType){
                case PHONE_TYPE_MAIN:
                    contactModel.put("phoneMain",strPhone);
                    break;
                case PHONE_TYPE_HOME:
                    contactModel.put("phoneHome",strPhone);
                    break;
                case PHONE_TYPE_MOBILE:
                    contactModel.put("phoneMobile",strPhone);
                    break;
                case PHONE_TYPE_WORK:
                    contactModel.put("phoneWork",strPhone);
                    break;
            }
        }
        address.close();


        //查询==邮箱==类型的数据操作
        Cursor email = contentResolver.query(EMAIL_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + _ID,
                null, null);
        while (email.moveToNext())
        {
            String strEmail = email.getString(email.getColumnIndex(EMAIL_DATA));
            int emailType = email.getInt(email.getColumnIndex(EMAIL_TYPE));
            switch (emailType){
                case EMAIL_TYPE_MAIN:
                    contactModel.put("emailMain",strEmail);
                    break;
                case EMAIL_TYPE_HOME:
                    contactModel.put("emailHome",strEmail);
                    break;
                case EMAIL_TYPE_MOBILE:
                    contactModel.put("emailMobile",strEmail);
                    break;
                case EMAIL_TYPE_WORK:
                    contactModel.put("emailWork",strEmail);
                    break;
            }
        }
        address.close();



        return contactModel;
    }

    /**
     * 添加一个联系人
     * @param contentResolver
     * @param contactModel
     * @return
     */
    public static boolean addContact(Context context,ContentResolver contentResolver, ContactModel contactModel){
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentValues values = new ContentValues();
        long rawContactId = ContentUris.parseId(contentResolver.insert(uri, values));

        boolean isSame = false;
        Contacts.initialize(context);
        Query query= Contacts.getQuery();
        query.include(Contact.Field.DisplayName, Contact.Field.PhoneNumber);
        List<Contact> contacts = query.find();
        for (Contact contact: contacts){
            if (contact.getDisplayName().equals(contactModel.get("name"))){
                for (PhoneNumber phoneNum : contact.getPhoneNumbers()){
                    if (phoneNum.equals(contactModel.getPhoneModels().get(0).getPhoneNumber())){
                        isSame = true;
                    }
                }
            }
        }
        if (!isSame){
            //向data表插入数据
            if (contactModel.get("name") != null){ //插入名字
                uri = Uri.parse("content://com.android.contacts/data");
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype", NAME_ITEM_TYPE);
                values.put("data2", contactModel.get("name"));
                contentResolver.insert(uri, values);
            }

            if (contactModel.get("nickName")  != null){ //插入昵称
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",NICK_NAME_ITEM_TYPE);
                values.put(NICK_NAME,contactModel.get("nickName"));
                contentResolver.insert(uri,values);
            }

            if (contactModel.get("company") != null){ //插入公司
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",COMPANY_ITEM_TYPE);
                values.put(COMPANY,contactModel.get("company"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("im") != null){ //插入通讯账号
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",IM_ITEM_TYPE);
                values.put(IM,contactModel.get("im"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("company") != null){ //插入备注
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",NOTE_ITEM_TYPE);
                values.put(NOTE,contactModel.get("note"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("phoneMain") != null){ //插入主要电话号码
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",PHONE_ITEM_TYPE);
                values.put(PHONE_TYPE,PHONE_TYPE_MAIN);
                values.put(PHONE_NUMBER,contactModel.get("phoneMain"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("phoneWork") != null){ //插入工作电话号码
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",PHONE_ITEM_TYPE);
                values.put(PHONE_TYPE,PHONE_TYPE_WORK);
                values.put(PHONE_NUMBER,contactModel.get("phoneWork"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("phoneHome") != null){ //插入家庭电话号码
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",PHONE_ITEM_TYPE);
                values.put(PHONE_TYPE,PHONE_TYPE_HOME);
                values.put(PHONE_NUMBER,contactModel.get("phoneHome"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("phoneMobile") != null){ //插入移动电话号码
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",PHONE_ITEM_TYPE);
                values.put(PHONE_TYPE,PHONE_TYPE_MOBILE);
                values.put(PHONE_NUMBER,contactModel.get("phoneMobile"));
                contentResolver.insert(uri,values);
                //  contentResolver.update(uri,values,null,null);
            }
            if (contactModel.get("emailMain") != null){ //插入主要邮箱地址
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",EMAIL_ITEM_TYPE);
                values.put(EMAIL_TYPE,EMAIL_TYPE_MAIN);
                values.put(EMAIL_DATA,contactModel.get("emailMain"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("emailHome") != null){
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",EMAIL_ITEM_TYPE);
                values.put(EMAIL_TYPE,EMAIL_TYPE_HOME);
                values.put(EMAIL_DATA,contactModel.get("emailHome"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("emailWork") != null){
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype",EMAIL_ITEM_TYPE);
                values.put(EMAIL_TYPE,EMAIL_TYPE_WORK);
                values.put(EMAIL_DATA,contactModel.get("emailWork"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("emailMobile") != null){
                values.clear();
                values.put("raw_contact_id", rawContactId);
                values.put("mimetype", EMAIL_ITEM_TYPE);
                values.put(EMAIL_TYPE, EMAIL_TYPE_MOBILE);
                values.put(EMAIL_DATA, contactModel.get("emailMobile"));
                contentResolver.insert(uri, values);
            }
            if (contactModel.get("addressMain") != null){
                values.clear();
                values.put("raw_contact_id", rawContactId);
                values.put("mimetype", ADDRESS_ITEM_TYPE);
                values.put(ADDRESS_TYPE, ADDRESS_TYPE_MAIN);
                values.put(ADDRESS_DATA, contactModel.get("addressMain"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("addressHome") != null){
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype", ADDRESS_ITEM_TYPE);
                values.put(ADDRESS_TYPE, ADDRESS_TYPE_HOME);
                values.put(ADDRESS_DATA, contactModel.get("addressHome"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("addressWork") != null){
                values.clear();
                values.put("raw_contact_id", rawContactId);
                values.put("mimetype", ADDRESS_ITEM_TYPE);
                values.put(ADDRESS_TYPE, ADDRESS_TYPE_WORK);
                values.put(ADDRESS_DATA, contactModel.get("addressWork"));
                contentResolver.insert(uri,values);
            }
            if (contactModel.get("addressOther") != null){
                values.clear();
                values.put("raw_contact_id",rawContactId);
                values.put("mimetype", ADDRESS_ITEM_TYPE);
                values.put(ADDRESS_TYPE, ADDRESS_TYPE_OTHER);
                values.put(ADDRESS_DATA, contactModel.get("addressOther"));
                contentResolver.insert(uri,values);
            }
            return true;
        }
       return false;
    }

    // 删除联系人

    /**
     * 删除联系人我们只需删除raw_contacts表中指定RawContactID的行删除，其余表中的数据会自动删除
     * @param contentResolver
     */
    public static boolean deleteContact(ContentResolver contentResolver,Long Id) {
        contentResolver.delete(ContactsContract.Contacts.CONTENT_URI, ContactsContract.Contacts._ID + " =?", new String[]{Id+""});
        contentResolver.delete(ContactsContract.RawContacts.CONTENT_URI, ContactsContract.RawContacts.CONTACT_ID + " =?", new String[]{Id+""});
        return true;
    }


    public static boolean updateContact(Context context,ContentResolver contentResolver,Long _ID,ContactModel contactModel){
        if (deleteContact(contentResolver,_ID)){
            if (addContact(context,contentResolver,contactModel)){
                return true;
            }else
                return false;
        }else
            return false;
    }

}