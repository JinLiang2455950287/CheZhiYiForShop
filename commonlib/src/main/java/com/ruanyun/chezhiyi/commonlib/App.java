package com.ruanyun.chezhiyi.commonlib;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.base.BaseActivity;
import com.ruanyun.chezhiyi.commonlib.data.api.ApiManager;
import com.ruanyun.chezhiyi.commonlib.data.api.ApiService;
import com.ruanyun.chezhiyi.commonlib.data.api.HxApiService;
import com.ruanyun.chezhiyi.commonlib.data.db.DaoMaster;
import com.ruanyun.chezhiyi.commonlib.data.db.DaoSession;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.BeanCacheHelper;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LoadingDialogHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.PrefUtility;
import com.ruanyun.chezhiyi.commonlib.util.UserInfoSaver;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressImageProxy;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressImageProxyService;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressImageTask;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.DefaultCompressResultFactory;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.List;
import java.util.Vector;

import cn.sharesdk.framework.ShareSDK;

/**
 * Description:
 * author: zhangsan on 16/7/19 下午2:26.
 */
public class App extends MultiDexApplication {
    private List<BaseActivity> activityStacks = new Vector<BaseActivity>();
    private HxApiService hxApiService;
    protected static App app;
    private ApiService apiService;
    static DaoMaster daoMaster;//GreenDao数据库
    static DaoSession daoSession;//GreenDao数据库
    public LoadingDialogHelper loadingDialogHelper;
    private User user;
    private AccountMoneyInfo accountMoneyInfo;
    public String storeNum = "st30390000000011";
    private StoreInfo storeInfo;
    public BeanCacheHelper beanCacheHelper;
    private String currentUserNum;
    public CompressImageProxy compressImageProxy;
    public CompressImageProxyService imageProxyService;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        beanCacheHelper = new BeanCacheHelper();
        app = this;
        ShareSDK.initSDK(app);
        AppUtility.setContext(app);
        AutoLayoutConifg.getInstance().useDeviceSize();
        ApiManager.build();//初始化retrofit对象
        hxApiService = ApiManager.getRetrofit().create(HxApiService.class);
        apiService = ApiManager.getRetrofit().create(ApiService.class);//获取retrofit对象
        DbHelper.getInstance(app);//初始化数据库
        beanCacheHelper.checkVersion();//4.1.2	获取需要缓存的版本号
        HXHelper.getInstance().init(app);
        loadingDialogHelper = new LoadingDialogHelper();
        initCompressProxy();//图片压缩
        initWebView();
        //        门店 "900059190"
//        司机端 "94af79b2a8"
//        if (isClient()) {
//            CrashReport.initCrashReport(getApplicationContext(), "94af79b2a8", false);
//        } else {
        CrashReport.initCrashReport(getApplicationContext(), "900059190", false);
//        }
    }

    private void initWebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //        QbSdk.setTbsListener(new TbsListener() {
        //            @Override
        //            public void onDownloadFinish(int i) {
        ////                Log.d("app","onDownloadFinish");
        //            }
        //
        //            @Override
        //            public void onInstallFinish(int i) {
        ////                Log.d("app","onInstallFinish");
        //            }
        //
        //            @Override
        //            public void onDownloadProgress(int i) {
        ////                Log.d("app","onDownloadProgress:"+i);
        //            }
        //        });
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initCompressProxy() {
        compressImageProxy = new CompressImageProxy();
        compressImageProxy.setResultConverter(new DefaultCompressResultFactory().creat()).setCompressGear(CompressImageTask.THIRD_GEAR);
        imageProxyService = compressImageProxy.getProxyService(CompressImageProxyService.class);
    }

    public String getCurrentUserNum() {
        user = getUser();
        if (user != null && !TextUtils.isEmpty(user.getUserNum())) {
            return user.getUserNum();
        }
        return "";
    }

    public void setCurrentUserNum(String currentUserNum) {
        this.currentUserNum = currentUserNum;
    }

    public static App getInstance() {
        return app;
    }


    public HxApiService getHxApiService() {
        return hxApiService;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public User getUser() {
        if (user == null) {
            return UserInfoSaver.getUserInfo();
        }
        return user;
    }

    public void setUser(User user) {
        if (user == null || TextUtils.isEmpty(user.getUserNum())) return;
        this.user = user;
        UserInfoSaver.saveUserInfo(user);
        HXHelper.getInstance().getUserProfileManager().setUserInfo(FileUtil.getFileUrl(user.getUserPhoto()), user.getNickName());
    }

    public StoreInfo getStoreInfo() {
        String json = PrefUtility.get(C.PrefName.PREF_STORE_INFO, "");
        if (storeInfo == null && AppUtility.isNotEmpty(json)) {
            storeInfo = new Gson().fromJson(json, StoreInfo.class);
        }
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
        PrefUtility.put(C.PrefName.PREF_STORE_INFO, new Gson().toJson(storeInfo));
    }

    public AccountMoneyInfo getAccountMoneyInfo() {
        return accountMoneyInfo;
    }

    public void setAccountMoneyInfo(AccountMoneyInfo accountMoneyInfo) {
        this.accountMoneyInfo = accountMoneyInfo;
    }

    public void pushActivity(BaseActivity activity) {
        if (!activityStacks.contains(activity))
            activityStacks.add(activity);
    }

    public void popActivity(BaseActivity activity) {
        activityStacks.remove(activity);
    }

    public void exitApp() {
        for (BaseActivity activity : activityStacks) {
            activity.finish();
        }
        activityStacks.clear();
    }

    /**
     * true -  客户端
     * false - 商家端
     *
     * @return boolean
     */
    public boolean isClient() {
        LogX.i(getPackageName(), "packageName ---->  " + getPackageName());
        return getPackageName().contains("client");
    }


    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    "info_db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
