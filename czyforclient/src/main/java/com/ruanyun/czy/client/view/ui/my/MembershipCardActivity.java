package com.ruanyun.czy.client.view.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.RechangeInfo;
import com.ruanyun.chezhiyi.commonlib.model.RechargeListInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.RechangeParams;
import com.ruanyun.chezhiyi.commonlib.model.params.RechargeListParams;
import com.ruanyun.chezhiyi.commonlib.presenter.RechargePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.RechargeMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.RechargeListAdapter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * 会员卡
 * Created by Sxq on 2016/10/10.
 */
public class MembershipCardActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener, RechargeMvpView {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edit_acoument)
    EditText editAcoument;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.iv_user_photo)
    CircleImageView ivUserPhoto;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rl_account)
    RelativeLayout rlAccount;
    private RechargeListParams params = new RechargeListParams();
    private RechargeListAdapter adapter;

    RechargePresenter rechargePresenter = new RechargePresenter();
    private Call call;
    RechangeParams rechangeParams = new RechangeParams();
    private String amount;//金额
    private String discountMealNum = null;//充值套餐编号，如果是手工输入充值金额则为null
    private int score;//积分

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerBus();
        setContentView(R.layout.activity_membership_card);
        ButterKnife.bind(this);
        rechargePresenter.attachView(this);
        initView();
        initRefreshLayout();
        refreshLayout.beginRefreshing();
        refreshLayout.setPullDownRefreshEnable(false);
    }


    private void initView() {
        topbar.setTttleText("会员卡")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("明细")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        User user = app.getUser();
        tvUserName.setText(user ==null?"": user.getNickName() == null? "": user.getNickName());
        //tvBalance.setText("余额：");//余额  user暂时无
        tvBalance.setText(String.format("余额：%s元", new DecimalFormat("#0.00").format(app.getAccountMoneyInfo() ==null?0: app.getAccountMoneyInfo()
                .getAccountBalance())) );
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(app.getUser().getUserPhoto()), ivUserPhoto, com.ruanyun.chezhiyi.commonlib.R.drawable.default_img);
        Glide.with(mContext)
                .load(FileUtil.getImageUrl(user ==null?"": user.getUserPhoto() == null? "": user.getUserPhoto()))
                .error(R.drawable.default_img)
                .into(ivUserPhoto);
        adapter = new RechargeListAdapter(mContext, new ArrayList<RechargeListInfo>(), R.layout.grid_item_recharge);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                RechargeListInfo rechargeListInfo = adapter.getItem(position);
                amount = rechargeListInfo.getAmount().toString();
                discountMealNum = rechargeListInfo.getDiscountMealNum();
                score = rechargeListInfo.getScore();
                rechangeResult();
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MainThread, priority = 124)
    public void getAccountMoneySuccess(Event<AccountMoneyInfo> event) {
        if (event != null && event.key.equals(C.EventKey.ACCOUNT_MONEY) ) {
            AccountMoneyInfo moneyInfo = event.value;
            app.setAccountMoneyInfo(moneyInfo);
            tvBalance.setText(String.format("余额：%s元", new DecimalFormat("#0").format(app.getAccountMoneyInfo() ==null?0: app.getAccountMoneyInfo().getAccountBalance())));
        }
    }

    /**
     * topbar 点击事件
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            //明细
            Intent intent = new Intent(mContext, IntegralExchangeListActivity.class);
            intent.putExtra(C.IntentKey.ACCOUNT_TYPE, IntegralExchangeListActivity.RECHARGE_TYPE);
            startActivity(intent);
        }
    }


    @Override
    public Call loadData() {
        params.setStatus(1);
        params.setPageNum(currentPage);
//        params.setOrderBy("amount");
        return app.getApiService().rechargeList(app.getCurrentUserNum(), params);
    }


    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
        adapter.notifyDataSetChanged();
    }


    @OnClick(R.id.btn_recharge)
    public void onClick() {
        String money = editAcoument.getText().toString();
        if (!TextUtils.isEmpty(money)) {
            double mon = Double.parseDouble(money);
            if (mon == 0) {
                AppUtility.showToastMsg("输入充值金额不能为0");
                return;
            }
            amount = money;
            rechangeResult();

        } else {
            AppUtility.showToastMsg("请输入充值金额");
        }

    }

    //充值
    private void rechangeResult() {
        rechangeParams.setUserNum(app.getCurrentUserNum());
        rechangeParams.setScore(score);
        rechangeParams.setAmount(new BigDecimal(amount));
        rechangeParams.setDiscountMealNum(discountMealNum);
        call = app.getApiService().rechanger(app.getCurrentUserNum(), rechangeParams);
        rechargePresenter.Recharge(call);
    }


    @Override
    public void onSuccess(ResultBase<RechangeInfo> rechargeInfos) {
        //AppUtility.showToastMsg(rechargeInfos.getMsg()+"++");
        showActivity(AppUtility.getPayIntent(rechargeInfos.getObj().getOrderInfo(),  mContext));
    }

    @Override
    public void onError(ResultBase resultBase) {
        AppUtility.showToastMsg(resultBase.getMsg());
    }


    @Override
    public void onFail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Override
    public Context getContext() {
        return null;
    }
}
