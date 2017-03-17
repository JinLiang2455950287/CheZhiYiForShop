package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：等候区服务商品Adapter
 * <p/>
 * Created by hdl on 2016/10/11.
 */

public class WaitionAreaServiceGoddsAdapter extends CommonAdapter<OrderGoodsInfo> {

    public WaitionAreaServiceGoddsAdapter(Context context, int layoutId, List<OrderGoodsInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setDatas(List<OrderGoodsInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, OrderGoodsInfo
            goodsInfo, int position) {
        AutoUtils.auto(viewHolder.getConvertView());
        viewHolder.setText(R.id.tv_goods_name, goodsInfo.getGoodsName());
        viewHolder.setText(R.id.tv_goods_number, "x"+goodsInfo.getTotalCount());

//        String goodsType = AppUtility.getTypeName(goodsInfo.getGoodsInfo().getGoodsType());
//        if ( goodsType.equals(C.OrderType.ORDER_TYPE_CP)||
//                goodsType.equals(C.OrderType.ORDER_TYPE_TG)||
//                goodsType.equals(C.OrderType.ORDER_TYPE_JF) ) {
//            viewHolder.setTextColorRes(com.ruanyun.chezhiyi.commonlib.R.id.tv_goods_name, com.ruanyun.chezhiyi.commonlib.R.color.theme_color_default);
//        } else {
//            viewHolder.setTextColorRes(com.ruanyun.chezhiyi.commonlib.R.id.tv_goods_name, com.ruanyun.chezhiyi.commonlib.R.color.text_gray);
//        }
    }

}
