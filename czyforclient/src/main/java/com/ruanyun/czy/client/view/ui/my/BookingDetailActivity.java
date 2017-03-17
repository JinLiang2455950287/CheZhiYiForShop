package com.ruanyun.czy.client.view.ui.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.CancelMakeParams;
import com.ruanyun.chezhiyi.commonlib.presenter.BookingInfoPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.CancelMakePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.BookingInfoMvpView;
import com.ruanyun.chezhiyi.commonlib.view.CancelMakeMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.BookingServiceAdapter;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderDetailedActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Description ：预约详情
 * <p/>
 * Created by hdl on 2016/9/18.
 */
public class BookingDetailActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, BookingInfoMvpView, CancelMakeMvpView {

    public static final int REQUEST_CODE = 1112;
    Topbar topbar;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;//预约编号
    @BindView(R.id.tv_booking_state)
    TextView tvBookingState;//预约服务状态
    @BindView(R.id.tv_look_workorder)
    TextView tvLookWorkorder;//查看工单
    @BindView(R.id.rv_booking_project)
    RecyclerView rvBookingProject;//预约服务项目详情
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;//预计到店时间
    @BindView(R.id.tv_remark)
    TextView tvRemark;//备注
    @BindView(R.id.text_money)
    TextView textMoney;//定金或已付定金text
    @BindView(R.id.tv_money)
    TextView tvMoney;//定金金额
    @BindView(R.id.text_pay_money)
    TextView textPayMoney;//支付定金或再约一次
    @BindView(R.id.tv_cancel_booking)
    TextView tvCancelBooking;//取消预约
    private BookingServiceAdapter adapter;
    private String makeNum;//预约编号
    private List<WorkOrderInfo> firstList;//一级
    private Map<String, List<WorkOrderInfo>> subProject = new HashMap<>();//二级map

    BookingInfoPresenter presenter = new BookingInfoPresenter();
    AppointmentInfo appointmentInfo = null;
    OrderInfo orderInfo;//预约订单

