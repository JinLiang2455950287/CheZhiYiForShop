package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.CarBookingInfo;
import com.ruanyun.chezhiyi.commonlib.model.CustomerRepUiModel;
import com.ruanyun.chezhiyi.commonlib.model.Event;
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
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.CustomerRepMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CustomExpandableListView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.adapter.CustomerRepAdapter;
import com.ruanyun.chezhiyi.view.widget.ChooseServiceTab;
import com.wintone.demo.plateid.MemoryCameraActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.ruanyun.chezhiyi.R.id.edt_carnum_input;

public class QuickOpenOrderActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, CustomerRepMvpView {
    public static final int REQ_REC_PLATENUM = 32434;//获取车牌扫描结果
    public static final int REQ_ADD_UPKEEP = 2345;//添加里程数
    public static final String WORK_ORDER_GOODS_LIST = "workOrderGoodsList";
    public static final String LEADING_USER_NUM_IS_EMPTY = "leading_user_num_is_empty";
    public static final String WORKBAY_INFO_NUM_ISEMPTY = "workbay_info_num_isempty";
    public static final String CANSUBMIT = "cansubmit";

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


    private CarBookingInfo carBookingInfo;//当前预约客户信息
    private CustomerRepPresenter presenter = new CustomerRepPresenter();
    //    private CustomerRepAdapter adapter;
    private List<CustomerRepUiModel> workOrderUiList = new ArrayList<>();
    private WorkOrderSubmitInfo workOrderSubmitInfo = new WorkOrderSubmitInfo();
    private int serviceTypeCount = 0;//服务分类数量
    private CarMileageParams params;// 修改最新里程数 参数
    private String upkeep;//  修改最新里程数
    private String carNumber;// 当前的车牌号
    private String plateNumber;
    private boolean textWatcherEnable = true;

    //View
    private CustomExpandableListView expandableListView;

    //Model：定义的数据
    private List<WorkOrderInfo> groups = new ArrayList<>();
    public Map<String, List<OrderGoodsInfo>> childs = new HashMap<>();
    //    private KaijieOpenOrderGoods kaijieOpenOrderGoods = new KaijieOpenOrderGoods();
    private MyExpandableListView myExpandableAdapter;

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
        } else if (resultCode == RESULT_FIRST_USER) {
            switch (requestCode) {
                case REQ_REC_PLATENUM://车牌识别页面返回 没有扫码
                    textWatcherEnable = true;
            }

        }
        if (requestCode == 1522) {
            
        }

    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_queck_open_order);
        ButterKnife.bind(this);
        initView();
        presenter.attachView(this);
        registerBus();
        serviceTypeCount = DbHelper.getInstance().getServiceTypeCount();
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

    }

    private void initExpandListView() {
        expandableListView = (CustomExpandableListView) findViewById(R.id.expandableListView);
        myExpandableAdapter = new MyExpandableListView();
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(myExpandableAdapter);

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void getProductInfoCount(Event<KaijieOpenOrderGoods> event) {
        if (event != null && event.key.equals("KuaiJieKaiDan")) {
            LogX.e("KuaiJieKaiDan", event.value.toString());
        }
    }

    //为ExpandableListView自定义适配器
    class MyExpandableListView extends BaseExpandableListAdapter {

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return groups.size();
        }

        //返回每个二级列表的个数 List<Map<String,List<ProductInfo>>>
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            return childs.get(groups.get(groupPosition).getProjectNum()).size();
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        //返回二级列表中的单个item（返回的是对象）List<Map<String,List<ProductInfo>>>
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {   //List<Map<String,List<ProductInfo>>>
            return childPosition;
        }

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_group, null);
            } else {

            }
            TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group_title);
            TextView tv_group_pai = (TextView) convertView.findViewById(R.id.tv_group_pai);
            tv_group_pai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QuickOpenOrderActivity.this, OperOrderPaiGongActivity.class);
                    intent.putExtra("projectNumber", groups.get(groupPosition).getProjectNum() + "");
                    startActivity(intent);
                }
            });

            tv_group.setText(groups.get(groupPosition).getProjectName() + "");

            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_child, null);
            }

            TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);
            TextView tv_child_detail = (TextView) convertView.findViewById(R.id.tv_child_detail);
            tv_child.setText(childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getGoodsName());
            tv_child_detail.setText("¥ " + childs.get(groups.get(groupPosition).getProjectNum()).get(childPosition).getAmount() + "×1");
            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


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
     *
     * @author zhangsan
     * @date 16/11/2 下午2:03
     */
    private void submitWorkOrder() {
        workOrderSubmitInfo.makeNum = carBookingInfo != null && carBookingInfo.getMakeInfo() != null ? carBookingInfo.getMakeInfo().getMakeNum() : "";//预约编号
        workOrderSubmitInfo.remark = edtWriteMark.getText().toString();//服务备注字段
        workOrderSubmitInfo.carAllName = TextUtils.isEmpty(edtCarnumInput.getText().toString()) ? "" : edtCarnumInput.getText().toString().toUpperCase();//号牌字段
        workOrderSubmitInfo.customerUserNum = carBookingInfo != null && carBookingInfo.getCustomerUser() != null ? carBookingInfo.getCustomerUser().getUserNum() : "";//司机编号

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
        LogX.e("服务项目groups", groups.toString());
        for (int i = 0; i < groups.size(); i++) {
            childs.put(groups.get(i).getProjectNum(), groups.get(i).getOrderGoodsDetailList());
        }
        LogX.e("服务项目child", childs.toString());
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
        clearLastTabItems(relataedPostion);
        if (!goodsInfos.isEmpty()) {
            workOrderUiList.addAll(relataedPostion + 1, goodsInfos);
        } else {
        }
    }

    //relataedPostion 切换标签所在的position
    @Override
    public void onFreeWorkbayResult(String projectNum, List<WorkBayInfo> workBayInfos, int relataedPostion) {
        clearLastTabItems(relataedPostion);
        if (!workBayInfos.isEmpty()) {
            workOrderUiList.addAll(relataedPostion + 1, presenter.getUiModelFromWorkBay(workBayInfos, projectNum));
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
            workOrderUiList.addAll(relataedPostion + 1, presenter.getUiModelFromTechnianUser(result, projectNum));
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
    }

}
