package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/27.
 */
public class ReportInfo {
    private int userCount;//	Integer	用户数
    private double sgtcAmount;//	Double	施工提成
    private double xstcAmount;//	Double	销售提成

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public double getSgtcAmount() {
        return sgtcAmount;
    }

    public void setSgtcAmount(double sgtcAmount) {
        this.sgtcAmount = sgtcAmount;
    }

    public double getXstcAmount() {
        return xstcAmount;
    }

    public void setXstcAmount(double xstcAmount) {
        this.xstcAmount = xstcAmount;
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
                "userCount=" + userCount +
                ", sgtcAmount=" + sgtcAmount +
                ", xstcAmount=" + xstcAmount +
                '}';
    }
}
