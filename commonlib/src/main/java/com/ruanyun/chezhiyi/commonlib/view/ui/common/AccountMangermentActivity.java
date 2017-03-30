package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.hxchat.HXHelper;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.presenter.AccountMangementPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.AccountMangementMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.ChooseInterestsDialog;
import com.ruanyun.chezhiyi.commonlib.view.widget.CircleImageView;
import com.ruanyun.chezhiyi.commonlib.view.widget.FlowLayout;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.imagepicker.AndroidImagePicker;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 账户管理
 * Created by Sxq on 2016/9/11.
 */
public class AccountMangermentActivity extends HeaderPickerActivity implements Topbar
        .onTopbarClickListener, View.OnClickListener, AccountMangementMvpView,
        ChooseInterestsDialog.onInterestsResult {

    Topbar topbar;
    CircleImageView imgPhoto;//头像
    LinearLayout llPhoto;//头像
    TextView tvNickname;// 昵称
    LinearLayout llNickname;// 昵称
    TextView tvSex;//性别
    LinearLayout llSex;//性别
    TextView tvBirDate;//出生日期
    LinearLayout llBirDate;//出生日期
    LinearLayout llSignature;//司机端   的    个性签名
    Button btnWorkState;
    //    TextView tvPersonalLabel;
    LinearLayout llPersonalLabel;

    TextView tvSignature;
    LinearLayout llGrade;
    TextView tvPersonalNote;
    FlowLayout flowView;
    ChooseInterestsDialog dialog;
    TextView tvPersonalLabel;

    private Call call;
    private AccountMangementPresenter mangementPresenter = new AccountMangementPresenter();
    private HashMap<String, RequestBody> map = new HashMap<>();
    public static final int UPDAET_NICKNAME = 1321;
    public static final int REQUESTCODE_SIGNATURE = 34123;
    private String birDate;
    private String personalNote = "", userInterest = "";
    private int sex;
    private String workState;
    private User appUser;
    private LinearLayout llSignatureTechnician;//技师端的个人说明
    private TextView tvPersonalTechnicianNote;//技师端的个人说明
    private DatePickerDialog mDatePickerDialog;
    private Calendar mCalendar;
    private long mTimeInMillis;
    private String aa;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_account_mengerment);
        ButterKnife.bind(this);
        mangementPresenter.attachView(this);
        initSexPop();
        initSelectPopView();
        initTopbar();
        initView();
    }

    private void initTopbar() {
        topbar = getView(R.id.topbar);
        imgPhoto = getView(R.id.img_photo);
        llPhoto = getView(R.id.ll_photo);//头像
        tvNickname = getView(R.id.tv_nickname);//昵称
        llNickname = getView(R.id.ll_nickname);// 昵称
        tvSex = getView(R.id.tv_sex);//性别
        llSex = getView(R.id.ll_sex);//性别
        llGrade = getView(R.id.ll_grade);//会员等级
        llSignature = getView(R.id.ll_signature);//司机端   的    个性签名
        tvBirDate = getView(R.id.tv_bir_date);//出生日期
        llBirDate = getView(R.id.ll_bir_date);//出生日期
        llPersonalLabel = getView(R.id.ll_personal_label);//个人标签  个人爱好
        flowView = getView(R.id.flow_view);//个人标签  个人爱好
        tvSignature = getView(R.id.tv_signature);//司机端   的    个性签名
        tvPersonalNote = getView(R.id.tv_personal_note);//司机端   的    个性签名
        tvPersonalLabel = getView(R.id.tv_personal_label);// 个人标签  个人爱好
        //技师端的个人说明
        llSignatureTechnician = getView(R.id.ll_signature_technician);// 技师端的个人说明
        tvPersonalTechnicianNote = getView(R.id.tv_personal_technician_note);// 技师端的个人说明

        //btQuit = getView(R.id.bt_quit);
        llPhoto.setOnClickListener(this);//
        llNickname.setOnClickListener(this);//
        llSex.setOnClickListener(this);//
        llSignature.setOnClickListener(this);//
        llBirDate.setOnClickListener(this);//
        //btQuit.setOnClickListener(this);
        btnWorkState = getView(R.id.btn_work_state);//工作状态
        btnWorkState.setOnClickListener(this);//
        topbar.setTttleText(R.string.account_mangement)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
        if (isClient()) {
            tvSignature.setText("个性签名");
            btnWorkState.setVisibility(View.GONE);//工作状态
            tvPersonalLabel.setText("个人爱好");//标签
            llPersonalLabel.setVisibility(View.VISIBLE);
            llGrade.setVisibility(View.GONE);
            llPersonalLabel.setOnClickListener(this);//
        } else {
//            tvSignature.setText("个人说明");
            llSignatureTechnician.setVisibility(View.VISIBLE);
            llSignature.setVisibility(View.GONE);
            btnWorkState.setVisibility(View.VISIBLE);
            llPersonalLabel.setVisibility(View.VISIBLE);
            tvPersonalLabel.setText("个人标签");//标签
            llGrade.setVisibility(View.GONE);
            llPersonalLabel.setOnClickListener(null);//
        }
        mCalendar = Calendar.getInstance();
        mTimeInMillis = mCalendar.getTimeInMillis();
        initDatePicker();
        // 请求获取用户信息
        mangementPresenter.getUserByNum(app.getCurrentUserNum());
    }

    @SuppressWarnings("ResourceType")
    private void initDatePicker() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(year, monthOfYear, dayOfMonth);
                int age = AppUtility.getAgeByBirthday(mCalendar.getTime());
                if (0 <= age && age < 1000) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    getDate(simpleDateFormat.format(mCalendar.getTime()));
                } else {
                    AppUtility.showToastMsg(R.string.birthday_legitimate);
                }
            }
        }, year, month, day);
        mDatePickerDialog.getDatePicker().setMaxDate(mTimeInMillis);
        mDatePickerDialog.setTitle("");
    }

    private void initView() {
        appUser = app.getUser();
        if (appUser.getUserNum() == null) return;
        if (mContext == null) return;
        aa = FileUtil.getImageUrl(appUser.getUserPhoto());
        LogX.e("账户管理==", appUser.getUserPhoto().toString().trim());
        LogX.e("账户管理=", aa.trim());
//        Glide.with(mContext).load(aa.trim()).into(imgPhoto);

        ImageUtil.loadImage(mContext, aa.trim(), imgPhoto, R.drawable.default_img);

        tvNickname.setText(appUser.getNickName());
        sex = appUser.getUserSex();
        tvSex.setText(sex == C.USER_SEX_UNKNOW ? "" : sex == C.USER_SEX_MAN ? "男" : "女");
        birDate = appUser.getUserBirth();
        if (AppUtility.isNotEmpty(birDate) && birDate.length() >= 10) {
            tvBirDate.setText(birDate.substring(0, 10));
        }
        workState = appUser.getWorkStatus();
        btnWorkState.setSelected(workState.equals(C.WORKSTATE_NOT_BUSY) || workState.equals(C.WORKSTATE_BUSY));//1 -工作中 2-休息中
        tvPersonalNote.setText(appUser.getPersonalNote());//个性签名
        userInterest = appUser.getUserInterest();
        tvPersonalTechnicianNote.setText(appUser.getPersonalNote());
        if (isClient()) {
            if (!TextUtils.isEmpty(userInterest))
                creatServiceLables(Arrays.asList(userInterest.split(",")));
        } else {
            String userLabel = appUser.getLabelCode();
            List<String> allLabels = new ArrayList<>();
            List<String> label = Arrays.asList(userLabel.split(","));
            for (int i = 0; i < label.size(); i++) {
                allLabels.add(DbHelper.getInstance().getParentName(label.get(i), C.ParentCode.USER_LABEL));
            }
            creatServiceLables(allLabels);
        }

    }

    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_photo) {
            showSelectPopView(llPhoto);
        } else if (i == R.id.ll_nickname) {
            Intent intent = new Intent(mContext, UpdateNickNameActivity.class);
            startActivityForResult(intent, UPDAET_NICKNAME);
        } else if (i == R.id.ll_sex) {
            showPopView(llSex);
        } else if (i == R.id.ll_bir_date) {
            getBirDate();
        } else if (i == R.id.ll_signature) {//个人说明
            Intent intent = new Intent(mContext, RemarkActivity.class);
            intent.putExtra(C.IntentKey.TOPBAR_TITLE, tvSignature.getText().toString());
            intent.putExtra(C.IntentKey.EDIT_CONTEXT, appUser.getPersonalNote());
            startActivityForResult(intent, REQUESTCODE_SIGNATURE);
        } else if (i == R.id.btn_work_state) {//工作状态
            if (appUser.getWorkStatus().equals(C.WORKSTATE_BUSY)) {
                AppUtility.showToastMsg("技师正繁忙！");
            } else {
                getWorkState();
            }
        } else if (i == R.id.ll_personal_label) {
            if (null == dialog) {
                dialog = new ChooseInterestsDialog();
                dialog.setOnInterestsResult(this);
            }
            dialog.setIntrestsSelectString(userInterest);
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    /**
     * 修改头像
     **/
    @Override
    public void saveUserHeaderImage(File file) {
        RequestBody userPhoto = RequestBody.create(MediaType.parse("image/jpeg"), file);
        map.put("userPhoto", RequestBody.create(MediaType.parse("text/plain"), appUser
                .getUserPhoto()));
        map.put("userPhotoPic\"; filename=\"" + file.getName(), userPhoto);
        setRequest();
    }

    /**
     * 修改用户信息
     */
    protected void setRequest() {
        map.put("personalNote", RequestBody.create(MediaType.parse("text/plain"), tvPersonalNote.getText().toString()));
        //if (AppUtility.isNotEmpty(userInterest))
        map.put("userInterest", RequestBody.create(MediaType.parse("text/plain"), userInterest));
        map.put("userSex", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(sex)));
        map.put("userBirth", RequestBody.create(MediaType.parse("text/plain"), tvBirDate.getText().toString()));
        map.put("nickName", RequestBody.create(MediaType.parse("text/plain"), tvNickname.getText().toString()));
        if (!isClient()) {
            map.put("workStatus", RequestBody.create(MediaType.parse("text/plain"), workState));
        }
        call = app.getApiService().updatePersonalInfo(appUser.getUserNum(), map);
        mangementPresenter.accountMangement(call);
    }

    @SuppressWarnings("ResourceType")
    private void creatServiceLables(List<String> stringList) {
        flowView.removeAllViews();
        ViewGroup.MarginLayoutParams lp =
                new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8, 5, 0, 0);
        for (String info : stringList) {
            if (AppUtility.isNotEmpty(info)) {
                TextView view = new TextView(mContext);
                view.setText(info);
                view.setTextColor(Color.WHITE);
                view.setTextSize(12);
                view.setLayoutParams(lp);
                view.setBackgroundResource(R.drawable.shape_text_station_label);
                flowView.addView(view);
            }
        }
    }

    /**
     * 出生日期
     *
     * @param format
     */
    private void getDate(String format) {
        tvBirDate.setText(format);
        setRequest();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_SIGNATURE) {
//                personalNote = data.getStringExtra(C.IntentKey.UPDATE_SIGNATURE);
                tvPersonalNote.setText(data.getStringExtra(C.IntentKey.UPDATE_SIGNATURE));
                setRequest();
            } else if (requestCode == UPDAET_NICKNAME) {
                tvNickname.setText(data.getStringExtra(C.IntentKey.UPDATE_NICKNAME));
                setRequest();
            } else if (requestCode == AndroidImagePicker.REQ_CAMERA) {
                if (!TextUtils.isEmpty(androidImagePicker.getCurrentPhotoPath())) {
                    AndroidImagePicker.galleryAddPic(mContext,
                            androidImagePicker.getCurrentPhotoPath());
                    androidImagePicker.notifyPictureTaken();

                }
            }
        }
    }

    @Override
    protected void getSexed(String s, int state) {
        tvSex.setText(s);
        sex = state;
        setRequest();
    }

    /**
     * 工作状态
     */
    private void getWorkState() {
        btnWorkState.setSelected(!btnWorkState.isSelected());
        workState = (btnWorkState.isSelected() ? C.WORKSTATE_NOT_BUSY : C.WORKSTATE_REST);
        setRequest();
    }

    //日期
    public void getBirDate() {
        CharSequence tvBirDateText = tvBirDate.getText();
        if (!TextUtils.isEmpty(tvBirDateText)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = simpleDateFormat.parse(tvBirDateText.toString());
                mCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            mCalendar.setTimeInMillis(System.currentTimeMillis());
        }
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog.updateDate(year, month, day);
        mDatePickerDialog.show();
    }

    @Override
    public void accountMangementSuccess(User user) {
        app.setUser(user);
        HXHelper.getInstance().getUserProfileManager().setUserInfo(FileUtil.getFileUrl(user.getUserPhoto()), user.getNickName());
        EventBus.getDefault().post(C.EventKey.UPDATE_USER_INFO);//刷新数据
        initView();
    }

    @Override
    public void showTipMsg(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingView() {
//        showLoading("处理中");
    }

    @Override
    public void dismissLoadingView() {

    }

    @Override
    public void accountMangementFail() {
        initView();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onInterestsResult(String result) {
        userInterest = result;
        creatServiceLables(Arrays.asList(result.split(",")));
        setRequest();
    }
}
