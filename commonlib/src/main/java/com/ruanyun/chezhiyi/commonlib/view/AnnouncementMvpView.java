package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.GongGaoInfo;

import java.util.List;

/**
 * Description ：首页的公告,退款申请数量，今日预约数量
 * <p/>
 * Created by jin on 2017/3/13.
 */
public interface AnnouncementMvpView extends MvpView {

    void getGongGaoInfoSuccess(List<GongGaoInfo> gongGaoInfo);//获取公告

    void getGongGaoInfoError();

    void getGongGaoInfoResult();

    void getAppointMentCountSuccess(String String);//获取预约数量

    void getRePayApplyCountSuccess(String String);//获取退款申请数量

    void getWaitCountSuccess(String String);//获取提醒数量
}
