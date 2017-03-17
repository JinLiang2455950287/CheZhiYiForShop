package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MyWorkOrderParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.adapter.WorkOrderListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Description ：消费记录
 * <p/>
 * Created by ycw on 2016/12/27.
 */

public class RecordActivity
        extends RefreshBaseActivity
        implements Topbar.onTopbarClickListener {

    private static final int REQUEST_CODE_REFRESH = 2233;
    private ListView mListView;
    private MyWorkOrderParams params = new MyWorkOrderParams();
    private List<WorkOrderInfo> mDatas = new ArrayList<>();
    private WorkOrderListAdapter mAdapter;
    private User mUser;
    private Topbar mTopBar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_record);
        mUser = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
        initView();
    }

    private void initView() {
        initRefreshLayout();
        mTopBar = getView(R.id.topbar);
        mTopBar.setBackBtnEnable(true)
                .setTttleText("消费记录")
                .onBackBtnClick()
                .setTopbarClickListener(this);
        mListView = getView(R.id.list);
        mAdapter = new WorkOrderListAdapter(mContext, R.layout.list_item_work_order, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                WorkOrderInfo info = mAdapter.getItem(position);
                Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                intent.putExtra(C.IntentKey.WORKORDER_INFO, info);
                startActivityForResult(intent, REQUEST_CODE_REFRESH);
            }
        });
        refreshWithLoading();
    }


    @Override
    public Call loadData() {
        params.setServiceUserNum(mUser.getUserNum());
        params.setWorkOrderStatus(9);
        params.setPageNum(currentPage);
        return app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        mDatas = result.getResult();
        mAdapter.setDate(mDatas);
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        mDatas = result.getResult();
        mAdapter.setDate(mDatas);
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId()== R.id.img_btn_left) {
            finish();
        }
    }
}
