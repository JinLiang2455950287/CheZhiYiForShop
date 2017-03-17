package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 弹出框
 * Created by wp on 2016/10/12.
 */
public abstract class CurrencyPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener {
    private Context mContext;
    private TextView tvTitle;
    private TextView tvLeftTitle;
    private TextView tvRightTitle;
    private String leftTitle;//
    private String rightTitle;
    private String title;//中间标题
    private EditText editContent;
    private String hintContent;//editView 提示
    private Button btnSend;
    private String btnText;//button上的文字
    private int titleTextColor;//标题字体颜色

    private boolean state = false;//控制button  显示、隐藏

    public CurrencyPopupWindow(Context context, boolean states) {
        super(context);
        this.mContext = context;
        this.state = states;
        initView();
    }
//    public CurrencyPopupWindow(Context context,String rightTitles,String titles,String leftTitles) {
//        super(context);
//        this.mContext = context;
//        this.rightTitle=rightTitles;
//        this.title=titles;
//        this.leftTitle=leftTitles;
//        initView();
//    }

    private void initView() {
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color
                .transparent)));
        setOutsideTouchable(false);
        setFocusable(true);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popupwindow_anim_style);
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_currency, null);

        Button btSubmit = ButterKnife.findById(view, R.id.bt_submit);
        btSubmit.setText(btnText);

        btSubmit.setOnClickListener(this);
        if (state) {
            btSubmit.setVisibility(View.VISIBLE);
        } else {
            btSubmit.setVisibility(View.GONE);
        }
        tvTitle = ButterKnife.findById(view, R.id.tv_title);
        tvTitle.setTextColor(titleTextColor);
        tvLeftTitle = ButterKnife.findById(view, R.id.tv_left_title);
        tvRightTitle = ButterKnife.findById(view, R.id.tv_right_title);
        editContent = ButterKnife.findById(view, R.id.edit_content);
        tvRightTitle.setText(rightTitle);
        tvLeftTitle.setText(leftTitle);
        editContent.setHint(hintContent);
        tvTitle.setText(title);
        setContentView(view);
        tvRightTitle.setOnClickListener(rightOnClick(editContent.getText().toString()));
//        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setOnDismissListener(this);
    }

    protected abstract View.OnClickListener rightOnClick(String s);


    //    private int getColors(int colorId) {
//        return mContext.getResources().getColor(colorId);
//    }
    public int getTitleTextColor() {
        return titleTextColor;
    }

    public CurrencyPopupWindow setTitleTextColor(int titleTextColor) {
        tvTitle.setTextColor(titleTextColor);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CurrencyPopupWindow setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public CurrencyPopupWindow setLeftTitle(String leftTitle) {
        tvLeftTitle.setText(leftTitle);
        return this;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public CurrencyPopupWindow setRightTitle(String rightTitle) {
        tvRightTitle.setText(rightTitle);
        return this;
    }

    public CurrencyPopupWindow setEditHintContent(String editHintContent) {
        editContent.setText(editHintContent);
        return this;
    }

    public String getBtnText() {
        return btnText;
    }

    public CurrencyPopupWindow setBtnText(String btnText) {
        btnSend.setText(btnText);
        return this;
    }

    public void showBottom(View parent) {
        showAtLocation(parent, Gravity.CENTER, 0, 0);
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

    // button点击事件
    @Override
    public void onClick(View v) {
        returnResult(editContent.getText().toString());
        dismiss();
    }

    protected abstract void returnResult(String s);

}
