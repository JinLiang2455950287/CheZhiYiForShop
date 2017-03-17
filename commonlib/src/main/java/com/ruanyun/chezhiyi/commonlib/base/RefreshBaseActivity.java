package com.ruanyun.chezhiyi.commonlib.base;

import android.content.Context;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.presenter.RefreshListPresenter;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.RefreshListMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

/**
 * Description:下拉刷新基类
 * author: zhangsan on 16/7/20 下午5:03.
 */
public abstract class RefreshBaseActivity extends AutoLayoutActivity implements RefreshListMvpView,BGARefreshLayout.BGARefreshLayoutDelegate{

    protected RefreshListPresenter listPresenter=new RefreshListPresenter() ;
    protected RYEmptyView emptyView;
    protected BGARefreshLayout refreshLayout;
    protected int loadType=-1;
    protected int currentPage=1;
    protected int totalPage=0;
    protected boolean isbindEmptyView=true;
    protected  BGANormalRefreshViewHolder normalRefreshViewHolder;

    protected void initRefreshLayout(){
        listPresenter.attachView(this);
        refreshLayout=getView(R.id.refreshlayout);
        emptyView=getView(R.id.emptyview);
        normalRefreshViewHolder=new BGANormalRefreshViewHolder(mContext, true);
        refreshLayout.setRefreshViewHolder(normalRefreshViewHolder);
        refreshLayout.setDelegate(this);
        if (isbindEmptyView) {
            emptyView.bind(refreshLayout);
            emptyView.setOnReloadListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    reload();
                }
            });
        }

    }
   /**
     * 主动下拉刷新 走下拉刷新动画回调
     *@author zhangsan
     *@date   16/8/12 上午11:39
     */
   protected void refreshWithAnim(){
       refreshLayout.beginRefreshing();
   }
   /**
     * 刷新数据显示loding动画
     *@author zhangsan
     *@date   16/8/23 上午10:04
     */
   protected void refreshWithLoading(){
       emptyView.showLoading();
       onStartRefresh();
   }
   protected void onStartRefresh(){
       currentPage=1;
       loadType=REFRESH;
       listPresenter.loadData(loadData(),loadType);
   }

   protected boolean onStartLoadMore(){
       currentPage++;
       if (currentPage <= totalPage) {
           loadType=LOADMORE;
           listPresenter.loadData(loadData(),loadType);
           return true;
       } else {
           refreshLayout.endLoadingMore();
           return false;
       }
   }

   public abstract Call loadData();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        listPresenter.detachView();
    }

    protected void reload(){
        refreshWithLoading();
    }

    @Override
    public Context getContext() {
        return null;
    }



    @Override
    public void showEmptyViewLoading(String tag) {
        if (isbindEmptyView)
            emptyView.showLoading();
    }

    @Override
    public void showEmpty(String tag) {
     emptyView.showEmpty();
    }

    @Override
    public void showLoadFair(String msg, String tag) {
       emptyView.showLoadFail();
    }

    @Override
    public void onLoadResponse(String tag,int loadType) {
        emptyView.loadSuccuss();
        if(loadType==REFRESH){
          refreshLayout.endRefreshing();
        }else if(loadType==LOADMORE){
            refreshLayout.endLoadingMore();
        }
    }

    @Override
    public void onPageResult(PageInfoBase result) {
        totalPage=result.getTotalPage();
    }

    @Override
    public void onRefreshNoPageResult(ResultBase resultBase, String tag) {

    }

    @Override
    public void onLoadMoreNoPageResult(ResultBase result, String tag) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
         onStartRefresh();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return onStartLoadMore();
    }
}
