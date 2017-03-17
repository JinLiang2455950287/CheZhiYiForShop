package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by Sxq on 2016/9/1.
 */
public class StoreInfo {
//    storeNum	String	用户编号【暂时默认st30390000000011】
//    sotreName	String	昵称【如果为好友时，该值为好友备注值】
//    FigurePic	String	代表图【file/storeinfo/】
//    backgroundImg	String	背景图【file/storeinfo/】
//    introduce	String	介绍
//    linkTel	String	联系方式
//    longitude	String	经度
//    latitude	Date	纬度
//    address	String	详细地址
//    begin_time	String	 营销开始时间【08:00】
//    end_time	String	营销结束时间【20:00】
//    isOrder	Intger	是否支持预约(1是 2否)
//    provinceid	String	省份编号
//    citiesid	String	城市编号
//    areaid	String	地区编号
//    huanxin_num	String	环信编号【会议室的环信群组编号】
    private String storeNum;
    private String sotreName;
    private String figurePic;
    private String backgroundImg;
    private String introduce;
    private String linkTel;
    private String longitude;
    private String latitude;
    private String address;
    private String beginTime;
    private String endTime;
    private int isOrder;
    private String provinceid;
    private String citiesid;
    private String areaid;
    private String huanxinNum;//
    //    huanxin_num	String
    private String userNumSecretary;

    public String getUserNumSecretary() {
        return userNumSecretary;
    }

    public void setUserNumSecretary(String userNumSecretary) {
        this.userNumSecretary = userNumSecretary;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getSotreName() {
        return sotreName;
    }

    public void setSotreName(String sotreName) {
        this.sotreName = sotreName;
    }

    public String getFigurePic() {
        return figurePic;
    }

    public void setFigurePic(String figurePic) {
        this.figurePic = figurePic;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCitiesid() {
        return citiesid;
    }

    public void setCitiesid(String citiesid) {
        this.citiesid = citiesid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getHuanxinNum() {
        return huanxinNum;
    }

    public void setHuanxinNum(String huanxinNum) {
        this.huanxinNum = huanxinNum;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
                "storeNum='" + storeNum + '\'' +
                ", sotreName='" + sotreName + '\'' +
                ", figurePic='" + figurePic + '\'' +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", introduce='" + introduce + '\'' +
                ", linkTel='" + linkTel + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isOrder=" + isOrder +
                ", provinceid='" + provinceid + '\'' +
                ", citiesid='" + citiesid + '\'' +
                ", areaid='" + areaid + '\'' +
                ", huanxinNum='" + huanxinNum + '\'' +
                ", userNumSecretary='" + userNumSecretary + '\'' +
                '}';
    }
}
