package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 订单列表参数
 * Created by Sxq on 2016/9/19.
 */
public class OrderListParams extends PageParamsBase {
    /**
     * 读取字典表T_ORDER_INFO_ORDER_STATUS
     * 订单状态(-1已取消 1待付款 2、待消费 3、待评价 4、退款中  5、退款完成 6、已完成)
     */
    private String orderStatus;
    /**
     * 读取字典表T_ORDER_INFO_ORDER_TYPE
     * 如果为多个则以逗号分隔
     * 例如我的订单需要传值TG,CP,MS,CX
     * 众筹传值ZC
     */
    private String orderType;

    /**
     * orderStatusString	String	订单状态  传多个状态使用 例如 1,2,3 使用该字段
     *
     * @return
     */
    private String orderStatusString;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }
}
