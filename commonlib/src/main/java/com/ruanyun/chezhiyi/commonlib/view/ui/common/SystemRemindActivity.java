package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.RemindInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderRecordInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.SystemRemindParams;
import com.ruanyun.chezhiyi.commonlib.presenter.SystemRemindPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.SystemRemindMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.SystemRemindAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Description:  系统消息  消息提醒
 * author: zhangsan on 16/10/27 上午10:19.
 */
public class SystemRemindActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener, SystemRemindMvpView {

    public static final int REQUEST_CODE_REFRESH = 1234;
    public static final String SYSTEM_NOTICE = "GD,DDTK,HD,ZC,GDJS,YY,YYQR,DD,YYQR,XTTZ";
    public static final String SYSTEM_MSG = "BY,YE,SR,HYLS";

    private SystemRemindParams systemRemindParams = new SystemRemindParams();
    private SystemRemindAdapter adapter;
    private ListView list;
    private Topbar topbar;
    private SystemRemindPresenter remindPresenter = new SystemRemindPresenter();
    private List<RemindInfo> remindInfos;//集合
    private RemindInfo itemAtPosition;
    private String currentRemindType = SYSTEM_NOTICE;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        remindPresenter.attachView(this);
        setContentView(R.layout.activity_system_remind);
        currentRemindType = getIntent().getStringExtra(C.IntentKey.SYSTEM_TYPE);
        initView();
        setResult(RESULT_OK);// 返回刷新
    }

    private void initView() {
        initRefreshLayout();
        topbar = getView(R.id.topbar);
        list = getView(R.id.list);
        remindInfos = new ArrayList<>();
        adapter = new SystemRemindAdapter(mContext, R.layout.list_item_system_remind, remindInfos);
        list.setAdapter(adapter);
//        Drawable drawable = new ColorDrawable(ContextCompat.getColor(mContext, R.color.divider));
//        list.setDivider(drawable);
//        list.setDividerHeight(1);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                //消息提醒列表点击事件
                itemAtPosition = (RemindInfo) parent.getItemAtPosition(position);
                toCorrespondActivity(itemAtPosition);
                if (itemAtPosition.getIsRead() == RemindInfo.READ_NO) {
//                    BGABadgeImageView imgMsgType = (BGABadgeImageView) view.findViewById(R.id.img_msg_type);
//                    imgMsgType.hiddenBadge();
//                    PrefUtility.put(C.PrefName.REMINDTYPE, PrefUtility.getInt(C.PrefName.REMINDTYPE, 0)-1);
//                    remindInfo.setIsRead(RemindInfo.READ_YES);
//                    DbHelper.getInstance().insertRemindInfo(remindInfo);
                    // 修改消息已读状态
                    remindPresenter.updateRemind(app.getApiService().updateRemind(app.getCurrentUserNum(), String.valueOf(itemAtPosition.getRemindInfoId())));
                }
            }
        });
        String title = getIntent().getStringExtra(C.IntentKey.TOPBAR_TITLE);
        title = TextUtils.isEmpty(title) ? "系统消息" : title;
        topbar.setTttleText(title)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        refreshWithLoading();
    }

    /**
     * 对应消息跳转
     *
     * @param remindInfo
     */
    private void toCorrespondActivity(RemindInfo remindInfo) {
        Intent intent;
        switch (remindInfo.getRemindType()) {
            case RemindInfo.REIND_TYPE_CAMPAIGN:  // 进入活动报名详情
                if (isClient()) {
                    intent = new Intent();
                    intent.putExtra(C.IntentKey.ACTIVITY_NUM, remindInfo.getCommonNum());
                    intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.ActivityDetailedActivity");
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_REFRESH);
                }
                break;
            case RemindInfo.REMIND_TYPE_BALANCE:        //余额不足提醒
                if (!isClient()) {
                    toRemindInfoWeb(remindInfo);
                }
                break;
            case RemindInfo.REMIND_TYPE_BESPEAK://进入预定详情
//                if (isClient()) {
//                    intent = new Intent();
//                    intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.BookingDetailActivity");
//                    intent.putExtra(C.IntentKey.BOOKING_MAKENUM, remindInfo.getCommonNum());
//                    startActivityForResult(intent, REQUEST_CODE_REFRESH);
//                }
//                break;
            case RemindInfo.REMIND_TYPE_BESPEAK_CONFIRM://进入预约详情
                if (isClient()) {
                    intent = new Intent();
                    intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.BookingDetailActivity");
                    intent.putExtra(C.IntentKey.BOOKING_MAKENUM, remindInfo.getCommonNum());
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_REFRESH);
                }
                break;

            case RemindInfo.REMIND_TYPE_BIRTHDAY://生日提醒
                if (!isClient()) {
                    toRemindInfoWeb(remindInfo);
                }
                break;

            case RemindInfo.REMIND_TYPE_CROWDFUNDING: //进入众筹详情
                if (isClient()) {
                    remindPresenter.getOrderDetail(remindInfo.getCommonNum());
                }
                break;

            case RemindInfo.REMIND_TYPE_MAINTAIN://保养提醒
                if (!isClient()) {
                    toRemindInfoWeb(remindInfo);
                }
                break;

            case RemindInfo.REMIND_TYPE_MEMBERLOSE: //会员流失提醒
                if (!isClient()) {
                    toRemindInfoWeb(remindInfo);
                }
                break;

            case RemindInfo.REMIND_TYPE_ORDER://订单被消费提醒
