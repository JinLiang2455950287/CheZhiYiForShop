package com.ruanyun.czy.client.view.ui.my;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.AddCarPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.view.AddCarMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CarRegionPopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.MainActivity;
import com.ruanyun.czy.client.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by msq on 2016/10/11.
 * 添加车辆信息Activity
 */
public class AddCarInformationActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,
        DatePickerDialog.OnDateSetListener, CarRegionPopWindow.OnPopWindowCarRegionClickListener, AddCarMvpView {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.et_cat_number)
    EditText etCatNumber;
    @BindView(R.id.et_mileage)
    EditText etMileage;
    @BindView(R.id.tv_card_time)
    TextView tvCardTime;
    @BindView(R.id.tv_insutance_time)
    TextView tvInsutanceTime;
    @BindView(R.id.btn_commission)
    Button btnCommission;
    @BindView(R.id.edt_maintenance_mile)
    EditText edtMaintenanceMile;

    String currentSelectDate = "";
    Calendar mCalendar;
    Date selectedDate;
    DatePickerDialog datePickerDialog;//日期dialog
    boolean flag = false;//标记是否点击了
    CarRegionPopWindow carRegionPopWindow;//汽车省份PopWindow
    Handler handler = new Handler();
    AddCarPresenter addCarPresenter = new AddCarPresenter();
    CarInfo carInfo = new CarInfo();
    List<CarInfo> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_car_information);
        addCarPresenter.attachView(this);
        ButterKnife.bind(this);
        initView();
        openHandler();
    }

    //开启一个线程
    private void openHandler() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 500);
                    //上牌时间tvCardTime不为空时，字体为黑色
                    if (AppUtility.isNotEmpty(tvCardTime.getText().toString())) {
                        tvCardTime.setTextColor(getResources().getColor(R.color.text_black));
                    }
                    //保险时间tvInsutanceTime不为空时，字体为黑色
                    if (AppUtility.isNotEmpty(tvInsutanceTime.getText().toString())) {
                        tvInsutanceTime.setTextColor(getResources().getColor(R.color.text_black));
                    }
                    //判断不为空时，底部Button变色
                    if (AppUtility.isNotEmpty(etCatNumber.getText().toString()) && AppUtility.isNotEmpty(etMileage.getText().toString())
                            && AppUtility.isNotEmpty(tvCardTime.getText().toString()) && AppUtility.isNotEmpty(tvInsutanceTime.getText().toString())) {
                        if (!btnCommission.isEnabled()) {
                            btnCommission.setBackgroundColor(getResources().getColor(R.color.theme_color_default));
                            btnCommission.setEnabled(true);
                        }
                    } else {
                        if (btnCommission.isEnabled()) {
                            btnCommission.setBackgroundColor(getResources().getColor(R.color.shallow_blue2));
                            btnCommission.setEnabled(false);
                        }
                    }

                } catch (Exception e) {
                    System.out.println("exception..." + e);
                }
            }
        });
    }

    private void initView() {
        mCalendar = Calendar.getInstance();
        carRegionPopWindow = new CarRegionPopWindow(mContext);
        carRegionPopWindow.setOnPopupClickListener(this);
        tvProvince.setText("皖");
        topbar.setTttleText("添加车辆信息")
                .setBackBtnEnable(true)
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .setTopbarClickListener(this);
        edtMaintenanceMile.setText("5000");
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right) {
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(year, monthOfYear, dayOfMonth);
        selectedDate = mCalendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        currentSelectDate = formatter.format(selectedDate);
        if (true == flag) {
            tvCardTime.setText(currentSelectDate);
        } else {
            tvInsutanceTime.setText(currentSelectDate);
        }
    }

    private void showDatePickDialog() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        if (null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(mContext, R.style.time_picker_style, this, year, month, day);
            datePickerDialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());
        }
        datePickerDialog.updateDate(year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onPopWindowCarRegionItemClick(String carRegion) {
        tvProvince.setText(carRegion);
    }

    @OnClick({R.id.tv_province, R.id.tv_card_time, R.id.tv_insutance_time, R.id.btn_commission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_province:
                carRegionPopWindow.show(getCurrentFocus());
                break;
            case R.id.tv_card_time:
                flag = true;
                showDatePickDialog();
                break;
            case R.id.tv_insutance_time:
                flag = false;
                showDatePickDialog();
                break;
            case R.id.btn_commission:
                carInfo.setCarShortName(tvProvince.getText().toString());
                carInfo.setPlateNumber(TextUtils.isEmpty(etCatNumber.getText().toString())?"":etCatNumber.getText().toString().toUpperCase());
                carInfo.setCarAllName(new StringBuilder(tvProvince.getText().toString()).append(carInfo.getPlateNumber()).toString());
                carInfo.setRegisterDate(tvCardTime.getText().toString());
                carInfo.setInsuranceStart(tvInsutanceTime.getText().toString());
                String maintenanceMile = edtMaintenanceMile.getText().toString().trim();
                String mileage = etMileage.getText().toString().trim();
                carInfo.setMaintenanceMileage(isValiditNumberStr(maintenanceMile)?Integer.parseInt(maintenanceMile):0);
                carInfo.setCarMileage(isValiditNumberStr(mileage)?Integer.parseInt(mileage):0);
                list.clear();
                list.add(carInfo);
                if (!addCarPresenter.firstAddCarEmpty(list)) {
                    addCarPresenter.addOrUpdateCar(list);
                }
                break;
        }
    }

    private boolean isValiditNumberStr(String content) {
        if (content.length() > 1 && content.startsWith("0")) {
            return false;
        } else if (TextUtils.isEmpty(content)) {
            return false;
        }
        return true;
    }

    @Override
    public void showLodingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showToast(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void carListResult(List<CarInfo> carInfos, int type) {
    }

    @Override
    public void carResult(CarInfo carInfo) {

    }

    /**
     * 成功时回调接口
     */
    @Override
    public void onPostEditSuccess() {
        //默认省份为皖
        showActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onPostEditFail() {
    }

    @Override
    public void onDeleteSuccess(CarInfo carInfo) {
    }

}
