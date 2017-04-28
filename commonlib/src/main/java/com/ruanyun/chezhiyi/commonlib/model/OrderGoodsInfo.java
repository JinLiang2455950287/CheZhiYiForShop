package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;

import java.math.BigDecimal;

/**
 * 1.24.2订单信息的商品【团购 秒杀 众筹 产品 优惠卷等有 报名活动 定金等无】
 * Created by Sxq on 2016/9/19.
 */
public class OrderGoodsInfo implements /*Parcelable*/IGoodsInfo {
    private BigDecimal amount;
    private String orderNum;//订单编号
    private String goodsNum;//商品num
    private String storeNum;//门店num
    private String projectParent;//服务项目父分类 工单服务和技师技能 数据结构 一级数据业务主键【projectNum】
    private String projectChild;//服务项目子分类 【子级数据业务主键projectNum】
    private String goodsType;//(CP_01实体商品 TG_01普通团购 ZC_01众筹 YHQ_01优惠券 TG_02特殊团购)YHQ_02满减券 字典表读取
    private String goodsName;//商品名称
    private BigDecimal marketPrice;//市场价【YHQ_02优惠卷为满金额】
    private BigDecimal salePrice;//销售价 销售价【YHQ_02优惠卷为减金额】
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
    private int soldNumber;//已售数量
    private int commentCount;//评论量
    private String createTime;//创建时间
    private String validitTime;//有效期限
    private String validitBeginDate;//优惠卷有效期开始时间
    private String validitEndDate;//优惠卷有效期限结束时间
    private String viceTitle;//副标题
    private String createUserNum;//创建人num
    private String createUserName;//创建人名称
    private double activityPrice;//活动价格【秒杀为秒杀价 团购为团购价】
    private String orderGoodsDetailNum;//可用服务商品编号
    private String content;
    private float level;
    private double sgtcAmount;//施工提成
    private double xstcAmount;//销售提成
    private int workOrderGoodsId;//	 Integer 	主键
    private String workOrderNum;//	 String  	工单编号
    private String totalCount;//	 String  	使用数量
    private double singlePrice;//	BigDecimal	单价
    private double totalPrice;//	BigDecimal	总价
    private String userNum;//	String  	添加人
    private int isDaiXiaDan;//	Integer	是否为代下单商品 1是 2否
    private int isOverdue;//	Integer	是否过期 1是 2否

    private String orderCreateTime;  //   时间
    private String orderUserName;   //	用户名
    private String orderUserNum;   //	用户num

    //  工单详情的商品的详细信息
    private OrderGoodsInfo goodsInfo;

    private int orderStatus;   //	优惠劵的使用状态  大于 2  已使用

    /**
     * 是否父类 (代下单管理中使用)
     */
    private boolean isParent = false;

    /**
     * 是否选中提交
     */
    private boolean isService = false;

    private boolean isSelect = false;//多选优惠券选中状态
    /**
     * 抵扣券
     */
    public static final String REBATE_COUPON = "YHQ_01";

    //    ================================================================
    private int couponCount;//优惠卷可用数量【技师端使用】
    private int couponCountAll;//优惠卷总数量【技师端使用】
    private String userCouponNum;//优惠卷编号【技师端使用】

