package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.params.ArticleListParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CommentZanMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.AriticleListAdapter;

import java.util.ArrayList;

import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * Description:文章列表通用
 * author: zhangsan on 16/9/8 下午7:19.
 */
public class ArticleListFragment extends RefreshBaseFragment /*implements LazyFragmentPagerAdapter.Laziable*/ {

    ArticleListParams params = new ArticleListParams();
    ListView list;
    AriticleListAdapter adapter;
    String articleType = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        articleType = getArguments().getString(C.IntentKey.ARTICLE_TYPE);
        mContentView = inflater.inflate(R.layout.fragment_article_common, container, false);
        isFirstIn = true;
        isPrepared = true;
        return mContentView;
    }

    public static ArticleListFragment newInstance(String articleType) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.IntentKey.ARTICLE_TYPE, articleType);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        refreshWithLoading();
        initView();
        lazyLoad();
    }

    @Subscribe
    public void onReciveRefresh(Event<String> msg){
        if(msg.key.equals(C.EventKey.KEY_REFRESH_LIST)){
            refreshWithLoading();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refreshLayout != null)
        refreshLayout.endRefreshing();
        unRegisterBus();
    }

    private void initView() {
        initRefreshLayout();
        list = getView(R.id.list);
        adapter = new AriticleListAdapter(mContext, R.layout.list_item_article, new ArrayList<ArticleInfo>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleInfo articleInfo = adapter.getItem(position);
                if (articleInfo != null) {
                    Intent intent = AppUtility.getArticleCaseLibDetailIntent(mContext, FileUtil.getFileUrl(String.format(C.ApiUrl.URL_ARTICLE_DETAIL,
                            app.getCurrentUserNum(), articleInfo.getArticleNum())), CommentZanMvpView.TYPE_ARTICLE);
                    intent.putExtra(C.IntentKey.ARTICLE_INFO, articleInfo);
                    intent.putExtra(C.IntentKey.WEBVIEW_TITLE, /*"文章详情"*/articleInfo.getTitle());
                    showActivity(intent);
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        //params.setIsHome(2);
        params.setArticleType(articleType);
//        params.setCurrentPage(currentPage);
        params.setPageNum(currentPage);
        return app.getApiService().articleList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.refresh(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.loadMore(result.getResult());
    }
}
