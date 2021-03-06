package com.ruanyun.chezhiyi.view.ui.mine;

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
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.MyGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.model.TiChengInfoModel;
import com.ruanyun.chezhiyi.commonlib.model.TiChengListModel;
import com.ruanyun.chezhiyi.commonlib.presenter.MyGongDanPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.TiChengPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.MyGongDanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.TiChengListView;
import com.ruanyun.chezhiyi.commonlib.view.TiChengView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MendianGongdanshuAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * 工单数
 * Created by jin on 2017/4/13.
 */
public class GongDanShuActivity extends BaseActivity implements Topbar.onTopbarClickListener, TiChengView, MyGongDanTongJiView,
        BGARefreshLayout.BGARefreshLayoutDelegate {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.li_month)
    LinearLayout liMonth;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_increase)
    TextView tvIncrease;
    private MendianGongdanshuAdapter adapter;
    private List<MyGongDanInfo.ResultBean> listData;
    //条件选择器
    private OptionsPickerView pvOptions;

    List<String> dateList = new ArrayList<>();
    private TiChengPresenter tiChengPresenter = new TiChengPresenter();
    private MyGongDanPresenter myGongDanPresenter = new MyGongDanPresenter();//列表

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat format2 = new SimpleDateFormat("yyyyMM");

    private String date, startDay, endDay, selectMonth;
    private Calendar c = Calendar.getInstance();

    private int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gongdan_shu);
        ButterKnife.bind(this);
        tiChengPresenter.attachView(this);
        myGongDanPresenter.attachView(this);
        initRefreshLayout(mRefreshLayout);
        initView();
        setAdapter();
    }

    private void initView() {
        date = format2.format(new Date());
        //获取当前月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDay = format.format(c.getTime());
        startDay = format1.format(c.getTime());
        selectMonth = date.substring(4, 6);
        tvYear.setText(date.substring(0, 4) + "年");
        tvMonth.setText(date.substring(4, 6) + "月");
        for (int i = 0; i < 12; i++) {
            if (i <= 9) {
                dateList.add("0" + i);
            } else {
                dateList.add(i + "");
            }
        }
        listData = new ArrayList<>();
        emptyview.bind(mRefreshLayout);
        emptyview.showLoading();
        topbar.setTttleText("工单数")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightImgBtnBg(R.drawable.gongdian_right_icon)
                .onRightImgBtnClick()
                .setTopbarClickListener(this);
        tiChengPresenter.getTiChengInfo(app.getApiService().getTiChengInfo(app.getCurrentUserNum(), date, 3)); //1:销售提成 2：施工提成
        myGongDanPresenter.getGongDanMyTongJiInfo(app.getApiService().getMyGongDanList(app.getCurrentUserNum(), startDay + "-01", endDay, app.getCurrentUserNum(), 1));
    }

    private void setAdapter() {

        adapter = new MendianGongdanshuAdapter(mContext, R.layout.list_item_gongdanshu_item, listData);
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
        myGongDanPresenter.detachView();
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
        tiChengPresenter.getTiChengInfo(app.getApiService().getTiChengInfo(app.getCurrentUserNum(), date, 3)); //1:销售提成 2：施工提成
        myGongDanPresenter.getGongDanMyTongJiInfo(app.getApiService().getMyGongDanList(app.getCurrentUserNum(), date.substring(0, 4) + "-" + selectMonth + "-01",
                date.substring(0, 4) + "-" + selectMonth + "-31", app.getCurrentUserNum(), 1));
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        myGongDanPresenter.getGongDanMyTongJiInfo(app.getApiService().getMyGongDanList(app.getCurrentUserNum(), date.substring(0, 4) + "-" + selectMonth + "-01",
                date.substring(0, 4) + "-" + selectMonth + "-31", app.getCurrentUserNum(), pageNumber));
        return true;

    }

    @OnClick({R.id.li_month})
    public void UiOnclick(View view) {
        switch (view.getId()) {
            case R.id.li_month:
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        LogX.e("工单数", date.substring(0, 4) + dateList.get(options1));
                        selectMonth = dateList.get(options1);
                        date = date.substring(0, 4) + selectMonth;
                        tvMonth.setText(selectMonth + "月");
                        emptyview.showLoading();
                        tiChengPresenter.getTiChengInfo(app.getApiService().getTiChengInfo(app.getCurrentUserNum(), date.substring(0, 4) + dateList.get(options1), 3)); //1:销售提成 2：施工提成
                        myGongDanPresenter.getGongDanMyTongJiInfo(app.getApiService().getMyGongDanList(app.getCurrentUserNum(), date.substring(0, 4) + "-" + selectMonth + "-01",
                                date.substring(0, 4) + "-" + selectMonth + "-31", app.getCurrentUserNum(), 1));

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
        }
    }

    @Override
    public void getTiChengSuccess(TiChengInfoModel tiChengInfoModel) {
        LogX.e("工单数getDataSuccess", tiChengInfoModel.toString());
        emptyview.loadSuccuss();
        mRefreshLayout.endRefreshing();
        if (tiChengInfoModel.getMap() == null) {
            AppUtility.showToastMsg("未查到相关数据");
            tvCount.setText(" 0");
            tvIncrease.setText("0");
            return;
        }
        tvCount.setText(tiChengInfoModel.getMap().getCommonNumber() + "");
        tvIncrease.setText(tiChengInfoModel.getMap().getCommonPercent());
    }

    @Override
    public void cancelTiChengErr() {

    }

    @Override
    public void getGongDanSuccess(MyGongDanInfo ResultBase) {
        LogX.e("工单Mypersenter", ResultBase.getResult().toString());
        if (pageNumber == 1) {
            listData.clear();
            listData = ResultBase.getResult();
            adapter.setDatas(listData);
            adapter.notifyDataSetChanged();
            mRefreshLayout.endRefreshing();
        } else {
            listData.addAll(ResultBase.getResult());
            mRefreshLayout.endLoadingMore();
        }
        ++pageNumber;
        LogX.e("pageNum", pageNumber + "");
    }

    @Override
    public void cancelGongDanTiChengErr() {

    }
}
