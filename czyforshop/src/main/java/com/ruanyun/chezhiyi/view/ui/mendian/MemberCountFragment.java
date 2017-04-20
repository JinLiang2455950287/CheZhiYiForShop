package com.ruanyun.chezhiyi.view.ui.mendian;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.MenDianHuiYuanInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.HuiYuanTongjiPresenter;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanTongJiView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * jin
 * 2017/4/12
 * 门店会员统计
 */
public class MemberCountFragment extends BaseFragment implements HuiYuanTongJiView, SwipeRefreshLayout.OnRefreshListener {

    private TextView tvChongCount, tvChongMoney, tvReaminConsume, tvRemainmoney, tvBanka, tvBankaMoney;
    private SwipeRefreshLayout refreshlayout;
    private HuiYuanTongjiPresenter huiYuanTongjiPresenter = new HuiYuanTongjiPresenter();
    private SimpleDateFormat sDateFormatend = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
    private String startdate, enddate;
    private String workOrderStatusString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_member_count, container, false);
        tvChongCount = (TextView) mContentView.findViewById(R.id.tv_chongCount);

        tvChongMoney = (TextView) mContentView.findViewById(R.id.tv_chongMoney);
        tvReaminConsume = (TextView) mContentView.findViewById(R.id.tv_reaminConsume);
        tvRemainmoney = (TextView) mContentView.findViewById(R.id.tv_remainmoney);
        tvBanka = (TextView) mContentView.findViewById(R.id.tv_banka);
        tvBankaMoney = (TextView) mContentView.findViewById(R.id.tv_bankaMoney);


        workOrderStatusString = getArguments().getString(C.IntentKey.WORK_ORDER_STATUS_STRING);
        initRefreshView();
        startdate = sDateFormat.format(new Date()) + "-01";
        enddate = sDateFormatend.format(new Date());
        LogX.e("日期", startdate + ";" + enddate);
        huiYuanTongjiPresenter.attachView(this);
        if (workOrderStatusString.equals("day")) {
            huiYuanTongjiPresenter.getTongJiInfo(app.getApiService().getMenDianHuiYuan(app.getCurrentUserNum(), enddate, ""));
        } else {
            huiYuanTongjiPresenter.getTongJiInfo(app.getApiService().getMenDianHuiYuan(app.getCurrentUserNum(), startdate, enddate));
        }
        return mContentView;
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
        huiYuanTongjiPresenter.detachView();
    }

    @Override
    public void onRefresh() {
        if (workOrderStatusString.equals("day")) {
            huiYuanTongjiPresenter.getTongJiInfo(app.getApiService().getMenDianHuiYuan(app.getCurrentUserNum(), enddate, ""));
        } else {
            huiYuanTongjiPresenter.getTongJiInfo(app.getApiService().getMenDianHuiYuan(app.getCurrentUserNum(), startdate, enddate));
        }
    }

    public void upDateUi(MenDianHuiYuanInfo menDianHuiYuanInfo) {
//        ¥
        tvChongCount.setText(menDianHuiYuanInfo.getUserCenterCount() + "次");
        tvChongMoney.setText("¥" + menDianHuiYuanInfo.getUserCenterAmount());
        tvReaminConsume.setText("¥" + menDianHuiYuanInfo.getUserCardAmount());
        tvRemainmoney.setText("¥" + menDianHuiYuanInfo.getAccountBalance() + "");
        tvBanka.setText(menDianHuiYuanInfo.getMemberCardCount() + "次");
        tvBankaMoney.setText("¥" + menDianHuiYuanInfo.getMemberCardAmount() + "");
    }

    @Override
    public void getHSuccess(MenDianHuiYuanInfo menDianHuiYuanInfo) {
        LogX.e("会员getHSuccess", menDianHuiYuanInfo.toString());
        refreshlayout.setRefreshing(false);
        if (menDianHuiYuanInfo != null) {
            upDateUi(menDianHuiYuanInfo);
        }
    }

    @Override
    public void cancelTiChengErr() {

    }
}
