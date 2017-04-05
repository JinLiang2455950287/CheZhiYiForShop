package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CardPackageDetailInfo;
import com.ruanyun.chezhiyi.commonlib.model.GoodsListBean;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.MakeOrderParams;
import com.ruanyun.chezhiyi.commonlib.presenter.CardPackageDingDanPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.CardPackageInfoPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.CardPackageDingDanView;
import com.ruanyun.chezhiyi.commonlib.view.CardPackageInfoView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.adapter.CardPackageInfoAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 卡套餐详情
 * Created by jl on 2017/4/1
 */
public class CardPackageDetailActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, CardPackageInfoView, CardPackageDingDanView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.card_list)
    RecyclerView recyclerViewHomeRecommend;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_orginprice)
    TextView tvOrginprice;
    @BindView(R.id.tv_newprice)
    TextView tvNewprice;
    @BindView(R.id.tv_totalprice)
    TextView tvTotalprice;
    @BindView(R.id.tv_purchase)
    TextView tvPurchase;
    private CardPackageInfoPresenter cardPackageInfoPresenter = new CardPackageInfoPresenter();
    private CardPackageDingDanPresenter cardPackageDingDanPresenter = new CardPackageDingDanPresenter();
    private String packageNumber;
    private CardPackageInfoAdapter cardPackageInfoAdapter;
    private CardPackageDetailInfo cardPackageDetailInfo = new CardPackageDetailInfo();
    private List<GoodsListBean> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_package_detail);
        ButterKnife.bind(this);
        initView();
        initAdapter();
    }

    private void initView() {
        topbar.setTttleText("提交订单")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        cardPackageInfoPresenter.attachView(this);
        cardPackageDingDanPresenter.attachView(this);
        listData = new ArrayList<>();
        packageNumber = getIntent().getStringExtra("packageNumber");
        cardPackageInfoPresenter.getCardPackageInfo(app.getApiService().getCardPackegInfo(app.getCurrentUserNum(), packageNumber));
        tvPurchase.setVisibility(View.GONE);
    }

    private void initAdapter() {
        //服务商品
        GridLayoutManager homeManager = new GridLayoutManager(mContext, 1);
        recyclerViewHomeRecommend.setLayoutManager(homeManager);
        cardPackageInfoAdapter = new CardPackageInfoAdapter(mContext, R.layout.list_item_cardpackage_info, listData);
        recyclerViewHomeRecommend.setAdapter(cardPackageInfoAdapter);
        cardPackageInfoAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void getDataSuccess(CardPackageDetailInfo cardPackageDetail) {
        cardPackageDetailInfo = cardPackageDetail;
        listData = cardPackageDetailInfo.getGoodsList();
        cardPackageInfoAdapter.setDatas(listData);
        cardPackageInfoAdapter.notifyDataSetChanged();
        tvTitle.setText(cardPackageDetailInfo.getPackageName());
        tvOrginprice.setText("¥ " + cardPackageDetailInfo.getPackagePrice());
        tvTotalprice.setText("¥ " + cardPackageDetailInfo.getPackagePrice() + "");
        tvOrginprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
        tvNewprice.setText("¥ " + cardPackageDetailInfo.getPackagePrice() + "");
    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void cancelSuccess() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cardPackageInfoPresenter.detachView();
        cardPackageDingDanPresenter.detachView();
    }

    @OnClick({R.id.tv_purchase})
    public void purse(View view) {
        switch (view.getId()) {
            case R.id.tv_purchase:
                app.loadingDialogHelper.showLoading(this, "订单提交中.....");

                MakeOrderParams makeOrderParams = new MakeOrderParams();
//                makeOrderParams.setActualPrice(String.format(Locale.CHINA, "%2.2f", cardPackageDetailInfo.getPackagePrice()));          //实际支付金额
                makeOrderParams.setActualPrice(cardPackageDetailInfo.getPackagePrice() + "");          //实际支付金额
//                makeOrderParams.setCouponNum(couponNum);           //优惠劵
//                makeOrderParams.setPreferentialPrice(String.format(Locale.CHINA, "%2.2f", preferentialPrice));    //优惠金额
                makeOrderParams.setTotalCount(1);            //数量
//                makeOrderParams.setTotalPrice(String.format(Locale.CHINA, "%2.2f", cardPackageDetailInfo.getPackagePrice()));            //总价
                makeOrderParams.setTotalPrice(cardPackageDetailInfo.getPackagePrice() + "");            //总价
//                makeOrderParams.setSinglePrice(String.format(Locale.CHINA, "%2.2f", cardPackageDetailInfo.getPackagePrice()));//单价
                makeOrderParams.setSinglePrice(cardPackageDetailInfo.getPackagePrice() + "");//单价
                makeOrderParams.setCommonNum(cardPackageDetailInfo.getPackageNum());
                makeOrderParams.setGoodsNum(cardPackageDetailInfo.getPackageNum());
                makeOrderParams.setOrderType("KTC");
                cardPackageDingDanPresenter.getDingDanData(app.getApiService().makeOrder(app.getCurrentUserNum(), makeOrderParams));
                break;
        }
    }

    @Override
    public void reportDinDanSuccess(OrderInfo orderInfo) {
        dissMissLoading();
        LogX.e("提交订单", orderInfo.toString());
        toPayActivity(orderInfo);
//        finish();
    }

    @Override
    public void reportDinDanFalse() {
        dissMissLoading();
        AppUtility.showToastMsg("订单提交失败");
    }

    /**
     * 去支付界面
     *
     * @param orderInfo
     */
    private void toPayActivity(OrderInfo orderInfo) {
        Intent intent = AppUtility.getPayIntent(orderInfo, mContext);
        intent.putExtra(C.IntentKey.GOODS_NAME, cardPackageDetailInfo.getPackageName());
        showActivity(intent);
        finish();
    }
}
