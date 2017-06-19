package com.qq.singleangelvip.contact.popupwindows;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.qq.singleangelvip.contact.R;
import com.qq.singleangelvip.contact.activities.MainActivity;
import com.qq.singleangelvip.contact.contacts.ContactHelper;

/**
 * Created by singl on 2017/4/4.
 */
public class DialogPopup extends BasePopupWindow{

    private TextView ok;
    private TextView cancel;
    private TextView title;

    public DialogPopup(Activity context, final Long _ID, final int theme) {
        super(context);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorAccent,typedValue,true);
        int[] attribute = new int[]{R.attr.colorAccent};
        TypedArray typedArray = getContext().obtainStyledAttributes(typedValue.resourceId,attribute);
        int colorAccent = typedArray.getColor(0,-1);
        typedArray.recycle();
        title = (TextView) findViewById(R.id.title);
        ok= (TextView) findViewById(R.id.ok);
        title.setTextColor(colorAccent);
        ok.setTextColor(colorAccent);
        cancel= (TextView) findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactHelper.deleteContact(getContext().getContentResolver(),_ID);
                Toast.makeText(getContext(),"删除成功", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                if (0 != theme){
                    Bundle bundle = new Bundle();
                    bundle.putInt("theme",theme);
                    intent1.putExtras(bundle);
                }
                intent1.setClass(getContext(), MainActivity.class);
                getContext().startActivity(intent1);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
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
        return createPopupById(R.layout.popup_dialog);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

}