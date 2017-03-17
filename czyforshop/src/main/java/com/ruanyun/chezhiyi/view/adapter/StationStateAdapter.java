package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.widget.LabelFlowLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description ：工位状态信息
 * <p/>
 * Created by hdl on 2016/9/27.
 */

public class StationStateAdapter extends CommonAdapter<WorkBayInfo> {
    //    Drawable drawable;
    private Map<String, String> projectNameList = new HashMap<>();

    private String getProjectName(String projectNum) {
        String projectName = projectNameList.get(projectNum);
        if (TextUtils.isEmpty(projectName)) {
            projectName = DbHelper.getInstance().getParentSeviceTypeName(projectNum);
            projectNameList.put(projectNum, projectName);
        }
        return projectName;
    }

    public StationStateAdapter(Context context, int layoutId, List<WorkBayInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setDatas(List<WorkBayInfo> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(ViewHolder holder, WorkBayInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvStationName = holder.getView(R.id.tv_station_name);
        TextView tvStationState = holder.getView(R.id.tv_station_state);
        tvStationName.setText(item.getWorkbayName());
        TextView tvServiceItem = holder.getView(R.id.tv_service_item);
        TextView tvCarNumber = holder.getView(R.id.tv_car_number);
        TextView textTechnician = holder.getView(R.id.text_technician);
        TextView tvLeisureState = holder.getView(R.id.tv_leisure_state);//空闲时显示
        LabelFlowLayout labelFlowLayout = holder.getView(R.id.labelflow_technician);


        //        drawable = ContextCompat.getDrawable(mContext, R.drawable.check_station_busy);
        //        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvStationName.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.check_station_busy), null, null, null);
        if (item.getWorkbayStatus() != WorkBayInfo.LEISURE) {//施工中
            tvServiceItem.setVisibility(View.VISIBLE);
            tvCarNumber.setVisibility(View.VISIBLE);
            textTechnician.setVisibility(View.VISIBLE);
            labelFlowLayout.setVisibility(View.VISIBLE);
            tvLeisureState.setVisibility(View.GONE);
            tvStationState.setText("施工中");
            tvStationState.setTextColor(Color.rgb(243, 151, 26));
            if (item.getProjectNum() != null && !TextUtils.isEmpty(item.getProjectNum())) tvServiceItem.setText("【" + item.getProjectName() + "】");
            else tvServiceItem.setText("【其他】");
            if ("".equals(item.getServicePlateNumber())) {
                tvCarNumber.setText("未上牌");
            } else tvCarNumber.setText(item.getServicePlateNumber());
            textTechnician.setText("【技师】");
            //标签控件
            labelFlowLayout.removeAllViews();
            ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(source);
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            lp.topMargin = 5;
            lp.bottomMargin = 5;
            for (int i = 0; i < 1; i++) {
                TextView view = new TextView(mContext);
                view.setText(item.getUser() == null ? "" : item.getUser().getNickName());
                view.setTextColor(Color.WHITE);
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                view.setBackgroundResource(R.drawable.shape_text_station_label);
                view.setLayoutParams(lp);
                AutoUtils.auto(view);
                labelFlowLayout.addView(view);
            }
        } else {//空闲中
            //            drawable = ContextCompat.getDrawable(mContext, R.drawable.check_station_free);
            //            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvStationName.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.check_station_free), null, null, null);
            tvStationState.setText("空闲中");
            tvStationState.setTextColor(Color.rgb(48, 178, 108));
            tvLeisureState.setVisibility(View.VISIBLE);
            tvServiceItem.setVisibility(View.GONE);
            tvCarNumber.setVisibility(View.GONE);
            textTechnician.setVisibility(View.GONE);
            labelFlowLayout.setVisibility(View.GONE);
        }

    }
}
