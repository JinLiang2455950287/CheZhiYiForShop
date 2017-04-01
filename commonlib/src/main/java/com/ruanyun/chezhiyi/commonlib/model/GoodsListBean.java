package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by czy on 2017/3/21.
 */


public class GoodsListBean {
    /**
     * createTime : null
     * goodsCount :
     * goodsId : 25
     * goodsName : 新一波洗车狂潮
     * goodsNum : SP28050000000327
     * goodsPrice : 50
     * mainPhoto :
     * packageNum :
     * surplusCount : 0
     */

    private Object createTime;
    private String goodsCount;
    private int goodsId;
    private String goodsName;
    private String goodsNum;
    private int goodsPrice;
    private String mainPhoto;
    private String packageNum;
    private int surplusCount;

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(String packageNum) {
        this.packageNum = packageNum;
    }

    public int getSurplusCount() {
        return surplusCount;
    }

    public void setSurplusCount(int surplusCount) {
        this.surplusCount = surplusCount;
    }

    @Override
    public String toString() {
        return "GoodsListBean{" +
                "createTime=" + createTime +
                ", goodsCount='" + goodsCount + '\'' +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", packageNum='" + packageNum + '\'' +
                ", surplusCount=" + surplusCount +
                '}';
    }
}
