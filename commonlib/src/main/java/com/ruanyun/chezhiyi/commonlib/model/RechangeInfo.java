package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;

/**
 * 充值实体类
 * Created by Sxq on 2016/10/10.
 */
public class RechangeInfo {
    private OrderInfo orderInfo;
    /**充值套餐编号 **/
    private String discountMealNum;
    /** 充值金额**/
    private BigDecimal amount;
    /** 赠送积分**/
    private int score;
    /**充值用户编号 **/
    private String userNum;
    /** 时间**/
    private String createTime;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getDiscountMealNum() {
        return discountMealNum;
    }

    public void setDiscountMealNum(String discountMealNum) {
        this.discountMealNum = discountMealNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
