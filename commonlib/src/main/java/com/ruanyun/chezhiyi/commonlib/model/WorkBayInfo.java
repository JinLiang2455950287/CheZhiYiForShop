package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：工位数据结构
 * <p/>
 * Created by hdl on 2016/9/26.
 */

public class WorkBayInfo {
    private int workbayInfoId;//主键
    private String workbayInfoNum;//工位编号
    private String workbayName;//工位名称
    private int workbayStatus;//工位状态 1为空闲 2为施工中
    private String projectNum;//服务一级编号
    private String projectName;//服务一级名称
    private String storeNum;//String
    private String userNum;//String
    private String createTime;//Date
    private String servicePlateNumber;//当前工位服务的车牌号
    private String leadingUserNum;//当前工位使用的技师
    private User user;//技师的用户对象用户数据结构
    private String workOrderNum;//工单编号

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    /**空闲中*/
    public static final int LEISURE = 1;

    public int getWorkbayInfoId() {
        return workbayInfoId;
    }

    public void setWorkbayInfoId(int workbayInfoId) {
        this.workbayInfoId = workbayInfoId;
    }

    public String getWorkbayInfoNum() {
        return workbayInfoNum;
    }

    public void setWorkbayInfoNum(String workbayInfoNum) {
        this.workbayInfoNum = workbayInfoNum;
    }

    public String getWorkbayName() {
        return workbayName;
    }

    public void setWorkbayName(String workbayName) {
        this.workbayName = workbayName;
    }

    public int getWorkbayStatus() {
        return workbayStatus;
    }

    public void setWorkbayStatus(int workbayStatus) {
        this.workbayStatus = workbayStatus;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getServicePlateNumber() {
        return servicePlateNumber;
    }

    public void setServicePlateNumber(String servicePlateNumber) {
        this.servicePlateNumber = servicePlateNumber;
    }

    public String getLeadingUserNum() {
        return leadingUserNum;
    }

    public void setLeadingUserNum(String leadingUserNum) {
        this.leadingUserNum = leadingUserNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
