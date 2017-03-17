package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.ActivitySignInfo;
import com.ruanyun.chezhiyi.commonlib.model.AdverListInfo;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.FindMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/9/10.
 */
public class FindPresenter implements Presenter<FindMvpView> {

    private FindMvpView findMvpView;
    private Call<ResultBase<ActivitySignInfo>> call;
    private Call<ResultBase<PageInfoBase<AdverListInfo>>> adverCall;
    private Call<ResultBase<List<ActivityInfo>>> activityCall;
    private Call<ResultBase<PageInfoBase<ArticleInfo>>> articleCall;
    private Call<ResultBase<PageInfoBase<PromotionInfo>>> promotionCall;
    private Call<ResultBase<PageInfoBase<RecommendInfo>>> recommendCall;

    @Override
    public void attachView(FindMvpView mvpView) {
        this.findMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.findMvpView = null;
    }

    @Override
    public void onCancel() {
        if (adverCall != null && !adverCall.isCanceled())
            adverCall.cancel();
        if (activityCall != null && !activityCall.isCanceled())
            activityCall.cancel();
        if (articleCall != null && !articleCall.isCanceled())
            articleCall.cancel();
        if (promotionCall != null && !promotionCall.isCanceled())
            promotionCall.cancel();
        if (recommendCall != null && !recommendCall.isCanceled())
            recommendCall.cancel();
    }


