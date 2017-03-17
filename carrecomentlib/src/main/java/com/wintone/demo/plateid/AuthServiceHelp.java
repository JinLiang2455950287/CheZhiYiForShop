package com.wintone.demo.plateid;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;

import com.wintone.plateid.AuthService;
import com.wintone.plateid.PlateAuthParameter;

/**
 * Description ：摄像头扫码   授权验证  工具类
 * <br/>
 * Created by ycw on 2016/12/14.
 */

public abstract class AuthServiceHelp {

    private Context mContext;
    private FragmentActivity activity;
    public String SN;
    public AuthService.MyBinder authBinder;
    private int ReturnAuthority = -1;

    public AuthServiceHelp(Context mContext) {
        this.mContext = mContext;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * 开始授权验证
     */
    public void start() {
        Intent authIntent = new Intent(mContext, AuthService.class);
        mContext.bindService(authIntent, authConn, Service.BIND_AUTO_CREATE);
    }

    /**
     * 开始授权验证
     */
    public void start(String SN) {
        this.SN = SN;
        Intent authIntent = new Intent(mContext, AuthService.class);
        mContext.bindService(authIntent, authConn, Service.BIND_AUTO_CREATE);
    }

    //授权验证服务绑定后的操作与start识别服务
    public ServiceConnection authConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            authBinder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            authBinder = (AuthService.MyBinder) service;
//            Toast.makeText(getApplicationContext(), R.string.auth_check_service_bind_success, Toast.LENGTH_SHORT).show();
            try {
//                TelephonyManager tm=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
//                LogX.i("ycw", "------------->  "+tm.getDeviceId());
                PlateAuthParameter pap = new PlateAuthParameter();
                pap.sn = SN;
                pap.authFile = "";
                pap.devCode = Devcode.DEVCODE/*tm.getDeviceId()*/;
                ReturnAuthority = authBinder.getAuth(pap);
//                String sn = "UR265PUNJVVYPW1YYT32YYH3R";
//                String authFile = "";
//                ReturnAuthority = authBinder.getAuth(sn, authFile);
                if (ReturnAuthority != 0) { // 授权验证失败
                    authServiceFailure(mContext.getString(R.string.license_verification_failed) + ":" + ReturnAuthority);
//                    Toast.makeText(mContext.getApplicationContext(), mContext.getString(R.string.license_verification_failed) + ":" + ReturnAuthority, Toast.LENGTH_LONG).show();
                } else {  // 授权验证成功
                    authServiceSuccess();
//                    Toast.makeText(mContext.getApplicationContext(), R.string.license_verification_success, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                authServiceFailure(mContext.getString(R.string.failed_check_failure));
//                Toast.makeText(mContext.getApplicationContext(), R.string.failed_check_failure, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                if (authBinder != null) {
                    mContext.unbindService(authConn);
                }
            }
        }
    };

    public abstract void authServiceSuccess();

    public abstract void authServiceFailure(String msg);

    public void clear() {
        setContext(null);
        setActivity(null);
    }
}
