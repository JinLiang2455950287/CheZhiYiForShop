package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.ProductGroupPurchaseParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ProductListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 团购列表界面
 * Created by hdl on 2016/9/12
 */
public class GroupPurchaseActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener,
        ProductListAdapter.OnProductBuyClickListener
//        ,WebMvpView
{

    Topbar topbar;
    private ListView lvProduct;
    private ProductGroupPurchaseParams params;//传参
    private ProductListAdapter adapter;
    private List<ProductInfo> mProductInfos;
//    AllWebViewDetailPresenter presenter = new AllWebViewDetailPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
//        presenter.attachView(this);
        initRefreshLayout();
        initView();
        initData();
        setAdapter();
        refreshWithLoading();
    }

    private void initView() {
        lvProduct = (ListView) findViewById(R.id.list);
        topbar = (Topbar) findViewById(R.id.topbar);
        topbar.setTttleText("团购")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        params = new ProductGroupPurchaseParams();
        params.setNumPerPage(10);
        params.setTotalPage(10);
        params.setGoodsType(ProductGroupPurchaseParams.GOODS_TYPE_GROUP_PURCHASE);
    }

    private void setAdapter() {
        mProductInfos = new ArrayList<>();
        adapter = new ProductListAdapter(mContext, R.layout.list_item_product, mProductInfos);
        adapter.setClient(app.isClient());
        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductInfo item = mProductInfos.get(position);
                AppUtility.showGoodsWebView(item.getSalePrice(), app.getCurrentUserNum(), item
                        .getGoodsNum(), C.OrderType.ORDER_TYPE_TG, item.getGoodsNum(), app
                        .getCurrentUserNum(), mContext, item.getGoodsName(), item
                        .getProjectParent(), item.getMainPhoto(), item.getViceTitle() );
            }
        });
    }


    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        Call<ResultBase<PageInfoBase<ProductInfo>>> call = app.getApiService().getProductList(app.getCurrentUserNum()
                , params);
        return call;
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        mProductInfos = result.getResult();
        totalPage = result.getTotalPage();
        adapterUpData();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        mProductInfos.addAll(result.getResult());
        adapterUpData();
    }

    /**
     * 刷新Adapter
     */
    private void adapterUpData() {
        adapter.setData(mProductInfos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 点击购买回调
     * @param productInfo
     */
    @Override
    public void onProductBuyItemClick(ProductInfo productInfo) {
        productInfo.setGoodsType(C.OrderType.ORDER_TYPE_TG);
        AppUtility.toSubmit(mContext, productInfo);
//        Call<ResultBase<ProductInfo>> call = app.getApiService().productDetailInfo(app.getCurrentUserNum(),productInfo.getGoodsNum());
//        presenter.productInfo(call);
    }
//    @Override
//    public void showLoadingView() {
//        showLoading();
//    }
//
//    @Override
//    public void dismissLoadingView() {
//        dissMissLoading();
//    }
//
//    @Override
//    public void showTip(String msg) {
//        AppUtility.showToastMsg(msg);
//    }
//
//    @Override
//    public void getSeckillInfoSuccess(SeckillDetailInfo seckillDetailInfo) {
//
//    }
//
//    @Override
//    public void getCrowdInfoSuccess(CrowdFundingInfo crowdFundingInfo) {
//
//    }
//
//    @Override
//    public void getPromotionInfoSuccess(PromotionInfo promotionInfo) {
//
//    }
//
//    @Override
//    public void getProductInfoSuccess(ProductInfo productInfo) {
//        if(mContext!=null) {
//            AppUtility.toSubmit(mContext, productInfo);
//        }
//    }
//
//    @Override
//    public void getActivitySuccess(ResultBase<ActivityListInfo> resultBase) {
//
//    }
}
