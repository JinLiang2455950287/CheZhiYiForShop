package com.ruanyun.chezhiyi.commonlib.util;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit2.Call;


/**
 * Description:实体类缓存工具类
 * author: zhangsan on 16/8/2 上午9:18.
 */
public class BeanCacheHelper {
    public static final String TAG = "BeanCacheHelper";
    public Map<String, ParentCodeInfo> parentMaps = new HashMap<String, ParentCodeInfo>();//版本号parentcode缓存

    public void checkVersion() {
        App.getInstance().getApiService().getParentCodeVersion().enqueue(new ResponseCallback<ResultBase<List<ParentCodeInfo>>>() {
            @Override
            public void onSuccess(Call call, final ResultBase<List<ParentCodeInfo>> parentCodeInfoResult) {
                Log.e("======4.1.2获取需要缓存的版本号", parentCodeInfoResult.getObj().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for (ParentCodeInfo info : parentCodeInfoResult.getObj()) {
                            parentMaps.put(info.getItemName(), info);
                        }
                        for (ParentCodeInfo info : parentCodeInfoResult.getObj()) {
                            if (compareVersion(info, C.ParentCode.CARMODEL)) {
                                getCarModes();     //获取车辆类型
                            } else if (compareVersion(info, C.ParentCode.DICTIONARY)) {
                                getParentCode();   //获取parentCode
                            }
                        }
                        //  if (DbHelper.getInstance().)) {
                        //getProjectTypes();
                        // }
                        //if (TextUtils.isEmpty(loginName))
//                        getHomeIconToDb();

                    }
                }).start();

            }

            @Override
            public void onError(Call call, ResultBase<List<ParentCodeInfo>> parentCodeInfoResult, int erroCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 获取parentCode
     */
    public void getParentCode() {
        App.getInstance().getApiService().getParentCodeInfo().enqueue(new ResponseCallback<ResultBase<List<ParentCodeInfo>>>() {
            @Override
            public void onSuccess(Call call, final ResultBase<List<ParentCodeInfo>> parentCodeInfoResult) {
                LogX.e("=====1.8.2获取公共字典表", parentCodeInfoResult.getObj().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper.getInstance().insertParentCodes(parentCodeInfoResult.getObj());
                        ParentCodeInfo parentCodeInfo = parentMaps.get(C.ParentCode.DICTIONARY);
                        if (parentCodeInfo != null)
                            DbHelper.getInstance().insertParentCode(parentCodeInfo);

                    }
                }).start();
            }

            @Override
            public void onError(Call call, ResultBase<List<ParentCodeInfo>> parentCodeInfoResult, int erroCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });

    }

    /**
     * 获取车辆类型
     */
    private void getCarModes() {
        App.getInstance().getApiService().getCarModels().enqueue(new ResponseCallback<ResultBase<List<CarModel>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<CarModel>> carModelsResult) {
                //save(carModelsResult, C.PrefName.PREF_CARMODEL);
                LogX.e("=====1.8.6 获取车型信息", carModelsResult.getObj().toString());
                DbHelper.getInstance().saveCarModels(carModelsResult.getObj());
                DbHelper.getInstance().insertParentCode(parentMaps.get(C.ParentCode.CARMODEL));
            }

            @Override
            public void onError(Call call, ResultBase<List<CarModel>> carModelsResult, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 获取服务项目
     */
    public void getProjectTypes() {
        App.getInstance().getApiService().getProjectType().enqueue(new ResponseCallback<ResultBase<List<ProjectType>>>() {
            @Override
            public void onSuccess(Call call, final ResultBase<List<ProjectType>> projectTypeResult) {
                LogX.e("=====1.8.7获取工单服务或技师技能1", projectTypeResult.getObj().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<ProjectType> projectTypeList = projectTypeResult.getObj();
                        Collections.sort(projectTypeList, new Comparator<ProjectType>() {
                            @Override
                            public int compare(ProjectType lhs, ProjectType rhs) {
                                return lhs.getSortNum() < rhs.getSortNum() ? -1 : lhs.getSortNum
                                        () == rhs.getSortNum() ? 0 : 1;
                            }
                        });
                        LogX.e("=====1.8.7获取工单服务或技师技能2", projectTypeList.toString());
                        DbHelper.getInstance().inserServiceTypes(projectTypeList);
                        //save(projectTypeResult, C.PrefName.PREF_PROJECTTYPE);
                    }
                }).start();
            }

            @Override
            public void onError(Call call, ResultBase<List<ProjectType>> projectTypeResult, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 获取  当前  用户信息
     *
     * @param userNum
     * @param activity
     * @param eventKey
     */
    public void getUserByNum(final String userNum, FragmentActivity activity, final String eventKey) {
        App.getInstance().loadingDialogHelper.showIgnoreStatu(activity, "处理中...");
        Call<ResultBase<User>> call = App.getInstance().getHxApiService().getUserByNum(App.getInstance().getCurrentUserNum(), userNum);
        call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResult) {
                EventBus.getDefault().post(new Event<User>(eventKey, userResult.getObj()));
                App.getInstance().setUser(userResult.getObj());
                EventBus.getDefault().post(eventKey);
            }

            @Override
            public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                AppUtility.showToastMsg(userResult.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                App.getInstance().loadingDialogHelper.dissMiss();
                //EventBus.getDefault().postSticky(C.EventKey.GET_HXUSER_RESPONSE);
            }
        });
    }

    /**
     * 根据用户编号获取  含有朋友的关系 的用户信息
     *
     * @param userNum  用户编号
     * @param activity
     * @param eventKey
     */
    public void getFriendShipInfo(final String userNum, FragmentActivity activity, final String eventKey) {
        App.getInstance().loadingDialogHelper.showIgnoreStatu(activity, "处理中...");
        Call<ResultBase<User>> call = App.getInstance().getHxApiService().getFriendShipInfo(App.getInstance().getCurrentUserNum(), userNum);
        call.enqueue(new ResponseCallback<ResultBase<User>>() {
            @Override
            public void onSuccess(Call call, ResultBase<User> userResult) {
                EventBus.getDefault().postSticky(new Event<User>(eventKey, userResult.getObj()));
            }

            @Override
            public void onError(Call call, ResultBase<User> userResult, int errorCode) {
                AppUtility.showToastMsg(userResult.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                App.getInstance().loadingDialogHelper.dissMiss();
                //EventBus.getDefault().postSticky(C.EventKey.GET_HXUSER_RESPONSE);
            }
        });
    }

    /**
     * 1.10.9	获取所有图片信息
     */
    public void getHomeIconToDb() {
        Call<ResultBase<List<HomeIconInfo>>> call = App.getInstance().getApiService().getHomeIcon();
        call.enqueue(new ResponseCallback<ResultBase<List<HomeIconInfo>>>() {
            @Override
            public void onSuccess(Call call, final ResultBase<List<HomeIconInfo>> listResultBase) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<HomeIconInfo> list = listResultBase.getObj();
                        Collections.sort(list, new Comparator<HomeIconInfo>() {
                            @Override
                            public int compare(HomeIconInfo lhs, HomeIconInfo rhs) {
                                return lhs.getSortNum() < rhs.getSortNum() ? -1 : lhs.getSortNum
                                        () == rhs.getSortNum() ? 0 : 1;
                            }
                        });
                        LogX.e("homeIcon前", list.toString());
                        DbHelper.getInstance().insertHomeIcons(list);
                        LogX.e("homeIcon后", list.toString());
                    }
                }).start();
            }

            @Override
            public void onError(Call call, ResultBase<List<HomeIconInfo>> listResultBase, int
                    errorCode) {
                AppUtility.showToastMsg(listResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                AppUtility.showToastMsg("图标失败");
            }

            @Override
            public void onResult() {

            }
        });
    }

