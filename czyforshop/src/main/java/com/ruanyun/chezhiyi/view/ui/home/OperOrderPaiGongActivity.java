package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.KaiDanGongweiPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.KaiDanYuanGongPresenter;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.KaiDanGongweiView;
import com.ruanyun.chezhiyi.commonlib.view.KaiDanJiShiView;
import com.ruanyun.chezhiyi.commonlib.view.SingleChoiceGongWeiAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.SingleChoiceYuanGongAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description ：添加工位或技师
 * <p/>
 * Created by hdl on 2017/4/27.
 */


public class OperOrderPaiGongActivity extends BaseActivity implements Topbar.onTopbarClickListener, KaiDanGongweiView, KaiDanJiShiView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.recyclerview_yuangong)
    RecyclerView recyclerviewYuangong;
    @BindView(R.id.recyclerview_gongwei)
    RecyclerView recyclerviewGongwei;
    private List<User> jiShi;
    private List<WorkBayInfo> gongWei;
    private SingleChoiceYuanGongAdapter adapterYuanGong;
    private SingleChoiceGongWeiAdapter adapterGongWei;
    private KaiDanGongweiPresenter kaiDanGongweiPresenter = new KaiDanGongweiPresenter();
    private KaiDanYuanGongPresenter kaiDanYuanGongPresenter = new KaiDanYuanGongPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oper_order_gong);
        ButterKnife.bind(this);
        initView();
        initRecyclerView();

    }

    private void initView() {
        kaiDanGongweiPresenter.attachView(this);
        kaiDanYuanGongPresenter.attachView(this);
        topbar.setTttleText("派工")
                .setBackBtnEnable(true)
                .setRightText("确认")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .onRightTextClick()
                .setTopbarClickListener(this);
        String projectNumber = getIntent().getStringExtra("projectNumber");
        kaiDanGongweiPresenter.getKaiDanGongWeiInfo(app.getApiService().getWorkOrderGongWei(app.getCurrentUserNum(), projectNumber));
        kaiDanYuanGongPresenter.getKaiDanJiShiInfo(app.getApiService().getLeisureTechnician(app.getCurrentUserNum(), projectNumber));
    }

    private void initRecyclerView() {

        jiShi = new ArrayList<>();
        gongWei = new ArrayList<>();

        adapterYuanGong = new SingleChoiceYuanGongAdapter(this, jiShi);
        adapterGongWei = new SingleChoiceGongWeiAdapter(this, gongWei);
        // 默认选中第一个item
        adapterYuanGong.setDefaultCheckedItemPosition(0);
        // 这个方法不能忘，指定显示布局
        recyclerviewYuangong.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerviewGongwei.setLayoutManager(new GridLayoutManager(this, 3));

        // 添加分隔线，DividerItemDecoration这个类是系统提供的，在support包中
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewYuangong.setAdapter(adapterYuanGong);
        recyclerviewGongwei.setAdapter(adapterGongWei);

        adapterYuanGong.setOnItemClickListener(new SingleChoiceYuanGongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                adapterYuanGong.check(position);
                Toast.makeText(getApplicationContext(), jiShi.get(position).getNickName().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        adapterGongWei.setOnItemClickListener(new SingleChoiceGongWeiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                adapterGongWei.check(position);
                Toast.makeText(getApplicationContext(), gongWei.get(position).getWorkbayName().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_left:
                finish();
                break;
            case R.id.tv_title_right:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kaiDanGongweiPresenter.detachView();
        kaiDanYuanGongPresenter.detachView();
    }

    @Override
    public void getKaiDanSuccess(List<WorkBayInfo> workBayInfo) {
        LogX.e("开单工位", workBayInfo.toString());
        gongWei = workBayInfo;
        adapterGongWei.setData(gongWei);
        adapterGongWei.notifyDataSetChanged();
    }

    @Override
    public void cancelKaiDanTiChengErr() {

    }

    @Override
    public void getKaiDanJiShiSuccess(List<User> jishiList) {
        LogX.e("技师persenter", jishiList.toString());
        jiShi = jishiList;
        adapterYuanGong.setData(jiShi);
        adapterYuanGong.notifyDataSetChanged();
    }

    @Override
    public void cancelKaiDanJiShiErr() {

    }
}
