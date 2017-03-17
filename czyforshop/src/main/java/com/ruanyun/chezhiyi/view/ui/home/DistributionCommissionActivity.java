package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AssistUserInfo;
import com.ruanyun.chezhiyi.commonlib.model.DistrCommissionModel;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderCommissionInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.SaveDistrubutionParams;
import com.ruanyun.chezhiyi.commonlib.presenter.DistributionCommissionPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.DistributionCommissionMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.adapter.DistributionCommissionAdapter;
import com.ruanyun.chezhiyi.view.widget.AfterMoneyChanged;
import com.ruanyun.chezhiyi.view.widget.ItemTextWatcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by msq on 2016/10/9.
 * 分配提成Activity
 */
public class DistributionCommissionActivity extends AutoLayoutActivity implements Topbar
        .onTopbarClickListener, DistributionCommissionMvpView, AfterMoneyChanged {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.btn_commission)
    Button btnCommission;
    private TextView tvNumber;
    private TextView tvMoney;
    private TextView tvMoneyMine;


    private DistributionCommissionPresenter presenter = new DistributionCommissionPresenter();
    private DistributionCommissionAdapter adapter;
    private List<DistrCommissionModel> list = new ArrayList<>();
    private WorkOrderInfo mWorkOrderInfo;      // 工单信息
    private SaveDistrubutionParams params = new SaveDistrubutionParams();
    private WorkOrderCommissionInfo workOrderCommissionInfo;
    private String jsonArrayString;
    private double commonMoney;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_distribution_commission);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initData();
    }

    private void initData() {
        mWorkOrderInfo = getIntent().getParcelableExtra(C.IntentKey.WORKORDER_INFO);
        topbar.setTttleText("分配提成")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        adapter = new DistributionCommissionAdapter(this, R.layout.list_item_technicians, list);
        lv.setAdapter(adapter);
        View view = getViewFromLayout(R.layout.distribution_head_view);
        tvNumber=ButterKnife.findById(view, R.id.tv_number);
        tvMoney=ButterKnife.findById(view, R.id.tv_money);
        tvMoneyMine=ButterKnife.findById(view, R.id.tv_money_mine);
        lv.addHeaderView(view);
        View footView = getViewFromLayout(R.layout.foot_tip_view);
        lv.addFooterView(footView);
        presenter.getDistrubuteUser(app.getApiService().getDistrubuteUser(app.getCurrentUserNum()
                , mWorkOrderInfo.getWorkOrderNum()));
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void getDistrubuteUserSuccess(WorkOrderCommissionInfo workOrderCommissionInfo) {
        // TODO: 2016/11/7 判断时间是否是结算当天
        if (StringUtil.getDateFromStr("yyyy-MM-dd", workOrderCommissionInfo.getCreateTime(), workOrderCommissionInfo.getSysDate()) == 0) {
            //有效
            btnCommission.setEnabled(true);
            btnCommission.setVisibility(View.VISIBLE);
            adapter.setEnable(true);
        } else {
            btnCommission.setEnabled(false);
            btnCommission.setVisibility(View.GONE);
            adapter.setEnable(false);
        }
        if ( TextUtils.isEmpty( workOrderCommissionInfo.getWorkOrderNum() ) ) return;
        this.workOrderCommissionInfo = workOrderCommissionInfo;
        String commonNum = workOrderCommissionInfo.getCommonNum();
        commonMoney = Double.parseDouble(AppUtility.isNotEmpty(commonNum)? commonNum : "0" );
        tvMoney.setText(new BigDecimal(commonMoney).setScale(1, BigDecimal.ROUND_HALF_UP).toString());// 施工提成
        tvMoneyMine.setText( new BigDecimal(workOrderCommissionInfo.getCommissionAmount()).setScale(1, BigDecimal.ROUND_HALF_UP).toString() );
        tvNumber.setText(workOrderCommissionInfo.getWorkOrderNum()); // 工单编号
        List<AssistUserInfo> assistUserInfoList = workOrderCommissionInfo.getAssistList();  //
        // 施工人员集合
        for (int i = 0; i < assistUserInfoList.size(); i++) {
            AssistUserInfo assistUserInfo = assistUserInfoList.get(i);
            DistrCommissionModel model = new DistrCommissionModel();
            model.setCommissionAmount(assistUserInfo.getAssistTotalAmount());
            model.setUserName(assistUserInfo.getAssistUserName());
            model.setUserNum(assistUserInfo.getAssistUserNum());
            list.add(model);
            ItemTextWatcher itemTextWatcher = new ItemTextWatcher(model, this);
            adapter.addItemTextWatcherList(itemTextWatcher);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showTips(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void saveDistrubutionSuccess() {
//        保存分配成功后
        EventBus.getDefault().post(C.EventKey.REFRESH_DISTRUBUTION);
        finish();
    }

    @OnClick(R.id.btn_commission)
    public void onClick(View view) {
        if (view.getId() == R.id.btn_commission) {
            jsonArrayString = new Gson().toJson(adapter.getDatas());
            params.setJsonArrayString(jsonArrayString);
            params.setWorkOrderNum(mWorkOrderInfo.getWorkOrderNum());
            params.setCommissionInfoNum(workOrderCommissionInfo == null? "": workOrderCommissionInfo.getCommissionInfoNum());
            presenter.saveDistrubution(app.getApiService().saveDistrubution(app.getCurrentUserNum(), params));

        }
    }

    @Override
    public void afterChanged() {
        double remanent = commonMoney - adapter.getAdapterMoney();
        if (remanent < 0) {
            AppUtility.showToastMsg("分配金额不正确，请输入正确的金额");
        }
        tvMoneyMine.setText("¥"+ (remanent < 0? "0" : new BigDecimal(remanent).setScale(1,
                BigDecimal.ROUND_HALF_UP)).toString());
    }
}
