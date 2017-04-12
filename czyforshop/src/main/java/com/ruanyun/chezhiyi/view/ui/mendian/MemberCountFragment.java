package com.ruanyun.chezhiyi.view.ui.mendian;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.util.C;

import butterknife.ButterKnife;

/**
 * jin
 * 2017/4/12
 * 门店会员统计
 */
public class MemberCountFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshlayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_member_count, container, false);
        String workOrderStatusString = getArguments().getString(C.IntentKey.WORK_ORDER_STATUS_STRING);
        initRefreshView();
        ButterKnife.bind(this, mContentView);
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

    }

    @Override
    public void onRefresh() {
        try {
            Thread.sleep(3000);
            if (refreshlayout != null) {
                refreshlayout.setRefreshing(false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
