package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.AdverListInfo;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.model.PromotionInfo;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;

import java.util.List;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/9/10.
 */
public interface FindMvpView extends MvpView {

    void getAdverListOnSuccess(List<AdverListInfo> adverListInfos);


    void getActivityListOnSuccess(List<ActivityInfo> result);


    void getArticleOnSuccess(List<ArticleInfo> result);


    void getPromotionOnSuccess(List<PromotionInfo> result);


    void getRecommendOnSuccess(List<RecommendInfo> result);


    void checkStatus();

    void onSuccess();

    void showTip(String msg);

    void showLoadingView();

    void dismissLoadingView();

    void getActivitySuccess(ActivityInfo activityInfo);
}
