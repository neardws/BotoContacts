package com.qq.singleangelvip.contact.contactselector;

/**
 * Created by singl on 2017/4/1.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.qq.singleangelvip.contact.R;


public class SideBar extends View {
    //触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    //26个字母
    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    /**
     *为SideBar设置显示字母的TextView
     */
    public void setTextView(TextView mTextDialog){
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
    }

    public SideBar(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public SideBar(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);//获取焦点改变背景颜色

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height/b.length;//获取每一个字母的高度

        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorAccent,typedValue,true);
        int[] attribute = new int[]{R.attr.colorAccent};
        TypedArray typedArray = getContext().obtainStyledAttributes(typedValue.resourceId,attribute);
        int colorAccent = typedArray.getColor(0,-1);
        typedArray.recycle();
        for(int i = 0; i < b.length; i++){

            paint.setColor(colorAccent);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            //选中状态
            if(i == choose){
                paint.setColor(Color.parseColor("#ffffff"));
                paint.setFakeBoldText(true);
            }
            //x坐标等于中间-字符串宽度的一半
            float xPos = width / 2 - paint.measureText(b[i])/2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int)(y / getHeight() * b.length); //点击y坐标所占总高度的比例*b数组的长度等于点击b中的个数

        switch (action){
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x000000));
                choose = -1;
                invalidate();
                if (mTextDialog != null){
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                if(oldChoose != c){
                    if(c >= 0 && c < b.length){
                        if (listener != null){
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null){
                            mTextDialog.setText(b[c]);
                            //mTextDialog.setTextColor(getResources().getColor(android.support.design.R.color.primary_dark_material_dark));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }

                        choose = c;
                        invalidate();
                    }
                }
                break;
        }

        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener){
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);
    }
}