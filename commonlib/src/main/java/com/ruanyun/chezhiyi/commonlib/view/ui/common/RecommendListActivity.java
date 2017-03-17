package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.RecommentParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.adapter.HomeRecommendAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Description ：推荐列表
 * <p/>
 * Created by ycw on 2016/9/19.
 */
public class RecommendListActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener {

    private Topbar topbar;
    private RecyclerView list;
    private HomeRecommendAdapter recommendAdapter;
    private RecommentParams params = new RecommentParams();//获取推荐项目的参数



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ativity_recommend_list);
        ButterKnife.bind(this);
        initRefreshLayout();
        initView();
    }


    private void initView() {
        topbar = getView(R.id.topbar);
        list = getView(R.id.list);

        topbar.setTttleText("推荐项目")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        //推荐项目数据
        GridLayoutManager homeManager = new GridLayoutManager(mContext, 1);
        list.setLayoutManager(homeManager);
        recommendAdapter = new HomeRecommendAdapter(mContext, R.layout.list_item_project, new ArrayList<RecommendInfo>());
        recommendAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                RecommendInfo recommendInfo = (RecommendInfo) o;
                double payPrice = AppUtility.getPrice(recommendInfo.getGoodsType(), recommendInfo
                        .getSalePrice(), recommendInfo.getActivityPrice());
                LogX.d(TAG, "---payPrice----->\n" + payPrice);
                AppUtility.showGoodsWebView(payPrice, app.getCurrentUserNum(), recommendInfo
                        .getGoodsNum(), recommendInfo.getGoodsType(), recommendInfo.getGoodsNum()
                        , app.getCurrentUserNum(), mContext, recommendInfo.getTitle(),
                        recommendInfo.getRecommentProjectNum(), recommendInfo.getMainPhoto(), recommendInfo.getTitle() );

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        list.setAdapter(recommendAdapter);
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        //获取推荐项目
        params.setRecommentProjectType(1);
        params.setPageNum(currentPage);
        return app.getApiService().getRecommendList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        recommendAdapter.setDatas( recommendAdapter.getTypeNameList(result.getResult()) );
        recommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        int size = recommendAdapter.getItemCount();
        List<RecommendInfo> list = result.getResult();
        recommendAdapter.addDatas(recommendAdapter.getTypeNameList( list ));
        recommendAdapter.notifyItemRangeInserted(size, list.size());
    }


    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }
}
