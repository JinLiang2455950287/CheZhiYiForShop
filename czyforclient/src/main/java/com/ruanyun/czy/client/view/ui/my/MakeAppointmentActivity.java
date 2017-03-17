package com.ruanyun.czy.client.view.ui.my;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AppointmentInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.params.MakeAppointmentParams;
import com.ruanyun.chezhiyi.commonlib.presenter.MakeAppointmentPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.MakeAppointmentMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ServerAdapter;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.RemarkActivity;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Description ：在线预约
 * <p>
 * Created by ycw on 2016/9/13.
 */
public class MakeAppointmentActivity extends AutoLayoutActivity
        implements MakeAppointmentMvpView,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        Topbar.onTopbarClickListener {


    private static final int REQ_REMAKE = 288;
    @BindView(R.id.recyclerView_serve_type)
    RecyclerView recyclerViewServeType;
    @BindView(R.id.tv_appointment_time)
    TextView tvAppointmentTime;
    @BindView(R.id.tv_serve_remake)
    TextView tvServeRemake;
    @BindView(R.id.btn_to_appointment)
    Button btnToAppointment;
    @BindView(R.id.topbar)
    Topbar topbar;

    private MakeAppointmentPresenter presenter = new MakeAppointmentPresenter();
    private List<ProjectType> projectTypeListAll;
    private List<ProjectType> firstList;
    private List<ProjectType> dataList; //adapter的数据源
    private ServerAdapter adapter;
    private Map<String, List<ProjectType>> cacheProject = new HashMap<>();
    private MakeAppointmentParams params = new MakeAppointmentParams();
//    private MyDatePickDialog datePickerDialog;
//    private MyTimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private String predictShopTime;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_make_appointment_new);
        ButterKnife.bind(this);
        initView();
        presenter.attachView(this);
    }

    private void initView() {
        topbar.setTttleText("在线预约")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        // : 2016/10/25 修改为支持预约的集合
        projectTypeListAll = DbHelper.getInstance().getAllMakeAbleSeviceTypes();
        LogX.d(TAG, "--------project------> \n" + projectTypeListAll.toString());
        // : 2016/10/25 修改为支持预约的一级集合
        firstList = DbHelper.getInstance().getMakeAbleSeviceTypes();//获取一级的数据
        dataList = presenter.getFirstType(firstList); //一级父类集合设置为true
        //initRecyclerView
        GridLayoutManager serverManager = new GridLayoutManager(mContext, 4);
        serverManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return dataList.get(position).isParent() ? 4 : 1;
            }
        });
        recyclerViewServeType.setLayoutManager(serverManager);
        adapter = new ServerAdapter(mContext, dataList);
        recyclerViewServeType.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                ProjectType info = (ProjectType) o;
                if (info.isParent()) { //是一级
                    ImageView imageView = (ImageView) holder.itemView.findViewById(com.ruanyun.chezhiyi.commonlib.R.id.iv_type_name);
                    imageView.setSelected(!imageView.isSelected());

                    //缓存Map中
                    List<ProjectType> cacheSecondList = cacheProject.get(info.getProjectNum());
                    if (cacheSecondList == null || cacheSecondList.size() == 0) {
                        cacheSecondList = presenter.getTypeByProjectNum(info.getProjectNum(), projectTypeListAll);
                        cacheProject.put(info.getProjectNum(), cacheSecondList);
                    }

                    if (info.isSelected()) { //没有选中
                        if (!cacheSecondList.isEmpty()) {
                            dataList.removeAll(cacheSecondList);
                            adapter.notifyItemRangeRemoved(position + 1, cacheSecondList.size());
                            info.setSelected(false);
                        }
                    } else { //选中
                        if (!cacheSecondList.isEmpty()) {
                            dataList.addAll(position + 1, cacheSecondList);
                            adapter.notifyItemRangeInserted(position + 1, cacheSecondList.size());
                            info.setSelected(true);
                        }
                    }
                    adapter.notifyItemChanged(position);

                } else {  //选中

                    info.setSelected(!info.isSelected());
                    adapter.notifyItemChanged(position);
                    for (ProjectType projectType : firstList) {
                        if (info.getParentNum().equals(projectType.getProjectNum())) {

                            StringBuilder sb = new StringBuilder();
                            if (AppUtility.isNotEmpty(projectType.getProjectAllSelectName())) {
                                sb.append(projectType.getProjectAllSelectName()).append("，");
                            }
                            if (info.isSelected()) {
                                sb.append(info.getProjectName());
                            } else { //取消对应的字符串
                                sb = sb.deleteCharAt(sb.length() - 1);
                                int index = sb.indexOf(info.getProjectName());  // 第一个字符的位置
                                if (index != -1) {
                                    int lastIndex = index + info.getProjectName().length();// 最后一个字符的位置
                                    int start = index > 0 ? index - 1 : 0;
                                    int end = lastIndex >= sb.length() ? sb.length() : (lastIndex + 1);
                                    if (start > 0 && end < sb.length()) {
                                        sb.replace(start, end, "，");
                                    } else {
                                        sb.replace(start, end, "");
                                    }
                                }
                            }
                            projectType.setProjectAllSelectName(sb.toString());
                            adapter.notifyItem(projectType);
                            break;
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        calendar = Calendar.getInstance();
        datePickerDialog = getDatePickerDialog();
        timePickerDialog = getTimePickerDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.btn_to_appointment, R.id.tv_appointment_time, R.id.ll_serve_remake})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_to_appointment:  //预约
                //确认预约
                List<ProjectType> projectTypeList = presenter.getProjectNumInfos(firstList, cacheProject);
                if (projectTypeList.size() == 0) {
                    AppUtility.showToastMsg("请选择服务项目");
                    return;
                }
                String projectNum = new Gson().toJson(projectTypeList);
                if (TextUtils.isEmpty(predictShopTime)) {
                    AppUtility.showToastMsg("请选择预计时间！");
                    return;
                }
                params.setProjectNum(projectNum);
                params.setPredictShopTime(predictShopTime);
                params.setRemark(tvServeRemake.getText().toString());
                Call<ResultBase<AppointmentInfo>> call = app.getApiService().makeAppointment(app.getCurrentUserNum(), params);
                presenter.makeAppointment(call);
                break;

            case R.id.tv_appointment_time:  // 选择时间
                datePickerDialog.show();
                datePickerDialog.getButton(BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.theme_color_default));
                datePickerDialog.getButton(BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.theme_color_default));
                break;

            case R.id.ll_serve_remake:  //设置备注
                Intent intent = new Intent(mContext, RemarkActivity.class);
                intent.putExtra(C.IntentKey.TOPBAR_TITLE, "设置备注");
                intent.putExtra(C.IntentKey.EDIT_CONTEXT, tvServeRemake.getText().toString());
                startActivityForResult(intent, REQ_REMAKE);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_REMAKE) {
            if (resultCode == RESULT_OK) {
                String remake = data.getStringExtra(C.IntentKey.UPDATE_SIGNATURE);
                tvServeRemake.setText(remake);
            }
        }
    }

    @Override
    public void showMakeAppointmentLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void makeAppointmentOnsuccess(AppointmentInfo appointmentInfo) {
        // : 2016/9/19 在线预约成功后
        finish();
    }

    @Override
    public void makeAppointmentOnResult() {
        dissMissLoading();
    }

    @Override
    public void showTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public Context getContext() {
        return null;
    }


    /**
     * 获取  DatePickerDialog
     *
     * @return
     */
