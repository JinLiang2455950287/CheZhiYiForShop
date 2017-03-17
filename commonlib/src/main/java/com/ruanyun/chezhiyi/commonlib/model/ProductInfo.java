package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;

import java.math.BigDecimal;

/**
 * 产品、团购信息数据结构
 * Created by hdl on 2016/9/12
 */
public class ProductInfo implements IGoodsInfo {
    private int goodsId;//主键
    private String goodsNum;//业务主键商品num
    private String projectParent;//服务项目父分类 工单服务和技师技能 数据结构 一级数据业务主键【projectNum】
    private String projectChild;//服务项目子分类 【子级数据业务主键projectNum】
    private String goodsType;//(CP_01实体商品 TG_01普通团购 ZC_01众筹 YHQ_01满减券 TG_02特殊团购)YHQ_02抵扣券 字典表读取
    private String goodsName;//商品名称
    private BigDecimal marketPrice;//市场价
    private double salePrice;//销售价
    private String goodsDescrip;//图文详情
    private String productSpecificat;//产品规格
    private int goodsStatus;//状态 （1上架  2下架 -1删除）
    private String mainPhoto;//主图
    private int isAnytimeCancle;//是否支持随时退(1是 2否)
    private int isOverdueCancle;//是否支持过期退(1是 2否)
    private int sgtcfs;//施工提成方式（1比例 2金额）
    private BigDecimal sgtcbl;//施工提成比例
    private BigDecimal sgtcje;//施工提成金额
    private int xstcfs;//销售提成方式（1比例 2金额）
    private BigDecimal xstcbl;//销售提成比例
    private BigDecimal xstcje;//销售提成金额
    private String userNum;//用户编号
    private int soldNumber;//已售数量
    private int commentCount;//评论量
    private String createTime;//创建时间
    private String viceTitle;//副标题

    private String validitBeginDate;//优惠卷有效期开始时间
    private String validitEndDate;//优惠卷有效期限结束时间
    private int couponCount;//优惠卷可用数量【技师端使用】
    private int couponCountAll;//优惠卷总数量【技师端使用】
    private String userCouponNum;//优惠卷编号【技师端使用】

