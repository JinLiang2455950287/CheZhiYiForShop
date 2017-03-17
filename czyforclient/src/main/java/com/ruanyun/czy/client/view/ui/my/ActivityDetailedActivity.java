package com.ruanyun.czy.client.view.ui.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.ActivityInfo;
import com.ruanyun.chezhiyi.commonlib.model.ActivitySignInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.ActivityPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.ActivityDetailedMvpView;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description ：我的 活动的 详情
 * <p/>
 * Created by ycw on 2016/10/24.
 */
public class ActivityDetailedActivity extends AutoLayoutActivity implements
        ActivityDetailedMvpView, Topbar.onTopbarClickListener {


    public static final int REQUEST_CODE = 221;
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_activity_status)
    TextView tvActivityStatus;
    @BindView(R.id.iv_activity_status)
    ImageView ivActivityStatus;   //活动状态图片
    @BindView(R.id.tv_activity_text)
    TextView tvActivityText;   //信息
    @BindView(R.id.tv_activity_cancel)
    TextView tvActivityCancel;      //取消活动
    @BindView(R.id.rl_activity_operation)
    RelativeLayout rlActivityOperation;  //是否显示取消活动
    @BindView(R.id.iv_goods_pic)
    ImageView ivGoodsPic;  //活动图片
    @BindView(R.id.tv_title)
    TextView tvTitle;  // 活动名
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;  //开始时间
    @BindView(R.id.tv_end_time)
    TextView tvEndTime; //结束时间
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;   //报名时间
    @BindView(R.id.ll_create_time)
    LinearLayout llCreateTime;
    @BindView(R.id.tv_activity_number)
    TextView tvActivityNumber;   //报名人数
    @BindView(R.id.ll_activity_number)
    LinearLayout llActivityNumber;
    @BindView(R.id.tv_single_money)
    TextView tvSingleMoney;  //单人费用
    @BindView(R.id.ll_single_money)
    LinearLayout llSingleMoney;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;        //报名费用
    @BindView(R.id.ll_all_money)
    LinearLayout llAllMoney;
    @BindView(R.id.tv_link_people)
    TextView tvLinkPeople;      //报名联系人
    @BindView(R.id.ll_link_people)
    LinearLayout llLinkPeople;
    @BindView(R.id.tv_link_tel)
    TextView tvLinkTel;     //联系方式
    @BindView(R.id.ll_link_tel)
    LinearLayout llLinkTel;
    @BindView(R.id.tv_remark)
    TextView tvRemark;      //备注信息
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.rl_activity_info)
    RelativeLayout rlActivityInfo;

    private String activityNum;
    private ActivityInfo activityInfo;
    private ActivityPresenter presenter = new ActivityPresenter();


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_activity_detailed);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
//        setResult(RESULT_OK);
    }

    private void initView() {
        activityNum = getIntent().getStringExtra(C.IntentKey.ACTIVITY_NUM);
        //  获取  活动报名信息
        presenter.getSignInfo(app.getApiService().signInfo(app.getCurrentUserNum(), activityNum));
        topbar.setBackBtnEnable(true)
                .setTttleText("活动详情")
                .onBackBtnClick()
                .setTopbarClickListener(this);

    }

    private void updataUI(ActivitySignInfo activitySignInfo) {
        activityInfo = activitySignInfo.getActivityInfo();
        OrderInfo orderInfo = activitySignInfo.getOrderInfo();

        if (activityInfo == null || activityInfo.getActivityNum() == null) return;
        if (activitySignInfo.getSignStatus() == 1) {
            setStatusStringAndPic(getActivityStatus(activityInfo));//设置图片  状态文字
        } else {
            tvActivityStatus.setText("活动已取消");
            ivActivityStatus.setImageResource(R.drawable.myactivity_pendant_ended);
            rlActivityOperation.setVisibility(View.GONE);
        }
        if (mContext != null)
            Glide.with(mContext).load(FileUtil.getImageUrl(activityInfo.getActivityPath()))
                    .error(R.drawable.default_img).into(ivGoodsPic);
        tvTitle.setText(activityInfo.getActivityName());
        tvStartTime.setText("开始时间：" + activityInfo.getActivityBeginTime());
        tvEndTime.setText("结束时间：" + activityInfo.getActivityEndTime());
        tvCreateTime.setText(activitySignInfo.getCreateTime());//报名时间
        tvActivityNumber.setText(activitySignInfo.getTotalNum() + "人");
        if (activityInfo.getCost() != 0) {
            tvSingleMoney.setText("¥" + activityInfo.getCost()); //单人费用
            tvAllMoney.setText("¥" + activitySignInfo.getTotalPrice());//报名费
        } else {
            llSingleMoney.setVisibility(View.GONE);
            llAllMoney.setVisibility(View.GONE);
        }
        tvLinkPeople.setText(activitySignInfo.getUserName());
        tvLinkTel.setText(activitySignInfo.getLinkTel());
        tvRemark.setText(activitySignInfo.getRemark());


        StringBuilder sb = new StringBuilder();
        sb.append("创建时间：").append(activitySignInfo.getCreateTime());
        if (activitySignInfo.getTotalPrice() != 0 && orderInfo != null) {
            sb.append("\n").append("支付时间：").append(orderInfo.getPayTime());//支付时间
            sb.append("\n").append("支付方式：").append(DbHelper.getInstance().getParentName(String
                    .valueOf(orderInfo.getPayMethod()), C.ParentCode.PAY_METHOD));//支付方式
            if (orderInfo.getPayMethod() == 2 || orderInfo.getPayMethod() == 3) {
                sb.append("\n").append("第三方交易流水号：").append(orderInfo.getPayThirtAccount());//第三方交易流水
            }
        }
        if (activityInfo.getActivityStatus() == -1) {
            sb.append("\n").append("取消时间：").append(orderInfo.getConsumeTime());//取消时间
        }
        tvActivityText.setText(sb.toString());

    }

    /**
     * 活动时间获取状态
     * @param activityInfo
     * @return
     */
    private int getActivityStatus(ActivityInfo activityInfo) {
        // 活动报名开始时间
        //Date startTime = StringUtil.getDateFromString(activityInfo.getStartTime());
        // 活动报名截止时间
        //Date endTime = StringUtil.getDateFromString(activityInfo.getEndTime());
        // 活动开始时间
        Date activityBeginTime = StringUtil.getDateFromString(activityInfo.getActivityBeginTime());
        // 活动截止时间
        Date activityEndTime = StringUtil.getDateFromString(activityInfo.getActivityEndTime());
        Date currentTime = new Date(System.currentTimeMillis());

        if (/*currentTime.compareTo(endTime) >= 0 && */currentTime.compareTo(activityBeginTime) < 0) {
            return 2;  //即将开始
        } else if (currentTime.compareTo(activityBeginTime)>=0 && currentTime.compareTo(activityEndTime) < 0 ) {
            return 1; // 活动进行中
        } else if (currentTime.compareTo(activityEndTime)>=0) {
            return 3;// 活动已结束
        }
        return 0;
    }

    /**
     * 状态更改显示
     * @param activityStatus
     */
    private void setStatusStringAndPic(int activityStatus) {
        String string = "";
        int picImg;
        switch (activityStatus) {
            case 1:
                string = "活动进行中";
                picImg = R.drawable.myactivity_pendant_ongoing;
                break;
            case 2:
                string = "活动即将开始";
                picImg = R.drawable.myactivity_pendant_tobegin;
                if (showOption()) {
                    rlActivityOperation.setVisibility(View.VISIBLE);
                } else {
                    rlActivityOperation.setVisibility(View.GONE);
                }
                break;
            case 3:
                string = "活动已结束";
                picImg = R.drawable.myactivity_pendant_ended;
                break;
            case -1:
                string = "活动已取消";
                picImg = R.drawable.myactivity_pendant_ended;
                break;
            default:
                string = "其他";
                picImg = R.drawable.myactivity_pendant_ended;
                break;
        }
        tvActivityStatus.setText(string);
        ivActivityStatus.setImageResource(picImg);
    }

    public boolean showOption() {
        if (activityInfo.getIsCost() == ActivityInfo.IS_COST_YES) { // 收费活动
            if (activityInfo.getSfyytfyCost() == ActivityInfo.IS_COST_YES) {  // 活动允许退款
                // 判断时间是否允许退费
                //  当前时间加上允许退款的天数的日期
                Date currentDate = new Date(System.currentTimeMillis() + activityInfo.getRefundDay() * (24 * 60 * 60 * 1000));
                String currentString = StringUtil.getTimeStr("yyyy-MM-dd HH:mm:ss", currentDate);

                if (StringUtil.getDateFromStr("yyyy-MM-dd HH:mm:ss", currentString, activityInfo.getActivityBeginTime()) >= 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                // 活动不允许退款
                return false;
            }
        } else {
            // 不收费活动, 随时取消
            return true;
        }
    }

    /**
     * 取消活动
     */
    @OnClick({R.id.tv_activity_cancel, R.id.rl_activity_info})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_activity_cancel) {
            // : 2016/10/24 取消活动
            createDialog();
        } else if (view.getId() == R.id.rl_activity_info) {
            // 跳转到活动详情
            String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_ACTIVITY), app
                    .getCurrentUserNum(), activityNum);
            Intent intent = AppUtility.getWebIntent(mContext, url, WebViewActivity.HD);
            intent.putExtra(C.IntentKey.ACTIVITY_INFO, activityInfo);
            intent.putExtra(C.IntentKey.ACTIVITY_OPTION, showOption());
            intent.putExtra(C.IntentKey.ACTIVITY_CANCELABLE, showOption());
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * 创建一个取消活动提示  dialog
     */
    private void createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("确定取消活动吗?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.delActivity(app.getApiService().delActivity(app
                                .getCurrentUserNum(), activityNum));
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    @Override
    public void getSignInfoSuccess(ActivitySignInfo signInfo) {
        updataUI(signInfo);
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void delActivitySuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }
}
