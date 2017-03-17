package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.GoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.ExchangeParams;
import com.ruanyun.chezhiyi.commonlib.presenter.AllWebViewDetailPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.WebMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.MyWebView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.SharePopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.math.BigDecimal;
import java.util.Date;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Description: 商品详情 或 活动报名详情,  销售提成， 施工提成，等类型
 * author: zhangsan on 16/9/10 上午10:08.
 */
public class WebViewActivity extends AutoLayoutActivity implements View.OnClickListener, WebMvpView, Topbar.onTopbarClickListener, MyWebView.onScrollChangedListent {

    public static final String MD = "门店详情";
    public static final String CP = "产品详情";
    public static final String HD = "活动详情";
    public static final String TZ = "通知详情";

    public static final String YYHZ = "营业汇总";
    public static final String HYTJ = "会员统计";
    public static final String XZYH = "新增用户";
    public static final String XSSP = "销售商品";

    public static final String SGTC = "施工提成";
    public static final String XSTC = "销售提成";
    public static final String YHS = "工单数";


    private Topbar topbar;
    private MyWebView webview;
    private String url = "", title = "", jsCode = "javascript:$('.float_layer').hide();";
    private RYEmptyView emptyView;
    //private LinearLayout llReserve;
    private RelativeLayout llRoot;
    private Button btnBuy, btnReserve, btnCancel;
    private ActivityInfo activityInfo = null;
    private boolean showActivityCancel = false;
    private AllWebViewDetailPresenter detailPresenter = new AllWebViewDetailPresenter();
    private String goodsName, mainPhoto, subtitle;
    private SharePopWindow sharePopWindow;
//    private boolean isDuihuan = false;
    private boolean needShare = true;  // topbar 是否需要分享按钮
    private boolean mSendToFriend;// 发送给好友
    /**
     * 销售提成， 施工提成，等类型
     */
    private String countType;
//    private ProductInfo productInfo;// 积分兑换的商品信息
    private boolean loadError = false;
    private boolean showOption = false;
//    private boolean needScrollListener = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_webview);
        //initReserveLayout();
        initView();
        detailPresenter.attachView(this);
        setResult(RESULT_OK);
    }


    private void initReserveLayout() {
        View llReserve = findViewById(R.id.ll_apply);
        llReserve.setVisibility(View.VISIBLE);
        //llReserve = getViewFromLayout(R.layout.layout_activity_apply);
        btnBuy = ButterKnife.findById(llReserve, R.id.btn_buy);
        btnReserve = ButterKnife.findById(llReserve, R.id.btn_reserve);
        btnBuy.setOnClickListener(this);    //马上报名
        btnReserve.setOnClickListener(this);    //在线咨询
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {
        //jsCode = getJsCodes();
        title = getIntent().getStringExtra(C.IntentKey.TITLE);
        url = getIntent().getStringExtra(C.IntentKey.WEB_URL);
        activityInfo = getIntent().getParcelableExtra(C.IntentKey.ACTIVITY_INFO);
        showOption = getIntent().getBooleanExtra(C.IntentKey.ACTIVITY_OPTION, false);
        showActivityCancel = getIntent().getBooleanExtra(C.IntentKey.ACTIVITY_CANCELABLE, false);
//        isDuihuan = getIntent().getBooleanExtra(C.IntentKey.IS_DUIHUAN, false);
        needShare = getIntent().getBooleanExtra(C.IntentKey.NEED_SHARE, true);
        countType = getIntent().getStringExtra(C.IntentKey.WEB_COUNT_TYPE);
        mSendToFriend = getIntent().getBooleanExtra(C.IntentKey.SEND_TO_FRIEND, false);

        goodsName = getIntent().getStringExtra(C.IntentKey.SHARE_CP_NAME);
        mainPhoto = getIntent().getStringExtra(C.IntentKey.SHARE_CP_IMAGE);
        subtitle = getIntent().getStringExtra(C.IntentKey.SHARE_CP_SUBTITLE);

        topbar = getView(R.id.topbar);
        topbar.setBackgroundColor(Color.argb(0, 1, 189, 190));
        webview = getView(R.id.webview);
        emptyView = getView(R.id.emptyview);
        llRoot = getView(R.id.ll_root);
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
//                .enableRightImageBtn()
//                .setRightImgBtnBg(R.drawable.nav_share)
//                .onRightImgBtnClick()
                .setTopbarClickListener(this);
        if (needShare) {
            topbar.enableRightImageBtn()
                    .setRightImgBtnBg(R.drawable.nav_share)
                    .onRightImgBtnClick()
                    .setTopbarClickListener(this);
        } else if (AppUtility.isNotEmpty(countType) && !countType.equals(C.CountType.NUMBER)) { // 不为空
            topbar.enableRightImageBtn()
                    .setRightImgBtnBg(R.drawable.nav_change_toyear)
                    .onRightImgBtnClick()
                    .setTopbarClickListener(this);
        }
//        if (isDuihuan) {
//            productInfo = getIntent().getParcelableExtra(C.IntentKey.PRODUCTINFO_INFO);
//        }

        if (!TextUtils.isEmpty(title))
            topbar.setTttleText(title);

        emptyView.bind(webview);
        emptyView.onReloadClick(this, "reload");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setLoadWithOverviewMode(true);
//        if (isClient())
        webview.addJavascriptInterface(new JsMethodListener(app), "ruanyun");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!loadError) {//当网页加载成功的时候判断是否加载成功
                    webview.setEnabled(true);
                } else { //加载失败的话，初始化页面加载失败的图，然后替换正在加载的视图页面
//                    webview.loadUrl("file:///android_asset/error.html");
                    emptyView.showLoadError();
                    topbar.setRightImgBtnNull().getImgTitleRight().setVisibility(View.INVISIBLE);
//                    loadError=false;
                }
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebViewClient.a a) {
                super.onReceivedError(webView, webResourceRequest, a);
                loadError = true;
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    //progressBar.setVisibility(View.GONE);
                    //progressBar.setProgress(0);
                    // addImageClickListner();
                    emptyView.loadSuccuss();
                    webview.loadUrl(jsCode);
                    if (activityInfo != null && isClient() && !loadError) {//是活动，并且是司机端
                        if (showOption) {
                            if (!showActivityCancel)
                                initReserveLayout();
                            else
                                initCancelButton();
                        }
                    }
                } else {
                    // progressBar.setVisibility(View.VISIBLE);
                    // progressBar.setProgress(newProgress);
                    //addImageClickListner();
                    emptyView.showLoading();
                }
            }

            @Override//覆盖系统默认弹框
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog dlg = new AlertDialog.Builder(mContext).setTitle(null)
                        .setMessage(message).setPositiveButton("确定", new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                result.cancel();
                            }
                        }).create();
                dlg.show();
                dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        //  Auto-generated method stub
                        result.cancel();
                    }
                });
                return true;
            }

            /**
             * 当WebView加载之后，返回 HTML 页面的标题 Title
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
                    loadError = true;
                } else {
                    loadError = false;
                }
            }
        });

        // webview.loadUrl(C.ApiUrl.URL_TEACHER_HOME+"UT70600000010172");
        webview.loadUrl(url);

        LogX.d(TAG, "url:" + webview.getUrl());
        sharePopWindow = new SharePopWindow(mContext, mSendToFriend, new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                sharePopWindow.dismiss();
                String url = webview.getUrl();
                int lastIndexOf = url.lastIndexOf("?");
                if (lastIndexOf != -1) {
                    // AppUtility.copy(url.substring(0, lastIndexOf));
                    Intent intent = new Intent(mContext, Share2HxUsersActivity.class);
                    intent.putExtra(C.IntentKey.GOODS_URL, url.substring(0, lastIndexOf));
                    intent.putExtra(C.IntentKey.GOODS_JSON, getIntent().getStringExtra(C.IntentKey.GOODS_JSON));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, Share2HxUsersActivity.class);
                    intent.putExtra(C.IntentKey.GOODS_URL, url);
                    intent.putExtra(C.IntentKey.GOODS_JSON, getIntent().getStringExtra(C.IntentKey.GOODS_JSON));
                    startActivity(intent);
                }
            }
        });
        // 设置webView  的滚动监听
        webview.setListent(this);

        if (MD.equals(title)) {  // 门店的分享
            StoreInfo storeInfo = App.getInstance().getStoreInfo();
            sharePopWindow.setShareTitle(storeInfo.getSotreName());
            sharePopWindow.setShareImage(FileUtil.getImageUrl(storeInfo.getFigurePic()));
            sharePopWindow.setShareText(storeInfo.getSotreName());
            sharePopWindow.setShareUrl(webview.getUrl());
            sharePopWindow.setShareApp(false);

        } else if (CP.equals(title)) {  //   产品的分享

            sharePopWindow.setShareTitle(goodsName);
            sharePopWindow.setShareImage(FileUtil.getImageUrl(mainPhoto));
            sharePopWindow.setShareText(subtitle);
            sharePopWindow.setShareApp(false);

            int lastIndexOf = url.lastIndexOf("?");
            if (lastIndexOf != -1) {
                sharePopWindow.setShareUrl(url.substring(0, lastIndexOf));
            } else {
                sharePopWindow.setShareUrl(url);
            }
        } else if (TZ.equals(title)
                || YYHZ.equals(title)
                || HYTJ.equals(title)
                || XZYH.equals(title)
                || XSSP.equals(title)
                || SGTC.equals(title)
                || XSTC.equals(title)
                || YHS.equals(title) ) {        //    不包含分享
            // 设置webView  的滚动监听
            webview.setListent(null); // 不设置滚动监听
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) webview.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.topbar);
            webview.setLayoutParams(params);
            topbar.setBackgroundColor(Color.argb(255, 1, 189, 190));
        } else if (activityInfo != null && isClient()) {   // 活动的分享

            sharePopWindow.setShareTitle(activityInfo.getActivityName());
            sharePopWindow.setShareImage(FileUtil.getImageUrl(activityInfo.getActivityPath()));
            sharePopWindow.setShareText(activityInfo.getActivityName());
            sharePopWindow.setShareUrl(webview.getUrl());
            sharePopWindow.setShareApp(false);
        }
    }

    /**
     * 取消活动按钮
     */
    private void initCancelButton() {
        View view = findViewById(R.id.ll_cancel);
        view.setVisibility(View.VISIBLE);
        btnCancel = ButterKnife.findById(view, R.id.btn_activity_cancel);
        btnCancel.setOnClickListener(this);    //取消活动的按钮
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_buy) { //活动报名
            int registerable = activityInfo.getLimitNum() - activityInfo.getRegisterNumber();
            if (activityInfo.getIsLimitNum() == 1 && registerable <= 0) {
                AppUtility.showToastMsg("报名人数已满");
                return;
            }
//            detailPresenter.getActiveAddInfo(app.getApiService().getActivityInfo(app.getCurrentUserNum(), activityListInfo.getActivityNum()));
            gotoApplyActive();
        } else if (v.getId() == R.id.btn_reserve) {  //在线咨询
            if (app.getStoreInfo() != null && app.getStoreInfo().getUserNumSecretary() != null)
                showActivity(AppUtility.getChatIntent(mContext, app.getStoreInfo().getUserNumSecretary()));
        } else if (v.getId() == R.id.btn_activity_cancel) {  //取消报名
            createDialog();
        }
    }


    /**
     * 创建一个取消活动提示  dialog
     */
    private void createDialog() {
        new android.support.v7.app.AlertDialog.Builder(mContext).setMessage("确定取消活动吗?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        detailPresenter.delActivity(app.getApiService().delActivity(app.getCurrentUserNum(), activityInfo.getActivityNum()));
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    /**
     * 跳转到活动报名
     */
    private void gotoApplyActive() {
        Intent intent = new Intent(mContext, ApplyOnlineActivity.class);
        intent.putExtra(C.IntentKey.ACTIVITY_INFO, activityInfo);
        showActivity(intent);
    }

    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (onKeyClick(keyCode)) return true;
        return super.onKeyDown(keyCode, event);
    }

    private boolean onKeyClick(int keyCode) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack() && !loadError) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
            if (!webview.canGoBack()) {
                topbar.onRightImgBtnClick().setRightImgBtnBg(R.drawable.nav_change_toyear).setTopbarClickListener(this);
            }
            return true;
        }
        setResult(RESULT_OK);
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        emptyView.unbind();
        detailPresenter.detachView();
        sharePopWindow.setCopyClickListener(null);
        webview.removeAllViews();
        llRoot.removeAllViews();
        webview.destroy();
        AppUtility.fixInputMethodManagerLeak(this);
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
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void getActivitySuccess(ResultBase<ActivityInfo> resultBase) {
        if (resultBase.getObj() != null && AppUtility.isNotEmpty(resultBase.getObj().getActivityNum())) {
            //获取到活动信息
            AppUtility.showToastMsg(resultBase.getMsg());
        } else {
            //没获取到活动信息
            gotoApplyActive();
        }
    }

    /**
     * 纯积分兑换成功
     *
     * @param orderInfo
     */
    @Override
    public void exchangeSuccess(OrderInfo orderInfo) {
        app.beanCacheHelper.getAccountMoney();//刷新账户信息
        finish();
    }

    @Override
    public void delActivitySuccess() {
        // 取消活动成功后
        EventBus.getDefault().post(new Event<String>(C.EventKey.DEL_ACTIVITY, ""));
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_right) {
            if (!needShare && AppUtility.isNotEmpty(countType) && !countType.equals(C.CountType.NUMBER)) {
                if (countType.equals(C.CountType.EXECUTION)) {
                    toCountListUrl();
                } else if (countType.equals(C.CountType.MARKET)) {
                    toCountListUrl();
                }
                topbar.setRightImgBtnBg(0).setRightImgBtnNull();
            } else {
                sharePopWindow.showBottom(v);
            }
        } else if (id == R.id.img_btn_left) {
            if (!onKeyClick(KeyEvent.KEYCODE_BACK)) {
                finish();
            }
        }
    }

    /**
     * 1.36.3	销售提成/施工提成月份列表
     */
    private void toCountListUrl() {
        String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_COUNT_LIST), app
                .getCurrentUserNum(), StringUtil.getTimeStr("yyyy", new Date()), countType);
        webview.loadUrl(url);
    }

    @Override
    public void onScrollChang(int l, int t, int oldl, int oldt) {
        LogX.e(TAG, "onScrollChang: WebViewActivity   [l, t, oldl, oldt]" +"[" + l +","+ t+","+ oldl+","+ oldt+"]" );
        if (t <= 0) {
            t = 0;
        } else if (t>=255) {
            t = 255;
        }
        topbar.setBackgroundColor(Color.argb(t, 1, 189, 190));
    }


    class JsMethodListener {

        public JsMethodListener(Context context) {
        }

        @JavascriptInterface
        public void payOrder(String jsonArrayString) {
            String str = AppUtility.deCodePayString(jsonArrayString);
            if (!AppUtility.isNotEmpty(str)) {
                AppUtility.showToastMsg("数据异常！");
                return;
            }
            LogX.e(TAG, "payOrder: JsMethodListener \n" + str);
            GoodsInfo goodsInfo = AppUtility.getGoodsInfo(str);
            if (C.OrderType.ORDER_TYPE_JF.equals(goodsInfo.getOrderType())) { //兑换商品页面
                if (goodsInfo.getScoreNumber() < app.getAccountMoneyInfo().getScoreBalance()) {
                    if (goodsInfo.getActivityPrice() == 0) {  //  纯积分兑换
                        createDialog(goodsInfo);
                    } else {
                        Intent intent = AppUtility.getSubmitIntent(mContext, goodsInfo);
                        intent.putExtra(C.IntentKey.SUBMIT_EXCHANGE_TYPE, true);
                        showActivity(intent);
                    }
                } else {
                    AppUtility.showToastMsg("积分不足");
                }

            } else {  //其他详情页
                AppUtility.toSubmit(mContext, goodsInfo);
            }
        }

        @JavascriptInterface
        public void payOrder() {
            //  签到页面的分享
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sharePopWindow.setShareUrl(String.format(FileUtil.getFileUrl(C.ApiUrl.URL_DOWNLOAD), app.getCurrentUserNum()));
                    sharePopWindow.showBottom(webview);
                    LogX.d(TAG, "-----------------签到  分享 --------------------");
                }
            });
        }

        /**
         * 用户生日提醒
         *
         * @param userNum
         */
        @JavascriptInterface
        public void openUser(String userNum) {
            showActivity(AppUtility.getChatIntent(mContext, userNum));
            finish();
        }
    }

    /**
     * 纯积分兑换
     *
     * @param goodsInfo
     */
    private void createDialog(final GoodsInfo goodsInfo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("确定要积分兑换此商品？")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 纯积分兑换
                        ExchangeParams exchangeParams = new ExchangeParams();
                        exchangeParams.setOrderType(C.OrderType.ORDER_TYPE_JF);
                        exchangeParams.setOrderRemark("");
                        exchangeParams.setCommonNum(goodsInfo.getCommonNum());
                        exchangeParams.setStoreNum(app.getStoreInfo().getStoreNum());
                        exchangeParams.setTotalCount(1);
                        exchangeParams.setSinglePrice(new BigDecimal(1));
                        exchangeParams.setTotalPrice(new BigDecimal(1));
                        exchangeParams.setPreferentialPrice(new BigDecimal(goodsInfo.getScoreNumber()));
                        exchangeParams.setActualPrice(new BigDecimal(0));
                        exchangeParams.setGoodsNum(goodsInfo.getGoodsNum());
                        detailPresenter.exchangeOnlyScore(app.getApiService().exchangeOnlyScore
                                (app.getCurrentUserNum(),
                                        exchangeParams));
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
}
