package com.ruanyun.chezhiyi.commonlib.model;

import com.ruanyun.imagepicker.bean.CompressImageInfoGetter;

/**
 * TCarInfo车辆信息数据结构
 * Created by ycw on 2016/8/29.
 */
public class CarInfo implements CompressImageInfoGetter{
    public static final int EDIT_STATUS_ADD = 999;
    public static final int EDIT_STATUS_MODIFY = 997;
    public static final int EDIT_STATUS_NOMODIFY = 998;

    private String carNum;              //车辆编号 业务主见
    private String carModelNum = "";            //车系名编号 对应TcarModel车型数据结构
    private String plateNumber;         //车牌号
    private int color;               //颜色对应 dictionary数据结构  parentCode 等于CAR_COLOR
    private String frameNumber;         //车架号
    private String engineNumber;        //发动机号
    private String registerDate = "";        //行驶证注册日期 格式为yyyy-MM-dd   上牌时间
    private String picPath;             //车辆照片file/carinfo/
    private String obverseSidePhoto;    //行驶证 正面照片file/carinfo/
    private String otherSidePhoto;      //行驶证 反面照片file/carinfo/
    private String userNum;//车主编号
    private String carShortName;//车牌简称 皖
    private String carAllName;//车牌全称 皖A88888
    private String storeNum;//
    private int carMileage;//车辆行驶公里
    private boolean isAdd = false;//控制是否是添加view
    private int editStatus = EDIT_STATUS_NOMODIFY;
    private String carBandName;
    private String carName; // 车型全称
    private String insuranceStart = "";//  保险开始时间
    private int maintenanceMileage = 5000;//保养里程

    public CarInfo() {
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getMaintenanceMileage() {
        return maintenanceMileage;
    }

    public void setMaintenanceMileage(int maintenanceMileage) {
        this.maintenanceMileage = maintenanceMileage;
    }

    public String getInsuranceStart() {
        return insuranceStart;
    }

    public void setInsuranceStart(String insuranceStart) {
        this.insuranceStart = insuranceStart;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getCarShortName() {
        return carShortName;
    }

    public void setCarShortName(String carShortName) {
        this.carShortName = carShortName;
    }

    public String getCarAllName() {
        return carAllName;
    }

    public void setCarAllName(String carAllName) {
        this.carAllName = carAllName;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public int getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(int carMileage) {
        this.carMileage = carMileage;
    }

    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarModelNum() {
        return carModelNum;
    }

    public void setCarModelNum(String carModelNum) {
        this.carModelNum = carModelNum;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getObverseSidePhoto() {
        return obverseSidePhoto;
    }

    public void setObverseSidePhoto(String obverseSidePhoto) {
        this.obverseSidePhoto = obverseSidePhoto;
    }

    public String getOtherSidePhoto() {
        return otherSidePhoto;
    }

    public void setOtherSidePhoto(String otherSidePhoto) {
        this.otherSidePhoto = otherSidePhoto;
    }

    @Override
    public void setParamsName(String paramsName) {

    }

    @Override
    public String imageFilePath() {
        return picPath;
    }

    @Override
    public String imageFileName() {
        return carAllName;
    }

    @Override
    public String requestPramsName() {
        return carAllName;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "carNum='" + carNum + '\'' +
                ", carModelNum='" + carModelNum + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", color=" + color +
                ", frameNumber='" + frameNumber + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", picPath='" + picPath + '\'' +
                ", obverseSidePhoto='" + obverseSidePhoto + '\'' +
                ", otherSidePhoto='" + otherSidePhoto + '\'' +
                ", userNum='" + userNum + '\'' +
                ", carShortName='" + carShortName + '\'' +
                ", carAllName='" + carAllName + '\'' +
                ", storeNum='" + storeNum + '\'' +
                ", carMileage=" + carMileage +
                ", isAdd=" + isAdd +
                ", editStatus=" + editStatus +
                ", carBandName='" + carBandName + '\'' +
                ", carName='" + carName + '\'' +
                ", insuranceStart='" + insuranceStart + '\'' +
                ", maintenanceMileage=" + maintenanceMileage +
                '}';
    }
}
