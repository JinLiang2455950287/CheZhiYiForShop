package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msq on 2016/9/22.
 */
public class CarCommentAdapter extends CommonAdapter<CarModel> {

    List<CarModel> list = new ArrayList<>();
    public CarCommentAdapter(Context context, int layoutId, List<CarModel> datas) {
        super(context, layoutId, datas);
        this.list = datas;
    }

    @Override
    protected void convert(ViewHolder holder, CarModel item, int position) {
        AutoUtils.auto(holder.getConvertView());
        View view = holder.getView(R.id.view);
        LinearLayout linearLayout_top = holder.getView(R.id.linearLayout_top);
        LinearLayout linearLayout_bottom = holder.getView(R.id.linearLayout_bottom);

        //判断当前ParentModelNum与上一个ParentModelNum是否相同
        if(position>0 && item.getParentModelNum().equals(getItem(position-1).getParentModelNum())){
            linearLayout_top.setVisibility(View.VISIBLE);
            linearLayout_bottom.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            holder.setBackgroundRes(R.id.linearLayout,R.color.white);
        }else{
            //判断当前ParentModelNum与上一个CarModelNum是否相同
            if(position>0 && item.getParentModelNum().equals(getItem(position-1).getCarModelNum())){
                linearLayout_top.setVisibility(View.VISIBLE);
                linearLayout_bottom.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                holder.setBackgroundRes(R.id.linearLayout,R.color.white);
            }else{
                linearLayout_top.setVisibility(View.GONE);
                linearLayout_bottom.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            }
        }

        //去掉最后一个view和本页一级和二级之间的view
        if(position <= list.size()-2 && !item.getParentModelNum().equals(getItem(position+1).getParentModelNum())){
            view.setVisibility(View.GONE);
        }
        if(position == list.size()-1){
            view.setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_vehicle, item.getCarModelAllName());
    }
}
