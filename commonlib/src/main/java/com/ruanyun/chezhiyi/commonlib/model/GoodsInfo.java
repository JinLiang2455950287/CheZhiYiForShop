package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;

/**
 * Description ：商品信息
 * <p/>
 * Created by ycw on 2016/9/20.
 */
public class GoodsInfo implements IGoodsInfo {

    private String commonNum ="";   //  公共编号 例如 产品填写goodNum报名填写的是 signNum 秒杀填写的是seckillDetailNum
    private String goodsNum ="";    //  商品编号
    private String orderType ="";   //  商品类型  提交易订单的  订单类型
    private String goodsName ="";       //	String	标题
    private double activityPrice;   //	double	实际需要付款的  单价
    private String projectNum ="";
    private String projectParent ="";
    private double scoreNumber;     //积分兑换用
    private String mainPhoto ="";       //  图片
    private int totalCount;         //  数量
    private String commonFlag  ="1";         //  可以购买1  不允许购买 2
    private int totalCountSurplus;
    private String viceTitle="";       // 副标题


    public GoodsInfo() {
    }

    public GoodsInfo(String commonNum,
            String goodsNum,
            String orderType,
            String goodsName,
            double activityPrice,
            String projectNum,
            String mainPhoto,
            String commonFlag,
            String viceTitle) {
        this.commonNum = commonNum;
        this.goodsNum = goodsNum;
        this.orderType = orderType;
        this.goodsName = goodsName;
        this.activityPrice = activityPrice;
        this.projectNum = projectNum;
        this.projectParent = projectNum;
        this.mainPhoto = mainPhoto;
        this.commonFlag = commonFlag;
        this.viceTitle = viceTitle;
    }

    private boolean isPrice = false;

    public static final String TYPE_NORMAL = "normal";
    public static final String TYPE_FAVORABLE = "favorable";
    public static final String TYPE_COLOR = "Color";
    public static final String TYPE_INTEGRAL = "integral";

    private String priceType = TYPE_NORMAL;

    public GoodsInfo(String commonNum, String goodsNum, String orderType, String goodsName,
                     double activityPrice, String projectNum, String mainPhoto) {
        this.commonNum = commonNum;
        this.goodsNum = goodsNum;
        this.orderType = orderType;
        this.goodsName = goodsName;
        this.activityPrice = activityPrice;
        this.projectNum = projectNum;
        this.projectParent = projectNum;
        this.mainPhoto = mainPhoto;
    }

    public String getViceTitle() {
        return viceTitle;
    }

    public void setViceTitle(String viceTitle) {
        this.viceTitle = viceTitle;
    }

    public int getTotalCountSurplus() {
        return totalCountSurplus;
    }

    public void setTotalCountSurplus(int totalCountSurplus) {
        this.totalCountSurplus = totalCountSurplus;
    }

    public String getCommonFlag() {
        return commonFlag;
    }

    public void setCommonFlag(String commonFlag) {
        this.commonFlag = commonFlag;
    }

    public double getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(double scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getProjectNum() {
        return projectParent;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectParent() {
        return projectParent;
    }

    public void setProjectParent(String projectParent) {
        this.projectParent = projectParent;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public boolean isPrice() {
        return isPrice;
    }

    public void setPrice(boolean price) {
        isPrice = price;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    @Override
    public String getGoodsType() {
        return orderType;
    }

    //    @Override
//    public String getSubmitGoodsType() {
//        return AppUtility.getTypeName(orderType);
//    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commonNum);
        dest.writeString(this.goodsNum);
        dest.writeString(this.orderType);
        dest.writeString(this.goodsName);
        dest.writeDouble(this.activityPrice);
        dest.writeString(this.projectParent);
        dest.writeDouble(this.scoreNumber);
        dest.writeString(this.mainPhoto);
        dest.writeInt(this.totalCount);
        dest.writeString(this.commonFlag);
        dest.writeInt(this.totalCountSurplus);
        dest.writeString(this.viceTitle);
        dest.writeByte(this.isPrice ? (byte) 1 : (byte) 0);
        dest.writeString(this.priceType);
    }

    protected GoodsInfo(Parcel in) {
        this.commonNum = in.readString();
        this.goodsNum = in.readString();
        this.orderType = in.readString();
        this.goodsName = in.readString();
        this.activityPrice = in.readDouble();
        this.projectParent = in.readString();
        this.scoreNumber = in.readDouble();
        this.mainPhoto = in.readString();
        this.totalCount = in.readInt();
        this.commonFlag = in.readString();
        this.totalCountSurplus = in.readInt();
        this.viceTitle = in.readString();
        this.isPrice = in.readByte() != 0;
        this.priceType = in.readString();
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        @Override
        public GoodsInfo createFromParcel(Parcel source) {return new GoodsInfo(source);}

        @Override
        public GoodsInfo[] newArray(int size) {return new GoodsInfo[size];}
    };
}