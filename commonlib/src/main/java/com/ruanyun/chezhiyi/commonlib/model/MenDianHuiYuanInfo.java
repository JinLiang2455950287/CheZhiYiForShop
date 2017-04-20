package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by czy on 2017/4/20.
 * 门店会员
 */

public class MenDianHuiYuanInfo {

    /**
     * memberCardCount : 42
     * memberCardAmount : 20219.53
     * accountBalance : 3081478.2
     * userCenterCount : 269
     * userCenterAmount : 546
     * userCardAmount : 546
     */

    private int memberCardCount;
    private double memberCardAmount;
    private double accountBalance;
    private int userCenterCount;
    private int userCenterAmount;
    private int userCardAmount;

    public int getMemberCardCount() {
        return memberCardCount;
    }

    public void setMemberCardCount(int memberCardCount) {
        this.memberCardCount = memberCardCount;
    }

    public double getMemberCardAmount() {
        return memberCardAmount;
    }

    public void setMemberCardAmount(double memberCardAmount) {
        this.memberCardAmount = memberCardAmount;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getUserCenterCount() {
        return userCenterCount;
    }

    public void setUserCenterCount(int userCenterCount) {
        this.userCenterCount = userCenterCount;
    }

    public int getUserCenterAmount() {
        return userCenterAmount;
    }

    public void setUserCenterAmount(int userCenterAmount) {
        this.userCenterAmount = userCenterAmount;
    }

    public int getUserCardAmount() {
        return userCardAmount;
    }

    public void setUserCardAmount(int userCardAmount) {
        this.userCardAmount = userCardAmount;
    }

    @Override
    public String toString() {
        return "MenDianHuiYuanInfo{" +
                "memberCardCount=" + memberCardCount +
                ", memberCardAmount=" + memberCardAmount +
                ", accountBalance=" + accountBalance +
                ", userCenterCount=" + userCenterCount +
                ", userCenterAmount=" + userCenterAmount +
                ", userCardAmount=" + userCardAmount +
                '}';
    }
}