    /**
     * 获取用户账号信息
     */
    public void getAccountMoney() {
        Call<ResultBase<AccountMoneyInfo>> call = App.getInstance().getApiService()
                .getUserAccountInfo(App.getInstance().getCurrentUserNum());
        call.enqueue(new ResponseCallback<ResultBase<AccountMoneyInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<AccountMoneyInfo>
                    accountMoneyInfoResultBase) {
                App.getInstance().setAccountMoneyInfo(accountMoneyInfoResultBase.getObj());
                EventBus.getDefault().post(new Event<AccountMoneyInfo>(C.EventKey.ACCOUNT_MONEY, accountMoneyInfoResultBase.getObj()));

            }

            @Override
            public void onError(Call call, ResultBase<AccountMoneyInfo>
                    accountMoneyInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                EventBus.getDefault().post(new Event<>(C.EventKey.ACCOUNT_MONEY, C.EventKey.ACCOUNT_MONEY));
            }
        });
    }

    /**
     * 比较本地缓存版本与服务端版本 true需要更新 false不需要更新
     *
     * @author zhangsan
     * @date 16/8/8 上午10:03
     */
    private boolean compareVersion(ParentCodeInfo info, String parentCodeName) {
        String version = DbHelper.getInstance().getBeanVersion(parentCodeName);
        boolean t1 = info.getItemName().equals(parentCodeName);
        boolean t2 = !version.equals(info.getItemCode());
        if (t1 && t2) {
            return true;
        } else {
            return false;
        }
        // return true;
    }

    /**
     * 获取缓存车型信息
     *
     * @author zhangsan
     * @date 16/8/6 下午3:57
     */
    public List<CarModel> getLocalCarModels() {
        // CarModelsResult result=get(C.PrefName.PREF_CARMODEL,CarModelsResult.class);
      /*  Type type = new TypeReference<ResultBase<List<CarModel>>>() {
        }.getSelectPosition();
        ResultBase<List<CarModel>> result = get(C.PrefName.PREF_CARMODEL, type);
        return result == null ? new ArrayList<CarModel>() : result.getObj();*/
        return DbHelper.getInstance().getAllCarModel();
    }

    /**
     * 获取车辆颜色
     *
     * @author zhangsan
     * @date 16/9/24 下午5:41
     */
    public List<ParentCodeInfo> getCarColorList() {
        return DbHelper.getInstance().getParentCodeList(C.ParentCode.CAR_COLOR);
    }

