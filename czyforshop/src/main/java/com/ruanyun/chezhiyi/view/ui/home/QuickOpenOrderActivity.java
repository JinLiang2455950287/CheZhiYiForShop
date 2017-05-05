package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.CustomerRepUiModel;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.GongWeiJiShiBean;
import com.ruanyun.chezhiyi.commonlib.model.KaijieOpenOrderGoods;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.CarMileageParams;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.CustomerRepPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.CloseKeyBoard;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressImageTask;
import com.ruanyun.chezhiyi.commonlib.util.compressimage.CompressTaskCallback;
import com.ruanyun.chezhiyi.commonlib.view.CustomerRepMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CustomExpandableListView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.adapter.MyExpandableListPaiGongAdapter;
import com.ruanyun.chezhiyi.view.widget.ChooseServiceTab;
import com.ruanyun.imagepicker.bean.CompressImageInfoGetter;
import com.ruanyun.imagepicker.bean.ImageItem;
import com.ruanyun.imagepicker.widget.RYAddPictureView;
import com.wintone.demo.plateid.MemoryCameraActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.ruanyun.chezhiyi.R.id.edt_carnum_input;

public class QuickOpenOrderActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, CustomerRepMvpView, MyExpandableListPaiGongAdapter.OnPaiGongClickListener
        , MyExpandableListPaiGongAdapter.OnBuyCountClickListener, RYAddPictureView.onPickResultChangedListener {
    public static final int REQ_REC_PLATENUM = 32434;//获取车牌扫描结果
    public static final int REQ_ADD_UPKEEP = 2345;//添加里程数

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edt_appointe_phone)
    TextView edtAppointePhone;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(edt_carnum_input)
    TextView edtCarnumInput;
    @BindView(R.id.tv_costomer_info)
    TextView tvCostomerInfo;
    @BindView(R.id.edt_write_mark)
    EditText edtWriteMark;
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
    @BindView(R.id.grid_case)
    RYAddPictureView gridCase;

    private CarBookingInfo carBookingInfo;//当前预约客户信息
    private CustomerRepPresenter presenter = new CustomerRepPresenter();
    private List<CustomerRepUiModel> workOrderUiList = new ArrayList<>();

    private int serviceTypeCount = 0;//服务分类数量
    private CarMileageParams params;// 修改最新里程数 参数
    private String upkeep;//  修改最新里程数
    private String carNumber;// 当前的车牌号
    private String plateNumber;
    private boolean textWatcherEnable = true;
    //图片
    private List<ImageItem> addItemList;

    //View
    private CustomExpandableListView expandableListView;

    //Model：定义的数据
    private List<WorkOrderInfo> groups = new ArrayList<>();
    public Map<String, List<OrderGoodsInfo>> childs = new HashMap<>();
    private MyExpandableListPaiGongAdapter myExpandableAdapter;
    private List<ProductInfo> productInfoHuiChuanList = new ArrayList<>();
    private GongWeiJiShiBean gongWeiJiShiBean = new GongWeiJiShiBean();
    List<ProjectType> stairprojectTypes = new ArrayList<>();//一级工单服务分类集合
    private int[] delAttachInfoId = null;
    private int index = 0;
    private CompressImageTask imageTask;
    private HashMap<String, RequestBody> caseMapParams = new HashMap<>();

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
                            params.setCarAllName(edtCarnumInput.getText().toString().trim());
                            params.setCarMileage(upkeep);
                            presenter.saveCarMileage(app.getApiService().saveCarMileage(app.getCurrentUserNum(), params));
                        }
                    }
                    break;
            }

            if (requestCode == gridCase.requestCode) {
                gridCase.onImageActivityResult();
            }

        } else if (resultCode == RESULT_FIRST_USER) {
            switch (requestCode) {
                case REQ_REC_PLATENUM://车牌识别页面返回 没有扫码
                    textWatcherEnable = true;
            }
        }

        /*添加服务商品*/
        if (requestCode == 1522) {
            ProductInfo infoTemp;//传过来的商品bean
            OrderGoodsInfo goodsInfoTemp;//本地的商品bean
            WorkOrderInfo workOrderInfoTemp;//本地的商品一级目录bean
            List<OrderGoodsInfo> childsListTemp;//本地的商品bean集合
            productInfoHuiChuanList.clear();
            productInfoHuiChuanList = (ArrayList<ProductInfo>) data.getSerializableExtra("1522");
            LogX.e("1522getAll", productInfoHuiChuanList.size() + ";" + productInfoHuiChuanList.toString());

            for (int i = 0; i < productInfoHuiChuanList.size(); i++) {
                infoTemp = productInfoHuiChuanList.get(i);
                LogX.e("1522getiteminfoTemp", infoTemp.getProjectNum());
                if (childs.get(infoTemp.getProjectNum()) != null) {      //判断是否添加过的项目
                    childsListTemp = childs.get(infoTemp.getProjectNum());
                    goodsInfoTemp = new OrderGoodsInfo();
                    goodsInfoTemp.setGoodsName(infoTemp.getGoodsName());
                    goodsInfoTemp.setGoodsNum(infoTemp.getGoodsNum());
                    goodsInfoTemp.setGoodsCount(infoTemp.getGoodsCount());
                    goodsInfoTemp.setMainPhoto(infoTemp.getMainPhoto());
                    goodsInfoTemp.setOrderGoodsDetailNum(infoTemp.getProductSpecificat());
                    goodsInfoTemp.setSgtcAmount(infoTemp.getSgtcje().doubleValue());
                    goodsInfoTemp.setAmount(new BigDecimal(infoTemp.getSalePrice()));
                    goodsInfoTemp.setIsDaiXiaDan(1);
                    goodsInfoTemp.setXstcAmount(infoTemp.getXstcje().doubleValue());
                    LogX.e("1522goodsInfoTempif", goodsInfoTemp.toString());
                    childsListTemp.add(goodsInfoTemp);

                } else {
                    goodsInfoTemp = new OrderGoodsInfo();
                    childsListTemp = new ArrayList<>();
                    goodsInfoTemp.setGoodsName(infoTemp.getGoodsName());
                    goodsInfoTemp.setGoodsNum(infoTemp.getGoodsNum());
                    goodsInfoTemp.setGoodsCount(infoTemp.getGoodsCount());
                    goodsInfoTemp.setMainPhoto(infoTemp.getMainPhoto());
                    goodsInfoTemp.setOrderGoodsDetailNum(infoTemp.getProductSpecificat());
                    goodsInfoTemp.setSgtcAmount(infoTemp.getSgtcje().doubleValue());
                    goodsInfoTemp.setAmount(new BigDecimal(infoTemp.getSalePrice()));
                    goodsInfoTemp.setIsDaiXiaDan(1);
                    goodsInfoTemp.setXstcAmount(infoTemp.getXstcje().doubleValue());
                    LogX.e("1522infoTemp", infoTemp.toString());
                    LogX.e("1522goodsInfoTempNew", goodsInfoTemp.toString());
                    childsListTemp.add(goodsInfoTemp);
                    childs.put(productInfoHuiChuanList.get(i).getProjectNum(), childsListTemp);

                    //一级目录
                    workOrderInfoTemp = new WorkOrderInfo();
                    if (infoTemp.getProjectNum().equals("004000000000000")) {
                        workOrderInfoTemp.setProjectName("机修");
                    } else if (infoTemp.getProjectNum().equals("002000000000000")) {
                        workOrderInfoTemp.setProjectName("常规");
                    } else if (infoTemp.getProjectNum().equals("003000000000000")) {
                        workOrderInfoTemp.setProjectName("保养");
                    } else if (infoTemp.getProjectNum().equals("005000000000000")) {
                        workOrderInfoTemp.setProjectName("美容");
                    } else if (infoTemp.getProjectNum().equals("006000000000000")) {
                        workOrderInfoTemp.setProjectName("轮胎");
                    }
                    workOrderInfoTemp.setProjectNum(infoTemp.getProjectNum());

                    groups.add(workOrderInfoTemp);
                }
            }

            LogX.e("1522MapNew", childs.size() + childs.toString());

            for (Map.Entry<String, List<OrderGoodsInfo>> entry : childs.entrySet()) {
                String key = entry.getKey();
                List<OrderGoodsInfo> listTemp = childs.get(key);
                for (int i = 0; i < listTemp.size() - 1; i++) {
                    for (int j = i + 1; j < listTemp.size(); j++) {
                        if (listTemp.get(i).getIsDaiXiaDan() == 1 && listTemp.get(i).getGoodsNum().equals(listTemp.get(j).getGoodsNum()) && listTemp.get(j).getIsDaiXiaDan() == 1) {
                            listTemp.get(i).setGoodsCount(listTemp.get(j).getGoodsCount());
                            listTemp.remove(j);
                        }
                    }
                }
            }


            for (int j = 0; j < groups.size(); j++) {
                String projectNumber = groups.get(j).getProjectNum();
                for (int i = 0; i < stairprojectTypes.size(); i++) {
                    if (stairprojectTypes.get(i).getProjectNum().equals(projectNumber)) {
                        groups.get(j).setIsWorkBbay(stairprojectTypes.get(i).getIsWorkBay());
                    }
                }
            }

            myExpandableAdapter.setData(groups, childs);
            myExpandableAdapter.notifyDataSetChanged();

            LogX.e("1522MapNew", groups.size() + "==" + childs.size() + "==" + groups.toString() + "==" + childs.toString());
        }

       /*工位/技师*/
        if (requestCode == 1544) {
            gongWeiJiShiBean = (GongWeiJiShiBean) data.getParcelableExtra("gongWeiJiShiBean");
            if (gongWeiJiShiBean != null) {
                for (int i = 0; i < groups.size(); i++) {
                    if (groups.get(i).getProjectNum().equals(gongWeiJiShiBean.getProjectNumber())) {
                        groups.get(i).setLeadingUserName(gongWeiJiShiBean.getJishiname());
                        groups.get(i).setLeadingUserNum(gongWeiJiShiBean.getJishiid());
                        groups.get(i).setWorkbayName(gongWeiJiShiBean.getGongweiname());
                        groups.get(i).setWorkbayInfoNum(gongWeiJiShiBean.getGongweiid());
                        if (gongWeiJiShiBean.getGongweiname() != null || gongWeiJiShiBean.getJishiname() != null) {
                            groups.get(i).setRemark("技师：" + gongWeiJiShiBean.getJishiname() + " 工位：" + gongWeiJiShiBean.getGongweiname());
                        }

                        LogX.e("15444", "message ：" + "技师：" + gongWeiJiShiBean.getJishiname() + " 工位：" + gongWeiJiShiBean.getGongweiname());
                    }
                }
            }

            LogX.e("15444set", groups.toString() + "groups");
            myExpandableAdapter.setData(groups, childs);
            myExpandableAdapter.notifyDataSetChanged();

        }

    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_queck_open_order);
        ButterKnife.bind(this);
        initView();
        presenter.attachView(this);
        serviceTypeCount = DbHelper.getInstance().getServiceTypeCount();
        stairprojectTypes = DbHelper.getInstance().getSeviceTypes();//获取工单服务和技师技能数据结构一级
        initExpandListView();
    }

    private void initView() {
        topbar.setTttleText("快捷开单")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
            /*获取开单界面传过来的车牌号码*/
        plateNumber = getIntent().getStringExtra("plateNumber");
        edtCarnumInput.setText(plateNumber);
        String cph = plateNumber.toUpperCase();
        if (StringUtil.isCarNum(cph) && textWatcherEnable) {
            carNumber = cph;
            presenter.getScanCustomerInfo(carNumber);
        }
        gridCase.setOnListeners();
        gridCase.setSizeLimit(5);
    }

    private void initExpandListView() {
        expandableListView = (CustomExpandableListView) findViewById(R.id.expandableListView);
        myExpandableAdapter = new MyExpandableListPaiGongAdapter(mContext, groups, childs);
        expandableListView.setGroupIndicator(null);
        myExpandableAdapter.setPaiGongClickListener(this);
        myExpandableAdapter.setOnBuyCountClickListener(this);
        expandableListView.setAdapter(myExpandableAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
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
            tvCostomerInfo.setText("详情");
            tvCostomerInfo.setVisibility(View.VISIBLE);
            tvRemarks.setText(carBookingInfo.getMakeInfo().getRemark());
            tvArriveTime.setText(carBookingInfo.getMakeInfo().getPredictShopTime());
            edtAppointePhone.setText(carBookingInfo.getCustomerUser().getLoginName());
            if (AppUtility.isNotEmpty(carBookingInfo.getMakeInfo().getMakeNum())) {
                List<ProjectType> projectTypes = new Gson().fromJson(carBookingInfo.getMakeInfo().getProjectNum(), new TypeToken<List<ProjectType>>() {
                }.getType());
                LogX.e("根据预约工单服务项初始化服务标签", carBookingInfo.getMakeInfo().getProjectNum());
                LogX.e("根据预约工单服务项初始化服务标签", projectTypes.toString());
                creatServiceLables(projectTypes);
            }
        }
    }

    /**
     * 根据预约工单服务项初始化服务标签
     *
     * @author zhangsan
     * @date 16/10/19 下午2:31
     */
    @SuppressWarnings("ResourceType")
    private void creatServiceLables(List<ProjectType> projectTypes) {
        LogX.e("服务标签", projectTypes.toString());
        lablesService.removeAllViews();
        // ViewGroup.LayoutParams source = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
        //      .LayoutParams.WRAP_CONTENT);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // lp.leftMargin=8;
        // lp.topMargin=8;
        lp.setMargins(10, 10, 0, 0);
        for (ProjectType info : projectTypes) {
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
     */
    private void submitWorkOrder() {
        WorkOrderSubmitInfo workOrderSubmitInfo = new WorkOrderSubmitInfo();//提交表单的实体类
        workOrderSubmitInfo.makeNum = carBookingInfo != null && carBookingInfo.getMakeInfo() != null ? carBookingInfo.getMakeInfo().getMakeNum() : "";//预约编号
        workOrderSubmitInfo.remark = edtWriteMark.getText().toString();//服务备注字段
        workOrderSubmitInfo.carAllName = TextUtils.isEmpty(edtCarnumInput.getText().toString()) ? "" : edtCarnumInput.getText().toString().toUpperCase();//号牌字段
        workOrderSubmitInfo.customerUserNum = carBookingInfo != null && carBookingInfo.getCustomerUser() != null ? carBookingInfo.getCustomerUser().getUserNum() : "";//司机编号
        workOrderSubmitInfo.phone = edtAppointePhone.getText().toString();

        if (!StringUtil.isCarNum(workOrderSubmitInfo.carAllName)) {
            AppUtility.showToastMsg("车牌号不合法");
            return;
        }

        WorkOrderSubmitInfo.WorkOrderListInfo workOrderinfo;//服务商品列表
        WorkOrderSubmitInfo.WorkOrderGoods workOrderGoods;//服务商品
        List<WorkOrderSubmitInfo.WorkOrderGoods> workOrderGoodsList = new ArrayList<>();
        List<OrderGoodsInfo> childsListsubTemp;
        List<WorkOrderSubmitInfo.WorkOrderListInfo> workOrderList = new ArrayList<>();//提交表单的List
        LogX.e("15444sub", childs.toString() + "childs");
        for (int i = 0; i < groups.size(); i++) {
            WorkOrderInfo WorkOrderInfo = groups.get(i);
            childsListsubTemp = childs.get(WorkOrderInfo.getProjectNum());
            for (int j = 0; j < childsListsubTemp.size(); j++) {
                workOrderGoods = new WorkOrderSubmitInfo.WorkOrderGoods();
                OrderGoodsInfo orderGoodsInfo = childsListsubTemp.get(j);
                workOrderGoods.goodsName = orderGoodsInfo.getGoodsName();
                workOrderGoods.goodsNum = orderGoodsInfo.getGoodsNum();
                workOrderGoods.goodsTotalCount = orderGoodsInfo.getGoodsCount();
                workOrderGoods.mainPhoto = orderGoodsInfo.getMainPhoto();
                workOrderGoods.sgtcAmount = orderGoodsInfo.getSgtcAmount() + "";
                workOrderGoods.singlePrice = orderGoodsInfo.getAmount() + "";
                workOrderGoods.xstcAmount = orderGoodsInfo.getXstcAmount() + "";
                if (orderGoodsInfo.isService()) {
                    workOrderGoodsList.add(workOrderGoods);
                }
            }
            if (workOrderGoodsList.size() > 0) {
                workOrderinfo = new WorkOrderSubmitInfo.WorkOrderListInfo();
                workOrderinfo.leadingUserName = WorkOrderInfo.getLeadingUserName() != null ? WorkOrderInfo.getLeadingUserName() : "";
                workOrderinfo.leadingUserNum = WorkOrderInfo.getLeadingUserNum() != null ? WorkOrderInfo.getLeadingUserNum() : "";
                workOrderinfo.projectNum = WorkOrderInfo.getProjectNum();
                workOrderinfo.workOrderNum = " ";
                workOrderinfo.workbayInfoNum = WorkOrderInfo.getWorkbayInfoNum() != null ? WorkOrderInfo.getWorkbayInfoNum() : "";
                workOrderinfo.workbayName = WorkOrderInfo.getWorkbayName() != null ? WorkOrderInfo.getWorkbayName() : "";
                workOrderinfo.workOrderGoodsList = workOrderGoodsList;
                workOrderList.add(workOrderinfo);
            }

        }

        if (workOrderList.size() <= 0) {
            showToast("至少选择一个服务项");
            return;
        }
        workOrderSubmitInfo.workOrderList = workOrderList;
        final String paramsResult = new Gson().toJson(workOrderSubmitInfo);
        LogX.e("retrofit3", paramsResult);
        caseMapParams.put("resultJosnString", RequestBody.create(MediaType.parse("text/plain"), paramsResult));
        showLoading("开单中...");

        //图片
        addItemList = getLocalPicParams(gridCase.getImageList());
        if (addItemList.size() > 0) {
            imageTask = App.getInstance().imageProxyService.getCompressTask("workOrderPic", (CompressImageInfoGetter[]) addItemList.toArray(new ImageItem[0]));

            imageTask.start(new CompressTaskCallback<HashMap<String, RequestBody>>() {
                @Override
                public void onCompresComplete(HashMap<String, RequestBody> compressResults) {
                    caseMapParams.putAll(compressResults);
                    presenter.submitWorkOrder2(caseMapParams);
                }

                @Override
                public void onCompresFail(Throwable throwable) {
                }
            });
        } else {
            presenter.submitWorkOrder2(caseMapParams);
        }
    }

    @OnClick({R.id.tv_costomer_info, R.id.bt_submit, R.id.rl_add_server2, R.id.img_btn_scan, R.id.edt_carnum_input})
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
            case R.id.rl_add_server2://添加商品2
//                Intent intent = new Intent(mContext, AddServiceGoodsActivity.class);
//                intent.putStringArrayListExtra(C.IntentKey.PROJECT_NUMS, adapter.getCurrentProjectNums());
//                showActivity(intent);
                Intent intent2 = new Intent(mContext, AddServiceGoodsActivity2.class);
                startActivityForResult(intent2, 1522);
                break;
            case R.id.img_btn_scan://车牌扫描
                showVideoRecPlateNum();
                break;
            case R.id.edt_carnum_input://点击号牌返回号牌输入界面
                skipActivity(OpenOrderActivity.class);
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
     * 获取服务项目
     *
     * @author zhangsan
     * @date 16/11/4 下午2:24
     */
    @Override
    public void onScanCarBookingSuccess(CarBookingInfo carBookingInfo) {
        LogX.e("服务标签", carBookingInfo.toString());
        this.carBookingInfo = carBookingInfo;
        updateView();
        AppointmentInfo makeInfo = carBookingInfo.getMakeInfo();
        if (makeInfo != null) {
            workOrderUiList.clear();
            workOrderUiList.addAll(presenter.getUiModelFromWorkOrder(makeInfo.getWorkOrderInfoList(), ChooseServiceTab.TAB_WORK_STATION));
        }
        groups = carBookingInfo.getMakeInfo().getWorkOrderInfoList();
        if (groups.size() > 0) {
            for (int i = 0; i < groups.size(); i++) {
                for (int j = 0; j < stairprojectTypes.size(); j++) {
                    if (groups.get(i).getProjectNum().equals(stairprojectTypes.get(j).getProjectNum())) {
                        groups.get(i).setIsWorkBbay(stairprojectTypes.get(j).getIsWorkBay());
                    }
                }

            }
        }

        LogX.e("服务项目groups", groups.toString());
        for (int i = 0; i < groups.size(); i++) {
            childs.put(groups.get(i).getProjectNum(), groups.get(i).getOrderGoodsDetailList());
        }
        LogX.e("服务项目child", childs.toString());
        LogX.e("1522Map", childs.size() + "==" + childs.toString());
        myExpandableAdapter.setData(groups, childs);
        myExpandableAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScanCarBookingFail() {
        this.carBookingInfo = null;
        updateView();
        workOrderUiList.clear();
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
    }

    //relataedPostion 切换标签所在的position
    @Override
    public void onFreeWorkbayResult(String projectNum, List<WorkBayInfo> workBayInfos, int relataedPostion) {

    }

    /**
     * 获取空闲技师结果回调
     *
     * @author zhangsan
     * @date 16/11/4 上午10:02
     */
    @Override
    public void onFreeTechnicanResult(String projectNum, List<User> result, int relataedPostion) {

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
        addItemList.clear();
    }

    /**
     * 修改保养里程数成功
     */
    @Override
    public void saveCarMileageSuccess() {
    }

    /*派工按钮*/
    @Override
    public void onProductBuyItemClick(String project, int isWorkBbay) {
        //isWorkBbay 是否需要工位 1：是 2： 否
        Intent intent = new Intent(mContext, OperOrderPaiGongActivity.class);
        intent.putExtra("projectNumber", project);
        intent.putExtra("isWorkBay", isWorkBbay);
        startActivityForResult(intent, 1544);
    }

    /*改变商品数量*/
    @Override
    public void onBuyCountItemClick(int count) {

    }

    /*图片选择器*/
    @Override
    public void onPicDelete(ImageItem item) {
        if (item.type == ImageItem.TYPE_REMOTE && delAttachInfoId != null) {
            // TODO: 2016/9/9 添加删除的图片的   id
            delAttachInfoId[index] = item.attachId;
            index++;
        }
    }

    /**
     * 获取新增图片
     *
     * @param imageList
     */
    private List<ImageItem> getLocalPicParams(List<ImageItem> imageList) {
        List<ImageItem> tempItemList = new ArrayList<>();
        for (ImageItem item : imageList) {
            if (item.type == ImageItem.TYPE_LOCAL && !item.isAdd) {
                // caseMapParams.put("attachInfoPic\"; filename=\"" + item.name, RequestBody.create(MediaType.parse("image/jpeg"), new File(item.path)));
                // TODO: 2016/12/1   添加图片压缩
                tempItemList.add(item);
            }
        }
        return tempItemList;
    }


}
