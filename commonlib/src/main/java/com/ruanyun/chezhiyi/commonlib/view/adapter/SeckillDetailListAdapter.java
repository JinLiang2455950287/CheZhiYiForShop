package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.SeckillDetailInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;
import java.util.Locale;

/**
 * 秒杀列表Adapter
 * Created by hdl on 2016/9/12
 */
public class SeckillDetailListAdapter
        extends CommonAdapter<SeckillDetailInfo> {

    public boolean purchaseEnable = true;
    public boolean isRight = false;

    // click callback
    private OnSeckillClickListener onSeckillClickListener;

    public SeckillDetailListAdapter(Context context, int layoutId, List<SeckillDetailInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<SeckillDetailInfo> datas) {
        mDatas = datas;
    }

    public void addData(List<SeckillDetailInfo> datas) {
        mDatas.addAll(datas);
    }

    @Override
    protected void convert(ViewHolder holder, final SeckillDetailInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        Glide.with(mContext)
                .load(FileUtil.getImageUrl(item.getMainPhoto()))
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img)
                .into((ImageView) holder.getView(R.id.iv_product_photo));
        holder.setText(R.id.tv_price, "¥" + item.getSalePrice().toString());
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_subtitle, "[价值" + item.getMarketPrice() + "元]" + item.getViceTitle());
        //不可以买
//        boolean canSale = item.getSaleCount() >= item.getActivityCount();
//        if (canSale) {
//            holder.setText(R.id.tv_sold_number, "已售100%");
//        } else {
//            int progress = item.getSaleCount() * 100 / item.getActivityCount();//销售进度
//            if (item.getSaleCount() > 0 && progress == 0) {
//                holder.setText(R.id.tv_sold_number, "已售1%");
//            } else {
//                holder.setText(R.id.tv_sold_number, "已售" + progress + "%");
//            }
//        }
//        if (isRight) {
//            holder.setText(R.id.tv_sold_number, "已售0%");
//        }

        holder.setText(R.id.tv_sold_number, getText(item));
        TextView tvPurchase = holder.getView(R.id.tv_purchase);

        if (!App.getInstance().isClient()) {
            tvPurchase.setVisibility(View.GONE);
        }
        tvPurchase.setBackgroundResource(getPurchaseDrawable(item));
        tvPurchase.setEnabled(getSaleEnable(item));
        tvPurchase.setText("¥" + item.getActivityPrice() + "抢");
        tvPurchase.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                if (onSeckillClickListener != null) {
                    onSeckillClickListener.OnSeckillItemClick(item);
                }
            }
        });
    }

    private String getText(SeckillDetailInfo item) {
        boolean canSale = item.getSaleCount() < item.getActivityCount();
        double progress = item.getSaleCount() * 100f / item.getActivityCount();//销售进度
        if (canSale) {
            return String.format(Locale.CHINA, "已售%.1f%%", progress);
        } else {
            return "已售100%";
        }
    }

    public boolean getSaleEnable(SeckillDetailInfo item) {
        boolean canSale = item.getSaleCount() < item.getActivityCount();
        return canSale && purchaseEnable;
    }

    private int getPurchaseDrawable(SeckillDetailInfo item) {
        return getSaleEnable(item) ? R.drawable.ease_button_selector_red : R.drawable.corner_rectangle_gray_shape_bg;
    }

    /**
     * 设置秒杀按钮回调监听
     *
     * @param onSeckillClickListener
     */
    public void setOnSeckillClickListener(OnSeckillClickListener onSeckillClickListener) {
        this.onSeckillClickListener = onSeckillClickListener;
    }

    /**
     * 秒杀按钮 点击回调
     */
    public interface OnSeckillClickListener {
        void OnSeckillItemClick(SeckillDetailInfo info);
    }
}
