package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Created by czy on 2017/3/20.
 */

public class YuYueDealParams {
    private String projectNum;
    private String appointMoney;


    public YuYueDealParams() {

    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getAppointMoney() {
        return appointMoney;
    }

    public void setAppointMoney(String appointMoney) {
        this.appointMoney = appointMoney;
    }

    @Override
    public String toString() {
        return "YuYueDealParams{" +
                "projectNum='" + projectNum + '\'' +
                ", appointMoney=" + appointMoney +
                '}';
    }
}
