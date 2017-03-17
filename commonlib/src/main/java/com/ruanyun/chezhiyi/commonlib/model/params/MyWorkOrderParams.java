package com.ruanyun.chezhiyi.commonlib.model.params;

import android.content.Intent;

/**
 * Description ：司机端或技师端获取我的工单Params
 * <p/>
 * Created by hdl on 2016/9/26.
 */

public class MyWorkOrderParams extends PageParamsBase {
    private Integer workOrderStatus;//工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
    private String workOrderStatusString;//进行中【4,5,6,7】等候区工单 3
    private String leadingUserNum;//施工负责人【技师端获取我的服务器工单时把该值写入当前用户】
    private String serviceUserNum;//司机用户num【司机端获取我的工单 把该值写入当前用户】
    /**类型 传值1 为获取 代下单管理*/
    private String type;
    private String isDaiXiaDan;

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getWorkOrderStatusString() {
        return workOrderStatusString;
    }

    public void setWorkOrderStatusString(String workOrderStatusString) {
        this.workOrderStatusString = workOrderStatusString;
    }

    public String getLeadingUserNum() {
        return leadingUserNum;
    }

    public void setLeadingUserNum(String leadingUserNum) {
        this.leadingUserNum = leadingUserNum;
    }

    public String getServiceUserNum() {
        return serviceUserNum;
    }

    public void setServiceUserNum(String serviceUserNum) {
        this.serviceUserNum = serviceUserNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsDaiXiaDan() {
        return isDaiXiaDan;
    }

    public void setIsDaiXiaDan(String isDaiXiaDan) {
        this.isDaiXiaDan = isDaiXiaDan;
    }
}
