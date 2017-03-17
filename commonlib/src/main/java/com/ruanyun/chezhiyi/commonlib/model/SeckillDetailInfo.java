package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;

import java.math.BigDecimal;

/**
 * Description ：秒杀产品信息
 * <p/>
 * Created by hdl on 2016/9/13.
 */
public class SeckillDetailInfo implements IGoodsInfo {
    private int seckillDetailId;//主键
    private String seckillDetailNum;//编号
    private String seckillMainInfoNum;//主表编号 秒杀主表数据结构
    private String title;//标题
    private String viceTitle;//副标题
    private String goodsNum;//商品num
    private String goodsType;//商品类型
    private String mainPhoto;//主图
    private BigDecimal salePrice;//销售价
    private BigDecimal marketPrice;//市场价
    private double activityPrice;//秒杀价
    private int activityCount;//秒杀数量
    private int saleCount;//已秒杀量
    private int commentCount;//评论量
    private int isEnable;//是否启用
    private String createTime;//创建时间
    private String userNum;//用户编号
    private int sortNum;//排序
    private String projectNum;//服务


    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public int getSeckillDetailId() {
        return seckillDetailId;
    }

    public void setSeckillDetailId(int seckillDetailId) {
        this.seckillDetailId = seckillDetailId;
    }

    public String getSeckillDetailNum() {
        return seckillDetailNum;
    }

    public void setSeckillDetailNum(String seckillDetailNum) {
        this.seckillDetailNum = seckillDetailNum;
    }

    public String getSeckillMainInfoNum() {
        return seckillMainInfoNum;
    }

    public void setSeckillMainInfoNum(String seckillMainInfoNum) {
        this.seckillMainInfoNum = seckillMainInfoNum;
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

    @Override
    public String getCommonNum() {
        return seckillDetailNum;
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
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

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.seckillDetailId);
        dest.writeString(this.seckillDetailNum);
        dest.writeString(this.seckillMainInfoNum);
        dest.writeString(this.title);
        dest.writeString(this.viceTitle);
        dest.writeString(this.goodsNum);
        dest.writeString(this.goodsType);
        dest.writeString(this.mainPhoto);
        dest.writeSerializable(this.salePrice);
        dest.writeSerializable(this.marketPrice);
        dest.writeDouble(this.activityPrice);
        dest.writeInt(this.activityCount);
        dest.writeInt(this.saleCount);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.isEnable);
        dest.writeString(this.createTime);
        dest.writeString(this.userNum);
        dest.writeInt(this.sortNum);
        dest.writeString(this.projectNum);
    }

    public SeckillDetailInfo() {}

    protected SeckillDetailInfo(Parcel in) {
        this.seckillDetailId = in.readInt();
        this.seckillDetailNum = in.readString();
        this.seckillMainInfoNum = in.readString();
        this.title = in.readString();
        this.viceTitle = in.readString();
        this.goodsNum = in.readString();
        this.goodsType = in.readString();
        this.mainPhoto = in.readString();
        this.salePrice = (BigDecimal) in.readSerializable();
        this.marketPrice = (BigDecimal) in.readSerializable();
        this.activityPrice = in.readDouble();
        this.activityCount = in.readInt();
        this.saleCount = in.readInt();
        this.commentCount = in.readInt();
        this.isEnable = in.readInt();
        this.createTime = in.readString();
        this.userNum = in.readString();
        this.sortNum = in.readInt();
        this.projectNum = in.readString();
    }

    public static final Creator<SeckillDetailInfo> CREATOR = new Creator<SeckillDetailInfo>() {
        @Override
        public SeckillDetailInfo createFromParcel(Parcel source) {return new SeckillDetailInfo(source);}

        @Override
        public SeckillDetailInfo[] newArray(int size) {return new SeckillDetailInfo[size];}
    };
}
