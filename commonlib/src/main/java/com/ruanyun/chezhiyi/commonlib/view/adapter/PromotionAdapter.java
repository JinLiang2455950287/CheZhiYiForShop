package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：限时促销的数据
 * <p/>
 * Created by ycw on 2016/9/13.
 */
public class PromotionAdapter extends CommonAdapter<PromotionInfo> {


    public PromotionAdapter(Context context, int layoutId, List<PromotionInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, PromotionInfo promotionInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageView imageView = holder.getView(R.id.iv_promotion);
        TextView tvPurchaseTime = holder.getView(R.id.tv_purchase_time);
        TextView tvNewPrice = holder.getView(R.id.tv_news_price);
        TextView tvOldPrice = holder.getView(R.id.tv_old_price);

        Glide.with(mContext).load((FileUtil.getImageUrl(promotionInfo.getMainPhoto())).trim())
                .error(R.drawable.default_img).into(imageView);
        tvNewPrice.setText(new StringBuilder().append("¥").append(promotionInfo.getActivityPrice()).toString());
        tvOldPrice.setText(new StringBuilder().append("¥").append(promotionInfo.getMarketPrice()).toString());
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvPurchaseTime.setText("限时购: " + StringUtil.getMonthDayTime(promotionInfo.getPromotionBeginDate())
                + "—" + StringUtil.getMonthDayTime(promotionInfo.getPromotionEndDate()));
    }
}
