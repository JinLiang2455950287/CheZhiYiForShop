package com.ruanyun.chezhiyi.view.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.TiChengInfoModel;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListModel;
import com.ruanyun.chezhiyi.commonlib.presenter.TiChengListPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.TiChengPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.TiChengListView;
import com.ruanyun.chezhiyi.commonlib.view.TiChengView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MendianGongdanshuAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.TiChenAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.TiChenPublicAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 销售提成
 * Created by jin on 2017/4/13.
 */
public class XiaoShouTiChengActivity extends BaseActivity implements Topbar.onTopbarClickListener, TiChengView, TiChengListView, BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.li_month)
    LinearLayout liMonth;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_increase)
    TextView tvIncrease;
    @BindView(R.id.activity_reback_pay)
    LinearLayout activityRebackPay;
    private TiChenAdapter adapter;
    private List<TiChengListModel> listData;
    //条件选择器
    private OptionsPickerView pvOptions;

    List<String> dateList = new ArrayList<>();
    private TiChengPresenter tiChengPresenter = new TiChengPresenter();
    private TiChengListPresenter tiChengListPresenter = new TiChengListPresenter();//列表
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMM");
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_shou_ti_cheng);
        ButterKnife.bind(this);
        tiChengPresenter.attachView(this);
        tiChengListPresenter.attachView(this);
        initRefreshLayout(mRefreshLayout);
        initView();
        setAdapter();
    }

    private void initView() {
        date = sDateFormat.format(new Date());
        LogX.e("提成", date);
        tvYear.setText(date.substring(0, 4) + "年");
        tvMonth.setText(date.substring(4, date.length()) + "月");
        for (int i = 1; i < 13; i++) {
            if (i <= 9) {
                dateList.add("0" + i);
            } else {
                dateList.add(i + "");
            }
        }
        listData = new ArrayList<>();
        emptyview.bind(mRefreshLayout);
        emptyview.showLoading();
        topbar.setTttleText("销售提成")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .enableRightImageBtn()
                .setRightImgBtnBg(R.drawable.gongdian_right_icon)
                .onRightImgBtnClick()
                .setTopbarClickListener(this);
        tiChengPresenter.getTiChengInfo(app.getApiService().getTiChengInfo(app.getCurrentUserNum(), date, 1)); //1:销售提成 2：施工提成
        tiChengListPresenter.getTiChengList(app.getApiService().getTiChengList(app.getCurrentUserNum(), 1, 1, date));
    }

    private void setAdapter() {

        adapter = new TiChenAdapter(mContext, R.layout.list_shigongticheng_item, listData);
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
        tiChengPresenter.detachView();
        tiChengListPresenter.detachView();
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
        tiChengListPresenter.getTiChengList(app.getApiService().getTiChengList(app.getCurrentUserNum(), 1, 1, date));

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

    @OnClick({R.id.li_month})
    public void UiOnclick(View view) {
        switch (view.getId()) {
            case R.id.li_month:
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        tvMonth.setText(dateList.get(options1) + "月");
                        date = date.substring(0, 4) + dateList.get(options1);
                        emptyview.showLoading();
                        tiChengListPresenter.getTiChengList(app.getApiService().getTiChengList(app.getCurrentUserNum(), 1, 1, date));
                        tiChengPresenter.getTiChengInfo(app.getApiService().getTiChengInfo(app.getCurrentUserNum(), date, 2)); //1:销售提成 2：施工提成
                    }
                }).setOutSideCancelable(true)//点击外部dismiss default true
                        .setCyclic(true, false, false)//循环与否
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setSelectOptions(1, 0, 0)  //设置默认选中项
                        .build();
                pvOptions.setPicker(dateList);
                pvOptions.show();
                break;
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        } else if (id == R.id.img_btn_right) {
            Intent intent = new Intent(this, TiChengPublicActivity.class);
            intent.putExtra("titleName", "销售提成");
            startActivity(intent);
        }
    }

    @Override
    public void getTiChengSuccess(TiChengInfoModel tiChengInfoModel) {
        LogX.e("提成persenter", tiChengInfoModel.toString());
        if (tiChengInfoModel.getMap() == null) {
            AppUtility.showToastMsg("未查到相关数据");
            tvMoney.setText("¥ 0");
            tvIncrease.setText("0");
            return;
        }
        tvMoney.setText("¥ " + tiChengInfoModel.getMap().getCommonNumber() + "");
        tvIncrease.setText(tiChengInfoModel.getMap().getCommonPercent());
    }

    @Override
    public void cancelTiChengErr() {
    }

    @Override
    public void getTiChengListSuccess(List<TiChengListModel> resultBase) {
        emptyview.loadSuccuss();
        mRefreshLayout.endRefreshing();
        listData.clear();
        listData = resultBase;
        LogX.e("提成数据", listData.toString());
        adapter.setDatas(listData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dismissListLoadingView() {

    }

    @Override
    public void cancelTiChengListErr() {

    }
}
