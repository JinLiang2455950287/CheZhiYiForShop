package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.CardPackageListModel;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 卡套餐Adapter
 * Created by hdl on 2017/3/21
 */
public class CardPackageListAdapter extends CommonAdapter<CardPackageListModel> {


    public CardPackageListAdapter(Context context, int layoutId, List<CardPackageListModel> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<CardPackageListModel> datas) {
        mDatas = datas;
    }


    @Override
    protected void convert(ViewHolder holder, final CardPackageListModel item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(""),
                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.cardpackage);

        holder.setText(R.id.tv_title, item.getPackageName());
//        holder.setText(R.id.tv_subtitle, "[价值" + (item.getPackageCost()+item.getPackagePrice()) + "元]" + " 优惠" + item.getPackageCost());
        holder.setText(R.id.tv_subtitle, "[价值" + item.getPackagePrice() + "元]" + item.getPackageName());
        holder.setText(R.id.tv_price, "¥" + item.getPackagePrice());
        holder.setText(R.id.tv_sold_number, "已售" + item.getPackageSale());
        TextView tvPurchase = holder.getView(R.id.tv_purchase);
        tvPurchase.setText("点击查看");

        tvPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductBuyClickListener.onProductBuyItemClick(item);
            }
        });

    }

    public interface OnProductBuyClickListener {
        void onProductBuyItemClick(CardPackageListModel cardPackageListModel);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
