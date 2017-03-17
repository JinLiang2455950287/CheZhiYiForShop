package com.ruanyun.czy.client.view.ui.my;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.RefudApplicationParams;
import com.ruanyun.chezhiyi.commonlib.presenter.RefundApplicationPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.RefundApplicationMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.RefundApplicationPopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 申请退款
 * Created by Sxq on 2016/9/18.
 */
public class RefundApplicationActivity extends AutoLayoutActivity implements Topbar
        .onTopbarClickListener, RefundApplicationMvpView {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_choice_reason)
    TextView tvChoiceReason;
    @BindView(R.id.edit_remarks)
    EditText editRemarks;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private RefundApplicationPopWindow applicationPopWindow;
    private RefundApplicationPresenter applicationPresenter = new RefundApplicationPresenter();
    private RefudApplicationParams params = new RefudApplicationParams();
    private String remarks, price;//退款备注  退款金额
    private String reasons = "";//退款原因
    private OrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_refund_application);
        applicationPresenter.attachView(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        orderInfo = getIntent().getParcelableExtra(C.IntentKey.ORDER_INFO);
        topbar.setTttleText(R.string.refund_application)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        applicationPopWindow = new RefundApplicationPopWindow(this) {
            @Override
            protected void getReasons(String reasons) {
                tvChoiceReason.setText(reasons);
            }
        };
//        price = orderInfo.getSinglePrice().multiply(new BigDecimal(orderInfo.getTotalCountSurplus())).setScale(2, BigDecimal.ROUND_HALF_UP)
//                .toString();
        price = orderInfo.getActualPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        tvPrice.setText(String.format("¥%s", price));

    }

    /**
     * topbar点击事件
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void refundApplicationMsg(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void refundApplicationSuccess() {
        // TODO: 2016/10/21 申请退款成功后
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public Context getContext() {
        return null;
    }


    @OnClick({R.id.tv_choice_reason, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choice_reason:
                applicationPopWindow.showBottom(tvChoiceReason);
                reasons = tvChoiceReason.getText().toString();
                break;
            case R.id.btn_submit:
                submitApplicate();
                break;
        }
    }

    /**
     * 提交退款申请
     */
    private void submitApplicate() {
        reasons = tvChoiceReason.getText().toString();
        if (!AppUtility.isNotEmpty(reasons)) {
            AppUtility.showToastMsg("请选择退款原因");
        } else {
            remarks = editRemarks.getText().toString();
            params.setOrderNum(orderInfo.getOrderNum());//订单编号
            params.setRefundPrice(new BigDecimal(price));//退款金额
            params.setRefundRemark(remarks);//备注
            params.setRefundReason(reasons);//退款原因
            Call call = app.getApiService().refundApplication(app.getCurrentUserNum(), params);
            applicationPresenter.refundApplicate(call);
        }

    }
}
