package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;

import java.math.BigDecimal;

/**
 * Description ：众筹信息数据结构
 * <p/>
 * Created by hdl on 2016/9/12.
 */
public class CrowdFundingInfo implements IGoodsInfo {
    private int crowdInfoId;        //主键
    private String crowdNum;        //业务主键
    private String storeNum;            //店铺编号【暂无】
    private String goodsNum;            //商品编号
    private String beginTime;           //开始日期
    private String endTime;         //截止日期
    private String title;           //标题
    private String viceTitle;           //副标题
    private BigDecimal marketPrice;         //市场价
    private BigDecimal salePrice;           //销售价
    private double activityPrice;           //众筹价
    private String graphicDetail;           //图文详情
    private String productSpecificat;           //产品规格
    private String raiseNotice;         //众筹须知
    private int partNum;            //参与人数
    private int fullNum;            //满多少人即售
    private String mainPhoto;           //主图
    private String userNum;     //创建编号
    private String createTime;      //创建时间
    private String status;      //状态(1众筹成功 2，众筹失败  3、已上架 众筹中)
    private String goodsType;       //商品类型
    private String projectNum;      //服务
    private String orderNum;        //众筹订单编号
    private int residueDay;     // 剩余天数
    private double progress;     //显示进度

    public static final String UNDERWAY = "3";//进行中
    public static final String ENDED = "1,2,21,22";//已结束
    public static final String SUCCEED = "1";//众筹成功
    public static final String FAIL = "2";//众筹失败

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getResidueDay() {
        return residueDay;
    }

    public void setResidueDay(int residueDay) {
        this.residueDay = residueDay;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
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

    public int getCrowdInfoId() {
        return crowdInfoId;
    }

    public void setCrowdInfoId(int crowdInfoId) {
        this.crowdInfoId = crowdInfoId;
    }

    public String getCrowdNum() {
        return crowdNum;
    }

    public void setCrowdNum(String crowdNum) {
        this.crowdNum = crowdNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    @Override
    public String getCommonNum() {
        return crowdNum;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getGraphicDetail() {
        return graphicDetail;
    }

    public void setGraphicDetail(String graphicDetail) {
        this.graphicDetail = graphicDetail;
    }

    public String getProductSpecificat() {
        return productSpecificat;
    }

    public void setProductSpecificat(String productSpecificat) {
        this.productSpecificat = productSpecificat;
    }

    public String getRaiseNotice() {
        return raiseNotice;
    }

    public void setRaiseNotice(String raiseNotice) {
        this.raiseNotice = raiseNotice;
    }

    public int getPartNum() {
        return partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }

    public int getFullNum() {
        return fullNum;
    }

    public void setFullNum(int fullNum) {
        this.fullNum = fullNum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CrowdFundingInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.crowdInfoId);
        dest.writeString(this.crowdNum);
        dest.writeString(this.storeNum);
        dest.writeString(this.goodsNum);
        dest.writeString(this.beginTime);
        dest.writeString(this.endTime);
        dest.writeString(this.title);
        dest.writeString(this.viceTitle);
        dest.writeSerializable(this.marketPrice);
        dest.writeSerializable(this.salePrice);
        dest.writeDouble(this.activityPrice);
        dest.writeString(this.graphicDetail);
        dest.writeString(this.productSpecificat);
        dest.writeString(this.raiseNotice);
        dest.writeInt(this.partNum);
        dest.writeInt(this.fullNum);
        dest.writeString(this.mainPhoto);
        dest.writeString(this.userNum);
        dest.writeString(this.createTime);
        dest.writeString(this.status);
        dest.writeString(this.goodsType);
        dest.writeString(this.projectNum);
        dest.writeString(this.orderNum);
    }

    protected CrowdFundingInfo(Parcel in) {
        this.crowdInfoId = in.readInt();
        this.crowdNum = in.readString();
        this.storeNum = in.readString();
        this.goodsNum = in.readString();
        this.beginTime = in.readString();
        this.endTime = in.readString();
        this.title = in.readString();
        this.viceTitle = in.readString();
        this.marketPrice = (BigDecimal) in.readSerializable();
        this.salePrice = (BigDecimal) in.readSerializable();
        this.activityPrice = in.readDouble();
        this.graphicDetail = in.readString();
        this.productSpecificat = in.readString();
        this.raiseNotice = in.readString();
        this.partNum = in.readInt();
        this.fullNum = in.readInt();
        this.mainPhoto = in.readString();
        this.userNum = in.readString();
        this.createTime = in.readString();
        this.status = in.readString();
        this.goodsType = in.readString();
        this.projectNum = in.readString();
        this.orderNum = in.readString();
    }

    public static final Creator<CrowdFundingInfo> CREATOR = new Creator<CrowdFundingInfo>() {
        @Override
        public CrowdFundingInfo createFromParcel(Parcel source) {
            return new CrowdFundingInfo(source);
        }

        @Override
        public CrowdFundingInfo[] newArray(int size) {
            return new CrowdFundingInfo[size];
        }
    };
}
