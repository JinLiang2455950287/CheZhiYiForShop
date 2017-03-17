package com.ruanyun.chezhiyi.commonlib.model.params;

import com.ruanyun.chezhiyi.commonlib.model.ProjectType;

import java.math.BigDecimal;

/**
 * Description ：获取优惠券(司机端)
 * <p/>
 * Created by hdl on 2016/9/20.
 */
public class DiscountCouponParams {
    /**YHQ 获取所有的优惠卷（我的优惠卷管理中使用） YHQ_01 获取满减卷在付款时需要 YHQ_02 获取抵扣卷*/
    private String goodsType;//  YHJ 获取所有的优惠卷（我的优惠卷管理中使用） YHJ_01 获取满减卷在付款时需要 YHJ_02 获取抵扣    卷
    /**1未消费 2 已消费 3已过期*/
    private Integer goodsDetailStatus;
    /**付款金额 在下订单时选择优惠券使用*/
    private BigDecimal amount;
    /**服务类型*/
    private String projectNum;

    public static final String ALL_YHQ = "YHQ";
    /**抵扣券*/
    public static final String YHQ_01 = "YHQ_01";
    /**满减券*/
    public static final String YHQ_02 = "YHQ_02";

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getGoodsDetailStatus() {
        return goodsDetailStatus;
    }

    public void setGoodsDetailStatus(Integer goodsDetailStatus) {
        this.goodsDetailStatus = goodsDetailStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }
}
