package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description ：代下单管理子数据
 * <p/>
 * Created by hdl on 2016/10/8.
 */

public class InsteadOrderDelagate implements ItemViewDelegate<OrderGoodsInfo> {
    Context context;

    public InsteadOrderDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_item_instead_order;
    }

    @Override
    public boolean isForViewType(OrderGoodsInfo item, int position) {
        return !item.isParent();
    }

    @Override
    public void convert(ViewHolder holder, OrderGoodsInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(context, FileUtil.getImageUrl(item.getMainPhoto()), (ImageView) holder.getView(R.id.iv_photo), R.drawable.default_img);
        holder.setText(R.id.tv_goods_name, item.getGoodsName());
        holder.setText(R.id.tv_money, String.format("¥%s", item.getSinglePrice()));
        holder.setText(R.id.tv_goods_number, String.format("x%s", item.getTotalCount()));
        holder.setText(R.id.tv_total_money, String.format("¥%s", item.getTotalPrice()));
    }
}
