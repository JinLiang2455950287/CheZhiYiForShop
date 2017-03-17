package com.ruanyun.czy.client.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.CommonAdapter;
import com.ruanyun.chezhiyi.commonlib.model.RefundInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：退款的数据
 * <p/>
 * Created by ycw on 2016/9/23.
 */
public class RefundAdapter extends CommonAdapter<RefundInfo> {

    SpannableStringBuilder builder = new SpannableStringBuilder();

    public RefundAdapter(Context context, int layoutId, List<RefundInfo> datas) {
        super(context, layoutId, datas);
    }

    public void setDatas(List<RefundInfo> refundInfos) {
        this.mDatas = refundInfos;
    }

    @Override
    protected void convert(ViewHolder holder, RefundInfo refundInfo, int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvMsg = holder.getView(R.id.tv_msg);
        tvMsg.setText(getStr(refundInfo));
    }

    private SpannableStringBuilder getStr(RefundInfo refundInfo) {
        builder.clear();
        builderName("退款金额：");
        builder.append(refundInfo.getRefundPrice().toString()).append("\n");
        builderName("退款原因：");
        builder.append(refundInfo.getRefundReason()).append("\n");
        builderName("退款备注：");
        builder.append(refundInfo.getRefundRemark()).append("\n");
        builderName("退款编号：");
        builder.append(refundInfo.getRefundApplicationNum()).append("\n");
        builderName("申请时间：");
        builder.append(refundInfo.getRefundTime());

        switch (refundInfo.getRefundStatus()){
            case RefundInfo.REFUND_STATUS_LOADING: //退款处理中
                break;
            case RefundInfo.REFUND_STATUS_SUCCESS:  //退款成功
            case RefundInfo.REFUND_STATUS_FAIL:  //退款失败
                String auditTime = refundInfo.getAuditTime();
                if (AppUtility.isNotEmpty(auditTime)) {
                    builderName("\n审核时间：");
                    builder.append(auditTime);
                }
                break;
        }
        return  builder;
    }

    private void builderName(String text) {
        StringUtil.builderString(builder, text, new ForegroundColorSpan(ContextCompat.getColor
                (mContext, R.color.text_gray)), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }


}
