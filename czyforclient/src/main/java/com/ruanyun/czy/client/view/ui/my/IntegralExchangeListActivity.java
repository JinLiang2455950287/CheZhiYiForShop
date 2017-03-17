package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.RecordListInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.RecordListParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.IntegralExchangeListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 积分兑换明细   账户余额明细
 * Created by wp on 2016/10/13.
 */
public class IntegralExchangeListActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView list;
    public static final int RECHARGE_TYPE = 1;
    public static final int EXCHANGE_TYPE = 5;//1账户余额 2支付宝 3微信 4到店支付 5积分账户
    RecordListParams params = new RecordListParams();
    IntegralExchangeListAdapter adapter;
    private int type ;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_integral_exchange_list);
        ButterKnife.bind(this);
        initView();
        refreshLayout.beginRefreshing();
    }

    private void initView() {
        topbar.setTttleText("明细").onBackBtnClick()
                .setBackBtnEnable(true).setTopbarClickListener(this);
        initRefreshLayout();
        type=getIntent().getIntExtra(C.IntentKey.ACCOUNT_TYPE,-1);
        adapter = new IntegralExchangeListAdapter(mContext, new ArrayList<RecordListInfo>(), R.layout.list_item_exchange_details, type);
        list.setAdapter(adapter);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 获取积分兑换明细
     * @return
     */
    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        params.setAccountType(type);// 1账户余额 2支付宝 3微信 4到店支付 5积分账户
        return app.getApiService().recordListinfo(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
    }
}
