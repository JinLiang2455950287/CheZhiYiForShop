package com.ruanyun.chezhiyi.commonlib.model.params;

import java.util.List;

/**
 * Description ：结算工单时 ，下订单的参数
 * <p/>
 * Created by ycw on 2016/11/2.
 */

public class SettleWorkOrderInfo {

    /**
     * workOrderNumString : 要结算的工单编号，英文逗号拼接
     * payTotalAmount : 页面计算的支付总金额
     * userNum : 司机userNum
     * payWorkOrders : [{"workOrderNum":"工单编号","payAmount":"需要支付的金额，在get_jiesuan中获取后直接传入",
     * "couponNum":"使用的优惠券编号","preferentialPrice":"优惠金额"}]
     */

    private String workOrderNumString="";
    private String payTotalAmount="0";
    private String userNum="";
    private String couponNum="";
    private String preferentialPrice="0";
    /**
     * workOrderNum : 工单编号
     * payAmount : 需要支付的金额，在get_jiesuan中获取后直接传入
     * couponNum : 使用的优惠券编号
     * preferentialPrice : 优惠金额
     */

    private List<PayWorkOrdersBean> payWorkOrders;

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

    public List<PayWorkOrdersBean> getPayWorkOrders() {
        return payWorkOrders;
    }

    public void setPayWorkOrders(List<PayWorkOrdersBean> payWorkOrders) {
        this.payWorkOrders = payWorkOrders;
    }

    public static class PayWorkOrdersBean {
        private String workOrderNum="";
        private String payAmount="0";
        private String couponNum="";
        private String preferentialPrice="";

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
    }
}
