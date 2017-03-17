package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Description ：代下单管理adapter
 * <p/>
 * Created by hdl on 2016/10/8.
 */

public class InsteadOrderAdapter extends MultiItemTypeAdapter<OrderGoodsInfo> {

    public InsteadOrderAdapter(Context context, List<OrderGoodsInfo> datas) {
        super(context, datas);
        addItemViewDelegate(new InsteadOrderParentDelagate(context));
        addItemViewDelegate(new InsteadOrderDelagate(context));
    }

    /**
     * 下拉刷新
     */
    public void refresh(List<OrderGoodsInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     */
    public void loadMore(List<OrderGoodsInfo> datas){
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
