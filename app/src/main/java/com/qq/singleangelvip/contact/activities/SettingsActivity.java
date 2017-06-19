package com.qq.singleangelvip.contact.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.backup.ContactBackup;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.popupwindows.ChangeColorPopup;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class SettingsActivity extends AppCompatActivity {
    private ImageView imageView_pink;
    private ImageView imageView_blue;
    private ImageView imageView_green;
    private ImageView imageView_orange;
    private ImageView imageView_red;
    private ImageView imageView_purple;

    private EditText editText;
    private ProgressBar progressBar;
    private Button btn_backup;
    private Button btn_recovery;
    private Button btn_change;
    MyHandler myHandler;

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
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationIcon(R.drawable.go_back);//设置导航栏图标
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle(R.string.action_settings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (bundle != null){
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("theme",bundle.getInt("theme"));
                    intent.putExtras(bundle);
                }
                intent.setClass(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        myHandler = new MyHandler(Looper.myLooper());

        imageView_blue = (ImageView) findViewById(R.id.imageViewBlue);
        imageView_green = (ImageView) findViewById(R.id.imageViewGreen);
        imageView_orange = (ImageView) findViewById(R.id.imageViewOrange);
        imageView_pink = (ImageView) findViewById(R.id.imageViewPink);
        imageView_purple = (ImageView) findViewById(R.id.imageViewPurple);
        imageView_red = (ImageView) findViewById(R.id.imageViewRad);

        imageView_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppBlueTheme);
                ChangeColorPopup changeColorPopup = new ChangeColorPopup(SettingsActivity.this,R.style.AppBlueTheme_NoActionBar);
                changeColorPopup.showPopupWindow();
            }
        });
        imageView_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppGreenTheme);
                ChangeColorPopup changeColorPopup = new ChangeColorPopup(SettingsActivity.this,R.style.AppGreenTheme_NoActionBar);
                changeColorPopup.showPopupWindow();
            }
        });
        imageView_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppOrangeTheme);
                ChangeColorPopup changeColorPopup = new ChangeColorPopup(SettingsActivity.this,R.style.AppOrangeTheme_NoActionBar);
                changeColorPopup.showPopupWindow();
            }
        });
        imageView_pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppThemePink);
                ChangeColorPopup changeColorPopup = new ChangeColorPopup(SettingsActivity.this,R.style.AppThemePink_NoActionBar);
                changeColorPopup.showPopupWindow();
            }
        });
        imageView_purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppPurpleTheme);
                ChangeColorPopup changeColorPopup = new ChangeColorPopup(SettingsActivity.this,R.style.AppPurpleTheme_NoActionBar);
                changeColorPopup.showPopupWindow();
            }
        });
        imageView_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppRedTheme);
                ChangeColorPopup changeColorPopup = new ChangeColorPopup(SettingsActivity.this,R.style.AppRedTheme_NoActionBar);
                changeColorPopup.showPopupWindow();
            }
        });

        btn_backup = (Button) findViewById(R.id.btn_backup);
        btn_recovery = (Button) findViewById(R.id.but_recovery);
        btn_change = (Button) findViewById(R.id.change);

        editText = (EditText) findViewById(R.id.phone_num);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editText.getText().toString();
                String path = Environment.getExternalStorageDirectory() + "/phone_num.dat";

                try {
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
                    writer.write(phone);
                    writer.close();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<ContactModel> contactModels = ContactBackup.restoreContacts();
                            if (ContactHelper.addAllContact(getBaseContext(),getContentResolver(),contactModels)){
                                Message message = new Message();
                                message.what = 4;               //恢复存储成功
                                myHandler.sendMessage(message);
                            }else {
                                Message message = new Message();
                                message.what = 3;              //恢复存储失败
                                myHandler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message message = new Message();
                            message.what = 2;                    //恢复失败
                            myHandler.sendMessage(message);
                        }
                    }
                });
                thread.start();
            }
        });
        btn_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        ArrayList<ContactModel> contactModels = ContactHelper.fetchAllContact(getContentResolver());
                        if (ContactBackup.Backup(contactModels)){
                            Message message = new Message();
                            message.what = 1;               //备份成功
                            myHandler.sendMessage(message);
                        }else {
                            Message message = new Message();
                            message.what = 0;               //备份失败
                            myHandler.sendMessage(message);
                        }
                    }
                });
                thread1.start();

            }
        });
    }


    class MyHandler extends android.os.Handler{
        public MyHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int complete = msg.what;
            switch (complete){
                case 0:
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SettingsActivity.this,"备份失败",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SettingsActivity.this,"备份成功",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getBaseContext(),"恢复失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getBaseContext(),"恢复存储失败",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getBaseContext(),"恢复存储成功",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}

