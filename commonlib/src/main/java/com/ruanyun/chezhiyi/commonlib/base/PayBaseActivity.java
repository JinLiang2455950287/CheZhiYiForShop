package com.ruanyun.chezhiyi.commonlib.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.alipay.OrderInfoUtil2_0;
import com.ruanyun.chezhiyi.commonlib.util.alipay.PayResult;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Description:  支付宝  微信支付基类
 * author: jery on 2016/5/11 9:35.
 */
public abstract class PayBaseActivity extends AutoLayoutActivity {

    /** 支付宝支付业务：入参app_id */
    public String APPID = "";

    /** 商户私钥，pkcs8格式 */
    public String RSA_PRIVATE = "";

    /**   支付宝公钥   */
    public static final String RSA_PUBLIC = "";

    protected static final int SDK_PAY_FLAG = 1;
    protected PayResultHandler mHandler=null;

    // IWXAPI 是第三方app和微信通信的openapi接口
    protected IWXAPI api;

    protected void  initPayHandler(){
       mHandler=new PayResultHandler(this);
    }

    public abstract void onPaySuccess();
    public abstract void onPayFail();

    /**
     *    微信支付
     */
    protected void wechatPay(String Prepay_id, String Nonce_str, String Timestamp, String Sign) {
        PayReq req = new PayReq();
        //        应用ID
        req.appId = C.PayType.APP_ID;
        //        商户号
        req.partnerId = C.PayType.PARTNER_ID;
        //        预支付交易会话ID
        req.prepayId = Prepay_id;
        //        随机字符串
        req.nonceStr = Nonce_str;
        //        时间戳
        req.timeStamp = Timestamp;
        //        扩展字段
        req.packageValue = "Sign=WXPay";
        //        签名
       // req.sign = "c9f75d4e0f1b0795259bcc7bc3e5bc0e";
        req.sign = Sign;
        req.extData = "app data"; // optional

        //Toast.makeText(PayOnlineActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    /**
     * 新的 支付宝支付
     */
   static class PayResultHandler extends Handler {
        WeakReference<PayBaseActivity> mActivityWeakReference;

        PayResultHandler(PayBaseActivity activity) {
            mActivityWeakReference = new WeakReference<PayBaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final PayBaseActivity activity = mActivityWeakReference.get();
            if (activity != null) {

                    switch (msg.what) {
                        case SDK_PAY_FLAG: {
                            @SuppressWarnings("unchecked")
                            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                            /**
                             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                             */
                            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                            String resultStatus = payResult.getResultStatus();

                            // 判断resultStatus 为9000则代表支付成功
                            if (TextUtils.equals(resultStatus, "9000")) {
                                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                                Toast.makeText(PayBaseActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                                AppUtility.showToastMsg(payResult.getMemo());
                                activity.onPaySuccess();
                            } else {
                                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                                Toast.makeText(PayBaseActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                AppUtility.showToastMsg(payResult.getMemo());
                                activity.onPayFail();
                            }
                            break;
                        }
                        default:
                            break;
                    }

            }
        }
    }

    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v, String orderNum) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, orderNum);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayBaseActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
