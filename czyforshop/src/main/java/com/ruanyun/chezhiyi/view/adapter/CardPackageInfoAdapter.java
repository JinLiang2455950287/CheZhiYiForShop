package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.GoodsListBean;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * Description ： 卡套餐详情提交订单
 * <p/>
 * Created by jl on 2017/3/21.
 */
public class CardPackageInfoAdapter extends CommonAdapter<GoodsListBean> {

    public CardPackageInfoAdapter(Context context, int layoutId, List<GoodsListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GoodsListBean goodsListBean, int position) {
        AutoUtils.auto(holder.getConvertView());
        LogX.e("卡套餐详情提交订单", goodsListBean.toString());
        ImageView ivImage = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.iv_product_photo);
        TextView tvTitle = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_title);
        TextView tvPrice = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_price);
        TextView tvpurchase = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_purchase);
        tvTitle.setText(goodsListBean.getGoodsName());
        tvPrice.setText("¥ " + goodsListBean.getGoodsPrice());
        LogX.e("不限次", goodsListBean.getGoodsCount());
        if (goodsListBean.getGoodsCount().equals("")) {
            tvpurchase.setText("不限次");
        } else {
            tvpurchase.setText("/" + goodsListBean.getGoodsCount());
        }

        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(goodsListBean.getMainPhoto()), ivImage, R.drawable.default_imge_middle);

    }

    public void setDatas(List<GoodsListBean> result) {
        this.mDatas = result;
    }
}
