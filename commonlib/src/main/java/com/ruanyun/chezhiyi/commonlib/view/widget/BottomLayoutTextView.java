package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.util.LogX;

/**
 * Created by ycw on 2016/8/25.
 */
public class BottomLayoutTextView extends TextView {

    private onCheckChangedListener mListener;

    public BottomLayoutTextView(Context context) {
        this(context, null);
    }

    public BottomLayoutTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomLayoutTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setChecked(boolean flag) {
        setSelected(flag);
        if (mListener == null) {
            LogX.e("BottomLayoutTextView", "BottomLayoutTextView  ----没有设置监听----");
            return;
        }
        if (flag) mListener.onCheckChanged(true, this);
        else mListener.onCheckChanged(false, this);

    }

    public void removeCallback() {
        mListener = null;
    }

    public void setOnCheckChangedListener(onCheckChangedListener changed) {
        this.mListener = changed;
    }

    public interface onCheckChangedListener {
        void onCheckChanged(boolean checked, View view);
    }

}
