package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeImageView;

/**
 * Description:  系统消息的
 * author: zhangsan on 16/10/27 上午10:12.
 */
public class SystemRemindAdapter extends CommonAdapter<RemindInfo> {

    public SystemRemindAdapter(Context context, int layoutId, List<RemindInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, RemindInfo remindInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        holder.setText(R.id.tv_title,remindInfo.getRemindTitle());
        holder.setText(R.id.tv_content,remindInfo.getRemindContent());
        holder.setText(R.id.tv_time,remindInfo.getCreateTimeFormat());
        BGABadgeImageView badgeImageView = holder.getView(R.id.img_msg_type);
        badgeImageView.setImageResource(remindInfo.getResIdByType());
        if (remindInfo.getIsRead() == RemindInfo.READ_NO) {
            badgeImageView.showCirclePointBadge();
        } else {
            badgeImageView.hiddenBadge();
        }
    }

    public void setData(List<RemindInfo> remindInfos){
      this.mDatas=remindInfos;
      notifyDataSetChanged();
    }

    public void addData(List<RemindInfo> remindInfos){
        mDatas.addAll(remindInfos);
        notifyDataSetChanged();
    }
}
