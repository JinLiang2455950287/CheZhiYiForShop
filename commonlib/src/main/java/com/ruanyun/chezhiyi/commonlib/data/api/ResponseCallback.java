package com.ruanyun.chezhiyi.commonlib.data.api;

import android.text.TextUtils;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description:
 * author: zhangsan on 16/7/26 上午9:50.
 */
public abstract class ResponseCallback<T extends ResultBase> implements Callback<T> {

    public abstract void onSuccess(Call call,T t);

    public abstract void onError(Call call,T t,int errorCode);

    public abstract void onFail(Call call, String msg);

    public abstract void onResult();

    public void  onResponseResult(Response<T> response){

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onResponseResult(response);
        onResult();
        if(response.body()!=null){
         if(response.body().getResult()==1){
             onSuccess(call,response.body());
         }else {
             onError(call,response.body(),response.body().getResult());
         }
        }else {
            onFail(call,"no body error");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onResult();
        if (t != null && !TextUtils.isEmpty(t.getMessage())) {
            String msg = t.getMessage();
            if (t.getMessage().contains("Failed to connect")) {
                if (AppUtility.isNetworkAvailable()) {// 当前手机网络可用
                    msg = "服务器未响应,请稍后重试";
                } else {   // 当前手机网络不可用
                    msg = "网络错误,请检查网络";
                }
            }
            onFail(call, msg);
            LogX.d("retrofit", t.getMessage());
        }
    }
}
