package com.ruanyun.chezhiyi.commonlib.model.params;

/**
 * Description ：获取产品、团购、或给工单添加商品
 * <p/>
 * Created by hdl on 2016/9/12.
 */
public class ProductGroupPurchaseParams extends PageParamsBase {

    /** 服务一级编号 */
    private String projectParent;
    /** 值为：TG（团购） 值为：CP（产品） 值为：CP_01,TG_01,TG_02（给工单添加商品列表） */
    private String goodsType;
    /** 值为多个 CP_01,TG_01,TG_02 */
    private String goodsTypeString;

    public static final String GOODS_TYPE_PRODUCT = "CP";
    public static final String GOODS_TYPE_GROUP_PURCHASE = "TG";
    public static final String WORKORDER_ADD_GOODS = "CP_01,TG_01"/*,TG_02*/;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getProjectParent() {
        return projectParent;
    }

    public void setProjectParent(String projectParent) {
        this.projectParent = projectParent;
    }

    public String getGoodsTypeString() {
        return goodsTypeString;
    }

    public void setGoodsTypeString(String goodsTypeString) {
        this.goodsTypeString = goodsTypeString;
    }
}
