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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.LabelFlowLayout;
import com.ruanyun.chezhiyi.view.ui.home.WorkOrderFragment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：等候区/质检区
 * <p/>
 * Created by hdl on 2017/4/10
 */

public class WaitAreaAdapter extends CommonAdapter<WorkOrderInfo> {

    //    LabelFlowLayout labelFlowLayout;

    public WaitAreaAdapter(Context context, int layoutId, List<WorkOrderInfo> datas) {
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
        LogX.e("质检区", item.toString());
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(item.getCarPicPath()), (ImageView) holder.getView(R.id.iv_car_photo), R.drawable.default_img);
        holder.setText(R.id.tv_car_number, item.getServicePlateNumber());//车牌号
        TextView tvUserName = holder.getView(R.id.tv_user_name);//用户名
        TextView tvTakeOrder = holder.getView(R.id.tv_take_order);//结算
        tvUserName.setText(item.getUser() == null ? "" : item.getUser().getNickName() == null ? "" : item.getUser().getNickName());
        TextView tvAwaitTimeOrMoney = holder.getView(R.id.tv_await_time_or_money);//等候时长或结算金额
//        if (type.equals(WorkOrderFragment.TAB_TYPE_WAIT_AREA)) {//等候区
        String awaitTime = "已等候：" + awaitTime(item.getCreateTime()) + "H";
//            String awaitTime = "已等候："+awaitTime("2016-10-09 14:00:00")+"H";
        SpannableString spStr = new SpannableString(awaitTime);
        spStr.setSpan(new RelativeSizeSpan(1.3f), 4, awaitTime.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spStr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.theme_color_default))
                , 4, awaitTime.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvAwaitTimeOrMoney.setText(spStr);

        //标签控件
        LabelFlowLayout labelFlowLayout = holder.getView(R.id.labelflow_service_item);
        labelFlowLayout.removeAllViews();
        ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(source);
        lp.leftMargin = 1;
        lp.rightMargin = 1;
        lp.topMargin = 1;
        lp.bottomMargin = 1;
        for (int i = 0; i < 1; i++) {
            TextView view = new TextView(mContext);
            view.setText(item.getUser() == null ? "" : item.getProjectName());
            view.setTextColor(Color.rgb(255, 151, 114));
//                view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 33);
            view.setBackgroundResource(R.drawable.shape_text_station_label_new);
            view.setLayoutParams(lp);
//            AutoUtils.auto(view);
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
