package com.ruanyun.czy.client.view.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.data.api.ApiManager;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SystemRemindParams;
import com.ruanyun.chezhiyi.commonlib.presenter.MyPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.MyMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.AccountMangermentActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.SystemRemindActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderListActivity;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.ClientMyGridItemDecoration;
import com.ruanyun.czy.client.view.adapter.ClientMyRecyclerViewAdapter;
import com.ruanyun.czy.client.view.ui.account.AccountBookActivity;
import com.ruanyun.czy.client.view.ui.account.ActivityCommonUserTelephone;
import com.ruanyun.czy.client.view.ui.account.OpinionFeedBackActivity;
import com.ruanyun.czy.client.view.ui.my.ActivityListActivity;
import com.ruanyun.czy.client.view.ui.my.AddCarActivity;
import com.ruanyun.czy.client.view.ui.my.DiscountCouponActivity;
import com.ruanyun.czy.client.view.ui.my.IntegralManagementActivity;
import com.ruanyun.czy.client.view.ui.my.MembershipCardActivity;
import com.ruanyun.czy.client.view.ui.my.MyBookingActivity;
import com.ruanyun.czy.client.view.ui.my.MyCrowdFundingActivity;
import com.ruanyun.czy.client.view.ui.my.OrderListActivity;
import com.ruanyun.czy.client.view.ui.my.SettingActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * @author ycw
 *         我的界面
 */
public class MyFragment extends BaseFragment implements MyMvpView, SwipeRefreshLayout.OnRefreshListener {

