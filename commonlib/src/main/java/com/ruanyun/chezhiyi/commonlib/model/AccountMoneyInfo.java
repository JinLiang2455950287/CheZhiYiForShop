package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：用户账户积分  余额信息
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public class AccountMoneyInfo {

    private String userNum;//	String	用户编号
    private double accountBalance;//	BigDecimal	账号余额
    private double scoreBalance;//	BigDecimal	积分余额
    private String userCenterId;//	String	主键
    private String userCenterNum;//	String	业务编号
    private String payPassword;//	String	支付密码

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getScoreBalance() {
        return scoreBalance;
    }

    public void setScoreBalance(double scoreBalance) {
        this.scoreBalance = scoreBalance;
    }

    public String getUserCenterId() {
        return userCenterId;
    }

    public void setUserCenterId(String userCenterId) {
        this.userCenterId = userCenterId;
    }

    public String getUserCenterNum() {
        return userCenterNum;
    }

    public void setUserCenterNum(String userCenterNum) {
        this.userCenterNum = userCenterNum;
    }
}
