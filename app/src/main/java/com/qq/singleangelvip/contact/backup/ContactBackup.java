package com.qq.singleangelvip.contact.backup;

import android.os.Environment;
import android.provider.ContactsContract;

import com.qq.singleangelvip.contact.contacts.AddressModel;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.contacts.EmailModel;
import com.qq.singleangelvip.contact.contacts.PhoneModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import a_vcard.android.provider.Contacts;
import a_vcard.android.syncml.pim.VDataBuilder;
import a_vcard.android.syncml.pim.VNode;
import a_vcard.android.syncml.pim.vcard.ContactStruct;
import a_vcard.android.syncml.pim.vcard.VCardComposer;
import a_vcard.android.syncml.pim.vcard.VCardException;
import a_vcard.android.syncml.pim.vcard.VCardParser;

/**
 * Created by singl on 2017/4/17.
 */
public class ContactBackup {
    private static final int PHONE_TYPE_HOME = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
    private static final int PHONE_TYPE_MOBILE = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
    private static final int PHONE_TYPE_WORK = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
    private static final int PHONE_TYPE_MAIN = ContactsContract.CommonDataKinds.Phone.TYPE_MAIN;

    private static final int EMAIL_TYPE_HOME = ContactsContract.CommonDataKinds.Email.TYPE_HOME;
    private static final int EMAIL_TYPE_WORK = ContactsContract.CommonDataKinds.Email.TYPE_WORK;
    private static final int EMAIL_TYPE_MAIN = ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM;
    private static final int EMAIL_TYPE_MOBILE = ContactsContract.CommonDataKinds.Email.TYPE_MOBILE;

    private static final int ADDRESS_TYPE_MAIN = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM;
    private static final int ADDRESS_TYPE_HOME = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME;
    private static final int ADDRESS_TYPE_WORK = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK;
    private static final int ADDRESS_TYPE_OTHER = ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER;

