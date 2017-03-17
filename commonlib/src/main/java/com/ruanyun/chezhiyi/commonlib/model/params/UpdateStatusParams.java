package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：修改工单状态的参数
 * <p/>
 * Created by ycw on 2016/10/31.
 */
public class UpdateStatusParams {
    private String workOrderNum;//	String	工单编号
    private Integer workOrderStatus;//	Int	工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)
    private String remark;//	String	自检不通过时填写的理由

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public Integer getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(Integer workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
