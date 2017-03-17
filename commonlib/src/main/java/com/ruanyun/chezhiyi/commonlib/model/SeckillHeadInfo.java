package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description ：秒杀头部日期
 * <p/>
 * Created by hdl on 2016/9/13.
 */
public class SeckillHeadInfo implements Parcelable {
    private int seckillMainInfoId;//主键
    private String seckillMainInfoNum;//业务主键
    private String storeNum;//店铺编号【暂无】
    private String beginDate;//开始日期
    private String endDate;//截止日期
    private String beginTime;//开始时间 【格式 HH:mm】
    private String endTime;//截止时间【格式 HH:mm】
    private String createTime;//创建时间
    private String currentDate;//服务器当前时间【格式: yyyy-MM-dd HH:mm:ss】
    private String userNum;//用户编号

    public int getSeckillMainInfoId() {
        return seckillMainInfoId;
    }

    public void setSeckillMainInfoId(int seckillMainInfoId) {
        this.seckillMainInfoId = seckillMainInfoId;
    }

    public String getSeckillMainInfoNum() {
        return seckillMainInfoNum;
    }

    public void setSeckillMainInfoNum(String seckillMainInfoNum) {
        this.seckillMainInfoNum = seckillMainInfoNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.seckillMainInfoId);
        dest.writeString(this.seckillMainInfoNum);
        dest.writeString(this.storeNum);
        dest.writeString(this.beginDate);
        dest.writeString(this.endDate);
        dest.writeString(this.beginTime);
        dest.writeString(this.endTime);
        dest.writeString(this.createTime);
        dest.writeString(this.currentDate);
        dest.writeString(this.userNum);
    }

    public SeckillHeadInfo() {
    }

    protected SeckillHeadInfo(Parcel in) {
        this.seckillMainInfoId = in.readInt();
        this.seckillMainInfoNum = in.readString();
        this.storeNum = in.readString();
        this.beginDate = in.readString();
        this.endDate = in.readString();
        this.beginTime = in.readString();
        this.endTime = in.readString();
        this.createTime = in.readString();
        this.currentDate = in.readString();
        this.userNum = in.readString();
    }

    public static final Parcelable.Creator<SeckillHeadInfo> CREATOR = new Parcelable.Creator<SeckillHeadInfo>() {
        @Override
        public SeckillHeadInfo createFromParcel(Parcel source) {
            return new SeckillHeadInfo(source);
        }

        @Override
        public SeckillHeadInfo[] newArray(int size) {
            return new SeckillHeadInfo[size];
        }
    };
}
