package com.qq.singleangelvip.contact.popupwindows;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.activities.ContactDetailActivity;
import com.qq.singleangelvip.contact.contacts.Contact;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.contacts.Contacts;
import com.qq.singleangelvip.contact.contacts.PhoneModel;
import com.qq.singleangelvip.contact.contacts.Query;
import com.qq.singleangelvip.contact.contactselector.SortModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by singl on 2017/4/3.
 */
public class SlideFromBottomPopup extends BasePopupWindow  {

    private View popupView;
    private TextView tv_name;

    private TableRow row;
    private TableRow row1;
    private TableRow row2;
    private TableRow row3;

    private TextView phone_type;
    private TextView tv_phone;
    private TextView phone_type1;
    private TextView tv_phone1;
    private TextView phone_type2;
    private TextView tv_phone2;
    private TextView phone_type3;
    private TextView tv_phone3;

    private Button btn_show_info;
    private Button btn_delete;
    private Button btn_share;

    private Button btn_call_phone;
    private Button btn_send_message;
    private Button btn_call_phone1;
    private Button btn_send_message1;
    private Button btn_call_phone2;
    private Button btn_send_message2;
    private Button btn_call_phone3;
    private Button btn_send_message3;

    public SlideFromBottomPopup(final Activity context, SortModel sortModel, final int theme) {
        super(context);
        final Long _ID = sortModel.get_ID();

        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorAccent,typedValue,true);
        int[] attribute = new int[]{R.attr.colorAccent};
        TypedArray typedArray = getContext().obtainStyledAttributes(typedValue.resourceId,attribute);
        int colorAccent = typedArray.getColor(0,-1);
        typedArray.recycle();

        tv_name = (TextView) popupView.findViewById(R.id.name);
        btn_show_info = (Button) popupView.findViewById(R.id.show_info);
        btn_delete = (Button) popupView.findViewById(R.id.delete);
        btn_share = (Button) popupView.findViewById(R.id.share);

        row = (TableRow) popupView.findViewById(R.id.tableRow);
        row1 = (TableRow) popupView.findViewById(R.id.tableRow1);
        row2 = (TableRow) popupView.findViewById(R.id.tableRow2);
        row3 = (TableRow) popupView.findViewById(R.id.tableRow3);

        phone_type = (TextView) popupView.findViewById(R.id.phoneType);
        tv_phone = (TextView) popupView.findViewById(R.id.phoneNum);
        phone_type1 = (TextView) popupView.findViewById(R.id.phoneType1);
        tv_phone1 = (TextView) popupView.findViewById(R.id.phoneNum1);
        phone_type2 = (TextView) popupView.findViewById(R.id.phoneType2);
        tv_phone2 = (TextView) popupView.findViewById(R.id.phoneNum2);
        phone_type3 = (TextView) popupView.findViewById(R.id.phoneType3);
        tv_phone3 = (TextView) popupView.findViewById(R.id.phoneNum3);

        tv_phone.setTextColor(colorAccent);
        tv_phone1.setTextColor(colorAccent);
        tv_phone2.setTextColor(colorAccent);
        tv_phone3.setTextColor(colorAccent);

        btn_call_phone = (Button) popupView.findViewById(R.id.call_phone);
        btn_send_message = (Button) popupView.findViewById(R.id.send_message);
        btn_call_phone1 = (Button) popupView.findViewById(R.id.call_phone1);
        btn_send_message1 = (Button) popupView.findViewById(R.id.send_message1);
        btn_call_phone2 = (Button) popupView.findViewById(R.id.call_phone2);
        btn_send_message2 = (Button) popupView.findViewById(R.id.send_message2);
        btn_call_phone3 = (Button) popupView.findViewById(R.id.call_phone3);
        btn_send_message3 = (Button) popupView.findViewById(R.id.send_message3);


        tv_name.setText(sortModel.getName());

        ContactModel contactModel = ContactHelper.infoContact(context.getContentResolver(),_ID);
        final ArrayList<PhoneModel> phoneModels = contactModel.getPhoneModels();
        int size = phoneModels.size();

        switch (size){
            case 1:
                row1.setVisibility(View.INVISIBLE);
                row2.setVisibility(View.INVISIBLE);
                row3.setVisibility(View.INVISIBLE);
                tv_phone.setText(phoneModels.get(0).getPhoneNumber());
                phone_type.setText(phoneModels.get(0).getPhoneType());
                btn_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(0).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(0).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 2:
                row2.setVisibility(View.INVISIBLE);
                row3.setVisibility(View.INVISIBLE);
                tv_phone.setText(phoneModels.get(0).getPhoneNumber());
                phone_type.setText(phoneModels.get(0).getPhoneType());
                btn_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(0).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(0).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });
                tv_phone1.setText(phoneModels.get(1).getPhoneNumber());
                phone_type1.setText(phoneModels.get(1).getPhoneType());
                btn_call_phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(1).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(1).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 3:
                row3.setVisibility(View.INVISIBLE);

                tv_phone.setText(phoneModels.get(0).getPhoneNumber());
                phone_type.setText(phoneModels.get(0).getPhoneType());
                btn_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(0).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(0).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });


                tv_phone1.setText(phoneModels.get(1).getPhoneNumber());
                phone_type1.setText(phoneModels.get(1).getPhoneType());
                btn_call_phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(1).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(1).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });


                tv_phone2.setText(phoneModels.get(2).getPhoneNumber());
                phone_type2.setText(phoneModels.get(2).getPhoneType());
                btn_call_phone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(2).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(2).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });
                break;
            case 4:
                tv_phone.setText(phoneModels.get(0).getPhoneNumber());
                phone_type.setText(phoneModels.get(0).getPhoneType());
                btn_call_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(0).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(0).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });


                tv_phone1.setText(phoneModels.get(1).getPhoneNumber());
                phone_type1.setText(phoneModels.get(1).getPhoneType());
                btn_call_phone1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(1).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(1).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });


                tv_phone2.setText(phoneModels.get(2).getPhoneNumber());
                phone_type2.setText(phoneModels.get(2).getPhoneType());
                btn_call_phone2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(2).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(2).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });


                tv_phone3.setText(phoneModels.get(3).getPhoneNumber());
                phone_type3.setText(phoneModels.get(3).getPhoneType());
                btn_call_phone3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneModels.get(3).getPhoneNumber());
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
                        getContext().startActivity(intent);
                    }
                });
                btn_send_message3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+phoneModels.get(3).getPhoneNumber());
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "");
                        getContext().startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }


        btn_show_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id",_ID);
                if (0 != theme){
                    Bundle bundle = new Bundle();
                    bundle.putInt("theme",theme);
                    intent.putExtras(bundle);
                }
                intent.setClass(getContext(), ContactDetailActivity.class);
                getContext().startActivity(intent);
                dismiss();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPopup dialogPopup = new DialogPopup(context,_ID,theme);
                dialogPopup.showPopupWindow();
                dismiss();
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePopup sharePopup = new SharePopup(context,_ID);
                sharePopup.showPopupWindow();
                dismiss();
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
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_slide_from_bottom, null);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }


}
