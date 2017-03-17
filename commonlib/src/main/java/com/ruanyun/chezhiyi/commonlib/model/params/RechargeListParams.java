package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * 获取充值套餐
 * Created by Sxq on 2016/10/10.
 */
public class RechargeListParams extends PageParamsBase{
//    status	Integer	有效状态：1
//    orderBy	String 	排序字段：amount
//    order	String	排序值：asc
    /** 有效状态：1 **/
    private int status;
//    /** 排序字段：amount**/
//    private String orderBy;
//    /** 排序值**/
//    private String order;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public String getOrderBy() {
//        return orderBy;
//    }
//
//    public void setOrderBy(String orderBy) {
//        this.orderBy = orderBy;
//    }
//
//    public String getOrder() {
//        return order;
//    }
//
//    public void setOrder(String order) {
//        this.order = order;
//    }
}
