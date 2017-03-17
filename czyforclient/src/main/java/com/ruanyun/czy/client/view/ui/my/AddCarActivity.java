package com.ruanyun.czy.client.view.ui.my;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.AddCarPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.MyTextWathcher;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.AddCarMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CarRegionPopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.ParentCodeChoosePopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.commonlib.view.widget.VehicleDialogFragmnet;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.widget.CarViewPagerAdapter;
import com.ruanyun.czy.client.view.widget.ViewPagerPointIndicator;
import com.ruanyun.czy.client.view.widget.ZoomOutPageTransformer;
import com.ruanyun.imagepicker.AndroidImagePicker;
import com.ruanyun.imagepicker.bean.ImageItem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;

/**
 * Description:添加我的爱车
 * author: zhangsan on 16/9/22 上午11:28.
 */
public class AddCarActivity extends AutoLayoutActivity implements AddCarMvpView, Topbar.onTopbarClickListener, DatePickerDialog.OnDateSetListener,
        AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener, CarRegionPopWindow.OnPopWindowCarRegionClickListener, MyTextWathcher.TextChangeLisener, ParentCodeChoosePopWindow.onParentCodeResult {
    public static final int REQ_IMG_ADD = 123;//添加爱车图片或新增时选车系
    public static final int REQ_IMG_MODIFY = 2342;//修改车图片或修改车系
    static final int TYPE_PLATENUM_DATE = 4242;//上牌日期
    static final int TYPE_INSURANCE_DATE = 4545;//保险日期
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    ImageView indicator;
    @BindView(R.id.edt_travel_mile)
    EditText edtTravelMile;
    @BindView(R.id.tv_choose_date)
    TextView tvChooseDate;
    @BindView(R.id.tv_plate_attr)
    TextView tvPlateAttr;
    @BindView(R.id.edt_car_platenum)
    EditText edtCarPlatenum;
    @BindView(R.id.tv_car_color)
    TextView tvCarColor;
    @BindView(R.id.edt_frame_num)
    EditText edtFrameNum;
    @BindView(R.id.img_help1)
    ImageView imgHelp1;
    @BindView(R.id.edt_engine_num)
    EditText edtEngineNum;
    @BindView(R.id.img_help2)
    ImageView imgHelp2;
    @BindView(R.id.btn_illegal_info)
    Button btnIllegalInfo;
    @BindView(R.id.indicator_container)
    ViewPagerPointIndicator indicatorContainer;
    @BindView(R.id.tv_choose_insurancedate)
    TextView tvChooseInsurancedate;
    @BindView(R.id.edt_maintenance_mile)
    EditText edtMaintenanceMile;

    private Calendar mCalendar;

    private Date selectedDate;
    private VehicleDialogFragmnet helpDlg;//填写车架帮助对话框

    private DatePickerDialog datePickerDialog;
    AddCarPresenter addCarPresenter = new AddCarPresenter();
    private String currentSelectDate = "";
    private CarInfo currentCarInfo;
    private List<CarInfo> carInfoList = new ArrayList<>();
    private CarViewPagerAdapter carViewPagerAdapter;
    private CarInfo addCarInfo;
    private CarRegionPopWindow carRegionPopWindow;
    private ParentCodeChoosePopWindow colorChoosePopWindow;
    private ZoomOutPageTransformer zoomOutPageTransformer;
    private int selectDateType = -1;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_my_vehicle);
        ButterKnife.bind(this);
        initData();
        initView();
        registerBus();
        addCarPresenter.attachView(this);
        addCarPresenter.getUserCarInfo();
        AndroidImagePicker.getInstance().addOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImagePickCompleteListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            onPickPicResult(AndroidImagePicker.getInstance().getSelectedImages(), requestCode);
        }
    }

    @Override
    public void onBackPressed() {
       /* if (checkAllEditStatus()) {
          addCarPresenter.addOrUpdateCar(carInfoList);
        } else {
            super.onBackPressed();
        }*/
        checkAllEditStatus();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unRegisterBus();
        addCarPresenter.detachView();
        AndroidImagePicker.getInstance()
                .removeOnImagePickCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnPictureTakeCompleteListener(
                this);
    }

    private void initData() {
        addCarInfo = new CarInfo();
        addCarInfo.setAdd(true);
        carInfoList.add(addCarInfo);
    }

    private void initView() {
        mCalendar = Calendar.getInstance();
        topbar.setTttleText("我的爱车")
                .setBackBtnEnable(true)
                .enableRightImageBtn()
                .onRightImgBtnClick()
                .setRightImgBtnBg(R.drawable.mycar_delete)
                .onBackBtnClick()
                .setTopbarClickListener(this);
//        Drawable drawable = topbar.getBackground();
//        drawable.setAlpha(0);
//        topbar.setBackgroundDrawable(drawable);
        carRegionPopWindow = new CarRegionPopWindow(mContext);
        carRegionPopWindow.setOnPopupClickListener(this);
        carViewPagerAdapter = new CarViewPagerAdapter(mContext, carInfoList);
        viewpager.setOffscreenPageLimit(3);
        zoomOutPageTransformer = new ZoomOutPageTransformer();
        viewpager.setPageTransformer(true, zoomOutPageTransformer);
        viewpager.setAdapter(carViewPagerAdapter);
        edtCarPlatenum.addTextChangedListener(new MyTextWathcher(edtCarPlatenum.getId(), this));
        edtEngineNum.addTextChangedListener(new MyTextWathcher(edtEngineNum.getId(), this));
        edtTravelMile.addTextChangedListener(new MyTextWathcher(edtTravelMile.getId(), this));
        //edtTravelMile.setFilters(new InputFilter[]{new EditNumberInputFilter()});
        edtFrameNum.addTextChangedListener(new MyTextWathcher(edtFrameNum.getId(), this));
        edtMaintenanceMile.addTextChangedListener(new MyTextWathcher(edtMaintenanceMile.getId(), this));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorContainer.changeIndicator(position);
                currentCarInfo = carInfoList.get(position);
                if(currentCarInfo.isAdd()){
                    topbar.getImgTitleRight().setVisibility(View.GONE);
                }else {
                    topbar.getImgTitleRight().setVisibility(View.VISIBLE);
                }
                updateCarInfo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    /**
     * 选车系回调结果
     *
     * @author zhangsan
     * @date 16/10/9 下午2:58
     */
    @Subscribe
    public void onChooseModelResult(Event<CarModel> event) {
        switch (event.keyInt) {//这里和选图片共用requestcode
            case REQ_IMG_ADD:
                CarInfo carInfo = new CarInfo();
                carInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                carInfo.setCarModelNum(event.value.getCarModelNum());
                carInfo.setCarName(event.value.getCarModelAllName());
                updateLocalData(carInfo);
                break;
            case REQ_IMG_MODIFY:
                currentCarInfo.setCarModelNum(event.value.getCarModelNum());
                currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                currentCarInfo.setCarName(event.value.getCarModelAllName());
                carViewPagerAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 图片选取回调
     *
     * @author zhangsan
     * @date 16/10/18 上午11:25
     */
    private void onPickPicResult(List<ImageItem> items, int requestCode) {
        switch (requestCode) {
            case REQ_IMG_ADD:
                CarInfo carInfo = new CarInfo();
                carInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                carInfo.setPicPath(items.get(0).path);
                updateLocalData(carInfo);
                break;
            case REQ_IMG_MODIFY:
                currentCarInfo.setPicPath(items.get(0).path);
                currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                carViewPagerAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 车辆滑动或初始化时根据当前currecarinfo 刷新底部车辆信息
     *
     * @author zhangsan
     * @date 16/10/9 下午3:07
     */
    private void updateCarInfo() {
        tvPlateAttr.setText(checkNullStr(currentCarInfo.getCarShortName(), "皖"));
        tvChooseDate.setText(checkNullStr(StringUtil.getRegisterDate(currentCarInfo.getRegisterDate())));
        edtCarPlatenum.setText(checkNullStr(currentCarInfo.getPlateNumber()));
        // edtCarPlatenum.setText(checkNullStr(currentCarInfo.getCarShortName()));
        edtFrameNum.setText(checkNullStr(currentCarInfo.getFrameNumber()));
        edtEngineNum.setText(checkNullStr(currentCarInfo.getEngineNumber()));
        edtTravelMile.setText(Integer.toString(currentCarInfo.getCarMileage()));
        edtMaintenanceMile.setText(Integer.toString(currentCarInfo.getMaintenanceMileage()));
        tvCarColor.setText(addCarPresenter.getColorByCode(currentCarInfo.getColor()));
        tvChooseInsurancedate.setText(checkNullStr(StringUtil.getRegisterDate(currentCarInfo.getInsuranceStart())));
        if (!currentCarInfo.isAdd()) {
            currentCarInfo.setCarShortName(tvPlateAttr.getText().toString().trim());
        }

    }

    private String checkNullStr(String value) {
        return checkNullStr(value, "");
    }

    /**
     * 检查字符串是否为空 可提供默认值
     *
     * @author zhangsan
     * @date 16/10/9 下午3:02
     */
    private String checkNullStr(String value, String defaultValue) {
        if (TextUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 检查是否要提交更改
     *
     * @author zhangsan
     * @date 16/9/26 上午10:18
     */
    // boolean isEdit=false;//是否
    private void checkAllEditStatus() {
        boolean flag = false;
        for (CarInfo info : carInfoList) {
            if (!info.isAdd() && info.getEditStatus() == CarInfo.EDIT_STATUS_MODIFY) {
                flag = true;
                break;
            }
        }
        if (flag) {
            showEditConfirm();
        } else {
            finish();
        }
    }

    private void showDatePickDialog() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        if (null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(mContext, R.style.time_picker_style, this, year, month, day);
            datePickerDialog.setTitle("选择截止日期");
            datePickerDialog.getDatePicker().setMaxDate(mCalendar.getTimeInMillis());
        }
        datePickerDialog.updateDate(year, month, day);
        datePickerDialog.show();

    }

    /**
     * 显示填写车架号帮助图片dialog
     *
     * @author zhangsan
     * @date 16/11/3 下午5:31
     */
    private void showHelpDlg() {
        if (null == helpDlg) {
            helpDlg = new VehicleDialogFragmnet();
            helpDlg.setImgs(R.drawable.mycar_sample);
            helpDlg.setTitle("");
        }
        helpDlg.show(getSupportFragmentManager(), "");
    }

    /**
     * 选颜色弹框
     *
     * @author zhangsan
     * @date 16/10/9 下午2:59
     */
    private void showColorChooser() {
        if (null == colorChoosePopWindow) {
            colorChoosePopWindow = new ParentCodeChoosePopWindow(mContext, "选择车辆颜色", C.ParentCode.CAR_COLOR);
            colorChoosePopWindow.setParentCodeResult(this);
        }
        colorChoosePopWindow.showBottom(edtEngineNum);
    }


    private void showDelCarConfirm(final CarInfo carInfo) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setMessage("确认要删除吗")
                .setNegativeButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        addCarPresenter.deleteCar(carInfo);
                    }
                }).setPositiveButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void showEditConfirm() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setMessage("是否保存更改")
                .setNegativeButton("是", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!addCarPresenter.addCarEmpty(carInfoList)) {
                            addCarPresenter.addOrUpdateCar(carInfoList);
                        }

                    }
                }).setPositiveButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /**
     * 新增车辆放入内存
     *
     * @author zhangsan
     * @date 16/9/24 上午9:40
     */
    private void updateLocalData(CarInfo carInfo) {
        indicatorContainer.addPoint(mContext);//添加圆点指示器
        carInfoList.remove(addCarInfo);//将添加car的model 移动到list尾部
        carInfoList.add(carInfo);
        carInfoList.add(addCarInfo);
        carViewPagerAdapter.notifyDataSetChanged();
        currentCarInfo = carInfo;
        viewpager.setCurrentItem(carInfoList.indexOf(carInfo));
        topbar.getImgTitleRight().setVisibility(View.VISIBLE);
    }
      /**
        * 校验edittext 输入的数字值
        *@author zhangsan
        *@date   16/11/4 下午6:33
        */
    private boolean checkNumberStr(String content,int comparedNumber){
        boolean t1 = content.length() > 1;
        boolean t2 = content.startsWith("0");
        boolean t3 = TextUtils.isEmpty(content);
        boolean t4 = content.equals(Integer.toString(comparedNumber));
        //  if (!(content.length() > 1 && content.startsWith("0")) && !TextUtils.isEmpty(content))
        if (!(t1 && t2) && !t3 && !t4) { //不为空 数字不能以0开头 且与上次结果不相同
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 根据文本框编辑回调 和id修改指定字段
     *
     * @author zhangsan
     * @date 16/9/29 下午4:53
     */
    private void setCarInfoAttrById(int viewId, String content) {
        if (currentCarInfo != null && !currentCarInfo.isAdd()) {
            //editText.setText(content);
            switch (viewId) {
                case R.id.edt_frame_num://车架号码
                    if (!content.equals(currentCarInfo.getFrameNumber())) {
                        currentCarInfo.setFrameNumber(content);
                    } else {
                        return;
                    }
                    break;
                case R.id.edt_engine_num://发动机号
                    if (!content.equals(currentCarInfo.getEngineNumber())) {
                        currentCarInfo.setEngineNumber(content);
                    } else {
                        return;
                    }
                    break;
                case R.id.edt_travel_mile://行驶里程
                    if (checkNumberStr(content,currentCarInfo.getCarMileage())) {
                        currentCarInfo.setCarMileage(Integer.parseInt(content));
                    } else {
                        return;
                    }
                    break;
                case R.id.edt_maintenance_mile://保养里程
                    if (checkNumberStr(content,currentCarInfo.getMaintenanceMileage())) {
                        currentCarInfo.setMaintenanceMileage(Integer.parseInt(content));
                    } else {
                        return;
                    }
                    break;
                case R.id.edt_car_platenum://车牌号
                    if (!content.equals(currentCarInfo.getPlateNumber())) {
                        currentCarInfo.setPlateNumber(TextUtils.isEmpty(content)?"":content.toUpperCase());
                        currentCarInfo.setCarAllName(new StringBuilder(tvPlateAttr.getText().toString().trim())
                                .append(content.toUpperCase()).toString());
                        currentCarInfo.setCarShortName(tvPlateAttr.getText().toString().trim());
                    } else {
                        return;
                    }
                    break;
            }
            currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);//设置车辆信息为编辑过状态
        } else {
            switch (viewId) {
                case R.id.edt_car_platenum://车牌号
                    if (AppUtility.isNotEmpty(content) && StringUtil.isCarNumWithoutFirst(content.toUpperCase())) {
                        CarInfo info = new CarInfo();
                        info.setPlateNumber(TextUtils.isEmpty(content)?"":content.toUpperCase());
                        info.setCarAllName(new StringBuilder(tvPlateAttr.getText().toString().trim())
                                .append(content.toUpperCase()).toString());
                        info.setCarShortName(tvPlateAttr.getText().toString().trim());
                        info.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                        updateLocalData(info);
                    }
                    break;
            }
        }
    }

    /**
     * 日期选择回调
     *
     * @author zhangsan
     * @date 16/10/11 上午10:58
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(year, monthOfYear, dayOfMonth);
        selectedDate = mCalendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        currentSelectDate = formatter.format(selectedDate);
        switch (selectDateType) {
            case TYPE_INSURANCE_DATE://保险日期
                tvChooseInsurancedate.setText(currentSelectDate);
                currentCarInfo.setInsuranceStart(currentSelectDate);
                currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                break;
            case TYPE_PLATENUM_DATE://上牌日期
                tvChooseDate.setText(currentSelectDate);
                currentCarInfo.setRegisterDate(currentSelectDate);
                currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
                break;
        }

    }

    @OnClick({R.id.tv_choose_date, R.id.img_help1, R.id.img_help2, R.id.btn_illegal_info, R.id.tv_plate_attr, R.id.tv_car_color, R.id.tv_choose_insurancedate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_insurancedate://选择保险日期
                selectDateType = TYPE_INSURANCE_DATE;
                showDatePickDialog();
                break;
            case R.id.tv_choose_date://选择上牌日期
                selectDateType = TYPE_PLATENUM_DATE;
                showDatePickDialog();
                break;
            case R.id.img_help1://帮助
                showHelpDlg();
                break;
            case R.id.img_help2://帮助
                showHelpDlg();
                break;
            case R.id.btn_illegal_info://查询违章信息

                break;
            case R.id.tv_plate_attr://选择车牌归属地
                carRegionPopWindow.show(edtEngineNum);
                break;
            case R.id.tv_car_color://选择车辆颜色
                showColorChooser();
                break;
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left://返回按钮
                //finish();
                checkAllEditStatus();
                break;
            case com.ruanyun.chezhiyi.commonlib.R.id.img_btn_right://删除按钮
                if (currentCarInfo != null && !currentCarInfo.isAdd() && !TextUtils.isEmpty(currentCarInfo.getCarNum())) {
                    showDelCarConfirm(currentCarInfo);
                } else {  //删除本地刚刚添加的车辆
                    indicatorContainer.removePoint();
                    carViewPagerAdapter.removeItem(currentCarInfo);
                }
                break;
        }
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items, int requestCode) {
        onPickPicResult(items, requestCode);
    }

    @Override
    public void onPictureTakeComplete(String picturePath, int requestCode) {
           ArrayList<ImageItem> imageItems=new ArrayList<>();
          imageItems.add(new ImageItem(picturePath,new File(picturePath).getName(),0));
          onPickPicResult(imageItems,requestCode);
    }

    /**
     * 添加或修改车辆成功
     *
     * @author zhangsan
     * @date 16/9/29 下午1:56
     */
    @Override
    public void onPostEditSuccess() {
        finish();
    }

    /**
     * 添加或修改车辆失败
     *
     * @author zhangsan
     * @date 16/9/29 下午1:56
     */
    @Override
    public void onPostEditFail() {
//        finish();
    }

    /**
     * 删除车辆成功
     *
     * @author zhangsan
     * @date 16/9/29 下午5:13
     */
    @Override
    public void onDeleteSuccess(CarInfo carInfo) {
        indicatorContainer.removePoint();
  /*      carInfoList.remove(carInfo);
        carViewPagerAdapter.notifyDataSetChanged();*/
        carViewPagerAdapter.removeItem(carInfo);
        currentCarInfo = carInfoList.get(viewpager.getCurrentItem());
        updateCarInfo();
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

    /**
     * 进入页面时获取用户车辆信息的回调
     *
     * @author zhangsan
     * @date 16/10/9 下午3:01
     */
    @Override
    public void carListResult(List<CarInfo> carInfos, int type) {
        if (!carInfos.isEmpty()) {
            carInfoList.addAll(0, carInfos);
            carViewPagerAdapter.notifyDataSetChanged();
        }
        indicatorContainer.addPoint(mContext, carInfoList.size());//添加圆点指示器
        indicatorContainer.changeIndicator(0);
        currentCarInfo = carInfoList.get(0);
        currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_NOMODIFY);
        viewpager.setCurrentItem(0);
        updateCarInfo();
    }

    @Override
    public void carResult(CarInfo carInfo) {

    }

    /**
     * 车辆归属地弹框选择回调
     *
     * @author zhangsan
     * @date 16/10/9 下午3:01
     */
    @Override
    public void onPopWindowCarRegionItemClick(String carRegion) {
        tvPlateAttr.setText(carRegion);
        if (currentCarInfo != null && !currentCarInfo.isAdd()) {
            currentCarInfo.setCarShortName(carRegion);
            currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int viewId) {
        setCarInfoAttrById(viewId, s.toString().trim());
    }

    /**
     * 选择车辆颜色回调结果
     *
     * @author zhangsan
     * @date 16/10/9 下午3:02
     */
    @Override
    public void onParentCodeResult(ParentCodeInfo parentCodeInfo) {
        if (currentCarInfo != null && !currentCarInfo.isAdd()) {
            try {
                currentCarInfo.setColor(Integer.parseInt(parentCodeInfo.getItemCode()));

            } catch (NumberFormatException e) {
                currentCarInfo.setColor(1);
            }
            currentCarInfo.setEditStatus(CarInfo.EDIT_STATUS_MODIFY);
        }
        tvCarColor.setText(parentCodeInfo.getItemName());
    }


}
