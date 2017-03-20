package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.adapter.YuyueDealAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Description ：预约处理详情页面
 * <p/>jin
 * Created by hdl on 2017/3/9.
 */
public class AppointMentDealDetailActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView lvProduct;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;
    @BindView(R.id.tv_projecttime)
    TextView tvProjectcreatetime;
    private YuyueDealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_ment_deal_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar.setTttleText("预约处理")
                .setBackBtnEnable(true)
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .setRightText("确定")
                .onRightTextClick()
                .setTopbarClickListener(this);

        String project = getIntent().getStringExtra("project");
        String crateTime = getIntent().getStringExtra("crateTime");
        tvProjectcreatetime.setText(crateTime);
        List<ProjectType> projectTypes = new Gson().fromJson(project, new TypeToken<List<ProjectType>>() {
        }.getType());
        setAdapter(projectTypes);
    }

    private void setAdapter(List<ProjectType> projectTypes) {
        adapter = new YuyueDealAdapter(mContext, R.layout.list_item_yuyue_deal_product, projectTypes);
//        adapter.setOnPopupClickListener(this);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "jfiejf", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            AppUtility.showToastMsg("你点击了确定");
        }
    }
}