    public static final String GOODS_TYPE_CP_01 = "CP_01";// 产品
    public static final String GOODS_TYPE_TG_01 = "TG_01"; //团购
    public static final String GOODS_TYPE_TG_02 = "TG_02"; //特殊团购
    /**
     * 商品数量
     */
    private int goodsCount = 0;
    /**
     * 积分
     */
    private double scoreNumber;

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

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public double getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(double scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    //    ===============================================================

    public OrderGoodsInfo getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(OrderGoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderUserNum() {
        return orderUserNum;
    }

    public void setOrderUserNum(String orderUserNum) {
        this.orderUserNum = orderUserNum;
    }

    public int getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public int getWorkOrderGoodsId() {
        return workOrderGoodsId;
    }

    public void setWorkOrderGoodsId(int workOrderGoodsId) {
        this.workOrderGoodsId = workOrderGoodsId;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(double singlePrice) {
        this.singlePrice = singlePrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getIsDaiXiaDan() {
        return isDaiXiaDan;
    }

    public void setIsDaiXiaDan(int isDaiXiaDan) {
        this.isDaiXiaDan = isDaiXiaDan;
    }

    public double getSgtcAmount() {
        return sgtcAmount;
    }

    public void setSgtcAmount(double sgtcAmount) {
        this.sgtcAmount = sgtcAmount;
    }

    public double getXstcAmount() {
        return xstcAmount;
    }

    public void setXstcAmount(double xstcAmount) {
        this.xstcAmount = xstcAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    public String getOrderGoodsDetailNum() {
        return orderGoodsDetailNum;
    }

    public void setOrderGoodsDetailNum(String orderGoodsDetailNum) {
        this.orderGoodsDetailNum = orderGoodsDetailNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
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

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getValiditTime() {
        return validitTime;
    }

    public void setValiditTime(String validitTime) {
        this.validitTime = validitTime;
    }

    public String getViceTitle() {
        return viceTitle;
    }

    public void setViceTitle(String viceTitle) {
        this.viceTitle = viceTitle;
    }

    public String getCreateUserNum() {
        return createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public double getActivityPrice() {
        return activityPrice;
    }

    @Override
    public String getProjectNum() {
        return null;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public OrderGoodsInfo() {
    }


    @Override
    public String toString() {
        return "\nOrderGoodsInfo{" +
                "amount=" + amount +
                ", orderNum='" + orderNum + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                ", storeNum='" + storeNum + '\'' +
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
                ", soldNumber=" + soldNumber +
                ", commentCount=" + commentCount +
                ", createTime='" + createTime + '\'' +
                ", validitTime='" + validitTime + '\'' +
                ", viceTitle='" + viceTitle + '\'' +
                ", createUserNum='" + createUserNum + '\'' +
                ", createUserName='" + createUserName + '\'' +
                ", activityPrice=" + activityPrice +
                ", orderGoodsDetailNum='" + orderGoodsDetailNum + '\'' +
                ", content='" + content + '\'' +
                ", level=" + level +
                ", sgtcAmount=" + sgtcAmount +
                ", xstcAmount=" + xstcAmount +
                ", workOrderGoodsId=" + workOrderGoodsId +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", singlePrice=" + singlePrice +
                ", totalPrice=" + totalPrice +
                ", userNum='" + userNum + '\'' +
                ", isDaiXiaDan=" + isDaiXiaDan +
                ", isSelect=" + isSelect +
                ", isService=" + isService +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.amount);
        dest.writeString(this.orderNum);
        dest.writeString(this.goodsNum);
        dest.writeString(this.storeNum);
        dest.writeString(this.projectParent);
        dest.writeString(this.projectChild);
        dest.writeString(this.goodsType);
        dest.writeString(this.goodsName);
        dest.writeSerializable(this.marketPrice);
        dest.writeSerializable(this.salePrice);
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
        dest.writeInt(this.soldNumber);
        dest.writeInt(this.commentCount);
        dest.writeString(this.createTime);
        dest.writeString(this.validitTime);
        dest.writeString(this.validitBeginDate);
        dest.writeString(this.validitEndDate);
        dest.writeString(this.viceTitle);
        dest.writeString(this.createUserNum);
        dest.writeString(this.createUserName);
        dest.writeDouble(this.activityPrice);
        dest.writeString(this.orderGoodsDetailNum);
        dest.writeString(this.content);
        dest.writeFloat(this.level);
        dest.writeDouble(this.sgtcAmount);
        dest.writeDouble(this.xstcAmount);
        dest.writeInt(this.workOrderGoodsId);
        dest.writeString(this.workOrderNum);
        dest.writeString(this.totalCount);
        dest.writeDouble(this.singlePrice);
        dest.writeDouble(this.totalPrice);
        dest.writeString(this.userNum);
        dest.writeInt(this.isDaiXiaDan);
        dest.writeInt(this.isOverdue);
        dest.writeString(this.orderCreateTime);
        dest.writeString(this.orderUserName);
        dest.writeString(this.orderUserNum);
        dest.writeParcelable(this.goodsInfo, flags);
        dest.writeInt(this.orderStatus);
        dest.writeByte(this.isParent ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isService ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.couponCount);
        dest.writeInt(this.couponCountAll);
        dest.writeString(this.userCouponNum);
        dest.writeInt(this.goodsCount);
        dest.writeDouble(this.scoreNumber);
    }

    protected OrderGoodsInfo(Parcel in) {
        this.amount = (BigDecimal) in.readSerializable();
        this.orderNum = in.readString();
        this.goodsNum = in.readString();
        this.storeNum = in.readString();
        this.projectParent = in.readString();
        this.projectChild = in.readString();
        this.goodsType = in.readString();
        this.goodsName = in.readString();
        this.marketPrice = (BigDecimal) in.readSerializable();
        this.salePrice = (BigDecimal) in.readSerializable();
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
        this.soldNumber = in.readInt();
        this.commentCount = in.readInt();
        this.createTime = in.readString();
        this.validitTime = in.readString();
        this.validitBeginDate = in.readString();
        this.validitEndDate = in.readString();
        this.viceTitle = in.readString();
        this.createUserNum = in.readString();
        this.createUserName = in.readString();
        this.activityPrice = in.readDouble();
        this.orderGoodsDetailNum = in.readString();
        this.content = in.readString();
        this.level = in.readFloat();
        this.sgtcAmount = in.readDouble();
        this.xstcAmount = in.readDouble();
        this.workOrderGoodsId = in.readInt();
        this.workOrderNum = in.readString();
        this.totalCount = in.readString();
        this.singlePrice = in.readDouble();
        this.totalPrice = in.readDouble();
        this.userNum = in.readString();
        this.isDaiXiaDan = in.readInt();
        this.isOverdue = in.readInt();
        this.orderCreateTime = in.readString();
        this.orderUserName = in.readString();
        this.orderUserNum = in.readString();
        this.goodsInfo = in.readParcelable(OrderGoodsInfo.class.getClassLoader());
        this.orderStatus = in.readInt();
        this.isParent = in.readByte() != 0;
        this.isService = in.readByte() != 0;
        this.isSelect = in.readByte() != 0;
        this.couponCount = in.readInt();
        this.couponCountAll = in.readInt();
        this.userCouponNum = in.readString();
        this.goodsCount = in.readInt();
        this.scoreNumber = in.readDouble();
    }

    public static final Creator<OrderGoodsInfo> CREATOR = new Creator<OrderGoodsInfo>() {
        @Override
        public OrderGoodsInfo createFromParcel(Parcel source) {
            return new OrderGoodsInfo(source);
        }

        @Override
        public OrderGoodsInfo[] newArray(int size) {
            return new OrderGoodsInfo[size];
        }
    };
}
