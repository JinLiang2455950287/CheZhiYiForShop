package com.ruanyun.chezhiyi.view.ui.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.BookingInfoPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.BookingInfoMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.BookingServiceAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Description ：预约详情未进店界面
 * <p/>
 * Created by hdl on 2016/10/10.
 */

public class BookingDetailsNotRecrptionActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, BookingInfoMvpView {

    Topbar topbar;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;//预约编号
    @BindView(R.id.tv_booking_state)
    TextView tvBookingState;//预约服务状态
    @BindView(R.id.tv_client_name)
    TextView tvClientName;//客户姓名
    @BindView(R.id.rv_booking_project)
    RecyclerView rvBookingProject;//服务预约项目
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;//预计到店时间
    @BindView(R.id.tv_remark)
    TextView tvRemark;//客户备注

    private BookingServiceAdapter adapter;
    private String makeNum;//预约编号
    private List<WorkOrderInfo> firstList;//一级
    private Map<String, List<WorkOrderInfo>> subProject = new HashMap<>();//二级map
    AppointmentInfo mAppointmentInfo = new AppointmentInfo();//预约信息
    BookingInfoPresenter presenter = new BookingInfoPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_booking_details_not_reception);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachView();
    }

    private void initView() {
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        topbar.setTttleText("预约详情")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
//        List<ParentCodeInfo> codeInfoList = DbHelper.getInstance().getParentCodeList(C.ParentCode.MAKEINFO_STATUS);//字典表
        makeNum = getIntent().getStringExtra(C.IntentKey.BOOKING_MAKENUM);//预约编号
        firstList = new ArrayList<>();
        GridLayoutManager serverManager = new GridLayoutManager(mContext, 4);
        serverManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (firstList.get(position).isParent()) {
                    return 4;
                }
                return 1;
            }
        });
        rvBookingProject.setLayoutManager(serverManager);
        adapter = new BookingServiceAdapter(mContext, firstList);
        rvBookingProject.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                List<WorkOrderInfo> stubSecondList = subProject.get(((WorkOrderInfo) o).getProjectNum());
                if (stubSecondList == null || stubSecondList.size() == 0) {
                    String json = ((WorkOrderInfo) o).getChildProjectNum();
                    Type type = new TypeToken<List<WorkOrderInfo>>() {
                    }.getType();
                    stubSecondList = (new Gson()).fromJson(json, type);
                    subProject.put(((WorkOrderInfo) o).getProjectNum(), stubSecondList);
                }
                if (((WorkOrderInfo) o).isParent()) {
                    if (!((WorkOrderInfo) o).isSelected()) {
                        firstList.addAll(position + 1, stubSecondList);
                        firstList.get(position).setSelected(true);
                        adapter.notifyDataSetChanged();
                    } else {
                        firstList.removeAll(stubSecondList);
                        firstList.get(position).setSelected(false);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        getBookingDetails();
    }

    /**
     * 获取预约详情
     */
    private void getBookingDetails() {
        Call<ResultBase<AppointmentInfo>> call = app.getApiService().getBookingInfo(app.getCurrentUserNum(), makeNum);
        presenter.getBookingInfo(call);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showGetBookingInfoTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showGetBookingInfoSuccess(ResultBase<AppointmentInfo> appointmentInfoResultBase) {
        mAppointmentInfo = appointmentInfoResultBase.getObj();
        showData();
    }

    /**
     * 界面显示 数据
     */
    private void showData() {
        tvOrderNumber.setText(mAppointmentInfo.getMakeNum());//预约编号
//        if (mAppointmentInfo.getOrderInfo() != null)
//            tvClientName.setText(mAppointmentInfo.getOrderInfo().getOrderUserName());//客户姓名
        tvClientName.setText(mAppointmentInfo.getUser().getNickName());//客户姓名
        String predictShopTime = mAppointmentInfo.getPredictShopTime();
        predictShopTime = predictShopTime.substring(5, 7) + "月" + predictShopTime.substring(8, 10) + "日" + predictShopTime.substring(10);
        tvArriveTime.setText(predictShopTime);//预计到店时间
        tvRemark.setText(mAppointmentInfo.getRemark());//客户备注
        tvBookingState.setText(getBookingState());//预约服务状态
        //预约服务项目详情
        firstList = mAppointmentInfo.getWorkOrderInfoList();
        for (WorkOrderInfo workOrderInfo : firstList) {
            workOrderInfo.setParent(true);
            workOrderInfo.setSelected(false);
        }
        adapter.setDatas(firstList);
    }

    /**
     * 获取预约状态 Name
     *
     * @return
     */
    private String getBookingState() {
        String makeName;//预约服务状态
        switch (mAppointmentInfo.getMakeStatus()) {
            case AppointmentInfo.SWAIT_AFFIRM:
                makeName = "待确认";
                break;
            case AppointmentInfo.ALREADY_AFFIRM:
                makeName = "待付款";
                break;
            case AppointmentInfo.SWAIT_SERVICE:
                makeName = "待服务";
                break;
            case AppointmentInfo.ACCOMPLISH:
                makeName = "已完成";
                break;
            default:
                makeName = "已取消";
                break;
        }
        return makeName;
    }

    @OnClick(R.id.tv_client_name)
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_client_name) {
            User user = mAppointmentInfo.getUser();
            if (user != null) {
                user.setFriendStatus(10);  // 显示拨打电话   发消息的功能
                DbHelper.getInstance().insertUser(user);
                AppUtility.goToUserProfile(mContext, user);
            }
        }
    }
}
