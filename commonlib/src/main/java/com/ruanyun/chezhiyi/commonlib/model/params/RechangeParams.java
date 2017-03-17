package com.ruanyun.chezhiyi.commonlib.model.params;

import java.math.BigDecimal;

/**
 * 充值参数
 * Created by Sxq on 2016/10/10.
 */
public class RechangeParams {
    /** 充值套餐编号，如果是手工输入充值金额则为null**/
    private String discountMealNum;
    /** 充值金额**/
    private BigDecimal amount;
    /** 赠送积分**/
    private int score;
    /**充值用户编号 **/
    private String userNum;

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
}
