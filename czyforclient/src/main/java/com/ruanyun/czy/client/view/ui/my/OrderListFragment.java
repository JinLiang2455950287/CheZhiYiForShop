package com.ruanyun.czy.client.view.ui.my;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.OrderListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.UpOrderStatusParams;
import com.ruanyun.chezhiyi.commonlib.presenter.CancelOrderPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.UpStatusMvpView;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.OrderListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * 我的订单列表 Fragment
 * Created by Sxq on 2016/9/19.
 */
public class OrderListFragment extends RefreshBaseFragment implements /*LazyFragmentPagerAdapter.Laziable,*/ AdapterView.OnItemClickListener, OrderListAdapter.OnItemBtnClickListener, UpStatusMvpView {

    private static final int REQ_REFRESH = 198;
    @BindView(R.id.list)
    ListView list;
    private OrderListParams params = new OrderListParams();
    private OrderListAdapter adapter;
    private String orderType = "";
    private UpOrderStatusParams statusParams = new UpOrderStatusParams();
    private CancelOrderPresenter presenter = new CancelOrderPresenter();
    private OrderInfo orderInfo; //  点击的待支付的订单


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_order_list, container, false);
        orderType = getArguments().getString(C.IntentKey.ORDER_TYPE);
        ButterKnife.bind(this, mContentView);
        initRefreshLayout();
        presenter.attachView(this);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        lazyLoad();
    }

    private void initView() {
        adapter = new OrderListAdapter(mContext, R.layout.list_item_my_order, new ArrayList<OrderInfo>());
        adapter.setOnClickListener(this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        refreshWithLoading();
    }

    /**
     * 获取订单列表
     *
     * @return
     */
    @Override
    public Call loadData() {
//        params.setOrderStatus(orderType);
        params.setOrderStatusString(orderType);
        // TODO: 2016/12/12  工单的订单
        // 如果  是待付款订单  添加工单类型待付款订单
        String type = this.orderType.equals("1") ? "TG,CP,MS,CX,ZC,JF,GD" : "TG,CP,MS,CX,ZC,JF";
        params.setOrderType(type);
        params.setPageNum(currentPage);
        return app.getApiService().orderList(app.getCurrentUserNum(), params);
    }

    /**
     * 加载数据
     *
     * @param result
     * @param tag
     */
    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
    }

    /**
     * 加载更多
     *
     * @param result
     * @param tag
     */
    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addDate(result.getResult());
    }

    /**
     * 跳转到订单详情页面
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderInfo info = adapter.getItem(position);
//        if (info.getOrderType().equals(C.OrderType.ORDER_TYPE_GD)) return;
        Intent intent = new Intent(mContext, OrderDetailedActivity.class);
        intent.putExtra(C.IntentKey.ORDER_INFO, info);
        showActivity(intent);
    }

    /**
     * 订单列表中订单状态的点击事件
     *
     * @param view
     * @param orderInfo
     * @param buttonType
     */
    @Override
    public void OnItemBtnClick(View view, final OrderInfo orderInfo, String buttonType) {
        switch (buttonType) {
            case OrderListAdapter.BUTTON_TYPE_PAY://付款
                // todo 校验订单
                this.orderInfo = orderInfo;
                presenter.valiteOrder(app.getApiService().valiteOrder(app.getCurrentUserNum(), orderInfo.getOrderNum()));
                break;
            case OrderListAdapter.BUTTON_TYPE_CANCEL://取消订单
                createDialog(orderInfo, "确定取消订单吗?", OrderListAdapter.BUTTON_TYPE_CANCEL);
                break;
            case OrderListAdapter.BUTTON_TYPE_WAIT://申请退款
                if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_JF)) {
                    createDialog(orderInfo, "积分兑换退款，积分将不会返回", OrderListAdapter.BUTTON_TYPE_WAIT);
                } else {
//                    去申请退款
                    toRefundApplication(orderInfo);
                }
                break;
            case OrderListAdapter.BUTTON_TYPE_EVALUATED://评价
                Intent intent = new Intent();
                intent.setClass(mContext, AppraiseActivity.class);
                intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
                startActivityForResult(intent, REQ_REFRESH);
                //showActivity(intent);
                break;

            case OrderListAdapter.BUTTON_TYPE_DELETE:  //删除订单
                createDialog(orderInfo, "确认删除此订单？", OrderListAdapter.BUTTON_TYPE_DELETE);
                break;
        }
    }

    /**
     * 跳转到申请退款界面
     *
     * @param orderInfo
     */
    private void toRefundApplication(OrderInfo orderInfo) {
        Intent intent = new Intent();
        intent.setClass(mContext, RefundApplicationActivity.class);
        intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
        startActivityForResult(intent, REQ_REFRESH);
        //showActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_REFRESH) {
                //  刷新界面
                lazyLoad();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshList(Event<String> event) {
        if (event.key.equals(C.EventKey.KEY_REFRESH_LIST)) {
            lazyLoad();
        }
    }

    /**
     * 创建一个取消订单提示  dialog
     *
     * @param orderInfo
     * @param title
     * @param buttonType
     */
    private void createDialog(final OrderInfo orderInfo, String title, final String buttonType) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (OrderListAdapter.BUTTON_TYPE_CANCEL.equals(buttonType)) {
                            presenter.updateOrderStatus(getCall(orderInfo));
                        } else if (OrderListAdapter.BUTTON_TYPE_WAIT.equals(buttonType)) {
                            toRefundApplication(orderInfo);
                        } else if (OrderListAdapter.BUTTON_TYPE_DELETE.equals(buttonType)) {
                            presenter.delOrder(app.getApiService().delOrder(app.getCurrentUserNum(), orderInfo.getOrderNum()));
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private Call<ResultBase> getCall(OrderInfo orderInfo) {
        statusParams.setOrderNum(orderInfo.getOrderNum());
        statusParams.setOrderStatus(-1);
        return app.getApiService().updateOrderStatus(app.getCurrentUserNum(), statusParams);
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void cancelSuccess() {
        // : 2016/10/11 取消订单成功后
//        lazyLoad();
//        onStartRefresh();
        refreshLayout.beginRefreshing();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void delOrderSuccess() {
        refreshLayout.beginRefreshing();
    }

    @Override
    public void valiteOrderSuccess() {
        showActivity(AppUtility.getPayIntent(orderInfo, mContext));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }
}
