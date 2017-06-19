package com.qq.singleangelvip.contact.popupwindows;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.activities.MainActivity;
import com.qq.singleangelvip.contact.contacts.ContactHelper;
import com.qq.singleangelvip.contact.contacts.ContactModel;
import com.qq.singleangelvip.contact.qrcard.core.CallingCard;
import com.qq.singleangelvip.contact.wxapi.WXEntryActivity;

/**
 * Created by singl on 2017/4/4.
 */
public class SharePopup extends BasePopupWindow{
    private Button wechat;
    private Button close;
    private ImageView qr_card;
    private TextView title;

    public SharePopup(final Activity context, final Long _ID) {
        super(context);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorAccent,typedValue,true);
        int[] attribute = new int[]{R.attr.colorAccent};
        TypedArray typedArray = getContext().obtainStyledAttributes(typedValue.resourceId,attribute);
        int colorAccent = typedArray.getColor(0,-1);
        typedArray.recycle();

        title = (TextView) findViewById(R.id.title);
        title.setTextColor(colorAccent);
        wechat = (Button) findViewById(R.id.btn_wechat);
        close = (Button) findViewById(R.id.btn_close);
        qr_card = (ImageView) findViewById(R.id.qr_card);

        final ContactModel contactModel = ContactHelper.infoContact(context.getContentResolver(),_ID);
        CallingCard callingCard = new CallingCard();
        callingCard.setName(contactModel.get("name"));
        callingCard.setNickName(contactModel.get("nickName"));
        callingCard.setNote(contactModel.get("note"));
        callingCard.setIm(contactModel.get("im"));
        callingCard.setCompany(contactModel.get("company"));
        if (contactModel.getPhoneModels().size() >0){
            callingCard.setTel(contactModel.getPhoneModels().get(0).getPhoneNumber());
        }
        if (contactModel.getAddressModels().size() >0){
            callingCard.setAddress(contactModel.getAddressModels().get(0).getAddress());
        }
        if (contactModel.getEmailModels().size() >0){
            callingCard.setEmail(contactModel.getEmailModels().get(0).getEmailAddress());
        }

        try {
           Bitmap bitmap = callingCard.Creat2DCode(callingCard.toString());
            qr_card.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name",contactModel.get("name"));
                intent.putExtra("phone",contactModel.getPhoneModels().get(0).getPhoneNumber());
                intent.setClass(context, WXEntryActivity.class);
                context.startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set=new AnimationSet(false);
        Animation shakeAnima=new RotateAnimation(0,15,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_share);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

}
