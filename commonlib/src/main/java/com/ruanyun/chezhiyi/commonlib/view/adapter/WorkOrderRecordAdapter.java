package com.ruanyun.chezhiyi.commonlib.view.adapter;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderRecordInfo;
import com.ruanyun.chezhiyi.commonlib.util.CommentUtils;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：工单流水
 * <p>
 * Created by ycw on 2016/9/27.
 */
public class WorkOrderRecordAdapter extends MultiItemTypeAdapter<WorkOrderRecordInfo> {

    private boolean operationable = false;     //可操作的
    private boolean needToDistribution = false;  //  需要分配提成
    private boolean isLeadingUser = false;//  是否是 施工负责人
    public boolean isShowQulityCheckBtn = true;//判断是否 显示质检 按钮

    public void setNeedToDistribution(boolean needToDistribution) {
        this.needToDistribution = needToDistribution;
    }

    /**
     * 是否显示按钮
     *
     * @param operationable true - 显示
     *                      false - 不显示
     */
    public void setOperationable(boolean operationable) {
        this.operationable = operationable;
    }

    public void setLeadingUser(boolean leadingUser) {
        isLeadingUser = leadingUser;
    }

    public WorkOrderRecordAdapter(Context context, List<WorkOrderRecordInfo> datas) {
        super(context, datas);

//        有3个按钮操作
        addItemViewDelegate(new ItemViewDelegate<WorkOrderRecordInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.list_item_work_order_type;
            }

            @Override
            public boolean isForViewType(WorkOrderRecordInfo item, int position) {
//                施工中
                return item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_UNDER_CONSTRUCTION;
            }

            @Override
            public void convert(ViewHolder holder, final WorkOrderRecordInfo workOrderRecordInfo,
                                final int position) {
                AutoUtils.auto(holder.getConvertView());
                holder.setBackgroundRes(R.id.iv_time_line, position == 0 ? R.drawable.green : R
                        .drawable.grey);   // 时间线
                if (position == 0) {
                    holder.setTextColorRes(R.id.tv_time_line_event, R.color.theme_color_default);
                } else {
                    holder.setTextColorRes(R.id.tv_time_line_event, R.color.text_black);
                }

                TextView tvTimeLineEvent = holder.getView(R.id.tv_time_line_event); //事件类容
                tvTimeLineEvent.setText(getRecordContent(workOrderRecordInfo));

                //是否可以操作
                if (operationable && position == 0) {
                    holder.setVisible(R.id.ll_operation_add_helper, true);
                    if (isLeadingUser) {
                        holder.setVisible(R.id.tv_end_order, true);
                        holder.setVisible(R.id.tv_add_helper, true);
                    } else {  // 不是施工技师  只可以代下单
                        holder.setVisible(R.id.tv_end_order, false);
                        holder.setVisible(R.id.tv_add_helper, false);
                    }
                } else {
                    holder.setVisible(R.id.ll_operation_add_helper, false);
                    return;
                }

                final TextView tvEndOrder = holder.getView(R.id.tv_end_order);  //结束施工
                tvEndOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (btnClick != null) {
                            btnClick.onBtnClick(workOrderRecordInfo.getWorkOrderStatus(),
                                    tvEndOrder, position);
                        }
                    }
                });

                final TextView tvAddHelper = holder.getView(R.id.tv_add_helper); //添加助手
                tvAddHelper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (btnClick != null) {
                            btnClick.onBtnClick(workOrderRecordInfo.getWorkOrderStatus(),
                                    tvAddHelper, position);
                        }
                    }
                });

                final TextView tvAssist = holder.getView(R.id.tv_assist); //代下单
                tvAssist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (btnClick != null) {
                            btnClick.onBtnClick(workOrderRecordInfo.getWorkOrderStatus(),
                                    tvAssist, position);
                        }
                    }
                });

                if (CommentUtils.permission.contains("GDNZ")) {
                    holder.setVisible(R.id.tv_add_helper, true);
                    holder.setVisible(R.id.tv_end_order, true);
                    holder.setVisible(R.id.tv_add_helper, false);
                }
            }
        });

