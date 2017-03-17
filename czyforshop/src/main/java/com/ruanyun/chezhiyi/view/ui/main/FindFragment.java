package com.ruanyun.chezhiyi.view.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.AdverListInfo;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.model.GoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.ArticleListParams;
import com.ruanyun.chezhiyi.commonlib.model.params.GetPromotionParams;
import com.ruanyun.chezhiyi.commonlib.model.params.RecommentParams;
import com.ruanyun.chezhiyi.commonlib.presenter.FindPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.Base64;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.RSAUtils;
import com.ruanyun.chezhiyi.commonlib.view.CommentZanMvpView;
import com.ruanyun.chezhiyi.commonlib.view.FindMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ActivityToRecruitAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.AdverListHolder;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ClientFindGridItemDecoration;
import com.ruanyun.chezhiyi.commonlib.view.adapter.FindFunctionAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.HomeRecommendAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.PromotionAdapter;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.ArticleListActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.CrowdFundingActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.GroupPurchaseActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.ProductActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.PromotionActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.SeckillActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RollableTextView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 商家端   发现 、电商 界面
 * Created by ycw on 2016/9/7.
 */
public class FindFragment extends RefreshBaseFragment implements FindMvpView, OnItemClickListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView_time_limit_promotion)
    RecyclerView recyclerViewTimeLimitPromotion;//限时促销的商品列表
    @BindView(R.id.topbar)
    Topbar topbar;//
    @BindView(R.id.progressbar)
    ProgressBar progressbar;// 广告的等待进度条
    @BindView(R.id.convenient_banner)
    ConvenientBanner convenientBanner;//  广告
    @BindView(R.id.recyclerView_function)
    RecyclerView recyclerViewFunction;//  功能图标的列表
    @BindView(R.id.iv_car_flash)
    ImageView ivCarFlash; //汽车快讯 图
    @BindView(R.id.rtv_flash_top)
    RollableTextView rtvFlashTop;//  滚动广告
    @BindView(R.id.rtv_flash_bottom)
    RollableTextView rtvFlashBottom;//  滚动广告
    @BindView(R.id.iv_time_limit_promotion)
    ImageView ivTimeLimitPromotion;  // 限时促销的  图片
    @BindView(R.id.tv_time_limit_promotion)
    TextView tvTimeLimitPromotion;//限时促销  文字
    @BindView(R.id.iv_activities_to_recruit)
    ImageView ivActivitiesToRecruit;
    @BindView(R.id.iv_guess_you_like)
    ImageView ivGuessYouLike;
    @BindView(R.id.recyclerView_guess_you_like)
    RecyclerView recyclerViewGuessYouLike;   //  猜你喜欢列表
    @BindView(R.id.recyclerView_activities_to_recruit)
    RecyclerView recyclerViewActivityToRecruit; //活动招募  列表
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_activities_to_recruit_name)
    TextView tvActivitiesToRecruitName;  //活动招募   文字
    @BindView(R.id.tv_guess_you_like_name)
    TextView tvGuessYouLikeName;//猜你喜欢  文字
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.emptyview)
    RYEmptyView ryEmptyView;
    @BindView(R.id.tv_time_countdown)
    TextView tvTimeCountdown;//促销倒计时前  文字
