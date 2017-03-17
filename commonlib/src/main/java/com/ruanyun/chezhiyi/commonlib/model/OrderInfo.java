package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单信息
 * Created by Sxq on 2016/9/18.
 */
public class OrderInfo implements Parcelable {

    // 订单状态(-1已取消 1待付款 2、待消费 3、待评价 4、退款中  5、退款完成 6、已完成)
    /** 退款成功  */
    public static final int ORDER_STATE_REFUNDED_COMPLETED_FINISH = -2;
    /** 已取消 */
    public static final int ORDER_STATE_CANCELED = -1;
    /** 待付款 */
    public static final int ORDER_STATE_PENDING_PAYMENT = 1;
    /**  待消费 */
    public static final int ORDER_STATE_CONSUMED = 2;
    /**  待评价 */
    public static final int ORDER_STATE_EVALUATED = 3;
    /**  退款中 */
    public static final int ORDER_STATE_REFUNDEDING = 4;
    /** 已完成**/
    public static final int ORDER_STATE_REFUNDED = 5;

    /**
     * 众筹支付成功
     */
    public static final int ORDER_STATE_ZC_REFUNDED = 10;

    //支付方式Alipay
    public static final int PAY_MEMBERSHIP_CARD=1;//1会员卡支付
    public static final int PAY_ALIPAY=2;//2支付宝
    public static final int PAY_WX=3;//3微信
    public static final int PAY_SHOP=4;//4到店支付
    public static final int PAY_GIVE=5;//5 赠送

      /** 订单编号 **/
    private String orderNum;
    /** 订单状态(-1已取消 1待付款 2、待消费 3、待评价 4、退款中  5、退款完成 6、已完成)**/
    private int orderStatus;
    /** 下单人账号**/
    private String orderLoginName;
    /** 下单人num**/
    private String orderUserNum;
    /** 下单人名称 **/
    private String orderUserName;
    /** 备注 **/
    private String orderRemark;
    /** 订单类型 **/
    private String orderType;
    /** 公共编号**/
    private String commonNum;
    /** 门店编号**/
    private String storeNum;
    /** 数量**/
    private int totalCount;
    /** 单价**/
    private BigDecimal  singlePrice;
    /** 总价**/
    private BigDecimal  totalPrice;
    /** 优惠金额**/
    private BigDecimal  preferentialPrice;
    /** 优惠卷编号**/
    private String couponNum;
    /** 实际支付金额**/
    private BigDecimal actualPrice;
    /** 支付方式**/
    private int payMethod;
    /** 支付时间**/
    private String payTime;
    /** 消费时间**/
   private String consumeTime;
    /**创建时间 **/
    private String orderCreateTime;
    /** 第三方账号**/
    private String payThirtAccount;
   /**订单商品【购买产品时有 报名定金等都没有该值】**/
    private List<OrderGoodsInfo> orderGoodsList;
   //订单状态  传多个状态使用 例如 1,2,3 使用该字段
    private String orderStatusString;
    /**   剩余数量  */
    private int totalCountSurplus;

    public int getTotalCountSurplus() {
        return totalCountSurplus;
    }

    public void setTotalCountSurplus(int totalCountSurplus) {
        this.totalCountSurplus = totalCountSurplus;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderLoginName() {
        return orderLoginName;
    }

    public void setOrderLoginName(String orderLoginName) {
        this.orderLoginName = orderLoginName;
    }

    public String getOrderUserNum() {
        return orderUserNum;
    }

    public void setOrderUserNum(String orderUserNum) {
        this.orderUserNum = orderUserNum;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCommonNum() {
        return commonNum;
    }

    public void setCommonNum(String commonNum) {
        this.commonNum = commonNum;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(String consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getPayThirtAccount() {
        return payThirtAccount;
    }

    public void setPayThirtAccount(String payThirtAccount) {
        this.payThirtAccount = payThirtAccount;
    }

    public List<OrderGoodsInfo> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoodsInfo> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public OrderInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderNum);
        dest.writeInt(this.orderStatus);
        dest.writeString(this.orderLoginName);
        dest.writeString(this.orderUserNum);
        dest.writeString(this.orderUserName);
        dest.writeString(this.orderRemark);
        dest.writeString(this.orderType);
        dest.writeString(this.commonNum);
        dest.writeString(this.storeNum);
        dest.writeInt(this.totalCount);
        dest.writeSerializable(this.singlePrice);
        dest.writeSerializable(this.totalPrice);
        dest.writeSerializable(this.preferentialPrice);
        dest.writeString(this.couponNum);
        dest.writeSerializable(this.actualPrice);
        dest.writeInt(this.payMethod);
        dest.writeString(this.payTime);
        dest.writeString(this.consumeTime);
        dest.writeString(this.orderCreateTime);
        dest.writeString(this.payThirtAccount);
        dest.writeTypedList(this.orderGoodsList);
        dest.writeString(this.orderStatusString);
        dest.writeInt(this.totalCountSurplus);
    }

    protected OrderInfo(Parcel in) {
        this.orderNum = in.readString();
        this.orderStatus = in.readInt();
        this.orderLoginName = in.readString();
        this.orderUserNum = in.readString();
        this.orderUserName = in.readString();
        this.orderRemark = in.readString();
        this.orderType = in.readString();
        this.commonNum = in.readString();
        this.storeNum = in.readString();
        this.totalCount = in.readInt();
        this.singlePrice = (BigDecimal) in.readSerializable();
        this.totalPrice = (BigDecimal) in.readSerializable();
        this.preferentialPrice = (BigDecimal) in.readSerializable();
        this.couponNum = in.readString();
        this.actualPrice = (BigDecimal) in.readSerializable();
        this.payMethod = in.readInt();
        this.payTime = in.readString();
        this.consumeTime = in.readString();
        this.orderCreateTime = in.readString();
        this.payThirtAccount = in.readString();
        this.orderGoodsList = in.createTypedArrayList(OrderGoodsInfo.CREATOR);
        this.orderStatusString = in.readString();
        this.totalCountSurplus = in.readInt();
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
