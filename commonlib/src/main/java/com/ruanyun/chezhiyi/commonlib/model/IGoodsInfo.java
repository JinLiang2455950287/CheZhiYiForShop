package com.ruanyun.chezhiyi.commonlib.model;

import android.os.Parcelable;

/**
 * Description ：商品的抽象接口
 * <p/>
 * Created by ycw on 2016/12/28.
 */

public interface IGoodsInfo extends Parcelable {

    /** 获取商品的公共编号
     * @return
     */
    String getCommonNum();

    /** 获取商品的编号
     * @return
     */
    String getGoodsNum();

    /**
     * 获取商品的类型
     * @return
     */
//    String getSubmitGoodsType();
    String getGoodsType();

    /**
     * 获取商品名称
     * @return
     */
    String getGoodsName();

    /**
     * 获取商品的支付价格
     * @return
     */
    double getActivityPrice();

    /**
     * 获取商品所属分类
     * @return
     */
    String getProjectNum();

    /**
     * 获取商品的主图
     * @return
     */
    String getMainPhoto();

    double getScoreNumber();
}