//    @NonNull
//    private MyDatePickerDialog getMyDatePickerDialog() {
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        MyDatePickerDialog myDatePickDialog = new MyDatePickerDialog(mContext, R.style.date_picker_style,  this, year, month, day);
//        myDatePickDialog.setButton(BUTTON_POSITIVE, "选择时间", myDatePickDialog);
//        myDatePickDialog.setButton(BUTTON_NEGATIVE, "取消", myDatePickDialog);
//        myDatePickDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
//        myDatePickDialog.setCancelable(true);
//        return myDatePickDialog;
//    }

    /**
     * 获取  DatePickerDialog
     *
     * @return
     */
    @NonNull
    private DatePickerDialog getDatePickerDialog() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog myDatePickDialog = new DatePickerDialog(mContext, R.style.date_picker_style,  this, year, month, day);
        myDatePickDialog.setButton(BUTTON_POSITIVE, "选择时间", myDatePickDialog);
        myDatePickDialog.setButton(BUTTON_NEGATIVE, "取消", myDatePickDialog);
        myDatePickDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        myDatePickDialog.setCancelable(true);
        return myDatePickDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        LogX.d(TAG, "-----year -> " + year + "-----monthOfYear -> " + monthOfYear + "-----dayOfMonth -> " + dayOfMonth);

        if (view.equals(datePickerDialog.getDatePicker())) {
            this.year = year;
            this.monthOfYear = monthOfYear;
            this.dayOfMonth = dayOfMonth;
            timePickerDialog.show();
            timePickerDialog.getButton(BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.theme_color_default));
            timePickerDialog.getButton(BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.theme_color_default));
        }
    }

    /**
     * 获取  TimePickerDialog
     *
     * @return
     */
