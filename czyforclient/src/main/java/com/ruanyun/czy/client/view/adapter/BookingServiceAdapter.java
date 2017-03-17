package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：预约详情界面
 * <p/>
 * Created by hdl on 2016/9/19.
 */
public class BookingServiceAdapter extends MultiItemTypeAdapter<WorkOrderInfo> {

    public BookingServiceAdapter(Context context, List<WorkOrderInfo> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<WorkOrderInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return com.ruanyun.chezhiyi.commonlib.R.layout.item_server_parent_type;
            }

            @Override
            public boolean isForViewType(WorkOrderInfo item, int position) {
                return item.isParent();
            }

            @Override
            public void convert(ViewHolder holder, WorkOrderInfo item, int position) {
                AutoUtils.auto(holder.getConvertView());
                TextView tvTypeName = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_type_name);
                tvTypeName.setPadding(25,0,0,0);
                TextView tvPrice = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_price);
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setTextSize(12);
                tvPrice.setTextColor(Color.rgb(170,170,170));
                String str="(订金：<font color='#FBBB53'>¥"+item.getDownPayment()+"</font>)";
                tvPrice.setText(Html.fromHtml(str));
                tvTypeName.setText(item.getProjectName());
                if (item.isSelected()) {
                    holder.getView(R.id.iv_type_name).setSelected(true);
                }else holder.getView(R.id.iv_type_name).setSelected(false);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<WorkOrderInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return com.ruanyun.chezhiyi.commonlib.R.layout.item_server_chid_type;
            }

            @Override
            public boolean isForViewType(WorkOrderInfo item, int position) {
                return !item.isParent();
            }

            @Override
            public void convert(ViewHolder holder, WorkOrderInfo projectType, int position) {
                AutoUtils.auto(holder.getConvertView());
                TextView tvServerSubType = holder.getView(com.ruanyun.chezhiyi.commonlib.R.id.tv_server_sub_type);
                tvServerSubType.setText(projectType.getProjectName());
                tvServerSubType.setSelected(true);
            }
        });


    }
    public void setDatas(List<WorkOrderInfo> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }
}
