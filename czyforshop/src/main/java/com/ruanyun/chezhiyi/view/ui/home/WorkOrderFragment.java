package com.ruanyun.chezhiyi.view.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseFragment;
import com.ruanyun.chezhiyi.commonlib.model.CustomerAccountModel;
import com.ruanyun.chezhiyi.commonlib.model.JieSuanParm;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.PayWorkOrdersBean;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MyWorkOrderParams;
import com.ruanyun.chezhiyi.commonlib.presenter.CustomerAccountPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.SubmitWorkOrderPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CustomerAccountView;
import com.ruanyun.chezhiyi.commonlib.view.SubmitWorkOrderMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderDetailedActivity;
import com.ruanyun.chezhiyi.view.adapter.AwaitOrClearingAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/12/27.
 */

public class WorkOrderFragment extends RefreshBaseFragment implements AwaitOrClearingAdapter.OnTakeOrderClickListener, CustomerAccountView, SubmitWorkOrderMvpView {

    public static final String TAB_TYPE_WAIT_AREA = "2";//等候区
    public static final String TAB_TYPE_SETTLE_ACCOUNTS = "3";//结算区
    public static final String TAB_TYPE_QUALITY_CHECK = "6";//质检去

    private static final String arg_params_status = "ARG_PARAMS_STATUS";
    private static final String workorderstatus = "";
    public MyWorkOrderParams params = new MyWorkOrderParams();
    //    private Topbar topbar;
    private ListView lvStation;
    private AwaitOrClearingAdapter mAdapter;//等候区或结算中获取
    private List<WorkOrderInfo> workOrderInfoList = new ArrayList<>();
    private String paramsStatus;
    private CustomerAccountPresenter customerAccountPresenter = new CustomerAccountPresenter();//获取用户余额
    private SubmitWorkOrderPresenter submitWorkOrderPresenter = new SubmitWorkOrderPresenter();//结算
    private JieSuanParm parm = null;//结算params
    private List<PayWorkOrdersBean> payWorkOrdersBeanList = null;
    private int PayType = 4;//结算方式
    private static final int REQUEST_FINISH_CODE = 234;// 支付成功后
    private WorkOrderInfo workOderInfoitem = new WorkOrderInfo();//选择的
    private int position;

    public WorkOrderFragment() {
    }

    public static WorkOrderFragment newInstance(String paramsStatus) {
        WorkOrderFragment fragment = new WorkOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(arg_params_status, paramsStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
        paramsStatus = getArguments().getString(arg_params_status);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_workbay, container, false);
        initView();
        customerAccountPresenter.attachView(this);
        submitWorkOrderPresenter.attachView(this);
        return mContentView;
    }

