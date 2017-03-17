package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;

/**
 * Description ：退款的实体类
 * <p/>
 * Created by ycw on 2016/9/22.
 */
public class RefundInfo {
    public static final int REFUND_STATUS_SUCCESS = 1;//退款成功
    public static final int REFUND_STATUS_LOADING = 2;//退款中
    public static final int REFUND_STATUS_FAIL = 3;//退款失败

    private int refundApplicationId;//  	Integer   	主键
    private String refundApplicationNum;// 	String    	业务主键
    private String storeNum;//             	String    	店铺编号
    private String orderNum ;//            	String    	订单编号
    private int refundStatus;//         	Integer   	字典表T_ORDER_REFUND_APPLICATION_STATUS    //1 退款成功 2退款中 3 退款失败
    private String refundReason;//         	String    	退款原因
    private BigDecimal refundPrice;//          	BigDecimal	退款金额
    private String refundRemark;//         	String    	退款备注
    private String refundTime;//           	Date      	退款时间
    private String auditTime;//           	Date      	退款时间
    private String userNum;//              	String    	账号编号
    private String userName;//             	String    	用户名称


    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
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

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
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

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
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
}
