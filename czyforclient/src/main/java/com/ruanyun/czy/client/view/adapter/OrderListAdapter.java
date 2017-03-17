package com.ruanyun.czy.client.view.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的订单列表的数据适配器
 * Created by Sxq on 2016/9/18
 */
public class OrderListAdapter extends CommonAdapter<OrderInfo> {

//    private OrderGoodsListAdapter goodsListAdapter;
    public static final String BUTTON_TYPE_PAY = "pay";
    public static final  String BUTTON_TYPE_WAIT= "wait";
    public static final  String BUTTON_TYPE_EVALUATED = "evaluated";
    public static final String BUTTON_TYPE_CANCEL = "cancel";
    public static final String BUTTON_TYPE_DELETE = "button_type_delete";

    private OnItemBtnClickListener onClickListener;

    public void setOnClickListener(OnItemBtnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OrderListAdapter(Context context, int layoutId, List<OrderInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, final OrderInfo item, final int position) {
        AutoUtils.auto(holder.getConvertView());
        TextView tvOrderNum = holder.getView(R.id.tv_order_number);//订单编号
        tvOrderNum.setText(String.format("订单编号: %s", item.getOrderNum()));
        TextView tvCancel = holder.getView(R.id.tv_cancel_amount);//取消订单  申请退款
        TextView tvPay = holder.getView(R.id.tv_payment);//付款    评价
        TextView tvState = holder.getView(R.id.tv_state);//付款状态
        TextView tvActualPrice = holder.getView(R.id.tv_actual_price);
        ImageView ivPic = holder.getView(R.id.iv_pic);
        TextView tvOrderGoods = holder.getView(R.id.tv_order_good);
        TextView tvPrice = holder.getView(R.id.tv_price);
        TextView tvCount = holder.getView(R.id.tv_count);
        TextView tvCountSurplus = holder.getView(R.id.tv_count_surplus);
        LinearLayout llGoodsInfo = holder.getView(R.id.ll_goods_info);
//        LinearLayout llActualPrice = holder.getView(R.id.ll_actual_price);
        tvCountSurplus.setText(String.format("剩余数量：%s", String.valueOf(item.getTotalCountSurplus())));

        List<OrderGoodsInfo> goodsInfos = item.getOrderGoodsList();
        if (goodsInfos != null && goodsInfos.size() > 0) {
            OrderGoodsInfo orderGoodsInfo = goodsInfos.get(0);
            Glide.with(mContext).load(FileUtil.getImageUrl(orderGoodsInfo.getMainPhoto())).error(R.drawable.default_img).into(ivPic);
            tvOrderGoods.setText(orderGoodsInfo.getGoodsName());
            tvPrice.setText(String.format("¥%s", item.getSinglePrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
            tvCount.setText(String.format("x%s", String.valueOf(item.getTotalCount())));
            llGoodsInfo.setVisibility(View.VISIBLE);
        }
        else {
            llGoodsInfo.setVisibility(View.GONE);
        }

        int orderState = item.getOrderStatus();
        // 订单状态(-1已取消 1待付款 2、待消费 3、待评价 4、退款中  5、已完成)
        //1待付款
        if (orderState == OrderInfo.ORDER_STATE_PENDING_PAYMENT) {
            tvCancel.setVisibility(View.VISIBLE);
            tvPay.setVisibility(View.VISIBLE);
            tvState.setText(/*"待付款"*/ getNameCache(orderState));
            tvCancel.setText("取消订单");
            tvPay.setText("付款");
            tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                    onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_PAY);
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                    onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_CANCEL);
                }
            });
            tvActualPrice.setText(String.format("合计：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //2待消费
        else if (orderState == OrderInfo.ORDER_STATE_CONSUMED) {
            tvCancel.setVisibility(View.VISIBLE);
            tvPay.setVisibility(View.GONE);
            tvCancel.setText("申请退款");
            tvState.setText(/*"待消费"*/getNameCache(orderState));
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_WAIT);
                }
            });
            tvActualPrice.setText(String.format("交易金额：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //3待评价
        else if (orderState == OrderInfo.ORDER_STATE_EVALUATED) {
            tvState.setText(/*"待评价"*/getNameCache(orderState));
            tvCancel.setVisibility(View.GONE);
            tvPay.setVisibility(View.VISIBLE);
            tvPay.setText("评价");
            tvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_EVALUATED);
                }
            });
            tvActualPrice.setText(String.format("交易金额：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //4、退款中
        else if (orderState == OrderInfo.ORDER_STATE_REFUNDEDING) {
            tvState.setText(/*"退款处理中"*/getNameCache(orderState));
            tvCancel.setVisibility(View.GONE);
            tvPay.setVisibility(View.GONE);
//            tvPay.setText("催款");
            tvActualPrice.setText(String.format("交易金额：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        // 5、 已完成
        else if (orderState==OrderInfo.ORDER_STATE_REFUNDED){  // 5、 已完成
            tvCancel.setVisibility(View.GONE);// // TODO: 2017/1/4 已完成订单不能删除   只有已取消才能删除
//            tvCancel.setText("删除订单");
//            tvCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickListener != null)
//                        onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_DELETE);
//                }
//            });
            tvPay.setVisibility(View.GONE);
            tvState.setText(/*"已完成"*/getNameCache(orderState));
            tvActualPrice.setText(String.format("交易金额：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //-1已取消
        else if (orderState==OrderInfo.ORDER_STATE_CANCELED){   //-1已取消
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText("删除订单");
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_DELETE);
                }
            });
            tvPay.setVisibility(View.GONE);
            tvState.setText(/*"已取消"*/getNameCache(orderState));
            tvActualPrice.setText(String.format("合计：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //-2 退款完成
        else if (orderState == OrderInfo.ORDER_STATE_REFUNDED_COMPLETED_FINISH ) {//-2 退款完成
            tvCancel.setVisibility(View.GONE);  // TODO: 2017/1/4 已完成订单不能删除   只有已取消才能删除
//            tvCancel.setText("删除订单");
//            tvCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickListener != null)
//                        onClickListener.OnItemBtnClick(holder.getConvertView(), item, BUTTON_TYPE_DELETE);
//                }
//            });
            tvPay.setVisibility(View.GONE);
            tvState.setText(/*"退款成功"*/getNameCache(orderState));
            tvActualPrice.setText(String.format("退款金额：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //  众筹 订单
        else if (orderState==OrderInfo.ORDER_STATE_ZC_REFUNDED){
            tvCancel.setVisibility(View.GONE);
            tvPay.setVisibility(View.GONE);
            tvState.setText(/*"等待中"*/getNameCache(orderState));
            tvActualPrice.setText(String.format("交易金额：¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
        //  积分订单
        if (item.getOrderType().equals(C.OrderType.ORDER_TYPE_JF) )/* && item.getActualPrice().doubleValue() == 0*/ {
            if (orderState == OrderInfo.ORDER_STATE_PENDING_PAYMENT ||
                    orderState == OrderInfo.ORDER_STATE_CANCELED ||
                    orderState == OrderInfo.ORDER_STATE_EVALUATED ) { // 积分订单是 待付款 或 已取消的 保持付款 和  删除订单  、  评价订单  的功能

            } else {  // 否则
                tvPay.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
            }
            if (item.getActualPrice().doubleValue() == 0) {     //纯积分兑换
                tvPrice.setText(String.format("¥%s", item.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
            }
        }
    }

    private SparseArray<String> cacheMap = new SparseArray<>();

    private String getNameCache(int orderState) {
        String name = "";
        if (cacheMap.get(orderState) == null) {
            name = DbHelper.getInstance().getParentName(String.valueOf(orderState), C.ParentCode.ORDER_STATUS_TYPE);
            cacheMap.put(orderState, name);
        } else {
            name = cacheMap.get(orderState);
        }
        return name;
    }


    public void setData(List<OrderInfo> result) {
        this.mDatas = result;
        notifyDataSetChanged();
    }

    public void addDate(List<OrderInfo> result) {
        this.mDatas.addAll(result);
        notifyDataSetChanged();
    }

    public interface OnItemBtnClickListener{
        void OnItemBtnClick(View view, OrderInfo orderInfo, String buttonType);
    }
}
