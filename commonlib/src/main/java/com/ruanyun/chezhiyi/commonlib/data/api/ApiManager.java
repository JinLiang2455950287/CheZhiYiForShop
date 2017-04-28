package com.ruanyun.chezhiyi.commonlib.data.api;


import com.ruanyun.chezhiyi.commonlib.data.api.converter.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by jery on 2016/4/13.
 */
public class ApiManager {

    //    public static final String API_URL = "http://121.199.25.216:5190/czy/";
//    public static final String API_URL = "http://192.168.8.200:8080/czy/";
    public static final String API_URL = "http://192.168.1.114:8080/czy/";//本地
//    public static final String API_URL = "http://106.15.40.78:8080/czy/";//测试
//    public static final String API_URL = " http://106.15.40.78:8081/czy/";//正式


    private static Retrofit retrofit;
    private static final long CONNECT_TIME_OUT = 10 * 1000;
    private static final long READ_TIME_OUT = 20 * 1000;
    private static OkHttpClient okHttpClient;

    public static void build() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            okHttpClient = builder
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY
                    ))
                    .addNetworkInterceptor(new HttpLoggingInterceptor())
                    .build();
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    //.addConverterFactory(FastJsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    // .callFactory(okHttpClient)
                    .build();
        }
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
