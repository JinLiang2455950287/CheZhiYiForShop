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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MyExpandableListKaiDanAdapter extends BaseExpandableListAdapter {
    private List<WorkOrderInfo> groups;
    public Map<String, List<OrderGoodsInfo>> childs;
    private Context mContext;
    private int countTemp = 0;
    private boolean isSelect;

    public MyExpandableListKaiDanAdapter(Context mContext, List<WorkOrderInfo> groups, Map<String, List<OrderGoodsInfo>> childs) {
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
        GroupViewHolder gHolder = null;
        //如果convertView对象为空则创建新对象，不为空则复用
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
            // 创建 ViewHodler 对象
            gHolder = new GroupViewHolder();
            gHolder.tv_group = (TextView) convertView.findViewById(R.id.tv_group_title);
            gHolder.tv_group_pai = (TextView) convertView.findViewById(R.id.tv_group_pai);
            gHolder.tv_group_detail = (TextView) convertView.findViewById(R.id.tv_group_detail);
            convertView.setTag(gHolder);
        } else {
            gHolder = (GroupViewHolder) convertView.getTag();
        }

        gHolder.tv_group_pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProductBuyClickListener.onProductBuyItemClick(groups.get(groupPosition).getProjectNum(), groups.get(groupPosition).getIsWorkBbay());
            }
        });

        gHolder.tv_group.setText(groups.get(groupPosition).getProjectName() + "");
        if (groups.get(groupPosition).getRemark() != null) {
            gHolder.tv_group_detail.setText(groups.get(groupPosition).getRemark());
        }
//        LogX.e("expandlegroup", groups.get(groupPosition).toString());
        return convertView;
    }

    //【重要】填充二级列表
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LogX.e("expandlegroupChild", groupPosition + ";" + childPosition + ";;;;" + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).toString());
        final ChildViewHolder vHolder;
        //如果convertView对象为空则创建新对象，不为空则复用
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_child, null);
            // 创建 ViewHodler 对象
            vHolder = new ChildViewHolder();
            vHolder.tv_child = (TextView) convertView.findViewById(R.id.tv_child);
            vHolder.tv_child_detail = (TextView) convertView.findViewById(R.id.tv_child_price_detail);
            vHolder.tvchilddetailtv = (TextView) convertView.findViewById(R.id.tv_child_detailtv);
            vHolder.cb_service = (ImageView) convertView.findViewById(R.id.cb_service);
            vHolder.tv_child_detail_count = (TextView) convertView.findViewById(R.id.tv_child_detail_count);
            vHolder.imbtnSub = (ImageButton) convertView.findViewById(R.id.imbtn_sub);
            vHolder.imbtnadd = (ImageButton) convertView.findViewById(R.id.imbtn_add);
            vHolder.countlinearLayout = (LinearLayout) convertView.findViewById(R.id.count_linearLayout);
            vHolder.xuxian = (View) convertView.findViewById(R.id.view_xuxian);
            vHolder.shixian = (View) convertView.findViewById(R.id.view_shixian);
            // 将ViewHodler保存到Tag中(Tag可以接收Object类型对象，所以任何东西都可以保存在其中)
            convertView.setTag(vHolder);
        } else {
            //当convertView不为空时，通过getTag()得到View
            vHolder = (ChildViewHolder) convertView.getTag();
        }

        int goodsCount = childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount();
        vHolder.tv_child.setText(childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsName());

        if (isLastChild) {
            vHolder.xuxian.setVisibility(View.GONE);
            vHolder.shixian.setVisibility(View.VISIBLE);
        } else {
            vHolder.xuxian.setVisibility(View.VISIBLE);
            vHolder.shixian.setVisibility(View.GONE);
        }

//        LogX.e("expandlechild", childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getIsDaiXiaDan() + "");
        if (childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getIsDaiXiaDan() != 1) {
            vHolder.countlinearLayout.setVisibility(View.GONE);
            vHolder.tvchilddetailtv.setVisibility(View.VISIBLE);
            vHolder.tv_child_detail.setVisibility(View.GONE);
            if (goodsCount == 0) {
                vHolder.tvchilddetailtv.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×1");
            } else {
                vHolder.tvchilddetailtv.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×" + goodsCount);
            }

        } else {
            vHolder.countlinearLayout.setVisibility(View.VISIBLE);
            vHolder.tvchilddetailtv.setVisibility(View.GONE);
            vHolder.tv_child_detail.setVisibility(View.VISIBLE);
        }

        if (childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).isService()) {
            vHolder.cb_service.setImageResource(R.drawable.checkbox_pressed);
        } else {
            vHolder.cb_service.setImageResource(R.drawable.checkbox_normal);
        }

        vHolder.cb_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelect = childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).isService();
                if (isSelect) {
                    childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setService(false);
                    vHolder.cb_service.setImageResource(R.drawable.checkbox_normal);
                } else {
                    childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setService(true);
                    vHolder.cb_service.setImageResource(R.drawable.checkbox_pressed);
                }

                LogX.e("expandlechild是否=选择", groupPosition + ";" + childPosition + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).isService() + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).toString());
            }
        });

        if (goodsCount == 0) {
            vHolder.tv_child_detail.setText("¥ 0");
            vHolder.tv_child_detail_count.setText(goodsCount + "");
        } else {
            vHolder.tv_child_detail.setText("¥ " + (childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount()).multiply(new BigDecimal(goodsCount)));
            vHolder.tv_child_detail_count.setText(goodsCount + "");
        }

        countTemp = Integer.valueOf(vHolder.tv_child_detail_count.getText().toString().trim());

        vHolder.imbtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countTemp = Integer.valueOf(vHolder.tv_child_detail_count.getText().toString().trim());
                if (Integer.valueOf(countTemp) > 1) {
                    countTemp = countTemp - 1;
                    onBuyCountClickListener.onBuyCountItemClick(countTemp);
                    childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setGoodsCount(countTemp);
//                    LogX.e("expandlechild数量减；", countTemp + ";" + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount());
                    notifyDataSetChanged();
                } else {
                    childs.get(groups.get(groupPosition).getProjectNum()).remove(childPosition);
                    if (childs.get(groups.get(groupPosition).getProjectNum()).size() <= 0) {
                        groups.remove(groupPosition);
                    }
                    notifyDataSetChanged();
                }
            }
        });
        vHolder.imbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countTemp = Integer.valueOf(vHolder.tv_child_detail_count.getText().toString().trim());
                countTemp = countTemp + 1;
                onBuyCountClickListener.onBuyCountItemClick(countTemp);
                childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).setGoodsCount(countTemp);
//                LogX.e("expandlechild数量加；", countTemp + ";" + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsCount());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    //二级列表中的item是否能够被选中？可以改为true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView tv_group;
        TextView tv_group_pai;
        TextView tv_group_detail;
    }

    static class ChildViewHolder {
        TextView tv_child;
        TextView tv_child_detail;
        TextView tvchilddetailtv;
        ImageView cb_service;
        TextView tv_child_detail_count;
        ImageButton imbtnSub;
        ImageButton imbtnadd;
        LinearLayout countlinearLayout;
        View xuxian;
        View shixian;
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