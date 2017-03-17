package com.ruanyun.chezhiyi.commonlib.view.adapter;


import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Description ：工单流水商品的列表
 * <p>
 * Created by ycw on 2016/9/27.
 */
public class WorkOrderGoodsAdapter extends CommonAdapter<OrderGoodsInfo> {

    public WorkOrderGoodsAdapter(Context context, int layoutId, List<OrderGoodsInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, OrderGoodsInfo goodsInfo, int position) {
        viewHolder.setText(R.id.tv_goods_name, goodsInfo.getGoodsName());
        if (goodsInfo.getIsDaiXiaDan() == 1) {  // 代下单  显示 （代）
            viewHolder.setVisible(R.id.tv_goods_add, true);
        } else {
            viewHolder.setVisible(R.id.tv_goods_add, false);
        }
//        LogX.e("0000000000",goodsInfo.getGoodsInfo().getGoodsType());
//        String goodsType = AppUtility.getTypeName(goodsInfo.getGoodsInfo().getGoodsType());
//        LogX.e("0000000000",goodsType);
//        if ( goodsType.equals(C.OrderType.ORDER_TYPE_CP)||
//                goodsType.equals(C.OrderType.ORDER_TYPE_TG)||
//                goodsType.equals(C.OrderType.ORDER_TYPE_JF) ) {
//            viewHolder.setTextColorRes(R.id.tv_goods_name, R.color.theme_color_default);
//        } else {
//            viewHolder.setTextColorRes(R.id.tv_goods_name, R.color.text_gray);
//        }
    }
}
