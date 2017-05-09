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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MyExpandableListPaiGongAdapter extends BaseExpandableListAdapter {
    private List<WorkOrderInfo> groups;//
    public Map<String, List<OrderGoodsInfo>> childs;//
    private Context mContext;
    private int countTemp = 0;

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
        TextView tv_group_detail = (TextView) convertView.findViewById(R.id.tv_group_detail);
        tv_group_pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductBuyClickListener.onProductBuyItemClick(groups.get(groupPosition).getProjectNum(), groups.get(groupPosition).getIsWorkBbay());
            }
        });

        tv_group.setText(groups.get(groupPosition).getProjectName() + "");
        if (groups.get(groupPosition).getRemark() != null) {
            tv_group_detail.setText(groups.get(groupPosition).getRemark());
        }
        LogX.e("1544Adapter", groups.get(groupPosition).toString());
        return convertView;
    }

    //【重要】填充二级列表
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child, null);
        }

        TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);
        final TextView tv_child_detail = (TextView) convertView.findViewById(R.id.tv_child_price_detail);
        TextView tvchilddetailtv = (TextView) convertView.findViewById(R.id.tv_child_detailtv);
        CheckBox cb_service = (CheckBox) convertView.findViewById(R.id.cb_service);
        final TextView tv_child_detail_count = (TextView) convertView.findViewById(R.id.tv_child_detail_count);
        ImageButton imbtnSub = (ImageButton) convertView.findViewById(R.id.imbtn_sub);
        ImageButton imbtnadd = (ImageButton) convertView.findViewById(R.id.imbtn_add);

        tv_child.setText(childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsName());

        int goodsCount = childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount();
        LinearLayout countlinearLayout = (LinearLayout) convertView.findViewById(R.id.count_linearLayout);

        View xuxian = (View) convertView.findViewById(R.id.view_xuxian);
        View shixian = (View) convertView.findViewById(R.id.view_shixian);
        if (childPosition != childs.get(groups.get(groupPosition).getProjectNum()).size() - 1) {
            xuxian.setVisibility(View.VISIBLE);
            shixian.setVisibility(View.GONE);
        } else {
            xuxian.setVisibility(View.GONE);
            shixian.setVisibility(View.VISIBLE);
        }

        LogX.e("开单", childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getIsDaiXiaDan() + "");
        if (childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getIsDaiXiaDan() != 1) {
            countlinearLayout.setVisibility(View.GONE);
            tvchilddetailtv.setVisibility(View.VISIBLE);
            tv_child_detail.setVisibility(View.GONE);
            if (goodsCount == 0) {
                tvchilddetailtv.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×1");
            } else {
                tvchilddetailtv.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×1" + goodsCount);
            }

        } else {
            countlinearLayout.setVisibility(View.VISIBLE);
            tvchilddetailtv.setVisibility(View.GONE);
            tv_child_detail.setVisibility(View.VISIBLE);
        }

        cb_service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setService(isChecked);
                LogX.e("是否选择", childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).isService() + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).toString());
            }
        });

        if (goodsCount == 0) {
            tv_child_detail.setText("¥ 0");
            tv_child_detail_count.setText(goodsCount + "");
        } else {
            tv_child_detail.setText("¥ " + (childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount()).multiply(new BigDecimal(goodsCount)));
            tv_child_detail_count.setText(goodsCount + "");
        }

        countTemp = Integer.valueOf(tv_child_detail_count.getText().toString().trim());

        imbtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countTemp = Integer.valueOf(tv_child_detail_count.getText().toString().trim());
                if (Integer.valueOf(countTemp) > 1) {
                    countTemp = countTemp - 1;
                    onBuyCountClickListener.onBuyCountItemClick(countTemp);
                    childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setGoodsCount(countTemp);
                    LogX.e("数量减；", countTemp + ";" + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount());
                    notifyDataSetChanged();
                } else {
                    childs.get(groups.get(groupPosition).getProjectNum()).remove(childPosition);
                    notifyDataSetChanged();
                }
            }
        });
        imbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countTemp = Integer.valueOf(tv_child_detail_count.getText().toString().trim());
                countTemp = countTemp + 1;
                onBuyCountClickListener.onBuyCountItemClick(countTemp);
                childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setGoodsCount(countTemp);
                LogX.e("数量加；", countTemp + ";" + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount());
                notifyDataSetChanged();
            }
        });

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
        void onProductBuyItemClick(String project, int isWorkBbay);
    }

    public void setPaiGongClickListener(OnPaiGongClickListener onProductBuyClickListener) {
        this.onProductBuyClickListener = onProductBuyClickListener;
    }

    // 商品数量click callback
    OnBuyCountClickListener onBuyCountClickListener;

    public interface OnBuyCountClickListener {
        void onBuyCountItemClick(int count);
    }

    public void setOnBuyCountClickListener(OnBuyCountClickListener onBuyCountClickListener) {
        this.onBuyCountClickListener = onBuyCountClickListener;
    }

}