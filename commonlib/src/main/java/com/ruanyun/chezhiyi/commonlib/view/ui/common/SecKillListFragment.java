package com.ruanyun.chezhiyi.commonlib.view.ui.common;

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
import com.ruanyun.chezhiyi.commonlib.model.SeckillDetailInfo;
import com.ruanyun.chezhiyi.commonlib.model.SeckillHeadInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.SeckillDetaillParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.adapter.SeckillDetailListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/9/19 下午2:09.
 */
public class SecKillListFragment extends RefreshBaseFragment implements LazyFragmentPagerAdapter.Laziable {
    public static final int RIGHT = 1;
    public static final int LEFT = 0;
    private SeckillDetailListAdapter adapter;
    private SeckillDetaillParams params = new SeckillDetaillParams();
    private String seckillMainInfoNum;//获取产品信息传递的值
    private int dayTime;//获取产品信息传递的值
    private SeckillHeadInfo headInfo;
    private ListView list;
    private String commonFlag;//是否可以购买

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        seckillMainInfoNum=getArguments().getString(C.IntentKey.SECKILLMAININFONUM);
        dayTime = getArguments().getInt(C.IntentKey.DAY_TIME);
        headInfo = getArguments().getParcelable(C.IntentKey.SECKILLINFO);
        mContentView = inflater.inflate(R.layout.fragment_seckill_list, container, false);
        initView();
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshWithLoading();
    }

    private void initView() {
        initRefreshLayout();
        list = getView(R.id.list);
        adapter = new SeckillDetailListAdapter(mContext, R.layout.list_item_product, new ArrayList<SeckillDetailInfo>());
        list.setAdapter(adapter);
        adapter.setOnSeckillClickListener(new SeckillDetailListAdapter.OnSeckillClickListener() {
            @Override
            public void OnSeckillItemClick(SeckillDetailInfo seckillDetailInfoo) {
                // TODO: 2016/12/19 修改为直接购买
                seckillDetailInfoo.setGoodsType(C.OrderType.ORDER_TYPE_MS);
                AppUtility.toSubmit(mContext, seckillDetailInfoo);
            }
        });
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                SeckillDetailInfo item = adapter.getItem(position);
                commonFlag = adapter.getSaleEnable(item) ? "1" : "2";
                AppUtility.showGoodsWebView(item.getActivityPrice(), app.getCurrentUserNum(),
                        item.getGoodsNum(), C.OrderType.ORDER_TYPE_MS, item.getSeckillDetailNum(),
                        app.getCurrentUserNum(), mContext, item.getTitle(), item.getProjectNum(),
                        item.getMainPhoto(), commonFlag, item.getViceTitle() );
            }
        });
        if (dayTime == RIGHT) {
            adapter.purchaseEnable = false;
            adapter.isRight = true;
        }
    }

    public void refreshListStatus(boolean f) {
        adapter.purchaseEnable = f;
        adapter.notifyDataSetChanged();
    }

    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        params.setSeckillMainInfoNum(seckillMainInfoNum);
        return app.getApiService().getSeckillInfo(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
        adapter.notifyDataSetChanged();
    }
}
