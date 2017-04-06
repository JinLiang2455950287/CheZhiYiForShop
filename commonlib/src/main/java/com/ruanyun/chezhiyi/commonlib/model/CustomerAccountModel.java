package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by czy on 2017/4/6.
 * 获取会员余额
 */

public class CustomerAccountModel {


    /**
     * accountBalance : 999912
     * flag1 :
     * flag2 :
     * flag3 :
     * payPassword : 96e79218965eb72c92a549dd5a330112
     * scoreBalance : 999999
     * userCenterId : 80
     * userCenterNum : UC85180000000080
     * userLevel : 0
     * userNum : sys40830000010909
     */

    private int accountBalance;
    private String flag1;
    private String flag2;
    private String flag3;
    private String payPassword;
    private int scoreBalance;
    private int userCenterId;
    private String userCenterNum;
    private int userLevel;
    private String userNum;

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public int getScoreBalance() {
        return scoreBalance;
    }

    public void setScoreBalance(int scoreBalance) {
        this.scoreBalance = scoreBalance;
    }

    public int getUserCenterId() {
        return userCenterId;
    }

    public void setUserCenterId(int userCenterId) {
        this.userCenterId = userCenterId;
    }

    public String getUserCenterNum() {
        return userCenterNum;
    }

    public void setUserCenterNum(String userCenterNum) {
        this.userCenterNum = userCenterNum;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Override
    public String toString() {
        return "CustomerAccountModel{" +
                "accountBalance=" + accountBalance +
                ", flag1='" + flag1 + '\'' +
                ", flag2='" + flag2 + '\'' +
                ", flag3='" + flag3 + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", scoreBalance=" + scoreBalance +
                ", userCenterId=" + userCenterId +
                ", userCenterNum='" + userCenterNum + '\'' +
                ", userLevel=" + userLevel +
                ", userNum='" + userNum + '\'' +
                '}';
    }
}
