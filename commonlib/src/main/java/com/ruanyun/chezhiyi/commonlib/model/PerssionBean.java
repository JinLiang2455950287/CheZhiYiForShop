package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Created by czy on 2017/3/27.
 * 权限表
 */

public class PerssionBean {

    /**
     * appButton : null
     * appId : 74
     * btnId : 0
     * butCoordinate : top
     * butName : 开单
     * butNum : KD
     * roleId : 0
     */

    private Object appButton;
    private int appId;
    private int btnId;
    private String butCoordinate;
    private String butName;
    private String butNum;
    private int roleId;

    public Object getAppButton() {
        return appButton;
    }

    public void setAppButton(Object appButton) {
        this.appButton = appButton;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getBtnId() {
        return btnId;
    }

    public void setBtnId(int btnId) {
        this.btnId = btnId;
    }

    public String getButCoordinate() {
        return butCoordinate;
    }

    public void setButCoordinate(String butCoordinate) {
        this.butCoordinate = butCoordinate;
    }

    public String getButName() {
        return butName;
    }

    public void setButName(String butName) {
        this.butName = butName;
    }

    public String getButNum() {
        return butNum;
    }

    public void setButNum(String butNum) {
        this.butNum = butNum;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "PerssionBean{" +
                "appButton=" + appButton +
                ", appId=" + appId +
                ", btnId=" + btnId +
                ", butCoordinate='" + butCoordinate + '\'' +
                ", butName='" + butName + '\'' +
                ", butNum='" + butNum + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
