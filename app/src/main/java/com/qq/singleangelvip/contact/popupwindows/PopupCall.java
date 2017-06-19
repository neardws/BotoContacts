package com.qq.singleangelvip.contact.popupwindows;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.activities.ContactDetailActivity;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.contacts.PhoneModel;
import com.qq.singleangelvip.contact.contactselector.SortModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by singl on 2017/4/25.
 */
public class PopupCall extends BasePopupWindow  {

    private View popupView;
    private TextView phoneNum;
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    private TextView five;
    private TextView six;
    private TextView seven;
    private TextView eight;
    private TextView nine;
    private TextView zero;
    private TextView star;
    private TextView hash_key;
    private Button back_space;
    private Button call;

    private LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;

    public PopupCall(final Activity context) {
        super(context);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorAccent,typedValue,true);
        int[] attribute = new int[]{R.attr.colorAccent};
        TypedArray typedArray = getContext().obtainStyledAttributes(typedValue.resourceId,attribute);
        int colorAccent = typedArray.getColor(0,-1);
        typedArray.recycle();

        locationManager = (LocationManager) context.getSystemService(getContext().LOCATION_SERVICE);

        phoneNum = (TextView)popupView.findViewById(R.id.phone);
        one = (TextView)popupView.findViewById(R.id.one);
        two = (TextView) popupView.findViewById(R.id.two);
        three = (TextView) popupView.findViewById(R.id.three);
        four = (TextView) popupView.findViewById(R.id.four);
        five = (TextView) popupView.findViewById(R.id.five);
        six = (TextView) popupView.findViewById(R.id.six);
        seven = (TextView) popupView.findViewById(R.id.seven);
        eight = (TextView) popupView.findViewById(R.id.eight);
        nine = (TextView) popupView.findViewById(R.id.nine);
        zero = (TextView) popupView.findViewById(R.id.zero);
        star = (TextView) popupView.findViewById(R.id.star);
        hash_key = (TextView) popupView.findViewById(R.id.hash_key);

        back_space = (Button) popupView.findViewById(R.id.delete);
        call = (Button) popupView.findViewById(R.id.call);

        one.setTextColor(colorAccent);
        two.setTextColor(colorAccent);
        three.setTextColor(colorAccent);
        four.setTextColor(colorAccent);
        five.setTextColor(colorAccent);
        six.setTextColor(colorAccent);
        seven.setTextColor(colorAccent);
        eight.setTextColor(colorAccent);
        nine.setTextColor(colorAccent);
        zero.setTextColor(colorAccent);
        star.setTextColor(colorAccent);
        hash_key.setTextColor(colorAccent);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("1");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("2");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("3");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("4");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("9");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("0");
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("*");
            }
        });
        hash_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum.append("#");
            }
        });

        back_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = phoneNum.getText().toString();
                if (!text.isEmpty()){
                    String string =text.substring(0,text.length()-1);
                    phoneNum.setText(string);
                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phoneNum.getText().toString();
                if (number.equals("911")){
                    boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER);
                    if (gpsEnabled) {
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
                        }
                        String text = "我遇到危险，需要帮助，我的坐标是经度："+latitude+"纬度："+longitude;
                        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                        List<String> divideContents = smsManager.divideMessage(text);
                        String phone = null;
                        String file = Environment.getExternalStorageDirectory() + "/phone_num.dat";

                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                            phone = reader.readLine();
                            reader.close();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (String string : divideContents) {
                            smsManager.sendTextMessage("+86"+phone, null, string, null, null);
                        }
                    } else {        //开启GPS
                        Intent GPSIntent = new Intent();
                        GPSIntent.setClassName("com.android.settings",
                                "com.android.settings.widget.SettingsAppWidgetProvider");
                        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
                        GPSIntent.setData(Uri.parse("custom:3"));
                        try {
                            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }
                    }

                }else {
                    Uri uri = Uri.parse("tel:" + number);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)) {
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
            }
        });


    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateAnimation(250 * 2, 0, 300);
    }

    @Override
    public View getClickToDismissView() {
        return popupView.findViewById(R.id.click_to_dismiss);
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_call, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }

    LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
                latitude = location.getLatitude(); // 经度
                longitude = location.getLongitude(); // 纬度
            }
        }
    };
}