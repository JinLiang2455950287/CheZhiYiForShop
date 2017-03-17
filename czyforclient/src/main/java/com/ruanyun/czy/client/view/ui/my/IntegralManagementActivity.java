package com.ruanyun.czy.client.view.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.AccountMoneyInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.ProductGroupPurchaseParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.IntegralManagementAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * 积分管理
 * Created by wp on 2016/10/12.
 */
public class IntegralManagementActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener, IntegralManagementAdapter.PurchaseListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.iv_user_photo)
    CircleImageView ivUserPhoto;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rl_account)
    RelativeLayout rlAccount;
    @BindView(R.id.list)
    ListView list;

    private IntegralManagementAdapter adapter;
    private ProductGroupPurchaseParams params = new ProductGroupPurchaseParams();
    String integral="";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerBus();
        setContentView(R.layout.activity_integral_management);
        ButterKnife.bind(this);
        initView();
        refreshLayout.beginRefreshing();
    }

    private void initView() {
        topbar.setTttleText("积分管理")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("明细")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        tvUserName.setText(app.getUser().getNickName());
        tvBalance.setText(String.format("积分：%s分", new DecimalFormat("#0").format(app.getAccountMoneyInfo() ==null?0: app.getAccountMoneyInfo().getScoreBalance())));
        initRefreshLayout();
//        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(app.getUser().getUserPhoto()), ivUserPhoto, com.ruanyun.chezhiyi.commonlib.R.drawable.default_imge_big);
        Glide.with(mContext)
                .load(FileUtil.getImageUrl(app.getUser().getUserPhoto()))
                .error(R.drawable.default_img)
                .into(ivUserPhoto);
        adapter = new IntegralManagementAdapter(mContext, new ArrayList<ProductInfo>(), R.layout.list_item_integral_management);
        adapter.setPurchaseListener(this);
        list.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MainThread, priority = 124)
    public void getAccountMoneySuccess(Event<AccountMoneyInfo> event) {
        if (event != null && event.key.equals(C.EventKey.ACCOUNT_MONEY) ) {
            AccountMoneyInfo moneyInfo = event.value;
            app.setAccountMoneyInfo(moneyInfo);
            tvBalance.setText(String.format("积分：%s分", new DecimalFormat("#0").format(app
                    .getAccountMoneyInfo() == null ? 0 : app.getAccountMoneyInfo()
                    .getScoreBalance())));
        }
    }

    /**
     * topbar点击事件
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {     //明细
            Intent intent = new Intent(mContext, IntegralExchangeListActivity.class);
            intent.putExtra(C.IntentKey.ACCOUNT_TYPE, IntegralExchangeListActivity.EXCHANGE_TYPE);
            startActivity(intent);
        }
    }

    /**
     * 获取积分兑换列表
     * @return
     */
    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        params.setGoodsType(C.OrderType.ORDER_TYPE_JF);
        return app.getApiService().getProductList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(result.getResult());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        adapter.addData(result.getResult());
        adapter.notifyDataSetChanged();
    }

    /**
     * 跳转到积分兑换商品页
     * @param productInfo
     */
    @Override
    public void purchaseClick(ProductInfo productInfo) {
        //  积分兑换商品页
        //        if (productInfo.getScoreNumber() <= app.getAccountMoneyInfo().getScoreBalance()) {
/*        Intent intent = AppUtility.getGoodsIntent(productInfo.getSalePrice(),
                app.getCurrentUserNum(),
                productInfo.getGoodsNum(),
                C.OrderType.ORDER_TYPE_JF,
                productInfo.getGoodsNum(),
                app.getCurrentUserNum(),
                mContext,
                productInfo.getGoodsName(),
                productInfo.getProjectParent(),
                productInfo.getMainPhoto(),
                "1",
                productInfo.getViceTitle());
        intent.putExtra(C.IntentKey.IS_DUIHUAN, true);
        intent.putExtra(C.IntentKey.PRODUCTINFO_INFO, productInfo);
        showActivity(intent);*/
        AppUtility.showGoodsWebView(productInfo.getSalePrice(),
                app.getCurrentUserNum(),
                productInfo.getGoodsNum(),
                C.OrderType.ORDER_TYPE_JF,
                productInfo.getGoodsNum(),
                app.getCurrentUserNum(),
                mContext,
                productInfo.getGoodsName(),
                productInfo.getProjectParent(),
                productInfo.getMainPhoto(),
                productInfo.getViceTitle());

        //        } else {
        //            AppUtility.showToastMsg("当前积分不足！");
        //        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }
}
