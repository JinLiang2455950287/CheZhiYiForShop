package com.ruanyun.chezhiyi.view.ui.workorder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.CustomerRepUiModel;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.CarMileageParams;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.CustomerRepPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.DefaultItemAnimator;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.CustomerRepMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.UpdateNickNameActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.adapter.CustomerRepAdapter;
import com.ruanyun.chezhiyi.view.ui.home.AddServiceGoodsActivity;
import com.ruanyun.chezhiyi.view.widget.ChooseServiceTab;
import com.wintone.demo.plateid.MemoryCameraActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;

import static com.ruanyun.chezhiyi.R.id.edt_carnum_input;

/**
 * Description:客户接待页面
 * author: zhangsan on 16/10/8 上午8:31.
 */
public class CustomerReceptionActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,
        CustomerRepMvpView, ChooseServiceTab.onTabClickListener, TextView.OnEditorActionListener {
    public static final int REQ_REC_PLATENUM = 32434;//获取车牌扫描结果
    public static final int REQ_ADD_GOODS = 234234;//添加商品
    public static final int REQ_ADD_SERVICE = 2343;//添加服务大项
    public static final int REQ_ADD_UPKEEP = 2345;//添加里程数
    public static final String WORK_ORDER_GOODS_LIST = "workOrderGoodsList";
    public static final String LEADING_USER_NUM_IS_EMPTY = "leading_user_num_is_empty";
    public static final String WORKBAY_INFO_NUM_ISEMPTY = "workbay_info_num_isempty";
    public static final String CANSUBMIT = "cansubmit";

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edt_appointe_phone)
    CleanableEditText edtAppointePhone;

    @BindView(R.id.ry_choice_server)
    RecyclerView ryChoiceServer;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(edt_carnum_input)
    EditText edtCarnumInput;
    @BindView(R.id.tv_costomer_info)
    TextView tvCostomerInfo;
    @BindView(R.id.img_car_photo)
    ImageView imgCarPhoto;
    @BindView(R.id.tv_car_platenum)
    TextView tvCarPlatenum;
    @BindView(R.id.tv_car_owner)
    TextView tvCarOwner;
    @BindView(R.id.edt_write_mark)
    EditText edtWriteMark;
    @BindView(R.id.rl_add_server)
    RelativeLayout rlAddServer;
    @BindView(R.id.img_btn_scan)
    ImageButton imgBtnScan;
    @BindView(R.id.tv_arrive_time)
    TextView tvArriveTime;
    @BindView(R.id.lables_service)
    FlowLayout lablesService;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.ll_appoint_info)
    LinearLayout llAppointInfo;
    @BindView(R.id.tv_upkeep)
    TextView tvUpkeep;
    @BindView(R.id.ll_upkeep)
    LinearLayout llUpkeep;

    private CarBookingInfo carBookingInfo;//当前预约客户信息
    private CustomerRepPresenter presenter = new CustomerRepPresenter();
    private CustomerRepAdapter adapter;
    private List<CustomerRepUiModel> workOrderUiList = new ArrayList<>();
    private WorkOrderSubmitInfo workOrderSubmitInfo = new WorkOrderSubmitInfo();
    private int serviceTypeCount = 0;//服务分类数量
    private CarMileageParams params;// 修改最新里程数 参数
    private String upkeep;//  修改最新里程数
    private String carNumber;// 当前的车牌号
    private String plateNumber;
    private boolean textWatcherEnable = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_REC_PLATENUM://车牌识别成功回调
                    if (data != null) {
                        carNumber = data.getCharSequenceExtra("number").toString();
                        if (!TextUtils.isEmpty(carNumber)) {
                            edtCarnumInput.setText(carNumber);
                            presenter.getScanCustomerInfo(carNumber);
                        }
                    }
                    break;

                case REQ_ADD_UPKEEP://添加里程数
                    if (data != null) {
                        upkeep = data.getStringExtra(C.IntentKey.UPDATE_NICKNAME);
                        if (!TextUtils.isEmpty(upkeep)) {
                            params = new CarMileageParams();
                            params.setCarAllName(edtCarnumInput.getText().toString());
                            params.setCarMileage(upkeep);
                            presenter.saveCarMileage(app.getApiService().saveCarMileage(app.getCurrentUserNum(), params));
                        }
                    }
                    break;
            }
        } else if (resultCode == RESULT_FIRST_USER) {
            switch (requestCode) {
                case REQ_REC_PLATENUM://车牌识别页面返回 没有扫码
                    textWatcherEnable = true;
            }
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_customers_reception);
        ButterKnife.bind(this);
        initView();
        presenter.attachView(this);
        registerBus();
        serviceTypeCount = DbHelper.getInstance().getServiceTypeCount();
        plateNumber = getIntent().getStringExtra("plateNumber");
        edtCarnumInput.setText(plateNumber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    /**
     * 选择服务商品结果回调
     * post 在 addserviceActivity
     *
     * @author zhangsan
     * @date 16/10/19 上午9:40
     */
    @Subscribe
    public void onSelcectServiceReulst(Event<List<ProductInfo>> event) {
        if (event.key.equals(C.EventKey.ADD_PROJECT)) {
            List<ProductInfo> productInfos = event.value;
            if (productInfos.size() > 0) {
                if (event.keyInt == REQ_ADD_GOODS) {//服务大项添加商品
                    List<CustomerRepUiModel> cachedGoodsList = adapter.getGoodsListByProjectType(productInfos.get(0).getProjectParent());
                    List<CustomerRepUiModel> selectGoodsList = presenter.getUiModelFromProducts(productInfos);
                    if (cachedGoodsList == null) {
                        cachedGoodsList = new ArrayList<>();
                    }
//                    如果缓存中的商品数是0
                    if (cachedGoodsList.isEmpty()) {
                        int firstGoodsIndex = adapter.getFirstIndexOfType(CustomerRepUiModel.EMPTY_TYPE_GOODS);
                        clearLastTabItems(firstGoodsIndex - 1);
                    } else {
                        int firstGoodsIndex = adapter.getFirstIndexOfType(CustomerRepUiModel.TYPE_GOODS);
                        clearLastTabItems(firstGoodsIndex - 1);
                    }
                    cachedGoodsList.addAll(selectGoodsList);
                    int postion = adapter.getFirstIndexOfExpandItem();
                    workOrderUiList.addAll(postion + 1, cachedGoodsList);
                    workOrderUiList.add(postion + 1 + cachedGoodsList.size(), adapter.getAddServiceUIModel(productInfos.get(0).getProjectParent()));
                    adapter.notifyItemRangeInserted(postion + 1, cachedGoodsList.size() + 1);
                    adapter.onItemeSelect(productInfos.get(0).getProjectParent(), ChooseServiceTab.TAB_GOODS);
                } else {//添加服务大项
                    ProductInfo productInfo = productInfos.get(0);
                    int removeCount = adapter.removeExpandItems(productInfo.getProjectParent());//移除所有展开内容 并获取移除数量
                    if (removeCount > 0) {
                        adapter.notifyDataSetChanged();
                    }
                    List<CustomerRepUiModel> selectGoodsList = presenter.getUiModelFromProducts(productInfos);
                    workOrderUiList.add(presenter.getUIModelFromProduct(productInfos.get(0), ChooseServiceTab.TAB_GOODS));
                    adapter.putGoodsListIntoCache(productInfo.getProjectParent(), selectGoodsList);
                    adapter.notifyItemInserted(workOrderUiList.size() - 1);
                    adapter.inserChangeTabAtPostion(ChooseServiceTab.TAB_GOODS, workOrderUiList.size(), productInfo.getProjectParent());
                    if (adapter.getServiceTypeItemCount() >= serviceTypeCount) {
                        rlAddServer.setVisibility(View.GONE);
                    }
                    adapter.onItemeSelect(productInfo.getProjectParent(), ChooseServiceTab.TAB_GOODS);
                }
            }
        }
    }

    private void initView() {
        topbar.setTttleText("客户接待")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        edtCarnumInput.setOnEditorActionListener(this);
        edtCarnumInput.setText("");
        adapter = new CustomerRepAdapter(mContext, workOrderUiList);
        // adapter.setOnProjectTypeClick(this);
        ryChoiceServer.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        ryChoiceServer.setItemAnimator(new DefaultItemAnimator());
        ryChoiceServer.setAdapter(adapter);
        adapter.setTabclickListener(this);
        edtCarnumInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cph = s.toString().toUpperCase();
                if (StringUtil.isCarNum(cph) && textWatcherEnable) {
                    carNumber = cph;
                    presenter.getScanCustomerInfo(carNumber);
                }
            }
        });
