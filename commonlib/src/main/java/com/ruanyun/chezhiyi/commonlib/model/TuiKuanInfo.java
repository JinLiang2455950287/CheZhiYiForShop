package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by 创智 on 2017/3/10.
 * 退款审核
 */

public class TuiKuanInfo {


    /**
     * auditTime : 2017-01-04 16:34:42
     * flag1 :
     * flag2 :
     * flag3 :
     * orderNum : OR83060000004687
     * refundApplicationId : 42
     * refundApplicationNum : OFP66500000000042
     * refundPrice : 195
     * refundReason : 商品价格与市场不符
     * refundRemark : 哈哈哈
     * refundStatus : 3
     * refundTime : 2017-01-04 16:33:47
     * startTime : null
     * storeNum :
     * user : null
     * userName :
     * userNum : sys94650000010821
     */

    private String auditTime;
    private String flag1;
    private String flag2;
    private String flag3;
    private String orderNum;
    private int refundApplicationId;
    private String refundApplicationNum;
    private int refundPrice;
    private String refundReason;
    private String refundRemark;
    private int refundStatus;
    private String refundTime;
    private Object startTime;
    private String storeNum;
    private Object user;
    private String userName;
    private String userNum;

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getRefundApplicationId() {
        return refundApplicationId;
    }

    public void setRefundApplicationId(int refundApplicationId) {
        this.refundApplicationId = refundApplicationId;
    }

    public String getRefundApplicationNum() {
        return refundApplicationNum;
    }

    public void setRefundApplicationNum(String refundApplicationNum) {
        this.refundApplicationNum = refundApplicationNum;
    }

    public int getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(int refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Override
    public String toString() {
        return "TuiKuanInfo{" +
                "auditTime='" + auditTime + '\'' +
                ", flag1='" + flag1 + '\'' +
                ", flag2='" + flag2 + '\'' +
                ", flag3='" + flag3 + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", refundApplicationId=" + refundApplicationId +
                ", refundApplicationNum='" + refundApplicationNum + '\'' +
                ", refundPrice=" + refundPrice +
                ", refundReason='" + refundReason + '\'' +
                ", refundRemark='" + refundRemark + '\'' +
                ", refundStatus=" + refundStatus +
                ", refundTime='" + refundTime + '\'' +
                ", startTime=" + startTime +
                ", storeNum='" + storeNum + '\'' +
                ", user=" + user +
                ", userName='" + userName + '\'' +
                ", userNum='" + userNum + '\'' +
                '}';
    }
}
