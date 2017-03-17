package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.params.DiscountCouponParams;
import com.ruanyun.chezhiyi.commonlib.presenter.DiscountCouponPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.DiscountCouponMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.view.adapter.SelectDiscountCouponAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**
 * Description ：选择优惠券界面
 * <p/>
 * Created by hdl on 2016/9/21.
 */
@Deprecated
public class SelectDiscountCouponActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,
        DiscountCouponMvpView {

    @BindView(R.id.tv_not_usable_coupon)
    TextView tvNotUsableCoupon;//无符合条件的优惠券时使用
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.emptyview)
    RYEmptyView emptyView;

    private int maxSelectNumber = 1;//最大可选择优惠券的数量
    private int selectNumber = 0;//已选择的优惠券的数量
    /** 付款金额 !=-1 表示下订单单选优惠券  */
    private double amount;
    /** 下订单选择优惠券时使用  */
    private String projectNum;
    private OrderGoodsInfo  checkedCoupon;//下订单选择优惠券时 选择的优惠券
    /** 回传优惠券集合   */
    private List<OrderGoodsInfo> infoList;
    /** 可用的优惠券类型，及选择数量，0为未选择 1为已选择   */
    private HashMap<String, Integer> couponTypes = new HashMap<>();
    private DiscountCouponPresenter presenter = new DiscountCouponPresenter();
    private DiscountCouponParams params = new DiscountCouponParams();
    private SelectDiscountCouponAdapter adapter;
    private List<OrderGoodsInfo> orderGoodsInfos;//列表展示的优惠劵

    List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    HashMap<String, String> stairprojectMap = new HashMap<>();//一级工单服务分类Map
    /**  选择的要结算的工单编号，用”,”拼接  */
    //private String workOrderNumString;
    private List<OrderGoodsInfo> discountCouponList; //接收  传入的优惠劵列表
    private boolean discountCouponSelect;//工单结算选择优惠劵
    private HashMap<String, OrderGoodsInfo> selectWorkOrderCoupon = new HashMap<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
        initData();
    }

    private void initView() {
        topbar.setTttleText("优惠券")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("确定")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        emptyView.bind(refreshLayout);
        emptyView.setOnReloadListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                getUnUsedCoupon();
            }
        });
    }

    private void initData() {
        amount = getIntent().getDoubleExtra(C.IntentKey.AMOUNT, -1);
        if (amount != -1) {
            projectNum = getIntent().getStringExtra(C.IntentKey.PROJECTNUM);
//            projectNum = "002000000000000";
        } else { //工单结算时  传入的优惠劵
            //workOrderNumString = getIntent().getStringExtra(C.IntentKey.WORK_ORDER_NUM_STRING);
            //接收传入的优惠劵列表
            discountCouponList = getIntent().getParcelableArrayListExtra(C.IntentKey.DISCOUNT_COUPON_LIST);
            discountCouponSelect = getIntent().getBooleanExtra(C.IntentKey.DISCOUNT_COUPON_SELECT, false);
            for (int i = 0; i < discountCouponList.size(); i++) {
                OrderGoodsInfo orderGoodsInfo = discountCouponList.get(i);
                if (orderGoodsInfo.isSelect()) {
                    if (TextUtils.isEmpty(orderGoodsInfo.getWorkOrderNum())) {
                        selectWorkOrderCoupon.put("000000", orderGoodsInfo);
                    } else {
                        selectWorkOrderCoupon.put(orderGoodsInfo.getWorkOrderNum(), orderGoodsInfo);
                    }
                }
            }
        }
        orderGoodsInfos = new ArrayList<>();
        stairprojectTypes = new ArrayList<>();
        stairprojectTypes = DbHelper.getInstance().getSeviceTypes();//获取工单服务和技师技能数据结构集合
        for (ProjectType stairprojectType : stairprojectTypes) {
            stairprojectMap.put(stairprojectType.getProjectNum(), stairprojectType.getProjectName());
        }
        orderGoodsInfos = discountCouponSelect ? discountCouponList : orderGoodsInfos;
        adapter = new SelectDiscountCouponAdapter(mContext, R.layout.list_item_my_coupon, orderGoodsInfos, stairprojectMap);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderGoodsInfo info = orderGoodsInfos.get(position);
                if (discountCouponSelect) {  //工单结算选择优惠劵

                    if (info.isSelect()) {//如果选中时 改变为未选中
                        info.setSelect(false);
                        selectWorkOrderCoupon.remove(TextUtils.isEmpty(info.getWorkOrderNum()) ? "000000" : info.getWorkOrderNum());
                        adapter.setDatas(orderGoodsInfos);
                        selectNumber--;
                    } else {  // 选择 选中
                        if (AppUtility.isNotEmpty(info.getWorkOrderNum())) {     // 其他优惠劵
                            if (selectWorkOrderCoupon.get("000000") != null) {      // 已经选择了通用劵
                                selectWorkOrderCoupon.get("000000").setSelect(false);//清除通用劵的选中状态
                                selectWorkOrderCoupon.clear();
                                selectNumber = 0;
                            } else { // 没有选择通用劵  找出选中集合中的相同 WorkOrderNum 的优惠劵 选中状态 置 空 false
                                for (String key : selectWorkOrderCoupon.keySet()) {//遍历map的key值
                                    if (key.equals(info.getWorkOrderNum())) {
                                        selectWorkOrderCoupon.get(key).setSelect(false);
                                    }
                                }
                            }
                            selectNumber++;
                            selectWorkOrderCoupon.put(info.getWorkOrderNum(), info);
                        } else if (info.getProjectParent().equals("000000")) {    //通用券
                            //遍历map 将map中的值全部修改成不选中
                            for (String key : selectWorkOrderCoupon.keySet()) {
                                OrderGoodsInfo value = selectWorkOrderCoupon.get(key);
                                value.setSelect(false);
                            }
                            selectWorkOrderCoupon.clear();
                            selectWorkOrderCoupon.put("000000", info);
                            selectNumber = 1;
                        }
                        //选中当前项
                        info.setSelect(true);
                    }
                    adapter.notifyDataSetChanged();

                } else {  //其他选择优惠劵

                    if (info.isSelect()) {//如果选中时 改变为未选中
                        info.setSelect(false);
                        adapter.setDatas(orderGoodsInfos);
                        selectNumber--;
                    } else {
                        if (selectNumber < maxSelectNumber) {
                            if (amount != -1) {
                                couponAvailable(position);
                                checkedCoupon = info;
                            } else {
                                if (couponTypes.get(info.getProjectParent()) != null) {
                                    if (couponTypes.get(info.getProjectParent()) == 0) {
                                        couponAvailable(position);
                                        couponTypes.put(info.getProjectParent(), 1);
                                    } else {//同种专用优惠券已选中一个
                                        AppUtility.showToastMsg("只能选择一张" + adapter.getCouponName(info.getProjectParent()));

                                    }
                                } else {//通用券
                                    couponAvailable(position);
                                }
                            }
                        } else {
                            AppUtility.showToastMsg("最多只能选择" + maxSelectNumber + "张");
                        }
                    }
                }
            }
        });
        if (amount != -1) {
            getUnUsedCoupon();
        }
