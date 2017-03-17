package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.FeedBackInfo;

/**
 * Created by msq on 2016/9/9.
 */
public interface FeedBackMvpView extends MvpView{

    void onFeedBackShowLoading();

    void onFeedBackSuccess(ResultBase<FeedBackInfo> resultBase);

    void onFeedBackError(ResultBase<FeedBackInfo> resultBase, int errorCode);

    void onFeedBackFail(String msg);

    void onFeedBackResponse();

    void onLoginLoadingDissMiss();
}
