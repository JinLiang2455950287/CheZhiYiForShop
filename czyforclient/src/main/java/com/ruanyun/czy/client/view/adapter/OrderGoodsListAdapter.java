package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Sxq on 2016/9/19.
 */
public class OrderGoodsListAdapter extends CommonAdapter<OrderGoodsInfo> {
    public OrderGoodsListAdapter(Context context, int layoutId, List<OrderGoodsInfo> datas) {
        super(context, layoutId, datas);
    }
    @Override
    protected void convert(ViewHolder holder, OrderGoodsInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageView imgPic = holder.getView(R.id.iv_goods_pic);
        Glide.with(mContext).load(FileUtil.getImageUrl(item.getMainPhoto())).error(R.drawable
                .default_img).into(imgPic);
        TextView tvOrderGood = holder.getView(R.id.tv_title);//商品名称
        tvOrderGood.setText(item.getGoodsName());
        TextView tvPrice = holder.getView(R.id.tv_price);
        SpannableString string = AppUtility.getSpannerString(mContext, "x", String.format
                ("¥%sx%s", item.getSinglePrice(), item.getTotalCount()), true);
        tvPrice.setText(string); //市场价
    }
}
