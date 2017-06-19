package com.qq.singleangelvip.contact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qq.singleangelvip.contact.R;

/**
 * Created by singl on 2017/4/17.
 */
public class BackupActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
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
                intent.setClass(BackupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
