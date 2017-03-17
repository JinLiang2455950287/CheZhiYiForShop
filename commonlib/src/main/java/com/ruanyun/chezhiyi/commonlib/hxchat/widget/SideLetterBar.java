package com.ruanyun.chezhiyi.commonlib.hxchat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;


/**
 * Created by Administrator on 2015/8/5.
 */
public class SideLetterBar extends View {
    //触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    private TextView mTextDialog;

    public static String[] header = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "W", "X", "Y", "Z"};
    private int choose = -1;//选中的位置
    private Paint paint = new Paint();

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
    }

    //设置字母提醒

    /**
     * 为SideBar设置显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取焦点背景改变颜色
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / header.length;

        for (int i = 0; i < header.length; i++) {
            paint.setColor(getResources().getColor(R.color.text_default));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(24);

            //如果是选中状态
            if (i == choose) {
                paint.setColor(getResources().getColor(R.color.text_gray));
                paint.setFakeBoldText(true);
            }

            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(header[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(header[i], xPos, yPos, paint);
            paint.reset();//重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y / getHeight() * header.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.color.transparent);
                choose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(INVISIBLE);
                }
                break;
            default:
                setBackgroundResource(R.color.half_transparent_black);
                if (oldChoose != c) {
                    if (c >= 0 && c < header.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(header[c].toLowerCase());
                        }
                        if (mTextDialog != null) {
                            //mTextDialog.setText(header[c]);这句做了滑动提醒后就不需要了
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

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
