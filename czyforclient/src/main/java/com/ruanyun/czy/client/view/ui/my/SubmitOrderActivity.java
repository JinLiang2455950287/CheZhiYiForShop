package com.ruanyun.czy.client.view.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.IGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.DiscountCouponParams;
import com.ruanyun.chezhiyi.commonlib.model.params.ExchangeParams;
import com.ruanyun.chezhiyi.commonlib.model.params.MakeOrderParams;
import com.ruanyun.chezhiyi.commonlib.presenter.ExchangeScorePresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.SubmitOrderPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.ExchangeMvpView;
import com.ruanyun.chezhiyi.commonlib.view.SubmitOrderMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

/**
 * Description ：购买商品  提交订单界面
 * <p/>
 * Created by ycw on 2016/9/20.
 */
public class SubmitOrderActivity
        extends AutoLayoutActivity
        implements Topbar.onTopbarClickListener, View.OnClickListener, SubmitOrderMvpView, ExchangeMvpView {

    private static final int REQ_SELECT_DISCOUNT = 1233;
    Topbar topbar;
    TextView tvType;// 商品类型
    ImageView ivGoodsPic;//商品图片
    TextView tvTitle;//商品标题
    TextView tvTotal;//合计
    TextView tvPrice;//商品单价
    TextView tvAllPrice;// 商品合计
    Button btnSubmitOrder;//提交订单按钮
    ImageButton imbtnSub;// 商品数量减 按钮
    ImageButton imbtnAdd;// 商品数量加 按钮
    TextView editNumeber;// 商品数量
    TextView tvPreferential;// 优惠劵可用
    LinearLayout llSelectCoupon;
    private LinearLayout llNeedPay, llNeedScore;
    private TextView tvNeedScore, tvNeedPay;
    private View llProductOperation;

    private MakeOrderParams makeOrderParams = new MakeOrderParams();
    private SubmitOrderPresenter submitOrderPresenter = new SubmitOrderPresenter();
    private ExchangeScorePresenter exchangeScorePresenter = new ExchangeScorePresenter();
    private IGoodsInfo goodsInfo;// 当前商品的信息
    private int totalCount = 1;
    private double actualPrice;//实际支付金额
    private String couponNum;//优惠劵的编号
    private double preferentialPrice = 0;//优惠金额
    private double totalPrice;//总价
    private boolean isExchange = false;// 当前页面 是否是积分兑换
    private List<OrderGoodsInfo> mCouponList;//当前可用优惠劵
    private OrderGoodsInfo orderGoodsCoupon;// 当前选择的优惠劵

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        submitOrderPresenter.attachView(this);
        exchangeScorePresenter.attachView(this);
//        registerBus();
        setContentView(R.layout.activity_submit_order);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        tvType = getView(R.id.tv_type);
        ivGoodsPic = getView(R.id.iv_goods_pic);
        tvTitle = getView(R.id.tv_title);
        tvTotal = getView(R.id.tv_total);
        tvPrice = getView(R.id.tv_price);
        tvAllPrice = getView(R.id.tv_all_price);
        btnSubmitOrder = getView(R.id.btn_submit_order);
        imbtnSub = getView(R.id.imbtn_sub);
        imbtnAdd = getView(R.id.imbtn_add);
        editNumeber = getView(R.id.edit_number);
        llSelectCoupon = getView(R.id.ll_select_coupon);
        tvPreferential = getView(R.id.tv_preferential);
        llNeedScore = getView(R.id.ll_need_score);
        llNeedPay = getView(R.id.ll_need_pay);
        tvNeedScore = getView(R.id.tv_need_score);
        tvNeedPay = getView(R.id.tv_need_pay);
        llProductOperation = getView(R.id.ll_product_operation);

        goodsInfo = getIntent().getParcelableExtra(C.IntentKey.GOODS_INFO);
        isExchange = getIntent().getBooleanExtra(C.IntentKey.SUBMIT_EXCHANGE_TYPE, false);

        topbar.setTttleText("提交订单")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        btnSubmitOrder.setOnClickListener(this);
        String goodsType = AppUtility.getTypeName(goodsInfo.getGoodsType());
        if (!goodsType.equals(C.OrderType.ORDER_TYPE_ZC)) {
            imbtnSub.setOnClickListener(this);
            imbtnAdd.setOnClickListener(this);
        } else {  // 众筹提交订单
            imbtnAdd.setImageResource(R.drawable.order_add_disabled);
        }

        if (goodsInfo == null) return;
        tvType.setText(DbHelper.getInstance().getParentName(goodsType, C.ParentCode.ORDER_INFO_TYPE));
        tvType.setBackgroundResource(getResourcesByType(goodsType));
        tvTitle.setText(goodsInfo.getGoodsName());
        Glide.with(mContext).load(FileUtil.getImageUrl(goodsInfo.getMainPhoto()))
                .error(R.drawable.default_img)
                .placeholder(R.drawable.default_img)
                .into(ivGoodsPic);
        updateUI();
        getDiscount();
        if (isExchange) {  //积分兑换
            tvNeedScore.setText(new DecimalFormat("#").format(goodsInfo.getScoreNumber()));
            tvNeedPay.setText(new StringBuilder().append("¥").append(new DecimalFormat("#.00").format
                    (goodsInfo.getActivityPrice())).toString());
            llNeedPay.setVisibility(View.VISIBLE);
            llNeedScore.setVisibility(View.VISIBLE);
            llProductOperation.setVisibility(View.GONE);
        }
    }

    private int getResourcesByType(String goodsType) {
        int resid;
        switch (goodsType) {
            case C.OrderType.ORDER_TYPE_CP:
                resid = R.drawable.shape_cp_bg;
                break;
            case C.OrderType.ORDER_TYPE_CX:
                resid = R.drawable.shape_cx_bg;
                break;
            case C.OrderType.ORDER_TYPE_MS:
                resid = R.drawable.shape_ms_bg;
                break;
            case C.OrderType.ORDER_TYPE_ZC:
                resid = R.drawable.shape_zc_bg;
                break;
            case C.OrderType.ORDER_TYPE_TG:
                resid = R.drawable.shape_tg_bg;
                break;
            default:
                resid = R.drawable.shape_cp_bg;
                break;
        }
        return resid;
    }


    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 更新界面显示
     */
    private void updateUI() {
        if (goodsInfo == null) return;
        actualPrice = (new BigDecimal(totalCount).multiply(new BigDecimal(goodsInfo
                .getActivityPrice()))).subtract(new BigDecimal(preferentialPrice)).doubleValue();
        totalPrice = new BigDecimal(totalCount).multiply(new BigDecimal(goodsInfo
                .getActivityPrice())).doubleValue();
        tvPrice.setText(AppUtility.getSpannerString(mContext, "x", String.format(Locale.CHINA,"¥%2.2fx%d", goodsInfo.getActivityPrice(), totalCount), true));
        tvTotal.setText(AppUtility.getSpannerString(mContext, "¥",
                String.format(Locale.CHINA,"共%d件商品 合计：¥%2.2f", totalCount, new BigDecimal(goodsInfo.getActivityPrice()).multiply(new BigDecimal(totalCount)).doubleValue()),
                false));
        tvAllPrice.setText(AppUtility.getSpannerString(mContext, "¥",
                String.format(Locale.CHINA,"合计：¥%2.2f", (new BigDecimal(goodsInfo.getActivityPrice()).multiply(new BigDecimal(totalCount))).subtract(new BigDecimal(preferentialPrice))),
                false));
        editNumeber.setText(String.format(Locale.CHINA,"%d", totalCount));
        imbtnSub.setImageResource(totalCount == 1 ? R.drawable.order_minus_disabled : R.drawable.order_minus_normal);
    }


