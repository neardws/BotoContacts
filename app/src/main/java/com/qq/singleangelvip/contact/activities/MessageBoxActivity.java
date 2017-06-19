package com.qq.singleangelvip.contact.activities;

import android.support.v7.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.message.MessageBean;
import com.qq.singleangelvip.contact.message.MessageBoxListAdapter;

/**
 * Created by singl on 2017/4/10.
 */
public class MessageBoxActivity extends AppCompatActivity {
    private ListView talkView;
    private List<MessageBean> messages = null;
    private AsyncQueryHandler asyncQuery;
    private SimpleDateFormat sdf;
    private EditText et_message;
    private Button but_send;

    @Override
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
        setContentView(R.layout.activity_message_box);
        sdf = new SimpleDateFormat("MM-dd HH:mm");
        String thread = getIntent().getStringExtra("threadId");
        String phoneNum = getIntent().getStringExtra("phoneNumber");
        init(thread,phoneNum);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.go_back);//设置导航栏图标
        toolbar.setTitle(phoneNum);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                if (bundle != null){
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("theme",bundle.getInt("theme"));
                    intent.putExtras(bundle);
                }
                intent.setClass(MessageBoxActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(String thread,String phoneNum) {
        asyncQuery = new MessageAsynQueryHandler(getContentResolver());
        talkView = (ListView) findViewById(R.id.message_list);
        messages = new ArrayList<MessageBean>();

        et_message = (EditText) findViewById(R.id.edi_message_send);
        but_send = (Button) findViewById(R.id.button_send);

        final String phoneNumber = phoneNum;
        but_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et_message.getText().toString();
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                List<String> divideContents = smsManager.divideMessage(message);
                for (String text : divideContents) {
                    smsManager.sendTextMessage("+86"+phoneNumber, null, text, null, null);
                }
                Toast.makeText(getBaseContext(),"发送成功", Toast.LENGTH_SHORT).show();

            }
        });


        Uri uri = Uri.parse("content://sms");
        String[] projection = new String[] { "date", "address", "person",
                "body", "type" }; // 查询的列
        asyncQuery.startQuery(0, null, uri, projection,
                "thread_id = " + thread, null, "date asc");
    }

    private class MessageAsynQueryHandler extends AsyncQueryHandler {

        public MessageAsynQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String date = sdf.format(new Date(cursor.getLong(cursor
                            .getColumnIndex("date"))));
                    if (cursor.getInt(cursor.getColumnIndex("type")) == 1) {
                        MessageBean d = new MessageBean(
                                cursor.getString(cursor
                                        .getColumnIndex("address")),
                                date,
                                cursor.getString(cursor.getColumnIndex("body")),
                                R.layout.list_say_he_item);
                        messages.add(d);
                    } else {
                        MessageBean d = new MessageBean(
                                cursor.getString(cursor
                                        .getColumnIndex("address")),
                                date,
                                cursor.getString(cursor.getColumnIndex("body")),
                                R.layout.list_say_me_item);
                        messages.add(d);
                    }
                }
                if (messages.size() > 0) {
                    talkView.setAdapter(new MessageBoxListAdapter(
                            MessageBoxActivity.this, messages));
                    talkView.setDivider(null);
                    talkView.setSelection(messages.size());
                } else {
                    Toast.makeText(MessageBoxActivity.this, "没有短信进行操作",
                            Toast.LENGTH_SHORT).show();
                }
            }
            super.onQueryComplete(token, cookie, cursor);
        }
    }
}
