package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：服务类型
 * <p>
 * Created by ycw on 2016/9/13.
 */
public class ServerAdapter extends MultiItemTypeAdapter<ProjectType> {


    public ServerAdapter(Context context, List<ProjectType> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<ProjectType>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_server_parent_type;
            }

            @Override
            public boolean isForViewType(ProjectType item, int position) {
                return item.isParent();
            }

            @Override
            public void convert(ViewHolder holder, ProjectType projectType, int position) {
                AutoUtils.auto(holder.getConvertView());
                TextView tvTypeName = holder.getView(R.id.tv_type_name);
                tvTypeName.setText(projectType.getProjectName());

                // 已选的服务小项
                TextView tvPrice = holder.getView(R.id.tv_price);
                if (AppUtility.isNotEmpty(projectType.getProjectAllSelectName())) {
                    tvPrice.setVisibility(View.VISIBLE);
                    tvPrice.setText(new StringBuilder().append("(").append(projectType
                            .getProjectAllSelectName()).append(")").toString());
                } else {
                    tvPrice.setVisibility(View.GONE);
                    tvPrice.setText("");
                }

                boolean selected = projectType.isSelected();
                //                上边线
//                holder.setVisible(R.id.top_view_line, selected);
                ImageView imageView = holder.getView(R.id.iv_type_name);
                imageView.setSelected(selected);
            }
        });

        addItemViewDelegate(new ItemViewDelegate<ProjectType>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_server_chid_type;
            }

            @Override
            public boolean isForViewType(ProjectType item, int position) {
                return !item.isParent();
            }

            @Override
            public void convert(ViewHolder holder, ProjectType projectType, int position) {
                AutoUtils.auto(holder.getConvertView());
                TextView tvServerSubType = holder.getView(R.id.tv_server_sub_type);
                tvServerSubType.setText(projectType.getProjectName());
                tvServerSubType.setSelected(projectType.isSelected());
            }
        });
    }

    public int getItemPosition(ProjectType projectType) {
        int position = mDatas.indexOf(projectType);
        return position == -1? 0: position;
    }

    public void notifyItem(ProjectType projectType) {
        notifyItemChanged( getItemPosition(projectType) );
    }
}
