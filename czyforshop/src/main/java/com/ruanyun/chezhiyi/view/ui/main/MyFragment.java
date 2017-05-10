package com.ruanyun.chezhiyi.view.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.data.api.ApiManager;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.MyListCount;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.model.ReportInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SystemRemindParams;
import com.ruanyun.chezhiyi.commonlib.presenter.HomeNoticePresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.ShopMyPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.NoticeMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ShopMyMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.AccountMangermentActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.CaseLibActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.MyCaseLibActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.SystemRemindActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderListActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.view.adapter.ShopMyGridItemDecoration;
import com.ruanyun.chezhiyi.view.adapter.ShopMyRecyclerViewAdapter;
import com.ruanyun.chezhiyi.view.ui.mine.DiscountCouponActivity;
import com.ruanyun.chezhiyi.view.ui.mine.GongDanShuActivity;
import com.ruanyun.chezhiyi.view.ui.mine.InsteadOrderActivity;
import com.ruanyun.chezhiyi.view.ui.mine.SettingActivity;
import com.ruanyun.chezhiyi.view.ui.mine.ShiGongTiChengActivity;
import com.ruanyun.chezhiyi.view.ui.mine.XiaoShouTiChengActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static android.app.Activity.RESULT_OK;

/**
 * @author hdl
 *         我的界面
 */
public class MyFragment extends BaseFragment implements MultiItemTypeAdapter.OnItemClickListener, ShopMyMvpView, NoticeMvpView, SwipeRefreshLayout.OnRefreshListener {

    public static final int REQUEST_CODE_REFRESH = 578;
    private static final int REQUEST_CODE_LISTCOUNT_REFRESH = 779;
    //    public List<FunctionMessage> functions = new ArrayList<>();
    @BindView(R.id.rcv_my)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_my_user_photo)
    CircleImageView circleImageView;
    @BindView(R.id.tv_my_user_name)
    TextView tvMyUserName;
    //    @BindView(R.id.tv_my_maintenance)
