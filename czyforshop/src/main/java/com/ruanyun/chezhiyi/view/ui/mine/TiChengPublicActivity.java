package com.ruanyun.chezhiyi.view.ui.mine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListPublicInfo;
import com.ruanyun.chezhiyi.commonlib.model.TiChengPublicInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.TiChengDetailList2Presenter;
import com.ruanyun.chezhiyi.commonlib.presenter.TiChengDetailListPresenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.TiChengDetailList2View;
import com.ruanyun.chezhiyi.commonlib.view.TiChengDetailListView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.TiChenPublicAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 提成详情
 * Created by jin on 2017/4/14.
 */
public class TiChengPublicActivity extends BaseActivity implements Topbar.onTopbarClickListener, TiChengDetailList2View, TiChengDetailListView, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private TiChenPublicAdapter adapter;
    private List<TiChengListPublicInfo.ResultBean> listData;
    //条件选择器
    private OptionsPickerView pvOptions;

    List dateList = new ArrayList<>();
    private TiChengDetailListPresenter tiChengDetailListPresenter = new TiChengDetailListPresenter();
    private TiChengDetailList2Presenter tiChengDetailList2Presenter = new TiChengDetailList2Presenter();
    private int date;
    private String endDay;
    Calendar c = Calendar.getInstance();//首先要获取日历对象
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private int typePublicTiCheng = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_cheng_public);
        ButterKnife.bind(this);
        tiChengDetailListPresenter.attachView(this);
        tiChengDetailList2Presenter.attachView(this);
        initRefreshLayout(mRefreshLayout);
        initView();
        setAdapter();
    }

    private void initView() {
        for (int i = 0; i < 12; i++) {
            dateList.add(i + "月");
        }
        listData = new ArrayList<>();
        emptyview.bind(mRefreshLayout);
        emptyview.showLoading();
        String titlename = getIntent().getStringExtra("titleName");
        if (titlename != null) {
            if (titlename.equals("1")) {//1:销售提成 2：施工提成
                titlename = "销售提成";
                typePublicTiCheng = 1;
            } else {
                titlename = "施工提成";
                typePublicTiCheng = 2;
            }
        }

        topbar.setTttleText(titlename)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        date = c.get(Calendar.YEAR); // 获取当前年份
        //获取当前月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDay = format.format(c.getTime());
        LogX.e("日期", endDay + "");
        tiChengDetailListPresenter.getTiChengYearList(app.getApiService().getTiChengDetailYearList(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
        tiChengDetailList2Presenter.getTiChengYearList2(app.getApiService().getTiChengDetailYearList2(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
    }

    private void setAdapter() {

        adapter = new TiChenPublicAdapter(mContext, R.layout.list_ticheng_detail_item, listData);
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
        tiChengDetailListPresenter.detachView();
        tiChengDetailList2Presenter.detachView();
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
        tiChengDetailListPresenter.getTiChengYearList(app.getApiService().getTiChengDetailYearList(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
        tiChengDetailList2Presenter.getTiChengYearList2(app.getApiService().getTiChengDetailYearList2(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
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

    @OnClick({R.id.iv_left, R.id.iv_right})
    public void UiOnclick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                emptyview.showLoading();
                date--;
                tiChengDetailListPresenter.getTiChengYearList(app.getApiService().getTiChengDetailYearList(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
                tiChengDetailList2Presenter.getTiChengYearList2(app.getApiService().getTiChengDetailYearList2(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
                break;
            case R.id.iv_right:
                date++;
                emptyview.showLoading();
                tiChengDetailListPresenter.getTiChengYearList(app.getApiService().getTiChengDetailYearList(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
                tiChengDetailList2Presenter.getTiChengYearList2(app.getApiService().getTiChengDetailYearList2(app.getCurrentUserNum(), date + "", date + "", app.getCurrentUserNum(), typePublicTiCheng));
                break;
        }

    }


    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void getTiChengListSuccess(ResultBase resultBase) {
        LogX.e("施工提成", resultBase.getObj() + "");
        TiChengPublicInfo info = new Gson().fromJson(resultBase.getObj().toString(), TiChengPublicInfo.class);
        if (info != null) {
            tvTitle.setText(date + "( ¥ " + info.getAmount() + ")");
        }

    }

    @Override
    public void dismissListLoadingView() {

    }

    @Override
    public void cancelTiChengListErr() {

    }

    @Override
    public void getTiChengList2Success(TiChengListPublicInfo tiChengListPublicInfo) {
        LogX.e("提成Yearlist2persenter", tiChengListPublicInfo.toString());
        emptyview.loadSuccuss();
        mRefreshLayout.endRefreshing();
//        if (tiChengListPublicInfo.getResult().size() > 0) {
        listData.clear();
        listData = tiChengListPublicInfo.getResult();
        adapter.setDatas(listData);
        adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void dismissList2LoadingView() {

    }

    @Override
    public void cancelTiChengList2Err() {

    }
}
