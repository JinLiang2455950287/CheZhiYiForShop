package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Created by 创智 on 2017/3/10.
 * 预约item bean
 */

public class YuYueItemBean {


    /**
     * createTime : 2017-03-09 09:41:22
     * downPayment : 0
     * downPaymentOrderNum :
     * linkTel :
     * loginName :
     * makeId : 821
     * makeNum : MI28400000000821
     * makeStatus : 1
     * makeStatusString :
     * nickName :
     * orderInfo : null
     * predictShopTime : 2017-03-10 09:41
     * projectNum : [
     {
     "isMake" : 0,
     "sortNum" : 1,
     "childProjectTypeList" : [
     {
     "isMake" : 0,
     "sortNum" : 2,
     "childProjectTypeList" : [

     ],
     "projectNum" : "002003000000000",
     "parentNum" : "002000000000000",
     "projectName" : "常规保养",
     "projectAllName" : "服务项目\/常规\/常规保养"
     }
     ],
     "projectNum" : "002000000000000",
     "parentNum" : "000000",
     "projectName" : "常规",
     "projectAllName" : "服务项目\/常规"
     },
     {
     "isMake" : 0,
     "sortNum" : 2,
     "childProjectTypeList" : [
     {
     "isMake" : 0,
     "sortNum" : 2,
     "childProjectTypeList" : [

     ],
     "projectNum" : "003004000000000",
     "parentNum" : "003000000000000",
     "projectName" : "更换刹车片",
     "projectAllName" : "服务项目\/保养\/更换刹车片"
     }
     ],
     "projectNum" : "003000000000000",
     "parentNum" : "000000",
     "projectName" : "保养",
     "projectAllName" : "服务项目\/保养"
     }
     ]
     * remark : Gggggg
     * servicePlateNumber :
     * startTime : null
     * storeNum : st30390000000011
     * user : null
     * userNum : sys50320000010837
     * workOrderInfoList : []
     */

    private String createTime;
    private int downPayment;
    private String downPaymentOrderNum;
    private String linkTel;
    private String loginName;
    private int makeId;
    private String makeNum;
    private int makeStatus;
    private String makeStatusString;
    private String nickName;
    private Object orderInfo;
    private String predictShopTime;
    private String projectNum;
    private String remark;
    private String servicePlateNumber;
    private Object startTime;
    private String storeNum;
    private Object user;
    private String userNum;
    private List<?> workOrderInfoList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }

    public String getDownPaymentOrderNum() {
        return downPaymentOrderNum;
    }

    public void setDownPaymentOrderNum(String downPaymentOrderNum) {
        this.downPaymentOrderNum = downPaymentOrderNum;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public int getMakeStatus() {
        return makeStatus;
    }

    public void setMakeStatus(int makeStatus) {
        this.makeStatus = makeStatus;
    }

    public String getMakeStatusString() {
        return makeStatusString;
    }

    public void setMakeStatusString(String makeStatusString) {
        this.makeStatusString = makeStatusString;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Object orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getPredictShopTime() {
        return predictShopTime;
    }

    public void setPredictShopTime(String predictShopTime) {
        this.predictShopTime = predictShopTime;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServicePlateNumber() {
        return servicePlateNumber;
    }

    public void setServicePlateNumber(String servicePlateNumber) {
        this.servicePlateNumber = servicePlateNumber;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public List<?> getWorkOrderInfoList() {
        return workOrderInfoList;
    }

    public void setWorkOrderInfoList(List<?> workOrderInfoList) {
        this.workOrderInfoList = workOrderInfoList;
    }

    @Override
    public String toString() {
        return "YuYueItemBean{" +
                "createTime='" + createTime + '\'' +
                ", downPayment=" + downPayment +
                ", downPaymentOrderNum='" + downPaymentOrderNum + '\'' +
                ", linkTel='" + linkTel + '\'' +
                ", loginName='" + loginName + '\'' +
                ", makeId=" + makeId +
                ", makeNum='" + makeNum + '\'' +
                ", makeStatus=" + makeStatus +
                ", makeStatusString='" + makeStatusString + '\'' +
                ", nickName='" + nickName + '\'' +
                ", orderInfo=" + orderInfo +
                ", predictShopTime='" + predictShopTime + '\'' +
                ", projectNum='" + projectNum + '\'' +
                ", remark='" + remark + '\'' +
                ", servicePlateNumber='" + servicePlateNumber + '\'' +
                ", startTime=" + startTime +
                ", storeNum='" + storeNum + '\'' +
                ", user=" + user +
                ", userNum='" + userNum + '\'' +
                ", workOrderInfoList=" + workOrderInfoList +
                '}';
    }
}
