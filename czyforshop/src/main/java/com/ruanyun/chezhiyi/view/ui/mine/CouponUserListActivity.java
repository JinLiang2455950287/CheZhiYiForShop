package com.ruanyun.chezhiyi.view.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.presenter.CouponUserListPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.CouponUserListMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CouponUserListAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.CouponUserListAdapter.CouponUserClickListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description ： 优惠劵   已发送优惠劵   使用情况列表
 * <p/>
 * Created by ycw on 2016/11/28.
 */
public class CouponUserListActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, CouponUserListMvpView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView list;
    private ProductInfo info;// 优惠劵
    private CouponUserListPresenter listPresenter = new CouponUserListPresenter();
    private CouponUserListAdapter adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_coupon_user_list);
        ButterKnife.bind(this);
        listPresenter.attachView(this);
        info = getIntent().getParcelableExtra(C.IntentKey.PRODUCTINFO_INFO);
        initView();
    }

    private void initView() {
        topbar.setTttleText("已发送优惠劵")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        adapter = new CouponUserListAdapter(mContext, R.layout.item_send_user_list, new ArrayList<OrderGoodsInfo>());
        list.setAdapter(adapter);
        listPresenter.commonList(app.getApiService().commonList(app.getCurrentUserNum(), info.getUserCouponNum()));
        adapter.setListener(new CouponUserClickListener() {
            @Override
            public void couponUserClick(String userNum) {
                // TODO: 2016/11/28   用户个人信息
                listPresenter.getUser(app.getHxApiService().getFriendShipInfo(app.getCurrentUserNum(), userNum));
            }
        });

    }


    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void showLoadingView(String s) {
        showLoading(s);
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showTips(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void getCommonListSuccess(List<OrderGoodsInfo> goodsInfoList) {
        adapter.setDatas(goodsInfoList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getUserSuccess(User user) {
        if (user == null) return;
        AppUtility.goToUserProfile(mContext, user);
    }
}