    /**
     * 刷新账户
     */
    public static final int REQUEST_CODE_REFRESH_ACCOUNT = 290;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.iv_my_user_photo)
    ImageView imageView;
    @BindView(R.id.tv_my_user_name)
    TextView tvMyUserName;
    @BindView(R.id.rcv_my)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_my_idiograph)
    TextView tvMyIdiograph;
    @BindView(R.id.tv_my_membership)
    TextView tvMyMembership;
    @BindView(R.id.tv_my_score)
    TextView tvMyScore;
    @BindView(R.id.bga_my_remind)
    BGABadgeImageView bgaMyRemind;
    private ClientMyRecyclerViewAdapter rvAdapter;
    private MyPresenter myPresenter = new MyPresenter();
    private SystemRemindParams systemRemindParams = new SystemRemindParams();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        myPresenter.attachView(this);
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_my, container,
                false);
        ButterKnife.bind(this, mContentView);
        setAdapter();
        return mContentView;
    }


    private void setAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //自定义分割线
        mRecyclerView.addItemDecoration(new ClientMyGridItemDecoration(getActivity()));
        //RecyclerView设置Adapter
        List<HomeIconInfo> functions = DbHelper.getInstance().getHomeIconInfoList(C.ModuleType
                .MODULE_TYPE_CLIENT_MINE);
        LogX.d(TAG, "----我的界面------>\n " + functions.toString());
        rvAdapter = new ClientMyRecyclerViewAdapter(getActivity(), functions);
        mRecyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int
                    position) {
                onFunctionClick(((HomeIconInfo) o).getModuleNum());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o,
                                           int position) {
                return false;
            }
        });
    }

    /**
     * 功能图标点击事件
     *
     * @param moduleNum
     */
    public void onFunctionClick(String moduleNum) {
        String url;
        Intent intent;
        switch (moduleNum) {
            case "4"://签到有礼
                url = String.format(FileUtil.getFileUrl(C.ApiUrl.URL_SIGN), app.getCurrentUserNum());
                intent = AppUtility.getWebIntent(mContext, url, "签到有礼");
                intent.putExtra(C.IntentKey.NEED_SHARE, false);
                // 返回刷新
                startActivityForResult(intent, REQUEST_CODE_REFRESH_ACCOUNT);

//                intent = new Intent(mContext, WebViewActivity.class);
//                intent.putExtra(C.IntentKey.WEB_URL, url);
//                intent.putExtra(C.IntentKey.TITLE, "签到有礼");
//                intent.putExtra(C.IntentKey.NEED_SHARE, false);
//                startActivityForResult(intent, REQUEST_CODE_REFRESH_ACCOUNT);
                break;
            case "5"://记账本
                showActivity(AccountBookActivity.class);
                break;
            case "6"://优惠卷
                showActivity(DiscountCouponActivity.class);
                break;
            case "12"://我的爱车
                showActivity(AddCarActivity.class);
                break;
            case "13"://我的工单
                showActivity(WorkOrderListActivity.class);
                break;
            case "14"://我的订单
                showActivity(OrderListActivity.class);
                break;
            case "15"://我的预约
                showActivity(MyBookingActivity.class);
                break;
            case "16"://我的活动
                showActivity(ActivityListActivity.class);
                break;
            case "17"://我的众筹
                showActivity(MyCrowdFundingActivity.class);
                break;
            case "18"://常用电话
                showActivity(ActivityCommonUserTelephone.class);
                break;
            case "19"://分享好友
                url = String.format(FileUtil.getFileUrl(C.ApiUrl.URL_SHARE), app.getCurrentUserNum());
                intent = AppUtility.getWebIntent(mContext, url, "分享好友");
                intent.putExtra(C.IntentKey.NEED_SHARE, false);
                showActivity(intent);
//                intent = new Intent(mContext, ShareWebViewActivity.class);
//                intent.putExtra(C.IntentKey.WEB_URL, url);
//                intent.putExtra(C.IntentKey.TITLE, "分享好友");
//                intent.putExtra(C.IntentKey.NEED_SHARE, false);
//                showActivity(intent);
                break;
            case "20"://意见反馈
                showActivity(OpinionFeedBackActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_REFRESH_ACCOUNT:
                    app.beanCacheHelper.getAccountMoney();
                    break;
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerBus();
        initView();
        app.beanCacheHelper.getAccountMoney();
        getSystemRemind();
        initRefreshView();
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshView() {
        mSwipeRefreshLayout.setColorSchemeResources(com.ruanyun.chezhiyi.commonlib.R.color.holo_blue_bright, com.ruanyun.chezhiyi.commonlib.R.color.holo_green_light,
                com.ruanyun.chezhiyi.commonlib.R.color.holo_orange_light, com.ruanyun.chezhiyi.commonlib.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 调用 获取系统 未读 消息列表
     */
    private void getSystemRemind() {
        systemRemindParams.setIsPush(RemindInfo.STATUS_PUSHED);
        systemRemindParams.setIsRead(RemindInfo.READ_NO);
        myPresenter.getSystemRemindList(app.getApiService().getSystemRemindList(app.getCurrentUserNum(), systemRemindParams));
    }

    private void initView() {
        User user = app.getUser();
        if (user == null) return;
        tvMyUserName.setText(user.getNickName());
//        Drawable img = mContext.getResources().getDrawable(user.getUserSex() == C.USER_SEX_MAN ? R
//                .drawable.icon_white_male : R.drawable.icon_white_female);
//        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
//        tvMyUserName.setCompoundDrawables(null, null, img, null); //设置右图标
        tvMyUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexWhiteResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexWhiteResId()), null);
        Glide.with(mContext).load(ApiManager.API_URL + user.getUserPhoto()).error(R.drawable.default_imge_small).into(imageView);
        tvMyIdiograph.setText(user.getPersonalNote());
    }

    @OnClick({R.id.tv_my_membership, R.id.tv_my_score, R.id.rl_account, R.id.iv_my_set, R.id
            .bga_my_remind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_membership://会员卡
                showActivity(MembershipCardActivity.class);
                break;
            case R.id.tv_my_score://积分兑换
                showActivity(IntegralManagementActivity.class);
                break;
            case R.id.rl_account://账户管理
                showActivity(AccountMangermentActivity.class);
                break;
            case R.id.iv_my_set: //系统设置
                showActivity(SettingActivity.class);
                break;
            case R.id.bga_my_remind: // 通知消息
                bgaMyRemind.hiddenBadge();
                Intent intent = new Intent(mContext, SystemRemindActivity.class);
                intent.putExtra(C.IntentKey.SYSTEM_TYPE, SystemRemindActivity.SYSTEM_NOTICE);
                showActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    /**
     * 刷新数据
     *
     * @param userResult
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void update(String userResult) {
        if (C.EventKey.UPDATE_USER_INFO.equals(userResult)) {
            initView();
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread, priority = 124)
    public void getAccountMoneySuccess(Event<AccountMoneyInfo> event) {
        if (event != null && event.key.equals(C.EventKey.ACCOUNT_MONEY)) {
            AccountMoneyInfo moneyInfo = event.value;
            app.setAccountMoneyInfo(moneyInfo);
            tvMyMembership.setText(getString(R.string.money, String.valueOf(new DecimalFormat("#0.00").format(moneyInfo.getAccountBalance()))));
            tvMyScore.setText(getString(R.string.integral, String.valueOf(new DecimalFormat("#0").format(moneyInfo.getScoreBalance()))));
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void getSystemReminded(String event) {
        if (event.equals(C.EventKey.REMINDTYPE)) {
            getSystemRemind();
        }
        //EventBus.getDefault().removeStickyEvent(C.EventKey.REMINDTYPE);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void refreshLayout(Event<String> event) {
        if ( event.key.equals(C.EventKey.ACCOUNT_MONEY) && event.value.equals(C.EventKey.ACCOUNT_MONEY) ) {
            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void getSystemRemindListSuccess(int totalCount) {
        if (totalCount>0) {
            bgaMyRemind.showCirclePointBadge();
        }
    }

    @Override
    public void onRefresh() {
        // 刷新账户信息
        app.beanCacheHelper.getAccountMoney();
    }
}
