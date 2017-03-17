package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：等候区接单(技师端)
 * <p/>
 * Created by hdl on 2016/10/9.
 */

public class OrderReceivingParams {

    private String workOrderNum;//工单编号
    private String workbayInfoNum;//工位编号
    private String workbayInfoName;//工位名称
    private String userName;//施工人
//    private String leadingUserName;//施工人

//    public String getLeadingUserName() {
//        return leadingUserName;
//    }
//
//    public void setLeadingUserName(String leadingUserName) {
//        this.leadingUserName = leadingUserName;
//    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkbayInfoName() {
        return workbayInfoName;
    }

    public void setWorkbayInfoName(String workbayInfoName) {
        this.workbayInfoName = workbayInfoName;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getWorkbayInfoNum() {
        return workbayInfoNum;
    }

    public void setWorkbayInfoNum(String workbayInfoNum) {
        this.workbayInfoNum = workbayInfoNum;
    }

}
