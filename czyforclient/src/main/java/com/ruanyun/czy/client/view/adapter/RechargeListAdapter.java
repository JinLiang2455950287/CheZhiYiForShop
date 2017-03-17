package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.RechargeListInfo;

import com.ruanyun.czy.client.R;
import com.ruanyun.imagepicker.base.CommonViewHolder;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by Sxq on 2016/10/10.
 */
public class RechargeListAdapter extends CommonAdapter<RechargeListInfo> {
    public RechargeListAdapter(Context context, List<RechargeListInfo> datas, int layoutId) {
        super(context,layoutId, datas );
    }

    @Override
    protected void convert(ViewHolder viewHolder, RechargeListInfo rechargeListInfo, int position) {
        TextView tvScore=viewHolder.getView(R.id.tv_score);//赠送积分
        tvScore.setText("赠"+String.valueOf(rechargeListInfo.getScore())+"积分");

        TextView tvAccount=viewHolder.getView(R.id.tv_amount);//充值金额
        tvAccount.setText(rechargeListInfo.getAmount().toString()+"元");
    }

    public void setData(List<RechargeListInfo> rechargeListInfos) {
        this.mDatas = rechargeListInfos;
    }

    public void addData(List<RechargeListInfo> rechargeListInfos) {
        this.mDatas.addAll(rechargeListInfos);
    }
}
