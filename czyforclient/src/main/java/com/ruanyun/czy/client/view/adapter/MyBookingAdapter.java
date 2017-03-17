package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

/**
 * Description ：我的预约界面Adapter
 * <p/>
 * Created by hdl on 2016/9/18.
 */
public class MyBookingAdapter extends CommonAdapter<AppointmentInfo> {

    public MyBookingAdapter(Context context, int layoutId, List<AppointmentInfo> datas) {
        super(context, layoutId, datas);
    }
    public void setDatas(List<AppointmentInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
    public static final int CANCEL_BOOKING = 1;//取消预约
    public static final int PAY_DEPOSIT = 2;//支付订金
    public static final int BOOKING_ONCE = 3;//再约一次

    @Override
    protected void convert(ViewHolder holder, final AppointmentInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        holder.setText(R.id.tv_order_number, "预约编号："+item.getMakeNum());
        Type type = new TypeToken<List<ProjectType>>() {}.getType();
        Gson gson = new Gson();
        List<ProjectType> projectTypes = gson.fromJson(item.getProjectNum(), type);
        holder.setText(R.id.tv_booking_project, getProjectName(projectTypes));
        String predictShopTime = item.getPredictShopTime();
        if(!"".equals(predictShopTime)) {
            predictShopTime = predictShopTime.substring(5, 7) + "月" + predictShopTime.substring(8, 10) + "日" + predictShopTime.substring(10);
        }
        holder.setText(R.id.tv_arrive_time, predictShopTime);

        TextView tvState = holder.getView(R.id.tv_state);
        TextView textMoney = holder.getView(R.id.text_money);
        TextView tvMoney = holder.getView(R.id.tv_money);
        TextView tvCancelAmount = holder.getView(R.id.tv_cancel_amount);
        TextView tvpayment = holder.getView(R.id.tv_payment);
        holder.getView(R.id.ll_money).setVisibility(View.VISIBLE);//显示金额
        tvMoney.setVisibility(View.VISIBLE);
        tvCancelAmount.setVisibility(View.VISIBLE);
        tvpayment.setVisibility(View.VISIBLE);
        tvMoney.setText("¥"+item.getDownPayment().setScale(2, BigDecimal.ROUND_HALF_UP));//订金金额

        if(item.getMakeStatus() == AppointmentInfo.SWAIT_AFFIRM){       //待确认时状态
            tvState.setText("待确认");
            textMoney.setText("合计订金：");
            tvCancelAmount.setText("取消预约");
            tvpayment.setVisibility(View.GONE);
//            tvpayment.setText("支付订金");
//            tvpayment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onMyBookingClickListener.onMyBookingClickListener(item,PAY_DEPOSIT);//支付订金
//                }
//            });
        }else if(item.getMakeStatus() == AppointmentInfo.ALREADY_AFFIRM){//待付款时状态
            tvState.setText("待付款");
            textMoney.setText("合计订金：");
            tvCancelAmount.setText("取消预约");
            if (item.getDownPayment().doubleValue() == 0){
                tvpayment.setVisibility(View.GONE);
            }
            tvpayment.setText("支付订金");
            tvpayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMyBookingClickListener.onMyBookingClickListener(item,PAY_DEPOSIT);//支付订金
                }
            });
        }else if(item.getMakeStatus() == AppointmentInfo.SWAIT_SERVICE){//待服务时状态
            tvState.setText("待服务");
            if (item.getDownPayment().doubleValue() > 0) {
                textMoney.setText("已付订金：");
                holder.getView(R.id.ll_money).setVisibility(View.VISIBLE);//不显示金额
                tvMoney.setVisibility(View.VISIBLE);
            } else {
                textMoney.setText("已付订金：");
                holder.getView(R.id.ll_money).setVisibility(View.GONE);//不显示金额
                tvMoney.setVisibility(View.GONE);
            }
            tvCancelAmount.setText("取消预约");
            tvpayment.setVisibility(View.GONE);

            //  待服务时状态   判断是否是已过期的预约
            String predictTime = item.getPredictShopTime();
            if (StringUtil.getDateCompare(predictTime) < 0) { // 预约时间小于当前时间
                tvState.setText("已过期");
            }

        }else if(item.getMakeStatus() == AppointmentInfo.ACCOMPLISH){
            tvState.setText("已完成");
            textMoney.setText("已付订金：");
            tvCancelAmount.setVisibility(View.GONE);
            tvpayment.setText("再约一次");
            tvpayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMyBookingClickListener.onMyBookingClickListener(item,BOOKING_ONCE);//再约一次
                }
            });
        }else {
            tvState.setText("已取消");
            textMoney.setText("合计订金：");
            tvCancelAmount.setVisibility(View.GONE);
            tvpayment.setText("再约一次");
            tvpayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMyBookingClickListener.onMyBookingClickListener(item,BOOKING_ONCE);//再约一次
                }
            });
        }

        tvCancelAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyBookingClickListener.onMyBookingClickListener(item,CANCEL_BOOKING);//取消预约
            }
        });

    }

    private String getProjectName(List<ProjectType> projectTypes) {
        StringBuilder builder = new StringBuilder();
        if (projectTypes == null || projectTypes.size() == 0) return builder.toString();
        for (int i = 0; i < projectTypes.size(); i++) {
            List<ProjectType> childProjectTypeList = projectTypes.get(i).getChildProjectTypeList();
            for (int j = 0; j < childProjectTypeList.size(); j++) {
                builder = builder.append(childProjectTypeList.get(j).getProjectName());
                if ( !(i == projectTypes.size() -1 && j == childProjectTypeList.size() -1) ) {
                    builder = builder.append("、");
                }
            }
        }
        return builder.toString();
    }

    public void refresh(List<AppointmentInfo> datas){
        this.mDatas=datas;
        notifyDataSetChanged();

    }

    public void loadMore(List<AppointmentInfo> datas){
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public interface OnMyBookingClickListener {
        void onMyBookingClickListener(AppointmentInfo appointmentInfo,int type);
    }
    // click callback
    OnMyBookingClickListener onMyBookingClickListener;

    public void setMyBookingClickListener(OnMyBookingClickListener onMyBookingClickListener) {
        this.onMyBookingClickListener = onMyBookingClickListener;
    }
}
