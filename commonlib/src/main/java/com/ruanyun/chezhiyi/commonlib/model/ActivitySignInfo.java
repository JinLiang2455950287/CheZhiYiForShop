package com.ruanyun.chezhiyi.commonlib.model;

/**
 * Description ：活动报名数据结构 TActivitySign
 * <p>
 * Created by ycw on 2016/10/24.
 */
public class ActivitySignInfo {
    private int signId;//	Int	主键
    private String signNum;//	String	编号
    private String activityNum;//	String	活动编号【活动数据结构的业务主键】
    private String userName;//	String	联系人
    private int signStatus;//	String	状态
    private String linkTel;//	String	联系电话
    private String remark;//	String	备注
    private double totalPrice;//	BigDecimal	总价格【活动数据结构的cost相乘totalNum】
    private int totalNum;//	int	报名人数
    private String userNum;//	String	用户编号
    private String createTime;//	String	报名时间
    private OrderInfo orderInfo;//	{}	订单对象订单数据结构TorderInfo
    private ActivityInfo activityInfo;//	{}	活动报名对象 活动信息数据结构TActivityInfo


    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
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

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public ActivityInfo getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
    }
}
