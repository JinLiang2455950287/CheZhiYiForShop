package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description ：结算实体
 * <p/>
 * Created by ycw on 2016/11/1.
 */
public class JieSuanInfo implements Parcelable {
    private double downPayment;     //结算工单已支付定金
    private double dxdAmount;       //代下单项目总金额
    private List<OrderGoodsInfo> dxdGoodsList;      //代下单商品列表
    private double totalAmount;     //结算工单总金额
    private List<WorkOrderInfo> workOrderList;      //服务工单列表
    private double ygAmount;        //已购项目总金额
    private List<OrderGoodsInfo> ygGoodsList;       //已购项目列表
    private List<OrderGoodsInfo> yhqGoodsList;      //可用优惠券列表

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public double getDxdAmount() {
        return dxdAmount;
    }

    public void setDxdAmount(double dxdAmount) {
        this.dxdAmount = dxdAmount;
    }

    public List<OrderGoodsInfo> getDxdGoodsList() {
        return dxdGoodsList;
    }

    public void setDxdGoodsList(List<OrderGoodsInfo> dxdGoodsList) {
        this.dxdGoodsList = dxdGoodsList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<WorkOrderInfo> getWorkOrderList() {
        return workOrderList;
    }

    public void setWorkOrderList(List<WorkOrderInfo> workOrderList) {
        this.workOrderList = workOrderList;
    }

    public double getYgAmount() {
        return ygAmount;
    }

    public void setYgAmount(double ygAmount) {
        this.ygAmount = ygAmount;
    }

    public List<OrderGoodsInfo> getYgGoodsList() {
        return ygGoodsList;
    }

    public void setYgGoodsList(List<OrderGoodsInfo> ygGoodsList) {
        this.ygGoodsList = ygGoodsList;
    }

    public List<OrderGoodsInfo> getYhqGoodsList() {
        return yhqGoodsList;
    }

    public void setYhqGoodsList(List<OrderGoodsInfo> yhqGoodsList) {
        this.yhqGoodsList = yhqGoodsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.downPayment);
        dest.writeDouble(this.dxdAmount);
        dest.writeTypedList(this.dxdGoodsList);
        dest.writeDouble(this.totalAmount);
        dest.writeTypedList(this.workOrderList);
        dest.writeDouble(this.ygAmount);
        dest.writeTypedList(this.ygGoodsList);
        dest.writeTypedList(this.yhqGoodsList);
    }

    public JieSuanInfo() {
    }

    protected JieSuanInfo(Parcel in) {
        this.downPayment = in.readDouble();
        this.dxdAmount = in.readDouble();
        this.dxdGoodsList = in.createTypedArrayList(OrderGoodsInfo.CREATOR);
        this.totalAmount = in.readDouble();
        this.workOrderList = in.createTypedArrayList(WorkOrderInfo.CREATOR);
        this.ygAmount = in.readDouble();
        this.ygGoodsList = in.createTypedArrayList(OrderGoodsInfo.CREATOR);
        this.yhqGoodsList = in.createTypedArrayList(OrderGoodsInfo.CREATOR);
    }

    public static final Parcelable.Creator<JieSuanInfo> CREATOR = new Parcelable.Creator<JieSuanInfo>() {
        @Override
        public JieSuanInfo createFromParcel(Parcel source) {
            return new JieSuanInfo(source);
        }

        @Override
        public JieSuanInfo[] newArray(int size) {
            return new JieSuanInfo[size];
        }
    };
}