    /**
     * 获取广告列表
     *
     * @param call
     */
    public void getAdverList(Call<ResultBase<PageInfoBase<AdverListInfo>>> call) {
        this.adverCall = call;
        this.adverCall.enqueue(new ResponseCallback<ResultBase<PageInfoBase<AdverListInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<AdverListInfo>>
                    pageInfoBaseResultBase) {
                if (findMvpView == null) return;
                findMvpView.getAdverListOnSuccess(pageInfoBaseResultBase.getObj().getResult());

            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<AdverListInfo>>
                    pageInfoBaseResultBase, int errorCode) {
                if (findMvpView == null) return;
                findMvpView.showTip(pageInfoBaseResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (findMvpView == null) return;
                findMvpView.showTip(msg);
            }

            @Override
            public void onResult() {

            }
        });
    }


    /**
     * 活动列表
     *
     * @param activityCall
     */
    public void getActivityList(Call<ResultBase<List<ActivityInfo>>> activityCall) {
        this.activityCall = activityCall;
        this.activityCall.enqueue(new ResponseCallback<ResultBase<List<ActivityInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<ActivityInfo>> listResultBase) {
                if (findMvpView == null) return;
                findMvpView.onSuccess();
                findMvpView.getActivityListOnSuccess(listResultBase.getObj());
                LogX.d("getActivityList", "----------onSuccess--------");
            }

            @Override
            public void onError(Call call, ResultBase<List<ActivityInfo>> listResultBase, int
                    errorCode) {
                if (findMvpView == null) return;
                findMvpView.showTip(listResultBase.getMsg());
                LogX.d("getActivityList", "----------onError--------");

            }

            @Override
            public void onFail(Call call, String msg) {
                if (findMvpView == null) return;
                findMvpView.showTip(msg);
                LogX.d("getActivityList", "----------onFail--------");

            }

            @Override
            public void onResult() {
                if (findMvpView == null) return;
                findMvpView.checkStatus();
                LogX.d("getActivityList", "----------onResult--------");
            }
        });
    }


    /**
     * 滚动消息
     *
     * @param articleCall
     */
    public void getArticle(Call<ResultBase<PageInfoBase<ArticleInfo>>> articleCall) {
        this.articleCall = articleCall;
//        if (findMvpView == null)
//            findMvpView.showFindLoadingView();
        this.articleCall.enqueue(new ResponseCallback<ResultBase<PageInfoBase<ArticleInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<ArticleInfo>>
                    pageInfoBaseResultBase) {
                if (findMvpView == null) return;
                findMvpView.onSuccess();
                findMvpView.getArticleOnSuccess(pageInfoBaseResultBase.getObj().getResult());

                LogX.d("getArticle", "----------onSuccess--------");
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<ArticleInfo>>
                    pageInfoBaseResultBase, int errorCode) {
                if (findMvpView == null) return;
                findMvpView.showTip(pageInfoBaseResultBase.getMsg());
                LogX.d("getArticle", "----------onError--------");

            }

            @Override
            public void onFail(Call call, String msg) {
                if (findMvpView == null) return;
                findMvpView.showTip(msg);
                LogX.d("getArticle", "----------onFail--------");

            }

            @Override
            public void onResult() {
                if (findMvpView == null) return;
                findMvpView.checkStatus();
                LogX.d("getArticle", "----------onResult--------");
            }
        });
    }


    /**
     * 限时促销
     *
     * @param promotionCall
     */
    public void getPromotionList(Call<ResultBase<PageInfoBase<PromotionInfo>>> promotionCall) {
        this.promotionCall = promotionCall;
        this.promotionCall.enqueue(new ResponseCallback<ResultBase<PageInfoBase<PromotionInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<PromotionInfo>>
                    pageInfoBaseResultBase) {
                if (findMvpView == null) return;
                findMvpView.onSuccess();
                findMvpView.getPromotionOnSuccess(pageInfoBaseResultBase.getObj().getResult());

                LogX.d("getPromotionList", "----------onSuccess--------");
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<PromotionInfo>>
                    pageInfoBaseResultBase, int errorCode) {
                if (findMvpView == null) return;
                findMvpView.showTip(pageInfoBaseResultBase.getMsg());
                LogX.d("getPromotionList", "----------onError--------");

            }

            @Override
            public void onFail(Call call, String msg) {
                if (findMvpView == null) return;
                findMvpView.showTip(msg);
                LogX.d("getPromotionList", "----------onFail--------");

            }

            @Override
            public void onResult() {
                if (findMvpView == null) return;
                findMvpView.checkStatus();
                LogX.d("getPromotionList", "----------onResult--------");
            }
        });
    }


    /**
     * 猜你喜欢
     *
     * @param recommendCall
     */
    public void getRecommendList(Call<ResultBase<PageInfoBase<RecommendInfo>>> recommendCall) {
        this.recommendCall = recommendCall;
        this.recommendCall.enqueue(new ResponseCallback<ResultBase<PageInfoBase<RecommendInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<RecommendInfo>>
                    pageInfoBaseResultBase) {
                if (findMvpView == null) return;
                findMvpView.onSuccess();
                findMvpView.getRecommendOnSuccess(pageInfoBaseResultBase.getObj().getResult());
                LogX.d("getRecommendList", "----------onSuccess--------");
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<RecommendInfo>>
                    pageInfoBaseResultBase, int errorCode) {
                if (findMvpView == null) return;
                findMvpView.showTip(pageInfoBaseResultBase.getMsg());
                LogX.d("getRecommendList", "----------onError--------");

            }

            @Override
            public void onFail(Call call, String msg) {
                if (findMvpView == null) return;
                findMvpView.showTip(msg);
                LogX.d("getRecommendList", "----------onFail--------");

            }

            @Override
            public void onResult() {
                if (findMvpView == null) return;
                findMvpView.checkStatus();
                LogX.d("getRecommendList", "----------onResult--------");
            }
        });
    }


    /**
     * 获取是否报名
     * @param activityCall
     */
    public void getActiveAddInfo(Call<ResultBase<ActivitySignInfo>> activityCall) {
        this.call = activityCall;
        if (findMvpView == null) return;
        findMvpView.showLoadingView();
        this.call.enqueue(new ResponseCallback<ResultBase<ActivitySignInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<ActivitySignInfo> activityListInfoResultBase) {
                if (findMvpView == null) return;
                findMvpView.getActivitySuccess(activityListInfoResultBase.getObj().getActivityInfo());
            }

            @Override
            public void onError(Call call, ResultBase<ActivitySignInfo> activityListInfoResultBase, int errorCode) {
                if (findMvpView == null) return;
                findMvpView.showTip(activityListInfoResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (findMvpView == null) return;
                findMvpView.dismissLoadingView();
            }
        });
    }

}
