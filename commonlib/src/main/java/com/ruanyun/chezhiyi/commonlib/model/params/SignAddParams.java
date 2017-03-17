package com.ruanyun.chezhiyi.commonlib.model.params;

import java.math.BigDecimal;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/9/14.
 */
public class SignAddParams {
    private String activityNum;//	String	活动编号【活动数据结构的业务主键】
    private String userName;//	String	联系人
    private String linkTel;//	String	联系电话
    private String remark;//	String	备注
    private String totalPrice;//	BigDecimal	总价格【活动数据结构的cost相乘totalNum】
    private Integer totalNum;//	int	报名人数

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(String activityNum) {
        this.activityNum = activityNum;
    }
}
