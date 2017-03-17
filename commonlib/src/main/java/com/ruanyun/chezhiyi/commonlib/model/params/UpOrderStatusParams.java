package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：修改订单状态的参数
 * <p/>
 * Created by ycw on 2016/10/11.
 */
public class UpOrderStatusParams {
    private String orderNum;//	String	订单编号
    private Integer orderStatus;//	Int	读取字典表T_ORDER_INFO_ORDER_STATUS  订单状态((-1已取消 1待付款 2、待消费 3、待评价 4、退款中  5、退款完成 6、已完成) 取消订单传值-1

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }
    /**	Int	读取字典表T_ORDER_INFO_ORDER_STATUS  订单状态((-1已取消 1待付款 2、待消费 3、待评价 4、退款中  5、退款完成 6、已完成) 取消订单传值-1 */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
