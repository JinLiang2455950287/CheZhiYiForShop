package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListPublicInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 提成Adapter
 * Created by jl on 2017/4/14
 */
public class TiChenPublicAdapter extends CommonAdapter<TiChengListPublicInfo.ResultBean> {

    public TiChenPublicAdapter(Context context, int layoutId, List<TiChengListPublicInfo.ResultBean> datas) {
        super(context, layoutId, datas);
    }

    public void setDatas(List<TiChengListPublicInfo.ResultBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, TiChengListPublicInfo.ResultBean item, int position) {
        AutoUtils.auto(holder.getConvertView());
        holder.setText(R.id.tv_time, item.getReportDate());
        holder.setText(R.id.tv_money, "¥" + item.getAmount());

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
