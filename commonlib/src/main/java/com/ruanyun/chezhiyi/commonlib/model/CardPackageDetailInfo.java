package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by czy on 2017/3/21.
 * 卡套餐详情
 */

public class CardPackageDetailInfo {


    /**
     * createTime : 2017-03-21 14:33:16
     * goodsList : [{"createTime":null,"goodsCount":"","goodsId":25,"goodsName":"新一波洗车狂潮","goodsNum":"SP28050000000327","goodsPrice":50,"mainPhoto":"","packageNum":"","surplusCount":0}]
     * packageCommMoney : 100
     * packageCommRatio : 50
     * packageCommission : 1
     * packageCost : 250
     * packageExpiryDate : 12
     * packageId : 30
     * packageName : 最新洗車套餐
     * packageNum : KTC37370000000030
     * packagePrice : 200
     * packageSale : 0
     * packageStatus : 1
     */

    private String createTime;
    private int packageCommMoney;
    private int packageCommRatio;
    private int packageCommission;
    private int packageCost;
    private int packageExpiryDate;
    private int packageId;
    private String packageName;
    private String packageNum;
    private BigDecimal packagePrice;
    private int packageSale;
    private int packageStatus;
    private List<GoodsListBean> goodsList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPackageCommMoney() {
        return packageCommMoney;
    }

    public void setPackageCommMoney(int packageCommMoney) {
        this.packageCommMoney = packageCommMoney;
    }

    public int getPackageCommRatio() {
        return packageCommRatio;
    }

    public void setPackageCommRatio(int packageCommRatio) {
        this.packageCommRatio = packageCommRatio;
    }

    public int getPackageCommission() {
        return packageCommission;
    }

    public void setPackageCommission(int packageCommission) {
        this.packageCommission = packageCommission;
    }

    public int getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(int packageCost) {
        this.packageCost = packageCost;
    }

    public int getPackageExpiryDate() {
        return packageExpiryDate;
    }

    public void setPackageExpiryDate(int packageExpiryDate) {
        this.packageExpiryDate = packageExpiryDate;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(String packageNum) {
        this.packageNum = packageNum;
    }

    public BigDecimal getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(BigDecimal packagePrice) {
        this.packagePrice = packagePrice;
    }

    public int getPackageSale() {
        return packageSale;
    }

    public void setPackageSale(int packageSale) {
        this.packageSale = packageSale;
    }

    public int getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(int packageStatus) {
        this.packageStatus = packageStatus;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }



    @Override
    public String toString() {
        return "CardPackageDetailInfo{" +
                "createTime='" + createTime + '\'' +
                ", packageCommMoney=" + packageCommMoney +
                ", packageCommRatio=" + packageCommRatio +
                ", packageCommission=" + packageCommission +
                ", packageCost=" + packageCost +
                ", packageExpiryDate=" + packageExpiryDate +
                ", packageId=" + packageId +
                ", packageName='" + packageName + '\'' +
                ", packageNum='" + packageNum + '\'' +
                ", packagePrice=" + packagePrice +
                ", packageSale=" + packageSale +
                ", packageStatus=" + packageStatus +
                ", goodsList=" + goodsList +
                '}';
    }
}
