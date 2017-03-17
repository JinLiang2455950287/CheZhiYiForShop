package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：下单参数
 * <p>
 * Created by ycw on 2016/9/20.
 */
public class MakeOrderParams {
    private String orderType;//        	String    	订单类型 字典表 t_order_info_order_type
    private String commonNum;//        	String    	公共编号 例如 产品填写goodNum报名填写的是 String signNum; 秒杀填写的是seckillDetailNum
    private String storeNum;//         	String    	门店编号【暂无使用】
    private Integer totalCount;//       	Integer   	数量
    private String singlePrice;//      	BigDecimal	单价
    private String totalPrice;//       	BigDecimal	总价
    private String preferentialPrice;//	BigDecimal	优惠金额
    private String couponNum;//        	String    	优惠卷编号
    private String actualPrice;//      	BigDecimal	实际支付金额
    private String goodsNum;//	String	商品编号

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

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }
}
