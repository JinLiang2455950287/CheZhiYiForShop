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
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CardPackageListModel;
import com.ruanyun.chezhiyi.commonlib.presenter.CardPackagePresenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.CardPackageView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CardPackageListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import retrofit2.Call;

/**
 * 我的产品-->卡套餐
 * Created by jl on 2017/3/21
 */


//activity_card_package
public class CardPackageActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, BGARefreshLayout.BGARefreshLayoutDelegate,
        CardPackageListAdapter.OnProductBuyClickListener, CardPackageView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    private CardPackageListAdapter adapter;
    private List<CardPackageListModel> cardPackageListModels;
    private CardPackagePresenter cardPackagePresenter = new CardPackagePresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_card_package);
        ButterKnife.bind(this);
        initView();
        setAdapter();
    }

    private void initView() {
        initRefreshLayout(mRefreshLayout);
        cardPackagePresenter.attachView(this);
        topbar.setTttleText("卡套餐")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        cardPackageListModels = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        emptyview.showLoading();
        LogX.e("卡套餐", "showLoading");

        cardPackagePresenter.getCardPackageList(app.getApiService().getCardPackegList(app.getCurrentUserNum()));
    }

    private void setAdapter() {
        adapter = new CardPackageListAdapter(mContext, R.layout.list_item_cardpackage, cardPackageListModels);
        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, CardPackageDetailActivity.class);
                intent.putExtra("packageNumber", cardPackageListModels.get(position).getPackageNum());
                startActivity(intent);
            }
        });
    }

    /**
     * 刷新Adapter
     */
    private void adapterUpData(List<CardPackageListModel> listinfo) {
        adapter.setData(listinfo);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardPackagePresenter.detachView();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }


    private void initRefreshLayout(BGARefreshLayout refreshLayout) {
        emptyview.bind(mRefreshLayout);
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
        emptyview.showLoading();
        cardPackagePresenter.getCardPackageList(app.getApiService().getCardPackegList(app.getCurrentUserNum()));

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
    public void getDataSuccess(List<CardPackageListModel> listinfo) {
        LogX.e("卡套餐", listinfo.toString());
        cardPackageListModels = listinfo;
        adapterUpData(listinfo);
        if (emptyview.isActivated()) {
            emptyview.loadSuccuss();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRefreshLayout.endRefreshing();

    }

    @Override
    public void dismissLoadingView() {
        emptyview.loadSuccuss();
    }

    @Override
    public void showLoadingView() {
        emptyview.showLoading();
    }

    @Override
    public void cancelSuccess() {
        emptyview.loadSuccuss();
    }

    /**
     * 点击购买回调
     *
     * @param cardPackageListModel
     */
    @Override
    public void onProductBuyItemClick(CardPackageListModel cardPackageListModel) {
        Intent intent = new Intent(mContext, CardPackageDetailActivity.class);
        intent.putExtra("packageNumber", cardPackageListModel.getPackageNum());
        startActivity(intent);
    }
}
