package com.ruanyun.chezhiyi.view.adapter;

/**
 * Created by czy on 2017/4/27.
 * 开单
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

import java.util.List;
import java.util.Map;

public class MyExpandableListPaiGongAdapter extends BaseExpandableListAdapter {
    private List<WorkOrderInfo> groups;//
    public Map<String, List<OrderGoodsInfo>> childs;//
    private Context mContext;

    public MyExpandableListPaiGongAdapter(Context mContext, List<WorkOrderInfo> groups, Map<String, List<OrderGoodsInfo>> childs) {
        this.groups = groups;
        this.childs = childs;
        this.mContext = mContext;
    }

    public void setData(List<WorkOrderInfo> groupsList, Map<String, List<OrderGoodsInfo>> childsList) {
        groups = groupsList;
        childs = childsList;
    }

    //返回一级列表的个数
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    //返回每个二级列表的个数 List<Map<String,List<ProductInfo>>>
    @Override
    public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
        return childs.get(groups.get(groupPosition).getProjectNum()).size();
    }

    //返回一级列表的单个item（返回的是对象）
    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    //返回二级列表中的单个item（返回的是对象）List<Map<String,List<ProductInfo>>>
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {   //List<Map<String,List<ProductInfo>>>
        return childPosition;
    }

    //每个item的id是否是固定？一般为true
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //【重要】填充一级列表
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
        } else {

        }
        TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group_title);
        TextView tv_group_pai = (TextView) convertView.findViewById(R.id.tv_group_pai);
        tv_group_pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductBuyClickListener.onProductBuyItemClick(groups.get(groupPosition).getProjectNum());
            }
        });

        tv_group.setText(groups.get(groupPosition).getProjectName() + "");

        return convertView;
    }

    //【重要】填充二级列表
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child, null);
        }

        TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);
        TextView tv_child_detail = (TextView) convertView.findViewById(R.id.tv_child_detail);
        tv_child.setText(childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsName());
        int goodsCount = childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount();
//        LogX.e("152数量", goodsCount + "erw");
        if (goodsCount == 0) {
            tv_child_detail.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×1");
        } else {
            tv_child_detail.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×" + goodsCount);
        }
        return convertView;
    }

    //二级列表中的item是否能够被选中？可以改为true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    // click callback
    OnPaiGongClickListener onProductBuyClickListener;

    public interface OnPaiGongClickListener {
        void onProductBuyItemClick(String project);

    }

    public void setPaiGongClickListener(OnPaiGongClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }

}