//    @BindView(R.id.countdwn_view_current)
//    CountdownView countdwnViewCurrent;//促销倒计时view

    private FindPresenter presenter = new FindPresenter(); //
    private List<AdverListInfo> adverList = new ArrayList<>();//广告的列表
    private ActivityToRecruitAdapter activityToRecruitAdapter;//活动招募的数据
    private PromotionAdapter promotionAdapter;//限时促销
    private HomeRecommendAdapter guessYouLikeAdapter;//猜你喜欢
    private ArticleListParams params = new ArticleListParams();//滚动消息
    private GetPromotionParams promotionParams = new GetPromotionParams();//限时促销
    private RecommentParams recommentParams = new RecommentParams();////猜你喜欢
    List<ArticleInfo> articleListTop;
    List<ArticleInfo> articleListBottom;
    private boolean isSuccess = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, mContentView);
        presenter.attachView(this);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getUIData();
    }

    private void initView() {
        topbar.setTttleText("电商");
        //获取当前界面的图标
        List<HomeIconInfo> list = DbHelper.getInstance().getHomeIconInfoList(C.ModuleType.MODULE_TYPE_SHOP_COMMERCE);
        LogX.d(TAG, "find  HomeIconInfo ---> \n" + list.toString());
        if (list.size() > 6) {
            //功能图标
            List<HomeIconInfo> listFunction = list.subList(0, 6);
            FindFunctionAdapter findFunctionAdapter = new FindFunctionAdapter(mContext, R.layout.item_client_my_rv_function, listFunction);
            findFunctionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                    onFunctionClick(((HomeIconInfo) o).getModuleNum());
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                    return false;
                }
            });
            recyclerViewFunction.setAdapter(findFunctionAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            recyclerViewFunction.setLayoutManager(gridLayoutManager);
            recyclerViewFunction.addItemDecoration(new ClientFindGridItemDecoration(mContext));

            //限时促销
            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(list.get(6).getAndroidPic()), ivTimeLimitPromotion);
            tvTimeLimitPromotion.setText(list.get(6).getHomeIconName());
        }
        if (list.size() > 7) {
            //活动招募
            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(list.get(7).getAndroidPic()), ivActivitiesToRecruit);
            tvActivitiesToRecruitName.setText(list.get(7).getHomeIconName());
        }
        if (list.size() > 8) {
            //猜你喜欢
            ImageUtil.loadImage(mContext, FileUtil.getImageUrl(list.get(8).getAndroidPic()), ivGuessYouLike);
            tvGuessYouLikeName.setText(list.get(8).getHomeIconName());
        }

        initActivityRecruitView();
        initPromotionView();
        initGuessYouLikeView();
        initRefreshView();
        rtvFlashTop.setOnClickListener(this);
        rtvFlashBottom.setOnClickListener(this);
    }

    /**
     * 初始化 活动招募列表
     */
    private void initActivityRecruitView() {
        GridLayoutManager ActivToRecruitGridLayout = new GridLayoutManager(mContext, 1);
        recyclerViewActivityToRecruit.setLayoutManager(ActivToRecruitGridLayout);
        activityToRecruitAdapter = new ActivityToRecruitAdapter(mContext, R.layout.item_ry_activities_to_recruit, new ArrayList<ActivityInfo>());
        activityToRecruitAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                //跳转活动详情
                ActivityInfo activityInfo = (ActivityInfo) o;
                LogX.d(TAG, "-------->" + (activityInfo).toString());
                String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_ACTIVITY), app.getCurrentUserNum(), (activityInfo).getActivityNum());
                Intent intent = AppUtility.getWebIntent(mContext, url, WebViewActivity.HD);
                intent.putExtra(C.IntentKey.ACTIVITY_INFO, activityInfo);
                showActivity(intent);