//        车辆图片的点击事件
        imgCarPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carBookingInfo == null || carBookingInfo.getMakeInfo() == null || carBookingInfo.getCarInfo() == null)
                    return;
                AppUtility.showBigImage(mContext, FileUtil.getFileUrl(carBookingInfo.getCarInfo().getPicPath()));
            }
        });
    }

    /**
     * 扫描车牌信息接口回调后更新预约栏view
     *
     * @author zhangsan
     * @date 16/11/2 下午2:03
     */
    private void updateView() {
        if (carBookingInfo == null) {
            llAppointInfo.setVisibility(View.GONE);
            tvCostomerInfo.setText("非会员");
            tvCostomerInfo.setVisibility(View.VISIBLE);
            edtAppointePhone.setText("");
        } else if (carBookingInfo.getMakeInfo() != null) {
            if (AppUtility.isNotEmpty(carBookingInfo.getMakeInfo().getMakeNum()))
                llAppointInfo.setVisibility(View.VISIBLE);
            tvCostomerInfo.setText("客户信息");
            tvCostomerInfo.setVisibility(View.VISIBLE);
            tvCarPlatenum.setText(carBookingInfo.getCarInfo().getCarAllName());
            tvCarOwner.setText(carBookingInfo.getCustomerUser().getNickName());
            tvRemarks.setText("客户备注:" + carBookingInfo.getMakeInfo().getRemark());
            //String text = getString(R.string.arrive_time, StringUtil.getTimeFromDate(carBookingInfo.getMakeInfo().getPredictShopTime()));
            SpannableStringBuilder spb = new SpannableStringBuilder("预计");
            spb.append(StringUtil.getTimeFromDate(carBookingInfo.getMakeInfo().getPredictShopTime()));
            spb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.text_fleet_orange)),
                    2, spb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spb.append("点到店");
            tvArriveTime.setText(spb);
            edtAppointePhone.setText(carBookingInfo.getCustomerUser().getLoginName());
            Glide.with(CustomerReceptionActivity.this)
                    .load(FileUtil.getFileUrl(carBookingInfo.getCarInfo().getPicPath()))
                    .error(R.drawable.default_imge_small)
                    .into(imgCarPhoto);
            creatServiceLables(carBookingInfo.getMakeInfo().getWorkOrderInfoList());
            adapter.buidBaughtGoods(carBookingInfo.getMakeInfo().getWorkOrderInfoList());
        }
    }

    /**
     * 根据预约工单服务项初始化服务标签
     *
     * @author zhangsan
     * @date 16/10/19 下午2:31
     */
    @SuppressWarnings("ResourceType")
    private void creatServiceLables(List<WorkOrderInfo> workOrderInfos) {
        lablesService.removeAllViews();
        // ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
        //      .LayoutParams.WRAP_CONTENT);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // lp.leftMargin=8;
        // lp.topMargin=8;
        lp.setMargins(10, 10, 0, 0);
        for (WorkOrderInfo info : workOrderInfos) {
            TextView view = new TextView(mContext);
            view.setText(info.getProjectName());
            view.setTextColor(Color.WHITE);
            view.setTextSize(12);
            view.setLayoutParams(lp);
            view.setBackgroundResource(R.drawable.shape_text_station_label);
            lablesService.addView(view);
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
        startActivityForResult(video_intent, REQ_REC_PLATENUM);
        textWatcherEnable = false;
    }

    /**
     * 提交工单信息
     *
     * @author zhangsan
     * @date 16/11/2 下午2:03
     */
    private void submitWorkOrder() {
       /* if (null == carBookingInfo || null == carBookingInfo.getMakeInfo()) {
            showToast("请完善服务信息!");
            return;
        }*/
        workOrderSubmitInfo.makeNum = carBookingInfo != null && carBookingInfo.getMakeInfo() != null ? carBookingInfo.getMakeInfo().getMakeNum() : "";//预约编号
        workOrderSubmitInfo.remark = edtWriteMark.getText().toString();//服务备注字段
        workOrderSubmitInfo.carAllName = TextUtils.isEmpty(edtCarnumInput.getText().toString()) ? "" : edtCarnumInput.getText().toString().toUpperCase();//号牌字段
        workOrderSubmitInfo.customerUserNum = carBookingInfo != null && carBookingInfo.getCustomerUser() != null ? carBookingInfo.getCustomerUser().getUserNum() : "";//司机编号
        workOrderSubmitInfo.workOrderList = adapter.buildWorkOrderParams();
        workOrderSubmitInfo.phone = edtAppointePhone.getText().toString();
        if (workOrderSubmitInfo.workOrderList.isEmpty()) {
            showToast("至少选择一个服务项");
            return;
        }
        String canSubmit = isCanSubmit();
        if (canSubmit.equals(LEADING_USER_NUM_IS_EMPTY)) {
            AppUtility.showToastMsg(DbHelper.getInstance().getServiceTypeName(workOrderListInfo.projectNum) + " 服务技师不能为空");
            return;
        } else if (canSubmit.equals(WORKBAY_INFO_NUM_ISEMPTY)) {
            AppUtility.showToastMsg(DbHelper.getInstance().getServiceTypeName(workOrderListInfo.projectNum) + " 工位不能为空");
            return;
        } else if (canSubmit.equals(WORK_ORDER_GOODS_LIST)) {   // 商品不能为 空
            AppUtility.showToastMsg(DbHelper.getInstance().getServiceTypeName(workOrderListInfo.projectNum) + " 商品不能为空");
            return;
        }
        if (!StringUtil.isCarNum(workOrderSubmitInfo.carAllName)) {
            AppUtility.showToastMsg("车牌号不合法");
            return;
        }
        String paramsResult = new Gson().toJson(workOrderSubmitInfo);
        LogX.d("retrofit", "==商品===" + paramsResult);
        presenter.submitWorkOrder(paramsResult);
    }

    /**
     * 记录不正确的工单信息
     */
    private WorkOrderSubmitInfo.WorkOrderListInfo workOrderListInfo;//记录不正确的工单信息

    /**
     * 检查同一工单  技师 和 工位 是否同时存在
     * <p>
     * #  LEADING_USER_NUM_IS_EMPTY   -  服务技师是空的
     * #  WORKBAY_INFO_NUM_ISEMPTY  -  服务工位是空的
     * #  WORK_ORDER_GOODS_LIST  -  服务商品是空的}
     *
     * @return
     */
    private String isCanSubmit() {
        String canSubmit = CANSUBMIT;
        // 遍历工单列表  只要存在 只有技师， 或者只有工位的情况  不允许提交
        for (int i = 0; i < workOrderSubmitInfo.workOrderList.size(); i++) {
            workOrderListInfo = workOrderSubmitInfo.workOrderList.get(i);
            if ((workOrderListInfo.leadingUserNum.isEmpty() && !workOrderListInfo.workbayInfoNum.isEmpty())) {
                canSubmit = LEADING_USER_NUM_IS_EMPTY;
                break;
            } else if (!workOrderListInfo.leadingUserNum.isEmpty() && workOrderListInfo.workbayInfoNum.isEmpty()) {
                canSubmit = WORKBAY_INFO_NUM_ISEMPTY;
                break;
            } else if (workOrderListInfo.workOrderGoodsList.isEmpty()) {
                canSubmit = WORK_ORDER_GOODS_LIST;
                break;
            }
        }
        return canSubmit;
    }

    /**
     * 清空上个tab加载的数据
     *
     * @author zhangsan
     * @date 16/10/24 下午6:02
     */
    private void clearLastTabItems(int relataedPostion) {
        //如果是empty 添加服务商品  直接 return
//        if (relataedPostion < 0) return;
        int removeCount = adapter.removeItemsExceptTab();
        if (removeCount > 0)
            adapter.notifyItemRangeRemoved(relataedPostion + 1, removeCount);
    }

    @OnClick({R.id.tv_costomer_info, R.id.bt_submit, R.id.rl_add_server, R.id.img_btn_scan, R.id.ll_upkeep})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_costomer_info://客户信息
                if (carBookingInfo != null && carBookingInfo.getCustomerUser() != null) {
                    AppUtility.goToUserProfile(mContext, carBookingInfo.getCustomerUser());
                }
                break;
            case R.id.bt_submit://提交工单
                submitWorkOrder();
                break;
            case R.id.rl_add_server://添加服务大项
                Intent intent = new Intent(mContext, AddServiceGoodsActivity.class);
                intent.putStringArrayListExtra(C.IntentKey.PROJECT_NUMS, adapter.getCurrentProjectNums());
                showActivity(intent);
                break;
            case R.id.img_btn_scan://车牌扫描
                showVideoRecPlateNum();
                break;
            case R.id.ll_upkeep://保养里程数
                String carNum = edtCarnumInput.getText().toString();
                carNum = carNum.toUpperCase();
                if (TextUtils.isEmpty(carNum)) {
                    AppUtility.showToastMsg("请先输入车牌号！");
                    return;
                } else if (!StringUtil.isCarNum(carNum)) {
                    AppUtility.showToastMsg("车牌号不合法");
                    return;
                }
                Intent i = new Intent(mContext, UpdateNickNameActivity.class);
                i.putExtra(C.IntentKey.TOPBAR_TITLE, UpdateNickNameActivity.STRING_UPKEEP);
                startActivityForResult(i, REQ_ADD_UPKEEP);
                break;
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left:
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
     * 扫描车牌后获取预约和客户信息回调
     *
     * @author zhangsan
     * @date 16/11/4 下午2:24
     */
    @Override
    public void onScanCarBookingSuccess(CarBookingInfo carBookingInfo) {
        this.carBookingInfo = carBookingInfo;
        updateView();
        AppointmentInfo makeInfo = carBookingInfo.getMakeInfo();
        if (makeInfo != null) {
            workOrderUiList.clear();
            workOrderUiList.addAll(presenter.getUiModelFromWorkOrder(makeInfo.getWorkOrderInfoList(), ChooseServiceTab.TAB_WORK_STATION));
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onScanCarBookingFail() {
        this.carBookingInfo = null;
        updateView();
        workOrderUiList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onScanCarBookingResult() {
        textWatcherEnable = true;
    }

    /**
     * 点击添加商品标签回调
     *
     * @author zhangsan
     * @date 16/10/26 上午11:22
     */
    @Override
    public void onNotSpendingServiceResult(String projectNum, List<CustomerRepUiModel> goodsInfos, int relataedPostion) {
        clearLastTabItems(relataedPostion);
        if (!goodsInfos.isEmpty()) {
            workOrderUiList.addAll(relataedPostion + 1, goodsInfos);
            workOrderUiList.add(relataedPostion + 1 + goodsInfos.size(), adapter.getAddServiceUIModel(projectNum));
            adapter.notifyItemRangeInserted(relataedPostion + 1, goodsInfos.size() + 1);
        } else {
            adapter.inserEmptyItemAtPostion(relataedPostion + 1, ChooseServiceTab.TAB_GOODS, projectNum);
        }
    }

    //relataedPostion 切换标签所在的position
    @Override
    public void onFreeWorkbayResult(String projectNum, List<WorkBayInfo> workBayInfos, int relataedPostion) {
        clearLastTabItems(relataedPostion);
        if (!workBayInfos.isEmpty()) {
            adapter.setWorkOrderSelection(-1);
            workOrderUiList.addAll(relataedPostion + 1, presenter.getUiModelFromWorkBay(workBayInfos, projectNum));
            adapter.notifyItemRangeInserted(relataedPostion + 1, workBayInfos.size());
            adapter.indexOfSecletModel(projectNum, ChooseServiceTab.TAB_WORK_STATION);
        } else {
            adapter.inserEmptyItemAtPostion(relataedPostion + 1, ChooseServiceTab.TAB_WORK_STATION, projectNum);
        }
    }

    /**
     * 获取空闲技师结果回调
     *
     * @author zhangsan
     * @date 16/11/4 上午10:02
     */
    @Override
    public void onFreeTechnicanResult(String projectNum, List<User> result, int relataedPostion) {
        clearLastTabItems(relataedPostion);
        if (!result.isEmpty()) {
            adapter.setTechnicianSelection(-1);//重置显示选中项
            workOrderUiList.addAll(relataedPostion + 1, presenter.getUiModelFromTechnianUser(result, projectNum));
            adapter.notifyItemRangeInserted(relataedPostion + 1, result.size());
            adapter.indexOfSecletModel(projectNum, ChooseServiceTab.TAB_TECHNICIAN);
        } else {
            adapter.inserEmptyItemAtPostion(relataedPostion + 1, ChooseServiceTab.TAB_TECHNICIAN, projectNum);
        }
    }

    /**
     * 提交工单成功回调
     *
     * @author zhangsan
     * @date 16/10/28 下午7:11
     */
    @Override
    public void submitWorkOrderSuccess() {
        finish();
    }

    /**
     * 修改保养里程数成功
     */
    @Override
    public void saveCarMileageSuccess() {
        tvUpkeep.setText(upkeep);
    }


    /**
     * 列表切换标签点击回调
     *
     * @author zhangsan
     * @date 16/10/12 下午2:16
     */
    @Override
    public void onTabClick(int tabType, int relataedPostion, String relatedProjectNum) {
        switch (tabType) {
            case ChooseServiceTab.TAB_GOODS://选择服务商品
                //presenter.getBaughtGoods(carBookingInfo.getCustomerUser().getUserNum(), relatedProjectNum, relataedPostion);
                onNotSpendingServiceResult(relatedProjectNum, adapter.getGoodsListByProjectType(relatedProjectNum), relataedPostion);
                break;
            case ChooseServiceTab.TAB_TECHNICIAN://选择技师
                presenter.getFreeTechnian(relatedProjectNum, relataedPostion);
                break;
            case ChooseServiceTab.TAB_WORK_STATION://选择工位
                presenter.getFreeWorkStation(relatedProjectNum, relataedPostion);
                break;
        }
        if (relataedPostion > 0 && relataedPostion < workOrderUiList.size()) {
            CustomerRepUiModel customerRepUiModel = workOrderUiList.get(relataedPostion - 1);
            if (customerRepUiModel.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE)
                customerRepUiModel.setSelecTab(tabType);
        }
    }

    /**
     * 输入框输入法监听回调
     *
     * @author zhangsan
     * @date 16/11/4 下午2:18
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {//按下输入法发送
            if (!TextUtils.isEmpty(edtCarnumInput.getText().toString())) {
                presenter.getScanCustomerInfo(edtCarnumInput.getText().toString());
            }
        }
        return false;
    }


}
