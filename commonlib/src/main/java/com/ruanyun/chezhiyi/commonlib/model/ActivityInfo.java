package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by msq on 2016/9/9.
 */
public class ActivityInfo implements Parcelable {
    public static final int IS_COST_YES = 1;
    public static final int IS_COST_NO = 2;

    private int activityId;	//	主键
    private String activityNum;	//	编号
    private String activityName;	//	活动名称
    private String activityBeginTime;	//	活动开始时间【yyyy-MM-dd】
    private String activityEndTime;	//	活动开始结束时间【yyyy-MM-dd】
    private String startTime;	//	报名开始时间【yyyy-MM-dd】
    private String endTime;	//	报名结束时间【【yyyy-MM-dd】
    private double cost;	//	单个人的费用
    private int isLimitNum;	//	是否有人数限制  1是 2否【等于1时limitNum才有值】
    private int limitNum;	//	上限人数
    private String activityPath;	//	活动图片
    private String userNum;	//	用户编号
    private String createTime;	//	创建时间
    private int isCost;	//	是否收费1-是2-否【等于1时cost才有值，需要付款】
    private int sfyytfyCost;	//	是否允许退费用1-是2-否
    private int refundDay;	//	延期天数
    private int registerNumber;	//	已报名人数
    private int activityStatus;	//	活动状态

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(String activityNum) {
        this.activityNum = activityNum;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityBeginTime() {
        return activityBeginTime;
    }

    public void setActivityBeginTime(String activityBeginTime) {
        this.activityBeginTime = activityBeginTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getIsLimitNum() {
        return isLimitNum;
    }

    public void setIsLimitNum(int isLimitNum) {
        this.isLimitNum = isLimitNum;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public String getActivityPath() {
        return activityPath;
    }

    public void setActivityPath(String activityPath) {
        this.activityPath = activityPath;
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

    public int getIsCost() {
        return isCost;
    }

    public void setIsCost(int isCost) {
        this.isCost = isCost;
    }

    public int getSfyytfyCost() {
        return sfyytfyCost;
    }

    public void setSfyytfyCost(int sfyytfyCost) {
        this.sfyytfyCost = sfyytfyCost;
    }

    public int getRefundDay() {
        return refundDay;
    }

    public void setRefundDay(int refundDay) {
        this.refundDay = refundDay;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(int registerNumber) {
        this.registerNumber = registerNumber;
    }

    @Override
    public String toString() {
        return "ActivityListInfo{" +
                "activityId=" + activityId +
                ", activityNum='" + activityNum + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityBeginTime='" + activityBeginTime + '\'' +
                ", activityEndTime='" + activityEndTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", cost=" + cost +
                ", isLimitNum=" + isLimitNum +
                ", limitNum='" + limitNum + '\'' +
                ", activityPath='" + activityPath + '\'' +
                ", userNum='" + userNum + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isCost=" + isCost +
                ", sfyytfyCost=" + sfyytfyCost +
                ", refundDay=" + refundDay +
                ", registerNumber=" + registerNumber +
                '}';
    }

    public ActivityInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.activityId);
        dest.writeString(this.activityNum);
        dest.writeString(this.activityName);
        dest.writeString(this.activityBeginTime);
        dest.writeString(this.activityEndTime);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeDouble(this.cost);
        dest.writeInt(this.isLimitNum);
        dest.writeInt(this.limitNum);
        dest.writeString(this.activityPath);
        dest.writeString(this.userNum);
        dest.writeString(this.createTime);
        dest.writeInt(this.isCost);
        dest.writeInt(this.sfyytfyCost);
        dest.writeInt(this.refundDay);
        dest.writeInt(this.registerNumber);
        dest.writeInt(this.activityStatus);
    }

    protected ActivityInfo(Parcel in) {
        this.activityId = in.readInt();
        this.activityNum = in.readString();
        this.activityName = in.readString();
        this.activityBeginTime = in.readString();
        this.activityEndTime = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.cost = in.readDouble();
        this.isLimitNum = in.readInt();
        this.limitNum = in.readInt();
        this.activityPath = in.readString();
        this.userNum = in.readString();
        this.createTime = in.readString();
        this.isCost = in.readInt();
        this.sfyytfyCost = in.readInt();
        this.refundDay = in.readInt();
        this.registerNumber = in.readInt();
        this.activityStatus = in.readInt();
    }

    public static final Creator<ActivityInfo> CREATOR = new Creator<ActivityInfo>() {
        @Override
        public ActivityInfo createFromParcel(Parcel source) {
            return new ActivityInfo(source);
        }

        @Override
        public ActivityInfo[] newArray(int size) {
            return new ActivityInfo[size];
        }
    };
}
