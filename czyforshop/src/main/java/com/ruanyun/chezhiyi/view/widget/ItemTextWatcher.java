package com.ruanyun.chezhiyi.view.widget;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.ruanyun.chezhiyi.commonlib.model.DistrCommissionModel;

/**
 * 列表中的文本框文字改变监听
 */
public class ItemTextWatcher implements TextWatcher {

    DistrCommissionModel commissionModel;
    private AfterMoneyChanged afterTextChanged;

    public ItemTextWatcher(DistrCommissionModel item, AfterMoneyChanged afterTextChanged) {
        this.commissionModel = item;
        this.afterTextChanged = afterTextChanged;
    }

    public ItemTextWatcher() {
    }

    public void setCommissionModel(DistrCommissionModel commissionModel) {
        this.commissionModel = commissionModel;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    @Override
    public void afterTextChanged(Editable s) {
        commissionModel.setCommissionAmount(Double.parseDouble(TextUtils.isEmpty(s.toString()) ?
                "0" : s.toString().equals(".") ? "0":s.toString()));
        if (afterTextChanged != null) {
            afterTextChanged.afterChanged();
        }
    }
}
