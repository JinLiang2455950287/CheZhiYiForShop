package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.HuiYuanKuaiChaInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MemberParams;
import com.ruanyun.chezhiyi.commonlib.presenter.MemberSearchPresenter;
import com.ruanyun.chezhiyi.commonlib.util.CloseKeyBoard;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.MemberSearchMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.HuiYuanListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

import static com.ruanyun.chezhiyi.R.id.imb_search;

/**
 * Description ：会员查询
 * <p/>
 * Created by ycw on 2017/3/9.
 */
public class MemberSearchActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, BGARefreshLayout.BGARefreshLayoutDelegate
        , HuiYuanListAdapter.OnProductBuyClickListener, MemberSearchMvpView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(imb_search)
    ImageButton imbSearch;
    private HuiYuanListAdapter adapter;
    private List<HuiYuanKuaiChaInfo> listinfo;
    private MemberParams params;

    private MemberSearchPresenter memberSearchPresenter = new MemberSearchPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_search);
        ButterKnife.bind(this);
        initView();
        setAdapter();
        initRefreshLayout(mRefreshLayout);
        memberSearchPresenter.attachView(this);
    }

    private void setAdapter() {
        adapter = new HuiYuanListAdapter(mContext, R.layout.list_item_huiyuan_product, listinfo);
        adapter.setOnPopupClickListener(this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, HuiYuanDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("huiYuanDetail", listinfo.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        params = new MemberParams();
        topbar.setTttleText("会员快查")
                .onBackBtnClick()
                .setBackBtnEnable(true)
                .setTopbarClickListener(this);
        listinfo = new ArrayList<>();
        emptyview.bind(mRefreshLayout);
    }

    @OnClick(imb_search)
    public void conditionSearch(View view) {
        int id = view.getId();
        if (id == imb_search) {
            if (edSearch.getText().toString().length() > 0) {
//                CloseKeyBoard.hideSoftInput(mContext, edSearch);
                CloseKeyBoard.showSoftInput(mContext);
                params.setParams(edSearch.getText().toString());
                emptyview.showLoading();
                memberSearchPresenter.loadMemberData(app.getApiService().getHuiYuanList(app.getCurrentUserNum(), params));
            }
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
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


    // 通过的回掉
    @Override
    public void onProductBuyItemClick(HuiYuanKuaiChaInfo huiYuanKuaiChaInfo) {
//        Toast.makeText(mContext, "通过", Toast.LENGTH_SHORT).show();
    }


    //prsenter
    @Override
    public void getDataSuccess(List<HuiYuanKuaiChaInfo> infolist) {
        LogX.e("1111111111111getDataSuccess", listinfo.toString());
        listinfo.clear();
        for (HuiYuanKuaiChaInfo info : infolist) {
            listinfo.add(info);
        }
        adapter.setData(listinfo);
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

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        memberSearchPresenter.detachView();
    }
}

