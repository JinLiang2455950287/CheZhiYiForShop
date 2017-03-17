package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description ：工单数据结构
 * <p/>
 * Created by hdl on 2016/9/20.
 */
public class WorkOrderInfo implements Parcelable {
    private int workOrderId;            //主键
    private String workOrderNum;        //工单编号
    private int workOrderStatus;        // 工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)
    private String storeNum;            //门店编号【暂无使用】
    private String projectNum;          //服务一级类编号
    private String projectName;         //服务一级类名称
    private String servicePlateNumber;      //车牌号
    /**车辆图片*/
    private String carPicPath;
    private String remark;     //  备注
    private String serviceUserName;          //司机用户num
    private String serviceUserNum;          //司机用户num
    private String createUserName;           //工单创建人
    private String createUserNum;           //工单创建人
    private String createTime;              //工单创建时间 "2016-09-20 09:39:59",
    private String makeNum;                 //预约编号
    private double downPayment;                // 已付定金
    private String downPaymentOrderNum;         //定金支付订单
    private double totalAmount;                    // 工单总额
    private String totalAmountOrderNum;         //工单结算订单
    private String workbayName;               // 工位号
    private String leadingUserName;              //施工负责人
    private String leadingUserNum;              //施工负责人
    private String childProjectNum;             //服务二级类 json字符串  "[{\"childProjectTypeList\":[],\"projectNum\":\"001002000000000\",\"projectName\":\"诊断\",\"parentNum\":\"001000000000000\",\"projectAllName\":\"\",\"projectId\":11,\"isSelected\":true,\"isParent\":false,\"sortNum\":1}]",
    /**  工单流水 详情时使用 工单流水   */
    private List<WorkOrderRecordInfo> workOrderRecordList;
//    /**代下单的商品信息代下单的商品数据结构*/
//    private List<OrderGoodsInfo> workOrderDxdList;
    /**  服务的商品信息商品数据结构，在工单详情页逗号拼接商品名称显示   */
    private List<OrderGoodsInfo> workOrderGoodsList;
    /**  已购项目  */
    private List<OrderGoodsInfo> orderGoodsDetailList;
    /**  协助技师 在技师端工单详情页如果此list数量大于0代表需要分配施工提成   */
    private List<AssistUserInfo> workOrderAssistList;

    /** 父类编号 与父类的projectNum相同 000000 为一级 **/
    private String parentNum;
    private User user;
    private double payAmount;

    private boolean isSelected=false;
    private boolean isParent=false;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
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

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getServicePlateNumber() {
        return servicePlateNumber;
    }

    public void setServicePlateNumber(String servicePlateNumber) {
        this.servicePlateNumber = servicePlateNumber;
    }

    public List<OrderGoodsInfo> getWorkOrderGoodsList() {
        return workOrderGoodsList;
    }

    public void setWorkOrderGoodsList(List<OrderGoodsInfo> workOrderGoodsList) {
        this.workOrderGoodsList = workOrderGoodsList;
    }

    public List<AssistUserInfo> getWorkOrderAssistList() {
        return workOrderAssistList;
    }

    public void setWorkOrderAssistList(List<AssistUserInfo> workOrderAssistList) {
        this.workOrderAssistList = workOrderAssistList;
    }

    public String getCarPicPath() {
        return carPicPath;
    }

    public void setCarPicPath(String carPicPath) {
        this.carPicPath = carPicPath;
    }

    public String getServiceUserName() {
        return serviceUserName;
    }

    public void setServiceUserName(String serviceUserName) {
        this.serviceUserName = serviceUserName;
    }

    public String getServiceUserNum() {
        return serviceUserNum;
    }

