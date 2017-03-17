package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PayBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.AliPayConfigInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WeiXinOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.AccountParams;
import com.ruanyun.chezhiyi.commonlib.presenter.PayPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.Base64;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.MD5;
import com.ruanyun.chezhiyi.commonlib.util.RSAUtils;
import com.ruanyun.chezhiyi.commonlib.view.PayMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.paypassworldview.InputPasswordPopWindow;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * author ycw
 * <p>
 * </br> ClassName: PayOnlineActivity
 * Description:  在线支付
 * date 2016/5/6 15:56
 */
public class PayOnlineActivity extends PayBaseActivity implements View.OnClickListener, PayMvpView
        //, ResponseCallback.OnResponseCallbackListener<ResultBase>,RYDialog.DialogClickListener
{

    //public static final String sReaOrderInfo = "ReaOrderInfo";
    public static final String PAGE_TYPE_RECHARGE = "recharge";
    private static final String STR_BALANCE = "可用余额%s元";
    public static final int REQUEST_CODE = 129;
    Topbar topBar;
//    TextView tvOrderNumStr;
//    TextView tvOrderNum;
//    LinearLayout llOrderNum;
    TextView tvPaySumStr;
    TextView tvPaySum;
    LinearLayout llPaySum;
    ImageView imageBalance;
    TextView tvBalanceStr;
    TextView tvBalance;
    ImageView imageAlipay;
    TextView tvAlipayStr;
    ImageView imageWechat;
    TextView tvWechatStr;
    Button btnToPay;
    RelativeLayout rlBalance;
    RelativeLayout rlAlipay;
    RelativeLayout rlWechat;
    List<ImageView> imageViewList;
//    EditText etTradPwd;//交易密码
    private int mPayType;//支付方式
    private String pageType = "";
    private String activityNum = "";
    private OrderInfo mOrderInfo;   //订单信息
    private double myBalance;       //账户余额


    private PayPresenter payPresenter = new PayPresenter();
    /**
     * 账户支付的  参数
     */
    private AccountParams accountParams = new AccountParams();
    /**
     * 当前订单的支付类型   默认是 购物 3
     */
    private int recordType = AccountParams.PAY_TYPE_SHOP;

    /**  商品名称   支付宝支付参数   */
    private String goodsName = "";

    AliPayConfigInfo aliPayConfigInfo = null;
    private boolean showRecharge = false;//跳转到充值界面
    private InputPasswordPopWindow passwordPopWindow;//账户支付的输入密码框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
        payPresenter.attachView(this);
        setContentView(R.layout.activity_pay_online);
        init();
    }

    private void init() {
        initPayHandler();
        goodsName = getIntent().getStringExtra(C.IntentKey.GOODS_NAME);
        mOrderInfo = getIntent().getParcelableExtra(C.IntentKey.ORDER_INFO);   // 当前 待支付的 订单的信息
        recordType = getIntent().getIntExtra(C.IntentKey.RECORD_TYPE, AccountParams.PAY_TYPE_SHOP);
        pageType = getIntent().getStringExtra(C.IntentKey.PAY_PAGE_TYPE);
        activityNum = getIntent().getStringExtra(C.IntentKey.ACTIVITY_NUM);  // 活动报名时  传的活动编号
        if (app.getAccountMoneyInfo() != null)
            myBalance = app.getAccountMoneyInfo().getAccountBalance();

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, C.PayType.APP_ID, false);
        api.registerApp(C.PayType.APP_ID);

        if (null == mOrderInfo) {
            return;
        }
        initView();
        //获取账户余额
        app.beanCacheHelper.getAccountMoney();
        //获取支付宝支付信息
        getAlipayConfig();

    }

    private void initView() {
//        tvOrderNum = getView(R.id.tv_order_num);
//        llOrderNum = getView(R.id.ll_order_num);
//        tvOrderNumStr = getView(R.id.tv_order_num_str);
        topBar = getView(R.id.topbar);
        tvPaySumStr = getView(R.id.tv_pay_sum_str);
        tvPaySum = getView(R.id.tv_pay_sum);
        llPaySum = getView(R.id.ll_pay_sum);
        imageBalance = getView(R.id.image_balance);
        tvBalanceStr = getView(R.id.tv_balance_str);
        tvBalance = getView(R.id.tv_balance);
        imageAlipay = getView(R.id.image_alipay);
        tvAlipayStr = getView(R.id.tv_alipay_str);
        imageWechat = getView(R.id.image_wechat);
        tvWechatStr = getView(R.id.tv_wechat_str);
        btnToPay = getView(R.id.btn_to_pay);
        rlBalance = getView(R.id.rl_balance);
        rlAlipay = getView(R.id.rl_alipay);
        rlWechat = getView(R.id.rl_wechat);

        ImageView ivBalance = getView(R.id.image_select_status_balance);
        ImageView ivAlipay = getView(R.id.image_select_status_alipay);
        ImageView ivWechat = getView(R.id.image_select_status_wechat);

        imageViewList = new ArrayList<>();
        imageViewList.add(ivBalance);
        imageViewList.add(ivAlipay);
        imageViewList.add(ivWechat);

        rlAlipay.setOnClickListener(this);
        rlBalance.setOnClickListener(this);
        rlWechat.setOnClickListener(this);

        topBar.setTttleText("在线支付")
                .setBackBtnEnable(true)
                .onBackBtnClick(this, "exit");
        tvBalance.setText(myBalance == 0 ? "无可用余额" : String.format(STR_BALANCE, new DecimalFormat("#0.0").format(myBalance)));
        btnToPay.setOnClickListener(this);
        if (!TextUtils.isEmpty(pageType) && PAGE_TYPE_RECHARGE.equals(pageType)) {
//            llOrderNum.setVisibility(View.GONE);
            rlBalance.setVisibility(View.GONE);
            setSelectPay(C.PayType.PAYTYPE_ALIPAY);
        } else {
            setSelectPay(C.PayType.PAYTYPE_BALANCE);//默认余额支付
        }
        tvPaySum.setText(new StringBuilder().append("¥").append(mOrderInfo.getActualPrice()
                .setScale(1, BigDecimal.ROUND_HALF_UP).toString()).toString());

        passwordPopWindow = new InputPasswordPopWindow(mContext, "输入支付密码") {
            @Override
            public void passwordInputAccomplish(String password) {
                accountPay(MD5.md5(password));
            }
        };
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_balance) {
            setSelectPay(C.PayType.PAYTYPE_BALANCE);
        } else if (i == R.id.rl_alipay) {
            setSelectPay(C.PayType.PAYTYPE_ALIPAY);
        } else if (i == R.id.rl_wechat) {
            setSelectPay(C.PayType.PAYTYPE_WECHAT);
        } else if (i == R.id.btn_to_pay) {
            payinfo(v);
        }
    }


    /**
     * 显示设置支付密码弹窗
     */
    private void showSetPayPwdDlg() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("您没有设置支付密码, 请设置支付密码")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showActivity("com.ruanyun.czy.client.view.ui.account.SetTradPwdActivity");
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }


    /**
     * 获取微信支付信息
     */
    private void getWxPayInfo() {
        Call<ResultBase<WeiXinOrderInfo>> call = app.getApiService().weixinDoOrder(mOrderInfo.getOrderNum());
        payPresenter.weiXinPay(call);
    }

    /**
     * 去支付
     *
     * @param v
     */
    private void payinfo(View v) {
        switch (mPayType) {
            case C.PayType.PAYTYPE_BALANCE:    //余额支付
                if (showRecharge) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setMessage("当前账户余额不足，请充值")
                            .setPositiveButton("去充值", new DialogInterface
                                    .OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    showActivity("com.ruanyun.czy.client.view.ui.my.MembershipCardActivity");
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface
                            .OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                } else if (AppUtility.isNotEmpty(app.getAccountMoneyInfo().getPayPassword())) {  // 显示支付密码输入框
//                    Intent intent = new Intent(mContext, InputTradePwdDialog.class);
//                    startActivityForResult(intent, REQUEST_CODE);
                    passwordPopWindow.showBottom(v);
                } else {
//                    显示支付密码弹框
                    showSetPayPwdDlg();
                }
                break;

            case C.PayType.PAYTYPE_ALIPAY:    //支付宝支付
                if (aliPayConfigInfo == null) {
                    showAlipayConfigError();
                } else {
                    RSA_PRIVATE = aliPayConfigInfo.getPrivateKeyAndroid();
                    APPID = aliPayConfigInfo.getPartner();
                    payV2(v, mOrderInfo.getOrderNum());
                }
                break;

            case C.PayType.PAYTYPE_WECHAT: // 微信支付
//                if (weiXinPayInfo != null) {
//                    wechatPay(weiXinPayInfo.getPrepay_id(), weiXinPayInfo.getNonce_str(),
//                            weiXinPayInfo.getTimestamp(), weiXinPayInfo.getSign());
//                } else {
                getWxPayInfo();
//                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MainThread, priority = 124)
    public void getAccountMoneySuccess(Event<AccountMoneyInfo> event) {
        if (event != null && event.key.equals(C.EventKey.ACCOUNT_MONEY)) {
            AccountMoneyInfo moneyInfo = event.value;
            app.setAccountMoneyInfo(moneyInfo);
            myBalance = moneyInfo.getAccountBalance();
            tvBalance.setText(myBalance == 0 ? "无可用余额" : String.format(STR_BALANCE, new DecimalFormat("#0.0").format(myBalance)));
            if (new BigDecimal(myBalance).subtract(mOrderInfo.getActualPrice()).doubleValue() < 0) {
                showRecharge = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                String payPassword = data.getStringExtra(C.IntentKey.PAY_PASSWORD);
                accountPay(MD5.md5(payPassword));
            }
        }
    }

    /**
     * 获取支付宝    支付配置表参数TPayInfo
     */
    private void getAlipayConfig() {
        Call<ResultBase<String>> call = app.getApiService().alipayConfig(app.getCurrentUserNum());
        payPresenter.alipayConfig(call);
    }

    /**
     * 选择支付方式
     *
     * @param payType
     */
    private void setSelectPay(int payType) {
        for (int i = 0; i < imageViewList.size(); i++) {
            imageViewList.get(i).setImageResource(R.drawable.icon_green_select);
        }
        imageViewList.get(payType).setImageResource(R.drawable.icon_green_selected);
        mPayType = payType;
    }

    /**
     * 账号余额支付
     *
     * @param payPassword
     */
    private void accountPay(String payPassword) {
        accountParams.setAccountBalance(mOrderInfo.getActualPrice().toString());
        accountParams.setOrderNum(mOrderInfo.getOrderNum());
        accountParams.setRecordType(recordType);
        accountParams.setPayPassword(payPassword);
        payPresenter.accountPay(app.getApiService().accountPay(app.getCurrentUserNum(), accountParams));
    }

    /**
     * 支付宝支付  回调接口 S
     */
    @Override
    public void onPaySuccess() {
        showTip("支付成功");
        app.beanCacheHelper.getAccountMoney();
        onlyPaySuccess();
    }

    @Override
    public void onPayFail() {

    }

    /**
     * 支付宝支付  回调接口 E
     */


    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    /**
     * 获取微信支付的参数
     *
     * @param weiXinOrderInfo
     */
    @Override
    public void weiXinPaySuccess(WeiXinOrderInfo weiXinOrderInfo) {
        wechatPay(weiXinOrderInfo.getPrepay_id(), weiXinOrderInfo.getNonce_str(), weiXinOrderInfo
                .getTimestamp(), weiXinOrderInfo.getSign());
    }

    @Override
    public void alipayConfigSuccess(String s) {
        String temp = RSAUtils.deCodeKey(s);
        try {
            temp = new String(Base64.decode(temp), "UTF-8");
            Gson gson = new Gson();
            aliPayConfigInfo = gson.fromJson(temp, AliPayConfigInfo.class);
            LogX.d(TAG, "========PrivateKeyAndroid======》\n" + aliPayConfigInfo.getPrivateKeyAndroid());
            LogX.d(TAG, "========PrivateKeyIos=========>\n" + aliPayConfigInfo.getPrivateKeyIos());
        } catch (UnsupportedEncodingException e) {
            showAlipayConfigError();
            e.printStackTrace();
        }
    }

    /**
     * 支付宝配置信息获取失败
     */
    @Override
    public void showAlipayConfigError() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("支付宝配置信息获取失败,请使用其他方式支付！")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**
     * 账户支付成功
     */
    @Override
    public void accountPaySuccess() {
        onlyPaySuccess();
    }

    /**
     * 获取订单详情
     *
     * @param orderInfo
     */
    @Override
    public void orderDetailResult(OrderInfo orderInfo) {
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.OrderDetailedActivity");
        intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST, ""));//
        showActivity(intent);
        finish();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }


    /**
     * 微信支付成功
     */
    @Subscribe(threadMode = ThreadMode.MainThread, priority = 122)
    public void closeView(String event) {
        if (event.equals(C.EventKey.CLOSE_PAY_ACTIVITY)) {
            onlyPaySuccess();
        }

    }

    /**
     * 三种支付方式   支付成功之后
     */
    private void onlyPaySuccess() {
        app.beanCacheHelper.getAccountMoney();// 刷新账户余额
        if (mOrderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_DJ) || mOrderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_GD) || PAGE_TYPE_RECHARGE.equals(pageType)) {          //   如果支付成功的是预约定金   或者  工单   或者是充值
            if (mOrderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_GD)) {
                // 工单的情况下 支付成功后 刷新工单列表的
                EventBus.getDefault().post(new Event<>(C.EventKey.REFRESH_WORKORDER_LIST, C.EventKey.REFRESH_WORKORDER_LIST));
                // 刷新 工单的 列表界面  显示  已结束的  工单列表
                EventBus.getDefault().post(new Event<String>(C.EventKey.WORK_ORDER_LIST_FINISH, C.EventKey.WORK_ORDER_LIST_FINISH));
            } else if (mOrderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_DJ)) {
                // 预约定金支付成功 后
                EventBus.getDefault().post(new Event<>(C.EventKey.TO_BOOKINR_WATIE, C.EventKey.TO_BOOKINR_WATIE));
            }
            setResult(RESULT_OK);
            finish();
        } else if (mOrderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_HD)) {
            //  支付的订单是活动报名 跳转到活动详细页
            toActivityDetailed();
        } else {  //商品的订单详情页
            payPresenter.getOrderDetail(mOrderInfo.getOrderNum());
        }
    }

    /**
     * 跳转到活动详情
     */
    private void toActivityDetailed() {
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.ActivityDetailedActivity");
        intent.putExtra(C.IntentKey.ACTIVITY_NUM, activityNum);
        showActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }
}
