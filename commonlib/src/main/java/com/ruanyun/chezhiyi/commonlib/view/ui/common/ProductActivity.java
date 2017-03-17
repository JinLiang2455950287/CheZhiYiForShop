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
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ProductListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 产品列表界面
 * Created by hdl on 2016/9/12
 */
public class ProductActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener,
        ProductListAdapter.OnProductBuyClickListener {

    private Topbar topbar;
    private ListView lvProduct;
    private ProductGroupPurchaseParams params;//传参
    private ProductListAdapter adapter;
    private List<ProductInfo> productInfos;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        initRefreshLayout();
        initView();
        initData();
        setAdapter();
        refreshWithLoading();
    }

    private void initView() {
        lvProduct = (ListView) findViewById(R.id.list);
        topbar = (Topbar) findViewById(R.id.topbar);
        topbar.setTttleText("产品")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        params = new ProductGroupPurchaseParams();
        params.setNumPerPage(10);
        params.setTotalPage(10);
        params.setGoodsType(ProductGroupPurchaseParams.GOODS_TYPE_PRODUCT);
    }

    private void setAdapter() {
        productInfos = new ArrayList<>();
        adapter = new ProductListAdapter(mContext, R.layout.list_item_product, productInfos);
        adapter.setClient(app.isClient());
        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductInfo item = productInfos.get(position);
                AppUtility.showGoodsWebView(item.getSalePrice(), app.getCurrentUserNum(), item
                        .getGoodsNum(), C.OrderType.ORDER_TYPE_CP, item.getGoodsNum(), app
                        .getCurrentUserNum(), mContext, item.getGoodsName(), item
                        .getProjectParent(), item.getMainPhoto(), item.getViceTitle());
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
        productInfos = setProductInfosType(result.getResult());
        totalPage = result.getTotalPage();
        LogX.e("====onRefreshResultonRefreshResult", productInfos.toString());
        adapterUpData();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        LogX.e("====onLoadMoreResultonRefreshResult", productInfos.toString());
        productInfos.addAll(setProductInfosType(result.getResult()));
        adapterUpData();
    }


    public List<ProductInfo> setProductInfosType(List<ProductInfo> productInfoList) {
        for (ProductInfo info : productInfoList) {
            info.setGoodsType(AppUtility.getTypeName(info.getGoodsType()));
        }
        return productInfoList;
    }

    /**
     * 刷新Adapter
     */
    private void adapterUpData() {
        adapter.setData(productInfos);
        adapter.notifyDataSetChanged();
    }

    /**
     * topbar的监听
     */
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
    public void onProductBuyItemClick(ProductInfo productInfo) {
        productInfo.setGoodsType(C.OrderType.ORDER_TYPE_CP);
        AppUtility.toSubmit(mContext, productInfo);
    }

}
