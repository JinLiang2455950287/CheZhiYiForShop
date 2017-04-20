package com.ruanyun.chezhiyi.view.ui.mendian;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.MenDianGongDanInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.HuiYuanGongDanPresenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.HuiYuanGongDanTongJiView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MendianGongdanListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;


import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

import com.ruanyun.chezhiyi.R;

/**
 * 2017/4/12
 * jin
 * 工单统计
 */
public class GongDanActivity extends BaseActivity implements Topbar.onTopbarClickListener, HuiYuanGongDanTongJiView, BGARefreshLayout.BGARefreshLayoutDelegate, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout mRefreshLayout;
    private MendianGongdanListAdapter adapter;
    private List<MenDianGongDanInfo.ResultBean> listData;
    private Calendar mCalendar;
    private SimpleDateFormat sDateFormatend = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
    private String startdate, enddate;
    private HuiYuanGongDanPresenter huiYuanGongDanPresenter = new HuiYuanGongDanPresenter();
    private String tempTime, starttime, endtime;
    private AlertDialog builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gong_dan);
        ButterKnife.bind(this);
        huiYuanGongDanPresenter.attachView(this);
        initRefreshLayout(mRefreshLayout);
        initView();
        setAdapter();
    }

    private void initView() {
        mCalendar = Calendar.getInstance();
        listData = new ArrayList<>();
        emptyview.bind(mRefreshLayout);
        emptyview.showLoading();
        topbar.setTttleText("工单统计")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .onRightTextClick()
                .enableRightText()
                .setRightText("统计时间")
                .setTopbarClickListener(this);
        startdate = sDateFormat.format(new Date()) + "-01";
        enddate = sDateFormatend.format(new Date());
        LogX.e("日期", startdate + ";" + enddate);
        huiYuanGongDanPresenter.getGongDanTongJiInfo(app.getApiService().getMenDianGongDan(app.getCurrentUserNum(), startdate, enddate));
    }

    private void setAdapter() {
        adapter = new MendianGongdanListAdapter(mContext, R.layout.list_item_gongdan_tongji_item, listData);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showActivity(GongdanDetailActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        huiYuanGongDanPresenter.detachView();
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
    public void onRefresh() {
        huiYuanGongDanPresenter.getGongDanTongJiInfo(app.getApiService().getMenDianGongDan(app.getCurrentUserNum(), "", ""));
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
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


        } else {
            // 网络不可用，返回 false，不显示正在加载更多
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();

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
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            dialogEducation();
        }
    }


    //结算方式dialog
    private void dialogEducation() {

        builder = new AlertDialog.Builder(this, R.style.Dialog).create(); // 先得到构造器
        builder.show();
        builder.getWindow().setContentView(R.layout.gongdantiem_dialog);
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.gongdantiem_dialog, null);
        builder.getWindow().setContentView(view);


        TextView tvcofirm = (TextView) view.findViewById(R.id.bt_confirm);
        final TextView startTime = (TextView) view.findViewById(R.id.tv_startTime);
        startTime.setText(startdate);
        final TextView endTime = (TextView) view.findViewById(R.id.tv_endTime);
        endTime.setText(enddate);
        TextView startTimebtn = (TextView) view.findViewById(R.id.tv_startTimebtn);
        TextView endTimebtn = (TextView) view.findViewById(R.id.tv_endTimebtn);
        ImageView dimiss = (ImageView) view.findViewById(R.id.tv_dimiss);
        //获取屏幕的尺寸
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Window dialogWindow = builder.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//显示在底部
        Display d = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.5
//        p.width = d.getWidth(); // 宽度设置为屏幕宽
        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕宽
        dialogWindow.setAttributes(p);
        startTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttime = initDatePicker();
                startTime.setText(starttime);
            }
        });

        endTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endtime = initDatePicker();
                endTime.setText(endtime);
            }
        });
        tvcofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huiYuanGongDanPresenter.getGongDanTongJiInfo(app.getApiService().getMenDianGongDan(app.getCurrentUserNum(), starttime, endtime));
                builder.dismiss();
            }
        });
        dimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

    }

    @SuppressWarnings("ResourceType")
    private String initDatePicker() {
        Calendar c = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(GongDanActivity.this, AlertDialog.THEME_HOLO_LIGHT,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tempTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        Toast.makeText(mContext, year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, 2).show();
                    }
                }
                // 设置初始日期
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
        return tempTime;
    }

    @Override
    public void getGongDanSuccess(MenDianGongDanInfo menDianGongDanInfo) {
        emptyview.loadSuccuss();
        LogX.e("工单统计", menDianGongDanInfo.getResult().toString());
        if (menDianGongDanInfo.getResult().size() > 0) {
            listData.clear();
            listData.addAll(menDianGongDanInfo.getResult());
            adapter.setData(listData);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancelGongDanTiChengErr() {

    }


}

