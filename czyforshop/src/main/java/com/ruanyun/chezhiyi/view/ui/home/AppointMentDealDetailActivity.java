package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.params.YuYueDealListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.YuYueDealParams;
import com.ruanyun.chezhiyi.commonlib.presenter.YuYueDealPresenter;
import com.ruanyun.chezhiyi.commonlib.util.CloseKeyBoard;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.YuYueDealMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.YuyueDealAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Description ：预约处理详情页面
 * <p/>jin
 * Created by hdl on 2017/3/9.
 */
public class AppointMentDealDetailActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, YuYueDealMvpView {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;
    @BindView(R.id.tv_projecttime)
    TextView tvProjectcreatetime;
    @BindView(R.id.emptyview)
    RYEmptyView emptyview;
    private YuyueDealAdapter adapter;
    private String MakeNum, crateTime, project;
    private YuYueDealPresenter yuYueDealPresenter = new YuYueDealPresenter();
    private List<YuYueDealParams> listparams = new ArrayList<>();
    private List<ProjectType> projectlist = new ArrayList<>();
    private YuYueDealListParams yuYueDealListParams = new YuYueDealListParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_ment_deal_detail);
        ButterKnife.bind(this);
        emptyview.bind(refreshlayout);
        initView();
    }

    private void initView() {
        yuYueDealPresenter.attachView(this);
        topbar.setTttleText("预约处理")
                .setBackBtnEnable(true)
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .setRightText("确定")
                .onRightTextClick()
                .setTopbarClickListener(this);

        project = getIntent().getStringExtra("project");
        LogX.e("project", project);
        crateTime = getIntent().getStringExtra("crateTime");
        LogX.e("crateTime", crateTime);
        MakeNum = getIntent().getStringExtra("MakeNum");
        LogX.e("MakeNum", MakeNum);
        tvProjectcreatetime.setText(crateTime);
        List<ProjectType> projectTypes = new Gson().fromJson(project, new TypeToken<List<ProjectType>>() {
        }.getType());
        setAdapter(projectTypes);
    }

    private void setAdapter(List<ProjectType> projectTypes) {
        adapter = new YuyueDealAdapter(mContext, R.layout.list_item_yuyue_deal_product, projectTypes);
        lvProduct.setAdapter(adapter);
        projectlist = projectTypes;

    }


    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            for (ProjectType info : projectlist) {
                YuYueDealParams yuYueDealParams = new YuYueDealParams();
                yuYueDealParams.setProjectNum(info.getProjectNum());
                yuYueDealParams.setAppointMoney(info.getYuMoney());
                listparams.add(yuYueDealParams);
            }
            Gson gson = new Gson();
            yuYueDealListParams.setProject(gson.toJson(listparams));
            emptyview.showLoading();
            CloseKeyBoard.showSoftInput(mContext);//关闭软键盘
            tvProjectcreatetime.setVisibility(View.GONE);
            yuYueDealPresenter.getGongGao(app.getApiService().getYuYueDeal(app.getCurrentUserNum(), 1, MakeNum, yuYueDealListParams));
            listparams.clear();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        yuYueDealPresenter.detachView();
    }

    @Override
    public void getDataSuccess(ResultBase resultBase) {
        if (resultBase.getResult() == 1) {
            emptyview.loadSuccuss();
            finish();
        } else {
            emptyview.showLoadFail();
        }
    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void cancelSuccess() {

    }


}
