package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.CrowdFundingInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.CrowdFundingParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CrowdFundingListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CrowdFundingListAdapter.OnCrowdFundingClickListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * 众筹列表界面
 * Created by hdl on 2016/9/12
 */
public class CrowdFundingActivity extends RefreshBaseActivity
        implements Topbar.onTopbarClickListener,
        OnCrowdFundingClickListener {

    private Topbar topbar;
    private ListView lvProduct;
    private CrowdFundingParams params = new CrowdFundingParams();//传参
    private CrowdFundingListAdapter adapter;
    private List<CrowdFundingInfo> crowdFundingInfos;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_product_list);
        initRefreshLayout();
        initView();
        initData();
        setAdapter();
        refreshWithLoading();
    }

    private void initView() {
        lvProduct = getView(R.id.list);
        topbar = (Topbar) findViewById(R.id.topbar);
        topbar.setTttleText("众筹")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        params.setStatus(3);
        params.setType("1");
    }

    private void setAdapter() {
        crowdFundingInfos = new ArrayList<>();
        adapter = new CrowdFundingListAdapter(mContext, R.layout.list_item_product, crowdFundingInfos);
        adapter.setClient(app.isClient());
        adapter.setOnCrowdFundingClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CrowdFundingInfo item = crowdFundingInfos.get(position);
                AppUtility.showGoodsWebView(item.getActivityPrice(),
                        app.getCurrentUserNum(),
                        item.getGoodsNum(),
                        C.OrderType.ORDER_TYPE_ZC,
                        item.getCrowdNum(),
                        app.getCurrentUserNum(),
                        mContext,
                        item.getTitle(),
                        item.getProjectNum(),
                        item.getMainPhoto(),
                        item.getResidueDay() > 0 ? "1" : "2",
                        item.getViceTitle() );
            }
        });
    }

    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        return app.getApiService().getCrowdFundingList(app.getCurrentUserNum(), params);
    }

    /**
     * 下拉刷新成功
     *
     * @param result
     * @param tag
     */
    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        totalPage = result.getTotalPage();
        crowdFundingInfos  = adapterUpData(result.getResult());
        adapter.setData(crowdFundingInfos);
        adapter.notifyDataSetChanged();
    }

    /**
     * 加载更多
     *
     * @param result
     * @param tag
     */
    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        crowdFundingInfos.addAll(adapterUpData(result.getResult()));
        adapter.setData(crowdFundingInfos);
        adapter.notifyDataSetChanged();
    }

    /**
     * 刷新Adapter
     * @param result
     */
    private List<CrowdFundingInfo> adapterUpData(List<CrowdFundingInfo> result) {
        if (result == null) return new ArrayList<>();
        int residueDay;
        double progress;
        for (int i = 0; i < result.size(); i++) {
            residueDay = 0;
            progress = 0;
            CrowdFundingInfo fundingInfo = result.get(i);
            if (fundingInfo.getEndTime() != null && fundingInfo.getEndTime().length() > 0) {
                residueDay = adapter.residueDay(fundingInfo.getEndTime());//众筹剩余时间
            }
            fundingInfo.setResidueDay(residueDay);
            if (fundingInfo.getFullNum() > 0) {
                progress = fundingInfo.getPartNum() * 100f / fundingInfo.getFullNum();
            }
            fundingInfo.setProgress(progress);
        }
        return result;
    }

    /**
     * topbar监听
     *
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 列表监听
     *
     * @param crowdFundingInfo
     */
    @Override
    public void OnCrowdFundingItemClick(CrowdFundingInfo crowdFundingInfo) {
        crowdFundingInfo.setGoodsType(C.OrderType.ORDER_TYPE_ZC);
        AppUtility.toSubmit(mContext, crowdFundingInfo);
    }
}
