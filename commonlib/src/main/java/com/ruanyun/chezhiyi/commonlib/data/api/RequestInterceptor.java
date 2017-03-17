package com.ruanyun.chezhiyi.commonlib.data.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description:接口请求拦截器
 * author: zhangsan on 16/8/4 下午3:13.
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        request.newBuilder().addHeader("","").build();

        return chain.proceed(request);
    }
}
