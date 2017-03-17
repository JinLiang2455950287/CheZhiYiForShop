package com.ruanyun.chezhiyi.view.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.presenter.DiscountCouponPresenter;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.DiscountCouponMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.view.adapter.DiscountCouponAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


/**
 * Description:技师端优惠券fragment
 * Created by hdl on 2016/9/19.
 */
public class DiscountCouponFragment extends BaseFragment implements /*LazyFragmentPagerAdapter.Laziable,*/ DiscountCouponMvpView {

    protected RYEmptyView emptyView;
    BGARefreshLayout refreshLayout;
    ListView list;
    DiscountCouponAdapter adapter;
    String couponType = "";
    DiscountCouponPresenter presenter = new DiscountCouponPresenter();
    List<ProductInfo> mProductList = new ArrayList<>();
    List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    HashMap<String,String> stairprojectMap = new HashMap<>();//一级工单服务分类Map

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        couponType = getArguments().getString(C.IntentKey.DISCOUNT_COUPON_TYPE);
        mContentView = inflater.inflate(R.layout.fragment_article_common, container, false);
        isFirstIn = true;
        isPrepared = true;
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        lazyLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unRegisterBus();
    }

    /**
     *  刷新数据
     *  SendCouponActivity 240
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void upDiscountCoupon(Event<String> event) {
        if (C.EventKey.UPDATE_USER_INFO.equals(event.key)) {
            if("refresh".equals(event.value)){
                lazyLoad();
            }
        }
    }

    private void initView() {
        list = getView(R.id.list);
        emptyView = getView(R.id.emptyview);
        refreshLayout = getView(R.id.refreshlayout);
        stairprojectTypes = new ArrayList<>();
        stairprojectTypes = DbHelper.getInstance().getSeviceTypes();//获取工单服务和技师技能数据结构集合
        for (ProjectType stairprojectType : stairprojectTypes) {
            stairprojectMap.put(stairprojectType.getProjectNum(),stairprojectType.getProjectName());
        }

        adapter = new DiscountCouponAdapter(mContext, R.layout.list_item_my_coupon, mProductList,couponType,stairprojectMap);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(DiscountCouponActivity.UNUSED.equals(couponType)){
                    Intent intent = new Intent(mContext, SendCouponActivity.class);
                    intent.putExtra(C.IntentKey.PRODUCTINFO_INFO,adapter.getItem(position));
                    showActivity(intent);
                } else if (DiscountCouponActivity.SEND.equals(couponType)){ // 已送出
                    Intent intent = new Intent(mContext, CouponUserListActivity.class);
                    intent.putExtra(C.IntentKey.PRODUCTINFO_INFO, adapter.getItem(position));
                    showActivity(intent);
                }
            }
        });

        emptyView.bind(refreshLayout);
        emptyView.setOnReloadListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    lazyLoad();
                }
            });
    }

    /**
     * 请求
     */
    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        emptyView.showLoading();
        presenter.setTechnicianDiscountCouponMvpView(app.getApiService().getTechnicianDiscountCoupon(app.getCurrentUserNum(),couponType));
    }


    @Override
    public void showLoadingView() {
            emptyView.showLoading();
    }

    @Override
    public void dismissLoadingView() {
//        emptyView.loadSuccuss();
    }

    @Override
    public void showDiscountCouponErrer(String msg) {
            emptyView.showLoadFail();
    }

    @Override
    public void showDiscountCouponSuccess(ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase) {
        //不使用(司机端使用)
    }

    /**
     * 获取优惠券成功
     * @param productInfoResultBase
     */
    @Override
    public void showTechnicianDiscountCouponSuccess(ResultBase<List<ProductInfo>> productInfoResultBase) {
        mProductList = productInfoResultBase.getObj();

        if(mProductList==null||mProductList.size()==0){
            emptyView.showEmpty();
            return;
        }

        emptyView.loadSuccuss();
        adapter.setDatas(mProductList);

        String discountCouponNumber = mProductList.size()+"";
        EventBus.getDefault().post(new Event<String[]>(C.EventKey.DISCOUNT_COUPON_NUMBER,
                new String[]{couponType,discountCouponNumber}));
    }

}
