package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.GongWeiJiShiBean;
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

import java.io.Serializable;
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
    private GongWeiJiShiBean gongWeiJiShiBean = new GongWeiJiShiBean();
    private String projectNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oper_order_gong);
        ButterKnife.bind(this);
        initView();
        initRecyclerView();
        showLoading("加载中..");
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
        projectNumber = getIntent().getStringExtra("projectNumber");
        gongWeiJiShiBean.setProjectNumber(projectNumber);
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
        adapterGongWei.setDefaultCheckedItemPosition(0);
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
                gongWeiJiShiBean.setJishiname(jiShi.get(position).getNickName().toString());

            }
        });

        adapterGongWei.setOnItemClickListener(new SingleChoiceGongWeiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                adapterGongWei.check(position);
                gongWeiJiShiBean.setGongweiname(gongWei.get(position).getWorkbayName().toString());
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_left:
                Intent intent2 = new Intent();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("gongWeiJiShiBean", gongWeiJiShiBean);
                intent2.putExtras(bundle2);
                LogX.e("1544", gongWeiJiShiBean.toString());
                setResult(1544, intent2);
                finish();
                break;
            case R.id.tv_title_right:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("gongWeiJiShiBean", gongWeiJiShiBean);
                intent.putExtras(bundle);
                LogX.e("1544", gongWeiJiShiBean.toString());
                setResult(1544, intent);
                finish();
                break;
        }
    }

    /*返回键的监听*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("gongWeiJiShiBean", gongWeiJiShiBean);
            intent.putExtras(bundle);
            LogX.e("1544", gongWeiJiShiBean.toString());
            setResult(1544, intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
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
        if (gongWei.size() > 0) {
            gongWeiJiShiBean.setGongweiname(gongWei.get(0).getWorkbayName());
            gongWeiJiShiBean.setGongweiid(gongWei.get(0).getWorkbayInfoNum());
        }
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
        gongWeiJiShiBean.setJishiname(jishiList.get(0).getNickName());
        gongWeiJiShiBean.setJishiid(jishiList.get(0).getUserNum());
        adapterYuanGong.setData(jiShi);
        adapterYuanGong.notifyDataSetChanged();
        dissMissLoading();
    }

    @Override
    public void cancelKaiDanJiShiErr() {

    }
}
