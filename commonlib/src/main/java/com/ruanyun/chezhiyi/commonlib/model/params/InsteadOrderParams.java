package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：代下单(添加商品)
 * <p/>
 * Created by hdl on 2016/10/10.
 */

public class InsteadOrderParams {
    /** 订单编号 */
    private String workOrderNum;
    /**
     * Json数组格式商品json格式 [{goodsNum:"产品或团购编号",goodsName:"商品名称",orderGoodsDetailNum:"已购商品编号",
     * "singlePrice":"价格 商家买的价格 金额类型",goodsTotalCount:"商品数量 int类型"}]  与 接单提交中的 商品格式一致
     */
    private String resultJosnString;

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getResultJosnString() {
        return resultJosnString;
    }

    public void setResultJosnString(String resultJosnString) {
        this.resultJosnString = resultJosnString;
    }
}
