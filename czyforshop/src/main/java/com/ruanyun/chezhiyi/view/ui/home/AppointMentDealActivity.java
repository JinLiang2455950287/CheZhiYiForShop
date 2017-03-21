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
import com.ruanyun.chezhiyi.commonlib.model.YuYueItemBean;
import com.ruanyun.chezhiyi.commonlib.presenter.AppointMentPresenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.AppointMentMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.YuyueListAdapter;
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
 * Description ：预约处理
 * <p/>
 * Created by hdl on 2017/3/9.
 */
public class AppointMentDealActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, YuyueListAdapter.OnProductBuyClickListener,
        BGARefreshLayout.BGARefreshLayoutDelegate, AppointMentMvpView {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    private YuyueListAdapter adapter;
    private List<YuYueItemBean> productInfos;
    private String yuYueItemBeanString = "null";
    private AppointMentPresenter appointMentPresenter = new AppointMentPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_ment_deal);
        ButterKnife.bind(this);
        initView();
        initRefreshLayout(mRefreshLayout);
        setAdapter();

    }

    private void initView() {
        emptyview.bind(mRefreshLayout);
        productInfos = new ArrayList<>();
        appointMentPresenter.attachView(this);
        topbar.setTttleText("预约处理")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

    }

    private void setAdapter() {
        adapter = new YuyueListAdapter(mContext, R.layout.list_item_yuyue_product, productInfos);
        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "jfeif", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setProductInfosType(List<YuYueItemBean> productInfoList) {
        productInfos.clear();
        for (YuYueItemBean info : productInfoList) {
            productInfos.add(info);
        }
        adapterUpData(productInfos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        emptyview.showLoading();
        appointMentPresenter.getYuYueData(app.getApiService().getYuYueList(app.getCurrentUserNum()));
    }

    /**
     * 刷新Adapter
     */
    private void adapterUpData(List<YuYueItemBean> productInfos) {
        adapter.setData(productInfos);
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
    public void onProductBuyItemClick(YuYueItemBean yuYueItemBean) {
        Intent intent = new Intent(mContext, AppointMentDealDetailActivity.class);
        intent.putExtra("MakeNum", yuYueItemBean.getMakeNum());
        intent.putExtra("crateTime", yuYueItemBean.getCreateTime());
        intent.putExtra("project", yuYueItemBean.getProjectNum());
        startActivity(intent);
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

    @Override
    public void getDataSuccess(List<YuYueItemBean> listinfo, String listinfoString) {
        yuYueItemBeanString = listinfoString;
        setProductInfosType(listinfo);
    }

    @Override
    public void dismissLoadingView() {
        emptyview.loadSuccuss();
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void cancelSuccess() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appointMentPresenter.detachView();
    }
}