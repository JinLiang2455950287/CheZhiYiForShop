package com.ruanyun.chezhiyi.view.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.GongGaoInfo;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.PerssionBean;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.model.ReportInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.RecommentParams;
import com.ruanyun.chezhiyi.commonlib.model.params.SystemRemindParams;
import com.ruanyun.chezhiyi.commonlib.presenter.AnnouncementPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.HomeNoticePresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.HomePerssionPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.HomeWaitAreaCountPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.StoreInfoPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.CommentUtils;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.AnnouncementMvpView;
import com.ruanyun.chezhiyi.commonlib.view.HomePerssionMvpView;
import com.ruanyun.chezhiyi.commonlib.view.HomeWaitAreaCountView;
import com.ruanyun.chezhiyi.commonlib.view.NoticeMvpView;
import com.ruanyun.chezhiyi.commonlib.view.StoreInfoMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.HomeAdverBanner;
import com.ruanyun.chezhiyi.commonlib.view.adapter.HomeRecommendAdapter;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.RecommendListActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.ShowContentActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.SystemRemindActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.RollableTextView;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPager;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyViewPagerAdapter;
import com.ruanyun.chezhiyi.view.ui.home.AppointMentDealActivity;
import com.ruanyun.chezhiyi.view.ui.home.DayAppointmentActivity;
import com.ruanyun.chezhiyi.view.ui.home.HomeFunctionView;
import com.ruanyun.chezhiyi.view.ui.home.MemberSearchActivity;
import com.ruanyun.chezhiyi.view.ui.home.OpenOrderActivity;
import com.ruanyun.chezhiyi.view.ui.home.RebackPayActivity;
import com.ruanyun.chezhiyi.view.ui.home.ShopCollectActivity;
import com.ruanyun.chezhiyi.view.ui.home.StationLookActivity;
import com.ruanyun.chezhiyi.view.ui.home.WaitAreaActivity;
import com.ruanyun.chezhiyi.view.ui.workorder.CustomerReceptionActivity;
import com.ruanyun.chezhiyi.view.ui.workorder.QuickWashCarActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * 技师端的首页
 * Created by ycw on 2016/8/23.
 */