//    /**
//     * 获取缓存工单服务或技师技能
//     *
//     * @author zhangsan
//     * @date 16/8/6 下午3:58
//     */
//    public List<ProjectType> getLocalProjectTypes() {
//        // ProjectTypeResult result=get(C.PrefName.PREF_PROJECTTYPE,ProjectTypeResult.class);
//        Type type = new TypeReference<ResultBase<List<ProjectType>>>() {
//        }.getSelectPosition();
//        ResultBase<List<ProjectType>> result = get(C.PrefName.PREF_PROJECTTYPE, type);
//        return result == null ? new ArrayList<ProjectType>() : result.getObj();
//    }

    /**
     * 根据parentcode Name 获取 parentcode
     *
     * @author zhangsan
     * @date 16/8/6 下午4:31
     */
    public List<ParentCodeInfo> getLocalParentCodes(String parentCodeName) {
        return DbHelper.getInstance().getParentCodeList(parentCodeName);
    }

    public String getArticleTypeName(int articleTyple) {
        return DbHelper.getInstance().getParentName(Integer.toString(articleTyple), C.ParentCode.ARTICLE_TYPE);
    }

    /**
     * 保存缓存的实体类
     *
     * @author zhangsan
     * @date 16/8/6 下午2:49
     */
    private <T> void save(T data, String prefName) {
        try {
            String content = new Gson().toJson(data);
            PrefUtility.put(prefName, content);
        } catch (Exception e) {
            e.printStackTrace();
            LogX.e(TAG, e.getMessage());
        }
    }

    /**
     * 获取缓存的实体类
     *
     * @author zhangsan
     * @date 16/8/6 下午2:49
     */
    private <T> T get(String prefClssName, Class clazz) {
        try {
            String data = PrefUtility.get(prefClssName, "");
            return (T) new Gson().fromJson(data, clazz);

        } catch (Exception e) {
            e.printStackTrace();
            LogX.e(TAG, e.getMessage());
        }
        return null;
    }

//    private <T> T get(String prefClssName, Type type) {
//        try {
//            String data = PrefUtility.get(prefClssName, "");
//            return (T) JSON.parseObject(data, type);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogX.e(TAG, e.getMessage());
//        }
//        return null;
//    }
}
