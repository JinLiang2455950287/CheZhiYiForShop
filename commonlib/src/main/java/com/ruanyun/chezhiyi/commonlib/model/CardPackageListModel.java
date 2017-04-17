package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by czy on 2017/3/21.
 * 卡套餐列表
 */

public class CardPackageListModel {


    /**
     * createTime : 2017-03-2817: 05: 13
     * goodsList : []
     * packageCommMoney : 0
     * packageCommRatio : 10
     * packageCommission : 1
     * packageCost : 0
     * packageExpiryDate : 1
     * packageId : 35
     * packageName : 套餐2
     * packageNum : KTC29540000000035
     * packagePrice : 0.01
     * packageSale : 1
     * packageStatus : 1
     */

    private String createTime;
    private String packageOprice;
    private BigDecimal packageCommMoney;
    private int packageCommRatio;
    private int packageCommission;
    private int packageCost;
    private int packageExpiryDate;
    private int packageId;
    private String packageName;
    private String packageNum;
    private double packagePrice;
    private int packageSale;
    private int packageStatus;
    private List<?> goodsList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPackageCommMoney() {
        return packageCommMoney;
    }

    public void setPackageCommMoney(BigDecimal packageCommMoney) {
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

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
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

    public List<?> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<?> goodsList) {
        this.goodsList = goodsList;
    }

    public String getPackageOprice() {
        return packageOprice;
    }

    public void setPackageOprice(String packageOprice) {
        this.packageOprice = packageOprice;
    }

    @Override
    public String toString() {
        return "CardPackageListModel{" +
                "createTime='" + createTime + '\'' +
                ", packageOprice='" + packageOprice + '\'' +
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