public class HomeFragment extends /*Refresh*/BaseFragment implements StoreInfoMvpView, OnItemClickListener, AnnouncementMvpView,
        HomeFunctionView.ItemViewClickListener, ViewPager.OnPageChangeListener, NoticeMvpView, SwipeRefreshLayout.OnRefreshListener,
        HomePerssionMvpView, HomeWaitAreaCountView {

    @BindView(R.id.rtv_flash_news)
    RollableTextView rtvFlashNews;//滚动消息
    //    @BindView(R.id.recyclerView_statement)
//    RecyclerView recyclerViewStatement;
    @BindView(R.id.iv_recommend)
    ImageView ivRecommend;
    @BindView(R.id.tv_all_project)
    TextView tvAllProject;
    @BindView(R.id.rl_all_project)
    RelativeLayout rlAllProject;
    @BindView(R.id.recryclerView_home_recommend)
    RecyclerView recyclerViewHomeRecommend;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    //BGARefreshLayout refreshlayout;
//    @BindView(R.id.iv_quick_entry)
//    ImageView ivQuickEntry;
    @BindView(R.id.viewpager)
    LazyViewPager viewpager;
    @BindView(R.id.loPageTurningPoint)
    LinearLayout loPageTurningPoint;
    @BindView(R.id.open_oder)
    TextView openOder;
    @BindView(R.id.wait_area_count)
    TextView waitAreaCount;
    @BindView(R.id.wait_settlement_count)
    TextView waitSettlementCount;
    @BindView(R.id.wait_quality_count)
    TextView waitQualityCount;
    @BindView(R.id.wait_area)
    TextView waitArea;
    @BindView(R.id.settlement_area)
    TextView settlementArea;
    @BindView(R.id.quality_area)
    TextView qualityArea;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;// 广告的等待进度条
    @BindView(R.id.convenient_banner)
    ConvenientBanner convenientBanner;//  广告
    @BindView(R.id.home_yuyue)
    LinearLayout homeYuyue;
    @BindView(R.id.home_rebackpay)
    LinearLayout homeRebackpay;
    @BindView(R.id.home_wake)
    LinearLayout homeWake;
    @BindView(R.id.home_dtyy)
    TextView homeDtyy;
    @BindView(R.id.home_gzzt)
    TextView homeGzzt;
    @BindView(R.id.home_hukc)
    TextView homeHukc;
    @BindView(R.id.home_mdtj)
    TextView homeMdtj;
    @BindView(R.id.tv_appointmemtcount)
    TextView tv_appointmemtcount;
    @BindView(R.id.tv_repaycount)
    TextView tv_repaycount;
    @BindView(R.id.tv_wakecount)
    TextView tv_wakecount;

    private List<Integer> listPic = new ArrayList<>();//广告数据源
    private HomeRecommendAdapter homeRecommendAdapter;
    private StoreInfoPresenter storeInfoPresenter = new StoreInfoPresenter();//门店的详细
    private HomeNoticePresenter homeNoticePresenter = new HomeNoticePresenter();
    private AnnouncementPresenter announcementPresenter = new AnnouncementPresenter();//公告 今日预约数量 退款申请数量 提醒数量
    private RecommentParams params = new RecommentParams();//获取推荐项目的参数
    private List<View> tabs = new ArrayList<>();
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();  //滚动指示器
    private int[] page_indicatorId = new int[]{R.drawable.shape_ring_indicator_unselect, R.drawable.shape_ring_indicator_selected};
    private List<GongGaoInfo> gongGaoInfoList = new ArrayList<>();
    private SystemRemindParams systemRemindParams = new SystemRemindParams();
    private HomePerssionPresenter homePerssionPresenter = new HomePerssionPresenter();//权限表
    private HomeWaitAreaCountPresenter homeWaitAreaCountPresenter = new HomeWaitAreaCountPresenter();//等候区数量

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
        storeInfoPresenter.detachView();
        homeNoticePresenter.detachView();
        announcementPresenter.detachView();
        homePerssionPresenter.detachView();
        homeWaitAreaCountPresenter.detachView();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mContentView);
        storeInfoPresenter.attachView(this);
        homeNoticePresenter.attachView(this);
        announcementPresenter.attachView(this);
        homePerssionPresenter.attachView(this);
        homeWaitAreaCountPresenter.attachView(this);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app.loadingDialogHelper.showLoading(getActivity(), "加载中...");
        initView();
        registerBus();
    }

    private void initView() {
        initRefreshView();
        List<HomeIconInfo> iconInfoList = DbHelper.getInstance().getHomeIconInfoList(C.ModuleType.MODULE_TYPE_SHOP_HOME);

        if (iconInfoList.size() < 7) {
            return;
        }

        int endPosition = iconInfoList.size() - 1;
        //前几个  快捷入口
        getHomeFunctionRecycler(iconInfoList.subList(1, endPosition - 5));

        //推荐项目  小图标
        setHomeIconRecommendView(iconInfoList.get(endPosition));
        //推荐项目列表数据
        setRecommendList();
        getUIData();
        //获取等候区数量  status=3;等候结算区status=8；等候质检区tatus=6
        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "3"), "3");
        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "6"), "6");
        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "8"), "8");
        //权限表
        homePerssionPresenter.getPerssionData(app.getApiService().getPerssion(app.getCurrentUserNum()));
        //今日预约数量
        announcementPresenter.getAppointMentCount(app.getApiService().getAppointMentCount(app.getCurrentUserNum()));

        // 今日申请退款数量
        announcementPresenter.getRePayCount(app.getApiService().getRePayCount(app.getCurrentUserNum()));

        // 提醒数量
        systemRemindParams.setIsPush(RemindInfo.STATUS_PUSHED);
        systemRemindParams.setIsRead(RemindInfo.READ_NO);
        systemRemindParams.setRemindType(RemindInfo.REMIND_TYPE_MAINTAIN + "," + RemindInfo.REMIND_TYPE_BIRTHDAY + "," + RemindInfo.REMIND_TYPE_BALANCE + "," + RemindInfo.REMIND_TYPE_MEMBERLOSE);
        announcementPresenter.getWakeCount(app.getApiService().getSystemRemindList(app.getCurrentUserNum(), systemRemindParams));

        // 滚动广告
        announcementPresenter.getGongGao(app.getApiService().getGongGaoInfo(app.getCurrentUserNum()));

        listPic.add(R.drawable.home_banner1);
        listPic.add(R.drawable.home_banner2);
        listPic.add(R.drawable.home_banner3);
        setadvertiseBanner();
    }

    /**
     * 设置滚动广告条
     */
    private void setadvertiseBanner() {
        progressbar.setVisibility(View.GONE);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public HomeAdverBanner createHolder() {
                return new HomeAdverBanner();
            }
        }, listPic)
                .setOnItemClickListener(this)
                .setPageIndicator(new int[]{R.drawable.indicator_unselected_shape,
                        R.drawable.indicator_selected_shape});
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
     * 推荐项目  小图标
     *
     * @param projectInfo
     */
    private void setHomeIconRecommendView(HomeIconInfo projectInfo) {
        tvAllProject.setText(projectInfo.getHomeIconName());
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(projectInfo.getAndroidPic()),
                ivRecommend);
        rlAllProject.setTag(projectInfo.getModuleNum());
    }

    /**
     * 推荐项目列表数据
     */
    private void setRecommendList() {
        GridLayoutManager homeManager = new GridLayoutManager(mContext, 1);
        recyclerViewHomeRecommend.setLayoutManager(homeManager);
        homeRecommendAdapter = new HomeRecommendAdapter(mContext, R.layout.list_item_project, new ArrayList<RecommendInfo>());
        recyclerViewHomeRecommend.setAdapter(homeRecommendAdapter);
        homeRecommendAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                RecommendInfo recommendInfo = (RecommendInfo) o;
                double payPrice = AppUtility.getPrice(recommendInfo.getGoodsType(), recommendInfo.getSalePrice(), recommendInfo.getActivityPrice());
                LogX.d(TAG, "---payPrice----->\n" + payPrice);
                AppUtility.showGoodsWebView(payPrice,
                        app.getCurrentUserNum(),
                        recommendInfo.getGoodsNum(),
                        recommendInfo.getGoodsType(),
                        recommendInfo.getGoodsNum(),
                        app.getCurrentUserNum(),
                        mContext,
                        recommendInfo.getTitle(),
                        recommendInfo.getRecommentProjectNum(),
                        recommendInfo.getMainPhoto(),
                        recommendInfo.getTitle());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
    }

    /**
     * 获取销售提成，施工提成
     */
    @Subscribe
    public void getReportInfo(String event) {
        if (event.equals(C.EventKey.REFRESH_DISTRUBUTION)) {
            //  获取	销售提成/施工提成/用户数
            homeNoticePresenter.getReportInfo(app.getApiService().report(app.getCurrentUserNum()));
        }
    }

    private void getUIData() {
        //获取推荐项目
        params.setRecommentProjectType(1);//1推荐项目   2猜你喜欢
        params.setPageNum(1);//当前页
        storeInfoPresenter.getRecommendList(app.getApiService().getRecommendList(app.getCurrentUserNum(), params));
        announcementPresenter.getGongGao(app.getApiService().getGongGaoInfo(app.getCurrentUserNum())); //获取通知通告（新）
        getReportInfo(C.EventKey.REFRESH_DISTRUBUTION);//homeNoticePresenter  销售提成/施工提成/用户数
        //店铺信息
        Call<ResultBase<StoreInfo>> call = app.getApiService().storeInfo(app.getCurrentUserNum(), app.storeNum);
        storeInfoPresenter.getStoreInfo(call);
    }

    /**
     * 快捷入口的图标的初始化
     */
    private void getHomeFunctionRecycler(List<HomeIconInfo> homeIconLink) {
        if (homeIconLink.size() == 0) {
            return;
        }
        int pageCount = (homeIconLink.size() / 4) + (homeIconLink.size() % 4 == 0 ? 0 : 1);  //需要的页数

        if (pageCount > 1) {
            loPageTurningPoint.setVisibility(View.VISIBLE);//翻页指示点的viewgroup
            setPageIndicator(pageCount, page_indicatorId);
        }
        for (int i = 0; i < pageCount; i++) {
            HomeFunctionView homeFunctionView = new HomeFunctionView(mContext);
            List<HomeIconInfo> iconInfos;
            if (i < pageCount - 1) {
                iconInfos = homeIconLink.subList(4 * i, 4 * i + 4);
            } else {
                iconInfos = homeIconLink.subList(4 * i, homeIconLink.size());
            }
            homeFunctionView.setDatas(iconInfos);
            homeFunctionView.setListener(this);
            tabs.add(homeFunctionView);
        }
        viewpager.setAdapter(new LazyViewPagerAdapter() {
            @Override
            protected View getItem(ViewGroup container, int position) {
                return tabs.get(position);
            }

            @Override
            public int getCount() {
                return tabs.size();
            }

        });
        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(this);
    }

    /**
     * 设置翻页指示器
     */
    private void setPageIndicator(int pageCount, int[] page_indicatorId) {
        loPageTurningPoint.removeAllViews();
        mPointViews.clear();
        for (int count = 0; count < pageCount; count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(mContext);
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty()) {
                pointView.setImageResource(page_indicatorId[1]);
            } else {
                pointView.setImageResource(page_indicatorId[0]);
            }
            mPointViews.add(pointView);
            loPageTurningPoint.addView(pointView);
        }
    }

