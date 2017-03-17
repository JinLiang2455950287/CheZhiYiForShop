package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.model.RecordListInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by wp on 2016/10/13.
 */
public class IntegralExchangeListAdapter extends CommonAdapter<RecordListInfo> {
    private int type;

    public IntegralExchangeListAdapter(Context context, List<RecordListInfo> datas, int layoutId, int types) {
        super(context, layoutId, datas);
        this.type=types;
    }
//    public IntegralExchangeListAdapter(Context context, List<RecordListInfo> datas, int layoutId,int types) {
//        super(context, datas, layoutId);
//        this.type=types;
//    }
//
//    @Override
//    public void convert(CommonViewHolder holder, RecordListInfo recordListInfo) {
//        TextView tvTitle = holder.getView(R.id.tv_title);//
//        tvTitle.setText(DbHelper.getInstance().getParentName(String.valueOf(recordListInfo.getRecordType()), C.ParentCode.ACCOUNT_RECORD_TYPE));
//        TextView tvTime = holder.getView(R.id.tv_time);//时间
//        tvTime.setText(StringUtil.getArtileTime(recordListInfo.getCreateTime()));
//        TextView tvResidualIntegral = holder.getView(R.id.tv_residual_integral);//剩余积分
//
//        TextView tvAccount = holder.getView(R.id.tv_amount);//
//
//
//        if (type == 1) {
//            tvAccount.setText("¥" + recordListInfo.getRecordAmount());
//            tvResidualIntegral.setText("余额：¥" + recordListInfo.getAmountBalance());
//        } else {
//            tvAccount.setText(String.valueOf(recordListInfo.getRecordAmount()));
//            tvResidualIntegral.setText("剩余积分：" + recordListInfo.getAmountBalance());
//        }
//
//
//    }

    @Override
    protected void convert(ViewHolder holder, RecordListInfo recordListInfo, int position) {
        TextView tvTitle = holder.getView(R.id.tv_title);//
        tvTitle.setText(DbHelper.getInstance().getParentName(String.valueOf(recordListInfo.getRecordType()), C.ParentCode.ACCOUNT_RECORD_TYPE));
        TextView tvTime = holder.getView(R.id.tv_time);//时间
        tvTime.setText(StringUtil.getArtileTime(recordListInfo.getCreateTime()));
        TextView tvResidualIntegral = holder.getView(R.id.tv_residual_integral);//剩余积分

        TextView tvAccount = holder.getView(R.id.tv_amount);//


//        if (type == 1) {
//            tvAccount.setText("¥" + recordListInfo.getRecordAmount());
//            tvResidualIntegral.setText("余额：¥" + recordListInfo.getAmountBalance());
//        } else {
            tvAccount.setText(String.valueOf(recordListInfo.getRecordAmount()));
            tvResidualIntegral.setText(/*"剩余积分："*/getAccountTypeName(recordListInfo.getAccountType()) + recordListInfo.getAmountBalance());
//        }

    }


    private String getAccountTypeName(int accountType) {
        String accountTypeName = DbHelper.getInstance().getParentName(String.valueOf(accountType), C.ParentCode.ACCOUNT_TYPE);
        if (TextUtils.isEmpty(accountTypeName)) return "";
        if (type == 1) {
            accountTypeName += ": ¥";
        } else {
            accountTypeName += ":";
        }
        return accountTypeName;
    }

    public void setData(List<RecordListInfo> result) {
        this.mDatas = result;
        notifyDataSetChanged();
    }

    public void addData(List<RecordListInfo> result) {
        this.mDatas.addAll(result);
        notifyDataSetChanged();
    }
}
