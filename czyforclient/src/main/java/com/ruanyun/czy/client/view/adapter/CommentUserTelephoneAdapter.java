package com.ruanyun.czy.client.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.model.CommentUserTelephoneInfo;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;


/**
 * Created by msq on 2016/9/19.
 */
public class CommentUserTelephoneAdapter extends CommonAdapter<CommentUserTelephoneInfo>{

    public CommentUserTelephoneAdapter(Context context, int layoutId, List<CommentUserTelephoneInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CommentUserTelephoneInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());

        holder.setText(R.id.tv_company_name,item.getTitle());
        holder.setText(R.id.tv_telephone_number,item.getContent());
    }

    public void setData(List<CommentUserTelephoneInfo> commentUserTelephoneInfo){
        this.mDatas = commentUserTelephoneInfo;
        notifyDataSetChanged();
    }

    public void addData(List<CommentUserTelephoneInfo> commentUserTelephoneInfo){
        this.mDatas.addAll(commentUserTelephoneInfo);
        notifyDataSetChanged();
    }
}
