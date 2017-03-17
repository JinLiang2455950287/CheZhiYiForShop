package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.params.DiscountCouponParams;
import com.ruanyun.chezhiyi.commonlib.presenter.DiscountCouponPresenter;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.DiscountCouponMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.czy.client.view.adapter.DiscountCouponAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Description:司机端优惠券fragment
 * Created by hdl on 2016/9/19.
 */
public class DiscountCouponFragment extends BaseFragment implements /*LazyFragmentPagerAdapter.Laziable,*/ DiscountCouponMvpView {

    protected RYEmptyView emptyView;
    private DiscountCouponParams params = new DiscountCouponParams();
    private BGARefreshLayout refreshLayout;
    private ListView list;
    private DiscountCouponAdapter adapter;
    private String couponType = "";
    private DiscountCouponPresenter presenter = new DiscountCouponPresenter();
    private List<OrderGoodsInfo> orderGoodsInfos = new ArrayList<>();
    private List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    private HashMap<String,String> stairprojectMap = new HashMap<>();//一级工单服务分类Map

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        couponType = getArguments().getString(C.IntentKey.DISCOUNT_COUPON_TYPE);
        mContentView = inflater.inflate(R.layout.fragment_article_common, container, false);
        isFirstIn = true;
        isPrepared = true;
        presenter.attachView(this);
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
        adapter = new DiscountCouponAdapter(mContext, R.layout.list_item_my_coupon, orderGoodsInfos,couponType,stairprojectMap);
        list.setAdapter(adapter);
        emptyView.bind(refreshLayout);
        emptyView.setOnReloadListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                lazyLoad();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        emptyView.showLoading();
        params.setGoodsType(DiscountCouponParams.ALL_YHQ);
        params.setGoodsDetailStatus(Integer.parseInt(couponType));
        presenter.setDiscountCouponMvpView(app.getApiService().getClientDiscountCoupon(app.getCurrentUserNum(),params));
    }


    @Override
    public void showLoadingView() {
        emptyView.showLoading();
    }

    @Override
    public void dismissLoadingView() {
//            emptyView.loadSuccuss();
    }

    @Override
    public void showDiscountCouponErrer(String msg) {
        emptyView.showLoadFail();
    }

    @Override
    public void showDiscountCouponSuccess(ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase) {
        orderGoodsInfos = orderGoodsInfoResultBase.getObj();
        if(orderGoodsInfos==null||orderGoodsInfos.size()==0){
            emptyView.showEmpty();
            return;
        }
        emptyView.loadSuccuss();

        adapter.setDatas(orderGoodsInfos);
        String discountCouponNumber = orderGoodsInfos.size()+"";
        EventBus.getDefault().post(new Event<String[]>(C.EventKey.DISCOUNT_COUPON_NUMBER,
                new String[]{couponType,discountCouponNumber}));
    }

    @Override
    public void showTechnicianDiscountCouponSuccess(ResultBase<List<ProductInfo>> productInfoResultBase) {
        //不使用(技师端使用)
    }
}
