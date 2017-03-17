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
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.BookingListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.CancelMakeParams;
import com.ruanyun.chezhiyi.commonlib.presenter.CancelMakePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CancelMakeMvpView;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.MyBookingAdapter;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Description:我的预约列表通用
 * Created by hdl on 2016/9/19.
 */
public class MyBookingFragment extends RefreshBaseFragment implements /*LazyFragmentPagerAdapter.Laziable,*/ MyBookingAdapter.OnMyBookingClickListener, CancelMakeMvpView {

    private static final int REQ_FRFRESH = 1111;
    BookingListParams params = new BookingListParams();
    ListView list;
    MyBookingAdapter adapter;
    String myBookingType = "";
    OrderInfo orderInfo;//预约订单
    CancelMakePresenter cancelMakePresenter = new CancelMakePresenter();
    private CancelMakeParams cancelParams = new CancelMakeParams();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_FRFRESH) {
                lazyLoad();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myBookingType = getArguments().getString(C.IntentKey.MY_BOOKING_TYPE);
        mContentView = inflater.inflate(R.layout.fragment_article_common, container, false);
        isFirstIn = true;
        isPrepared = true;
        cancelMakePresenter.attachView(this);
        initRefreshLayout();
        return mContentView;
    }
    /*
    public static MyBookingFragment newInstance(String articleType) {
        MyBookingFragment fragment = new MyBookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.IntentKey.MY_BOOKING_TYPE, articleType);
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //refreshWithLoading();
        initView();
        lazyLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelMakePresenter.detachView();
        if (refreshLayout != null) {
            refreshLayout.endRefreshing();
        }
    }

    private void initView() {
        list = getView(R.id.list);
        adapter = new MyBookingAdapter(mContext, R.layout.list_item_my_appointment, new ArrayList<AppointmentInfo>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                AppointmentInfo appointmentInfo = adapter.getItem(position);
                if (appointmentInfo != null) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, BookingDetailActivity.class);
                    intent.putExtra(C.IntentKey.BOOKING_MAKENUM, appointmentInfo.getMakeNum());
                    //showActivity(intent);
                    startActivityForResult(intent, REQ_FRFRESH);
                }
            }
        });
        adapter.setMyBookingClickListener(this);
        params.setTotalPage(10);
        params.setNumPerPage(10);
    }

    /**
     * 下拉刷新
     */
    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        if (!"".equals(myBookingType)) {
            params.setMakeStatusString(myBookingType);
        }
        return app.getApiService().getBookingList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        totalPage = result.getTotalPage();
        adapter.refresh(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.loadMore(result.getResult());
    }

    /**
     * adapter回调
     *
     * @param appointmentInfo
     * @param type
     */
    @Override
    public void onMyBookingClickListener(AppointmentInfo appointmentInfo, int type) {
        int makeStatus = appointmentInfo.getMakeStatus();//预约状态
        // orderInfo = appointmentInfo.getOrderInfo();
        switch (type) {
            case MyBookingAdapter.CANCEL_BOOKING://取消预约
                createDialog(appointmentInfo);
                break;
            case MyBookingAdapter.PAY_DEPOSIT: //支付订金
                // TODO: 2016/10/18 预约支付定金的处理
                orderInfo = new OrderInfo();
                orderInfo.setOrderNum(appointmentInfo.getDownPaymentOrderNum());
                orderInfo.setActualPrice(appointmentInfo.getDownPayment());
                orderInfo.setOrderType(C.OrderType.ORDER_TYPE_DJ);
                orderInfo.setOrderRemark(appointmentInfo.getRemark());
                Intent intent = AppUtility.getPayIntent(orderInfo, mContext);
                intent.putExtra(C.IntentKey.GOODS_NAME, "预约定金");
                startActivityForResult(intent, REQ_FRFRESH);
                break;
            case MyBookingAdapter.BOOKING_ONCE://再约一次
                showActivity(MakeAppointmentActivity.class);
                break;
        }
    }

    /**
     * 确定取消预约 弹窗
     *
     * @param appointmentInfo
     */
    private void createDialog(final AppointmentInfo appointmentInfo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("确定取消预约吗?").setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cancelMakePresenter.CancelMake(getCall(appointmentInfo));
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private Call<ResultBase> getCall(AppointmentInfo appointmentInfo) {
        cancelParams.setMakeNum(appointmentInfo.getMakeNum());
        cancelParams.setMakeStatus(-1);
        return app.getApiService().cancelMake(app.getCurrentUserNum(), cancelParams);
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void cancelSuccess() {
        lazyLoad();
    }
}
