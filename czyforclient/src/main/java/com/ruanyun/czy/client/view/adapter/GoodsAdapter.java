package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.GoodsInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description ：商品列表
 * <p/>
 * Created by ycw on 2016/9/22.
 */
public class GoodsAdapter extends MultiItemTypeAdapter<GoodsInfo> {
    public GoodsAdapter(Context context, List<GoodsInfo> datas) {
        super(context, datas);
        addItemViewDelegate(new GoodsItem());
        addItemViewDelegate(new GoodsMoney());
    }

    private class GoodsItem implements ItemViewDelegate<GoodsInfo> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.layout_product_view;
        }

        @Override
        public boolean isForViewType(GoodsInfo item, int position) {
            return !item.isPrice();
        }

        @Override
        public void convert(ViewHolder holder, final GoodsInfo goodsInfo, int position) {
            AutoUtils.auto(holder.getConvertView());
            TextView tvType = holder.getView(R.id.tv_type);
            RelativeLayout rlGoodsInfo = holder.getView(R.id.rl_goods_info);
            ImageView ivGoodsPic= holder.getView(R.id.iv_goods_pic);
            TextView tvTitle= holder.getView(R.id.tv_title);
            TextView tvPrice= holder.getView(R.id.tv_price);
            TextView tvCountSurplus= holder.getView(R.id.tv_count_surplus);
            tvCountSurplus.setText("剩余数量："+goodsInfo.getTotalCountSurplus());

            tvType.setText(DbHelper.getInstance().getParentName(goodsInfo.getOrderType(), C.ParentCode.ORDER_INFO_TYPE));
            Glide.with(mContext).load(FileUtil.getImageUrl(goodsInfo.getMainPhoto()))
                    .error(R.drawable.default_img).placeholder(R.drawable.default_imge_big)
                    .into(ivGoodsPic);
            tvTitle.setText(goodsInfo.getGoodsName());
            tvPrice.setText(AppUtility.getSpannerString(mContext, "x", String.format("¥%2.2fx%d", goodsInfo.getActivityPrice(), goodsInfo.getTotalCount()), true));

            if (goodsInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_GD)) {
                rlGoodsInfo.setVisibility(View.GONE);
            } else {
                rlGoodsInfo.setVisibility(View.VISIBLE);
            }


//            跳转商品详情
//            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AppUtility.showGoodsWebView(goodsInfo.getActivityPrice(), App.getInstance().getCurrentUserNum(),
//                            goodsInfo.getGoodsNum(), goodsInfo.getOrderType(),
//                            goodsInfo.getCommonNum(), App.getInstance().getCurrentUserNum(), mContext,
//                            goodsInfo.getGoodsName(), goodsInfo.getProjectNum(),
//                            goodsInfo.getMainPhoto(),
//                            TextUtils.isEmpty(goodsInfo.getCommonFlag()) ? "1" : goodsInfo.getCommonFlag());
//                }
//            });
        }
    }

    private class GoodsMoney implements ItemViewDelegate<GoodsInfo> {


        @Override
        public int getItemViewLayoutId() {
            return R.layout.layout_money_each;
        }

        @Override
        public boolean isForViewType(GoodsInfo item, int position) {
            return item.isPrice();
        }

        @Override
        public void convert(ViewHolder holder, GoodsInfo goodsInfo, int position) {
            AutoUtils.auto(holder.getConvertView());
            TextView tvPriceName = holder.getView(R.id.tv_price_name);
            TextView tvPrice = holder.getView(R.id.tv_price);
            tvPriceName.setText(goodsInfo.getGoodsName());
            tvPrice.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
            String priceStr = "";
            switch (goodsInfo.getPriceType()) {
                case GoodsInfo.TYPE_NORMAL:
                    priceStr = new StringBuilder().append("¥").append(new BigDecimal(goodsInfo.getActivityPrice()).setScale(2,
                            BigDecimal.ROUND_HALF_UP)).toString();
                    break;
                case GoodsInfo.TYPE_FAVORABLE:
                    priceStr = new StringBuilder().append("-¥").append(new BigDecimal(goodsInfo.getActivityPrice()).setScale(2,
                            BigDecimal.ROUND_HALF_UP)).toString();
                    break;
                case GoodsInfo.TYPE_COLOR:
                    priceStr = new StringBuilder().append("¥").append(new BigDecimal(goodsInfo.getActivityPrice()).setScale(2,
                            BigDecimal.ROUND_HALF_UP)).toString();
                    tvPrice.setTextColor(ContextCompat.getColor(mContext, R.color.text_orange));
                    break;
                case GoodsInfo.TYPE_INTEGRAL:
                    priceStr = new StringBuilder().append("+").append(new BigDecimal(goodsInfo.getActivityPrice()).setScale(2,
                            BigDecimal.ROUND_HALF_UP)).toString();
                    break;
            }
            tvPrice.setText(priceStr);
        }
    }
}
