package com.ruanyun.chezhiyi.commonlib.presenter;

import android.support.annotation.NonNull;

import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.view.MakeAppointmentMvpView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Description ：
 * <p>
 * Created by ycw on 2016/9/13.
 */
public class MakeAppointmentPresenter implements Presenter<MakeAppointmentMvpView> {

    MakeAppointmentMvpView makeAppointmentMvpView;
    Call<ResultBase<AppointmentInfo>> call;

    @Override
    public void attachView(MakeAppointmentMvpView mvpView) {
        this.makeAppointmentMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.makeAppointmentMvpView = null;
    }

    @Override
    public void onCancel() {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }


    /**
     * 1.14.2	添加预约信息
     * @param call
     */
    public void makeAppointment(Call<ResultBase<AppointmentInfo>> call){

        if (makeAppointmentMvpView != null) {
            makeAppointmentMvpView.showMakeAppointmentLoadingView("预约中...");
        }
        this.call = call;
        this.call.enqueue(new ResponseCallback<ResultBase<AppointmentInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<AppointmentInfo> appointmentInfoResultBase) {
                if (makeAppointmentMvpView != null) {
                    makeAppointmentMvpView.showTip(appointmentInfoResultBase.getMsg());
                    makeAppointmentMvpView.makeAppointmentOnsuccess(appointmentInfoResultBase.getObj());
                }
            }

            @Override
            public void onError(Call call, ResultBase<AppointmentInfo> appointmentInfoResultBase, int errorCode) {
                if (makeAppointmentMvpView != null) {
                    makeAppointmentMvpView.showTip(appointmentInfoResultBase.getMsg());
                }
            }

            @Override
            public void onFail(Call call, String msg) {
                if (makeAppointmentMvpView != null) {
                    makeAppointmentMvpView.showTip(msg);
                }
            }

            @Override
            public void onResult() {
                if (makeAppointmentMvpView != null) {
                    makeAppointmentMvpView.makeAppointmentOnResult();
                }
            }
        });

    }

    /**
     * 获取一级的数据
     * @param list
     * @return
     */
    public List<ProjectType> getFirstType(List<ProjectType> list) {
        List<ProjectType> firstList = new ArrayList<>();
        for (ProjectType project : list) {
            project.setParent(true);
            firstList.add(project);
        }
        return firstList;
    }


    /**
     * 获取二级的数据
     * @param listAll
     * @return
     */
    public List<ProjectType> getTypeByProjectNum(String projectNum, List<ProjectType> listAll) {
        List<ProjectType> secondList = new ArrayList<>();
        for (ProjectType project : listAll) {
            if (project.getParentNum().equals(projectNum)) {
                secondList.add(project);
            }
        }
        return secondList;
    }



    @NonNull
    public List<ProjectType> getProjectNumInfos(List<ProjectType> firstList, Map<String, List<ProjectType>> cacheProject) {
        List<ProjectType> projectTypeList = new ArrayList<>();
        for (ProjectType projectType : firstList) {
            List<ProjectType> listParent = cacheProject.get(projectType.getProjectNum());
            List<ProjectType> childProjectTypeList = getChildProjectType(listParent);
            if (childProjectTypeList != null && childProjectTypeList.size()>0) {
                projectType.setChildProjectTypeList(childProjectTypeList);
                projectTypeList.add(projectType);
            }
        }
        return projectTypeList;
    }


    /**
     * 获取传参数据
     * @param listParent
     * @return
     */
    private List<ProjectType> getChildProjectType(List<ProjectType> listParent) {
        List<ProjectType> projectTypeList = new ArrayList<>();
        if (listParent != null) {
            for (ProjectType projectType : listParent) {
                if (projectType.isSelected()) {
                    projectType.setChildProjectTypeList(new ArrayList<ProjectType>());
                    projectTypeList.add(projectType);
                }
            }
        }
        return projectTypeList;
    }



}
