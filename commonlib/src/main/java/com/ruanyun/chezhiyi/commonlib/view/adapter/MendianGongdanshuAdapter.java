package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.TuiKuanInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.widget.LabelFlowLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 工单数Adapter
 * Created by jl on 2017/4/13
 */
public class MendianGongdanshuAdapter extends CommonAdapter<String> {

    public MendianGongdanshuAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    public void setDatas(List<String> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, String item, int position) {
        AutoUtils.auto(holder.getConvertView());

        LabelFlowLayout labelFlowLayout = holder.getView(R.id.service_project);


        //标签控件
        labelFlowLayout.removeAllViews();
        ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(source);
        lp.leftMargin = 0;
        lp.rightMargin = 0;
        lp.topMargin = 0;
        lp.bottomMargin = 0;
        for (int i = 0; i < 2; i++) {
            TextView view = new TextView(mContext);
            view.setText("机修");
            view.setTextColor(Color.rgb(255, 151, 114));
//                view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
            view.setBackgroundResource(R.drawable.shape_text_station_label_new);
            view.setLayoutParams(lp);
//            AutoUtils.auto(view);
            labelFlowLayout.addView(view);
        }
    }
}