    public static boolean Backup(ArrayList<ContactModel> contactModels) {
        try {
            String path = Environment.getExternalStorageDirectory() + "/contacts.vcf";

            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");

            VCardComposer composer = new VCardComposer();

            for (ContactModel contactModel : contactModels) {
                ContactStruct contact = new ContactStruct();
                contact.name = contactModel.get("name");
                contact.company = contactModel.get("company");
                contact.notes.add(contactModel.get("note"));
                // 获取联系人电话信息, 添加至 ContactStruct
                ArrayList<PhoneModel> phoneModels = contactModel.getPhoneModels();
                for (PhoneModel phoneModel : phoneModels) {
                    int phoneType = 0;
                    switch (phoneModel.getPhoneType()){
                        case "主要":
                            phoneType = PHONE_TYPE_MAIN;
                            break;
                        case "工作":
                            phoneType = PHONE_TYPE_WORK;
                            break;
                        case "住宅":
                            phoneType = PHONE_TYPE_HOME;
                            break;
                        case "移动":
                            phoneType = PHONE_TYPE_MOBILE;
                            break;
                    }
                    contact.addPhone(phoneType, phoneModel.getPhoneNumber(),
                            null, true);
                }
                // 获取联系人Email信息, 添加至 ContactStruct
                ArrayList<EmailModel> emailModels = contactModel.getEmailModels();
                for (EmailModel emailModel : emailModels) {
                    int emailType = 0;
                    switch (emailModel.getEmailType()){
                        case "主要":
                            emailType = EMAIL_TYPE_MAIN;
                            break;
                        case "工作":
                            emailType = EMAIL_TYPE_WORK;
                            break;
                        case "住宅":
                            emailType = EMAIL_TYPE_HOME;
                            break;
                        case "移动":
                            emailType = EMAIL_TYPE_MOBILE;
                            break;
                    }
                    contact.addContactmethod(Contacts.KIND_EMAIL,
                            emailType, emailModel.getEmailAddress(), null, true);
                }
                ArrayList<AddressModel> addressModels = contactModel.getAddressModels();
                for (AddressModel addressModel : addressModels){
                    int addressType = 0;
                    switch (addressModel.getAddressType()){
                        case "主要":
                            addressType = ADDRESS_TYPE_MAIN;
                            break;
                        case "公司":
                            addressType = ADDRESS_TYPE_WORK;
                            break;
                        case "住宅":
                            addressType = ADDRESS_TYPE_HOME;
                            break;
                        case "其他":
                            addressType = ADDRESS_TYPE_OTHER;
                            break;
                    }
                    contact.addContactmethod(Contacts.KIND_POSTAL,
                            addressType,addressModel.getAddress(),null,true);
                }

                String vcardString = composer.createVCard(contact,
                        VCardComposer.VERSION_VCARD30_INT);
                writer.write(vcardString);
                writer.write("\n");
                writer.flush();
            }
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (VCardException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取vCard文件中的联系人信息
     *
     * @return
     */
    public static ArrayList<ContactModel> restoreContacts() throws Exception {
        ArrayList<ContactModel> contactInfoList = new ArrayList<ContactModel>();

        VCardParser parse = new VCardParser();
        VDataBuilder builder = new VDataBuilder();
        String file = Environment.getExternalStorageDirectory() + "/contacts.vcf";

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

        String vcardString = "";
        String line;
        while ((line = reader.readLine()) != null) {
            vcardString += line + "\n";
        }
        reader.close();

        boolean parsed = parse.parse(vcardString, "UTF-8", builder);

        if (!parsed) {
            throw new VCardException("Could not parse vCard file: " + file);
        }

        List<VNode> pimContacts = builder.vNodeList;

        for (VNode contact : pimContacts) {

            ContactStruct contactStruct = ContactStruct.constructContactFromVNode(contact, 1);
            // 获取备份文件中的联系人电话信息
            List<ContactStruct.PhoneData> phoneDataList = contactStruct.phoneList;
           ArrayList<PhoneModel> phoneModels = new ArrayList<>();
            for (ContactStruct.PhoneData phoneData : phoneDataList) {
                PhoneModel phoneInfo = new PhoneModel();
                int type = phoneData.type;
                String typeStr = null;
                switch (type){
                    case PHONE_TYPE_MAIN:
                        typeStr = "主要";
                        break;
                    case PHONE_TYPE_WORK:
                        typeStr = "工作";
                        break;
                    case PHONE_TYPE_HOME:
                        typeStr = "住宅";
                        break;
                    case PHONE_TYPE_MOBILE:
                        typeStr = "移动";
                        break;
                }
                phoneInfo.setPhoneNumber(phoneData.data,typeStr);
                phoneModels.add(phoneInfo);
            }

            // 获取备份文件中的联系人信息
            List<ContactStruct.ContactMethod> methodsList = contactStruct.contactmethodList;
            ArrayList<EmailModel> emailModels = new ArrayList<>();
            ArrayList<AddressModel> addressModels = new ArrayList<>();
            if (null != methodsList) {
                for (ContactStruct.ContactMethod contactMethod : methodsList) {
                    if (Contacts.KIND_EMAIL == contactMethod.kind) {
                        EmailModel emailModel = new EmailModel();
                        int type = contactMethod.type;
                        String typeStr = null;
                        switch (type){
                            case EMAIL_TYPE_MAIN:
                                typeStr = "主要";
                                break;
                            case EMAIL_TYPE_HOME:
                                typeStr = "住宅";
                                break;
                            case EMAIL_TYPE_WORK:
                                typeStr = "工作";
                                break;
                            case EMAIL_TYPE_MOBILE:
                                typeStr = "移动";
                                break;
                        }
                        emailModel.setEmailAddress(contactMethod.data,typeStr);
                        emailModels.add(emailModel);
                    }
                    if (Contacts.KIND_POSTAL == contactMethod.kind) {
                        AddressModel addressModel = new AddressModel();
                        int type = contactMethod.type;
                        String typeStr = null;
                        switch (type){
                            case ADDRESS_TYPE_MAIN:
                                typeStr = "主要";
                                break;
                            case ADDRESS_TYPE_HOME:
                                typeStr = "住宅";
                                break;
                            case ADDRESS_TYPE_WORK:
                                typeStr = "公司";
                                break;
                            case ADDRESS_TYPE_OTHER:
                                typeStr = "其他";
                                break;
                        }
                        addressModel.setAddress(contactMethod.data,typeStr);
                        addressModels.add(addressModel);
                    }
                }
            }
            ContactModel contactModel = new ContactModel();
            contactModel.put("name",contactStruct.name);
            contactModel.put("company",contactStruct.company);
            contactModel.put("note",contactStruct.notes.get(0));

            contactModel.setPhoneModels(phoneModels);
            contactModel.setEmailModels(emailModels);
            contactModel.setAddressModels(addressModels);
            contactInfoList.add(contactModel);
        }

        return contactInfoList;
    }


}