    /** 取消预约 presenter */
    CancelMakePresenter cancelMakePresenter = new CancelMakePresenter();
    private CancelMakeParams cancelParams = new CancelMakeParams();


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_booking_detail);
        ButterKnife.bind(this);
        presenter.attachView(this);
        cancelMakePresenter.attachView(this);
        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null)
        presenter.detachView();
        cancelMakePresenter.detachView();
    }

    private void initView() {
        tvLookWorkorder.setVisibility(View.INVISIBLE);
        tvCancelBooking.setVisibility(View.GONE);
        textPayMoney.setVisibility(View.GONE);
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        topbar.setTttleText("预约详情")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        makeNum = getIntent().getStringExtra(C.IntentKey.BOOKING_MAKENUM);
        Call<ResultBase<AppointmentInfo>> call = app.getApiService().getBookingInfo(app.getCurrentUserNum(),makeNum);
        presenter.getBookingInfo(call);
    }

    private void initData() {
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
        adapter = new BookingServiceAdapter(mContext,firstList);
        adapter.setOnBookingServiceClickListener(new BookingServiceAdapter.OnBookingServiceClickListener() {
            @Override
            public void onBookingServiceClick(WorkOrderInfo workOrderInfo) {
                Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                intent.putExtra(C.IntentKey.WORKORDER_INFO, workOrderInfo);
                showActivity(intent);
            }
        });
        rvBookingProject.setAdapter(adapter);
        //列表监听
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                List<WorkOrderInfo> stubSecondList = subProject.get(((WorkOrderInfo) o).getProjectNum());
                if (stubSecondList == null || stubSecondList.size() == 0) {
                    String json = ((WorkOrderInfo) o).getChildProjectNum();
                    Type type = new TypeToken<List<WorkOrderInfo>>(){}.getType();
                    stubSecondList = (new Gson()).fromJson(json,type);
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
    }

    /**
     * topbar监听
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 控件监听
     * @param view
     */
    @OnClick({R.id.tv_look_workorder, R.id.text_pay_money, R.id.tv_cancel_booking})
    public void onClick(View view) {
        int makeStatus = appointmentInfo.getMakeStatus();//预约状态
        switch (view.getId()) {
            case R.id.tv_look_workorder://查看工单
                //AppUtility.showToastMsg("查看工单");
                break;
            case R.id.text_pay_money://支付定金或再约一次
                if (makeStatus==AppointmentInfo.SWAIT_AFFIRM||makeStatus==AppointmentInfo.ALREADY_AFFIRM) {
                    if (orderInfo == null) return;
                    setResult(RESULT_OK);
                    startActivityForResult(AppUtility.getPayIntent(orderInfo, mContext), REQUEST_CODE);
                }else if(makeStatus==AppointmentInfo.ACCOMPLISH||makeStatus==AppointmentInfo.CANCEL) {
                    //AppUtility.showToastMsg("再约一次");
                    showActivity(MakeAppointmentActivity.class);
                }
                break;
            case R.id.tv_cancel_booking://取消预约
                //AppUtility.showToastMsg("取消预约");
                if (appointmentInfo != null)
                    createDialog(appointmentInfo);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    presenter.getBookingInfo(app.getApiService().getBookingInfo(app.getCurrentUserNum(),makeNum));
                    break;
            }
        }
    }

    /**
     * 取消预约确定 弹框
     * @param appointmentInfo
     */
    private void createDialog(final AppointmentInfo appointmentInfo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("确定取消预约吗?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cancelMakePresenter.CancelMake(getCall(appointmentInfo));
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    private Call<ResultBase> getCall(AppointmentInfo appointmentInfo) {
        cancelParams.setMakeNum(appointmentInfo.getMakeNum());
        cancelParams.setMakeStatus(-1);
        return app.getApiService().cancelMake(app.getCurrentUserNum(), cancelParams);
    }


    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void cancelSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showGetBookingInfoTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    /**
     * 获取预约信息成功
     * @param appointmentInfoResultBase
     */
    @Override
    public void showGetBookingInfoSuccess(ResultBase<AppointmentInfo> appointmentInfoResultBase) {
        appointmentInfo = appointmentInfoResultBase.getObj();
        showData();
    }

    /**
     * 显示数据
     */
    private void showData() {
        orderInfo = appointmentInfo.getOrderInfo();
        tvOrderNumber.setText(appointmentInfo.getMakeNum());//预约编号
        String predictShopTime = appointmentInfo.getPredictShopTime();
        predictShopTime = predictShopTime.substring(5, 7) + "月" + predictShopTime.substring(8, 10) + "日" + predictShopTime.substring(10);
        tvArriveTime.setText(predictShopTime);//预计到店时间
        tvRemark.setText(appointmentInfo.getRemark());//备注
        tvMoney.setText("¥"+appointmentInfo.getDownPayment().setScale(2,BigDecimal.ROUND_HALF_UP));//订金金额
        String makeName = "";//预约服务状态
        String moneyText = "";//合计订金或已付订金

        switch (appointmentInfo.getMakeStatus()){
            case AppointmentInfo.SWAIT_AFFIRM:
                makeName = "待确认";
                moneyText = "合计订金：";
                textPayMoney.setText("支付订金");
                tvCancelBooking.setVisibility(View.VISIBLE);
                textPayMoney.setVisibility(View.GONE);
                break;
            case AppointmentInfo.ALREADY_AFFIRM:
                makeName = "待付款";
                moneyText = "合计订金：";
                textPayMoney.setText("支付订金");
                tvCancelBooking.setVisibility(View.VISIBLE);
                textPayMoney.setVisibility(View.VISIBLE);
                break;
            case AppointmentInfo.SWAIT_SERVICE:
                makeName = "待服务";
                moneyText = "已付订金：";
//                tvLookWorkorder.setVisibility(View.GONE);
                tvCancelBooking.setVisibility(View.VISIBLE);
                textPayMoney.setVisibility(View.GONE);

                //  待服务时状态   判断是否是已过期的预约
                String predictTime = appointmentInfo.getPredictShopTime();
                if (StringUtil.getDateCompare(predictTime) < 0) { // 预约时间小于当前时间
                    makeName = "已过期";
                }
//                if (!"".equals(predictTime) && predictTime.length() >= 10) {
//                    Date currentDate = new Date(System.currentTimeMillis());
//                    Date predictDate;
//                    try {
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                        predictDate = format.parse(predictTime.substring(0, 10) /*"2016-01-03"*/);
//                        currentDate = format.parse(format.format(currentDate));
//                    } catch (ParseException e) {
//                        predictDate = new Date(System.currentTimeMillis());
//                        e.printStackTrace();
//                    }
//                    if (currentDate.compareTo(predictDate) > 0) {
//                        makeName="已过期";
//                    }
//                }
                break;
            case AppointmentInfo.ACCOMPLISH:
                makeName = "已完成";
                moneyText = "合计订金：";
//                tvLookWorkorder.setVisibility(View.GONE);
                textPayMoney.setVisibility(View.VISIBLE);
                textPayMoney.setText("再约一次");
                break;
            default:
                makeName = "已取消";
                moneyText = "合计订金：";
                textPayMoney.setVisibility(View.VISIBLE);
                textPayMoney.setText("再约一次");
                break;
        }
        tvBookingState.setText(makeName);//预约服务状态
        textMoney.setText(moneyText);//合计订金或已付订金
        //预约服务项目详情
        firstList = appointmentInfo.getWorkOrderInfoList();
        for (WorkOrderInfo workOrderInfo : firstList) {
            workOrderInfo.setParent(true);
            workOrderInfo.setSelected(false);
        }
        adapter.setDatas(firstList);
    }

    @Override
    public Context getContext() {
        return null;
    }
}
