package com.ruanyun.czy.client.view.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.FeedBackInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.FeedBackParams;
import com.ruanyun.chezhiyi.commonlib.presenter.FeedBackPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.FeedBackMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class OpinionFeedBackActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,FeedBackMvpView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.et_content)
    EditText etContent;//内容
    @BindView(R.id.tv_count)
    TextView tvCount;//数量
    @BindView(R.id.et_phone)
    EditText etPhone;//联系电话

    FeedBackParams feedBackParams = new FeedBackParams();
    FeedBackPresenter feedBackPresenter = new FeedBackPresenter();

    String userNum = null;
    String content = null;
    String linkTel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_feedback);
        ButterKnife.bind(this);
        initView();
        feedBackPresenter.attachView(this);

    }

    private void initView() {
        topbar.setTttleText("意见反馈")
                .setBackBtnEnable(true)
                .addViewToTopbar(topbar.getTvTitleRight(),topbar.getLayoutParamsImgRight())
                .setRightText("提交")
                .onRightTextClick()
                .onBackBtnClick()
                .setTopbarClickListener(this);

        //监听EditText内容变化
        etContent.addTextChangedListener(new EmojiFiltrationTextWatcher(etContent) {
            @Override
            public void emojiFiltAfterTextChanged(Editable editable) {
                tvCount.setText(editable.length() + "/400");
                if(400 == editable.length()){
                    AppUtility.showToastMsg("字数超出范围");
                }
            }
        });

//        new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                tvCount.setText(editable.length() + "/400");
//                if(400 == editable.length()){
//                    AppUtility.showToastMsg("字数超出范围");
//                }
//            }
//        });
        etPhone.setText(app.getUser().getLinkTel());
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
        if(id == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right){
                userNum = App.getInstance().getUser().getUserNum();
                content = etContent.getText().toString();
                linkTel = etPhone.getText().toString();

                if (!(AppUtility.isNotEmpty(content) && AppUtility.isNotEmpty(linkTel))){
                    AppUtility.showToastMsg("请将信息填写完整！");
                }else {
                    if (StringUtil.isPhone(linkTel)) {
                        feedBackParams.setContent(content);
                        feedBackParams.setLinkTel(linkTel);

                        Call call = app.getApiService().feedBackInfo(userNum, feedBackParams);
                        feedBackPresenter.onFeedBackApply(call);
                    }else {
                        AppUtility.showToastMsg("手机号格式不正确！");
                    }
                }
        }
    }

    @Override
    public void onFeedBackShowLoading() {
        showLoading();
    }

    @Override
    public void onFeedBackSuccess(ResultBase<FeedBackInfo> resultBase) {
        AppUtility.showToastMsg("提交成功");
        etContent.setText("");
        etPhone.setText("");
        finish();
    }

    @Override
    public void onFeedBackError(ResultBase<FeedBackInfo> resultBase, int errorCode) {
        AppUtility.showToastMsg(resultBase.getMsg());
    }

    @Override
    public void onFeedBackFail(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void onFeedBackResponse() {
        dissMissLoading();
    }

    @Override
    public void onLoginLoadingDissMiss() {
        dissMissLoading();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        feedBackPresenter.detachView();
    }
}
