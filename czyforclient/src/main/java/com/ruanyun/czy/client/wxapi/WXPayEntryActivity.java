package com.ruanyun.czy.client.wxapi;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.czy.client.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, C.PayType.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@SuppressLint("LongLogTag")
	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

//		if (resp.getSelectPosition() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			//builder.setTitle(R.string.app_tip);
//			//builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}

		String msg = "";
		int errCode = resp.errCode;
		if (errCode == 0) {
			//0 成功 展示成功页面
			//Intent intent = new Intent(Constant.ACTION_WECHAT_PAY);
			//intent.putExtra("errCode", resp.errCode);
			//sendBroadcast(intent);
			//finish();
			msg = "支付成功";
		}
		else if (errCode == -1) {
			//-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
			//Intent intent = new Intent(Constant.ACTION_WECHAT_PAY);
			//intent.putExtra("errCode", resp.errCode);
			//sendBroadcast(intent);
			//finish();
			msg = "支付失败";
		}
		else if (errCode == -2) {
			//-2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
			//Intent intent = new Intent(Constant.ACTION_WECHAT_PAY);
			//intent.putExtra("errCode", resp.errCode);
			//sendBroadcast(intent);
			//finish();
			msg = "支付取消";
		}

//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//		alertDialogBuilder.setTitle("微信支付").setMessage(msg)
//				.setPositiveButton(R.string.RESOURCE3, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				}).setNegativeButton(R.string.RESOURCE4, new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//			}
//		}).show();

		if (errCode == 0) {
			App.getInstance().beanCacheHelper.getAccountMoney();
			EventBus.getDefault().post(C.EventKey.CLOSE_PAY_ACTIVITY);
		}
		finish();
	}
}