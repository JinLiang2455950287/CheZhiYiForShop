package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description:
 * author: zhangsan on 16/8/10 下午4:24.
 */
public class ClientGroupDelegate implements ItemViewDelegate<HxUser> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.ease_list_item_name_letter;
    }

    @Override
    public boolean isForViewType(HxUser item, int position) {
        return item.getTypeId() == HxUser.TYPE_GROUP;
    }

    @Override
    public void convert(ViewHolder holder, HxUser hxUser, int position) {
        AutoUtils.auto(holder.getConvertView());
        holder.setText(R.id.tv_name_letter, hxUser.getGroupName());
    }
}
