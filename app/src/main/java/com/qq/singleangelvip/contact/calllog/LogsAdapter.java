package com.qq.singleangelvip.contact.calllog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;
import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.RequiresPermission;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qq.singleangelvip.contact.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by singl on 2017/4/1.
 */
public class LogsAdapter extends ArrayAdapter<LogObject> {

    List<LogObject> logs;
    Context context;
    int resource;

    public LogsAdapter(Context context, int resource, List<LogObject> callLogs) {
        super(context, resource, callLogs);
        this.logs = callLogs;
        this.context = context;
        this.resource = resource;

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public LogObject getItem(int position) {
        return logs.get(position);
    }

    @Override
    @SuppressLint("ViewHolder")
    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(getContext()).inflate(resource, parent, false);

        TextView phone = (TextView) row.findViewById(R.id.phoneNum);
        TextView duration = (TextView) row.findViewById(R.id.callDuration);
        TextView date = (TextView) row.findViewById(R.id.callDate);
        ImageView imageView = (ImageView) row.findViewById(R.id.callImage);
        Button send_message = (Button) row.findViewById(R.id.send_message);
        Button call_phone = (Button) row.findViewById(R.id.call_phone);

        LogObject log = getItem(position);
        Date date1 = new Date(log.getDate());

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.ERA_FIELD, DateFormat.SHORT);
        phone.setText(log.getContactName());
        duration.setText(log.getCoolDuration());
        date.setText(dateFormat.format(date1));
        final String number = log.getNumber();
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:"+number);
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                intent.putExtra("sms_body", "");
                context.startActivity(intent);
            }
        });

        call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + number);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });

        switch (log.getType()) {

            case LogsManager.INCOMING:
                imageView.setImageResource(R.mipmap.ic_calllog_incomming_normal);
                break;
            case LogsManager.OUTGOING:
                imageView.setImageResource(R.mipmap.ic_calllog_outgoing_nomal);
                break;
            case LogsManager.MISSED:
                imageView.setImageResource(R.mipmap.ic_calllog_missed_normal);
                break;
            default:
                imageView.setImageResource(R.mipmap.ic_calllog_outgoing_nomal);
                break;

        }

        return row;
    }
}
