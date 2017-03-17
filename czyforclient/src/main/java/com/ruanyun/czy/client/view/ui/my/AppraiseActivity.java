package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.AddOrderCommentParams;
import com.ruanyun.chezhiyi.commonlib.presenter.AppraisePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.AppraiseMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.AppraiseAdapter;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单商品的评价
 *
 * @author ycw
 */
public class AppraiseActivity
        extends AutoLayoutActivity
        implements View.OnClickListener, TextWatcher, RatingBar.OnRatingBarChangeListener, Topbar.onTopbarClickListener, AppraiseMvpView {

    RatingBar rbSatisfaction;//对商家的评价
    Button btSubmit;
    EditText etMerAppraise;
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list_appraise)
    ListView listAppraise;

    private AppraiseAdapter adapter;
    private List<OrderGoodsInfo> mGoodInfos = new ArrayList<>();
    private OrderInfo mOrderInfo;
    AppraisePresenter presenter = new AppraisePresenter();
    AddOrderCommentParams orderCommentParams =  new AddOrderCommentParams();

    private String shopContent = "";
    private float shopRating = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraise);
        presenter.attachView(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar.setTttleText("评价")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        mOrderInfo = getIntent().getParcelableExtra(C.IntentKey.ORDER_INFO);
        if (mOrderInfo != null) {
            mGoodInfos.addAll(mOrderInfo.getOrderGoodsList());
        }

        View headView = getViewFromLayout(R.layout.layout_appraisa_topview);
        rbSatisfaction = (RatingBar) headView.findViewById(R.id.rb_satisfaction);
        rbSatisfaction.setOnRatingBarChangeListener(this);
        etMerAppraise = (EditText) headView.findViewById(R.id.et_mer_appraise);
        etMerAppraise.setVisibility(View.GONE);
        etMerAppraise.addTextChangedListener(this);
        AutoUtils.auto(headView);
        listAppraise.addHeaderView(headView);

        View view = getViewFromLayout(R.layout.layout_btn_submit);
        btSubmit = (Button) view.findViewById(R.id.bt_submit);
        btSubmit.setOnClickListener(this);
        AutoUtils.auto(view);
        listAppraise.addFooterView(view);

        adapter = new AppraiseAdapter(mContext, mGoodInfos, R.layout.list_item_appraise);
        listAppraise.setAdapter(adapter);
    }


    /**
     * @description 提交用户的评价
     * @author ycw
     */
    private void saveEvalInfo(String evalStr) {
        LogX.d(TAG, "----评价的json string-----》\n" + evalStr + "\n");
        orderCommentParams.setOrderNum(mOrderInfo.getOrderNum());
        orderCommentParams.setJsonArrayString(evalStr);
        presenter.addOrderComment(app.getApiService().addOrderComment(app.getCurrentUserNum(), orderCommentParams));
    }

    /**
     * 获取评价的信息   json  string
     */
    private String getEvalInfoStr() {
        List<AppraiseInfo> appraiseInfos = new ArrayList<>();
        List<OrderGoodsInfo> infos = adapter.getData();
        AppraiseInfo appraiseInfoShop = new AppraiseInfo();
        appraiseInfoShop.commentContent = shopContent;
        appraiseInfoShop.commentScore = String.valueOf(shopRating);
        appraiseInfoShop.commentType = "t_store_info";
        appraiseInfoShop.glNum = app.getStoreInfo().getStoreNum();
        appraiseInfos.add(appraiseInfoShop);

        if (infos != null && infos.size() > 0) {
            AppraiseInfo appraiseInfoGoods = new AppraiseInfo();
            appraiseInfoGoods.commentContent = infos.get(0).getContent();
            appraiseInfoGoods.commentScore =String.valueOf(infos.get(0).getLevel());
            appraiseInfoGoods.commentType = "t_goods_info";
            appraiseInfoGoods.glNum =infos.get(0).getGoodsNum();
            appraiseInfos.add(appraiseInfoGoods);

        }
        Gson gson = new Gson();
        return gson.toJson(appraiseInfos);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**  提交按钮的点击事件  */
            case R.id.bt_submit:
//                String appr = getEvalInfoStr();
                if (AppUtility.isNotEmpty(shopContent)) {
                    saveEvalInfo(getEvalInfoStr());
                } else {
                    AppUtility.showToastMsg("请输入评价后再提交");
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        shopContent = s.toString().trim();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (rating == 0.0) {
            etMerAppraise.setVisibility(View.GONE);
        } else {
            etMerAppraise.setVisibility(View.VISIBLE);
        }
        shopRating = rating;
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void addCommentSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    class AppraiseInfo {
        public String glNum;//:"门店编号storeNum",;//
        public String commentType;//:"t_store_info","t_goods_info";//
        public String commentContent;//:"评论内容",;//
        public String commentScore;//:"评论分值";//
    }

}