//                if (isClient()) {
//                    remindPresenter.getOrderDetail(remindInfo.getCommonNum());
//                }
//                break;

            case RemindInfo.REMIND_TYPE_ORDER_REFUND://订单退款申请结果提醒
                if (isClient()) {
                    remindPresenter.getOrderDetail(remindInfo.getCommonNum());
                }
                break;

            case RemindInfo.REMIND_TYPE_SYSTEM://系统通知
                if (!isClient()) {
                    toRemindInfoWeb(remindInfo);
//                    startActivityForResult( AppUtility.getWebIntent(mContext, url, remindInfo.getRemindTitle()), REQUEST_CODE_REFRESH );
                }
                break;

            case RemindInfo.REMIND_TYPE_WORKORDER_BALANCE://工单质检合格，提醒结算
                if (isClient()) {
                    intent = new Intent();
                    intent.setClass(mContext, WorkOrderListActivity.class);
                    intent.putExtra("item", 3);
                    startActivity(intent);
//                    startActivityForResult(intent, REQUEST_CODE_REFRESH);
                }
                break;

            case RemindInfo.REMIND_TYPE_WORKORDER://进入工单详情
                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderNum(remindInfo.getCommonNum());
                workOrderInfo.setWorkOrderRecordList(new ArrayList<WorkOrderRecordInfo>());
                intent = new Intent();
                intent.setClass(mContext, WorkOrderDetailedActivity.class);
                intent.putExtra(C.IntentKey.WORKORDER_INFO, workOrderInfo);
                startActivity(intent);
//                startActivityForResult(intent,REQUEST_CODE_REFRESH);
                break;
        }
    }

    /**
     * 消息提醒详情
     *
     * @param remindInfo
     */
    private void toRemindInfoWeb(RemindInfo remindInfo) {
        /**
         * reportDate	String	提醒生成时间 list中获取直接传入
         * remindType	String 	提醒类型 list中获取直接传入
         * commonNum	String	公共num list中获取直接传入
         * */
        String commonNum = remindInfo.getRemindUserNum();
        if (TextUtils.isEmpty(commonNum) ) commonNum = "";
        if (commonNum.contains("null")) commonNum = "";
        String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REMIND_INFO),
                app.getCurrentUserNum(), remindInfo.getCreateTime(),
                remindInfo.getRemindType(), commonNum);

        if (RemindInfo.REMIND_TYPE_SYSTEM.equals(remindInfo.getRemindType())) {
            url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_JS_REMIND_INFO_SYSTEM),
                    app.getCurrentUserNum(), remindInfo.getRemindType(), remindInfo.getRemindInfoNum());
        }
        LogX.e(TAG, "toRemindInfoWeb: SystemRemindActivity==========\n"+ url);
//        startActivityForResult( AppUtility.getWebIntent(mContext, url, remindInfo.getRemindTitle()), REQUEST_CODE_REFRESH );
        Intent intent = AppUtility.getWebIntent(mContext, url, WebViewActivity.TZ);
        intent.putExtra(C.IntentKey.NEED_SHARE, false);
        startActivity(intent);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode==REQUEST_CODE_REFRESH && resultCode ==RESULT_OK) {
////            onStartRefresh();//刷新界面
////        }
//    }

    @Override
    public Call loadData() {
        systemRemindParams.setRemindType(currentRemindType);
        systemRemindParams.setPageNum(currentPage);
        systemRemindParams.setIsPush(RemindInfo.STATUS_PUSHED);
        return app.getApiService().getSystemRemindList(app.getCurrentUserNum(), systemRemindParams);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        remindInfos = result.getResult();
//        remindPresenter.getIsReadFromDb(remindInfos);
        adapter.setData(remindInfos);
    }


    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        remindInfos.addAll(result.getResult());
//        remindPresenter.getIsReadFromDb(remindInfos);
        adapter.setData(remindInfos);
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void orderDetailResult(OrderInfo orderInfo) {
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.OrderDetailedActivity");
        intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
//        startActivityForResult(intent, REQUEST_CODE_REFRESH);
        startActivity(intent);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void updateRemindSuccess() {
        //  刷新数据
        itemAtPosition.setIsRead(RemindInfo.READ_YES);
        adapter.notifyDataSetChanged();
//        onStartRefresh();
    }

    @Override
    public void showTips(String msg) {
        AppUtility.showToastMsg(msg);
    }
}