    public void setServiceUserNum(String serviceUserNum) {
        this.serviceUserNum = serviceUserNum;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserNum() {
        return createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMakeNum() {
        return makeNum;
    }

    public void setMakeNum(String makeNum) {
        this.makeNum = makeNum;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public String getDownPaymentOrderNum() {
        return downPaymentOrderNum;
    }

    public void setDownPaymentOrderNum(String downPaymentOrderNum) {
        this.downPaymentOrderNum = downPaymentOrderNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountOrderNum() {
        return totalAmountOrderNum;
    }

    public void setTotalAmountOrderNum(String totalAmountOrderNum) {
        this.totalAmountOrderNum = totalAmountOrderNum;
    }

    public String getWorkbayName() {
        return workbayName;
    }

    public void setWorkbayName(String workbayName) {
        this.workbayName = workbayName;
    }

    public String getLeadingUserName() {
        return leadingUserName;
    }

    public void setLeadingUserName(String leadingUserName) {
        this.leadingUserName = leadingUserName;
    }

    public String getLeadingUserNum() {
        return leadingUserNum;
    }

    public void setLeadingUserNum(String leadingUserNum) {
        this.leadingUserNum = leadingUserNum;
    }

    public String getChildProjectNum() {
        return childProjectNum;
    }

    public void setChildProjectNum(String childProjectNum) {
        this.childProjectNum = childProjectNum;
    }

    public List<WorkOrderRecordInfo> getWorkOrderRecordList() {
        return workOrderRecordList;
    }

    public void setWorkOrderRecordList(List<WorkOrderRecordInfo> workOrderRecordList) {
        this.workOrderRecordList = workOrderRecordList;
    }

//    public List<OrderGoodsInfo> getWorkOrderDxdList() {
//        return workOrderDxdList;
//    }
//
//    public void setWorkOrderDxdList(List<OrderGoodsInfo> workOrderDxdList) {
//        this.workOrderDxdList = workOrderDxdList;
//    }

    public List<OrderGoodsInfo> getOrderGoodsDetailList() {
        return orderGoodsDetailList;
    }

    public void setOrderGoodsDetailList(List<OrderGoodsInfo> orderGoodsDetailList) {
        this.orderGoodsDetailList = orderGoodsDetailList;
    }

    public String getParentNum() {
        return parentNum;
    }

    public void setParentNum(String parentNum) {
        this.parentNum = parentNum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public WorkOrderInfo() {
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.workOrderId);
        dest.writeString(this.workOrderNum);
        dest.writeInt(this.workOrderStatus);
        dest.writeString(this.storeNum);
        dest.writeString(this.projectNum);
        dest.writeString(this.projectName);
        dest.writeString(this.servicePlateNumber);
        dest.writeString(this.carPicPath);
        dest.writeString(this.remark);
        dest.writeString(this.serviceUserName);
        dest.writeString(this.serviceUserNum);
        dest.writeString(this.createUserName);
        dest.writeString(this.createUserNum);
        dest.writeString(this.createTime);
        dest.writeString(this.makeNum);
        dest.writeDouble(this.downPayment);
        dest.writeString(this.downPaymentOrderNum);
        dest.writeDouble(this.totalAmount);
        dest.writeString(this.totalAmountOrderNum);
        dest.writeString(this.workbayName);
        dest.writeString(this.leadingUserName);
        dest.writeString(this.leadingUserNum);
        dest.writeString(this.childProjectNum);
        dest.writeTypedList(this.workOrderRecordList);
        dest.writeTypedList(this.workOrderGoodsList);
        dest.writeTypedList(this.orderGoodsDetailList);
        dest.writeTypedList(this.workOrderAssistList);
        dest.writeString(this.parentNum);
        dest.writeParcelable(this.user, flags);
        dest.writeDouble(this.payAmount);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isParent ? (byte) 1 : (byte) 0);
    }

    protected WorkOrderInfo(Parcel in) {
        this.workOrderId = in.readInt();
        this.workOrderNum = in.readString();
        this.workOrderStatus = in.readInt();
        this.storeNum = in.readString();
        this.projectNum = in.readString();
        this.projectName = in.readString();
        this.servicePlateNumber = in.readString();
        this.carPicPath = in.readString();
        this.remark = in.readString();
        this.serviceUserName = in.readString();
        this.serviceUserNum = in.readString();
        this.createUserName = in.readString();
        this.createUserNum = in.readString();
        this.createTime = in.readString();
        this.makeNum = in.readString();
        this.downPayment = in.readDouble();
        this.downPaymentOrderNum = in.readString();
        this.totalAmount = in.readDouble();
        this.totalAmountOrderNum = in.readString();
        this.workbayName = in.readString();
        this.leadingUserName = in.readString();
        this.leadingUserNum = in.readString();
        this.childProjectNum = in.readString();
        this.workOrderRecordList = in.createTypedArrayList(WorkOrderRecordInfo.CREATOR);
        this.workOrderGoodsList = in.createTypedArrayList(OrderGoodsInfo.CREATOR);
        this.orderGoodsDetailList = in.createTypedArrayList(OrderGoodsInfo.CREATOR);
        this.workOrderAssistList = in.createTypedArrayList(AssistUserInfo.CREATOR);
        this.parentNum = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.payAmount = in.readDouble();
        this.isSelected = in.readByte() != 0;
        this.isParent = in.readByte() != 0;
    }

    public static final Creator<WorkOrderInfo> CREATOR = new Creator<WorkOrderInfo>() {
        @Override
        public WorkOrderInfo createFromParcel(Parcel source) {return new WorkOrderInfo(source);}

        @Override
        public WorkOrderInfo[] newArray(int size) {return new WorkOrderInfo[size];}
    };
}
