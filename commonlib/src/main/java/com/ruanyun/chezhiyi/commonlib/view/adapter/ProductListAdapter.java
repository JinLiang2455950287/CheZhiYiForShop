package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 产品、团购列表Adapter
 * Created by hdl on 2016/9/12
 */
public class ProductListAdapter extends CommonAdapter<ProductInfo> {

    private boolean isClient;
    public ProductListAdapter(Context context, int layoutId, List<ProductInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<ProductInfo> datas){
        mDatas = datas;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    @Override
    protected void convert(ViewHolder holder, final ProductInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext,FileUtil.getImageUrl(item.getMainPhoto()),
                (ImageView) holder.getView(R.id.iv_product_photo),R.drawable.default_img);

        String price = "¥" + item.getSalePrice();
        SpannableString spStr = new SpannableString(price);
        spStr.setSpan(new RelativeSizeSpan(0.7f),0,1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        TextView tvPrice = holder.getView(R.id.tv_price);
        tvPrice.setText(spStr);//销售价

        holder.setText(R.id.tv_sold_number,"已售"+item.getSoldNumber());
        holder.setText(R.id.tv_title,item.getGoodsName());
        holder.setText(R.id.tv_subtitle,"[价值"+item.getMarketPrice()+"元]"+item.getViceTitle());
        TextView tvPurchase = holder.getView(R.id.tv_purchase);
        if (C.OrderType.ORDER_TYPE_CP.equals( item.getGoodsType() )){
            tvPurchase.setText("立即购买");
        }else {
            tvPurchase.setText("立即抢购");
        }
        tvPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductBuyClickListener.onProductBuyItemClick(item);
            }
        });
        if(!isClient){//商家端不显示
            tvPurchase.setVisibility(View.GONE);
        }
    }

    public interface OnProductBuyClickListener {
        void onProductBuyItemClick(ProductInfo productInfo);
    }

    // click callback
    OnProductBuyClickListener onProductBuyClickListener;

    public void setOnPopupClickListener(OnProductBuyClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }
}
