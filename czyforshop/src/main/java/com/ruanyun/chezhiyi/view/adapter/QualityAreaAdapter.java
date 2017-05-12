package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.widget.LabelFlowLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：质检区
 * <p/>
 * Created by hdl on 2017/4/10
 */

public class QualityAreaAdapter extends CommonAdapter<WorkOrderInfo> {

    //    LabelFlowLayout labelFlowLayout;

    public QualityAreaAdapter(Context context, int layoutId, List<WorkOrderInfo> datas) {
        super(context, layoutId, datas);
    }

    public static final int AWAIT_AREA = 1;//等候区
    public static final int CLEARING = 2;//结算中

    public void setDatas(List<WorkOrderInfo> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, final WorkOrderInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(item.getCarPicPath()), (ImageView) holder.getView(R.id.iv_car_photo), R.drawable.default_img);
        holder.setText(R.id.tv_car_number, item.getServicePlateNumber());//车牌号
        TextView tvUserName = holder.getView(R.id.tv_user_name);//用户名
        TextView tvTakeOrder = holder.getView(R.id.tv_take_order);//结算
        tvUserName.setText(item.getUser() == null ? "" : item.getUser().getNickName() == null ? "" : item.getUser().getNickName());
        TextView tvAwaitTimeOrMoney = holder.getView(R.id.tv_await_time_or_money);//结算金额
        String price = "总计：¥" + item.getTotalAmount();
        SpannableString spStr = new SpannableString(price);
        spStr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.text_comment_gray))
                , 3, spStr.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spStr.setSpan(new RelativeSizeSpan(1.2f), 4, spStr.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvAwaitTimeOrMoney.setText(spStr);
        tvTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnTakeOrderClickListener.onTakeOrderClick(item, position);
            }
        });

        //标签控件
        LabelFlowLayout labelFlowLayout = holder.getView(R.id.labelflow_service_item);
        labelFlowLayout.removeAllViews();
        LayoutParams source = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        MarginLayoutParams lp = new MarginLayoutParams(source);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 1;
        lp.bottomMargin = 1;
        for (int i = 0; i < 1; i++) {
            TextView view = new TextView(mContext);
            view.setText(item.getProjectName());
            view.setTextColor(Color.rgb(255, 151, 114));
//                view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 35);
            view.setBackgroundResource(R.drawable.shape_text_station_label_new);
            view.setLayoutParams(lp);
            AutoUtils.auto(view);
            labelFlowLayout.addView(view);
        }

    }

    /**
     * 等候时间
     *
     * @param createTimeStr
     */
    private String awaitTime(String createTimeStr) {
        long createTime = AppUtility.date2TimeStamp(createTimeStr, "yyyy-MM-dd HH:mm:ss");
        long currentTime = AppUtility.date2TimeStamp(AppUtility.getCurrentDateAndTime(), "yyyy-MM-dd HH:mm:ss");
        long awaitTime = currentTime - createTime;
        return String.format("%.1f", awaitTime / 1000.0 / 60 / 60);
    }

    public interface OnTakeOrderClickListener {
        void onTakeOrderClick(WorkOrderInfo workOrderInfo, int position);
    }

    OnTakeOrderClickListener OnTakeOrderClickListener;

    public void setOnTakeOrderClickListener(OnTakeOrderClickListener onTakeOrderClickListener) {
        OnTakeOrderClickListener = onTakeOrderClickListener;
    }
}
