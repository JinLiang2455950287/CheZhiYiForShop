package com.ruanyun.czy.client.view.ui.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.ActivityListParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.ActivityListAdapter;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * 我的活动列表
 * Created by msq on 2016/9/10.
 */
public class ActivityListFragment extends RefreshBaseFragment implements LazyFragmentPagerAdapter.Laziable {

    private static final int REQ_REFRESH = 147;
    ActivityListParams params = new ActivityListParams();
    ListView list;
    ActivityListAdapter adapter;
    public  String activityType = "";
    private int signStatus = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityType = getArguments().getString(C.IntentKey.ACTIVITY_TYPE);
        mContentView = inflater.inflate(R.layout.fragment_activity_common, container, false);
        isFirstIn=true;
        isPrepared=true;
        initRefreshLayout();
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //refreshWithLoading();
        initView();
        lazyLoad();
    }

    private void initView() {
        list = getView(R.id.list);
        adapter = new ActivityListAdapter(mContext, R.layout.list_item_activity, new ArrayList<ActivityInfo>(),activityType);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
//                String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_ACTIVITY), app
//                        .getCurrentUserNum(), adapter.getItem(position).getActivityNum());
//                Intent intent = AppUtility.getWebIntent(mContext, url, "活动详情");
//                if (activityType.equals("2")) { // 即将开始状态
//                    intent.putExtra(C.IntentKey.ACTIVITY_INFO, adapter.getItem(position));
//                    intent.putExtra(C.IntentKey.ACTIVITY_CANCELABLE, true);
//                }
//                showActivity(intent);

                Intent intent = new Intent(mContext, ActivityDetailedActivity.class);
                intent.putExtra(C.IntentKey.ACTIVITY_NUM, adapter.getItem(position).getActivityNum());
                startActivityForResult(intent, REQ_REFRESH);
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
        if (activityType.equals("-1")) {
            signStatus = 2;
            params.setSignStatus(signStatus);
        } else {
            params.setType(activityType);
            params.setSignStatus(signStatus);
        }
        params.setPageNum(currentPage);
        return app.getApiService().myActivityListInfo(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_REFRESH) {
            if (resultCode == Activity.RESULT_OK) {
                lazyLoad();
            }
        }
    }
}
