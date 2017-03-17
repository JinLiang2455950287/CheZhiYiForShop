package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Description ：协助技师信息
 * <p/>
 * Created by ycw on 2016/10/12.
 */
public class AssistUserInfo implements Parcelable {
    private int assistId;//	 Integer 	主键
    private String userNum;//	 String  	添加人
    private String createTime;//	 String  	添加司机
    private double assistTotalAmount;//	BigDecimal	提成金额
    private String workOrderNum;//	 String  	工单编号
    private String assistUserNum;//	 String  	协助人员编号
    private String assistUserName;//	 String  	协助人员姓名

    public int getAssistId() {
        return assistId;
    }

    public void setAssistId(int assistId) {
        this.assistId = assistId;
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

    public double getAssistTotalAmount() {
        return assistTotalAmount;
    }

    public void setAssistTotalAmount(double assistTotalAmount) {
        this.assistTotalAmount = assistTotalAmount;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getAssistUserNum() {
        return assistUserNum;
    }

    public void setAssistUserNum(String assistUserNum) {
        this.assistUserNum = assistUserNum;
    }

    public String getAssistUserName() {
        return assistUserName;
    }

    public void setAssistUserName(String assistUserName) {
        this.assistUserName = assistUserName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.assistId);
        dest.writeString(this.userNum);
        dest.writeString(this.createTime);
        dest.writeDouble(this.assistTotalAmount);
        dest.writeString(this.workOrderNum);
        dest.writeString(this.assistUserNum);
        dest.writeString(this.assistUserName);
    }

    public AssistUserInfo() {
    }

    protected AssistUserInfo(Parcel in) {
        this.assistId = in.readInt();
        this.userNum = in.readString();
        this.createTime = in.readString();
        this.assistTotalAmount = in.readDouble();
        this.workOrderNum = in.readString();
        this.assistUserNum = in.readString();
        this.assistUserName = in.readString();
    }

    public static final Parcelable.Creator<AssistUserInfo> CREATOR = new Parcelable
            .Creator<AssistUserInfo>() {
        @Override
        public AssistUserInfo createFromParcel(Parcel source) {
            return new AssistUserInfo(source);
        }

        @Override
        public AssistUserInfo[] newArray(int size) {
            return new AssistUserInfo[size];
        }
    };
}
