package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderDxdInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderRecordInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Description ：代下单管理父数据
 * <p/>
 * Created by hdl on 2016/10/8.
 */

public class InsteadOrderParentDelagate implements ItemViewDelegate<OrderGoodsInfo> {
    Context context;

    public InsteadOrderParentDelagate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.list_item_instead_order_parent;
    }

    @Override
    public boolean isForViewType(OrderGoodsInfo item, int position) {
        return item.isParent();
    }

    @Override
    public void convert(ViewHolder holder, OrderGoodsInfo item, int position) {
        AutoUtils.auto(holder.getConvertView());
        holder.setText(R.id.tv_workorder_number, TextUtils.isEmpty(item.getWorkOrderNum()) ? "" :
                "NO. " + item.getWorkOrderNum());//工单编号
        TextView tvstatus = holder.getView(R.id.tv_service_state);
        LogX.e("代下单管理父数据", item.getGoodsNum() + "");
        if (item.getGoodsNum().equals("9")) {
            tvstatus.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.daixiadan_finsh), null, null, null);
        } else {
            tvstatus.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.daixiadan_service), null, null, null);
        }
        tvstatus.setText(getWorkorderState(item.getGoodsNum()));//当前item.getGoodsNum()为工单状态
    }

    private String getWorkorderState(String goodsNum) {
        String state = "";
        switch (goodsNum) {
            case "-1":
                state = "已取消";
                break;
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
                state = "服务中";
                break;
            case "8":
            case "9":
                state = "已结束";
                break;
        }
        return state;
    }
}
