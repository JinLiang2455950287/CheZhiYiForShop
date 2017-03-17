package com.ruanyun.chezhiyi.commonlib.view;

import com.ruanyun.chezhiyi.commonlib.base.MvpView;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;

/**
 * Description:
 * author: zhangsan on 16/9/9 下午4:39.
 * 评论点赞通用
 */
public interface CommentZanMvpView extends MvpView {
    //String	类型【t_case_library 案例,t_article_info 文章】
    String TYPE_CASE_LIBS="t_case_library";
    String TYPE_ARTICLE="t_article_info";

    int STATUS_PRAISED=6658;//已赞
    int STATUS_UNPRAISED=3434;//未赞
    /** 评论成功 **/
    void  onCommentSuccess();
    /** 点赞或取消赞返回 **/
    void  onPraisedResult();
    void showToast(String msg);
    void showLoadingDlg(String msg);
    void dissMisDlg();
    void getPraiseStatusFail();
    /**  获取点赞状态**/
    void getPraiseStatusSuccess(int status);
    void caseInfoReulst(CaseInfo caseInfo);
    void delCaseSuccess();
    void delCaseFail();
}
