package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by czy on 2017/4/6.
 */

public class PayWorkOrdersBean {
    /**
     * workOrderNum : 工单编号
     * payAmount : 需要支付的金额，在get_jiesuan中获取后直接传入
     * couponNum : 使用的优惠券编号
     * preferentialPrice : 优惠金额
     */

    private String workOrderNum;
    private String payAmount;
    private String couponNum;
    private String preferentialPrice;

    public PayWorkOrdersBean(String workOrderNum, String payAmount, String couponNum, String preferentialPrice) {
        this.workOrderNum = workOrderNum;
        this.payAmount = payAmount;
        this.couponNum = couponNum;
        this.preferentialPrice = preferentialPrice;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    @Override
    public String toString() {
        return "{" +
                "workOrderNum'"+":" + workOrderNum + '\'' +
                ", payAmount'"+":" + payAmount + '\'' +
                ", couponNum'"+":" + couponNum + '\'' +
                ", preferentialPrice:'"+":" + preferentialPrice + '\'' +
                '}';
    }
}
