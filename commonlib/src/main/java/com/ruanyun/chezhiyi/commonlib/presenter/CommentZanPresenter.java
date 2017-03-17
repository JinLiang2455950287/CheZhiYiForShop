package com.ruanyun.chezhiyi.commonlib.presenter;

import android.text.TextUtils;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.PraiseBase;
import com.ruanyun.chezhiyi.commonlib.model.params.AddCommentParams;
import com.ruanyun.chezhiyi.commonlib.model.params.AddOrPraiseParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.CommentZanMvpView;

import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/9/9 下午4:44.
 * 评论点赞通用
 */
public class CommentZanPresenter implements Presenter<CommentZanMvpView>{
    AddCommentParams params=new AddCommentParams();//评论参数
    AddOrPraiseParams zanParams=new AddOrPraiseParams();//点赞参数
    CommentZanMvpView commentZanMvpView;

    @Override
    public void attachView(CommentZanMvpView mvpView) {
        commentZanMvpView=mvpView;
    }

    @Override
    public void detachView() {
       commentZanMvpView=null;
    }

    @Override
    public void onCancel() {

    }
    /** 判断是否已经赞过 **/
    public void isPraised(String glNum,String praiseType){
        if(commentZanMvpView!=null)
        commentZanMvpView.showLoadingDlg("处理中...");
        zanParams.setGlNum(glNum);
        zanParams.setPraiseType(praiseType);
        App.getInstance().getApiService().isPraised(App.getInstance().getCurrentUserNum(),zanParams).enqueue(new ResponseCallback<ResultBase<PraiseBase>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PraiseBase> resultBase) {
                if(TextUtils.isEmpty(resultBase.getObj().getGlNum())){
                    if(commentZanMvpView!=null)
                    commentZanMvpView.getPraiseStatusSuccess(commentZanMvpView.STATUS_UNPRAISED);
                }else {
                    if(commentZanMvpView!=null)
                    commentZanMvpView.getPraiseStatusSuccess(commentZanMvpView.STATUS_PRAISED);
                }
            }

            @Override
            public void onError(Call call, ResultBase<PraiseBase> praiseBaseResultBase, int errorCode) {
                if(commentZanMvpView!=null)
                commentZanMvpView.getPraiseStatusFail();
            }

            @Override
            public void onFail(Call call, String msg) {
                if(commentZanMvpView!=null)
                commentZanMvpView.getPraiseStatusFail();
            }

            @Override
            public void onResult() {
                if(commentZanMvpView!=null)
                commentZanMvpView.dissMisDlg();
            }
        });
    }


    /**
      * 点赞或取消赞
      *@author zhangsan
      *@date   16/9/9 下午5:50
      */
    public void zanOrCancel(String glNum,String praiseType){
      zanParams.setGlNum(glNum);
      zanParams.setPraiseType(praiseType);
      App.getInstance().getApiService().addOrDeletePraise(App.getInstance().getCurrentUserNum(),zanParams).enqueue(new ResponseCallback<ResultBase>() {
          @Override
          public void onSuccess(Call call, ResultBase resultBase) {
              if(commentZanMvpView!=null) {
                  commentZanMvpView.showToast(resultBase.getMsg());
                  commentZanMvpView.onPraisedResult();
              }
          }

          @Override
          public void onError(Call call, ResultBase resultBase, int errorCode) {
              if(commentZanMvpView!=null)
              commentZanMvpView.getPraiseStatusFail();
          }

          @Override
          public void onFail(Call call, String msg) {
              if(commentZanMvpView!=null)
              commentZanMvpView.getPraiseStatusFail();
          }

          @Override
          public void onResult() {

          }
      });
    }

    public void comment(String content,String commentType,String glNum){
        if(TextUtils.isEmpty(content)){
          commentZanMvpView.showToast("请输入评论内容!");
            return;
        }
        params.setCommentContent(content);
        params.setCommentType(commentType);
        params.setGlNum(glNum);
        if(commentZanMvpView!=null)
        commentZanMvpView.showLoadingDlg("处理中...");
        App.getInstance().getApiService().addComment(App.getInstance().getCurrentUserNum(),params).enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                if(commentZanMvpView!=null)
                commentZanMvpView.showToast(resultBase.getMsg());
                commentZanMvpView.onCommentSuccess();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                if(commentZanMvpView!=null)
                commentZanMvpView.showToast(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {
                if(commentZanMvpView!=null)
                commentZanMvpView.showToast("请求失败");
            }

            @Override
            public void onResult() {
                if(commentZanMvpView!=null)
                commentZanMvpView.dissMisDlg();
            }
        });
    }

    /**
     * 删除案例
     * @param libNum
     */
    public void delCaseLib(String libNum){
        if(commentZanMvpView!=null)
         commentZanMvpView.showLoadingDlg("处理中...");
       App.getInstance().getApiService().deleteCaselib(App.getInstance().getCurrentUserNum(),libNum).enqueue(new ResponseCallback<ResultBase>() {
           @Override
           public void onSuccess(Call call, ResultBase resultBase) {
               if(commentZanMvpView!=null)
               commentZanMvpView.showToast(resultBase.getMsg());
               commentZanMvpView.delCaseSuccess();
           }

           @Override
           public void onError(Call call, ResultBase resultBase, int errorCode) {
               if(commentZanMvpView!=null)
               commentZanMvpView.showToast(resultBase.getMsg());
               commentZanMvpView.delCaseFail();
           }

           @Override
           public void onFail(Call call, String msg) {
               if(commentZanMvpView!=null)
                   commentZanMvpView.showToast("请求失败");
               commentZanMvpView.delCaseFail();
           }

           @Override
           public void onResult() {
               if(commentZanMvpView!=null)
                   commentZanMvpView.dissMisDlg();
           }
       });
    }
   /**
     * 获取案例详情
     *@author zhangsan
     *@date   16/10/17 下午5:49
     */
    public void getCaseLibDetaiLInfo(String caseLibNum){
        if(commentZanMvpView!=null)
         commentZanMvpView.showLoadingDlg("正在获取案例信息");
        App.getInstance().getApiService().getCaseInfoDetailByNum(App.getInstance().getCurrentUserNum(),caseLibNum).enqueue(new ResponseCallback<ResultBase<CaseInfo>>() {
            @Override
            public void onSuccess(Call call, ResultBase<CaseInfo> caseInfoResultBase) {
                if(commentZanMvpView!=null)
                commentZanMvpView.caseInfoReulst(caseInfoResultBase.getObj());
            }

            @Override
            public void onError(Call call, ResultBase<CaseInfo> caseInfoResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                if(commentZanMvpView!=null)
                commentZanMvpView.dissMisDlg();
            }
        });
    }

}
