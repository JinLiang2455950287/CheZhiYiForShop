package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：赠送优惠券(技师端)
 * <p/>
 * Created by hdl on 2016/10/9.
 */

public class SendDiscountCouponParams {
    private String userNums;//选择发送用户的编号 格式为 A,B,C  或者 数组
    private String userCouponNum;//优惠卷编号
    private String goodsNum;//优惠卷产品编号

    public String getUserNums() {
        return userNums;
    }

    public void setUserNums(String userNums) {
        this.userNums = userNums;
    }

    public String getUserCouponNum() {
        return userCouponNum;
    }

    public void setUserCouponNum(String userCouponNum) {
        this.userCouponNum = userCouponNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }
}
