package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.ReportInfo;

/**
 * Description ：
 * <p/>
 * Created by ycw on 2016/10/10.
 */
public interface NoticeMvpView extends MvpView {

//    void setNoticeInfo(NoticeInfo noticeInfo);

    void getReportInfoSuccess(ReportInfo reportInfo);

    void getReportInfoError();

    void getReportInfoResult();
}
