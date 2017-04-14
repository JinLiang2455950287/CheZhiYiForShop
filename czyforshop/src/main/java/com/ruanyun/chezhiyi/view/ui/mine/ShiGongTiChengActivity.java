package com.ruanyun.chezhiyi.view.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.view.adapter.MendianGongdanshuAdapter;
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

/**
 * 施工提成
 * Created by jin on 2017/4/13.
 */
public class ShiGongTiChengActivity extends BaseActivity implements Topbar.onTopbarClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {
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
    private MendianGongdanshuAdapter adapter;
    private List<String> listData;
    //条件选择器
    private OptionsPickerView pvOptions;

    //    List<String> dateList = Arrays.asList(getResources().getStringArray(R.array.month));
    List dateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_gong_ti_cheng);
        ButterKnife.bind(this);
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
//        emptyview.showLoading();
        topbar.setTttleText("施工提成")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .enableRightImageBtn()
                .setRightImgBtnBg(R.drawable.gongdian_right_icon)
                .onRightImgBtnClick()
                .setTopbarClickListener(this);
    }

    private void setAdapter() {

        listData.add("fe");
        listData.add("fe");
        listData.add("fe");
        listData.add("fe");
        listData.add("fe");
        listData.add("fe");

        adapter = new MendianGongdanshuAdapter(mContext, R.layout.list_shigongticheng_item, listData);
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

    @OnClick({R.id.li_month})
    public void UiOnclick(View view) {
        switch (view.getId()) {
            case R.id.li_month:
                pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        Toast.makeText(mContext, dateList.get(options1).toString(), 2).show();
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
            intent.putExtra("titleName","施工提成");
            startActivity(intent);
        }
    }
}