//    TextView tvMyMaintenance;
//    @BindView(R.id.tv_my_working_condition)
//    TextView tvMyWorkingCondition;
//    @BindView(R.id.tv_my_upkeep)
//    TextView tvMyUpkeep;
    @BindView(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @BindView(R.id.tv_my_type)
    TextView tv_my_type;
    @BindView(R.id.flow_view)
    TextView flowView;
    @BindView(R.id.tv_push_money)
    TextView tvPushMoney;
    @BindView(R.id.tv_sales_commissions)
    TextView tvSalesCommissions;
    @BindView(R.id.tv_my_user_nickname)
    TextView nickname;
    @BindView(R.id.tv_gongdan_number)
    TextView tvGongDanNumber;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    private ShopMyRecyclerViewAdapter rvAdapter;
    private ShopMyPresenter myPresenter = new ShopMyPresenter();
    private SystemRemindParams systemRemindParams = new SystemRemindParams();
    private List<HomeIconInfo> functions;
    private HomeNoticePresenter homeNoticePresenter = new HomeNoticePresenter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_my, container,
                false);
        myPresenter.attachView(this);
        ButterKnife.bind(this, mContentView);
        homeNoticePresenter.attachView(this);
        return mContentView;
    }


    private void setAdapter() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 12);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 12;
                } else if (position == 1 | position == 2 | position == 3 | position == 4) {
                    return 3;
                } else return 4;
            }

        });
        mRecyclerView.setLayoutManager(manager);
        //自定义分割线
        mRecyclerView.addItemDecoration(new ShopMyGridItemDecoration(getActivity()));
        //RecyclerView设置Adapter
        functions = DbHelper.getInstance().getHomeIconInfoList(C.ModuleType
                .MODULE_TYPE_SHOP_MINE);
        LogX.d(TAG, "find  HomeIconInfo ---> \n" + functions.toString());
        rvAdapter = new ShopMyRecyclerViewAdapter(getActivity(), functions);
        mRecyclerView.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerBus();
        setAdapter();
        initView();
        getSystemRemind();
        getMyListCount();
        getPersonOrder();//获取销售提成，施工提成
        initRefreshView();
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshView() {
        refreshlayout.setColorSchemeResources(com.ruanyun.chezhiyi.commonlib.R.color.holo_blue_bright, com.ruanyun.chezhiyi.commonlib.R.color.holo_green_light,
                com.ruanyun.chezhiyi.commonlib.R.color.holo_orange_light, com.ruanyun.chezhiyi.commonlib.R.color.holo_red_light);
        refreshlayout.setOnRefreshListener(this);
    }


    /**
     * 获取销售提成，施工提成
     */
    private void getPersonOrder() {
        getReportInfo(C.EventKey.REFRESH_DISTRUBUTION);//homeNoticePresenter  销售提成/施工提成/用户数
    }

    /**
     * 获取销售提成，施工提成
     *
     * @param event
     */
    @Subscribe
    public void getReportInfo(String event) {
        if (event.equals(C.EventKey.REFRESH_DISTRUBUTION)) {
            //  获取	销售提成/施工提成/用户数
            homeNoticePresenter.getReportInfo(app.getApiService().report(app.getCurrentUserNum()));
        }
    }

    //销售提成/施工提成/用户数跳转
    @OnClick({R.id.tv_push_money, R.id.tv_sales_commissions, R.id.tv_gongdan_number})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_push_money) {  //施工提成
//            toWebCount(C.CountType.EXECUTION, mContext, WebViewActivity.SGTC/*"施工提成"*/);
            showActivity(ShiGongTiChengActivity.class);
        } else if (id == R.id.tv_sales_commissions) {  //销售提成
//            toWebCount(C.CountType.MARKET, mContext, WebViewActivity.XSTC/*"销售提成"*/);
            showActivity(XiaoShouTiChengActivity.class);
        } else if (id == R.id.tv_gongdan_number) {  // 工单数
//            toWebCount(C.CountType.NUMBER, mContext, WebViewActivity.YHS/*"用户数"*/);
            showActivity(GongDanShuActivity.class);
        } else {
            onFunctionClick((String) view.getTag());
        }
    }

    /**
     * 跳转到对应web页
     *
     * @param executionType 类型
     * @param mContext
     * @param webName
     */
    private void toWebCount(String executionType, Context mContext, String webName) {
        String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_COUNT), app
                .getCurrentUserNum(), StringUtil.getTimeStr("yyyyMM", new Date()), executionType);
        Intent webIntent = AppUtility.getWebIntent(mContext, url, webName);
        webIntent.putExtra(C.IntentKey.WEB_COUNT_TYPE, executionType);
        webIntent.putExtra(C.IntentKey.NEED_SHARE, false);
        showActivity(webIntent);
    }

    /**
     * 调用 获取系统 未读 消息列表
     */
    private void getSystemRemind() {
        systemRemindParams.setIsPush(RemindInfo.STATUS_PUSHED);
        systemRemindParams.setIsRead(RemindInfo.READ_NO);
        myPresenter.getSystemRemindList(app.getApiService().getSystemRemindList(app.getCurrentUserNum(), systemRemindParams));
    }

    /**
     * 调用 获取工单个数
     */
    private void getMyListCount() {
        myPresenter.myListCount(app.getApiService().myListCount(app.getCurrentUserNum()));
    }

    private void initView() {
        User user = app.getUser();
        if (user == null) return;
//        Drawable img = mContext.getResources().getDrawable(user.getUserSex() == C.USER_SEX_MAN ? R.drawable
//                .icon_white_male : R.drawable.icon_white_female);
//        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
//        tvMyUserName.setCompoundDrawables(null, null, img, null); //设置右图标
        tvMyUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, user.getUserSexWhiteResId() == 0 ? null : ContextCompat.getDrawable(mContext, user.getUserSexWhiteResId()), null);
        tvMyUserName.setText(user.getNickName());
        Glide.with(mContext).load((ApiManager.API_URL + user.getUserPhoto()).trim()).error(R.drawable.default_imge_small).into(circleImageView);
        //标签
        String workStatus = DbHelper.getInstance().getParentName(user.getWorkStatus(), C
                .ParentCode.WORK_STATUS);
        String userLabel = user.getLabelCode();
        List<String> allLabels = new ArrayList<>();
        allLabels.add(workStatus);
        List<String> label = Arrays.asList(userLabel.split(","));
        for (int i = 0; i < label.size(); i++) {
            allLabels.add(DbHelper.getInstance().getParentName(label.get(i), C.ParentCode.USER_LABEL));
        }

        LogX.e("测试啦，", allLabels.toString());
        if (allLabels.size() > 0) {
            tv_my_type.setText(allLabels.get(0).toString());
        }
        if (allLabels.size() > 1) {
            flowView.setText(allLabels.get(1).toString() + " ");
        }
        nickname.setText(app.getUser().getPersonalNote());
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
        onFunctionClick(((HomeIconInfo) o).getModuleNum());
    }

    /**
     * 刷新用户 数据
     *
     * @param userResult
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void update(String userResult) {
        if (C.EventKey.UPDATE_USER_INFO.equals(userResult)) {
            initView();
        }
    }

    /**
     * 更新界面 工单的数量
     *
     * @param userResult
     */
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void updateUI(String userResult) {
        if (C.EventKey.UPDATE_WORKER_NUMBER.equals(userResult)) {
            getMyListCount();
        }
    }

    /**
     * 技师端  我的  功能图标点击事件
     *
     * @param moduleNum
     */
    public void onFunctionClick(String moduleNum) {
        switch (moduleNum) {
            case "42"://代下单管理
                showActivity(InsteadOrderActivity.class);
                break;
            case "43"://我的案例
                showActivity(MyCaseLibActivity.class);
                break;
            case "44"://系统设置
                showActivity(SettingActivity.class);
                break;
            case "45"://优惠卷管理
                showActivity(DiscountCouponActivity.class);
                break;
            case "46"://企业案例
                showActivity(CaseLibActivity.class);
                break;
            case "47"://系统消息
//                showActivity(SystemRemindActivity.class);
                Intent intent = new Intent(mContext, SystemRemindActivity.class);
                intent.putExtra(C.IntentKey.SYSTEM_TYPE, SystemRemindActivity.SYSTEM_NOTICE);
                startActivityForResult(intent, REQUEST_CODE_REFRESH);
                break;
            case "38"://待服务
                toWorkOrderList(1);
                break;
            case "39"://服务中
                toWorkOrderList(2);
                break;
            case "40"://结算中
                toWorkOrderList(3);
                break;
            case "41"://已完成
                toWorkOrderList(4);
                break;
            case "1"://服务工单
                toWorkOrderList(0);// 全部
                break;

        }
    }

    /**
     * 跳转到服务工单
     *
     * @param value
     */
    private void toWorkOrderList(int value) {
        Intent intent = new Intent(mContext, WorkOrderListActivity.class);
        intent.putExtra("item", value);
//        showActivity(intent);
//                返回刷新
        startActivityForResult(intent, REQUEST_CODE_LISTCOUNT_REFRESH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_LISTCOUNT_REFRESH:  //  刷新工单数量
                    getMyListCount();
                    break;
                case REQUEST_CODE_REFRESH:  // 刷新系统消息数量
                    getSystemRemind();
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int
            position) {
        return false;
    }

    /**
     * 跳转 账户管理界面
     */
    @OnClick(R.id.rl_person_info)
    public void onClick() {
        showActivity(AccountMangermentActivity.class);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
        homeNoticePresenter.detachView();
    }

    /**
     * 获取系统消息数量成功
     *
     * @param totalCount
     */
    @Override
    public void getSystemRemindListSuccess(int totalCount) {
        // 系统消息添加圆圈数量
        for (int i = 0, size = functions.size(); i < size; i++) {
            if (functions.get(i).getModuleNum().equals("47")) { //系统消息数量
                functions.get(i).setNumber(totalCount);
                rvAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void getmyListCountSuccess(MyListCount obj) {

        for (int i = 0; i < functions.size(); i++) {
            if (functions.get(i).getModuleNum().equals("38")) { // 待服务
                functions.get(i).setNumber(obj.getDfwCount());
                rvAdapter.notifyItemChanged(i);
            }
            if (functions.get(i).getModuleNum().equals("39")) { // 服务中
                functions.get(i).setNumber(obj.getFwzCount());
                rvAdapter.notifyItemChanged(i);
            }
            if (functions.get(i).getModuleNum().equals("40")) { // 结算中
                functions.get(i).setNumber(obj.getJszCount());
                rvAdapter.notifyItemChanged(i);
            }
        }
    }


//    @Override
//    public void setNoticeInfo(NoticeInfo noticeInfo) {
//
//    }

    @Override
    public void getReportInfoSuccess(ReportInfo reportInfo) {
        if (reportInfo == null) return;
        refreshlayout.setRefreshing(false);
        tvPushMoney.setText(getText("施工提成\n¥", new BigDecimal(reportInfo.getSgtcAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvSalesCommissions.setText(getText("销售提成\n¥", new BigDecimal(reportInfo.getXstcAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        tvGongDanNumber.setText(getText("工单数\n", String.valueOf(reportInfo.getUserCount())));
    }

    @Override
    public void getReportInfoError() {
        refreshlayout.setRefreshing(false);
        tvPushMoney.setText(getText("施工提成\n¥", "0.00"));
        tvSalesCommissions.setText(getText("销售提成\n¥", "0.00"));
        tvGongDanNumber.setText(getText("工单数\n", "0"));
    }

    @Override
    public void getReportInfoResult() {
//        if (refreshlayout != null) {
//            refreshlayout.setRefreshing(false);
//        }
    }

    /**
     * @param name
     * @param num
     * @return
     */
    private SpannableStringBuilder getText(String name, String num) {
        SpannableStringBuilder spb = new SpannableStringBuilder();
        spb.append(name).append(num).setSpan(new RelativeSizeSpan(1.6f), name.length(), name.length() + num.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return spb;
    }

    @Override
    public void onRefresh() {
        getPersonOrder();//获取销售提成，施工提成
    }
}
