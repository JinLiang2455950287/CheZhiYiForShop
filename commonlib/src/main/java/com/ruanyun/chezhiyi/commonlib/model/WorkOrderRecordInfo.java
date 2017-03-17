package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description ：工单流水
 * <p/>
 * Created by hdl on 2016/9/26.
 */

public class WorkOrderRecordInfo implements Parcelable {
    /**
     * 工单取消
     */
    public static final int WORK_ORDER_STATUS_CANCEL = -1;
    /**
     * 工单等待确认
     */
    public static final int WORK_ORDER_STATUS_WAIT = 1;
    /**
     * 已确认，等待进店
     */
    public static final int WORK_ORDER_STATUS_CONFIRM = 2;
    /**
     * 等待施工 等待接单
     */
    public static final int WORK_ORDER_STATUS_WAIT_FOR_CONSTRUCTION = 3;
    /**
     * 即将开始施工
     */
    public static final int WORK_ORDER_STATUS_PREPARE = 4;
    /**
     * 施工中
     */
    public static final int WORK_ORDER_STATUS_UNDER_CONSTRUCTION = 5;
    /**
     * 施工结束，质检中
     */
    public static final int WORK_ORDER_STATUS_QUALITY = 6;
    /**
     * 返修中，质检不合
     */
    public static final int WORK_ORDER_STATUS_REPAIR = 7;
    /**
     * 等待结算，质检合格
     */
    public static final int WORK_ORDER_STATUS_CONFORMITY = 8;
    /**
     * 完成结算
     */
    public static final int WORK_ORDER_STATUS_FINISH = 9;

    private int workOrderRecordId;//主键
    private String workOrderRecordNun;//主键业务编号
    private String workOrderNum;//工单编号
    //业务状态工单状态工单状态(-1取消 工单取消 1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合 8等待结算，质检合格 9完成结算)
    private int workOrderStatus;
    private String recordTitle;//标题
    private String recordContent;//内容
    private String userNum;//添加的人
    private String createTime;//时间

    public int getWorkOrderRecordId() {
        return workOrderRecordId;
    }

    public void setWorkOrderRecordId(int workOrderRecordId) {
        this.workOrderRecordId = workOrderRecordId;
    }

    public String getWorkOrderRecordNun() {
        return workOrderRecordNun;
    }

    public void setWorkOrderRecordNun(String workOrderRecordNun) {
        this.workOrderRecordNun = workOrderRecordNun;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public int getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(int workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
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


    public WorkOrderRecordInfo(String workOrderRecordNun, String workOrderNum, int
            workOrderStatus, String recordTitle, String recordContent, String userNum, String
            createTime) {
        this.workOrderRecordNun = workOrderRecordNun;
        this.workOrderNum = workOrderNum;
        this.workOrderStatus = workOrderStatus;
        this.recordTitle = recordTitle;
        this.recordContent = recordContent;
        this.userNum = userNum;
        this.createTime = createTime;
    }

    public WorkOrderRecordInfo() {
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.workOrderRecordId);
        dest.writeString(this.workOrderRecordNun);
        dest.writeString(this.workOrderNum);
        dest.writeInt(this.workOrderStatus);
        dest.writeString(this.recordTitle);
        dest.writeString(this.recordContent);
        dest.writeString(this.userNum);
        dest.writeString(this.createTime);
    }

    protected WorkOrderRecordInfo(Parcel in) {
        this.workOrderRecordId = in.readInt();
        this.workOrderRecordNun = in.readString();
        this.workOrderNum = in.readString();
        this.workOrderStatus = in.readInt();
        this.recordTitle = in.readString();
        this.recordContent = in.readString();
        this.userNum = in.readString();
        this.createTime = in.readString();
    }

    public static final Creator<WorkOrderRecordInfo> CREATOR = new Creator<WorkOrderRecordInfo>() {
        @Override
        public WorkOrderRecordInfo createFromParcel(Parcel source) {return new WorkOrderRecordInfo(source);}

        @Override
        public WorkOrderRecordInfo[] newArray(int size) {return new WorkOrderRecordInfo[size];}
    };
}
