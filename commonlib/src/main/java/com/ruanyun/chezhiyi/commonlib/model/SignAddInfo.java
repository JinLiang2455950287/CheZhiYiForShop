package com.ruanyun.chezhiyi.commonlib.model;

import java.math.BigDecimal;

/**
 * Description ：活动报名数据结构
 * <p/>
 * Created by ycw on 2016/9/14.
 */
public class SignAddInfo {
    private  int signId;//	Int	主键
    private  String signNum;//	String	编号
    private  String activityNum;//	String	活动编号【活动数据结构的业务主键】
    private  String userName;//	String	联系人
    private  String linkTel;//	String	联系电话
    private  String remark;//	String	备注
    private BigDecimal totalPrice;//	BigDecimal	总价格【活动数据结构的cost相乘totalNum】
    private  int totalNum;//	int	报名人数
    private  String userNum;//	String	用户编号
    private  String createTime;//	String	报名时间
    // : 2016/9/19 添加订单数据结构
    private OrderInfo orderInfo;//	{}	订单对象订单数据结构TorderInfo

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public int getSignId() {
        return signId;
    }

    public void setSignId(int signId) {
        this.signId = signId;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(String activityNum) {
        this.activityNum = activityNum;
    }

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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
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
}