//    /**
//     * 修改订单参数
//     */
//    private void updateData() {
//        // 请求可用优惠劵
//        getDiscount();
//    }

    /**
     * 获取可用优惠劵参数
     */
    private DiscountCouponParams params = new DiscountCouponParams();

    /**
     * 获取当前可用优惠劵
     */
    private void getDiscount() {
        params.setGoodsType(DiscountCouponParams.YHQ_02);
        params.setGoodsDetailStatus(Integer.parseInt(DiscountCouponActivity.UNUSED));
        params.setAmount(new BigDecimal(totalPrice));
        params.setProjectNum(goodsInfo.getProjectNum());
        Call<ResultBase<List<OrderGoodsInfo>>> call = app.getApiService().getClientDiscountCoupon(app
                .getCurrentUserNum(), params);
        submitOrderPresenter.getDiscountCoupon(call);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imbtn_sub) {     // 减少数量
            if (totalCount > 1)
                totalCount--;
            updateUI();
            getDiscount();
        } else if (id == R.id.imbtn_add) {  // 增加数量
            if (totalCount < 999)
                totalCount++;
            updateUI();
            getDiscount();
        } else if (id == R.id.btn_submit_order) { // 提交订单
            submitOrder();
        } else if (id == R.id.ll_select_coupon) {       //  选择优惠劵
            // : 2016/9/21 选择优惠劵
//            Intent intent = new Intent(mContext, SelectDiscountCouponActivity.class);
//            intent.putExtra(C.IntentKey.AMOUNT, totalPrice);
//            intent.putExtra(C.IntentKey.PROJECTNUM, goodsInfo.getProjectNum());
//            intent.putExtra(C.IntentKey.DISCOUNT_COUPON_SELECT, true);
            Intent intent = new Intent(mContext, SelectCouponActivity.class);
            intent.putParcelableArrayListExtra(C.IntentKey.DISCOUNT_COUPON_LIST, (ArrayList<? extends Parcelable>) mCouponList);
            startActivityForResult(intent, REQ_SELECT_DISCOUNT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_SELECT_DISCOUNT) {
            // 返回 优惠劵的集合  hashMap
//            List<OrderGoodsInfo> infoList = data.getParcelableArrayListExtra(C.IntentKey.DISCOUNT_COUPON_LIST);
            HashMap<String, OrderGoodsInfo> parcelables = (HashMap<String, OrderGoodsInfo>) data.getSerializableExtra(C.IntentKey.DISCOUNT_COUPON_LIST);
            orderGoodsCoupon = parcelables.get("000000");
            setCouponInfo();
            if (orderGoodsCoupon != null) {  // 选择的  优惠劵
                tvPreferential.setText(preferentialPrice + "元优惠劵");
                //当前 优惠劵 集合的选中状态
                for (int i = 0; i < mCouponList.size(); i++) {
                    OrderGoodsInfo orderGoodsInfo = mCouponList.get(i);
                    if (orderGoodsInfo.getOrderGoodsDetailNum().equals(orderGoodsCoupon.getOrderGoodsDetailNum())) {
                        orderGoodsInfo.setSelect(true);
                    } else {
                        orderGoodsInfo.setSelect(false);
                    }
                }
            } else {
                tvPreferential.setText(String.format("%d张优惠劵可用", mCouponList.size()));
                for (int i = 0; i < mCouponList.size(); i++) {
                    OrderGoodsInfo orderGoodsInfo = mCouponList.get(i);
                    orderGoodsInfo.setSelect(false);
                }
            }
            updateUI();
        }
    }

    /**
     * 设置优惠信息
     */
    private void setCouponInfo() {
        couponNum = orderGoodsCoupon == null ? "" : orderGoodsCoupon.getOrderGoodsDetailNum();
        preferentialPrice = orderGoodsCoupon == null ? 0 : orderGoodsCoupon.getSalePrice() == null ? 0 : orderGoodsCoupon.getSalePrice().doubleValue();
    }

    /**
     * 提交订单
     */
    private void submitOrder() {
        if (isExchange) {// 积分兑换
            ExchangeParams exchangeParams = new ExchangeParams();
            exchangeParams.setOrderType(C.OrderType.ORDER_TYPE_JF);
            exchangeParams.setOrderRemark("");
            exchangeParams.setCommonNum(goodsInfo.getCommonNum());
            exchangeParams.setStoreNum(app.getStoreInfo().getStoreNum());
            exchangeParams.setTotalCount(1);
            exchangeParams.setSinglePrice(new BigDecimal(goodsInfo.getActivityPrice()));
            exchangeParams.setTotalPrice(new BigDecimal(1).multiply(new BigDecimal(goodsInfo.getActivityPrice())));
            exchangeParams.setPreferentialPrice(new BigDecimal(goodsInfo.getScoreNumber()));
            exchangeParams.setActualPrice(new BigDecimal(1).multiply(new BigDecimal(goodsInfo.getActivityPrice())));
            exchangeParams.setGoodsNum(goodsInfo.getGoodsNum());
            exchangeScorePresenter.exchange(app.getApiService().exchangeWithMoney(app.getCurrentUserNum(), exchangeParams));
        } else {//购买商品
            makeOrderParams.setActualPrice(String.format(Locale.CHINA,"%2.2f", actualPrice));          //实际支付金额
            makeOrderParams.setCouponNum(couponNum);           //优惠劵
            makeOrderParams.setPreferentialPrice(String.format(Locale.CHINA,"%2.2f", preferentialPrice));    //优惠金额
            makeOrderParams.setTotalCount(totalCount);            //数量
            makeOrderParams.setTotalPrice(String.format(Locale.CHINA,"%2.2f", totalPrice));            //总价
            makeOrderParams.setSinglePrice(String.format(Locale.CHINA,"%2.2f", goodsInfo.getActivityPrice()));//单价
            makeOrderParams.setCommonNum(goodsInfo.getCommonNum());
            makeOrderParams.setGoodsNum(goodsInfo.getGoodsNum());
            makeOrderParams.setOrderType(goodsInfo.getGoodsType());
            Call<ResultBase<OrderInfo>> call = app.getApiService().makeOrder(app.getCurrentUserNum(), makeOrderParams);
            submitOrderPresenter.makeOrder(call);
        }
    }


