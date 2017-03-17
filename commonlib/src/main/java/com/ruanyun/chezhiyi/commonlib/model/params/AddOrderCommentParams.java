package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/10/14.
 */
public class AddOrderCommentParams {
    private String orderNum;//	String	订单编号
    private String jsonArrayString;//	String	评论内容，加工成json数组格式[{"glNum":"门店编号storeNum","commentType":"t_store_info","commentContent":"评论内容","commentScore":"评论分值"},{"glNum":"商品编号goodsNum","commentType":"t_goods_info","commentContent":"评论内容","commentScore":"评论分值"}]


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getJsonArrayString() {
        return jsonArrayString;
    }

    public void setJsonArrayString(String jsonArrayString) {
        this.jsonArrayString = jsonArrayString;
    }
}