    private void initView() {
        parm = new JieSuanParm();
        payWorkOrdersBeanList = new ArrayList<>();
        initRefreshLayout();
        lvStation = getView(R.id.list);
        mAdapter = new AwaitOrClearingAdapter(mContext, R.layout.list_item_await_car_new, workOrderInfoList, paramsStatus);
        lvStation.setAdapter(mAdapter);
        mAdapter.setOnTakeOrderClickListener(this);
        lvStation.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                switch (paramsStatus) {
                    case TAB_TYPE_QUALITY_CHECK://质检
                        Intent qualityCheckIntent = new Intent(mContext, WorkOrderDetailedActivity.class);
                        qualityCheckIntent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                        showActivity(qualityCheckIntent);
                        break;
                    case TAB_TYPE_SETTLE_ACCOUNTS://结算
                        Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                        intent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                        showActivity(intent);
                        break;
                    case TAB_TYPE_WAIT_AREA://等候区
                        Intent waitAreaIntent = new Intent(mContext, WaitingAreaActivity.class);
                        waitAreaIntent.putExtra(C.IntentKey.WORKORDER_INFO, mAdapter.getItem(position));
                        showActivity(waitAreaIntent);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshWithLoading();
    }

    @Override
    public Call loadData() {
        Call call = null;
        switch (paramsStatus) {
            case TAB_TYPE_QUALITY_CHECK://质检
                //params.setWorkOrderStatusString(null);
                //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
                params.setWorkOrderStatus(6);
                params.setPageNum(currentPage);
                listPresenter.setTag(TAB_TYPE_QUALITY_CHECK);
                call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
                break;
            case TAB_TYPE_SETTLE_ACCOUNTS://结算
                //params.setWorkOrderStatusString(null);
                //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
                params.setWorkOrderStatus(8);
                params.setPageNum(currentPage);
                listPresenter.setTag(TAB_TYPE_SETTLE_ACCOUNTS);
                call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
                break;
            case TAB_TYPE_WAIT_AREA://等候区
                //工单状态(1工单等待确认 2已确认，等待进店 3等待施工 4即将开始施工 5施工中 6施工结束，质检中 7返修中，质检不合格 8等待结算，质检合格 9完成结算)等候中传值3 等待结算8 完成结算9
                params.setPageNum(currentPage);
                params.setWorkOrderStatus(3);
                listPresenter.setTag(TAB_TYPE_WAIT_AREA);
                call = app.getApiService().getMyWorkOrderList(app.getCurrentUserNum(), params);
                break;
        }
        return call;
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        workOrderInfoList.clear();
        workOrderInfoList.addAll(result.getResult());
        LogX.e("workOrderInfoListgetResult()", result.getResult().toString());
        LogX.e("workOrderInfoList", workOrderInfoList.toString());
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        workOrderInfoList.addAll(result.getResult());
        mAdapter.setDatas(workOrderInfoList);
    }

    /**
     * 收到刷新列表通知
     *
     * @param evet
     */
    @Subscribe
    public void onReciverefresh(String evet) {
        if (evet.equals(C.EventKey.REFRESH_WAIT_AREA)) {
            if (paramsStatus.equals(TAB_TYPE_WAIT_AREA))
                refreshWithLoading();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        customerAccountPresenter.detachView();
        submitWorkOrderPresenter.detachView();
        unRegisterBus();
    }

    @Override
    public void onTakeOrderClick(WorkOrderInfo workOrderInfo, int positionData) {
        showLoading();
        position = positionData;
        LogX.e("WorkOrderInfo", workOrderInfo.toString());
        workOderInfoitem = workOrderInfo;
        payWorkOrdersBeanList.clear();
        payWorkOrdersBeanList.add(new PayWorkOrdersBean(workOrderInfo.getWorkOrderNum(), workOrderInfo.getDxdAmount(), "", "0"));
        parm.setPayWorkOrders(payWorkOrdersBeanList);

        parm.setWorkOrderNumString(workOrderInfo.getWorkOrderNum());
        parm.setPayTotalAmount(workOrderInfo.getDxdAmount() + "");
        parm.setUserNum(workOrderInfo.getUser().getUserNum());
        parm.setCouponNum("");
        parm.setPreferentialPrice("0");
        //获取会员账号余额
        customerAccountPresenter.getCustomerAccountData(app.getApiService().getCusomerYue(workOrderInfo.getUser().getUserNum()));
    }

    //结算方式dialog
    private void dialogEducation(double remainMoney) {

        final AlertDialog builder = new AlertDialog.Builder(getActivity(), R.style.Dialog).create(); // 先得到构造器
        builder.show();
        builder.getWindow().setContentView(R.layout.jiesuan_dialog);
        LayoutInflater factory = LayoutInflater.from(getActivity());
        View view = factory.inflate(R.layout.jiesuan_dialog, null);
        builder.getWindow().setContentView(view);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg);
        RadioButton rb_remain = (RadioButton) view.findViewById(R.id.rb_remain);
        if (remainMoney < workOderInfoitem.getTotalAmount()) {
            rb_remain.setEnabled(false);
        }
        TextView tvcofirm = (TextView) view.findViewById(R.id.bt_confirm);
        TextView tv_yue = (TextView) view.findViewById(R.id.tv_yue);
        ImageView dimiss = (ImageView) view.findViewById(R.id.tv_dimiss);
        tv_yue.setText("¥ " + remainMoney);
        //获取屏幕的尺寸
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Window dialogWindow = builder.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);//显示在底部
        Display d = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.5
//        p.width = d.getWidth(); // 宽度设置为屏幕宽
        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕宽
        dialogWindow.setAttributes(p);
        tvcofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogX.e("结算loadingDialogHelper", app.getCurrentUserNum() + ";" + new Gson().toJson(parm) + "" + PayType);
                submitWorkOrderPresenter.addJieSuan(app.getApiService().addJieSuan2(app.getCurrentUserNum(), new Gson().toJson(parm), PayType));
                builder.dismiss();
            }
        });
        dimiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int checkId = checkedId;
                switch (checkId) {
                    case R.id.rb_remain:
                        PayType = 1;
                        break;
                    case R.id.rb_pay:
                        PayType = 2;
                        break;
                    case R.id.rb_wei:
                        PayType = 3;
                        break;
                    case R.id.rb_cash:
                        PayType = 4;
                        break;
                }
            }
        });
    }

    /*获取会员余额*/
    @Override
    public void getRemainSuccess(CustomerAccountModel customerAccount) {
        LogX.e("获取会员余额", customerAccount.toString());
        dissMissLoading();
        dialogEducation(customerAccount.getAccountBalance().doubleValue());
    }

    @Override
    public void getRemainFalse() {

    }

    //结算
    @Override
    public void addJieSuanSuccess(OrderInfo orderInfo) {
        LogX.e("结算addJieSuanSuccess", orderInfo.toString());
//        if (orderInfo != null) {
//            //   跳转到支付界面
//            startActivityForResult(AppUtility.getPayIntent(orderInfo, mContext), REQUEST_FINISH_CODE);
//            //            finish();
//        }

        workOrderInfoList.remove(position);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void disMissLoadingView() {
        app.loadingDialogHelper.dissMiss();
    }

    @Override
    public void showLoadingView() {
        app.loadingDialogHelper.showLoading(getActivity(), com.ruanyun.chezhiyi.commonlib.R.string.loading);
    }


}
