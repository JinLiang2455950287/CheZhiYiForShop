package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.SignAddInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SignAddParams;
import com.ruanyun.chezhiyi.commonlib.presenter.ApplyOnlinePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.ApplyOnlineMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import retrofit2.Call;

/**
 * Description ：活动报名
 * <p>
 * Created by ycw on 2016/9/12.
 */
public class ApplyOnlineActivity extends AutoLayoutActivity implements ApplyOnlineMvpView, Topbar.onTopbarClickListener, TextWatcher {

    private static final int REQ_REMAKE = 154;
    Topbar topbar;
    EditText editName;
    LinearLayout llName;
    EditText editPhone;
    LinearLayout llPhone;
    EditText editNumber;
    LinearLayout llNumber;
    TextView tvRemark;
    LinearLayout llRemark;
    TextView tvPrice;
    LinearLayout llPrice;
    TextView tvTotalPrice;
    LinearLayout llTotalPrice;
    Button btnPay;

    ApplyOnlinePresenter presenter = new ApplyOnlinePresenter();
    SignAddParams signAddParams = new SignAddParams();
    ActivityInfo info;
    String remark;
    double totalPrice;
    int limitNum;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_apply_online);
        initView();
        presenter.attachView(this);
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        editName = getView(R.id.edit_name);
        llName = getView(R.id.ll_name);
        editPhone = getView(R.id.edit_phone);
        llPhone = getView(R.id.ll_phone);
        editNumber = getView(R.id.edit_number);
        llNumber = getView(R.id.ll_number);
        tvRemark = getView(R.id.edit_remark);
        llRemark = getView(R.id.ll_remark);
        tvPrice = getView(R.id.tv_price);
        llPrice = getView(R.id.ll_price);
        tvTotalPrice = getView(R.id.tv_total_price);
        llTotalPrice = getView(R.id.ll_total_price);
        btnPay = getView(R.id.btn_pay);
        topbar.setTttleText("活动报名")
                .onBackBtnClick()
                .setBackBtnEnable(true)
                .setTopbarClickListener(this);

//        btnPay.setClickable(false);
        btnPay.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                String linkTel = editPhone.getText().toString();
                String number = editNumber.getText().toString();
                String userName = editName.getText().toString();

                if (AppUtility.isNotEmpty(linkTel) && AppUtility.isNotEmpty(number) &&
                        AppUtility.isNotEmpty(userName)) {
                    Integer num = Integer.valueOf(number);
                    signAddParams.setActivityNum(info.getActivityNum());
                    signAddParams.setLinkTel(linkTel);
                    signAddParams.setRemark(remark);
                    signAddParams.setTotalNum(num);
                    signAddParams.setUserName(userName);
                    signAddParams.setTotalPrice(String.format("%2.2f", totalPrice));
                    if (num > 0) {
                        Call<ResultBase<SignAddInfo>> call = app.getApiService().signAdd(app.getCurrentUserNum(), signAddParams);
                        presenter.signAdd(call);
                    } else {
                        AppUtility.showToastMsg("请输入正确的人数！");
                    }
                } else {
                    AppUtility.showToastMsg("请填写完整信息！！");
                }
            }
        });
        llRemark.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                Intent intent = new Intent(mContext, RemarkActivity.class);
                intent.putExtra(C.IntentKey.EDIT_CONTEXT, remark);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "设置备注");
                startActivityForResult(intent, REQ_REMAKE);
            }
        });
        info = getIntent().getParcelableExtra(C.IntentKey.ACTIVITY_INFO);
        tvPrice.setText(new StringBuilder().append("¥").append(info.getCost()).append("/人").toString());
        tvTotalPrice.setText("¥ 0");
        editNumber.addTextChangedListener(this);
        if (info.getIsLimitNum() == 1) {
            limitNum = info.getLimitNum() - info.getRegisterNumber();
            editNumber.setHint("可报名人数" + limitNum + "人");
        } else {
            editNumber.setHint("报名无人数限制");
        }
        if (info.getCost() == 0) {
            btnPay.setText("确定报名");
        } else {
            btnPay.setText("提交并支付");
        }
        User user = app.getUser();
        if (user == null ) return;
        editName.setText(user.getNickName());
        if (AppUtility.isNotEmpty(user.getNickName())) {
            editName.setSelection(user.getNickName().length());
        }
        editPhone.setText(user.getLinkTel());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_REMAKE) {
            remark = data.getStringExtra(C.IntentKey.UPDATE_SIGNATURE);
            tvRemark.setText(remark);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showSignAddLoadingView() {
        showLoading();
    }

    @Override
    public void getSignAddOnSuccess(ResultBase<SignAddInfo> addInfo) {
        AppUtility.showToastMsg(addInfo.getMsg());
        //  将订单信息传递给支付界面
        if (addInfo.getObj().getOrderInfo() != null && AppUtility.isNotEmpty(addInfo.getObj()
                .getOrderInfo().getOrderNum())) {
            Intent intent = AppUtility.getPayIntent(addInfo.getObj().getOrderInfo(), mContext);
            intent.putExtra(C.IntentKey.ACTIVITY_NUM, info.getActivityNum());
            showActivity(intent);
        }
        finish();
    }

    @Override
    public void dismissSignAddLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showSignAddTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
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
        String num = editNumber.getText().toString();
        if (AppUtility.isNotEmpty(num)) {
            int number = Integer.valueOf(num);
            if (info.getIsLimitNum() == 1) {
                number = number > limitNum ? limitNum : number;
            }
            totalPrice = number * info.getCost();
        } else {
            totalPrice = 0;
        }
        LogX.d(TAG, "人数：" + num + "\n" + "金额：" + totalPrice);
        tvTotalPrice.setText(String.format("¥%2.2f", totalPrice));
    }
}