/*======================StoreInfoPresenter==============================*/

    /**
     * 获取商铺信息
     */
    @Override
    public void setHeadViewData(StoreInfo storeInfo) {
        if (storeInfo != null) {
            app.setStoreInfo(storeInfo);
        }

    }

    /**
     * 获取推荐商品列表
     */
    @Override
    public void getRecommendInfoOnSuccess(List<RecommendInfo> result) {
        homeRecommendAdapter.setDatas(homeRecommendAdapter.getTypeNameList(result));
        homeRecommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void disMissLoadingView() {
        // 司机端使用
    }

    @Override
    public void showLoadingView() {
        // 司机端使用
    }

    @Override
    public void getFriendShipInfoSuccess(User user) {
        // 司机端使用
    }
      /*======================StoreInfoPresenter==============================*/

    /**
     * 技师端 首页  功能图标点击事件
     *
     * @param moduleNum
     */
    public void onFunctionClick(String moduleNum) {
        switch (moduleNum) {
            case "23"://工位查看
                showActivity(StationLookActivity.class);
                break;
            case "24"://客户接待
                showActivity(CustomerReceptionActivity.class);
                break;
            case "25"://当天预约
                showActivity(DayAppointmentActivity.class);
                break;
            case "26": //消息提醒
                Intent intent = new Intent(mContext, SystemRemindActivity.class);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "消息提醒");
                intent.putExtra(C.IntentKey.SYSTEM_TYPE, SystemRemindActivity.SYSTEM_MSG);
                showActivity(intent);
                break;
            case "28"://营业汇总
                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_YYE), app.getCurrentUserNum(), "1"), WebViewActivity.YYHZ);
                break;
            case "29"://会员统计
                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_HY), app.getCurrentUserNum()), WebViewActivity.HYTJ);
                break;
            case "30"://新增用户
                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_XZKH), app.getCurrentUserNum()), WebViewActivity.XZYH);
                break;
            case "31"://销售商品
                toWebView(String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REPORT_XSSP), app.getCurrentUserNum()), WebViewActivity.XSSP);
                break;
            case "48"://快速洗车
                showActivity(QuickWashCarActivity.class);
                break;
            case "9"://推荐项目
                showActivity(RecommendListActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * web界面
     *
     * @param url
     * @param title
     */
    private void toWebView(String url, String title) {
        Intent intent = AppUtility.getWebIntent(mContext, url, title);
        intent.putExtra(C.IntentKey.NEED_SHARE, false);
        showActivity(intent);
    }

    //开单，等候区，质检区，结算区     当天预约 工位状态 会员快查 门店统计
    @OnClick({R.id.home_gzzt, R.id.home_hukc, R.id.home_dtyy, R.id.home_mdtj, R.id.home_yuyue, R.id.home_rebackpay, R.id.home_wake, R.id.rl_all_project,
            R.id.open_oder, R.id.wait_area, R.id.settlement_area, R.id.quality_area, R.id.rtv_flash_news})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.home_gzzt://工位状态
                if (CommentUtils.permission.contains("GWZT")) {
                    Intent intent4 = new Intent(mContext, WaitAreaActivity.class);
                    intent4.putExtra("AreaType", "4");
                    startActivity(intent4);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.home_hukc://会员快查
                if (CommentUtils.permission.contains("HYKC")) {
                    showActivity(MemberSearchActivity.class);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.home_dtyy://当天预约
                if (CommentUtils.permission.contains("DTYY")) {
                    showActivity(DayAppointmentActivity.class);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.home_mdtj://门店统计
                if (CommentUtils.permission.contains("MDTJ")) {
                    showActivity(ShopCollectActivity.class);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.home_yuyue: //预约
                if (CommentUtils.permission.contains("YY")) {
                    showActivity(AppointMentDealActivity.class);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.home_rebackpay://退款
                if (CommentUtils.permission.contains("TK")) {
                    showActivity(RebackPayActivity.class);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.home_wake://消息提醒
                Intent intent = new Intent(mContext, SystemRemindActivity.class);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "消息提醒");
                intent.putExtra(C.IntentKey.SYSTEM_TYPE, SystemRemindActivity.SYSTEM_MSG);
                showActivity(intent);
                break;
            case R.id.rl_all_project:
                onFunctionClick((String) view.getTag());
                break;
            case R.id.open_oder://开单
                if (CommentUtils.permission.contains("KD")) {
                    showActivity(OpenOrderActivity.class);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.wait_area://等候区界面
                if (CommentUtils.permission.contains("DHQ")) {
                    Intent intent2 = new Intent(mContext, WaitAreaActivity.class);
                    intent2.putExtra("AreaType", "2");
                    startActivity(intent2);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.settlement_area: //结算去界面
                if (CommentUtils.permission.contains("JSQ")) {
                    Intent intent3 = new Intent(mContext, WaitAreaActivity.class);
                    intent3.putExtra("AreaType", "3");
                    startActivity(intent3);
                } else {
                    permissionDialog();
                }
                break;
            case R.id.quality_area://质检区界面
                if (CommentUtils.permission.contains("ZZQ")) {
                    Intent intent6 = new Intent(mContext, WaitAreaActivity.class);
                    intent6.putExtra("AreaType", "6");
                    startActivity(intent6);
                } else {
                    permissionDialog();
                }

                break;
            case R.id.rtv_flash_news:  //通知公告 滚动消息设置
                if (gongGaoInfoList.size() > 0) {
                    int position = rtvFlashNews.getCurrentPosition();
                    Intent intentGongGao = new Intent(mContext, ShowContentActivity.class);
                    intentGongGao.putExtra(C.IntentKey.EDIT_CONTEXT, gongGaoInfoList.get(position).getContent());
                    intentGongGao.putExtra(C.IntentKey.TOPBAR_TITLE, "公告");
                    showActivity(intentGongGao);
                }
                break;
            default:
//                onFunctionClick((String) view.getTag());
                break;
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

    @Override
    public void itemClick(HomeIconInfo info) {
        onFunctionClick(info.getModuleNum());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mPointViews.size(); i++) {
            mPointViews.get(position).setImageResource(page_indicatorId[1]);
            if (position != i) {
                mPointViews.get(i).setImageResource(page_indicatorId[0]);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*======================homeNoticePresenter==============================*/

    @Override
    public void getReportInfoSuccess(ReportInfo reportInfo) {
        if (reportInfo == null) return;
    }

    @Override
    public void getReportInfoError() {
    }

    @Override
    public void getReportInfoResult() {
        if (refreshlayout != null) {
            refreshlayout.setRefreshing(false);
        }
    }
/*======================homeNoticePresenter==============================*/

    @Override
    public void onRefresh() {
        homeNoticePresenter.getReportInfo(app.getApiService().report(app.getCurrentUserNum()));
        //权限表
        homePerssionPresenter.getPerssionData(app.getApiService().getPerssion(app.getCurrentUserNum()));
//        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "3"));
        //获取等候区数量  status=3;等候结算区status=8；等候质检区tatus=6
        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "3"), "3");
        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "6"), "6");
        homeWaitAreaCountPresenter.getWaitAreaCountData(app.getApiService().getWaitAreaCount(app.getCurrentUserNum(), "8"), "8");

        //今日预约数量
        announcementPresenter.getAppointMentCount(app.getApiService().getAppointMentCount(app.getCurrentUserNum()));

        // 今日申请退款数量
        announcementPresenter.getRePayCount(app.getApiService().getRePayCount(app.getCurrentUserNum()));

        // 提醒数量
        systemRemindParams.setIsPush(RemindInfo.STATUS_PUSHED);
        systemRemindParams.setIsRead(RemindInfo.READ_NO);
        systemRemindParams.setRemindType(RemindInfo.REMIND_TYPE_MAINTAIN + "," + RemindInfo.REMIND_TYPE_BIRTHDAY + "," + RemindInfo.REMIND_TYPE_BALANCE + "," + RemindInfo.REMIND_TYPE_MEMBERLOSE);
        announcementPresenter.getWakeCount(app.getApiService().getSystemRemindList(app.getCurrentUserNum(), systemRemindParams));

    }

    @Override
    public void onResume() {
        super.onResume();
        // 开始自动翻页
        convenientBanner.startTurning(2500);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止翻页
        convenientBanner.stopTurning();
    }

    //bannar的点击监听
    @Override
    public void onItemClick(int position) {
//        AppUtility.showToastMsg("position"+position);
    }

    // 公告部分
    @Override
    public void getGongGaoInfoSuccess(List<GongGaoInfo> gongGaoInfo) {
        List<String> noticeInfoList = new ArrayList<>();
        for (GongGaoInfo str : gongGaoInfo) {
//            this.gongGaoInfo = gongGaoInfo;
            if (!TextUtils.isEmpty(str.getTitle())) {
                noticeInfoList.add(str.getTitle());
                gongGaoInfoList.add(str);
            }
        }
        rtvFlashNews.setStrings(noticeInfoList);  //滚动消息
        LogX.e("====getGongGallActivity", gongGaoInfo.toString());

    }

    @Override
    public void getGongGaoInfoError() {

    }

    @Override
    public void getGongGaoInfoResult() {

    }

    //今日预约数量
    @Override
    public void getAppointMentCountSuccess(String count) {
        LogX.e(TAG, "今日预约数量" + count);
        tv_appointmemtcount.setText(count.substring(0, count.length() - 2));
    }

    //今日退款申请数量
    @Override
    public void getRePayApplyCountSuccess(String count) {
        LogX.e(TAG, "今日退款申请数量" + count);
        tv_repaycount.setText(count.substring(0, count.length() - 2));
    }

    //今日提醒数量
    @Override
    public void getWaitCountSuccess(String count) {
        app.loadingDialogHelper.dissMiss();
        tv_wakecount.setText(count);
    }

    //获取权限表
    @Override
    public void getDataSuccess(List<PerssionBean> perssionList) {
        LogX.e("权限表", perssionList.toString());
        CommentUtils.permission.clear();
        for (PerssionBean perssion : perssionList) {
            CommentUtils.permission.add(perssion.getButNum());
        }
        LogX.e("权限表",  CommentUtils.permission.toString());
    }

    @Override
    public void getDataFault() {

    }

    /*权限dialog*/
    public void permissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("Material Design Dialog");
        builder.setMessage("您没有权限进入！");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    /*获取等候区数量*/
    @Override
    public void getWaitAreaCount(ResultBase resultBase) {
        LogX.e("等候区1", String.valueOf(resultBase.getObj()) + "");

        String count = resultBase.getObj().toString();
        if (count.length() > 1 && !count.equals("0")) {
            count = count.substring(0, count.indexOf("."));
            if (!count.equals("0")) {
                waitAreaCount.setVisibility(View.VISIBLE);
                waitAreaCount.setText(count);
            }
        }
    }

    /*获取质检区数量*/
    @Override
    public void getZhiJianAreaCount(ResultBase resultBase) {
        LogX.e("质检区", String.valueOf(resultBase.getObj()) + "");
        if (TextUtils.isEmpty(resultBase.getObj().toString()))
            return;
        String count = resultBase.getObj().toString();
        if (count.length() > 1 && !count.equals("0")) {
            count = count.substring(0, count.indexOf("."));
            if (!count.equals("0")) {
                waitQualityCount.setVisibility(View.VISIBLE);
                waitQualityCount.setText(count);
            }
        }
    }

    /*获取结算区数量*/
    @Override
    public void getJieSuanAreaCount(ResultBase resultBase) {
        LogX.e("结算区1", String.valueOf(resultBase.getObj()) + "");
        if (TextUtils.isEmpty(resultBase.getObj().toString()))
            return;
        String count = resultBase.getObj().toString();
        if (count.length() > 1 && !count.equals("0")) {
            count = count.substring(0, count.indexOf("."));
            if (!count.equals("0")) {
                waitSettlementCount.setVisibility(View.VISIBLE);
                waitSettlementCount.setText(count);
            }
        }

    }

    @Override
    public void getWaitAreaCounterr() {

    }
}
