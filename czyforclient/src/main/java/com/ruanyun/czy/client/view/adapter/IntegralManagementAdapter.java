package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

/**
 * Created by wp on 2016/10/12.
 */
public class IntegralManagementAdapter extends CommonAdapter<ProductInfo> {


    public IntegralManagementAdapter(Context context, List<ProductInfo> datas, int layoutId) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, final ProductInfo productInfo, int position) {

        ImageView ivProductPhoto = holder.getView(R.id.iv_product_photo);
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(productInfo.getMainPhoto()),
                ivProductPhoto, R.drawable.default_img);
        TextView tvIntegral = holder.getView(R.id.tv_integral);//钱
        tvIntegral.setText(String.format(Locale.CHINA, "¥%.2f", productInfo.getSalePrice()));
        TextView tvSubTitle = holder.getView(R.id.tv_subtitle);
        tvSubTitle.setText(String.format("[价值%s]", productInfo.getMarketPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        TextView tvTitle = holder.getView(R.id.tv_title);
        tvTitle.setText(productInfo.getGoodsName());
        TextView tvExchange = holder.getView(R.id.tv_exchange);
//        String salePrice = "兑换价" + productInfo.getSalePrice() + "元";
//        Spannable span = new SpannableString(salePrice);
//        span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color
//                .text_organge)), 3, salePrice.length() - 1, Spannable
//                .SPAN_EXCLUSIVE_EXCLUSIVE);
        tvExchange.setText(productInfo.getViceTitle());
        final TextView tvPurchase = holder.getView(R.id.tv_purchase);
        tvPurchase.setText(String.format(Locale.CHINA, "+%.0f积分兑换", productInfo.getScoreNumber()));
        tvPurchase.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                if (purchaseListener != null)
                    purchaseListener.purchaseClick(productInfo);
            }
        });
    }

    public void setData(List<ProductInfo> datas) {
        this.mDatas = datas;
    }

    public void addData(List<ProductInfo> datas) {
        this.mDatas.addAll(datas);
    }


    public interface PurchaseListener {
        void purchaseClick(ProductInfo productInfo);
    }

    private PurchaseListener purchaseListener;

    public void setPurchaseListener(PurchaseListener purchaseListener) {
        this.purchaseListener = purchaseListener;
    }
}
