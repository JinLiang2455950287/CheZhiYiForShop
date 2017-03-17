package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/9/12.
 */
public class RecommendInfo implements Parcelable {

    private int recommendProjectId;//	Int	主键
    private String recommentProjectNum;//	String	业务主键
    private String goodsNum;//	String	关联num【产品业务主键】
    private int sortNum;//	Int	排序
    private int isEnable;//	Int	是否启用 1-启用 2-禁用
    private String beginTime;//	Date	开始时间【暂无用处，预留】
    private String endTime;//String	截止时间【暂无用处，预留】
    private String title;//	String	标题
    private String mainPhoto;//	String	图片【列表显示图片】
    private String typeName;//	String	类型名称
    private BigDecimal marketPrice;//	BigDecimal	市场价【java 对应的金钱类型 小数点两位 】
    private double salePrice;//	BigDecimal	销售价【java 对应的金钱类型 小数点两位 】
    private BigDecimal seckillPrice;//	BigDecimal	秒杀价【java 对应的金钱类型 小数点两位 】
    private int saleCount;//	Integer	销售量
    private int commentCount;//	Integer	评论量
    private String createTime;//	Date	创建时间
    private String userNum;//String	用户编号
    private String beginDate;//             	 String    	秒杀开始日期（促销开始日期）
    private String endDate;//               	 String    	秒杀截止日期（促销截止日期）
    private String seckillBeginDate;//	date	秒杀开始日期（促销开始日期）【yyyy-MM-dd】
    private String seckillEndDate;//	date	秒杀结束日期（促销结束日期）【yyyy-MM-dd】
    private String seckillBeginTime;//	String	秒杀开始时段【HH:mm:dd】
    private String seckillEndTime;//	String	秒杀结束时段【HH:mm:dd】
    private String recommentProjectType;//	String	所属分类（1、推荐项目  2、猜你喜欢）
    private String goodsType;//	Int	商品类型【】
    private double activityPrice;//	活动价

    //商品类型【字典表T_GOODS_RECOMMENT_PROJECT_GOODS_TYPE】
    public static final String GOODS_TYPE_TG = "TG"; //TG团购
    public static final String GOODS_TYPE_CP = "CP";// CP产品
    public static final String GOODS_TYPE_MS = "MS";// MS秒杀
    public static final String GOODS_TYPE_CX = "CX";// CX促销
    public static final String GOODS_TYPE_ZC = "ZC";// 众筹


    public double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public int getRecommendProjectId() {
        return recommendProjectId;
    }

    public void setRecommendProjectId(int recommendProjectId) {
        this.recommendProjectId = recommendProjectId;
    }

    public String getRecommentProjectNum() {
        return recommentProjectNum;
    }

    public void setRecommentProjectNum(String recommentProjectNum) {
        this.recommentProjectNum = recommentProjectNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
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

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public BigDecimal getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(BigDecimal seckillPrice) {
        this.seckillPrice = seckillPrice;
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

    public String getSeckillBeginDate() {
        return seckillBeginDate;
    }

    public void setSeckillBeginDate(String seckillBeginDate) {
        this.seckillBeginDate = seckillBeginDate;
    }

    public String getSeckillEndDate() {
        return seckillEndDate;
    }

    public void setSeckillEndDate(String seckillEndDate) {
        this.seckillEndDate = seckillEndDate;
    }

    public String getSeckillBeginTime() {
        return seckillBeginTime;
    }

    public void setSeckillBeginTime(String seckillBeginTime) {
        this.seckillBeginTime = seckillBeginTime;
    }

    public String getSeckillEndTime() {
        return seckillEndTime;
    }

    public void setSeckillEndTime(String seckillEndTime) {
        this.seckillEndTime = seckillEndTime;
    }

    public String getRecommentProjectType() {
        return recommentProjectType;
    }

    public void setRecommentProjectType(String recommentProjectType) {
        this.recommentProjectType = recommentProjectType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public RecommendInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.recommendProjectId);
        dest.writeString(this.recommentProjectNum);
        dest.writeString(this.goodsNum);
        dest.writeInt(this.sortNum);
        dest.writeInt(this.isEnable);
        dest.writeString(this.beginTime);
        dest.writeString(this.endTime);
        dest.writeString(this.title);
        dest.writeString(this.mainPhoto);
        dest.writeString(this.typeName);
        dest.writeSerializable(this.marketPrice);
        dest.writeDouble(this.salePrice);
        dest.writeSerializable(this.seckillPrice);
        dest.writeInt(this.saleCount);
        dest.writeInt(this.commentCount);
        dest.writeString(this.createTime);
        dest.writeString(this.userNum);
        dest.writeString(this.beginDate);
        dest.writeString(this.endDate);
        dest.writeString(this.seckillBeginDate);
        dest.writeString(this.seckillEndDate);
        dest.writeString(this.seckillBeginTime);
        dest.writeString(this.seckillEndTime);
        dest.writeString(this.recommentProjectType);
        dest.writeString(this.goodsType);
        dest.writeDouble(this.activityPrice);
    }

    protected RecommendInfo(Parcel in) {
        this.recommendProjectId = in.readInt();
        this.recommentProjectNum = in.readString();
        this.goodsNum = in.readString();
        this.sortNum = in.readInt();
        this.isEnable = in.readInt();
        this.beginTime = in.readString();
        this.endTime = in.readString();
        this.title = in.readString();
        this.mainPhoto = in.readString();
        this.typeName = in.readString();
        this.marketPrice = (BigDecimal) in.readSerializable();
        this.salePrice = in.readDouble();
        this.seckillPrice = (BigDecimal) in.readSerializable();
        this.saleCount = in.readInt();
        this.commentCount = in.readInt();
        this.createTime = in.readString();
        this.userNum = in.readString();
        this.beginDate = in.readString();
        this.endDate = in.readString();
        this.seckillBeginDate = in.readString();
        this.seckillEndDate = in.readString();
        this.seckillBeginTime = in.readString();
        this.seckillEndTime = in.readString();
        this.recommentProjectType = in.readString();
        this.goodsType = in.readString();
        this.activityPrice = in.readDouble();
    }

    public static final Creator<RecommendInfo> CREATOR = new Creator<RecommendInfo>() {
        @Override
        public RecommendInfo createFromParcel(Parcel source) {
            return new RecommendInfo(source);
        }

        @Override
        public RecommendInfo[] newArray(int size) {
            return new RecommendInfo[size];
        }
    };
}
