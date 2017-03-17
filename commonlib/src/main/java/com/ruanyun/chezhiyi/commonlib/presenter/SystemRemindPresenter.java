package com.ruanyun.chezhiyi.commonlib.presenter;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.SystemRemindMvpView;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2016/11/11.
 */
public class SystemRemindPresenter implements Presenter<SystemRemindMvpView> {

    SystemRemindMvpView systemRemindMvpView;


    @Override
    public void attachView(SystemRemindMvpView mvpView) {
        this.systemRemindMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.systemRemindMvpView = null;
    }

    @Override
    public void onCancel() {

    }

    /**
     * 获取订单信息
     *@author zhangsan
     *@date   16/10/20 下午8:01
     */
    public void getOrderDetail(String commonNum){
        if(systemRemindMvpView!=null)
            systemRemindMvpView.showLoadingView("正在获取详情信息...");
        App.getInstance().getApiService().getOrderDetail(App.getInstance().getCurrentUserNum(),commonNum).enqueue(new ResponseCallback<ResultBase<OrderInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<OrderInfo> orderInfoResultBase) {
                if(systemRemindMvpView!=null){
                    //systemRemindMvpView.showTips(orderInfoResultBase.getMsg());
                    systemRemindMvpView.orderDetailResult(orderInfoResultBase.getObj());
                }
            }

            @Override
            public void onError(Call call, ResultBase<OrderInfo> orderInfoResultBase, int errorCode) {
                if(systemRemindMvpView!=null && errorCode == -1){
                    systemRemindMvpView.showTips(orderInfoResultBase.getMsg());
                }
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if(systemRemindMvpView!=null)
                    systemRemindMvpView.dismissLoadingView();
            }
        });
    }



    /**
     * 1.41	设为已读消息
     *@author zhangsan
     *@date   16/10/20 下午8:01
     */
    public void updateRemind(Call<ResultBase> updateRemind) {
//        if(systemRemindMvpView!=null)
//            systemRemindMvpView.showLoadingView("正在获取详情信息...");
        updateRemind.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase orderInfoResultBase) {
                if (systemRemindMvpView != null) {
                    systemRemindMvpView.updateRemindSuccess();
                }
            }

            @Override
            public void onError(Call call, ResultBase orderInfoResultBase, int errorCode) {
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
//                if (systemRemindMvpView != null)
//                    systemRemindMvpView.dismissLoadingView();
            }
        });
    }


    /**
     * 获取已读消息
     * @param infoList
     */
    public void getIsReadFromDb(List<RemindInfo> infoList) {
        List<RemindInfo> remindInfoList = DbHelper.getInstance().getAllRemindInfos();
        for (int i1 = 0; i1 < remindInfoList.size(); i1++) {
            RemindInfo remindInfoDb = remindInfoList.get(i1);
            for (int i = 0; i < infoList.size(); i++) {
                RemindInfo info = infoList.get(i);
                if (remindInfoDb.getRemindInfoNum().equals(info.getRemindInfoNum())) {
                    info.setIsRead(remindInfoDb.getIsRead());
                }
            }
        }
    }
}
