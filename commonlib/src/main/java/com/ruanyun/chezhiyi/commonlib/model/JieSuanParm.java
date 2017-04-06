package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by czy on 2017/4/6.
 * 结算para
 */

public class JieSuanParm {

    /**
     * workOrderNumString : 要结算的工单编号，英文逗号拼接
     * payTotalAmount : 页面计算的支付总金额
     * userNum : 司机userNum
     * couponNum : 全场通用优惠券
     * preferentialPrice : 优惠金额
     * payWorkOrders : [{"workOrderNum":"工单编号","payAmount":"需要支付的金额，在get_jiesuan中获取后直接传入","couponNum":"使用的优惠券编号","preferentialPrice":"优惠金额"}]
     */

    private String workOrderNumString;
    private String payTotalAmount;
    private String userNum;
    private String couponNum;
    private String preferentialPrice;
    private List<PayWorkOrdersBean> payWorkOrders;

    public JieSuanParm() {
    }

    public String getWorkOrderNumString() {
        return workOrderNumString;
    }

    public void setWorkOrderNumString(String workOrderNumString) {
        this.workOrderNumString = workOrderNumString;
    }

    public String getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(String payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
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

    public List<PayWorkOrdersBean> getPayWorkOrders() {
        return payWorkOrders;
    }

    public void setPayWorkOrders(List<PayWorkOrdersBean> payWorkOrders) {
        this.payWorkOrders = payWorkOrders;
    }


    @Override
    public String toString() {
        return "{" +
                "workOrderNumString'" +":"+ workOrderNumString + '\'' +
                ", payTotalAmount'" +":"+ payTotalAmount + '\'' +
                ", userNum'" +":"+ userNum + '\'' +
                ", couponNum'" +":"+ couponNum + '\'' +
                ", preferentialPrice'"+":" + preferentialPrice + '\'' +
                ", payWorkOrders" + payWorkOrders +
                '}';
    }
}
