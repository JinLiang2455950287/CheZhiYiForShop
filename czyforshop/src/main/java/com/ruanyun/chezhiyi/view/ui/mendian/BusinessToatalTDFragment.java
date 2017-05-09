package com.ruanyun.chezhiyi.view.ui.mendian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;
import com.ruanyun.chezhiyi.commonlib.model.MenDianYinYeEInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.HuiYuanTongjiPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.HuiYuanYingYeETongjiPresenter;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanYingYeETongJiView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 2017/4/12
 * jin
 * 营业额统计 当日/当月
 */
public class BusinessToatalTDFragment extends BaseFragment implements HuiYuanYingYeETongJiView, SwipeRefreshLayout.OnRefreshListener {

    private TextView tvBanka, tvGongdanMoney, tvShangcheng, tvRepay, tvHuiyuanreain, tvCashpay, tvWeixinpay, tvZhifubaopay;
    private SwipeRefreshLayout refreshlayout;
    private HuiYuanYingYeETongjiPresenter huiYuanYingYeETongjiPresenter = new HuiYuanYingYeETongjiPresenter();
    private SimpleDateFormat sDateFormatend = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
    private String startdate, enddate;
    private String workOrderStatusString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_business_toatal_td, container, false);
        workOrderStatusString = getArguments().getString(C.IntentKey.WORK_ORDER_STATUS_STRING);
        initRefreshView();
        initView();

        startdate = sDateFormat.format(new Date()) + "-01";
        enddate = sDateFormatend.format(new Date());
        huiYuanYingYeETongjiPresenter.attachView(this);
        if (workOrderStatusString.equals("day")) {
            huiYuanYingYeETongjiPresenter.getYingYeEInfo(app.getApiService().getMenDianYinYeE(app.getCurrentUserNum(), enddate, "", 0));
        } else {
            huiYuanYingYeETongjiPresenter.getYingYeEInfo(app.getApiService().getMenDianYinYeE(app.getCurrentUserNum(), startdate, enddate, 0));
        }
        return mContentView;
    }


    private void initView() {

        tvBanka = (TextView) mContentView.findViewById(R.id.tv_banka);
        tvZhifubaopay = (TextView) mContentView.findViewById(R.id.tv_zhifubaopay);
        tvGongdanMoney = (TextView) mContentView.findViewById(R.id.tv_gongdanMoney);
        tvShangcheng = (TextView) mContentView.findViewById(R.id.tv_shangcheng);
        tvRepay = (TextView) mContentView.findViewById(R.id.tv_repay);
        tvHuiyuanreain = (TextView) mContentView.findViewById(R.id.tv_huiyuanreain);
        tvCashpay = (TextView) mContentView.findViewById(R.id.tv_cashpay);
        tvWeixinpay = (TextView) mContentView.findViewById(R.id.tv_weixinpay);
        //手动调用,通知系统去测量
        refreshlayout.measure(0, 0);
        refreshlayout.setRefreshing(true);
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshView() {
        refreshlayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.refreshlayout);
        refreshlayout.setColorSchemeResources(com.ruanyun.chezhiyi.commonlib.R.color.holo_blue_bright, com.ruanyun.chezhiyi.commonlib.R.color.holo_green_light,
                com.ruanyun.chezhiyi.commonlib.R.color.holo_orange_light, com.ruanyun.chezhiyi.commonlib.R.color.holo_red_light);
        refreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        if (workOrderStatusString.equals("day")) {
            huiYuanYingYeETongjiPresenter.getYingYeEInfo(app.getApiService().getMenDianYinYeE(app.getCurrentUserNum(), enddate, "", 0));
        } else {
            huiYuanYingYeETongjiPresenter.getYingYeEInfo(app.getApiService().getMenDianYinYeE(app.getCurrentUserNum(), startdate, enddate, 0));
        }

    }

    public void upDataUi(MenDianYinYeEInfo menDianYinYeEInfo) {
        tvBanka.setText("¥" + (menDianYinYeEInfo.getResult().get(0).getMemberAmount() + menDianYinYeEInfo.getResult().get(0).getSrxmCzAmount()));
        tvGongdanMoney.setText("¥" + menDianYinYeEInfo.getResult().get(0).getWorkAmount());
        tvShangcheng.setText("¥" + menDianYinYeEInfo.getResult().get(0).getSrxmXsAmount());
        tvRepay.setText("¥" + menDianYinYeEInfo.getResult().get(0).getSrxmTkAmount());
        tvHuiyuanreain.setText("¥" + menDianYinYeEInfo.getResult().get(0).getSrxmTkAmount());
        tvCashpay.setText("¥" + menDianYinYeEInfo.getResult().get(0).getSkfsXjAmount());
        tvWeixinpay.setText("¥" + menDianYinYeEInfo.getResult().get(0).getSkfsWxAmount());
        tvZhifubaopay.setText("¥" + menDianYinYeEInfo.getResult().get(0).getSkfsZfbAmount());
    }

    @Override
    public void getYinYeESuccess(MenDianYinYeEInfo menDianYinYeEInfo) {
        LogX.e("营业额persenter", menDianYinYeEInfo.toString());
//        app.loadingDialogHelper.dissMiss();
        if (refreshlayout.isRefreshing()) {
            refreshlayout.setRefreshing(false);
        }

        if (menDianYinYeEInfo != null) {
            upDataUi(menDianYinYeEInfo);
        }

    }

    @Override
    public void cancelYinYeETiChengErr() {
        refreshlayout.setRefreshing(false);
    }
}
