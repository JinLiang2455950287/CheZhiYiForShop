package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.IconInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Sxq on 2016/9/29.
 */
public class ShareIconAdapter extends CommonAdapter<IconInfo> {
    public ShareIconAdapter(Context context, int layoutId, List<IconInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, IconInfo item, int position) {
        AutoUtils.auto(viewHolder.getConvertView());
        TextView tvTitle=viewHolder.getView(R.id.tv_title);
        tvTitle.setText(item.getTitle());

        ImageView ivIcon=viewHolder.getView(R.id.iv_icon);
        ivIcon.setImageResource(item.getIcon());
    }
}
