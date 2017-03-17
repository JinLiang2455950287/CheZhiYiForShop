package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.CrowdFundingInfo;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 众筹列表Adapter
 * Created by hdl on 2016/9/12
 */
public class CrowdFundingListAdapter
        extends CommonAdapter<CrowdFundingInfo> {

    private boolean isClient;
    // click callback
    private OnCrowdFundingClickListener onCrowdFundingClickListener;

    public CrowdFundingListAdapter(Context context, int layoutId, List<CrowdFundingInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setData(List<CrowdFundingInfo> datas) {
        this.mDatas = datas;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    @Override
    protected void convert(ViewHolder holder, final CrowdFundingInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        ImageUtil.loadImage(mContext,
                FileUtil.getImageUrl(item.getMainPhoto()),
                (ImageView) holder.getView(R.id.iv_product_photo),
                R.drawable.default_img);

        holder.setText(R.id.tv_title, item.getTitle());
        (holder.getView(R.id.tv_subtitle)).setVisibility(View.GONE);//副标题不显示
        (holder.getView(R.id.rl_progress_bar)).setVisibility(View.VISIBLE);//显示进度条
        ProgressBar progressBar = holder.getView(R.id.progress_bar);
        progressBar.setProgress((int) item.getProgress());//显示进度
        holder.setText(R.id.tv_percentage, String.format(Locale.CHINA, "%.2f%%", item.getProgress()));//显示进度百分比
        holder.setText(R.id.tv_limit_num, String.format("满%s人即售", item.getFullNum()));
        holder.setText(R.id.tv_sold_number, String.format("剩余%s天", item.getResidueDay()));
        TextView tvPurchase = holder.getView(R.id.tv_purchase);
        tvPurchase.setText("¥" + ((item.getActivityPrice() == 0) ? "0" : item.getActivityPrice()) + "抢");//众筹价
        tvPurchase.setBackgroundResource(item.getResidueDay() > 0 ? R.drawable.ease_button_selector_red : R.drawable.corner_rectangle_gray_shape_bg);
        tvPurchase.setEnabled(item.getResidueDay() > 0);
        tvPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCrowdFundingClickListener != null) onCrowdFundingClickListener.OnCrowdFundingItemClick(item);
            }
        });

        TextView tvPrice = holder.getView(R.id.tv_price);
        BigDecimal price;
        if (!isClient) {        //商家端不显示
            tvPurchase.setVisibility(View.GONE);
            price = item.getSalePrice();
            tvPrice.setTextColor(ContextCompat.getColor(mContext,R.color.text_orange));
        } else {
            tvPurchase.setVisibility(View.VISIBLE);
            price = item.getMarketPrice();
            tvPrice.setTextColor(ContextCompat.getColor(mContext,R.color.text_gray));
        }
        SpannableString spStr = new SpannableString(price == null ? "¥0" : "¥" + price);
        spStr.setSpan(new RelativeSizeSpan(0.7f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvPrice.setText(spStr);  //销售价
    }

    /**
     * 众筹剩余时间天数
     *
     * @param endTime
     */
    public int residueDay(String endTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        try {
            endDate = timeFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long residueTime = (endDate.getTime() + (24 * 60 * 60 * 1000) - 1) - System.currentTimeMillis();
        if (residueTime <= 0) {
            return 0;
        }
        double day = residueTime / 1000.0 / 60 / 60 / 24;
        return (int) (day);
    }

    public void setOnCrowdFundingClickListener(OnCrowdFundingClickListener onCrowdFundingClickListener) {
        this.onCrowdFundingClickListener = onCrowdFundingClickListener;
    }

    public interface OnCrowdFundingClickListener {
        void OnCrowdFundingItemClick(CrowdFundingInfo crowdFundingInfo);
    }
}