//    @Subscribe(threadMode = ThreadMode.MainThread)
//    public void getSelectDiscount(Event<List<OrderGoodsInfo>> event){
//        if (event != null && event.key.equals(C.EventKey.DISCOUNT_COUPON_LIST)) {
//            if (event.value!= null) {
//                couponNum = event.value.get(0).getOrderGoodsDetailNum();
//                preferentialPrice = event.value.get(0).getSalePrice().doubleValue();
//                tvPreferential.setText(preferentialPrice + "元优惠劵");
//                updateUI();
//            }
//        }
//    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        submitOrderPresenter.detachView();
        exchangeScorePresenter.detachView();
//        unRegisterBus();
    }

    @Override
    public void showLoadingView() {
        showLoading();
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
    public void exchangeSuccess(OrderInfo orderInfo) {
        toPayActivity(orderInfo);
    }

    /**
     * 去支付界面
     *
     * @param orderInfo
     */
    private void toPayActivity(OrderInfo orderInfo) {
        Intent intent = AppUtility.getPayIntent(orderInfo, mContext);
        intent.putExtra(C.IntentKey.GOODS_NAME, goodsInfo.getGoodsName());
        showActivity(intent);
        finish();
    }

    @Override
    public void makeOrderSuccess(OrderInfo orderInfo) {
        toPayActivity(orderInfo);
    }

    @Override
    public void makeOrderError() {

    }

    /**
     * 获取优惠劵成功后
     *
     * @param orderGoodsInfoResultBase
     */
    @Override
    public void showDiscountCouponSuccess(ResultBase<List<OrderGoodsInfo>> orderGoodsInfoResultBase) {
        List<OrderGoodsInfo> couponList = orderGoodsInfoResultBase.getObj();
        if (couponList != null) {
            mCouponList = couponList;
            int size = couponList.size();
            if (size > 0) {
                tvPreferential.setText(size + "张优惠劵可用");
                tvPreferential.setTextColor(ContextCompat.getColor(mContext, R.color.text_default));
                llSelectCoupon.setOnClickListener(this);
            } else {
                llSelectCoupon.setOnClickListener(null);
                tvPreferential.setText("暂无可用优惠劵");
                tvPreferential.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray));
            }
            //  获取优惠劵  当前选择的优惠劵 置为空
            orderGoodsCoupon = null;
            setCouponInfo();
            updateUI();
        } else {
            mCouponList = null;
        }
    }
}
