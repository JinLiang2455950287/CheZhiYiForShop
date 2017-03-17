package com.ruanyun.chezhiyi.commonlib.model.params;

import java.math.BigDecimal;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/19.
 */
public class ExchangeParams {
    private String orderRemark;//      	String    	备注
    private String orderType;//        	String    	订单类型 字典表 t_order_info_order_type
    private String commonNum;//        	String    	公共编号 例如 产品填写goodNum报名填写的是
    private String signNum;// 秒杀填写的是seckillDetailNum
    private String storeNum ;//        	String    	门店编号【暂无使用】
    private Integer totalCount;//       	Integer   	数量
    private BigDecimal singlePrice;//      	BigDecimal	单价
    private BigDecimal totalPrice;//       	BigDecimal	总价
    private BigDecimal preferentialPrice;//	BigDecimal	兑换所需积分
    private String couponNum;//        	String    	优惠卷编号
    private BigDecimal actualPrice;//      	BigDecimal	实际支付金额
    private String goodsNum;//	String	商品编号

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }
}