//                presenter.getActiveAddInfo(app.getApiService().getActivityInfo(app.getCurrentUserNum(), activityInfo.getActivityNum()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        recyclerViewActivityToRecruit.setAdapter(activityToRecruitAdapter);
    }

    /**
     * 初始化  限时促销
     */
    private void initPromotionView() {
        LinearLayoutManager promotionGridLayout = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTimeLimitPromotion.setLayoutManager(promotionGridLayout);
        promotionAdapter = new PromotionAdapter(mContext, R.layout.item_ry_promotion, new
                ArrayList<PromotionInfo>());
        promotionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                // : 2016/9/14 促销详情
                PromotionInfo promotionInfo = (PromotionInfo) o;
                AppUtility.showGoodsWebView(promotionInfo.getActivityPrice(),
                        app.getCurrentUserNum(),
                        promotionInfo.getGoodsNum(),
                        C.OrderType.ORDER_TYPE_CX,
                        promotionInfo.getPromotionInfoNum(),
                        app.getCurrentUserNum(),
                        mContext,
                        promotionInfo.getTitle(),
                        promotionInfo.getProjectNum(),
                        promotionInfo.getMainPhoto(),
                        promotionInfo.getViceTitle() );
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o,
                                           int position) {
                return false;
            }
        });
        recyclerViewTimeLimitPromotion.setAdapter(promotionAdapter);
        tvTimeCountdown.setOnClickListener(this);
        tvTimeCountdown.setText("更多");
    }

    /**
     * 初始化   猜你喜欢
     */
    private void initGuessYouLikeView() {
        GridLayoutManager LikeGridLayout = new GridLayoutManager(mContext, 1);
        recyclerViewGuessYouLike.setLayoutManager(LikeGridLayout);
        guessYouLikeAdapter = new HomeRecommendAdapter(mContext, R.layout.list_item_project, new ArrayList<RecommendInfo>());
        guessYouLikeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                // : 2016/9/14 猜你喜欢
                RecommendInfo recommendInfo = (RecommendInfo) o;
                double payPrice = AppUtility.getPrice(recommendInfo.getGoodsType(), recommendInfo
                        .getSalePrice(), recommendInfo.getActivityPrice());
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
                        recommendInfo.getTitle() );
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        recyclerViewGuessYouLike.setAdapter(guessYouLikeAdapter);
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefreshView() {
        swipeRefreshLayout.setColorSchemeResources(com.ruanyun.chezhiyi.commonlib.R.color.holo_blue_bright, com.ruanyun.chezhiyi.commonlib.R.color.holo_green_light,
                com.ruanyun.chezhiyi.commonlib.R.color.holo_orange_light, com.ruanyun.chezhiyi.commonlib.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        ryEmptyView.bind(swipeRefreshLayout);
        ryEmptyView.showLoading();
        ryEmptyView.setOnReloadListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGetData();
            }
        });
    }


    /**
     * 获取界面数据
     */
    private void getUIData() {
        params.setIsHome(1);
//        params.setArticleType("3");// 汽车快讯
        promotionParams.setIsHome(1);
        recommentParams.setNumPerPage(10000);
        recommentParams.setRecommentProjectType(2);
        startGetData();
    }

    /**
     * 请求接口  返回数据
     */
    private void startGetData() {
        //广告接口
        presenter.getAdverList(getAdverCall());
        //活动接口
        presenter.getActivityList(getActivityCall());
        //滚动消息接口
        presenter.getArticle(getArticleCall());
        //限时促销
        presenter.getPromotionList(getPromotionCall());
        //猜你喜欢
        presenter.getRecommendList(getRecommendCall());
    }

    private Call<ResultBase<PageInfoBase<RecommendInfo>>> getRecommendCall() {
        return app.getApiService().getRecommendList(app.getCurrentUserNum(), recommentParams);
    }

    private Call<ResultBase<PageInfoBase<PromotionInfo>>> getPromotionCall() {
        return app.getApiService().getPromotionList(app.getCurrentUserNum(), promotionParams);
    }

    private Call<ResultBase<PageInfoBase<ArticleInfo>>> getArticleCall() {
        return app.getApiService().articleList(app.getCurrentUserNum(), params);
    }

    private Call<ResultBase<List<ActivityInfo>>> getActivityCall() {
        return app.getApiService().activityListInfo(app.getCurrentUserNum());
    }

    private Call<ResultBase<PageInfoBase<AdverListInfo>>> getAdverCall() {
        return app.getApiService().adverListInfo(app.getCurrentUserNum());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Call loadData() {
        return null;
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {

    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {

    }

    @Override
    public void getAdverListOnSuccess(List<AdverListInfo> adverListInfos) {
        this.adverList = adverListInfos;
        progressbar.setVisibility(View.GONE);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public AdverListHolder createHolder() {
                return new AdverListHolder();
            }
        }, this.adverList)
                .setOnItemClickListener(this)
                .setPageIndicator(new int[]{R.drawable.indicator_unselected_shape,
                        R.drawable.indicator_selected_shape});
    }

    @Override
    public void getActivityListOnSuccess(List<ActivityInfo> result) {
        activityToRecruitAdapter.setDatas(result);
        activityToRecruitAdapter.notifyDataSetChanged();
    }

    @Override
    public void getArticleOnSuccess(List<ArticleInfo> result) {
        if (result == null || result.size() == 0) {
            return;
        }

        int size = result.size();
        if (size == 1) {
            articleListTop = result;
            articleListBottom = null;
            setRollString(articleListTop, rtvFlashTop);
            setRollString(articleListBottom, rtvFlashBottom);
        } else if (size == 2) {
            articleListTop = result.subList(0, 1);
            setRollString(articleListTop, rtvFlashTop);
            articleListBottom = result.subList(1, 2);
            setRollString(articleListBottom, rtvFlashBottom);
        } else {
            articleListTop = result.subList(0, result.size() / 2);
            setRollString(articleListTop, rtvFlashTop);
            articleListBottom = result.subList(result.size() / 2, result.size());
            setRollString(articleListBottom, rtvFlashBottom);
        }
    }

    /**
     * 滚动的string
     *
     * @param articleInfos
     * @param rtvFlash
     */
    private void setRollString(List<ArticleInfo> articleInfos, RollableTextView rtvFlash) {
        List<String> strings = new ArrayList<>();
        if (articleInfos != null) {
            for (ArticleInfo articleInfo : articleInfos) {
                strings.add(articleInfo.getTitle());
            }
        }
        rtvFlash.setStrings(strings);
    }

    @Override
    public void getPromotionOnSuccess(List<PromotionInfo> result) {
        promotionAdapter.setDatas(result);
        promotionAdapter.notifyDataSetChanged();

//        if (result.size() == 0 ) return;
//        PromotionInfo promotionInfo = result.get(0);
//        startCountDown(promotionInfo);
    }

//    /**
//     * @param promotionInfo
//     * @deprecated
//     */

//    public void startCountDown(PromotionInfo promotionInfo) {
//        if (TextUtils.isEmpty(promotionInfo.getPromotionInfoNum())) {
//            tvTimeCountdown.setText("");
//            return;
//        }
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        // SimpleDateFormat timeDf=new SimpleDateFormat("HH:mm");
//        try {
//            long currentTime = System.currentTimeMillis();
//            //Date currentDate = dateformat.parse();
//            Date startTimeDate = dateformat.parse(promotionInfo.getPromotionBeginDate());
//            Date endTimeDate = dateformat.parse(promotionInfo.getPromotionEndDate());
//            if (currentTime < startTimeDate.getTime()) {//小于开始时间
//                //currentStatus = STATUS_START;
//                countdwnViewCurrent.start(startTimeDate.getTime()-currentTime);
//                tvTimeCountdown.setText("距离开始：");
//                countdwnViewCurrent.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
//                    @Override
//                    public void onEnd(CountdownView cv) {
////                        if (onCountDownFinshListener != null) {
////                            onCountDownFinshListener.onCounDownFinish(cv, STATUS_START);
////                        }
//                    }
//                });
//            } else if (currentTime >= startTimeDate.getTime() && currentTime < endTimeDate.getTime()) {//在秒杀的时间范围内
//                //currentStatus = STATUS_END;
//                countdwnViewCurrent.start(endTimeDate.getTime() - currentTime);
//                tvTimeCountdown.setText("距离结束：");
//                countdwnViewCurrent.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
//                    @Override
//                    public void onEnd(CountdownView cv) {
////                        if (onCountDownFinshListener != null) {
////                            onCountDownFinshListener.onCounDownFinish(cv, STATUS_END);
////                        }
//                    }
//                });
//
//            } else if(currentTime>=endTimeDate.getTime()) {
//                tvTimeCountdown.setText("已结束：");
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void getRecommendOnSuccess(List<RecommendInfo> result) {
        guessYouLikeAdapter.setDatas(guessYouLikeAdapter.getTypeNameList(result));
        guessYouLikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void checkStatus() {
        swipeRefreshLayout.setRefreshing(false);
        if (!isSuccess) {
            ryEmptyView.showLoadFail();
        }
    }

    @Override
    public void onSuccess() {
        isSuccess = true;
        ryEmptyView.loadSuccuss();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void getActivitySuccess(ActivityInfo activityInfo) {
        if (activityInfo != null) {
            String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_ACTIVITY), app.getCurrentUserNum(), (activityInfo).getActivityNum());
            Intent intent = AppUtility.getWebIntent(mContext, url, WebViewActivity.HD);
            intent.putExtra(C.IntentKey.ACTIVITY_INFO, activityInfo);
            showActivity(intent);
        }
    }

    /**
     * 广告栏的点击事件
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        AdverListInfo adverInfo = adverList.get(position);
        LogX.d(TAG, "adverInfo ---> \n" + adverInfo.toString());
        showAdver(adverInfo);
    }

    private void showAdver(AdverListInfo adverInfo) {
        String url = "";
        switch (adverInfo.getAdverType()) {
            case AdverListInfo.ADVERTYPE_ADVER: //自定义广告
                url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_ADVERINFO), app.getCurrentUserNum(), adverInfo.getAdverNum());

                break;
            case AdverListInfo.ADVERTYPE_GOODSINFO: //商品链接  和下面相同
                String linkUrl = adverInfo.getLinkUrl();
                LogX.d("ycw", "----------linkUrl-------->\n" + linkUrl);
                String jsonArry = linkUrl.substring(linkUrl.lastIndexOf("/") + 1, linkUrl.length());
                LogX.d("ycw", "----------jsonArry-------->\n" + jsonArry);
                GoodsInfo goodsInfo = null;
                try {
                    jsonArry = jsonArry.replaceAll("@", "/");
                    //接口解密
                    String key = new String(Base64.decode(RSAUtils.deCodeKey(jsonArry)), "utf-8");
                    LogX.d("ycw", "----------key-------->\n" + key);
                    Gson gson = new Gson();
                    goodsInfo = gson.fromJson(key, GoodsInfo.class);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    goodsInfo = null;
                }
                if (goodsInfo != null) {
                    String goodsJsonString = AppUtility.getGoodsJsonString(goodsInfo.getGoodsNum(),
                            goodsInfo.getActivityPrice(),
                            goodsInfo.getOrderType(),
                            goodsInfo.getCommonNum(),
                            goodsInfo.getGoodsName(),
                            goodsInfo.getProjectNum(),
                            goodsInfo.getMainPhoto(),
                            TextUtils.isEmpty(goodsInfo.getCommonFlag()) ? "1" : goodsInfo.getCommonFlag(),
                            goodsInfo.getViceTitle());
                    url = AppUtility.getGoodsUrlString(app.getCurrentUserNum(), app.getCurrentUserNum(), goodsJsonString);
                }
                break;
            case AdverListInfo.ADVERTYPE_OTHER_LINK:
                url = adverInfo.getLinkUrl();

                break;
        }
        Intent intent = AppUtility.getWebIntent(mContext, url, adverInfo.getTitle());
        intent.putExtra(C.IntentKey.NEED_SHARE, false);
        showActivity(intent);

    }

    /**
     * 功能图标点击事件
     *
     * @param moduleNum
     */
    public void onFunctionClick(String moduleNum) {
        switch (moduleNum) {
            case "32"://文章咨询
                showActivity(ArticleListActivity.class);
                break;
            case "33"://产品
                showActivity(ProductActivity.class);
                break;
            case "34"://团购
                showActivity(GroupPurchaseActivity.class);
                break;
            case "35"://限时促销
                showActivity(PromotionActivity.class);
                break;
            case "36"://秒杀
                showActivity(SeckillActivity.class);
                break;
            case "37"://众筹
                showActivity(CrowdFundingActivity.class);
                break;
//            /*case "10"://限时促销
//
//                break;
//            case "11"://活动招募
//
//                break;
//            case "12"://猜你喜欢
//
//                break;*/
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rtv_flash_top:
                if (articleListTop != null) {
                    int topCurrentPosition = rtvFlashTop.getCurrentPosition();
                    showArtcle(articleListTop.get(topCurrentPosition));
                }
                break;

            case R.id.rtv_flash_bottom:
                if (articleListBottom != null) {
                    int bottomCurrentPosition = rtvFlashTop.getCurrentPosition();
                    showArtcle(articleListBottom.get(bottomCurrentPosition));
                }
                break;

            case R.id.tv_time_countdown:// 限时促销  更多
                showActivity(PromotionActivity.class);
                break;

        }
    }

    /**
     * 显示文章详情
     *
     * @param articleInfo
     */
    private void showArtcle(ArticleInfo articleInfo) {
        if (articleInfo != null) {
            Intent intent = AppUtility.getArticleCaseLibDetailIntent(mContext, FileUtil.getFileUrl(String.format(C.ApiUrl.URL_ARTICLE_DETAIL,
                    app.getCurrentUserNum(), articleInfo.getArticleNum())), CommentZanMvpView.TYPE_ARTICLE);
            intent.putExtra(C.IntentKey.ARTICLE_INFO, articleInfo);
            intent.putExtra(C.IntentKey.WEBVIEW_TITLE, /*"文章详情"*/articleInfo.getTitle());
            showActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        startGetData();
    }
}
