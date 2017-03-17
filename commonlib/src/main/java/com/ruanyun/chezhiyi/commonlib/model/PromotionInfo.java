package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;

import java.math.BigDecimal;

/**
 * 促销主表数据结构
 * Created by hdl on 2016/9/12
 */
public class PromotionInfo implements IGoodsInfo{
  private int promotionInfoId;//主键
  private String promotionInfoNum;//业务主键
  private String goodsNum;//商品编号
  private String goodsType;//商品类型
  private String title;//标题
  private String viceTitle;//副标题
  private String promotionBeginDate;//促销开始日期
  private String promotionEndDate;//促销截止日期
  private String mainPhoto;//图片
  private BigDecimal marketPrice;//市场价
  private BigDecimal salePrice;//销售价
  private double activityPrice;//促销价
  private int activityCount;//活动量
  private int saleCount;//销售量
  private int sortNum;//排序
  private int isHome;//是否首页显示
  private int isEnable;//是否启用
  private int commentCount;//评论量
  private String createTime;//创建时间
  private String userNum;//创建人编号
  private String projectNum;//服务


    public int getPromotionInfoId() {
        return promotionInfoId;
    }

    public void setPromotionInfoId(int promotionInfoId) {
        this.promotionInfoId = promotionInfoId;
    }

    public String getPromotionInfoNum() {
        return promotionInfoNum;
    }

    public void setPromotionInfoNum(String promotionInfoNum) {
        this.promotionInfoNum = promotionInfoNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    @Override
    public String getCommonNum() {
        return promotionInfoNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

//    @Override
//    public String getSubmitGoodsType() {
//        return AppUtility.getTypeName(goodsType);
//    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsType() {
        return goodsType;
    }

    @Override
    public String getGoodsName() {
        return title;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViceTitle() {
        return viceTitle;
    }

    public void setViceTitle(String viceTitle) {
        this.viceTitle = viceTitle;
    }

    public String getPromotionBeginDate() {
        return promotionBeginDate;
    }

    public void setPromotionBeginDate(String promotionBeginDate) {
        this.promotionBeginDate = promotionBeginDate;
    }

    public String getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(String promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    @Override
    public double getScoreNumber() {
        return 0;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getIsHome() {
        return isHome;
    }

    public void setIsHome(int isHome) {
        this.isHome = isHome;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public PromotionInfo() {}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.promotionInfoId);
        dest.writeString(this.promotionInfoNum);
        dest.writeString(this.goodsNum);
        dest.writeString(this.goodsType);
        dest.writeString(this.title);
        dest.writeString(this.viceTitle);
        dest.writeString(this.promotionBeginDate);
        dest.writeString(this.promotionEndDate);
        dest.writeString(this.mainPhoto);
        dest.writeSerializable(this.marketPrice);
        dest.writeSerializable(this.salePrice);
        dest.writeDouble(this.activityPrice);
        dest.writeInt(this.activityCount);
        dest.writeInt(this.saleCount);
        dest.writeInt(this.sortNum);
        dest.writeInt(this.isHome);
        dest.writeInt(this.isEnable);
        dest.writeInt(this.commentCount);
        dest.writeString(this.createTime);
        dest.writeString(this.userNum);
        dest.writeString(this.projectNum);
    }

    protected PromotionInfo(Parcel in) {
        this.promotionInfoId = in.readInt();
        this.promotionInfoNum = in.readString();
        this.goodsNum = in.readString();
        this.goodsType = in.readString();
        this.title = in.readString();
        this.viceTitle = in.readString();
        this.promotionBeginDate = in.readString();
        this.promotionEndDate = in.readString();
        this.mainPhoto = in.readString();
        this.marketPrice = (BigDecimal) in.readSerializable();
        this.salePrice = (BigDecimal) in.readSerializable();
        this.activityPrice = in.readDouble();
        this.activityCount = in.readInt();
        this.saleCount = in.readInt();
        this.sortNum = in.readInt();
        this.isHome = in.readInt();
        this.isEnable = in.readInt();
        this.commentCount = in.readInt();
        this.createTime = in.readString();
        this.userNum = in.readString();
        this.projectNum = in.readString();
    }

    public static final Creator<PromotionInfo> CREATOR = new Creator<PromotionInfo>() {
        @Override
        public PromotionInfo createFromParcel(Parcel source) {return new PromotionInfo(source);}

        @Override
        public PromotionInfo[] newArray(int size) {return new PromotionInfo[size];}
    };
}