//    @NonNull
//    private MyTimePickerDialog getMyTimePickerDialog() {
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        MyTimePickerDialog timePickerDialog = new MyTimePickerDialog(mContext, R.style.time_picker_style, this, hour, minute, true);
//        timePickerDialog.setButton(BUTTON_POSITIVE, "完成", timePickerDialog);
//        timePickerDialog.setButton(BUTTON_NEGATIVE, "取消", timePickerDialog);
//        timePickerDialog.setCancelable(true);
//        return timePickerDialog;
//    }

    /**
     * 获取  TimePickerDialog
     *
     * @return
     */
    @NonNull
    private TimePickerDialog getTimePickerDialog() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, R.style.time_picker_style, this, hour, minute, true);
        timePickerDialog.setButton(BUTTON_POSITIVE, "完成", timePickerDialog);
        timePickerDialog.setButton(BUTTON_NEGATIVE, "取消", timePickerDialog);
        timePickerDialog.setCancelable(true);
        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LogX.d(TAG, "-----------hourOfDay -> " + hourOfDay + "   ---------minute -> " + minute);
        if (app.getStoreInfo() != null) {
            String start = app.getStoreInfo().getBeginTime();
            String end = app.getStoreInfo().getEndTime();
            long minTime = StringUtil.getTimeLong(start);
            long maxTime = StringUtil.getTimeLong(end);
            long currentTime = StringUtil.getSelectStringTime(hourOfDay, minute);

            if (minTime < currentTime && currentTime < maxTime) {
                //  符合条件的
                calendar.set(this.year, this.monthOfYear, this.dayOfMonth, hourOfDay, minute);
                Date date = calendar.getTime();
                calendar.setTimeInMillis(System.currentTimeMillis());
                Date current = calendar.getTime();
                if (date.before(current)) {
                    predictShopTime = null;
                    // todo 正确的时间
                    AppUtility.showToastMsg("请选择正确的时间！");
                    tvAppointmentTime.setText(StringUtil.getTimeStr("yyyy年MM月dd日 HH:mm", current));
                } else {
                    predictShopTime = StringUtil.getTimeStr("yyyy-MM-dd HH:mm", date);
                    tvAppointmentTime.setText(StringUtil.getTimeStr("yyyy年MM月dd日 HH:mm", date));
                }

            } else {
                AppUtility.showToastMsg(String.format("请选择%s-%s的时间", start, end));
            }
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        }
    }
}