    public static final String GOODS_TYPE_CP_01 = "CP_01";// 产品
    public static final String GOODS_TYPE_TG_01 = "TG_01"; //团购
    public static final String GOODS_TYPE_TG_02 = "TG_02"; //特殊团购
    /**    抵扣券  */
    public static final String REBATE_COUPON = "YHQ_01";
    /**   商品数量   */
    private int goodsCount = 0;
    /**  积分   */
     private double scoreNumber;

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String getCommonNum() {
        return goodsNum;
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

    public String getProjectParent() {
        return projectParent;
    }

    public void setProjectParent(String projectParent) {
        this.projectParent = projectParent;
    }

    public String getProjectChild() {
        return projectChild;
    }

    public void setProjectChild(String projectChild) {
        this.projectChild = projectChild;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    @Override
    public double getActivityPrice() {
        return salePrice;
    }

    @Override
    public String getProjectNum() {
        return projectParent;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getGoodsDescrip() {
        return goodsDescrip;
    }

    public void setGoodsDescrip(String goodsDescrip) {
        this.goodsDescrip = goodsDescrip;
    }

    public String getProductSpecificat() {
        return productSpecificat;
    }

    public void setProductSpecificat(String productSpecificat) {
        this.productSpecificat = productSpecificat;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public int getIsAnytimeCancle() {
        return isAnytimeCancle;
    }

    public void setIsAnytimeCancle(int isAnytimeCancle) {
        this.isAnytimeCancle = isAnytimeCancle;
    }

    public int getIsOverdueCancle() {
        return isOverdueCancle;
    }

    public void setIsOverdueCancle(int isOverdueCancle) {
        this.isOverdueCancle = isOverdueCancle;
    }

    public int getSgtcfs() {
        return sgtcfs;
    }

    public void setSgtcfs(int sgtcfs) {
        this.sgtcfs = sgtcfs;
    }

    public BigDecimal getSgtcbl() {
        return sgtcbl;
    }

    public void setSgtcbl(BigDecimal sgtcbl) {
        this.sgtcbl = sgtcbl;
    }

    public BigDecimal getSgtcje() {
        return sgtcje;
    }

    public void setSgtcje(BigDecimal sgtcje) {
        this.sgtcje = sgtcje;
    }

    public int getXstcfs() {
        return xstcfs;
    }

    public void setXstcfs(int xstcfs) {
        this.xstcfs = xstcfs;
    }

    public BigDecimal getXstcbl() {
        return xstcbl;
    }

    public void setXstcbl(BigDecimal xstcbl) {
        this.xstcbl = xstcbl;
    }

    public BigDecimal getXstcje() {
        return xstcje;
    }

    public void setXstcje(BigDecimal xstcje) {
        this.xstcje = xstcje;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
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

    public String getViceTitle() {
        return viceTitle;
    }

    public double getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(double scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public void setViceTitle(String viceTitle) {
        this.viceTitle = viceTitle;
    }

    public String getValiditBeginDate() {
        return validitBeginDate;
    }

    public void setValiditBeginDate(String validitBeginDate) {
        this.validitBeginDate = validitBeginDate;
    }

    public String getValiditEndDate() {
        return validitEndDate;
    }

    public void setValiditEndDate(String validitEndDate) {
        this.validitEndDate = validitEndDate;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getCouponCountAll() {
        return couponCountAll;
    }

    public void setCouponCountAll(int couponCountAll) {
        this.couponCountAll = couponCountAll;
    }

    public String getUserCouponNum() {
        return userCouponNum;
    }

    public void setUserCouponNum(String userCouponNum) {
        this.userCouponNum = userCouponNum;
    }

    public ProductInfo() {
    }


    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.goodsId);
        dest.writeString(this.goodsNum);
        dest.writeString(this.projectParent);
        dest.writeString(this.projectChild);
        dest.writeString(this.goodsType);
        dest.writeString(this.goodsName);
        dest.writeSerializable(this.marketPrice);
        dest.writeDouble(this.salePrice);
        dest.writeString(this.goodsDescrip);
        dest.writeString(this.productSpecificat);
        dest.writeInt(this.goodsStatus);
        dest.writeString(this.mainPhoto);
        dest.writeInt(this.isAnytimeCancle);
        dest.writeInt(this.isOverdueCancle);
        dest.writeInt(this.sgtcfs);
        dest.writeSerializable(this.sgtcbl);
        dest.writeSerializable(this.sgtcje);
        dest.writeInt(this.xstcfs);
        dest.writeSerializable(this.xstcbl);
        dest.writeSerializable(this.xstcje);
        dest.writeString(this.userNum);
        dest.writeInt(this.soldNumber);
        dest.writeInt(this.commentCount);
        dest.writeString(this.createTime);
        dest.writeString(this.viceTitle);
        dest.writeString(this.validitBeginDate);
        dest.writeString(this.validitEndDate);
        dest.writeInt(this.couponCount);
        dest.writeInt(this.couponCountAll);
        dest.writeString(this.userCouponNum);
        dest.writeInt(this.goodsCount);
        dest.writeDouble(this.scoreNumber);
    }

    protected ProductInfo(Parcel in) {
        this.goodsId = in.readInt();
        this.goodsNum = in.readString();
        this.projectParent = in.readString();
        this.projectChild = in.readString();
        this.goodsType = in.readString();
        this.goodsName = in.readString();
        this.marketPrice = (BigDecimal) in.readSerializable();
        this.salePrice = in.readDouble();
        this.goodsDescrip = in.readString();
        this.productSpecificat = in.readString();
        this.goodsStatus = in.readInt();
        this.mainPhoto = in.readString();
        this.isAnytimeCancle = in.readInt();
        this.isOverdueCancle = in.readInt();
        this.sgtcfs = in.readInt();
        this.sgtcbl = (BigDecimal) in.readSerializable();
        this.sgtcje = (BigDecimal) in.readSerializable();
        this.xstcfs = in.readInt();
        this.xstcbl = (BigDecimal) in.readSerializable();
        this.xstcje = (BigDecimal) in.readSerializable();
        this.userNum = in.readString();
        this.soldNumber = in.readInt();
        this.commentCount = in.readInt();
        this.createTime = in.readString();
        this.viceTitle = in.readString();
        this.validitBeginDate = in.readString();
        this.validitEndDate = in.readString();
        this.couponCount = in.readInt();
        this.couponCountAll = in.readInt();
        this.userCouponNum = in.readString();
        this.goodsCount = in.readInt();
        this.scoreNumber = in.readDouble();
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel source) {return new ProductInfo(source);}

        @Override
        public ProductInfo[] newArray(int size) {return new ProductInfo[size];}
    };

    @Override
    public String toString() {
        return "ProductInfo{" +
                "goodsId=" + goodsId +
                ", goodsNum='" + goodsNum + '\'' +
                ", projectParent='" + projectParent + '\'' +
                ", projectChild='" + projectChild + '\'' +
                ", goodsType='" + goodsType + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", marketPrice=" + marketPrice +
                ", salePrice=" + salePrice +
                ", goodsDescrip='" + goodsDescrip + '\'' +
                ", productSpecificat='" + productSpecificat + '\'' +
                ", goodsStatus=" + goodsStatus +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", isAnytimeCancle=" + isAnytimeCancle +
                ", isOverdueCancle=" + isOverdueCancle +
                ", sgtcfs=" + sgtcfs +
                ", sgtcbl=" + sgtcbl +
                ", sgtcje=" + sgtcje +
                ", xstcfs=" + xstcfs +
                ", xstcbl=" + xstcbl +
                ", xstcje=" + xstcje +
                ", userNum='" + userNum + '\'' +
                ", soldNumber=" + soldNumber +
                ", commentCount=" + commentCount +
                ", createTime='" + createTime + '\'' +
                ", viceTitle='" + viceTitle + '\'' +
                ", validitBeginDate='" + validitBeginDate + '\'' +
                ", validitEndDate='" + validitEndDate + '\'' +
                ", couponCount=" + couponCount +
                ", couponCountAll=" + couponCountAll +
                ", userCouponNum='" + userCouponNum + '\'' +
                ", goodsCount=" + goodsCount +
                ", scoreNumber=" + scoreNumber +
                '}';
    }
}
