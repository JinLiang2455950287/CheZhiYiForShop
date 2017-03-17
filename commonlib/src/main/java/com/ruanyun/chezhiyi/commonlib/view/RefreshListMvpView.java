package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;

import java.util.List;


/**
 * Description:
 * author: zhangsan on 16/7/20 下午4:55.
 */
public interface RefreshListMvpView<T extends PageInfoBase> extends MvpView {
     int REFRESH = 654654;
     int LOADMORE = 685463541;

    void onPageResult(T result);
    void onRefreshResult(T result,String tag);

    void onLoadMoreResult(T result,String tag);

    void showEmptyViewLoading(String tag);

    void showEmpty(String tag);

    void showLoadFair(String msg,String tag);
    void onLoadResponse(String tag,int loadType);

    <F extends ResultBase<List>> void  onRefreshNoPageResult(F f,String tag);
    <F extends ResultBase<List>> void  onLoadMoreNoPageResult(F f, String tag);
}
