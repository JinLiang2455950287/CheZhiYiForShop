package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description ：预约
 * <p>
 * Created by ycw on 2016/9/13.
 */
public class AppointmentInfo implements Parcelable {
    private int makeId;//          	Integer 	主键
    private String makeNum;//          	String  	编号
    private String storeNum;//        	String  	门店编号【暂无】
    private String userNum;//         	String  	用户编号
    private int makeStatus;//      	Integer 	预约状态（-1已取消 1待确认 2已确认 3待服务 4已完成） 字典表TMAKEINFO_STATUS
    private String projectNum;//      	String  	服务内容 json格式 [{projectNum:"",childProjectTypeList:[{projectNum:"",childProjectTypeList:[]}]}]
    private String predictShopTime;// 	String  	预计到店时间【yyyy-MM-dd HH:mm】
    private String remark;//          	String  	备注
    private String createTime;//      	Date    	预约生成时间
    private String downPaymentOrderNum;//预约支付定金的预约编号
    private BigDecimal downPayment;//支付定金
    private List<WorkOrderInfo> workOrderInfoList;//工单 服务项目集合
    private OrderInfo orderInfo;//订单数据结构
    private User user;
    public static final int CANCEL = -1;//-1已取消
    public static final int SWAIT_AFFIRM = 1;//1待确认
    public static final int ALREADY_AFFIRM = 2;//2待付款
    public static final int SWAIT_SERVICE = 3;//3待服务
    public static final int ACCOMPLISH = 4;//4已完成

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public String getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(String makeNum) {
        this.makeNum = makeNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(int makeStatus) {
        this.makeStatus = makeStatus;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getPredictShopTime() {
        return predictShopTime;
    }

    public void setPredictShopTime(String predictShopTime) {
        this.predictShopTime = predictShopTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDownPaymentOrderNum() {
        return downPaymentOrderNum;
    }

    public void setDownPaymentOrderNum(String downPaymentOrderNum) {
        this.downPaymentOrderNum = downPaymentOrderNum;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public List<WorkOrderInfo> getWorkOrderInfoList() {
        return workOrderInfoList;
    }

    public void setWorkOrderInfoList(List<WorkOrderInfo> workOrderInfoList) {
        this.workOrderInfoList = workOrderInfoList;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public AppointmentInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.makeId);
        dest.writeString(this.makeNum);
        dest.writeString(this.storeNum);
        dest.writeString(this.userNum);
        dest.writeInt(this.makeStatus);
        dest.writeString(this.projectNum);
        dest.writeString(this.predictShopTime);
        dest.writeString(this.remark);
        dest.writeString(this.createTime);
        dest.writeString(this.downPaymentOrderNum);
        dest.writeSerializable(this.downPayment);
        dest.writeTypedList(this.workOrderInfoList);
        dest.writeParcelable(this.orderInfo, flags);
        dest.writeParcelable(this.user, flags);
    }

    protected AppointmentInfo(Parcel in) {
        this.makeId = in.readInt();
        this.makeNum = in.readString();
        this.storeNum = in.readString();
        this.userNum = in.readString();
        this.makeStatus = in.readInt();
        this.projectNum = in.readString();
        this.predictShopTime = in.readString();
        this.remark = in.readString();
        this.createTime = in.readString();
        this.downPaymentOrderNum = in.readString();
        this.downPayment = (BigDecimal) in.readSerializable();
        this.workOrderInfoList = in.createTypedArrayList(WorkOrderInfo.CREATOR);
        this.orderInfo = in.readParcelable(OrderInfo.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<AppointmentInfo> CREATOR = new Creator<AppointmentInfo>() {
        @Override
        public AppointmentInfo createFromParcel(Parcel source) {
            return new AppointmentInfo(source);
        }

        @Override
        public AppointmentInfo[] newArray(int size) {
            return new AppointmentInfo[size];
        }
    };

    @Override
    public String toString() {
        return "AppointmentInfo{" +
                "makeId=" + makeId +
                ", makeNum='" + makeNum + '\'' +
                ", storeNum='" + storeNum + '\'' +
                ", userNum='" + userNum + '\'' +
                ", makeStatus=" + makeStatus +
                ", projectNum='" + projectNum + '\'' +
                ", predictShopTime='" + predictShopTime + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", downPaymentOrderNum='" + downPaymentOrderNum + '\'' +
                ", downPayment=" + downPayment +
                ", workOrderInfoList=" + workOrderInfoList +
                ", orderInfo=" + orderInfo +
                ", user=" + user +
                '}';
    }
}
