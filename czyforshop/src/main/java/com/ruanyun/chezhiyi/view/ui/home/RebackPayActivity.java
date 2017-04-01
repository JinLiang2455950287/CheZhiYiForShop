package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.TuiKuanInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.RePayPresenter;
import com.ruanyun.chezhiyi.commonlib.view.RePayMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.RebackPayListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Description ：退款
 * <p/>
 * Created by ycw on 2017/3/9.
 */
public class RebackPayActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, RebackPayListAdapter.OnProductBuyClickListener,
        BGARefreshLayout.BGARefreshLayoutDelegate, RePayMvpView {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    private RebackPayListAdapter adapter;
    private List<TuiKuanInfo> productInfos;
    private RePayPresenter payPresenter = new RePayPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reback_pay);
        ButterKnife.bind(this);
        initRefreshLayout(mRefreshLayout);
        initView();
        setAdapter();
    }

    private void initView() {
        productInfos = new ArrayList<>();
        payPresenter.attachView(this);
        emptyview.bind(mRefreshLayout);
        emptyview.showLoading();
        topbar.setTttleText("退款审核")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        payPresenter.getRePayData(app.getApiService().getTuiKuanList(app.getCurrentUserNum()));
    }

    private void setAdapter() {
        adapter = new RebackPayListAdapter(mContext, R.layout.list_item_repay_product, productInfos);
        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, RebackPayDealActivity.class);
                intent.putExtra("refundNum", productInfos.get(position).getRefundApplicationNum());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 点击通过回调
     */
    @Override
    public void onProductBuyItemClick(TuiKuanInfo tuiKuanInfo) {
//        Toast.makeText(mContext, "通过", Toast.LENGTH_SHORT).show();
    }

    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        // 在这里加载最新数据

        if (true) {
            // 如果网络可用，则加载网络数据
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    // 加载完毕后在 UI 线程结束下拉刷新
                    mRefreshLayout.endRefreshing();
//                    mDatas.addAll(0, DataEngine.loadNewData());
//                    mAdapter.setDatas(mDatas);
                }
            }.execute();
        } else {
            // 网络不可用，结束下拉刷新
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            mRefreshLayout.endRefreshing();
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
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在 activity 的 onStart 方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payPresenter.detachView();
    }

    @Override
    public void getDataSuccess(List<TuiKuanInfo> listinfo) {
        productInfos.clear();
        for (TuiKuanInfo info : listinfo) {
            productInfos.add(info);
        }
        adapter.setData(productInfos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoadingView() {
        emptyview.loadSuccuss();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void cancelSuccess() {

    }

}
