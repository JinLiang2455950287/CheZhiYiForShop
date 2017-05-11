package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.OrderReceivingParams;
import com.ruanyun.chezhiyi.commonlib.presenter.LeisureStationPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.view.WaitingAreaDetailsMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderListActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.LabelFlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.WorkLocationPopWindow;
import com.ruanyun.chezhiyi.view.adapter.WaitionAreaServiceGoddsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**
 * Description ：工位查看  等候区  —— 接单详细页
 * <p/>
 * Created by hdl on 2016/9/26.
 */
public class WaitingAreaActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,
        WorkLocationPopWindow.OnPopupClickListener, WaitingAreaDetailsMvpView {
    public static final int REQUEST_CODE = 223;
    Topbar topbar;
    @BindView(R.id.iv_car_photo)
    ImageView ivCarPhoto;//车辆图片
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;//车牌
    @BindView(R.id.tv_await_time_or_money)
    TextView tvAwaitTimeOrMoney;//等候时间
    @BindView(R.id.tv_user_name)
    TextView tvUserName;//用户name
    @BindView(R.id.labelflow_service_item)
    LabelFlowLayout labelflowServiceItem;//服务项目标签
    @BindView(R.id.tv_user_remark)
    TextView tvUserRemark;//用户备注
    @BindView(R.id.tv_workorder_number)
    TextView tvWorkorderNumber;//工单编号
    @BindView(R.id.lv_service_item)
    ListView lvServiceItem;//服务项目商品列表
    @BindView(R.id.tv_service_gongwei)
    TextView tvServiceGongwei;//服务工位选择
    @BindView(R.id.tv_service_technician)
    TextView tvServiceTechnician;//服务技师
    @BindView(R.id.tv_remark)
    TextView tvRemark;//客服备注
    @BindView(R.id.text_remark)
    TextView textRemark;//客服备注
    @BindView(R.id.btn_order_receiving)
    Button btnOrderReceiving;//接单
    private WorkLocationPopWindow popwindow;//工位选择popwindow
    private List<WorkBayInfo> workLocationList = new ArrayList<>();//工位集合
    private String workbayInfoNum = "";//工位选择popwindow弹出时默认选中的项，工位编号
    private WorkBayInfo workBayInfo;//选中工位
    private WorkOrderInfo mWorkOrderInfo;
    private LeisureStationPresenter presenter = new LeisureStationPresenter();
    private OrderReceivingParams params = new OrderReceivingParams();//接单Params
    private WaitionAreaServiceGoddsAdapter adapter;//服务项目商品列表
    /**
     * 服务项目商品集合
     */
    private List<OrderGoodsInfo> mWorkOrderGoodsList;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_waiting_area);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
        initData();
        initAdapter();
        getWorkorderGoods();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    private void initView() {
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        topbar.setTttleText("等候区")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData() {
        mWorkOrderInfo = getIntent().getParcelableExtra(C.IntentKey.WORKORDER_INFO);
        ImageUtil.loadImage(mContext, "url", ivCarPhoto, R.drawable.default_img);
        tvCarNumber.setText(mWorkOrderInfo.getServicePlateNumber());//车牌号
        tvAwaitTimeOrMoney.setText(awaitTime(mWorkOrderInfo.getCreateTime()));//等候时间
        tvUserName.setText(mWorkOrderInfo.getUser() == null ? "" : mWorkOrderInfo.getUser().getNickName());//用户姓名
        // TODO: 2016/10/29  客户备注
        tvUserRemark.setVisibility(View.GONE);
        tvUserRemark.setText("客户备注：");//客户备注
        tvWorkorderNumber.setText(mWorkOrderInfo.getWorkOrderNum());
        if (false) {//等技师
            tvServiceGongwei.setText("1号维修工位");
            tvServiceGongwei.setTextColor(ContextCompat.getColor(mContext, R.color.text_black));
            tvServiceGongwei.setEnabled(false);
        } else tvServiceGongwei.setText("选择工位");

        tvServiceTechnician.setText("等待技师接单");
        // TODO: 2016/11/2 客服备注
        textRemark.setVisibility(View.INVISIBLE);
        tvRemark.setText(""); //客服备注

        //服务项目标签
        labelflowServiceItem.removeAllViews();
        ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(source);
        lp.leftMargin = 5;
        lp.rightMargin = 15;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < 1; i++) {
            TextView view = new TextView(mContext);
            view.setText(mWorkOrderInfo.getProjectName());
            view.setTextColor(Color.WHITE);
            view.setTextSize(12);
            view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.shape_text_station_label));
            labelflowServiceItem.addView(view, lp);
        }
    }

    private void initAdapter() {
        mWorkOrderGoodsList = new ArrayList<>();
        adapter = new WaitionAreaServiceGoddsAdapter(mContext, R.layout.list_item_service_goods_count, mWorkOrderGoodsList);
        lvServiceItem.setAdapter(adapter);
        // TODO: 2017/1/6 等候区商品的 点击查看详情
//        lvServiceItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                OrderGoodsInfo goodsInfo = mWorkOrderGoodsList.get(position);
////                String goodsType = AppUtility.getTypeName(goodsInfo.getGoodsInfo().getGoodsType());
////                if ( goodsType.equals(C.OrderType.ORDER_TYPE_CP)||
////                        goodsType.equals(C.OrderType.ORDER_TYPE_TG)||
////                        goodsType.equals(C.OrderType.ORDER_TYPE_JF) ) {  // 是产品。团购。积分商品  跳转到详情页
//                    AppUtility.showGoodsWebView((goodsInfo.getAmount() == null || goodsInfo.getAmount().equals("")) ? 0 : goodsInfo.getAmount().doubleValue(),
//                            app.getCurrentUserNum(),
//                            goodsInfo.getGoodsNum(),
//                            C.OrderType.ORDER_TYPE_CP,
//                            goodsInfo.getGoodsNum(),
//                            app.getCurrentUserNum(),
//                            mContext,
//                            goodsInfo.getGoodsName(),
//                            goodsInfo.getProjectParent(),
//                            goodsInfo.getMainPhoto(),
//                            "2",
//                            goodsInfo.getViceTitle());
////                }
//            }
//        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    @OnClick({R.id.tv_service_gongwei, R.id.btn_order_receiving})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_service_gongwei:
                getLeisureStation(); //获取工位
                break;
            case R.id.btn_order_receiving:
                User user = app.getUser();
                if (user == null) return;
                if (user.getIsOrder() != 1) {
                    AppUtility.showToastMsg("您没有权限接单");
                    return;
                }
                if (TextUtils.isEmpty(user.getWorkStatus())) {
                    return;
                } else if (user.getWorkStatus().equals(C.WORKSTATE_BUSY)) {
                    AppUtility.showToastMsg("工作中技师不可接单");
                    return;
                }
                orderReceiving();
                break;
        }
    }

    /**
     * 获取空闲工位
     */
    private void getLeisureStation() {
        Call<ResultBase<List<WorkBayInfo>>> leisureStationCall = app.getApiService().getWorkOrderGongWei(app
                .getCurrentUserNum(), mWorkOrderInfo.getProjectNum());
        presenter.setLeisureStationMvpView(leisureStationCall);
    }

    /**
     * 获取工单商品列表
     */
    private void getWorkorderGoods() {
        Call<ResultBase<List<OrderGoodsInfo>>> workorderGoodsCall = app.getApiService().getWorkorderGoodsList(app
                .getCurrentUserNum(), mWorkOrderInfo.getWorkOrderNum());
        presenter.setworkorderGoodsMvpView(workorderGoodsCall);
    }

    /**
     * 接单
     */
    private void orderReceiving() {
        if (workBayInfo == null) {
            AppUtility.showToastMsg("请选择工位");
            return;
        }
        params.setUserName(app.getUser() == null ? "" : app.getUser().getNickName());
        params.setWorkOrderNum(mWorkOrderInfo.getWorkOrderNum());
        params.setWorkbayInfoNum(workBayInfo.getWorkbayInfoNum());
        params.setWorkbayInfoName(workBayInfo.getWorkbayName());
        Call<ResultBase> orderReceivingCall = app.getApiService().saveOrderReceiving(app.getCurrentUserNum(), params);
        presenter.setOrderReceivingMvpView(orderReceivingCall);
    }

    /**
     * 选择空闲工位popwindow
     */
    private void showPopwindow() {
        popwindow = new WorkLocationPopWindow(mContext, getView(R.id.rl_waiting_area), workLocationList,
                workbayInfoNum);
        if (workLocationList != null && workLocationList.size() > 0) {
            popwindow.setTitle("请选择工位");
        }
        popwindow.setOnPopupClickListener(this);
        popwindow.show(true);
    }

    /**
     * 工位选择回调
     *
     * @param workBayInfo
     */
    @Override
    public void onItemClick(WorkBayInfo workBayInfo) {
        this.workBayInfo = workBayInfo;
        workbayInfoNum = workBayInfo.getWorkbayInfoNum();
        tvServiceGongwei.setText(workBayInfo.getWorkbayName());
    }

    /**
     * 等候时间
     *
     * @param createTimeStr
     */
    private SpannableString awaitTime(String createTimeStr) {
        long createTime = AppUtility.date2TimeStamp(createTimeStr, "yyyy-MM-dd HH:mm:ss");
        long currentTime = AppUtility.date2TimeStamp(AppUtility.getCurrentDateAndTime(), "yyyy-MM-dd HH:mm:ss");
        long awaitTime = currentTime - createTime;
        String str = "已等候：" + String.format("%.1f", awaitTime / 1000.0 / 60 / 60) + "H";
        SpannableString spStr = new SpannableString(str);
        spStr.setSpan(new RelativeSizeSpan(1.3f), 4, str.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spStr.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.bg_tangerine))
                , 4, str.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spStr;
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
    public void showLeisureStationErrer(String msg) {
        AppUtility.showToastMsg(msg);
    }

    /**
     * 获取空闲工位成功
     *
     * @param workBayInfoResultBase
     */
    @Override
    public void showLeisureStationSuccess(ResultBase<List<WorkBayInfo>> workBayInfoResultBase) {
        workLocationList = workBayInfoResultBase.getObj();
        showPopwindow();//显示工位
    }

    /**
     * 获取工单商品成功
     *
     * @param workBayInfoResultBase
     */
    @Override
    public void showWorkorderGoodsSuccess(ResultBase<List<OrderGoodsInfo>> workBayInfoResultBase) {
        mWorkOrderGoodsList = workBayInfoResultBase.getObj();
        adapter.setDatas(mWorkOrderGoodsList);
        AppUtility.measuredListHeight(lvServiceItem);
    }

    /**
     * 接单成功
     *
     * @param resultBase
     */
    @Override
    public void showOrderReceivingSuccess(ResultBase resultBase) {
          /*接单成功后，修改技师状态为繁忙*/
        app.getUser().setWorkStatus(C.WORKSTATE_BUSY);
        // TODO 跳转到服务工单
        Intent intent = new Intent(mContext, WorkOrderListActivity.class);
        intent.putExtra("item", 1);
        startActivityForResult(intent, REQUEST_CODE);
        //showActivity(WorkOrderListActivity.class);
        EventBus.getDefault().post(C.EventKey.REFRESH_WAIT_AREA);  //刷新等候区列表
        // 刷新 个人信息
        app.beanCacheHelper.getUserByNum(app.getCurrentUserNum(), this, C.EventKey.UPDATE_USER_INFO);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            EventBus.getDefault().postSticky(C.EventKey.UPDATE_WORKER_NUMBER);  //更新界面 工单的数量
            finish();
        }
    }
}
