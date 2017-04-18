package com.ruanyun.chezhiyi.view.ui.TiXing;

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
import com.ruanyun.chezhiyi.commonlib.util.CommentUtils;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.adapter.WakeAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 2017.4.17
 * jl
 * 会员提醒
 */
public class TiXingFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private ListView lvProduct;
    private BGARefreshLayout mRefreshLayout;
    private RYEmptyView emptyview;
    private WakeAdapter adapter;
    private List<String> listData;
    String type;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_ti_xing, container, false);
        initView();
        type = getArguments().getString("type");
        CommentUtils.wakeType = type;
        LogX.e("提醒", type);
        return mContentView;
    }

    private void initView() {
        lvProduct = (ListView) mContentView.findViewById(R.id.list);
        mRefreshLayout = (BGARefreshLayout) mContentView.findViewById(R.id.refreshlayout);
        emptyview = (RYEmptyView) mContentView.findViewById(R.id.emptyview);
        initRefreshLayout(mRefreshLayout);
        emptyview.bind(mRefreshLayout);
        listData = new ArrayList<>();
        setAdapter();
    }

    private void setAdapter() {
        listData.add("few");
        adapter = new WakeAdapter(mContext, R.layout.list_wake_item, listData);
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
        LogX.e("提醒", type);
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
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
