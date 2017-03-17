package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：获取已购并未消费的服务
 * <p/>
 * Created by hdl on 2016/9/26.
 */

public class NotSpendingServiceParams {
    private String projectNum;//服务一级列表
    private String customerUserNum;//车主的编号

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getCustomerUserNum() {
        return customerUserNum;
    }

    public void setCustomerUserNum(String customerUserNum) {
        this.customerUserNum = customerUserNum;
    }
}
