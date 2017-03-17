package com.ruanyun.chezhiyi.commonlib.model;

import java.util.List;

/**
 * Description ：提成表结构TWorkOrderCommission
 * <p/>
 * Created by ycw on 2016/11/5.
 */
public class WorkOrderCommissionInfo {

    private int commissionInfoId;//	Integer	主键
    private String commissionInfoNum;//	String 	主键业务编号
    private String userNum;//	String 	提成人num
    private String userName;//	String	姓名
    private String workOrderNum;//	String 	工单编号
    private String projectNum;//	String 	服务编号
    private String projectName;//	String 	名称
    private String goodsName;//	Date   	服务商品名称
    private double commissionAmount;//	BigDecimal	提成金额
    private int commissionType;//	Integer	提成类型 1销售提成 2施工提成
    private int commissionRecord;//	Integer	commission_type=1（1销售人员提成 2销售经理提成）commission_type=2 （1施工人员提成 2协助人员提成）
    private String commonNum;//	String	公共num
    private String createUserNum;//	String	创建人
    private String createTime;//	Date	创建时间
    private String sysDate;//	Date	系统当前时间
    private List<AssistUserInfo> assistList;//	[]	协助人 数据结构

    public int getCommissionInfoId() {
        return commissionInfoId;
    }

    public void setCommissionInfoId(int commissionInfoId) {
        this.commissionInfoId = commissionInfoId;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public String getCommissionInfoNum() {
        return commissionInfoNum;
    }

    public void setCommissionInfoNum(String commissionInfoNum) {
        this.commissionInfoNum = commissionInfoNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public int getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    public int getCommissionRecord() {
        return commissionRecord;
    }

    public void setCommissionRecord(int commissionRecord) {
        this.commissionRecord = commissionRecord;
    }

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }

    public String getCreateUserNum() {
        return createUserNum;
    }

    public void setCreateUserNum(String createUserNum) {
        this.createUserNum = createUserNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<AssistUserInfo> getAssistList() {
        return assistList;
    }

    public void setAssistList(List<AssistUserInfo> assistList) {
        this.assistList = assistList;
    }
}
