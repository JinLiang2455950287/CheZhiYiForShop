package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.RefreshListMvpView;

import java.util.List;

import retrofit2.Call;


/**
 * Description:下拉刷新 加载更多通用Presenter
 * author: zhangsan on 16/7/25 下午3:38.
 */
public class RefreshListPresenter implements Presenter<RefreshListMvpView> {
    //@NonNull
    RefreshListMvpView listMvpView;
    String tag = "";
    Call loadcall;

    @Override
    public void attachView(RefreshListMvpView mvpView) {
        this.listMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.listMvpView = null;
    }

    @Override
    public void onCancel() {
        if (loadcall != null && !loadcall.isCanceled())
            loadcall.cancel();
    }

    //不分页
    public void loadDataNoPage(Call call, final int loadType) {
        loadcall = call;
        loadcall.enqueue(new ResponseCallback<ResultBase<List>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List> listResult) {
                if (listResult.getObj().isEmpty()) {
                    if (listMvpView != null)
                        listMvpView.showEmpty(tag);
                } else {
                    if (listMvpView != null)
                        if (loadType == RefreshListMvpView.REFRESH) {
                            listMvpView.onRefreshNoPageResult(listResult, tag);
                        } else if (loadType == RefreshListMvpView.LOADMORE) {
                            listMvpView.onLoadMoreNoPageResult(listResult, tag);
                        }
                }
            }

            @Override
            public void onError(Call call, ResultBase<List> listResult, int errorCode) {
                if (listMvpView != null)
                    listMvpView.showLoadFair(listResult.getMsg(), tag);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (listMvpView != null)
                    listMvpView.showLoadFair(msg, tag);
            }

            @Override
            public void onResult() {
                if (listMvpView != null)
                    listMvpView.onLoadResponse(tag, loadType);
            }
        });
    }

    //分页
    public void loadData(Call call, final int loadType) {
        loadcall = call;
        loadcall.enqueue(new ResponseCallback<ResultBase<PageInfoBase>>() {

            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase> pageInfoBaseResult) {
                LogX.e("======callllllllCall", pageInfoBaseResult.getObj().toString());
                if (listMvpView == null) {
                    return;
                }
                if (pageInfoBaseResult.getObj().getResult().isEmpty()) {
                    if (listMvpView != null)
                        listMvpView.showEmpty(tag);
                } else {
                    listMvpView.onPageResult(pageInfoBaseResult.getObj());
                    if (loadType == RefreshListMvpView.REFRESH) {
                        if (listMvpView != null) {
                            listMvpView.onRefreshResult(pageInfoBaseResult.getObj(), tag);
                        }
                    } else if (loadType == RefreshListMvpView.LOADMORE) {
                        if (listMvpView != null) {
                            listMvpView.onLoadMoreResult(pageInfoBaseResult.getObj(), tag);
                        }
                    }
                }
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase> pageInfoBaseResult, int erroCode) {
                if (listMvpView != null)
                    listMvpView.showLoadFair(pageInfoBaseResult.getMsg(), tag);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (listMvpView != null)
                    listMvpView.showLoadFair(msg, tag);
            }

            @Override
            public void onResult() {
//                checkNull(listMvpView);
                if (listMvpView == null) {
                    return;
                }
                listMvpView.onLoadResponse(tag, loadType);
            }
        });
    }

    /*  private void checkNull(RefreshListMvpView mvpView){
          if(null==mvpView){
              return;
          }
      }*/
    public void setTag(String tag) {
        this.tag = tag;
    }
}
