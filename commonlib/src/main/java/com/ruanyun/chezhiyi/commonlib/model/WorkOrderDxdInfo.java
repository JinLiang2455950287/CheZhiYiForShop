package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;

/**
 * Description ：代下单的商品数据结构
 * <p/>
 * Created by hdl on 2016/10/12.
 */
@Deprecated
public class WorkOrderDxdInfo {
    private int workOrderDxdId;
    /**工单编号*/
    private String workOrderNum;
    /**商品编号 是父类时为工单状态*/
    private String goodsNum;
    /**总数量*/
    private int totalCount;
    /**单价*/
    private BigDecimal singlePrice;
    /**总金额*/
    private BigDecimal totalPrice;
    /**用户编号*/
    private String userNum;
    /**创建时间*/
    private String createTime;
    /**商品主图*/
    private String mainPhoto;
    /**商品名称*/
    private String goodsName;

    /**是否父类 (代下单管理中使用)*/
    private boolean isParent = false;

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public int getWorkOrderDxdId() {
        return workOrderDxdId;
    }

    public void setWorkOrderDxdId(int workOrderDxdId) {
        this.workOrderDxdId = workOrderDxdId;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
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

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    public WorkOrderDxdInfo() {
    }

}
