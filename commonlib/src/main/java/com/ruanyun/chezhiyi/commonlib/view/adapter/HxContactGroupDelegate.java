package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description:环信二级展开列表--分组
 * author: jery on 2016/7/27 14:20.
 */
public class HxContactGroupDelegate implements ItemViewDelegate<HxUser> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_item_contact_group;
    }

    @Override
    public boolean isForViewType(HxUser item, int position) {
        return item.getTypeId()==HxUser.TYPE_GROUP;
    }

    @Override
    public void convert(ViewHolder holder, HxUser hxUser, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvGroupName=holder.getView(R.id.tv_group_name);
        tvGroupName.setText(hxUser.getGroupName());
        tvGroupName.setSelected(hxUser.isSelected());

      // holder.setText(R.id.tv_group_name,hxUser.getGroupName());
    }
}
