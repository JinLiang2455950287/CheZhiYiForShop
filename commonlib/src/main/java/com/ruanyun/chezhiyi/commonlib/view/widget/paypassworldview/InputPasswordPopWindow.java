package com.ruanyun.chezhiyi.commonlib.view.widget.paypassworldview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;


/**
 * 输入密码popWindow
 * Created by hdl on 2016/11/8.
 */

public abstract class InputPasswordPopWindow extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener {
    private Context mContext;
    private String title;
    private PayView payView;
    private View view;
    private int forgetPsw = View.INVISIBLE;

    public InputPasswordPopWindow(Context context, String title) {
        this.mContext = context;
        this.title = title;
        initView();
    }

    public InputPasswordPopWindow(Context context, String title, int forgetPsw) {
        this.forgetPsw = forgetPsw;
        this.mContext = context;
        this.title = title;
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

        view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_input_pwd, null);
        payView = (PayView) view.findViewById(R.id.payView);
        payView.setOnFinishInput(new PayView.OnPasswordInputFinish() {
            @Override
            public void inputFinish(String passStr) {
                passwordInputAccomplish(passStr);
                dismiss();
            }
        });
        setForgetPswVisibility(forgetPsw);
        ImageView ivCancel = payView.getCancel();
        TextView tvForgetPwd = payView.getForgetPsw();
        TextView tvTitle = payView.getTitle();
        tvTitle.setText(title);
        ivCancel.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
        setContentView(view);
        setOnDismissListener(this);
    }

    public void showBottom(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.6f);
        //   5.1  有效
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public abstract void passwordInputAccomplish(String password);

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
        payView.clearPassword();
        backgroundAlpha(1f);
    }

    /**
     * 忘记密码的  visibility
     *
     * @param visibility
     */
    public void setForgetPswVisibility(int visibility) {
        payView.getForgetPsw().setVisibility(visibility);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_pay_back) {
            dismiss();
        } else if (id == R.id.tv_pay_forgetPwd) {
            if (App.getInstance().isClient()) {
//                Intent intent = new Intent(mContext, VerifyPayActivity.class);
//                intent.putExtra(C.IntentKey.TYPE, VerifyPayActivity.RESET_PASSWORD);
//                mContext.startActivity(intent);
            }
        }
    }
}
