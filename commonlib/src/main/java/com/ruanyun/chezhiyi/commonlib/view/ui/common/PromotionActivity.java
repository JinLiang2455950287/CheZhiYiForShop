package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.GetPromotionParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.adapter.PromotionListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 限时促销列表界面
 * Created by hdl on 2016/9/12
 */
public class PromotionActivity
        extends RefreshBaseActivity
        implements Topbar.onTopbarClickListener,
        PromotionListAdapter.OnPromotionBuyClickListener {

    Topbar topbar;
    private ListView lvProduct;
    private GetPromotionParams params;//传参
    private PromotionListAdapter adapter;
    private List<PromotionInfo> promotionInfos;

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
        topbar = getView(R.id.topbar);
        topbar.setTttleText("限时促销")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        params = new GetPromotionParams();
        params.setNumPerPage(10);
        params.setTotalPage(10);
    }

    private void setAdapter() {
        promotionInfos = new ArrayList<>();
        adapter = new PromotionListAdapter(mContext, R.layout.list_item_product, promotionInfos);
        adapter.setClient(app.isClient());
        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PromotionInfo item = promotionInfos.get(position);
                AppUtility.showGoodsWebView(item.getActivityPrice(), app.getCurrentUserNum(),
                        item.getGoodsNum(), C.OrderType.ORDER_TYPE_CX, item.getPromotionInfoNum()
                        , app.getCurrentUserNum(), mContext, item.getTitle(), item.getProjectNum
                                (), item.getMainPhoto(), item.getViceTitle() );
            }
        });
    }


    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        return app.getApiService().getPromotionList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        promotionInfos = result.getResult();
        totalPage = result.getTotalPage();
        adapterUpData();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        promotionInfos.addAll(result.getResult());
        adapterUpData();
    }

    /**
     * 刷新Adapter
     */
    private void adapterUpData() {
        adapter.setData(promotionInfos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 点击购买回调
     */
    @Override
    public void onPromotionBuyItemClick(PromotionInfo promotionInfo) {
        promotionInfo.setGoodsType(C.OrderType.ORDER_TYPE_CX);
        AppUtility.toSubmit(mContext, promotionInfo);

    }
}
