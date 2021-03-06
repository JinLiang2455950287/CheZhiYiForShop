package com.ruanyun.chezhiyi.view.ui.mendian;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanDetailInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.HuiYuanGongDanDetailPresenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanGongDanDetailView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MendianGongdanDetailListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MendianGongdanListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 2017/4/13
 * jin
 * 工单详细 当日/当月
 */
public class GongdanDetailActivity extends BaseActivity implements Topbar.onTopbarClickListener, HuiYuanGongDanDetailView, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    private MendianGongdanDetailListAdapter adapter;
    private List<MenDianGongDanDetailInfo.ResultBean> listData;
    private HuiYuanGongDanDetailPresenter huiYuanGongDanDetailPresenter = new HuiYuanGongDanDetailPresenter();
    private String time;
    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_dan);
        ButterKnife.bind(this);
        huiYuanGongDanDetailPresenter.attachView(this);
        initRefreshLayout(mRefreshLayout);
        initView();
        setAdapter();
        time = getIntent().getStringExtra("time");
        LogX.e("time", time);
        huiYuanGongDanDetailPresenter.getGongDanTongJiDtailInfo(app.getApiService().getGongDanDetailInfo(app.getCurrentUserNum(), time, time, 1));
    }

    private void initView() {
        listData = new ArrayList<>();
        emptyview.bind(mRefreshLayout);
        emptyview.showLoading();
        topbar.setTttleText("工单明细")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void setAdapter() {

        adapter = new MendianGongdanDetailListAdapter(mContext, R.layout.list_item_gongdan_detail_item, listData);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        huiYuanGongDanDetailPresenter.detachView();
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
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        pageNum = 1;
        huiYuanGongDanDetailPresenter.getGongDanTongJiDtailInfo(app.getApiService().getGongDanDetailInfo(app.getCurrentUserNum(), time, time, 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        pageNum++;
        huiYuanGongDanDetailPresenter.getGongDanTongJiDtailInfo(app.getApiService().getGongDanDetailInfo(app.getCurrentUserNum(), time, time, pageNum));
        return true;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }


    @Override
    public void getGongDanDetailSuccess(MenDianGongDanDetailInfo menDianGongDanDetailInfo) {
        LogX.e("工单Detailpersenter", menDianGongDanDetailInfo.toString());
        emptyview.loadSuccuss();
        mRefreshLayout.endLoadingMore();
        mRefreshLayout.endRefreshing();
        if (pageNum == 1) {
            listData.clear();
            listData = menDianGongDanDetailInfo.getResult();
            adapter.setData(listData);
            adapter.notifyDataSetChanged();
        } else {
            listData.addAll(menDianGongDanDetailInfo.getResult());
            adapter.setData(listData);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancelGongDanTiDetailChengErr() {

    }
}
