package com.ruanyun.chezhiyi.view.ui.workorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SaveWashCarParams;
import com.ruanyun.chezhiyi.commonlib.presenter.QuikWashCarPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.QuikWashCarMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.widget.ChooseTechnicianDialog;
import com.wintone.demo.plateid.MemoryCameraActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:快速洗车
 * author: zhangsan on 16/11/3 上午11:41.
 */
public class QuickWashCarActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, QuikWashCarMvpView, ChooseTechnicianDialog.onChooseTechinicanResult, TextView.OnEditorActionListener {
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edt_carnum_input)
    EditText edtCarnumInput;
    @BindView(R.id.img_btn_scan)
    ImageButton imgBtnScan;
    @BindView(R.id.edt_appointe_phone)
    EditText edtAppointePhone;
    @BindView(R.id.tv_customer_info)
    TextView tvCustomerInfo;
    @BindView(R.id.tv_server_project)
    TextView tvServerProject;
    @BindView(R.id.edit_work_order)
    EditText editWorkOrder;
    @BindView(R.id.tv_choose_technician)
    TextView tvChooseTechnician;
    @BindView(R.id.edit_server_remark)
    EditText editServerRemark;
    @BindView(R.id.bt_submit)
    Button btSubmit;

    private static final String WASHCARPROJECTNUM = "002000000000000";//获取空闲洗车技师参数
    private SaveWashCarParams params = new SaveWashCarParams();//提交洗车参数

    private User currentSelcetTechnician;//当前选中技师
    private CarBookingInfo carBookingInfo;//当前预约客户信息
    private QuikWashCarPresenter quikWashCarPresenter = new QuikWashCarPresenter();
    private ChooseTechnicianDialog chooseTechnicianDialog;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_car_wash);
        ButterKnife.bind(this);
        initView();
        quikWashCarPresenter.attachView(this);
        quikWashCarPresenter.getFreeTechnian(WASHCARPROJECTNUM);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        quikWashCarPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CustomerReceptionActivity.REQ_REC_PLATENUM://车牌识别成功回调
                    if (data != null) {
                        String carNumber = data.getCharSequenceExtra("number").toString();
                        if (!TextUtils.isEmpty(carNumber)) {
                            edtCarnumInput.setText(carNumber);
                            quikWashCarPresenter.getScanCustomerInfo(carNumber);
                        }
                    }
                    break;
            }
        }
    }


    private void initView() {
        topbar.setTttleText("快速洗车")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        chooseTechnicianDialog = new ChooseTechnicianDialog();
        chooseTechnicianDialog.setChooseTechinicanResult(this);
//        edtCarnumInput.setText("皖NRB167");
        edtCarnumInput.setText("");
        edtCarnumInput.setOnEditorActionListener(this);
    }

    @OnClick({R.id.img_btn_scan, R.id.tv_customer_info, R.id.tv_choose_technician, R.id.bt_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_scan://跳转到车牌识别页面
                showVideoRecPlateNum();
                break;
            case R.id.tv_customer_info://客户信息
                if (carBookingInfo != null && carBookingInfo.getCustomerUser() != null) {
                    carBookingInfo.getCustomerUser().setFriendStatus(10);
                    AppUtility.goToUserProfile(mContext, carBookingInfo.getCustomerUser());
                }
                break;
            case R.id.tv_choose_technician:
                chooseTechnicianDialog.showDlg(currentSelcetTechnician, getSupportFragmentManager());
                break;
            case R.id.bt_submit:
                sumbit();
                break;
        }
    }


    /**
     * 跳转到车牌识别页面
     *
     * @author zhangsan
     * @date 16/10/12 下午4:34
     */
    private void showVideoRecPlateNum() {
        Intent video_intent = new Intent();
        video_intent.putExtra("camera", true);
        video_intent.setClass(getApplicationContext(), MemoryCameraActivity.class);
        startActivityForResult(video_intent, CustomerReceptionActivity.REQ_REC_PLATENUM);
    }

    /**
     * 提交洗车信息
     *
     * @author zhangsan
     * @date 16/11/3 下午2:03
     */
    private void sumbit() {
        if (TextUtils.isEmpty(edtCarnumInput.getText().toString())) {
            showToast("请填写车牌信息");
            return;
        }
        if (currentSelcetTechnician == null) {
            showToast("请选择技师");
            return;
        }
        String phone = edtAppointePhone.getText().toString();
        if (!StringUtil.isPhone(phone)) {
            showToast("手机号不合法");
            return;
        }
        params.setJsUserNum(currentSelcetTechnician.getUserNum());
        params.setPhone(phone);
        params.setRemark(editServerRemark.getText().toString());
        params.setServicePlateNumber(edtCarnumInput.getText().toString());
        quikWashCarPresenter.submitWashCarInfo(params);
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_left:
                finish();
                break;
        }
    }


    @Override
    public void showToast(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    /**
     * 提交工单成功回调
     **/
    @Override
    public void submitWashCarSuccess() {
              /*开单成功后，修改技师状态为繁忙*/
        app.getUser().setWorkStatus(C.WORKSTATE_BUSY);
        finish();
    }

    /**
     * 获取空闲技师回调
     *
     * @param technicians
     **/
    @Override
    public void onFreeTechnicanResult(List<User> technicians) {
        chooseTechnicianDialog.updateList(technicians);
    }

    /**
     * 扫描成功回调
     *
     * @param carBookingInfo
     **/
    @Override
    public void onScanCarBookingResult(CarBookingInfo carBookingInfo) {
        this.carBookingInfo = carBookingInfo;
        edtAppointePhone.setText(carBookingInfo.getCustomerUser() == null ? "" : carBookingInfo.getCustomerUser().getLoginName());
    }

    @Override
    public void onChooseTechinicanResult(User user) {
        this.currentSelcetTechnician = user;
        tvChooseTechnician.setText(user.getNickName());
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {//按下输入法发送
            if (!TextUtils.isEmpty(edtCarnumInput.getText().toString())) {
                quikWashCarPresenter.getScanCustomerInfo(edtCarnumInput.getText().toString());
            }
        }
        return false;
    }
}
