package com.ruanyun.chezhiyi.commonlib.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressTaskCallback;
import com.ruanyun.chezhiyi.commonlib.view.AddCarMvpView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/9/22 下午2:39.
 */
public class AddCarPresenter implements Presenter<AddCarMvpView> {
    AddCarMvpView addCarMvpView;
    // HashMap<String, RequestBody> map = new HashMap<String, RequestBody>();
    String currentUserNum = App.getInstance().getCurrentUserNum();
    //AddCarTask addCarTask;

    @Override
    public void attachView(AddCarMvpView mvpView) {
        addCarMvpView = mvpView;
    }

    @Override
    public void detachView() {
        addCarMvpView = null;
    }

    @Override
    public void onCancel() {
        // addCarTask.cancel()
    }

    public void getUserCarInfo() {
        if (addCarMvpView != null)
            addCarMvpView.showLodingView("正在获取车辆信息...");
        App.getInstance().getApiService().getCarinfoList(currentUserNum).enqueue(new ResponseCallback<ResultBase<List<CarInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<CarInfo>> reuslt) {
                if (addCarMvpView != null)
                    addCarMvpView.carListResult(reuslt.getObj(), AddCarMvpView.TYPE_GET);
            }

            @Override
            public void onError(Call call, ResultBase<List<CarInfo>> listResultBase, int errorCode) {
                if (addCarMvpView == null) return;
                addCarMvpView.showToast(listResultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if (addCarMvpView == null) return;
                addCarMvpView.showToast(msg);
            }

            @Override
            public void onResult() {
                if (addCarMvpView != null) {
                    addCarMvpView.disMissLoadingView();
                }
            }
        });
    }

    /**
     * 添加车辆
     *
     * @author zhangsan
     * @date 16/9/23 下午3:41
     */
    public void addOrUpdateCar(List<CarInfo> carInfoList) {
//        if (addCarEmpty(carInfoList)) return;
        if (addCarMvpView != null)
            addCarMvpView.showLodingView("处理中...");
        //CarInfo [] infos=carInfoList.toArray(carInfoList.);
        // new AddCarTask().execute(toArray(carInfoList));

        CarInfo[] uploadCarInfo = buidParams(carInfoList).toArray(new CarInfo[0]);
        if (uploadCarInfo.length > 0) {
            App.getInstance().imageProxyService.getCompressTask("", uploadCarInfo)
                    .start(new CompressTaskCallback<HashMap<String, RequestBody>>() {
                        @Override
                        public void onCompresComplete(HashMap<String, RequestBody> compressResults) {
                            paramMap.putAll(compressResults);
                            startRequet();
                        }

                        @Override
                        public void onCompresFail(Throwable throwable) {

                        }
                    });
        } else {
            startRequet();
        }

    }

    public boolean addCarEmpty(List<CarInfo> carInfoList) {
        for (CarInfo carInfo : carInfoList) {
            if (!carInfo.isAdd()) {
                if (TextUtils.isEmpty(carInfo.getCarAllName())
                        || TextUtils.isEmpty(carInfo.getCarShortName())
                        || TextUtils.isEmpty(carInfo.getPlateNumber())
                        || TextUtils.isEmpty(carInfo.getInsuranceStart())
                        || TextUtils.isEmpty(carInfo.getRegisterDate())
                        || carInfo.getCarMileage() == 0
                        || carInfo.getMaintenanceMileage() == 0
                        || TextUtils.isEmpty(carInfo.getCarModelNum())) {
                    addCarMvpView.showToast("请完善车辆信息!");
                    return true;
                }
                if ( !StringUtil.isCarNumWithoutFirst(carInfo.getPlateNumber())) {// 不符合车牌号
                    addCarMvpView.showToast("请填写正确的车牌号!");
                    return true;
                }
                // break;
            }
        }
        return false;
    }

    public boolean firstAddCarEmpty(List<CarInfo> carInfoList) {
        for (CarInfo carInfo : carInfoList) {
            if (!carInfo.isAdd()) {
                if (
                        /*TextUtils.isEmpty(carInfo.getCarAllName()) || */
                                TextUtils.isEmpty(carInfo.getCarShortName()) ||
                                TextUtils.isEmpty(carInfo.getPlateNumber()) ||
                                TextUtils.isEmpty(carInfo.getInsuranceStart()) ||
                                TextUtils.isEmpty(carInfo.getRegisterDate()) ||
                                carInfo.getCarMileage() == 0 ||
                                carInfo.getMaintenanceMileage() == 0 /*||
                                TextUtils.isEmpty(carInfo.getCarModelNum())*/) {
                    addCarMvpView.showToast("请完善车辆信息!");
                    return true;
                }
                if ( !StringUtil.isCarNumWithoutFirst(carInfo.getPlateNumber())) {// 不符合车牌号
                    addCarMvpView.showToast("请填写正确的车牌号!");
                    return true;
                }
                // break;
            }
        }
        return false;
    }

    private void startRequet() {
        getAddCarCall(paramMap).enqueue(new ResponseCallback() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (addCarMvpView != null) {
                    addCarMvpView.showToast(resultBase.getMsg());
                    addCarMvpView.onPostEditSuccess();
                }
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (addCarMvpView != null) {
                    addCarMvpView.showToast(resultBase.getMsg());
                    addCarMvpView.onPostEditFail();
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                if (addCarMvpView != null) {
                    addCarMvpView.showToast(msg);
                    addCarMvpView.onPostEditFail();
                }
            }

            @Override
            public void onResult() {
                if (addCarMvpView != null)
                    addCarMvpView.disMissLoadingView();
            }
        });
    }

    /**
     * 删除车辆
     *
     * @author zhangsan
     * @date 16/9/23 下午3:41
     */

    public void deleteCar(final CarInfo carInfo) {
        if (addCarMvpView != null)
            addCarMvpView.showLodingView("处理中...");
        App.getInstance().getApiService().deleteCarInfo(App.getInstance().getCurrentUserNum(), carInfo.getCarNum()).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (addCarMvpView != null)
                    addCarMvpView.showToast("删除成功");
                addCarMvpView.onDeleteSuccess(carInfo);
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (addCarMvpView != null)
                    addCarMvpView.disMissLoadingView();
            }
        });
    }

    private Call getAddCarCall(HashMap<String, RequestBody> map) {
        return App.getInstance().getApiService().updateOrSaveCarInfo(currentUserNum, map);
    }

    HashMap<String, RequestBody> paramMap = new HashMap<>();

    private List<CarInfo> buidParams(List<CarInfo> carInfos) {
        paramMap.clear();
        List<CarInfo> params = new ArrayList<>();
        List<CarInfo> uploadImageCarInfo=new ArrayList<>();
        for (CarInfo carInfo : carInfos) {
            if (!carInfo.isAdd()) {
                if (!checkStrEmpty(carInfo.getPicPath())) {
                    File imageFile = new File(carInfo.getPicPath());
                    if (imageFile.exists()) {
                       // paramMap.put(getImageMdStr(carInfo.getCarAllName(), imageFile.getName()), creatImgBody(imageFile));
                       uploadImageCarInfo.add(carInfo);
                    }
                }
                params.add(carInfo);
            }
        }
        String result = new Gson().toJson(params, new TypeToken<List<CarInfo>>() {}.getType());
        LogX.e("ycw", "--->\n" + result);
        paramMap.put("resultJsonArray", creatTextBody(result));
        return uploadImageCarInfo;
    }

    private HashMap buildPamas(CarInfo carInfo) {
        HashMap<String, RequestBody> paramMap = new HashMap<>();
        if (!checkStrEmpty(carInfo.getPicPath())) {
            File imageFile = new File(carInfo.getPicPath());
            if (imageFile.exists()) {
                paramMap.put(getImageMdStr("", imageFile.getName()), creatImgBody(imageFile));
            }
        }
        if (!checkStrEmpty(carInfo.getCarNum())) {
            paramMap.put("carNum", creatTextBody(carInfo.getCarNum()));
        }
        paramMap.put("carModelNum", creatTextBody(carInfo.getCarModelNum()));
        paramMap.put("plateNumber", creatTextBody(carInfo.getPlateNumber()));
        paramMap.put("color", creatTextBody(String.valueOf(carInfo.getColor())));
        paramMap.put("frameNumber", creatTextBody(carInfo.getFrameNumber()));
        paramMap.put("engineNumber", creatTextBody(carInfo.getEngineNumber()));
        paramMap.put("registerDate", creatTextBody(carInfo.getRegisterDate()));
        paramMap.put("carShortName", creatTextBody(carInfo.getCarShortName()));
        paramMap.put("carAllName", creatTextBody(carInfo.getCarAllName()));
        paramMap.put("carMileage", creatTextBody(Integer.toString(carInfo.getCarMileage())));
        return paramMap;
    }

    private CarInfo[] toArray(List<CarInfo> carInfoList) {
        CarInfo[] infos = new CarInfo[carInfoList.size()];
        for (int i = 0; i < carInfoList.size(); i++) {
            infos[i] = carInfoList.get(i);
        }
        return infos;
    }

    private boolean checkStrEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private RequestBody creatTextBody(String value) {
        if (TextUtils.isEmpty(value)) {
            return RequestBody.create(MediaType.parse("text/plain"), "");
        } else {
            return RequestBody.create(MediaType.parse("text/plain"), value);
        }
    }

    private RequestBody creatImgBody(File file) {
        return RequestBody.create(MediaType.parse("image/jpeg"), file);
    }


    private String getImageMdStr(String paramsName, String fileName) {
        StringBuilder sb = new StringBuilder(paramsName)
                .append("\";filename=\"")
                .append(fileName);
        return sb.toString();
    }

    public String getColorByCode(int color) {
        String value = DbHelper.getInstance().getParentName(String.valueOf(color), C.ParentCode.CAR_COLOR);
        return TextUtils.isEmpty(value) ? "" : value;
    }



}
