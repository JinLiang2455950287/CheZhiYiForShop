package com.ruanyun.czy.client.view.ui.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.GoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.RefundInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.UpOrderStatusParams;
import com.ruanyun.chezhiyi.commonlib.presenter.CancelOrderPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.OrderDetailedPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.OrderDetailedMvpView;
import com.ruanyun.chezhiyi.commonlib.view.UpStatusMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.GoodsAdapter;
import com.ruanyun.czy.client.view.adapter.RefundAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Description ：订单详细页
 * <p/>
 * Created by ycw on 2016/9/19.
 */
public class OrderDetailedActivity
        extends AutoLayoutActivity
        implements Topbar
        .onTopbarClickListener, OrderDetailedMvpView, UpStatusMvpView {

    public static final int REQUEST_CODE_REFRESH = 2334;
    @BindView(R.id.tv_order_cancel)
    TextView tvOrderCancel;//取消订单  或  申请退款按钮
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.iv_order_type)
    ImageView ivOrderType;
    @BindView(R.id.recyclerView_product)
    RecyclerView recyclerViewProduct;
    @BindView(R.id.ll_sever)
    LinearLayout llSever;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;  //  评价  或   付款按钮
    @BindView(R.id.rl_operation)
    RelativeLayout rlOperation;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;

    private OrderInfo orderInfo;
    private int orderStatus;
    private List<GoodsInfo> infoList;
    private OrderDetailedPresenter detailedPresenter = new OrderDetailedPresenter();

    private UpOrderStatusParams statusParams = new UpOrderStatusParams();
    private CancelOrderPresenter presenter = new CancelOrderPresenter();

    private RefundAdapter refundAdapter;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_order_detail);
        detailedPresenter.attachView(this);
        presenter.attachView(this);
        ButterKnife.bind(this);
        initView();
        setResult(RESULT_OK);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogX.d(TAG, "_________onNewIntent___________");
        orderInfo = intent.getParcelableExtra(C.IntentKey.ORDER_INFO);
        updateUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailedPresenter.detachView();
    }

    private void initView() {
        topbar.setTttleText("订单详情")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        LinearLayoutManager manager =
                new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerViewProduct.setLayoutManager(manager);
        orderInfo = getIntent().getParcelableExtra(C.IntentKey.ORDER_INFO);
        updateUI();
    }

    /**
     * 更新界面显示
     */
    private void updateUI() {
        if (orderInfo == null) {
            return;
        }
        orderStatus = orderInfo.getOrderStatus();

        switch (orderStatus) {

            case OrderInfo.ORDER_STATE_REFUNDED_COMPLETED_FINISH:   //退款成功
                tvOrderType.setText("退款成功");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_tkcg);
                initRefundRecycler();
                tvOrderNum.setVisibility(View.GONE);
                rlOperation.setVisibility(View.VISIBLE);
                detailedPresenter.tuikuanList(app.getApiService().tuikuanList(app
                        .getCurrentUserNum(), orderInfo.getOrderNum()));
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderCancel.setText("删除订单");
                tvEvaluate.setVisibility(View.GONE);
                break;

            case OrderInfo.ORDER_STATE_CANCELED://已取消
                tvOrderType.setText("已取消");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_tkcg);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderCancel.setText("删除订单");
                tvEvaluate.setVisibility(View.GONE);
                rlOperation.setVisibility(View.VISIBLE);
                initRecyclerView();
                initBottomText();
                tvTotalPrice.setVisibility(View.VISIBLE);
                tvTotalPrice.setText(String.format("交易金额:¥%s", orderInfo.getTotalPrice()));
                break;

            case OrderInfo.ORDER_STATE_PENDING_PAYMENT://等待用户付款
                tvOrderType.setText("等待用户付款");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_dfk);
                initRecyclerView();
                initBottomText();
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvEvaluate.setVisibility(View.VISIBLE);
                tvEvaluate.setText("付款");
                tvOrderCancel.setText("取消订单");
                break;
            case OrderInfo.ORDER_STATE_CONSUMED://付款完成，等待消费
                tvOrderType.setText("付款完成，等待消费");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_dsf);
                initRecyclerView();
                initBottomText();
                tvEvaluate.setVisibility(View.GONE);
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderCancel.setText("申请退款");
                break;
            case OrderInfo.ORDER_STATE_EVALUATED://交易成功
                tvOrderType.setText("交易成功");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_dpj);
                initRecyclerView();
                initBottomText();
                tvEvaluate.setVisibility(View.VISIBLE);
                tvEvaluate.setText("评价");
                tvOrderCancel.setVisibility(View.GONE);
                break;

            case OrderInfo.ORDER_STATE_REFUNDEDING://退款处理中
                tvOrderType.setText("退款处理中");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_tkclz);
                initRefundRecycler();
                tvOrderNum.setVisibility(View.GONE);
                rlOperation.setVisibility(View.GONE);
                tvTotalPrice.setVisibility(View.VISIBLE);
                tvTotalPrice.setText(new StringBuilder().append("交易金额:¥").append(orderInfo
                        .getTotalPrice()).toString());
                detailedPresenter.tuikuanList(app.getApiService().tuikuanList(app
                        .getCurrentUserNum(), orderInfo.getOrderNum()));
                break;

            case OrderInfo.ORDER_STATE_REFUNDED://已完成
                tvOrderType.setText("已完成");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_tkcg);
                initRecyclerView();
                initBottomText();
                tvTotalPrice.setVisibility(View.VISIBLE);
                tvTotalPrice.setText(String.format("交易金额:¥%s", orderInfo.getTotalPrice()));
                tvOrderCancel.setVisibility(View.VISIBLE);
                tvOrderCancel.setText("删除订单");
                tvEvaluate.setVisibility(View.GONE);
                break;

            case OrderInfo.ORDER_STATE_ZC_REFUNDED://众筹订单详情
                tvOrderType.setText("众筹参与成功，请耐心等待结果");
                ivOrderType.setImageResource(R.drawable.myorder_pendant_dsf);
                initRecyclerView();
                initBottomText();
                rlOperation.setVisibility(View.GONE);
                break;

            default:

                break;
        }
        if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_JF)/* &&(orderInfo.getActualPrice() == null || orderInfo.getActualPrice().doubleValue() == 0)*/) {
            if (orderStatus == OrderInfo.ORDER_STATE_PENDING_PAYMENT || orderStatus == OrderInfo.ORDER_STATE_CANCELED || orderStatus == OrderInfo
                    .ORDER_STATE_EVALUATED ) { // 积分订单是 待付款 或 已取消的  或 待评价的   保持付款 和 删除订单、 评价订单  的功能
                rlOperation.setVisibility(View.VISIBLE);
            } else {  // 否则
                rlOperation.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 退款时界面的 recycleView 的 adapter
     */
    private void initRefundRecycler() {
        refundAdapter = new RefundAdapter(mContext, R.layout.layout_refund_info, new
                ArrayList<RefundInfo>());
        recyclerViewProduct.setAdapter(refundAdapter);
    }

    /**
     * 订单底部的编号、时间等信息
     */
    private void initBottomText() {
        StringBuilder builder = new StringBuilder();
        builder.append("订单编号:").append(orderInfo.getOrderNum());
        if (orderInfo.getPayMethod() == 2) {
            builder.append("\n").append("支付宝交易号:").append(orderInfo.getPayThirtAccount());
        } else if (orderInfo.getPayMethod() == 3) {
            builder.append("\n").append("微信交易号:").append(orderInfo.getPayThirtAccount());
        }
        builder.append("\n").append("创建时间:").append(orderInfo.getOrderCreateTime());
        String payMethod = DbHelper.getInstance().getParentName(String.valueOf(orderInfo
                .getPayMethod()), C.ParentCode.PAY_METHOD);
        if (AppUtility.isNotEmpty(payMethod)) {
            builder.append("\n").append("支付方式:").append(payMethod);
        }
        if (orderStatus == OrderInfo.ORDER_STATE_CONSUMED
                || orderStatus == OrderInfo.ORDER_STATE_EVALUATED) {
            builder.append("\n").append("付款时间:").append(orderInfo.getPayTime());
        }
        if (orderStatus == OrderInfo.ORDER_STATE_EVALUATED || orderStatus == OrderInfo.ORDER_STATE_REFUNDED) {
            builder.append("\n").append("消费时间:").append(orderInfo.getConsumeTime());
        }
        tvOrderNum.setText(builder.toString());
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        infoList = new ArrayList<>();
        infoList.addAll(getGoodsList(orderInfo.getOrderGoodsList()));

        GoodsInfo totalPriceInfo = new GoodsInfo();
        totalPriceInfo.setGoodsName("订单总价");
        if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_JF) && orderInfo.getActualPrice().doubleValue() == 0) {
            totalPriceInfo.setActivityPrice(orderInfo.getActualPrice().doubleValue());
        } else {
            totalPriceInfo.setActivityPrice(orderInfo.getTotalPrice().doubleValue());
        }
        totalPriceInfo.setPrice(true);
        totalPriceInfo.setPriceType(GoodsInfo.TYPE_NORMAL);
        infoList.add(totalPriceInfo);

        if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_JF)) {

            GoodsInfo preferentPriceInfo = new GoodsInfo();
            preferentPriceInfo.setGoodsName("积分");
            preferentPriceInfo.setActivityPrice(orderInfo.getPreferentialPrice().doubleValue());
            preferentPriceInfo.setPrice(true);
            preferentPriceInfo.setPriceType(GoodsInfo.TYPE_INTEGRAL);
            infoList.add(preferentPriceInfo);

        } else {

            GoodsInfo preferentPriceInfo = new GoodsInfo();
            preferentPriceInfo.setGoodsName("优惠金额");
            preferentPriceInfo.setActivityPrice(orderInfo.getPreferentialPrice().doubleValue());
            preferentPriceInfo.setPrice(true);
            preferentPriceInfo.setPriceType(GoodsInfo.TYPE_FAVORABLE);
            infoList.add(preferentPriceInfo);
        }

        GoodsInfo showPriceInfo = new GoodsInfo();
        showPriceInfo.setGoodsName("应付总额");
        showPriceInfo.setActivityPrice(orderInfo.getActualPrice() == null ? 0 : orderInfo.getActualPrice().setScale(1, BigDecimal.ROUND_HALF_DOWN).doubleValue());
        showPriceInfo.setPrice(true);
        showPriceInfo.setPriceType((orderStatus == OrderInfo.ORDER_STATE_PENDING_PAYMENT || orderStatus == OrderInfo.ORDER_STATE_CANCELED)
                ? GoodsInfo.TYPE_COLOR : GoodsInfo.TYPE_NORMAL);
        infoList.add(showPriceInfo);

        if (orderStatus == OrderInfo.ORDER_STATE_CONSUMED ||
                orderStatus == OrderInfo.ORDER_STATE_EVALUATED ||
                orderStatus == OrderInfo.ORDER_STATE_ZC_REFUNDED ) {
            GoodsInfo actualPrice = new GoodsInfo();
            actualPrice.setGoodsName("实付款");
            actualPrice.setActivityPrice(orderInfo.getActualPrice().doubleValue());
            actualPrice.setPrice(true);
            actualPrice.setPriceType(GoodsInfo.TYPE_COLOR);
            infoList.add(actualPrice);
        }

        GoodsAdapter adapter = new GoodsAdapter(mContext, infoList);
        recyclerViewProduct.setAdapter(adapter);
    }

    /**
     * 暂时后台定死 订单 只有一个产品
     *
     * @param orderGoodsList
     */
    private List<GoodsInfo> getGoodsList(List<OrderGoodsInfo> orderGoodsList) {
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        if (orderGoodsList == null) {
            return goodsInfos;
        }
        // 如果是工单   只返回商品类型  没有商品
        if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_GD)) {
            GoodsInfo info = new GoodsInfo();
            info.setGoodsName("");
            String goodsType = AppUtility.getTypeName(orderInfo.getOrderType());
            info.setOrderType(goodsType);
            // 如果是纯积分兑换商品  使用 实付金额
            info.setActivityPrice(0);
            info.setTotalCount(0);
            info.setMainPhoto("");
            info.setTotalCountSurplus(0);
            goodsInfos.add(info);
            return goodsInfos;
        }
        //  只取 商品列表里的 第一个商品信息  显示
        for (int i = 0; i < ((orderGoodsList.size() == 0) ? 0 : 1); i++) {
            GoodsInfo info = new GoodsInfo();
            info.setGoodsName(orderGoodsList.get(i).getGoodsName());
            String goodsType = AppUtility.getTypeName(orderGoodsList.get(i).getGoodsType());
            info.setOrderType(goodsType);
            // 如果是纯积分兑换商品  使用 实付金额
            if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_JF) && orderInfo.getActualPrice().doubleValue() == 0) {
                info.setActivityPrice(orderInfo.getActualPrice().doubleValue());
            } else {
                info.setActivityPrice(orderInfo.getSinglePrice().doubleValue());
            }
            info.setTotalCount(orderInfo.getTotalCount());
            info.setMainPhoto(orderGoodsList.get(i).getMainPhoto());
            info.setTotalCountSurplus(orderInfo.getTotalCountSurplus());
            goodsInfos.add(info);
        }
        return goodsInfos;
    }


    @OnClick({R.id.ll_sever, R.id.ll_call, R.id.tv_order_cancel, R.id.tv_evaluate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sever://联系商家
                if (app.getStoreInfo() != null && AppUtility.isNotEmpty(app.getStoreInfo().getUserNumSecretary())) {
                    showActivity(AppUtility.getChatIntent(mContext, app.getStoreInfo().getUserNumSecretary()));
                } else {
                    AppUtility.showToastMsg("商家没有设置联系人");
                }
                break;
            case R.id.ll_call://打电话
                if (app.getStoreInfo() != null && app.getStoreInfo().getLinkTel() != null) {
                    //showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + app.getStoreInfo().getLinkTel())));
                    confirmDialPhone();
                }
                break;
            case R.id.tv_order_cancel://取消订单 申请退款
                if (orderStatus == OrderInfo.ORDER_STATE_PENDING_PAYMENT) {
                    // : 2016/9/22 取消订单  操作
                    createDialog(orderInfo, "确定取消订单吗?", OrderInfo.ORDER_STATE_PENDING_PAYMENT);
                } else if (orderStatus == OrderInfo.ORDER_STATE_CONSUMED) {
                    if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_JF)) {
                        createDialog(orderInfo, "积分兑换退款，积分将不会返回", OrderInfo.ORDER_STATE_CONSUMED);
                    } else {
                        //                    去申请退款
                        toRefundApplication(orderInfo);
                    }

                } else if (orderStatus == (OrderInfo.ORDER_STATE_REFUNDED |/*| orderStatus == */OrderInfo.ORDER_STATE_REFUNDED_COMPLETED_FINISH)) {
                    createDelDialog(orderInfo);
                }
                break;
            case R.id.tv_evaluate: //付款 或 评价
                if (orderStatus == OrderInfo.ORDER_STATE_PENDING_PAYMENT) {
                    // todo : 2016/9/22 付款  校验   操作
                    presenter.valiteOrder(app.getApiService().valiteOrder(app.getCurrentUserNum(), orderInfo.getOrderNum()));
                } else if (orderStatus == OrderInfo.ORDER_STATE_EVALUATED) {
                    // : 2016/9/22 评价  操作
                    Intent intent = new Intent(mContext, AppraiseActivity.class);
                    intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
//                    showActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE_REFRESH);
                }
                break;
        }
    }

    private void toRefundApplication(OrderInfo orderInfo) {
        // : 2016/9/22 申请退款  操作
        Intent intent = new Intent(mContext, RefundApplicationActivity.class);
        intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
        startActivityForResult(intent, REQUEST_CODE_REFRESH);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REFRESH && resultCode == RESULT_OK) {
            detailedPresenter.getOrderDetail(orderInfo.getOrderNum());
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_left:
                finish();
                break;
        }
    }


    /**
     * 创建一个取消订单提示  dialog
     *  @param orderInfo
     * @param message
     * @param orderState
     */
    private void createDialog(final OrderInfo orderInfo, String message, final int orderState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (orderState == OrderInfo.ORDER_STATE_PENDING_PAYMENT) {
                            presenter.updateOrderStatus(getCall(orderInfo));
                        } else if (orderState == OrderInfo.ORDER_STATE_CONSUMED) {
                            toRefundApplication(orderInfo);
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 创建一个取消订单提示  dialog
     *
     * @param orderInfo
     */
    private void createDelDialog(final OrderInfo orderInfo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("确定删除订单吗?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.delOrder(app.getApiService().delOrder(app.getCurrentUserNum(), orderInfo.getOrderNum()));
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 确认拨打电话提示
     */
    public void confirmDialPhone() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).setMessage(app.getStoreInfo().getLinkTel())
                //                .setTitle("确认拨打电话？")
                .setNegativeButton(com.ruanyun.chezhiyi.commonlib.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                }).setPositiveButton(com.ruanyun.chezhiyi.commonlib.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + app.getStoreInfo().getLinkTel())));
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }


    /**
     * 修改订单状态的接口
     * @param orderInfo
     * @return
     */
    private Call<ResultBase> getCall(OrderInfo orderInfo) {
        statusParams.setOrderNum(orderInfo.getOrderNum());
        statusParams.setOrderStatus(-1);
        return app.getApiService().updateOrderStatus(app.getCurrentUserNum(), statusParams);
    }

    /**
     * 获取退款信息 成功
     * @param refundInfoList
     */
    @Override
    public void tuikuanSuccess(List<RefundInfo> refundInfoList) {
        // : 2016/9/22 获取退款信息 成功
        refundAdapter.setDatas(refundInfoList);
        refundAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    /**
     * 获取订单信息详情
     * @param orderInfo
     */
    @Override
    public void orderDetailResult(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        updateUI();
    }

    /**
     *  取消订单成功后
     */
    @Override
    public void cancelSuccess() {
        finish();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void delOrderSuccess() {
        finish();
    }

    @Override
    public void valiteOrderSuccess() {
        showActivity(AppUtility.getPayIntent(orderInfo, mContext));
    }

    @Override
    public Context getContext() {
        return null;
    }
}
