package com.ruanyun.chezhiyi.commonlib.model;

import android.text.TextWatcher;

/**
 * Description:
 * author: zhangsan on 16/10/27 下午8:08.
 */
public class DistrCommissionModel {

    /**
     * userNum : 协助人员编号
     * userName : 协助人员姓名
     * commissionAmount : 提成金额
     */

    private String userNum;
    private String userName;
    private double commissionAmount;
    private TextWatcher itemTextWatcher;

    public TextWatcher getItemTextWatcher() {
        return itemTextWatcher;
    }

    public void setItemTextWatcher(TextWatcher itemTextWatcher) {
        this.itemTextWatcher = itemTextWatcher;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    @Override
    public String toString() {
        return "\nDistrCommissionModel{" +
                "userNum='" + userNum + '\'' +
                ", userName='" + userName + '\'' +
                ", commissionAmount=" + commissionAmount +
                '}';
    }
}