//        else {
//            getWorkOrderCoupon();
//        }
    }

    /**
     * 优惠券可选择时
     */
    private void couponAvailable(int position) {
        orderGoodsInfos.get(position).setSelect(true);
        adapter.setDatas(orderGoodsInfos);
        selectNumber++;
    }


    /**
     * 获取未使用的优惠券 (下订单界面选择优惠券)
     */
    private void getUnUsedCoupon() {
        emptyView.showLoading();
        params.setGoodsType(DiscountCouponParams.YHQ_02);
        params.setGoodsDetailStatus(Integer.parseInt(DiscountCouponActivity.UNUSED));
        params.setAmount(new BigDecimal(amount));
        params.setProjectNum(projectNum);
        Call<ResultBase<List<OrderGoodsInfo>>> call = app.getApiService().getClientDiscountCoupon(app
                .getCurrentUserNum(), params);
        presenter.setDiscountCouponMvpView(call);
    }

    /**
     * 获取司机工单结算有效优惠券
     */
    private void getWorkOrderCoupon() {
//        emptyView.showLoading();
//        Call<ResultBase<List<OrderGoodsInfo>>> call = app.getApiService().getClientWorkOrderCoupon(app
//                .getCurrentUserNum(), workOrderNumString);
//        presenter.setDiscountCouponMvpView(call);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right) {
            confirmDispose();
        }
    }

    /**
     * 点击确认时 处理
     */
    private void confirmDispose() {
//        if(selectNumber <= 0){
//            AppUtility.showToastMsg("请选择优惠券");
//            return;
//        }
        infoList = new ArrayList<>();
        if (amount != -1) {
            infoList.add(checkedCoupon);
            EventBus.getDefault().post(new Event(C.EventKey.DISCOUNT_COUPON_LIST, infoList));
        } else {
//            for (OrderGoodsInfo orderGoodsInfo : orderGoodsInfos) {
//                if(orderGoodsInfo.isSelect()){
//                    selectWorkOrderCoupon.put(orderGoodsInfo.getWorkOrderNum(), orderGoodsInfo);
//                }
//            }
            setResult(RESULT_OK, getIntent().putParcelableArrayListExtra(C.IntentKey.DISCOUNT_COUPON_LIST, (ArrayList<? extends Parcelable>) orderGoodsInfos));
            EventBus.getDefault().post(new Event(C.EventKey.DISCOUNT_COUPON_MAP, selectWorkOrderCoupon));
        }
        finish();
    }

    @Override
    public void showLoadingView() {
        emptyView.showLoading();
    }

    @Override
    public void dismissLoadingView() {
//        emptyView.loadSuccuss();
    }

    @Override
    public void showDiscountCouponErrer(String msg) {
        emptyView.showLoadFail();
    }

    @Override
    public void showDiscountCouponSuccess(ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase) {
        orderGoodsInfos = orderGoodsInfoResultBase.getObj();
        if (orderGoodsInfos == null || orderGoodsInfos.size() == 0) {
            emptyView.showEmpty();
            return;
        }
        emptyView.loadSuccuss();
        if (amount != -1) {
            getAvailableCoupon();
        }
//        else {
//            couponProjectNumSave();
//        }
        adapter.setDatas(orderGoodsInfos);
    }

    @Override
    public void showTechnicianDiscountCouponSuccess(ResultBase<List<ProductInfo>> productInfoResultBase) {
        //不使用(技师端使用)
    }

    /**
     * 下订单界面 选择优惠券时筛选可用优惠券
     */
    private void getAvailableCoupon() {
        for (OrderGoodsInfo info : orderGoodsInfos) {
            if (!"000000".equals(info.getProjectParent())) {
                if (!projectNum.equals(info.getProjectParent())) {
                    orderGoodsInfos.remove(info);
                }
            }
        }
    }

    /**根据工单数量判断可用优惠券数量及类型*/
//    private void couponProjectNumSave() {
//        String[] arrStr = null;
//        arrStr = workOrderNumString.split(",");
//        maxSelectNumber = arrStr.length;
//        for (OrderGoodsInfo orderGoodsInfo : orderGoodsInfos) {
//            if(!"000000".equals(orderGoodsInfo.getProjectParent())){
//                couponTypes.put(orderGoodsInfo.getProjectParent(), 0);
//            }
//        }
//    }
}
