package com.ruanyun.czy.client.view.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.CrowdFundingInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.CrowdFundingParams;
import com.ruanyun.chezhiyi.commonlib.presenter.CrowdFundingOrderInfoPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CFOrderInfoMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.MyCrowdFundingAdapter;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:我的众筹列表通用fragment
 * Created by hdl on 2016/9/19.
 */
public class MyCrowdFundingFragment extends RefreshBaseFragment implements LazyFragmentPagerAdapter.Laziable, CFOrderInfoMvpView {

    private CrowdFundingParams params = new CrowdFundingParams();
    ListView list;
    MyCrowdFundingAdapter adapter;
    String myCrowdFundingType = "";
    List<CrowdFundingInfo> crowdFundingInfos = new ArrayList<>();
    CrowdFundingOrderInfoPresenter presenter = new CrowdFundingOrderInfoPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myCrowdFundingType = getArguments().getString(C.IntentKey.MY_CROWD_FUNDING_TYPE);
        mContentView = inflater.inflate(R.layout.fragment_article_common, container, false);
        isFirstIn = true;
        isPrepared = true;
        initRefreshLayout();
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        lazyLoad();
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(refreshLayout!=null){
            refreshLayout.endRefreshing();
        }
    }

    private void initView() {
        list = getView(R.id.list);
        adapter = new MyCrowdFundingAdapter(mContext, R.layout.item_my_crowd_funding,
                crowdFundingInfos, myCrowdFundingType);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                CrowdFundingInfo item = crowdFundingInfos.get(position);
                //我的众筹的订单详情
                presenter.getOrderDetail(item.getOrderNum());
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        params.setType("2");
        if (CrowdFundingInfo.UNDERWAY.equals(myCrowdFundingType)){
            params.setStatus(Integer.parseInt(myCrowdFundingType));
        }else {
            params.setStatusString(myCrowdFundingType);
        }
        return app.getApiService().getCrowdFundingList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        totalPage = result.getTotalPage();
        crowdFundingInfos = result.getResult();
        adapter.setData(crowdFundingInfos);
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        crowdFundingInfos.addAll(result.getResult());
        adapter.setData(crowdFundingInfos);
    }


    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void orderDetailResult(OrderInfo orderInfo) {
        Intent intent=new Intent(mContext, OrderDetailedActivity.class);
        intent.putExtra(C.IntentKey.ORDER_INFO,orderInfo);
        showActivity(intent);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }
}
