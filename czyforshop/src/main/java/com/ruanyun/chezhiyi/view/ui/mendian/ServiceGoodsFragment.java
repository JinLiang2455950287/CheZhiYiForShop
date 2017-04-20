package com.ruanyun.chezhiyi.view.ui.mendian;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.MenDianServiceGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.HuiYuanServicePresenter;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanServiceGoodTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MenDianGoodsListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 2017/4/12
 * jin
 * 服务商品统计 当日/当月
 */
public class ServiceGoodsFragment extends BaseFragment implements HuiYuanServiceGoodTongJiView, BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView lvProduct;
    private BGARefreshLayout mRefreshLayout;
    private RYEmptyView emptyview;
    private MenDianGoodsListAdapter adapter;
    private List<MenDianServiceGoodsInfo.ResultBean> listData;
    private HuiYuanServicePresenter huiYuanServicePresenter = new HuiYuanServicePresenter();
    private SimpleDateFormat sDateFormatend = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
    private String startdate, enddate;
    private String workOrderStatusString;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_service_goods, container, false);
        workOrderStatusString = getArguments().getString(C.IntentKey.WORK_ORDER_STATUS_STRING);
        initView();
        return mContentView;
    }

    private void initView() {
        huiYuanServicePresenter.attachView(this);
        lvProduct = (ListView) mContentView.findViewById(R.id.list);
        mRefreshLayout = (BGARefreshLayout) mContentView.findViewById(R.id.refreshlayout);
        emptyview = (RYEmptyView) mContentView.findViewById(R.id.emptyview);
        initRefreshLayout(mRefreshLayout);
        emptyview.bind(mRefreshLayout);
        listData = new ArrayList<>();
        setAdapter();
        startdate = sDateFormat.format(new Date()) + "-01";
        enddate = sDateFormatend.format(new Date());
        LogX.e("日期", startdate + ";" + enddate);
        if (workOrderStatusString.equals("day")) {
            huiYuanServicePresenter.getServiceGoodsInfo(app.getApiService().getServiceGoods(app.getCurrentUserNum(), enddate, ""));
        } else {
            huiYuanServicePresenter.getServiceGoodsInfo(app.getApiService().getServiceGoods(app.getCurrentUserNum(), startdate, enddate));
        }
    }

    private void setAdapter() {
        adapter = new MenDianGoodsListAdapter(mContext, R.layout.list_item_gongdan_servicegoods, listData);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据
        if (workOrderStatusString.equals("day")) {
            huiYuanServicePresenter.getServiceGoodsInfo(app.getApiService().getServiceGoods(app.getCurrentUserNum(), enddate, ""));
        } else {
            huiYuanServicePresenter.getServiceGoodsInfo(app.getApiService().getServiceGoods(app.getCurrentUserNum(), startdate, enddate));
        }

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        if (true) {
            // 如果网络可用，则异步加载网络数据，并返回 true，显示正在加载更多
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // 加载完毕后在 UI 线程结束加载更多
                    mRefreshLayout.endLoadingMore();
//                    mAdapter.addDatas(DataEngine.loadMoreData());
                }
            }.execute();

            return true;
        } else {
            // 网络不可用，返回 false，不显示正在加载更多
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            return false;

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        huiYuanServicePresenter.detachView();
    }

    @Override
    public void getServiceGoodsSuccess(MenDianServiceGoodsInfo resultBase) {
        mRefreshLayout.endRefreshing();
        LogX.e("服务商品Activity", resultBase.getResult().toString());
        if (resultBase.getResult().size() > 0) {
            listData.clear();
            listData = resultBase.getResult();
            adapter.setData(listData);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancelServiceGoodsTiChengErr() {

    }
}
