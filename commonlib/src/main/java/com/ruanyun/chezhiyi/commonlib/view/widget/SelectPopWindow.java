package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.util.C;

import butterknife.ButterKnife;

/**
 * Created by Sxq on 2016/9/10.
 */
public abstract class SelectPopWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private Context mContext;

    public SelectPopWindow(Context context) {
        this.mContext = context;
        initView();
    }

    private void initView() {
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color
                .transparent)));
        setOutsideTouchable(false);
        setFocusable(true);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popupwindow_anim_style);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_select_sex, null);
        TextView tvCancel = ButterKnife.findById(view, R.id.tv_cancel);
        final TextView tvFemail = ButterKnife.findById(view, R.id.tv_female);
        final TextView tvMale = ButterKnife.findById(view, R.id.tv_male);
        tvFemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getSex(tvFemail.getText().toString(), C.USER_SEX_FEMALE);
            }
        });
        tvMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSex(tvMale.getText().toString(), C.USER_SEX_MAN);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(view);
        //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setOnDismissListener(this);
    }

    protected abstract void getSex(String s,int i);

    public void showBottom(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.6f);
        //   5.1  有效
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }

}
