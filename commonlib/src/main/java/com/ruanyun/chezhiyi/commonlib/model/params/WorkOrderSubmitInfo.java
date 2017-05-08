package com.ruanyun.chezhiyi.commonlib.model.params;

import java.util.List;

/**
 * Description: 提交工单参数 用于转成json字符串传给服务端
 * author: zhangsan on 16/10/9 下午2:23.
 */
public class WorkOrderSubmitInfo {

    /**
     * carAllName :
     * customerUserNum : 司机编号
     * phone :
     * remark : 备注
     * makeNum : 预约编号
     * workOrderList : [{"workOrderNum":"订单编号","workbayInfoNum":"工位编号","workbayName":"工位名称","leadingUserNum":"施工负责人编号","leadingUserName":"施工负责人名称","projectNum":"服务一级大类","workOrderGoodsList":[{"goodsNum":"产品或团购编号","goodsName":"商品名称","orderGoodsDetailNum":"已购商品编号","singlePrice":"价格 商家买的价格 金额类型","goodsTotalCount":"商品数量 int类型"}]}]
     */
    public String carAllName="";
    public String customerUserNum="";
    public String phone="";
    public String remark="";
    public String makeNum="";
    public List<WorkOrderListInfo> workOrderList;
    /**
     * workOrderNum : 订单编号
     * workbayInfoNum : 工位编号
     * workbayName : 工位名称
     * leadingUserNum : 施工负责人编号
     * leadingUserName : 施工负责人名称
     * projectNum : 服务一级大类
     * workOrderGoodsList : [{"goodsNum":"产品或团购编号","goodsName":"商品名称","orderGoodsDetailNum":"已购商品编号","singlePrice":"价格 商家买的价格 金额类型","goodsTotalCount":"商品数量 int类型"}]
     */
    public static class WorkOrderListInfo {
        public String workOrderNum="";
        public String workbayInfoNum="";
        public String workbayName="";
        public String leadingUserNum="";
        public String leadingUserName="";
        public String projectNum="";
        public List<WorkOrderGoods> workOrderGoodsList;

        @Override
        public String toString() {
            return "WorkOrderListInfo{" +
                    "workOrderNum='" + workOrderNum + '\'' +
                    ", workbayInfoNum='" + workbayInfoNum + '\'' +
                    ", workbayName='" + workbayName + '\'' +
                    ", leadingUserNum='" + leadingUserNum + '\'' +
                    ", leadingUserName='" + leadingUserName + '\'' +
                    ", projectNum='" + projectNum + '\'' +
                    ", workOrderGoodsList=" + workOrderGoodsList +
                    '}';
        }
    }

    /**
     * goodsNum : 产品或团购编号
     * goodsName : 商品名称
     * orderGoodsDetailNum : 已购商品编号
     * singlePrice : 价格 商家买的价格 金额类型
     * goodsTotalCount : 商品数量 int类型
     */
    public static class WorkOrderGoods {
        public String goodsNum="";
        public String goodsName="";
        public String orderGoodsDetailNum="";
        public String singlePrice="0";
        public int goodsTotalCount=1;
        public String mainPhoto="";
        public String sgtcAmount="0";
        public String xstcAmount="0";
        public WorkOrderGoods(){

        }

        public WorkOrderGoods(String goodsNum, String goodsName, String orderGoodsDetailNum,
                              String singlePrice, int goodsTotalCount,
                              String mainPhoto, String sgtcAmount, String xstcAmount) {
            this.goodsNum = goodsNum;
            this.goodsName = goodsName;
            this.orderGoodsDetailNum = orderGoodsDetailNum;
            this.singlePrice = singlePrice;
            this.goodsTotalCount = goodsTotalCount;
            this.mainPhoto = mainPhoto;
            this.sgtcAmount = sgtcAmount;
            this.xstcAmount = xstcAmount;
        }

        @Override
        public String toString() {
            return "WorkOrderGoods{" +
                    "goodsNum='" + goodsNum + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", orderGoodsDetailNum='" + orderGoodsDetailNum + '\'' +
                    ", singlePrice='" + singlePrice + '\'' +
                    ", goodsTotalCount=" + goodsTotalCount +
                    ", mainPhoto='" + mainPhoto + '\'' +
                    ", sgtcAmount='" + sgtcAmount + '\'' +
                    ", xstcAmount='" + xstcAmount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WorkOrderSubmitInfo{" +
                "carAllName='" + carAllName + '\'' +
                ", customerUserNum='" + customerUserNum + '\'' +
                ", phone='" + phone + '\'' +
                ", remark='" + remark + '\'' +
                ", makeNum='" + makeNum + '\'' +
                ", workOrderList=" + workOrderList +
                '}';
    }
}
