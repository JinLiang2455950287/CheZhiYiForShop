package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 限时促销列表Adapter
 * Created by hdl on 2016/9/12
 */
public class PromotionListAdapter
        extends CommonAdapter<PromotionInfo> {
    // click callback
    private OnPromotionBuyClickListener onPromotionBuyClickListener;
    private boolean isClient;

    public PromotionListAdapter(Context context, int layoutId, List<PromotionInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<PromotionInfo> datas) {
        mDatas = datas;
    }

    public void setOnPopupClickListener(OnPromotionBuyClickListener onPromotionBuyClickListener) {
        this.onPromotionBuyClickListener = onPromotionBuyClickListener;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    @Override
    protected void convert(ViewHolder holder, final PromotionInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(item.getMainPhoto()),
                (ImageView) holder.getView(R.id.iv_product_photo), R.drawable.default_img);

        String price = "¥" + item.getSalePrice();
        SpannableString spStr = new SpannableString(price);
        spStr.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        TextView tvPrice = holder.getView(R.id.tv_price);
        tvPrice.setText(spStr);//销售价

        holder.setText(R.id.tv_sold_number, "已售" + item.getSaleCount());
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_subtitle, "[价值" + item.getMarketPrice() + "元]" + item.getViceTitle());
        TextView tvPurchase = holder.getView(R.id.tv_purchase);
        holder.getView(R.id.tv_purchase_time).setVisibility(View.VISIBLE);//显示限时购
        holder.setText(R.id.tv_purchase_time, "限时购: " + StringUtil.getMonthDayTime(item.getPromotionBeginDate())
                + "—" + StringUtil.getMonthDayTime(item.getPromotionEndDate()));
        tvPurchase.setText("¥" + item.getActivityPrice() + "抢");
        tvPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPromotionBuyClickListener.onPromotionBuyItemClick(item);
            }
        });
        if (!isClient) {//商家端不显示
            tvPurchase.setVisibility(View.GONE);
        }
    }


    public interface OnPromotionBuyClickListener {
        void onPromotionBuyItemClick(PromotionInfo PromotionInfo);
    }
}
