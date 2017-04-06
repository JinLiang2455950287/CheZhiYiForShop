package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AssistUserInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.PerssionBean;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderRecordInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.UpdateStatusParams;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.HomePerssionPresenter;
import com.ruanyun.chezhiyi.commonlib.presenter.WorkOrderDetailedPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.CommentUtils;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.HomePerssionMvpView;
import com.ruanyun.chezhiyi.commonlib.view.WorkOrderDetailedMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.WorkOrderGoodsAdapter;
import com.ruanyun.chezhiyi.commonlib.view.adapter.WorkOrderRecordAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description ：工单详情页 工单流水
 * <p/>
 * Created by ycw on 2016/9/26.
 */
public class WorkOrderDetailedActivity extends AutoLayoutActivity implements HomePerssionMvpView,
        WorkOrderDetailedMvpView, Topbar.onTopbarClickListener, WorkOrderRecordAdapter.BtnClick {

    public static final int REQUEST_CODE = 1992;
    public static final int REQUEST_CODE_UPDATE_STATUS = 1239;
    private Topbar topbar;
    private ListView list;
    private TextView tvWorkOrderNum;//工单编号
    private TextView tvWorkOrderProject;//服务项目
    private TextView tvWorkOrderGoods;//服务商品
    private RecyclerView rvWorkOrderGoods;//服务商品
    private TextView tvWorkOrderWorkNumber;//服务工位
    private TextView tvWorkOrderLeader;//服务技师
    private TextView tvWorkOrderBodyLeader;//服务技师
    private TextView tvWorkOrderBodyCustomer;//服务客户
    private TextView tvWorkOrderBodyCar;//服务车牌号
    private TextView tvWorkOrderRemake;//客服备注 技师
    private TextView tvWorkOrderAllPrice;//工单总价
    private TextView tvWorkOrderDownPayment;//已付定金

    private WorkOrderInfo mWorkOrderInfo;      // 工单信息
    private ProjectType projectType;
    private WorkOrderRecordAdapter workOrderRecordAdapter;   // 工单流水的
    private WorkOrderDetailedPresenter presenter = new WorkOrderDetailedPresenter();
    private AlertDialog.Builder alertDialogBuilder;
    private View listHeadView;
    private String refuseRemake = "";
    private WorkOrderGoodsAdapter adapter;//商品的集合
    private HomePerssionPresenter homePerssionPresenter = new HomePerssionPresenter();//权限表
    private List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        presenter.attachView(this);
        setContentView(R.layout.activity_workorder_detailed);
        initView();
        homePerssionPresenter.attachView(this);
    }

    private void initView() {
        mWorkOrderInfo = getIntent().getParcelableExtra(C.IntentKey.WORKORDER_INFO);
        listHeadView = getViewFromLayout(R.layout.list_work_order_head_view);

        tvWorkOrderNum = (TextView) listHeadView.findViewById(R.id.tv_work_order_num);
        tvWorkOrderProject = (TextView) listHeadView.findViewById(R.id.tv_work_order_project);
        tvWorkOrderGoods = (TextView) listHeadView.findViewById(R.id.tv_work_order_goods);
        rvWorkOrderGoods = (RecyclerView) listHeadView.findViewById(R.id.rv_work_order_goods);
        tvWorkOrderWorkNumber = (TextView) listHeadView.findViewById(R.id.tv_work_order_work_number);
        tvWorkOrderBodyLeader = (TextView) listHeadView.findViewById(R.id.tv_work_order_body_leader);
        tvWorkOrderLeader = (TextView) listHeadView.findViewById(R.id.tv_work_order_leader);
        tvWorkOrderBodyCustomer = (TextView) listHeadView.findViewById(R.id.tv_work_order_body_customer);
        tvWorkOrderBodyCar = (TextView) listHeadView.findViewById(R.id.tv_work_order_body_car);
        tvWorkOrderRemake = (TextView) listHeadView.findViewById(R.id.tv_work_order_remake);
        tvWorkOrderAllPrice = (TextView) listHeadView.findViewById(R.id.tv_work_order_all_price);
        tvWorkOrderDownPayment = (TextView) listHeadView.findViewById(R.id.tv_work_order_down_payment);

        list = getView(R.id.list);
        topbar = getView(R.id.topbar);

        topbar.setBackBtnEnable(true)
                .setTttleText("工单详情")
                .onBackBtnClick()
                .setTopbarClickListener(this);

        //商品的recycleView
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvWorkOrderGoods.setLayoutManager(manager);
        adapter = new WorkOrderGoodsAdapter(mContext, R.layout.item_rv_work_order_goods, new ArrayList<OrderGoodsInfo>());
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                OrderGoodsInfo goodsInfo = (OrderGoodsInfo) o;
                String goodsType = AppUtility.getTypeName(goodsInfo.getGoodsInfo().getGoodsType());
                if (goodsType.equals(C.OrderType.ORDER_TYPE_CP) ||
                        goodsType.equals(C.OrderType.ORDER_TYPE_TG) ||
                        goodsType.equals(C.OrderType.ORDER_TYPE_JF)) {  // 是产品。团购。积分商品  跳转到详情页
                    AppUtility.showGoodsWebView(goodsInfo.getSinglePrice(),
                            app.getCurrentUserNum(),
                            goodsInfo.getGoodsNum(),
                            goodsType,
                            goodsInfo.getGoodsNum(),
                            app.getCurrentUserNum(),
                            mContext,
                            goodsInfo.getGoodsName(),
                            goodsInfo.getProjectParent(),
                            goodsInfo.getMainPhoto(),
                            goodsInfo.getViceTitle());
                } else {  // 否则

                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        rvWorkOrderGoods.setAdapter(adapter);

        workOrderRecordAdapter = new WorkOrderRecordAdapter(mContext, new ArrayList<WorkOrderRecordInfo>());
        list.setAdapter(workOrderRecordAdapter);
        list.addHeaderView(listHeadView);
        //  点击服务客户跳转
        tvWorkOrderBodyCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mWorkOrderInfo.getServiceUserNum())) {
                    AppUtility.showToastMsg("用户不存在");
                    return;
                }
                presenter.getFriendShipInfo(app.getHxApiService().getFriendShipInfo(app.getCurrentUserNum(), mWorkOrderInfo.getServiceUserNum()));
            }
        });
        getData();
        //权限表
        homePerssionPresenter.getPerssionData(app.getApiService().getPerssion(app.getCurrentUserNum()));
    }

    /**
     * 获取界面显示数据 更新界面
     */
    private void updateUI() {
        tvWorkOrderNum.setText("服务编号：" + mWorkOrderInfo.getWorkOrderNum());
        tvWorkOrderProject.setText("服务项目：" + mWorkOrderInfo.getProjectName());
        tvWorkOrderWorkNumber.setText("服务工位：" + getNameString(mWorkOrderInfo.getWorkbayName()));//工位
        if (mWorkOrderInfo.getWorkOrderStatus() > 5) {
            tvWorkOrderAllPrice.setText("工单总价：¥" + mWorkOrderInfo.getTotalAmount());
            tvWorkOrderAllPrice.setVisibility(View.VISIBLE);
        } else {
            tvWorkOrderAllPrice.setVisibility(View.GONE);
        }
        if (mWorkOrderInfo.getDownPayment() != 0 && mWorkOrderInfo.getWorkOrderStatus() > 7) {
            tvWorkOrderDownPayment.setVisibility(View.VISIBLE);
            tvWorkOrderDownPayment.setText(String.format("已付定金：¥%2.2s", mWorkOrderInfo.getDownPayment()));
        } else {
            tvWorkOrderDownPayment.setVisibility(View.GONE);
        }
        // 工单的商品集合
        List<OrderGoodsInfo> workOrderGoodsInfos = mWorkOrderInfo.getWorkOrderGoodsList();
        //设置商品名
        tvWorkOrderGoods.setVisibility(View.VISIBLE);
        setGoodsNameList(workOrderGoodsInfos);
        //设置车牌号
        tvWorkOrderBodyCar.setText("服务车辆：" + mWorkOrderInfo.getServicePlateNumber());
        // 设置服务技师名
        List<AssistUserInfo> workOrderAssistList = mWorkOrderInfo.getWorkOrderAssistList();  // 协助技师 集合
        setLeadingUserName(workOrderAssistList);  //设置服务技师名
        // TODO: 2016/12/5  设置服务技师名  可以点击
//        setLeadingUserNameSpan(workOrderAssistList);  //设置服务技师名
        SpannableStringBuilder ssb = new SpannableStringBuilder("服务客户：");
        if (mContext == null) return;
        ssb.append(getNameString(mWorkOrderInfo.getServiceUserName()))
                .setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.theme_color_default)), 5, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String text = "服务客户：" + getNameString(mWorkOrderInfo.getServiceUserName());
        tvWorkOrderBodyCustomer.setText(ssb);// 技师端显示服务客户
        if (isClient()) {
            tvWorkOrderRemake.setVisibility(View.GONE);
        } else {
            if (workOrderAssistList.size() > 0) {  //根据 是否存在 协助技师 设置是否需要显示分配提成按钮
                workOrderRecordAdapter.setNeedToDistribution(true);
            } else {
                workOrderRecordAdapter.setNeedToDistribution(false);
            }
            workOrderRecordAdapter.isShowQulityCheckBtn = (app.getUser().getIsQcPerson() == 1);
            // todo 工单详情  客服备注  visible
            //            tvWorkOrderRemake.setVisibility(View.GONE);
            tvWorkOrderRemake.setText(String.format("客服备注：%s",
                    TextUtils.isEmpty(mWorkOrderInfo.getRemark()) ? "无" : mWorkOrderInfo.getRemark()));  //技师端
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        //请求工单详情信息
        presenter.getWorkorderInfo(app.getApiService().getWorkorderInfo(app.getCurrentUserNum(),
                mWorkOrderInfo.getWorkOrderNum()));

        //请求 工单商品 信息
//        presenter.getWorkorderGoods(app.getApiService().getWorkorderGoodsList(app
//                .getCurrentUserNum(), mWorkOrderInfo.getWorkOrderNum()));
        // 请求 工单 协助技师
//        presenter.getAssistList(app.getApiService().getAssistList(app
//                .getCurrentUserNum(), mWorkOrderInfo.getWorkOrderNum()));
    }

    private String getNameString(String serviceUserName) {
        return TextUtils.isEmpty(serviceUserName) ? "暂无" : serviceUserName;
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void getWorkorderInfoSuccess(WorkOrderInfo workOrderInfo) {
        mWorkOrderInfo = workOrderInfo;
        if (projectType == null) {
            projectType = DbHelper.getInstance().getServiceType(mWorkOrderInfo.getProjectNum());
        }
        updateUI();
        if (!isClient()) {
            workOrderRecordAdapter.setBtnClick(this);
            workOrderRecordAdapter.setOperationable(true);
            workOrderRecordAdapter.setLeadingUser(app.getCurrentUserNum().equals(mWorkOrderInfo.getLeadingUserNum()));
        }
        workOrderRecordAdapter.setData(mWorkOrderInfo.getWorkOrderRecordList());
        workOrderRecordAdapter.notifyDataSetChanged();

    }

    private ArrayList<String> pickedAssistUserNums = new ArrayList<>();//  负责技师和协助技师的UserNum集合

    /**
     * 设置服务技师名
     *
     * @param workOrderAssistList
     */
    private void setLeadingUserName(List<AssistUserInfo> workOrderAssistList) {
        tvWorkOrderLeader.setText("服务技师：");
        if (TextUtils.isEmpty(mWorkOrderInfo.getLeadingUserName())) {
            tvWorkOrderBodyLeader.setText("暂无技师");//技师
            return;
        }
        pickedAssistUserNums.clear();
        StringBuilder sb = new StringBuilder();
        sb.append("【负责人】").append(mWorkOrderInfo.getLeadingUserName());
        pickedAssistUserNums.add(mWorkOrderInfo.getLeadingUserNum());
        for (int i = 0; i < workOrderAssistList.size(); i++) {
            sb.append("、【协助人】");
            sb.append(workOrderAssistList.get(i).getAssistUserName());
            pickedAssistUserNums.add(workOrderAssistList.get(i).getAssistUserNum());
        }
        tvWorkOrderBodyLeader.setText(sb.toString());//技师
    }

/*
    //todo   技师名  可以点击     START
    private void setLeadingUserNameSpan(List<AssistUserInfo> workOrderAssistList) {
        tvWorkOrderLeader.setText("服务技师：");
        if (TextUtils.isEmpty(mWorkOrderInfo.getLeadingUserName())) {
            tvWorkOrderBodyLeader.setText("暂无技师");//技师
            return;
        }
        pickedAssistUserNums.clear();
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("【负责人】").append(mWorkOrderInfo.getLeadingUserName());
        pickedAssistUserNums.add(mWorkOrderInfo.getLeadingUserNum());
        sb.setSpan(new MyClickableSpan(mWorkOrderInfo.getLeadingUserNum()), 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (int i = 0; i< workOrderAssistList.size(); i++) {
            int startIndex = sb.length();
            sb.append("、【协助人】");
            sb.append(workOrderAssistList.get(i).getAssistUserName());
            pickedAssistUserNums.add(workOrderAssistList.get(i).getAssistUserNum());
            sb.setSpan(new MyClickableSpan(workOrderAssistList.get(i).getAssistUserNum()), startIndex+1, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvWorkOrderBodyLeader.setText(sb);//技师
        tvWorkOrderBodyLeader.setMovementMethod(LinkMovementMethod.getInstance());
    }

    class MyClickableSpan extends ClickableSpan {

        private String leadingUserNum;

        public MyClickableSpan(String leadingUserNum) {
            this.leadingUserNum = leadingUserNum;
        }

        @Override
        public void onClick(View widget) {
            if (TextUtils.isEmpty(leadingUserNum)) {
                AppUtility.showToastMsg("用户不存在");
                return;
            }
            presenter.getFriendShipInfo(app.getHxApiService().getFriendShipInfo(
                    app.getCurrentUserNum(), leadingUserNum));
        }

        @Override
        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }
    //   技师名  可以点击  END
*/

    /**
     * 设置商品的名称 “、”分割
     *
     * @param workOrderGoodsInfos
     */
    private void setGoodsNameList(List<OrderGoodsInfo> workOrderGoodsInfos) {
//        if (workOrderGoodsInfos.size() == 0) {
//            tvWorkOrderGoods.setText("暂无商品");//商品
//        } else {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i< workOrderGoodsInfos.size(); i++) {
//                if (sb.length() > 0) {
//                    sb.append("、");
//                }
//                sb.append(workOrderGoodsInfos.get(i).getGoodsName());
//            }
//            tvWorkOrderGoods.setText(sb.toString());//商品
//        }
//        list.removeHeaderView(listHeadView);
//        list.addHeaderView(listHeadView);
        adapter.setDatas(workOrderGoodsInfos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showError() {
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("当前数据不完整！即将关闭当前页面")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    /**
     * 获取工单商品成功
     *
     * @param workBayInfoResultBase
     */
    @Deprecated
    @Override
    public void getWorkorderGoodsSuccess(ResultBase<List<WorkOrderSubmitInfo.WorkOrderGoods>>
                                                 workBayInfoResultBase) {
        List<WorkOrderSubmitInfo.WorkOrderGoods> goodsList = workBayInfoResultBase.getObj();

//        if (goodsList.size() == 0) {
//            tvWorkOrderGoods.setText("暂无商品");//商品
//        } else {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i< goodsList.size(); i++) {
//                if (sb.length() > 0) {
//                    sb.append("、");
//                }
//                sb.append(goodsList.get(i).goodsName);
//            }
//            tvWorkOrderGoods.setText(sb.toString());//商品
//        }


    }

    /**
     * 获取协助技师成功后
     *
     * @param assistUserInfo
     */
    @Override
    public void getAssistListSuccess(List<AssistUserInfo> assistUserInfo) {

    }

    /**
     * 获取工单详情成功后
     */
    @Override
    public void updateStatusSuccess() {
        getData();//获取工单详情
    }

    @Override
    public void getFriendShipInfoSuccess(User user) {
        if (user == null || user.getUserNum() == null) return;
        if (user.getUserType() == 3) {
            if (StringUtil.isPhone(user.getLoginName())) {
                AppUtility.goToUserProfile(mContext, user);
            } else {
                AppUtility.showToastMsg("非会员");
            }
        } else /*if (user.getUserType() == 2)*/ {
            AppUtility.goToUserProfile(mContext, user);
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                getData();
            } else if (requestCode == REQUEST_CODE_UPDATE_STATUS) {
                refuseRemake = data.getStringExtra(C.IntentKey.UPDATE_SIGNATURE);
                UpdateWorkStatus(WorkOrderRecordInfo.WORK_ORDER_STATUS_REPAIR, refuseRemake);
            }
        }
    }

    /**
     * 点击事件
     *
     * @param status
     * @param view
     * @param position
     */
    @Override
    public void onBtnClick(int status, View view, int position) {
        setResult(RESULT_OK);//返回刷新
        int viewId = view.getId();
        if (viewId == R.id.tv_end_order) {     // 结束施工
            showTipDialog("确定结束施工？", WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY);
        } else if (viewId == R.id.tv_add_helper) {      // 添加助手
            toAddAssistUser();
        } else if (viewId == R.id.tv_assist) {      // 代下单
            Intent intent = new Intent();
            intent.putExtra(C.IntentKey.PROJECTTYPE_INFO, projectType);
            intent.putExtra(C.IntentKey.WORKORDER_NUM, mWorkOrderInfo.getWorkOrderNum());
            intent.setClassName(mContext, "com.ruanyun.chezhiyi.view.ui.home.AddServiceGoodsActivity");
            startActivityForResult(intent, REQUEST_CODE);
        } else if (viewId == R.id.tv_pass) {        //质检通过，开始施工， 分配提成
            if (status == WorkOrderRecordInfo.WORK_ORDER_STATUS_FINISH ||        //结算完成
                    status == WorkOrderRecordInfo.WORK_ORDER_STATUS_CONFORMITY) {       //等待结算
                //分配提成
                Intent intent = new Intent();
                intent.setClassName(mContext, "com.ruanyun.chezhiyi.view.ui.home.DistributionCommissionActivity");
                intent.putExtra(C.IntentKey.WORKORDER_INFO, mWorkOrderInfo);
                showActivity(intent);
            } else if (status == WorkOrderRecordInfo.WORK_ORDER_STATUS_PREPARE ||
                    status == WorkOrderRecordInfo.WORK_ORDER_STATUS_REPAIR) {//开始施工
                showTipDialog("确定开始施工？", WorkOrderRecordInfo.WORK_ORDER_STATUS_UNDER_CONSTRUCTION);
            } else if (status == WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY) {//质检中
                showTipDialog("确定质检通过？", WorkOrderRecordInfo.WORK_ORDER_STATUS_CONFORMITY);
            }
        } else if (viewId == R.id.tv_cancel) {      //拒绝， 添加助手
            if (status == WorkOrderRecordInfo.WORK_ORDER_STATUS_QUALITY) {//拒绝
                Intent intent = new Intent(mContext, RemarkActivity.class);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "拒绝理由");
                intent.putExtra(C.IntentKey.EDIT_CONTEXT, refuseRemake);
                startActivityForResult(intent, REQUEST_CODE_UPDATE_STATUS);
//                showTipDialog("拒绝通过质检？", WorkOrderRecordInfo.WORK_ORDER_STATUS_REPAIR);
            } else {//添加助手
                toAddAssistUser();
            }
        }
    }

    /**
     * 去添加助手
     */
    private void toAddAssistUser() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(C.IntentKey.PICK_ASSIST_USER, pickedAssistUserNums);
        intent.putExtra(C.IntentKey.WORKORDER_NUM, mWorkOrderInfo.getWorkOrderNum());
        intent.putExtra(C.IntentKey.PROJECTNUM, mWorkOrderInfo.getProjectNum());
        intent.setClassName(mContext, "com.ruanyun.chezhiyi.view.ui.workorder.PickAssistUserActivity");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private UpdateStatusParams updateStatusParams = new UpdateStatusParams();

    /**
     * 修改工单状态
     */
    private void UpdateWorkStatus(int status, String remark) {
        updateStatusParams.setWorkOrderNum(mWorkOrderInfo.getWorkOrderNum());
        updateStatusParams.setWorkOrderStatus(status);//状态
        updateStatusParams.setRemark(remark);//备注
        presenter.updateStatus(app.getApiService().updateStatus(app.getCurrentUserNum(), updateStatusParams));

    }


    /**
     * 显示提示
     *
     * @param msg
     * @param status
     */
    private void showTipDialog(String msg, final int status) {
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(msg)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateWorkStatus(status, "");
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePerssionPresenter.detachView();
    }

    @Override
    public void getDataSuccess(List<PerssionBean> perssionList) {
        LogX.e("权限表", perssionList.toString());
        CommentUtils.permission.clear();
        for (PerssionBean perssion : perssionList) {
//            permissionList.add(perssion.getButNum());
            CommentUtils.permission.add(perssion.getButNum());
        }
        LogX.e("权限表 CommentUtils.permission", CommentUtils.permission.toString());
    }

    @Override
    public void getDataFault() {

    }
}
