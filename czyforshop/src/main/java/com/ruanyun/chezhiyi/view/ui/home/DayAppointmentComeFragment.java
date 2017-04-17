package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.BookingListParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.view.adapter.DayAppointmentCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * 未进店
 * Created by msq on 2016/9/26.
 */
public class DayAppointmentComeFragment extends RefreshBaseFragment {

    List<AppointmentInfo> appointmentInfo = new ArrayList<>();
    BookingListParams params = new BookingListParams();
    DayAppointmentCommentAdapter adapter;
    @BindView(R.id.list)
    ListView list;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_comment_appointment, container, false);
        isFirstIn = true;
        isPrepared = true;
        ButterKnife.bind(this, mContentView);
        initView();
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initView() {
        initRefreshLayout();
        refreshWithLoading();
        adapter = new DayAppointmentCommentAdapter(mContext, R.layout.list_dangtian_item, appointmentInfo, DayAppointmentCommentAdapter.STATUS_UNFINISH);
        list.setAdapter(adapter);
        Drawable drawable = new ColorDrawable(getResources().getColor(R.color.color_gray_line));
        list.setDivider(drawable);
        list.setDividerHeight(1);
        //设置未到店预约个数
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppointmentInfo appointmentInfo = adapter.getItem(i);
                Intent intent = new Intent(mContext, BookingDetailsNotRecrptionActivity.class);
                intent.putExtra(C.IntentKey.BOOKING_MAKENUM, appointmentInfo.getMakeNum());
                showActivity(intent);
            }
        });
    }


    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        params.setMakeStatusString("3");//未到店
        Call<ResultBase<PageInfoBase<AppointmentInfo>>> call = app.getApiService().getBookingList(app.getCurrentUserNum(), params);
        return call;
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
    }

}
