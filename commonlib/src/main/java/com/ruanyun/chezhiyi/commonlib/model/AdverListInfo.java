package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by msq on 2016/9/9.
 */
public class AdverListInfo {

    public static final int ADVERTYPE_ADVER = 1;
    public static final int ADVERTYPE_OTHER_LINK = 2;
    public static final int ADVERTYPE_GOODSINFO = 3;

    private String adverNum;	//	广告编号
    private int adverType;	//	1-自定义广告 2-跳转外部连接  3-跳转商品详细页面
    private String storeNum;	//	店铺编号【暂无用处】
    private String title;	//	标题
    private String linkUrl;	//	连接地址调整
    private String mainPhoto;	//	主图

    public String getAdverNum() {
        return adverNum;
    }

    public void setAdverNum(String adverNum) {
        this.adverNum = adverNum;
    }

    public int getAdverType() {
        return adverType;
    }

    public void setAdverType(int adverType) {
        this.adverType = adverType;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
}
