package com.ruanyun.chezhiyi.commonlib.presenter;

import android.support.annotation.NonNull;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.CustomerRepUiModel;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.NotSpendingServiceParams;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.CustomerRepMvpView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/10/8 上午8:46.
 */
public class CustomerRepPresenter implements Presenter<CustomerRepMvpView> {
    private String currentUserNum = App.getInstance().getCurrentUserNum();
    private CustomerRepMvpView customerRepMvpView;
    private Call<ResultBase> resultBaseCall;

    // private HashMap<Object,List> selectItemsCache=new HashMap<>();

    @Override
    public void attachView(CustomerRepMvpView mvpView) {
        this.customerRepMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.customerRepMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 获取车主已购服务参数实体类
     **/
    NotSpendingServiceParams notSpendingServiceParams = new NotSpendingServiceParams();

    /**
     * 获取车主已购服务项
     *
     * @author zhangsan
     * @date 16/10/10 上午9:34
     */
    public void getBaughtGoods(String carOwnerNum, final String projectNum, final int postion) {
        if (customerRepMvpView != null)
            customerRepMvpView.showLoadingView("正在获取商品信息...");
        notSpendingServiceParams.setCustomerUserNum(carOwnerNum);
        notSpendingServiceParams.setProjectNum(projectNum);
        App.getInstance().getApiService().getNotSpendingService(currentUserNum, notSpendingServiceParams).enqueue(new ResponseCallback<ResultBase<List<OrderGoodsInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<OrderGoodsInfo>> listResultBase) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onNotSpendingServiceResult(projectNum, getUiModelFromGoods(listResultBase.getObj()), postion);
            }

            @Override
            public void onError(Call call, ResultBase<List<OrderGoodsInfo>> listResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (customerRepMvpView != null)
                    customerRepMvpView.disMissLoadingView();
            }
        });
    }

    /**
     * 获取空闲的工位
     *
     * @author zhangsan
     * @date 16/10/9 下午5:21
     */
    public void getFreeWorkStation(final String projectNum, final int postion) {
        if (customerRepMvpView != null)
            customerRepMvpView.showLoadingView("正在空闲工位...");
        App.getInstance().getApiService().getWorkOrderGongWei(currentUserNum, projectNum).enqueue(new ResponseCallback<ResultBase<List<WorkBayInfo>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<WorkBayInfo>> listResultBase) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onFreeWorkbayResult(projectNum, listResultBase.getObj(), postion);
            }

            @Override
            public void onError(Call call, ResultBase<List<WorkBayInfo>> listResultBase, int errorCode) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onFreeWorkbayResult(projectNum, new ArrayList<WorkBayInfo>(), postion);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onFreeWorkbayResult(projectNum, new ArrayList<WorkBayInfo>(), postion);
            }

            @Override
            public void onResult() {
                if (customerRepMvpView != null)
                    customerRepMvpView.disMissLoadingView();
            }
        });
    }


    /**
     * 获取空闲技师
     *
     * @author zhangsan
     * @date 16/10/9 下午5:12
     */
    public void getFreeTechnian(final String projectNum, final int postion) {
        if (customerRepMvpView != null)
            customerRepMvpView.showLoadingView("正在空闲技师...");
        App.getInstance().getApiService().getLeisureTechnician(currentUserNum, projectNum).enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> result) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onFreeTechnicanResult(projectNum, result.getObj(), postion);
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> result, int errorCode) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onFreeTechnicanResult(projectNum, new ArrayList<User>(), postion);
            }

            @Override
            public void onFail(Call call, String msg) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onFreeTechnicanResult(projectNum, new ArrayList<User>(), postion);
            }

            @Override
            public void onResult() {
                if (customerRepMvpView != null)
                    customerRepMvpView.disMissLoadingView();
            }
        });
    }

    /**
     * 获取扫描车牌工单详情信息
     *
     * @author zhangsan
     * @date 16/10/9 下午5:22
     */
    public void getScanCustomerInfo(String carPlateNum) {
        if (customerRepMvpView != null)
            customerRepMvpView.showLoadingView("处理中...");
        App.getInstance().getApiService().scanLicenseGetBookingInfo(currentUserNum, carPlateNum).enqueue(new ResponseCallback<ResultBase<CarBookingInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<CarBookingInfo> result) {
                if (customerRepMvpView != null) {
                    LogX.e("服务标签info", result.getObj().toString());
                    customerRepMvpView.onScanCarBookingSuccess(result.getObj());
                }
            }

            @Override
            public void onError(Call call, ResultBase<CarBookingInfo> carBookingInfoResultBase, int errorCode) {
                if (customerRepMvpView != null) {
                    customerRepMvpView.onScanCarBookingFail();
                    customerRepMvpView.showToast(carBookingInfoResultBase.getMsg());
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                if (customerRepMvpView != null)
                    customerRepMvpView.onScanCarBookingFail();
            }

            @Override
            public void onResult() {
                if (customerRepMvpView != null) {
                    customerRepMvpView.disMissLoadingView();
                    customerRepMvpView.onScanCarBookingResult();
                }
            }
        });
    }

    /**
     * 保存车辆里程数
     *
     * @param resultBaseCall
     */
    public void saveCarMileage(Call<ResultBase> resultBaseCall) {
        if (customerRepMvpView != null)
            customerRepMvpView.showLoadingView("保存中...");
        this.resultBaseCall = resultBaseCall;
        this.resultBaseCall.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase result) {
                if (customerRepMvpView != null) {
                    customerRepMvpView.saveCarMileageSuccess();
//                    customerRepMvpView.showToast(result.getMsg());
                }
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (customerRepMvpView != null) {
//                    customerRepMvpView.onScanCarBookingFail();
                    customerRepMvpView.showToast(resultBase.getMsg());
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                if (customerRepMvpView != null) {
//                    customerRepMvpView.onScanCarBookingFail();
                }
            }

            @Override
            public void onResult() {
                if (customerRepMvpView != null) {
                    customerRepMvpView.disMissLoadingView();
                }
            }
        });
    }

    /**
     * 提交服务工单
     *
     * @param workOrderJson
     */
    public void submitWorkOrder(String workOrderJson) {
        if (customerRepMvpView != null)
            customerRepMvpView.showLoadingView("处理中...");
        App.getInstance().getApiService().saveReceptionWorkorder(currentUserNum, workOrderJson).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if (customerRepMvpView != null) {
                    customerRepMvpView.showToast(resultBase.getMsg());
                    customerRepMvpView.submitWorkOrderSuccess();
                }
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if (customerRepMvpView != null) {
                    customerRepMvpView.showToast(resultBase.getMsg());
                }
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if (customerRepMvpView != null)
                    customerRepMvpView.disMissLoadingView();
            }
        });
    }

    /**
     * 提交服务工单
     */
    public void submitWorkOrder2(HashMap<String, RequestBody> caseMapParams) {
        if (customerRepMvpView != null)
            App.getInstance().getApiService().saveReceptionWorkorder2(currentUserNum, caseMapParams).enqueue(new ResponseCallback<ResultBase>() {
                @Override
                public void onSuccess(Call call, ResultBase resultBase) {
                    if (customerRepMvpView != null) {
                        customerRepMvpView.showToast(resultBase.getMsg());
                        customerRepMvpView.submitWorkOrderSuccess();
                    }
                }

                @Override
                public void onError(Call call, ResultBase resultBase, int errorCode) {
                    if (customerRepMvpView != null) {
                        customerRepMvpView.showToast(resultBase.getMsg());
                    }
                }

                @Override
                public void onFail(Call call, String msg) {

                }

                @Override
                public void onResult() {
                    if (customerRepMvpView != null)
                        customerRepMvpView.disMissLoadingView();
                }
            });
    }

    /**
     * 从新选的服务商品获取一级服务项item
     *
     * @author zhangsan
     * @date 16/10/17 上午9:23
     */
    public CustomerRepUiModel getUIModelFromProduct(@NonNull ProductInfo productInfo, int tab) {
        CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
        customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_PROJECT_TYPE);
        customerRepUiModel.setItemNum(productInfo.getProjectParent());
        customerRepUiModel.parentNum = productInfo.getProjectParent();
        customerRepUiModel.setItemName(getProjectNameByNum(productInfo.getProjectParent()));
        customerRepUiModel.setSelected(true);
        customerRepUiModel.setSelecTab(tab);
        customerRepUiModel.isNewItem = true;
        return customerRepUiModel;
    }

    private String getProjectNameByNum(String projectParentNum) {
        return DbHelper.getInstance().getServiceTypeName(projectParentNum);
    }


    /**
     * @author zhangsan根据工单服务json 获取列表一级数据
     * @date 16/10/10 下午3:10
     */
    public List<CustomerRepUiModel> getUiModelFromWorkOrder(List<WorkOrderInfo> workOrderInfos, int defaultTab) {
        // List<ProjectType> projectTypes = new Gson().fromJson(jsonString, new TypeToken<List<ProjectType>>() {
        //}.getSelectPosition());
        List<CustomerRepUiModel> customerRepUiModels = new ArrayList<CustomerRepUiModel>();
        for (int i = 0, size = workOrderInfos.size(); i < size; i++) {
            CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
            customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_PROJECT_TYPE);
            customerRepUiModel.setItemName(workOrderInfos.get(i).getProjectName());
            customerRepUiModel.setItemNum(workOrderInfos.get(i).getProjectNum());
            customerRepUiModel.relativeBean = workOrderInfos.get(i);
            customerRepUiModel.setSelecTab(defaultTab);
            customerRepUiModels.add(customerRepUiModel);
        }
        return customerRepUiModels;
    }

    /**
     * 从技师实体类获取uimodel
     *
     * @author zhangsan
     * @date 16/10/11 下午2:11
     */
    public List<CustomerRepUiModel> getUiModelFromTechnianUser(List<User> technicians, String projectNum) {
        List<CustomerRepUiModel> customerRepUiModels = new ArrayList<CustomerRepUiModel>();
        for (int i = 0, size = technicians.size(); i < size; i++) {
            CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
            customerRepUiModel.setItemNum(projectNum);
            customerRepUiModel.setItemName(technicians.get(i).getNickName());
            customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_TECHNICIAN);
            customerRepUiModel.relativeBean = technicians.get(i);
            customerRepUiModels.add(customerRepUiModel);

        }
        return customerRepUiModels;
    }

    /**
     * 从工位信息获取UImodel
     *
     * @author zhangsan
     * @date 16/10/11 下午2:11
     */

    public List<CustomerRepUiModel> getUiModelFromWorkBay(List<WorkBayInfo> workBayInfos, String projectNum) {
        List<CustomerRepUiModel> customerRepUiModels = new ArrayList<CustomerRepUiModel>();
        for (int i = 0, size = workBayInfos.size(); i < size; i++) {
            CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
            customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_WORK_STATION);
            customerRepUiModel.setItemName(workBayInfos.get(i).getWorkbayName());
            customerRepUiModel.setItemNum(projectNum);
            customerRepUiModel.relativeBean = workBayInfos.get(i);
            customerRepUiModels.add(customerRepUiModel);
        }
        return customerRepUiModels;
    }

    /**
     * 从已购商品获取UI实体类
     *
     * @author zhangsan
     * @date 16/10/11 下午2:09
     */
    public List<CustomerRepUiModel> getUiModelFromGoods(List<OrderGoodsInfo> goods) {
        List<CustomerRepUiModel> customerRepUiModels = new ArrayList<CustomerRepUiModel>();
        for (int i = 0, size = goods.size(); i < size; i++) {
            CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
            customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_GOODS);
            customerRepUiModel.setItemName(goods.get(i).getGoodsName());
            customerRepUiModel.setItemNum(goods.get(i).getGoodsNum());
            customerRepUiModel.parentNum = goods.get(i).getProjectParent();
            customerRepUiModel.relativeBean = goods.get(i);
            customerRepUiModels.add(customerRepUiModel);
        }
        return customerRepUiModels;
    }

    public List<CustomerRepUiModel> getUiModelFromProducts(List<ProductInfo> productInfos) {
        List<CustomerRepUiModel> customerRepUiModels = new ArrayList<CustomerRepUiModel>();
        for (int i = 0, size = productInfos.size(); i < size; i++) {
            CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
            customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_GOODS);
            customerRepUiModel.setItemName(productInfos.get(i).getGoodsName());
            customerRepUiModel.setItemNum(productInfos.get(i).getGoodsNum());
            customerRepUiModel.parentNum = productInfos.get(i).getProjectParent();
            customerRepUiModel.setCount(productInfos.get(i).getGoodsCount());
            customerRepUiModel.setSelected(true);//新添加商品默认为选中
            customerRepUiModel.isNewItem = true;
            customerRepUiModel.relativeBean = productInfos.get(i);
            customerRepUiModels.add(customerRepUiModel);
        }
        return customerRepUiModels;
    }
}