//         有2个按钮操作
        addItemViewDelegate(new ItemViewDelegate<WorkOrderRecordInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.list_item_work_order_type_quality;
            }

            @Override
            public boolean isForViewType(WorkOrderRecordInfo item, int position) {
//                施工结束，质检中   ；  返修中，质检不合格   ； 等待施工   ； 等待结算，质检合格
                return item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY ||  //施工结束，质检中
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_REPAIR ||    //返修中，质检不合格
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_PREPARE ||       //即将开始施工
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_FINISH;          //完成结算
            }

            @Override
            public void convert(ViewHolder holder, final WorkOrderRecordInfo workOrderRecordInfo,
                                final int position) {
                AutoUtils.auto(holder.getConvertView());
                holder.setBackgroundRes(R.id.iv_time_line, position == 0 ? R.drawable.green : R.drawable.grey);   // 时间线

                TextView tvTimeLineEvent = holder.getView(R.id.tv_time_line_event); //事件类容
                tvTimeLineEvent.setText(getRecordContent(workOrderRecordInfo));
                if (position == 0) {
                    holder.setTextColorRes(R.id.tv_time_line_event, R.color.theme_color_default);
                } else {
                    holder.setTextColorRes(R.id.tv_time_line_event, R.color.text_black);
                }

                if (operationable && position == 0) {      //是否可以操作
                    holder.setVisible(R.id.ll_operation_quality, true);
                    if (workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_FINISH) { // 已完成的工单
                        if (needToDistribution && isLeadingUser) { //  是施工技师 并且  需要 分配提成
                            holder.setVisible(R.id.ll_operation_quality, true);
                            holder.setVisible(R.id.tv_cancel, false);
                        } else {
                            holder.setVisible(R.id.ll_operation_quality, false);
                        }
                    } else if (workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY) {   //施工结束，质检中
                        if (isShowQulityCheckBtn) {     // 当前用户 有没有质检权限
                            holder.setVisible(R.id.ll_operation_quality, true);
                        } else {
                            holder.setVisible(R.id.ll_operation_quality, false);
                        }
                    } else if (workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_REPAIR ||
                            workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_PREPARE) {  //返修中，质检不合格  或  //即将开始施工
                        if (isLeadingUser) {     // 当前用户 是不是施工技师
                            holder.setVisible(R.id.ll_operation_quality, true);
                        } else {
                            holder.setVisible(R.id.ll_operation_quality, false);
                        }
                    }

                    final TextView tvPass = holder.getView(R.id.tv_pass); //质检通过，开始施工， 分配提成
                    if (workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY) {   //施工结束，质检中
                        tvPass.setText("质检通过");
                        //                        if (isShowQulityCheckBtn) {
                        //                            tvPass.setBackgroundResource(R.drawable.button_selector_default);
                        //                        } else {//不是 质检员隐藏
                        //                            tvPass.setVisibility(View.GONE);
                        //                        }
                    } else if (workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_REPAIR ||// 返修中
                            workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_PREPARE) {//即将开始施工
                        tvPass.setText("开始施工");
                        tvPass.setBackgroundResource(R.drawable.button_selector_default);
                    } else {
                        tvPass.setText("分配提成");
                        tvPass.setBackgroundResource(R.drawable.corner_rectangle_grange_border);
                    }
                    tvPass.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (btnClick != null) {
                                btnClick.onBtnClick(workOrderRecordInfo.getWorkOrderStatus(), tvPass, position);
                            }
                        }
                    });

                    final TextView tvCancel = holder.getView(R.id.tv_cancel); //拒绝， 添加助手
                    if (workOrderRecordInfo.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY) {  // 工单质检中   显示 拒绝
                        tvCancel.setText("拒绝");
                        //                        if(isShowQulityCheckBtn ) {
                        //                            holder.setVisible(R.id.ll_operation_quality, true);
                        //                            tvCancel.setVisibility(View.VISIBLE);//是 质检员显示
                        //                        } else {
                        //                            tvCancel.setVisibility(View.GONE);//不是 质检员隐藏
                        //                        }
                    } else {
                        tvCancel.setText("添加助手");
                    }
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (btnClick != null) {
                                btnClick.onBtnClick(workOrderRecordInfo.getWorkOrderStatus(), tvCancel, position);
                            }
                        }
                    });
                } else {
                    holder.setVisible(R.id.ll_operation_quality, false);
                }
                if (CommentUtils.permission.contains("GDNZ") && position == 0) {
                    holder.setVisible(R.id.ll_operation_quality, true);
                    holder.setVisible(R.id.tv_cancel, true);
                    holder.setVisible(R.id.tv_pass, true);
//                    holder.setVisible(R.id.tv_add_helper, false);
                }
            }
        });

//        只显示文字 没有操作按钮
        addItemViewDelegate(new ItemViewDelegate<WorkOrderRecordInfo>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.list_item_work_order_type_text;
            }

            @Override
            public boolean isForViewType(WorkOrderRecordInfo item, int position) {
                /* 工单等待确认 ； 已确认，等待进店 ； 完成结算*/
                return item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_WAIT ||
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_CONFIRM ||
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_WAIT_FOR_CONSTRUCTION ||
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_CONFORMITY ||    //等待结算，质检合格
                        item.getWorkOrderStatus() == 0 ||
                        item.getWorkOrderStatus() == WorkOrderRecordInfo.WORK_ORDER_STATUS_CANCEL;  //取消
            }

            @Override
            public void convert(ViewHolder holder, WorkOrderRecordInfo workOrderRecordInfo, int
                    position) {
                AutoUtils.auto(holder.getConvertView());
                holder.setBackgroundRes(R.id.iv_time_line, position == 0 ? R.drawable.green : R
                        .drawable.grey);  // 时间线

                TextView tvTimeLineEvent = holder.getView(R.id.tv_time_line_event); //事件类容
                tvTimeLineEvent.setText(getRecordContent(workOrderRecordInfo));
                if (position == 0) {
                    holder.setTextColorRes(R.id.tv_time_line_event, R.color.theme_color_default);
                } else {
                    holder.setTextColorRes(R.id.tv_time_line_event, R.color.text_black);
                }
                if (CommentUtils.permission.contains("GDNZ")) {
                    holder.setVisible(R.id.tv_add_helper, true);
                    holder.setVisible(R.id.tv_end_order, true);
                    holder.setVisible(R.id.tv_add_helper, false);
                }
            }

        });
    }

    /**
     * 设置显示的内容
     *
     * @param workOrderRecordInfo
     * @return
     */
    private SpannableStringBuilder getRecordContent(WorkOrderRecordInfo workOrderRecordInfo) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("【" + workOrderRecordInfo.getRecordTitle() + "】")
                .setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color
                        .theme_color_default)), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(workOrderRecordInfo.getRecordContent());
        ssb.append("\n").append(workOrderRecordInfo.getCreateTime());
        return ssb;
    }

    public void setData(List<WorkOrderRecordInfo> data) {
        this.mDatas = data;
    }

    /**
     * 按钮的点击事件
     */
    public interface BtnClick {
        void onBtnClick(int status, View view, int position);
    }

    //    按钮的点击事件
    private BtnClick btnClick;

    public BtnClick getBtnClick() {
        return btnClick;
    }

    public void setBtnClick(BtnClick btnClick) {
        this.btnClick = btnClick;
    }
}
