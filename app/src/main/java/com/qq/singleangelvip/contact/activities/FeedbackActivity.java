package com.qq.singleangelvip.contact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;

import java.util.List;

/**
 * Created by singl on 2017/5/2.
 */
public class FeedbackActivity extends AppCompatActivity {
    private EditText editText;
    private Button btn_send;

    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int theme = bundle.getInt("theme");
            if (theme == R.style.AppThemePink_NoActionBar || theme == R.style.AppBlueTheme_NoActionBar || theme == R.style.AppGreenTheme_NoActionBar
                    || theme == R.style.AppOrangeTheme_NoActionBar || theme == R.style.AppPurpleTheme_NoActionBar || theme == R.style.AppRedTheme_NoActionBar) {
                setTheme(theme);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.go_back);//设置导航栏图标
        toolbar.setLogo(R.mipmap.logo);
        toolbar.setTitle(R.string.action_feedback);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (bundle != null) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("theme", bundle.getInt("theme"));
                    intent.putExtras(bundle);
                }
                intent.setClass(FeedbackActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editText = (EditText) findViewById(R.id.et_feedback);
        btn_send = (Button) findViewById(R.id.btn_feedback);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                List<String> divideContents = smsManager.divideMessage(message);
                for (String text : divideContents) {
                    smsManager.sendTextMessage("+8613237671280", null, text, null, null);
                }
                Toast.makeText(getBaseContext(),"发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
