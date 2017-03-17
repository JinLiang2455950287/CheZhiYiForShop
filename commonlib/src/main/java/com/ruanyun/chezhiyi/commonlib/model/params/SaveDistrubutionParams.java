package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description:
 * author: zhangsan on 16/10/27 下午9:09.
 */
public class SaveDistrubutionParams {
    /**工单编号  **/
    private String  workOrderNum;
    /** 主技师提成编号 **/
    private String commissionInfoNum;
    /**  **/
    private String jsonArrayString;

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getCommissionInfoNum() {
        return commissionInfoNum;
    }

    public void setCommissionInfoNum(String commissionInfoNum) {
        this.commissionInfoNum = commissionInfoNum;
    }

    public String getJsonArrayString() {
        return jsonArrayString;
    }

    public void setJsonArrayString(String jsonArrayString) {
        this.jsonArrayString = jsonArrayString;
    }
}
