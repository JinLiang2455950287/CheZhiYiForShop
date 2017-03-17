package com.ruanyun.chezhiyi.view.ui.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ClientContactAdapter;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SearchAddFirendParams;
import com.ruanyun.chezhiyi.commonlib.model.params.SendDiscountCouponParams;
import com.ruanyun.chezhiyi.commonlib.presenter.SendDiscountCouponPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.SendDiscountCouponMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;


/**
 * Description: 技师赠送优惠券界面
 * author: hdl on 16/9/22 下午1:35.
 */
public class SendCouponActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, SendDiscountCouponMvpView {

    static final String STR_FINISH = "赠送(%s)";

    Topbar topbar;
    CleanableEditText query;
    ListView list;
    EaseSidebar sidebar;
    ClientContactAdapter clientContactAdapter;
    int selectCount;//选择人数
    int maxSelectCount;//最多选择人数
    ProductInfo mProductInfo;//优惠券
    SendDiscountCouponParams params;
    SendDiscountCouponPresenter presenter = new SendDiscountCouponPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_pick_contact);
        registerBus();
        presenter.attachView(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
        if(presenter!=null){
            presenter.detachView();
        }
    }

    private void initView() {
        mProductInfo = getIntent().getParcelableExtra(C.IntentKey.PRODUCTINFO_INFO);
        topbar = getView(R.id.topbar);
        query = getView(R.id.query);
        list = getView(R.id.list);
        sidebar = getView(R.id.sidebar);
        clientContactAdapter = new ClientContactAdapter(mContext, new ArrayList<HxUser>());
        clientContactAdapter.isShowChooser(true);
        maxSelectCount = mProductInfo.getCouponCount();
        if(maxSelectCount<1){
            clientContactAdapter.clientContactDelegate.isCouponChoose = false;
        }
        list.setAdapter(clientContactAdapter);
        sidebar.setListView(list);

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                clientContactAdapter.getFilter().filter(s);
            }
        });
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        topbar.setTttleText("选择联系人").setRightText("赠送");
        topbar.getTvTitleRight().setEnabled(false);
        topbar.getTvTitleRight().setAlpha(0.5f);
        topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        getMyCostomers();
    }

    /**
     * 获取所有好友
     * @author hdl
     * @date 16/10/12.
     */
  /*  private void getHxUserList() {
        showLoading("获取客户列表...");
        Call<ResultBase<List<User>>> call = app.getHxApiService().getUserList(app.getCurrentUserNum(), 1);
        call.enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> hxUserResult) {
                clientContactAdapter.setupDataFromDb(clientContactAdapter.getUserlist(hxUserResult.getObj()));
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> hxUserResult, int erroCode) {
                getContactsFail();
            }

            @Override
            public void onFail(Call call, String msg) {
                getContactsFail();
            }

            @Override
            public void onResult() {
                dissMissLoading();
            }
        });
    }*/

    /**
     * 获取我的客户
     *@author zhangsan
     *@date   16/10/31 上午8:55
     */
    private void getMyCostomers(){
        showLoading("获取客户列表...");
        SearchAddFirendParams params = new SearchAddFirendParams();
        params.setUserType(C.USETYPE_CUSTOMER);
        app.getHxApiService().searchAddFriend(app.getCurrentUserNum(),params).enqueue(new ResponseCallback<ResultBase<PageInfoBase<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<User>> result) {
                clientContactAdapter.setupDataFromDb(clientContactAdapter.getUserlist(result.getObj().getResult()));
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<User>> pageInfoBaseResultBase, int errorCode) {
                getContactsFail();
            }

            @Override
            public void onFail(Call call, String msg) {
                getContactsFail();
            }

            @Override
            public void onResult() {
               dissMissLoading();
            }
        });
    }


    private void getContactsFail() {
        AppUtility.showToastMsg("获取好友列表失败！");
    }

    private void onContactPick() {
        if (selectCount > 0) {
            topbar.getTvTitleRight().setEnabled(true);
            topbar.getTvTitleRight().setAlpha(1);
            topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
            topbar.setRightText(String.format(STR_FINISH, selectCount));
        } else if (selectCount == 0) {
            topbar.setRightText("赠送");
            topbar.getTvTitleRight().setEnabled(false);
            topbar.getTvTitleRight().setAlpha(0.5f);
            topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        }

    }

    /**
     * link ClientContactDelegate 62
     *
     * @author zhangsan
     * @date 16/9/22 下午3:54
     * 点击联系人触发
     */
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
    public void onUserSelect(String event) {
        if (!TextUtils.isEmpty(event)) {
            if (event.equals("select")) {//选中
                selectCount++;
                if(selectCount >= maxSelectCount) {//达到最大选择人数
                    clientContactAdapter.clientContactDelegate.isCouponChoose = false;
                }
            } else if (event.equals("deselect")) {//取消选中
                selectCount--;
                if (selectCount < 0)
                    selectCount = 0;
                clientContactAdapter.clientContactDelegate.isCouponChoose = true;
            }else {
                AppUtility.showToastMsg("优惠券不足!");
            }
            EventBus.getDefault().removeStickyEvent(event);
            onContactPick();
        }
    }

    private List<HxUser> getLocalUser() {
        List<HxUser> users = DbHelper.getInstance().getContactList();

        return users;
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        } else if (v.getId() == R.id.tv_title_right) {//topbar赠送
            String selectUserNum = clientContactAdapter.getSelectUserNum();
            if (!TextUtils.isEmpty(selectUserNum)) {
                sendCoupon();
            }
        }
    }

    /**
     * 赠送优惠券
     */
    private void sendCoupon() {
        params = new SendDiscountCouponParams();
        params.setUserNums(clientContactAdapter.getSelectUserNum());
        params.setUserCouponNum(mProductInfo.getUserCouponNum());
        params.setGoodsNum(mProductInfo.getGoodsNum());
        presenter.setSendDiscountCouponMvpView(app.getApiService().sendDiscountCoupon(app.getCurrentUserNum(), params));
    }

    @Override
    public void showLoadingView() {
        showLoading("赠送中...");
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showSendDiscountCouponErrer(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showSendDiscountCouponSuccess(ResultBase resultBase) {
        AppUtility.showToastMsg(resultBase.getMsg());
        finish();
        EventBus.getDefault().post(new Event<String>(C.EventKey.UPDATE_USER_INFO,
                "refresh"));
    }
}
