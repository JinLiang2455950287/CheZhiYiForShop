package com.ruanyun.chezhiyi.commonlib.model.params;

import java.math.BigDecimal;

/**
 * 申请退款
 * Created by Sxq on 2016/9/22.
 */
public class RefudApplicationParams {

    /**订单编号**/
    private String orderNum;
    /**退款原因**/
    private String refundReason;
    /**退款金额**/
    private BigDecimal refundPrice;
    /**退款备注**/
    private String refundRemark;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public BigDecimal getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(BigDecimal refundPrice) {
        this.refundPrice = refundPrice;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }
}
