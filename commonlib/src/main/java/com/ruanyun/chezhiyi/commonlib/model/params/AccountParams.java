package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：账户支付的参数
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public class AccountParams {
    //支付类型  //  3 商城购物消费 4 支付预约定金消费 5 活动报名消费 6 工单结算消费
    /**
     * 商城购物消费
     */
    public static final int PAY_TYPE_SHOP  = 3;
    /**
     * 支付预约定金消费
     */
    public static final int PAY_TYPE_DJ  = 4;
    /**
     * 活动报名消费
     */
    public static final int PAY_TYPE_APPLY  = 5;
    /**
     * 工单结算消费
     */
    public static final int PAY_TYPE_WORKINFO  = 6;

    private String orderNum;   //支付订单编号
    //private String userNum;    //支付用户
    private String accountBalance;  //支付金额
    /** 3 商城购物消费 4 支付预约定金消费 5 活动报名消费 6 工单结算消费  */
    private int recordType;   //支付类型  //  3 商城购物消费 4 支付预约定金消费 5 活动报名消费 6 工单结算消费
    private String payPassword;   //支付密码效验

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
