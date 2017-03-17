package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.ruanyun.chezhiyi.commonlib.R;

import java.lang.ref.WeakReference;


/**
 * @ClassName: ValidCodeButton.java
 * @Description: 发送验证码自动倒计时按钮
 * @author Daniel
 * @date 2016/2/22 16:23
 */
public class ValidCodeButton extends Button {
    ValidCodeCountDownTimer timer;

   // Context mContext;

    public ValidCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
       // mContext = context;
        timer = new ValidCodeCountDownTimer(60000, 1000, this);
    }


    public void resetTimer(){
        setText(getResources().getString(R.string.get_verification_code_hint));
        timer.cancel();
        setClickable(true);
        setSelected(false);
    }

    public void canCel(){
        timer.cancel();

    }

    public void startCountDownTimer() {
        timer.start();
    }

    /* 定义一个倒计时的内部类 */
    static class ValidCodeCountDownTimer extends CountDownTimer {

        WeakReference<ValidCodeButton>  weakReference;
        public ValidCodeCountDownTimer(long millisInFuture, long countDownInterval, ValidCodeButton button) {
            super(millisInFuture, countDownInterval);
            weakReference = new WeakReference<ValidCodeButton>(button);
        }

        @Override
        public void onFinish() {
            ValidCodeButton button = weakReference.get();
            if (button != null) {
                button.setText(button.getContext().getString(R.string.get_code_again, ""));
                button.setClickable(true);
                button.setSelected(false);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            ValidCodeButton button = weakReference.get();
            if (button != null) {
                button.setText(button.getContext().getString(R.string.get_code_again, "(" + millisUntilFinished / 1000 + ")"));
                button.setClickable(false);
                button.setSelected(true);
            }
        }
    }
}
