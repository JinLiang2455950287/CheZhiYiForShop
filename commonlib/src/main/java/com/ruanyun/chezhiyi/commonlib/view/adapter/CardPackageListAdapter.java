package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.CardPackageListModel;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
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
        LogX.e("卡套餐", item.toString());
        holder.setText(R.id.tv_title, item.getPackageName());
        holder.setText(R.id.tv_price, "¥ " + item.getPackagePrice());
        TextView tvorrgionprice = holder.getView(R.id.tv_orrgionprice);
        TextView tvPurchase = holder.getView(R.id.tv_purchase);
        TextView tvsubtitle = holder.getView(R.id.tv_subtitle);
        tvorrgionprice.setText("原价 ：¥ " + item.getPackageOprice() + "");
        tvorrgionprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
        tvsubtitle.setText(item.getPackageExpiryDate() + "个月内有效");
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
