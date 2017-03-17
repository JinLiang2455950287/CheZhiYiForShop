package com.ruanyun.chezhiyi.commonlib.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Description ：工单列表
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public class WorkOrderListAdapter extends CommonAdapter<WorkOrderInfo> {

    private SparseArray<String> statusMap = new SparseArray<String>();
    private boolean isSelectable = false;
    private selectAction selectAction;

    public void setSelectAction(WorkOrderListAdapter.selectAction selectAction) {
        this.selectAction = selectAction;
    }

    /**
     *  获取状态对应的名称
     * @param workOrderStatus
     * @return
     */
    private String getStatusName(int workOrderStatus){
        String statusName = statusMap.get(workOrderStatus);
        if (TextUtils.isEmpty(statusName)) {
            statusName = DbHelper.getInstance().getParentName(String.valueOf(workOrderStatus), C.ParentCode.WORK_ORDER_STATUS);
            statusMap.put(workOrderStatus, statusName);
        }
        return statusName;
    }

    public WorkOrderListAdapter(Context context, int layoutId, List<WorkOrderInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, final WorkOrderInfo item, int position) {

        viewHolder.setText(R.id.tv_order_number, "工单编号：" + item.getWorkOrderNum());
        if (isSelectable) {
            TextView tvState = viewHolder.getView(R.id.tv_state);
            viewHolder.setText(R.id.tv_state, "");
            tvState.setCompoundDrawablesWithIntrinsicBounds(item.isSelected() ? ContextCompat
                    .getDrawable(mContext, R.drawable.order_settlemen_selected) : ContextCompat
                    .getDrawable(mContext, R.drawable.order_settlement_pre), null, null, null);
            tvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setSelected(!item.isSelected());
                    notifyDataSetChanged();
                    if (selectAction != null)
                    selectAction.updateAllPriceUI(item);
                }
            });
        }else {
            viewHolder.setText(R.id.tv_state, getStatusName(item.getWorkOrderStatus()));
        }
        //服务项目
        viewHolder.setText(R.id.tv_service_project, item.getProjectName());
        //工位
        viewHolder.setText(R.id.tv_service_position, TextUtils.isEmpty(item.getWorkbayName()) ?
                "暂缺" : item.getWorkbayName());

        // 服务技师 或 服务客户
        viewHolder.setText(R.id.tv_service_user, App.getInstance().isClient() ? "服务技师" : "服务客户");
        String userName = App.getInstance().isClient() ? item.getLeadingUserName() : item.getUser() == null ? "" : item.getUser().getNickName();
        userName = TextUtils.isEmpty(userName) ? "暂无" : userName;
        viewHolder.setText(R.id.tv_service_customer, userName);

        if (item.getWorkOrderStatus() == 8 || item.getWorkOrderStatus() == 9) { // 8 - 待结算状态  或 9 -  已结束状态  显示价格
            viewHolder.setVisible(R.id.ll_all_price, true);
            if (item.getWorkOrderStatus() == 8) {
                viewHolder.setText(R.id.tv_price, new StringBuilder().append("工单总价：¥").append
                        (item.getTotalAmount()).toString());
            }
            if (item.getWorkOrderStatus() == 9) {
                viewHolder.setText(R.id.tv_price, new StringBuilder().append("消费总额：¥").append
                        (item.getTotalAmount()).toString());
            }
        } else {
            viewHolder.setVisible(R.id.ll_all_price, false);
        }

    }

    public void  setDate(List<WorkOrderInfo> datas){
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 显示选择按钮
     * @param flag
     */
    public void showSelectBtn(boolean flag) {
        isSelectable = flag;
    }

    /**
     *
     */
    public interface selectAction{
        void updateAllPriceUI(WorkOrderInfo item);
    }
}
