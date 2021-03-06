package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 提成Adapter
 * Created by jl on 2017/4/14
 */
public class TiChenAdapter extends CommonAdapter<TiChengListModel> {

    public TiChenAdapter(Context context, int layoutId, List<TiChengListModel> datas) {
        super(context, layoutId, datas);
    }

    public void setDatas(List<TiChengListModel> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, TiChengListModel item, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvname = holder.getView(R.id.tv_name);
        TextView tvnumber = holder.getView(R.id.tv_car_number);
        TextView serviceproject = holder.getView(R.id.service_project);
        TextView tvtime = holder.getView(R.id.tv_await_time);
        TextView tvmoney = holder.getView(R.id.tv_await_money);
        String goodsName = item.getGoodsName();
        tvname.setText(goodsName);

        tvnumber.setText(item.getServicePlateNumber());
        serviceproject.setText(item.getProjectName());
        tvtime.setText(item.getCreateTime());
        tvmoney.setText("提成: ¥" + item.getCommissionAmount() + "");

//        LabelFlowLayout labelFlowLayout = holder.getView(R.id.service_project);
//        //标签控件
//        labelFlowLayout.removeAllViews();
//        ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(source);
//        lp.leftMargin = 0;
//        lp.rightMargin = 0;
//        lp.topMargin = 0;
//        lp.bottomMargin = 0;
//        for (int i = 0; i < 2; i++) {
//            TextView view = new TextView(mContext);
//            view.setText("机修");
//            view.setTextColor(Color.rgb(255, 151, 114));
////                view.setTextColor(Color.WHITE);
//            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
//            view.setBackgroundResource(R.drawable.shape_text_station_label_new);
//            view.setLayoutParams(lp);
////            AutoUtils.auto(view);
//            labelFlowLayout.addView(view);
//        }
    }
}
