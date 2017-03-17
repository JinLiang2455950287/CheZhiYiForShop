package com.ruanyun.chezhiyi.view.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MyWorkOrderParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderDetailedActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.chezhiyi.view.adapter.InsteadOrderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Description ：代下单管理 Fragment
 * <p/>
 * Created by hdl on 2016/10/8.
 */
public class InsteadOrderFragment extends RefreshBaseFragment implements LazyFragmentPagerAdapter.Laziable {

    MyWorkOrderParams params = new MyWorkOrderParams();
    ListView list;
    InsteadOrderAdapter adapter;
    String insteadOrderType = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        insteadOrderType = getArguments().getString(C.IntentKey.INSTEAD_ORDER_TYPE);
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
        adapter = new InsteadOrderAdapter(mContext, new ArrayList<OrderGoodsInfo>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                OrderGoodsInfo goodsInfo = adapter.getItem(position);
                if (goodsInfo.isParent()) {   // 跳转到工单详情
                    WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                    workOrderInfo.setWorkOrderNum(goodsInfo.getWorkOrderNum());
                    Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                    intent.putExtra(C.IntentKey.WORKORDER_INFO, workOrderInfo);
                    showActivity(intent);
                } else {  // 商品详情
                    AppUtility.showGoodsWebView(goodsInfo.getSinglePrice(),
                            app.getCurrentUserNum(),
                            goodsInfo.getGoodsNum(),
                            C.OrderType.ORDER_TYPE_CP,
                            goodsInfo.getGoodsNum(),
                            app.getCurrentUserNum(),
                            mContext,
                            goodsInfo.getGoodsName(),
                            goodsInfo.getProjectParent(),
                            goodsInfo.getMainPhoto(),
                            goodsInfo.getViceTitle() );
                }
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
        if (InsteadOrderActivity.END.equals(insteadOrderType)) {
            params.setWorkOrderStatus(Integer.parseInt(insteadOrderType));
        } else if (InsteadOrderActivity.UNDERWAY.equals(insteadOrderType)) {
            params.setWorkOrderStatusString(insteadOrderType);
        }
        params.setLeadingUserNum(app.getCurrentUserNum());
        params.setType("1");//代下单管理
        return app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        totalPage = result.getTotalPage();
        adapter.refresh(getDatas(result.getResult()));
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.loadMore(getDatas(result.getResult()));
    }

    /**
     * 数据组装
     * @param result
     */
    private List<OrderGoodsInfo> getDatas(List<WorkOrderInfo> result) {
        List<OrderGoodsInfo> datas = new ArrayList<>();
        OrderGoodsInfo Info;
        for (WorkOrderInfo workOrderInfo : result) {
            Info = new OrderGoodsInfo();
            Info.setWorkOrderNum(workOrderInfo.getWorkOrderNum());
            Info.setGoodsNum(workOrderInfo.getWorkOrderStatus()+"");
            Info.setParent(true);
            datas.add(Info);
            List<OrderGoodsInfo> workOrderGoodsList = workOrderInfo.getWorkOrderGoodsList();
            if (workOrderGoodsList != null)
                for (int i = 0; i < workOrderGoodsList.size(); i++) {
                    OrderGoodsInfo info = workOrderGoodsList.get(i);
                    if (info.getIsDaiXiaDan() == 1) {
                        datas.add(info);
                    }
                }
        }
        return datas;
    }



}
