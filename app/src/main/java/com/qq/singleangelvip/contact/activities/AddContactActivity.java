package com.qq.singleangelvip.contact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;

import java.util.Objects;

/**
 * Created by singl on 2017/4/4.
 */
public class AddContactActivity extends AppCompatActivity {

    private EditText Name;
    private EditText NickName;
    private EditText phone;
    private EditText company;
    private EditText im;
    private EditText note;
    private EditText email;
    private EditText address;
    private Spinner spinner_phone;
    private Spinner spinner_email;
    private Spinner spinner_address;
    private Button butMore;
    private RelativeLayout moreToShow;
    private EditText phone2;
    private EditText email2;
    private EditText address2;
    private Spinner spinner_phone2;
    private Spinner spinner_email2;
    private Spinner spinner_address2;

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
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.go_back);//设置导航栏图标
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle(R.string.add_contact);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (bundle != null){
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("theme",bundle.getInt("theme"));
                    intent.putExtras(bundle);
                }
                intent.setClass(AddContactActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Name = (EditText) findViewById(R.id.etName);
        NickName = (EditText) findViewById(R.id.etNickName);
        company = (EditText) findViewById(R.id.etCompany);
        im = (EditText) findViewById(R.id.etIm);
        note = (EditText) findViewById(R.id.etNote);

        spinner_phone = (Spinner) findViewById(R.id.spPhoneType);
        spinner_email = (Spinner) findViewById(R.id.spEmailType);
        spinner_address = (Spinner) findViewById(R.id.spAddressType);

        phone = (EditText) findViewById(R.id.etPhone);
        email = (EditText) findViewById(R.id.etEmail);
        address =(EditText) findViewById(R.id.etAddress);

        moreToShow = (RelativeLayout) findViewById(R.id.more_to_show);

        phone2 = (EditText) findViewById(R.id.etPhone2);
        email2 = (EditText) findViewById(R.id.etEmail2);
        address2 =(EditText) findViewById(R.id.etAddress2);

        spinner_phone2 = (Spinner) findViewById(R.id.spPhoneType2);
        spinner_email2 = (Spinner) findViewById(R.id.spEmailType2);
        spinner_address2 = (Spinner) findViewById(R.id.spAddressType2);

        butMore = (Button) findViewById(R.id.but_add_contact_more);
        butMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                butMore.setVisibility(View.INVISIBLE);
                moreToShow.setVisibility(View.VISIBLE);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactModel contactModel = new ContactModel();
                contactModel.put("name",Name.getText().toString());
                contactModel.put("nickName", NickName.getText().toString());
                contactModel.put("company",company.getText().toString());
                contactModel.put("im",im.getText().toString());
                contactModel.put("note",note.getText().toString());
                String phoneNum = phone.getText().toString();
                if (!phoneNum.equals("")){
                    switch (spinner_phone.getSelectedItem().toString()){
                        case "主要":
                            contactModel.put("phoneMain",phoneNum);
                            break;
                        case "住宅":
                            contactModel.put("phoneHome",phoneNum);
                            break;
                        case "工作":
                            contactModel.put("phoneWork",phoneNum);
                            break;
                        case "移动":
                            contactModel.put("phoneMobile",phoneNum);
                            break;
                    }
                }
                else { //电话号码为空
                    Toast.makeText(getBaseContext(),"你没有输入电话号码", Toast.LENGTH_SHORT).show();

                }
                if (!phone2.getText().toString().equals("")){
                    switch (spinner_phone2.getSelectedItem().toString()){
                        case "主要":
                            contactModel.put("phoneMain",phone2.getText().toString());
                            break;
                        case "住宅":
                            contactModel.put("phoneHome",phone2.getText().toString());
                            break;
                        case "工作":
                            contactModel.put("phoneWork",phone2.getText().toString());
                            break;
                        case "移动":
                            contactModel.put("phoneMobile",phone2.getText().toString());
                            break;
                    }
                }
                if (!email.getText().toString().equals("")){
                    switch (spinner_email.getSelectedItem().toString()){
                        case "主要":
                            contactModel.put("emailMain",email.getText().toString());
                            break;
                        case "住宅":
                            contactModel.put("emailHome",email.getText().toString());
                            break;
                        case "工作":
                            contactModel.put("emailWork",email.getText().toString());
                            break;
                        case "移动":
                            contactModel.put("emailMobile",email.getText().toString());
                            break;
                    }

                }
                if (!email2.getText().toString().equals("")){
                    switch (spinner_email2.getSelectedItem().toString()){
                        case "主要":
                            contactModel.put("emailMain",email2.getText().toString());
                            break;
                        case "住宅":
                            contactModel.put("emailHome",email2.getText().toString());
                            break;
                        case "工作":
                            contactModel.put("emailWork",email2.getText().toString());
                            break;
                        case "移动":
                            contactModel.put("emailMobile",email2.getText().toString());
                            break;
                    }
                }
                if (!address.getText().toString().equals("")){
                    switch (spinner_address.getSelectedItem().toString()){
                        case "主要":
                            contactModel.put("addressMain",address.getText().toString());
                            break;
                        case "住宅":
                            contactModel.put("addressHome",address.getText().toString());
                            break;
                        case "工作":
                            contactModel.put("addressWork",address.getText().toString());
                            break;
                        case "其他":
                            contactModel.put("addressOther",address.getText().toString());
                            break;
                    }
                }
                if (!address2.getText().toString().equals("")){
                    switch (spinner_address2.getSelectedItem().toString()){
                        case "主要":
                            contactModel.put("addressMain",address2.getText().toString());
                            break;
                        case "住宅":
                            contactModel.put("addressHome",address2.getText().toString());
                            break;
                        case "工作":
                            contactModel.put("addressWork",address2.getText().toString());
                            break;
                        case "其他":
                            contactModel.put("addressOther",address2.getText().toString());
                            break;
                    }
                }

                if (ContactHelper.addContact(getBaseContext(),getContentResolver(),contactModel)) {
                    Toast.makeText(getBaseContext(),"添加联系人成功",Toast.LENGTH_SHORT);
                    try {
                        Thread.currentThread().sleep(1000);//阻断1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    if (bundle != null) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("theme", bundle.getInt("theme"));
                        intent.putExtras(bundle);
                    }
                    intent.setClass(AddContactActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Snackbar.make(view, "添加联系人失败", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }
}
