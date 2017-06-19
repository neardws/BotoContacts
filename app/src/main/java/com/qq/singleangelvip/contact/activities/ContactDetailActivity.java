package com.qq.singleangelvip.contact.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.contacts.Contact;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.contacts.Contacts;
import com.qq.singleangelvip.contact.contacts.Query;
import com.qq.singleangelvip.contact.popupwindows.DialogPopup;

import java.util.List;

/**
 * Created by singl on 2017/4/5.
 */
public class ContactDetailActivity extends AppCompatActivity {
    private EditText name;
    private EditText nickName;
    private EditText company;
    private EditText im;
    private EditText note;

    private EditText phone;
    private EditText phone1;
    private EditText phone2;
    private EditText phone3;

    private EditText email;
    private EditText email1;
    private EditText email2;
    private EditText email3;

    private EditText address;
    private EditText address1;
    private EditText address2;
    private EditText address3;

    private Button btn_edit;
    private Button btn_delete;

    private Button btn_send;
    private Button btn_call;
    private Button btn_send1;
    private Button btn_call1;
    private Button btn_send2;
    private Button btn_call2;
    private Button btn_send3;
    private Button btn_call3;

    private FloatingActionButton btn_save;

    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        if (bundle != null){
            int theme = bundle.getInt("theme");
            if (theme == R.style.AppThemePink_NoActionBar || theme == R.style.AppBlueTheme_NoActionBar || theme == R.style.AppGreenTheme_NoActionBar
                    || theme == R.style.AppOrangeTheme_NoActionBar || theme == R.style.AppPurpleTheme_NoActionBar || theme == R.style.AppRedTheme_NoActionBar){
                setTheme(theme);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationIcon(R.drawable.go_back);//设置导航栏图标
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle(R.string.contact_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (bundle != null) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("theme", bundle.getInt("theme"));
                    intent.putExtras(bundle);
                }
                intent.setClass(ContactDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        name = (EditText) findViewById(R.id.etName);
        nickName = (EditText) findViewById(R.id.etNickName);
        company = (EditText) findViewById(R.id.etCompany);
        im = (EditText) findViewById(R.id.etIm);
        note = (EditText) findViewById(R.id.etNote);

        phone = (EditText) findViewById(R.id.etPhoneNum);
        phone1 = (EditText) findViewById(R.id.etPhoneNum1);
        phone2 = (EditText) findViewById(R.id.etPhoneNum2);
        phone3 = (EditText) findViewById(R.id.etPhoneNum3);

        email = (EditText) findViewById(R.id.etEmailData);
        email1 = (EditText) findViewById(R.id.etEmailData1);
        email2 = (EditText) findViewById(R.id.etEmailData2);
        email3 = (EditText) findViewById(R.id.etEmailData3);

        address = (EditText) findViewById(R.id.etAddressData);
        address1 = (EditText) findViewById(R.id.etAddressData1);
        address2 = (EditText) findViewById(R.id.etAddressData2);
        address3 = (EditText) findViewById(R.id.etAddressData3);


        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_delete = (Button) findViewById(R.id.delete);

        btn_call = (Button) findViewById(R.id.btn_call);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_call1 = (Button) findViewById(R.id.btn_call1);
        btn_send1 = (Button) findViewById(R.id.btn_send1);
        btn_call2 = (Button) findViewById(R.id.btn_call2);
        btn_send2 = (Button) findViewById(R.id.btn_send2);
        btn_call3 = (Button) findViewById(R.id.btn_call3);
        btn_send3 = (Button) findViewById(R.id.btn_send3);

        btn_save = (FloatingActionButton) findViewById(R.id.fab_save);




        final Long _ID = getIntent().getExtras().getLong("id");
      //  String phoneNum = getIntent().getExtras().getString("phone");

       ContactModel contactModel = ContactHelper.infoContact(getContentResolver(), _ID);

        String nameStr = contactModel.get("name");
        final String nickStr = contactModel.get("nickName");
        String companyStr = contactModel.get("company");
        String imStr = contactModel.get("im");
        String noteStr = contactModel.get("note");


        final String phoneMain = contactModel.get("phoneMain");
        final String phoneWork = contactModel.get("phoneWork");
        final String phoneHome = contactModel.get("phoneHome");
        final String phoneMobile = contactModel.get("phoneMobile");

        String emailMain = contactModel.get("emailMain");
        String emailWork = contactModel.get("emailWork");
        String emailHome = contactModel.get("emailHome");
        String emailMobile = contactModel.get("emailMobile");

        String addressMain = contactModel.get("addressMain");
        String addressWork = contactModel.get("addressWork");
        String addressHome = contactModel.get("addressHome");
        String addressOther = contactModel.get("addressOther");

        if (nameStr != null){
            name.setText(nameStr);
        }else {
            name.setText("");
        }
        if (nickStr != null){
            nickName.setText(nickStr);
        }else {
            nickName.setText("");
        }

        if (companyStr != null){
            company.setText(companyStr);
        }else {
            company.setText("");
        }

        if (imStr != null){
            im.setText(imStr);
        }else {
            im.setText("");
        }

        if (noteStr != null){
            note.setText(noteStr);
        }else {
            note.setText("");
        }

        if (phoneMain != null){
            phone.setText(phoneMain);
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("tel:" + phoneMain);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                }
            });

            btn_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("smsto:"+phoneMain);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body","");
                    startActivity(intent);
                }
            });
        }else {
            phone.setText("");
        }
            if (phoneWork != null){
                phone1.setText(phoneWork);
                btn_call1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneWork);
                        Intent intent = new Intent(Intent.ACTION_CALL, uri);
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                });

                btn_send1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneWork);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body","");
                        startActivity(intent);
                    }
                });
            }else {
                phone1.setText("");
            }
            if (phoneHome != null){
                phone2.setText(phoneHome);
                btn_call2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneHome);
                        Intent intent = new Intent(Intent.ACTION_CALL, uri);
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                });

                btn_send2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneHome);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body","");
                        startActivity(intent);
                    }
                });
            }else {
                phone2.setText("");
            }
            if (phoneMobile != null){
                phone3.setText(phoneMobile);
                btn_call3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneMobile);
                        Intent intent = new Intent(Intent.ACTION_CALL, uri);
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                });

                btn_send3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneMobile);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra("sms_body","");
                        startActivity(intent);
                    }
                });
            }else {
                phone3.setText("");
            }

        if (emailMain != null){
            email.setText(emailMain);
        }else {
            email.setText("");
        }
            if (emailWork != null){
                email1.setText(emailWork);
            }else {
                email1.setText("");
            }
            if (emailHome != null){
                email2.setText(emailHome);
            }else {
                email2.setText("");
            }
            if (emailMobile != null){
                email3.setText(emailMobile);
            }else {
                email3.setText("");
            }

        if (addressMain != null){
            address.setText(addressMain);
        }else {
            address.setText("");
        }
            if (addressWork != null){
                address1.setText(addressWork);
            }else {
                address1.setText("");
            }
            if (addressHome != null){
                address2.setText(addressHome);
            }else {
                address2.setText("");
            }
            if (addressOther != null){
                address3.setText(addressOther);
            }else {
                address3.setText("");
            }


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null){
                    DialogPopup dialogPopup = new DialogPopup(ContactDetailActivity.this,_ID,bundle.getInt("theme"));
                    dialogPopup.showPopupWindow();
                }else {
                    DialogPopup dialogPopup = new DialogPopup(ContactDetailActivity.this,_ID,0);
                    dialogPopup.showPopupWindow();
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusable(true);
                nickName.setFocusable(true);
                company.setFocusable(true);
                im.setFocusable(true);
                note.setFocusable(true);

                phone.setFocusable(true);
                address.setFocusable(true);
                email.setFocusable(true);
                phone1.setFocusable(true);
                address1.setFocusable(true);
                email1.setFocusable(true);
                phone2.setFocusable(true);
                address2.setFocusable(true);
                email2.setFocusable(true);
                phone3.setFocusable(true);
                address3.setFocusable(true);
                email3.setFocusable(true);


                name.setFocusableInTouchMode(true);
                nickName.setFocusableInTouchMode(true);
                company.setFocusableInTouchMode(true);
                im.setFocusableInTouchMode(true);
                note.setFocusableInTouchMode(true);

                phone.setFocusableInTouchMode(true);
                address.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                phone1.setFocusableInTouchMode(true);
                address1.setFocusableInTouchMode(true);
                email1.setFocusableInTouchMode(true);
                phone2.setFocusableInTouchMode(true);
                address2.setFocusableInTouchMode(true);
                email2.setFocusableInTouchMode(true);
                phone3.setFocusableInTouchMode(true);
                address3.setFocusableInTouchMode(true);
                email3.setFocusableInTouchMode(true);

                name.requestFocus();
                nickName.requestFocus();
                company.requestFocus();
                im.requestFocus();
                note.requestFocus();

                phone.requestFocus();
                address.requestFocus();
                email.requestFocus();
                phone1.requestFocus();
                address1.requestFocus();
                email1.requestFocus();
                phone2.requestFocus();
                address2.requestFocus();
                email2.requestFocus();
                phone3.requestFocus();
                address3.requestFocus();
                email3.requestFocus();

                btn_save.setVisibility(View.VISIBLE);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ContactModel contact = new ContactModel();
                        /**
                        contact.put("name",name.getText().toString());
                        contact.put("nickName",nickName.getText().toString());
                        contact.put("company",company.getText().toString());
                        contact.put("im",im.getText().toString());
                        contact.put("note",note.getText().toString());
                        contact.put("phoneMain",phone.getText().toString()); **/

                        if (name.getText().toString() != ""){
                            contact.put("name",name.getText().toString());
                        }else {

                        }
                        if (nickName.getText().toString() != ""){
                            contact.put("nickName",nickName.getText().toString());
                        }else {

                        }
                        if (company.getText().toString() != ""){
                            contact.put("company",company.getText().toString());
                        }else {

                        }
                        if (im.getText().toString() != ""){
                            contact.put("im",im.getText().toString());
                        }else {

                        }
                        if (note.getText().toString() != ""){
                            contact.put("note",note.getText().toString());
                        }else {

                        }




                        if (phone.getText().toString() != ""){
                            contact.put("phoneMain",phone.getText().toString());
                        }else {

                        }
                        if (phone1.getText().toString() != ""){
                            contact.put("phoneWork",phone1.getText().toString());
                        }else {

                        }
                        if (phone2.getText().toString() != ""){
                            contact.put("phoneHome",phone2.getText().toString());
                        }else {

                        }

                        if (phone3.getText().toString() != ""){
                            contact.put("phoneMobile",phone3.getText().toString());
                        }else {

                        }
                        if (address.getText().toString() != ""){
                            contact.put("addressMain",address.getText().toString());
                        }else {

                        }
                        if (address1.getText().toString() != ""){
                            contact.put("addressWork",address1.getText().toString());
                        }else {

                        }

                        if (address2.getText().toString() != ""){
                            contact.put("addressHome",address2.getText().toString());
                        }else {

                        }
                        if (address3.getText().toString() != ""){
                            contact.put("addressMobile",address3.getText().toString());
                        }else {

                        }

                        if (email.getText().toString() != ""){
                            contact.put("emailMain",email.getText().toString());
                        }else {

                        }

                        if (email1.getText().toString() != ""){
                            contact.put("emailWork",email1.getText().toString());
                        }else {

                        }
                        if (email2.getText().toString() != ""){
                            contact.put("emailHome",email2.getText().toString());
                        }else {

                        }
                        if (email3.getText().toString() != ""){
                            contact.put("emailOther",email3.getText().toString());
                        }else {

                        }


                        if (ContactHelper.updateContact(ContactDetailActivity.this,getContentResolver(),_ID,contact)){
                            Toast.makeText(getBaseContext(),"更新成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            if (bundle != null) {
                                Bundle bundle1 = new Bundle();
                                bundle1.putInt("theme", bundle.getInt("theme"));
                                intent.putExtras(bundle);
                            }
                            intent.setClass(ContactDetailActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getBaseContext(),"更新失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

}


