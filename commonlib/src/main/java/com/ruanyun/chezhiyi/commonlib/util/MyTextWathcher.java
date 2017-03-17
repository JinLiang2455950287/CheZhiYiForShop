package com.ruanyun.chezhiyi.commonlib.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Description:
 * author: zhangsan on 16/9/24 下午4:18.
 */
public class MyTextWathcher implements TextWatcher {
    int viewId;
    TextChangeLisener textChangeLisener;
   public MyTextWathcher(int viewId,TextChangeLisener textChangeLisener) {
        this.viewId = viewId;
        this.textChangeLisener=textChangeLisener;
    }

    //public abstract void onTextChanged(int viewId,CharSequence s);
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(textChangeLisener!=null)
            textChangeLisener.onTextChanged(s,viewId);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setTextChangeLisener(TextChangeLisener textChangeLisener) {
        this.textChangeLisener = textChangeLisener;
    }

    public interface TextChangeLisener{
         void onTextChanged(CharSequence s,int viewId);
    }
}
