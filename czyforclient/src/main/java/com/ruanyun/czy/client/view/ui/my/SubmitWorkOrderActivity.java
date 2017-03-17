package com.ruanyun.czy.client.view.ui.my;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.JieSuanInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.SettleWorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.SubmitWorkOrderPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.SubmitWorkOrderMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.BookingServiceAdapter;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WorkOrderDetailedActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.OrderGoodsListAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;

/**
 * Description ：  工单结算   界面
 * <p/>
 * Created by ycw on 2016/9/20.
 */
public class SubmitWorkOrderActivity
        extends AutoLayoutActivity
        implements Topbar.onTopbarClickListener, SubmitWorkOrderMvpView, AdapterView.OnItemClickListener {


    public static final int REQUEST_YHQ_CODE = 278; //选择优惠劵
    private static final int REQUEST_FINISH_CODE = 234;// 支付成功后

    @BindView(R.id.rv_booking_project)
    RecyclerView rvBookingProject;//服务展开图
    @BindView(R.id.topbar)
    Topbar topbar;              // topbar
    @BindView(R.id.lv_dai_xia_dan)
    ListView lvDaiXiaDan;  // 代下单商品集合
    @BindView(R.id.tv_dai_xia_dan_total_price)
    TextView tvDaiXiaDanTotalPrice;    //代下单小计
    @BindView(R.id.lv_yi_gou)
    ListView lvYiGou;       //   已购商品集合
    @BindView(R.id.tv_yi_gou_total_price)
    TextView tvYiGouTotalPrice;     // 已购商品小计
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;        //订单总价
    @BindView(R.id.tv_dai_xia_dan_all_price)
    TextView tvDaiXiaDanAllPrice;       // 代下单项目总价
    @BindView(R.id.tv_down_payment)
    TextView tvDownPayment;     //预付定金
    @BindView(R.id.tv_yi_gou_all_price)
    TextView tvYiGouAllPrice;       //已购项目总价
    @BindView(R.id.tv_discount_price)
    TextView tvDiscountPrice;       //优惠金额
    @BindView(R.id.tv_to_be_paid)
    TextView tvToBePaid;        //待支付
    @BindView(R.id.tv_work_all_price)
    TextView tvWorkAllPrice;        //合计
    @BindView(R.id.tv_work_all_number)
    TextView tvWorkAllNumber;       //付款
    @BindView(R.id.ll_work_all_price)
    LinearLayout llWorkAllPrice;    //合计
    @BindView(R.id.tv_discount_info)
    TextView tvDiscountInfo;// 优惠劵展示
    @BindView(R.id.ll_discount)
    LinearLayout llDiscount;//去选择优惠劵
    @BindView(R.id.tv_dai_xia_dan_name)
    TextView tvDaiXiaDanName;
    @BindView(R.id.ll_dai_xia_dan_xiaoji)
    LinearLayout llDaiXiaDanXiaoji;
    @BindView(R.id.tv_yi_gou_name)
    TextView tvYiGouName;
    @BindView(R.id.ll_yi_gou_xiaoji)
    LinearLayout llYiGouXiaoji;

    private SubmitWorkOrderPresenter submitWorkOrderPresenter = new SubmitWorkOrderPresenter();
    private JieSuanInfo jieSuanInfo;        // 工单结算信息
    private BookingServiceAdapter adapter;
    //private Map<String, List<WorkOrderInfo>> subProject = new HashMap<>();//二级map
    private double discountPrice = 0;       //优惠金额
    private double toBePaid = 0;    //待支付
    private String jsonArrayString; //
    private String workOrderNumString;      // 工单编号“,”分割
    private List<WorkOrderInfo> workOrderList;      //工单集合
    private List<OrderGoodsInfo> daixiadanList;     //代下单商品集合
    private List<OrderGoodsInfo> yigouList;         //已购商品集合
    private List<OrderGoodsInfo> canCouponInfoList;    //可用优惠劵集合 优惠劵的 源集合
    private List<OrderGoodsInfo> couponInfoList = new ArrayList<>();  // 选择的 优惠劵集合
    private double totalAmount;//订单总价


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_submit_workorder);
        ButterKnife.bind(this);
        registerBus();
        submitWorkOrderPresenter.attachView(this);
        jieSuanInfo = getIntent().getParcelableExtra(C.IntentKey.JIESUAN_INFO);
        workOrderNumString = getIntent().getStringExtra(C.IntentKey.WORK_ORDER_NUM_STRING);
        initData();
    }

    private void initData() {
        topbar.setBackBtnEnable(true).setTttleText("工单结算").onBackBtnClick().setTopbarClickListener(this);

        //预约服务项目详情
        workOrderList = jieSuanInfo.getWorkOrderList();
        initWorkOrderInfo();

        //带下单项目
        daixiadanList = jieSuanInfo.getDxdGoodsList();
        if (daixiadanList == null || daixiadanList.isEmpty()) {
            tvDaiXiaDanName.setVisibility(View.GONE);
            llDaiXiaDanXiaoji.setVisibility(View.GONE);
            lvDaiXiaDan.setVisibility(View.GONE);
        } else {
            OrderGoodsListAdapter adapter = new OrderGoodsListAdapter(mContext, R.layout.item_order_goods, daixiadanList);
            lvDaiXiaDan.setAdapter(adapter);
            lvDaiXiaDan.setOnItemClickListener(this);
            AppUtility.measuredListHeight(lvDaiXiaDan);
            tvDaiXiaDanTotalPrice.setText("¥" + jieSuanInfo.getDxdAmount());       //带下单小计
        }

        //已购项目
        yigouList = jieSuanInfo.getYgGoodsList();
        if (yigouList == null || yigouList.isEmpty()) {
            tvYiGouName.setVisibility(View.GONE);
            llYiGouXiaoji.setVisibility(View.GONE);
            lvYiGou.setVisibility(View.GONE);
        } else {
            OrderGoodsListAdapter yiGouAdapter = new OrderGoodsListAdapter(mContext, R.layout.item_order_goods, yigouList);
            lvYiGou.setAdapter(yiGouAdapter);
//            已购项目不允许点击  看详情
//            lvYiGou.setOnItemClickListener(this);
            AppUtility.measuredListHeight(lvYiGou);
            tvYiGouTotalPrice.setText("¥" + jieSuanInfo.getYgAmount());       //已购小计
        }

        //可用优惠劵
        canCouponInfoList = jieSuanInfo.getYhqGoodsList();
        LogX.d(TAG, "=====优惠劵集合===" + canCouponInfoList.toString());

        //计算订单总价   =  代下单商品费用 + 已购商品费用
        //        totalAmount = new BigDecimal(jieSuanInfo.getDxdAmount() + jieSuanInfo.getYgAmount())
        // .setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() ;
        totalAmount = jieSuanInfo.getTotalAmount();
        // 订单总价
        tvAllPrice.setText("¥" + totalAmount);
        //代下单项目
        tvDaiXiaDanAllPrice.setText("¥" + jieSuanInfo.getDxdAmount());
        // 预付定金
        tvDownPayment.setText("-¥" + jieSuanInfo.getDownPayment());
        // 已购项目
        tvYiGouAllPrice.setText("-¥" + jieSuanInfo.getYgAmount());

        updatePrice();

    }

    /**
     * 更新价格
     */
    private void updatePrice() {
        // 已付金额  =  预付定金 + 已购项目 + 优惠金额
        //计算待支付总价   =  订单总价 - 已付金额
        toBePaid = new BigDecimal(totalAmount - (jieSuanInfo.getDownPayment() + jieSuanInfo.getYgAmount() + discountPrice)).setScale(1,
                BigDecimal.ROUND_HALF_UP).doubleValue();
        // 优惠金额
        tvDiscountPrice.setText("-¥" + discountPrice);
        // 待支付
        tvToBePaid.setText("¥" + toBePaid);

        if (couponInfoList.size() > 0) {
            tvDiscountInfo.setText(new StringBuilder().append("已选").append(couponInfoList.size()).append("张优惠劵").toString());
        } else {
            tvDiscountInfo.setText(canCouponInfoList == null ? "无可用优惠劵" : canCouponInfoList.size() == 0 ? "无可用优惠劵" : new StringBuilder().append(
                    canCouponInfoList.size()).append("张优惠劵可用").toString());
        }
        tvWorkAllPrice.setText(/*String.format("合计：¥%s", toBePaid)*/
                AppUtility.getSpannerString(mContext, "¥", String.format("合计：¥%s", toBePaid), false));
    }


    /**
     * 预约服务项目详情
     */
    private void initWorkOrderInfo() {
        final List<WorkOrderInfo> showWorkOrder = jieSuanInfo.getWorkOrderList();
        for (WorkOrderInfo workOrderInfo : showWorkOrder) {
            workOrderInfo.setParent(true);
            workOrderInfo.setSelected(false);
        }

        GridLayoutManager serverManager = new GridLayoutManager(mContext, 1);
        rvBookingProject.setLayoutManager(serverManager);
        adapter = new BookingServiceAdapter(mContext, showWorkOrder);
        adapter.setHasChild(false);
        rvBookingProject.setAdapter(adapter);
        //列表监听
        adapter.setOnBookingServiceClickListener(new BookingServiceAdapter.OnBookingServiceClickListener() {
            @Override
            public void onBookingServiceClick(WorkOrderInfo workOrderInfo) {
                Intent intent = new Intent(mContext, WorkOrderDetailedActivity.class);
                intent.putExtra(C.IntentKey.WORKORDER_INFO, workOrderInfo);
                showActivity(intent);
            }
        });
    }

    @OnClick({R.id.ll_discount, R.id.tv_work_all_number})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_work_all_number) {
            // : 2016/11/2 付款
            jsonArrayString = getJsonString();
            LogX.d("retrofit", "-----工单结算------>\n" + jsonArrayString);
            // 提交订单
            submitWorkOrderPresenter.addJieSuan(app.getApiService().addJieSuan(app.getCurrentUserNum(), jsonArrayString));
        } else if (id == R.id.ll_discount) {
            if (canCouponInfoList == null || canCouponInfoList.size() == 0) {
                return;
            }
            // TODO: 2016/11/2 选择优惠劵   将可用优惠劵传到下一个界面
            //            Intent intent = new Intent(mContext, SelectDiscountCouponActivity.class);
            Intent intent = new Intent(mContext, SelectCouponActivity.class);
            intent.putParcelableArrayListExtra(C.IntentKey.DISCOUNT_COUPON_LIST, (ArrayList<? extends Parcelable>) canCouponInfoList);
            intent.putExtra(C.IntentKey.DISCOUNT_COUPON_SELECT, true);
            startActivityForResult(intent, REQUEST_YHQ_CODE);

        }
    }

    private List<SettleWorkOrderInfo.PayWorkOrdersBean> workOrdersBeanArrayList = new ArrayList<>();

    private SettleWorkOrderInfo settleWorkOrderInfo = new SettleWorkOrderInfo();

    /**
     * 付款的json参数
     */
    private String getJsonString() {
        workOrdersBeanArrayList.clear();
        for (int i = 0; i < workOrderList.size(); i++) {
            SettleWorkOrderInfo.PayWorkOrdersBean payWorkOrdersBean = new SettleWorkOrderInfo.PayWorkOrdersBean();
            WorkOrderInfo workOrderInfo = workOrderList.get(i);
            payWorkOrdersBean.setPayAmount(new BigDecimal(workOrderInfo.getPayAmount()).setScale(1, BigDecimal.ROUND_HALF_UP).toString());
            String[] coupon = getCouponNum(workOrderInfo.getWorkOrderNum());
            payWorkOrdersBean.setCouponNum(coupon[0]);//优惠劵编号
            payWorkOrdersBean.setPreferentialPrice(coupon[1]);//优惠金额
            payWorkOrdersBean.setWorkOrderNum(workOrderInfo.getWorkOrderNum());
            workOrdersBeanArrayList.add(payWorkOrdersBean);
        }
        settleWorkOrderInfo.setPayTotalAmount(new BigDecimal(toBePaid).setScale(1, BigDecimal.ROUND_HALF_UP).toString());
        settleWorkOrderInfo.setPayWorkOrders(workOrdersBeanArrayList);
        settleWorkOrderInfo.setUserNum(app.getCurrentUserNum());
        settleWorkOrderInfo.setWorkOrderNumString(workOrderNumString);
        // TODO: 2016/11/2 通用劵
        String[] coupon = getCouponNum("");
        settleWorkOrderInfo.setCouponNum(coupon[0]);
        settleWorkOrderInfo.setPreferentialPrice(coupon[1]);
        return new Gson().toJson(settleWorkOrderInfo);
    }

    /**
     * 获取优惠劵的num
     *
     * @param workOrderNum
     */
    private String[] getCouponNum(String workOrderNum) {
        String[] couponNum = new String[]{"", "0"};
        if (couponInfoList.size() > 1) {//一定是普通优惠劵
            for (int i = 0; i < couponInfoList.size(); i++) {
                OrderGoodsInfo orderGoodsInfo = couponInfoList.get(i);//优惠劵
                if (orderGoodsInfo.getWorkOrderNum().equals(workOrderNum)) {
                    couponNum[0] = orderGoodsInfo.getOrderGoodsDetailNum();
                    couponNum[1] = String.valueOf(orderGoodsInfo.getSalePrice());
                    break;
                }
            }
        } else if (couponInfoList.size() > 0) { //  有一张券的时候
            OrderGoodsInfo orderGoodsInfo = couponInfoList.get(0);//优惠劵
            if (AppUtility.isNotEmpty(workOrderNum)) {// 是否有普通优惠劵
                if (orderGoodsInfo.getWorkOrderNum().equals(workOrderNum)) {
                    couponNum[0] = orderGoodsInfo.getOrderGoodsDetailNum();
                    couponNum[1] = String.valueOf(orderGoodsInfo.getSalePrice());
                }
            } else {
                if (TextUtils.isEmpty(orderGoodsInfo.getWorkOrderNum()) && orderGoodsInfo.getProjectParent().equals("000000")) {
                    couponNum[0] = orderGoodsInfo.getOrderGoodsDetailNum();
                    couponNum[1] = String.valueOf(orderGoodsInfo.getSalePrice());
                }
            }
        }
        return couponNum;
    }

    @Subscribe
    public void getCouponList(Event<Map<String, OrderGoodsInfo>> event) {
        if (event != null && event.key.equals(C.EventKey.DISCOUNT_COUPON_MAP)) {
            for (int i = 0; i < couponInfoList.size(); i++) {
                couponInfoList.get(i).setSelect(false);
            }
            couponInfoList.clear();//清空已选优惠劵
            discountPrice = 0;
            Map<String, OrderGoodsInfo> couponTempList = event.value; // 选择的优惠劵的集合
            if (!couponTempList.isEmpty()) { // 选择的优惠劵不为空
                for (String key : couponTempList.keySet()) {   // 遍历选择的优惠劵
                    OrderGoodsInfo selectInfo = couponTempList.get(key);
                    if (selectInfo == null) {
                        break;
                    }
                    for (int i = 0; i < canCouponInfoList.size(); i++) {// 遍历优惠劵集合修改源集合的选中状态
                        OrderGoodsInfo goodsInfo = canCouponInfoList.get(i);
                        if (selectInfo.getOrderGoodsDetailNum().equals(goodsInfo.getOrderGoodsDetailNum())) {
                            goodsInfo.setSelect(true);
                            discountPrice += goodsInfo.getSalePrice().doubleValue();
                            couponInfoList.add(goodsInfo);
                            break;
                        }
                    }
                }
            }
            //修改界面显示
            updatePrice();
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
        submitWorkOrderPresenter.detachView();
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    /**
     * @param orderInfo
     */
    @Override
    public void addJieSuanSuccess(OrderInfo orderInfo) {
        if (orderInfo != null) {
            //   跳转到支付界面
            startActivityForResult(AppUtility.getPayIntent(orderInfo, mContext), REQUEST_FINISH_CODE);
            //            finish();
        }
    }

    /**
     * 代下单   或   已购  商品商品的  点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderGoodsInfo goodsInfo = (OrderGoodsInfo) parent.getItemAtPosition(position);
        AppUtility.showGoodsWebView((goodsInfo.getAmount() == null || goodsInfo.getAmount().toString().equals("")) ? 0 : goodsInfo.getAmount().doubleValue(),
                app.getCurrentUserNum(),
                goodsInfo.getGoodsNum(),
                C.OrderType.ORDER_TYPE_CP,
                goodsInfo.getGoodsNum(),
                app.getCurrentUserNum(),
                mContext,
                goodsInfo.getGoodsName(),
                goodsInfo.getProjectParent(),
                goodsInfo.getMainPhoto(),
                "2",
                goodsInfo.getViceTitle());
    }

    /**
     * 工单结算完成
     */
    @Subscribe
    public void upViewToFinish(Event<String> event){
        if (event.key.equals(C.EventKey.WORK_ORDER_LIST_FINISH)) {
            finish();
        }
    }

}
