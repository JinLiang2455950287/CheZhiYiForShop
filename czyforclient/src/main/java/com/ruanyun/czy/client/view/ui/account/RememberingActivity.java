package com.ruanyun.czy.client.view.ui.account;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.chezhiyi.commonlib.model.AttachInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.presenter.RememberingBookPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.RememberingBookMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.EditInputFilter;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.chezhiyi.commonlib.view.adapter.RememberingRvAdapter;
import com.ruanyun.imagepicker.AndroidImagePicker;
import com.ruanyun.imagepicker.bean.ImageItem;
import com.ruanyun.imagepicker.widget.RYAddPictureView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author 客户端的记一笔界面
 * @ClassName: ${file_name}
 * @Description:
 * @date ${date}${time}
 */
public class RememberingActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener,
        RYAddPictureView.onPickResultChangedListener, RememberingBookMvpView {

    public static final int PAGE_MOD = 2;//修改
    public static final int PAGE_ADD = 1;//添加

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.et_remembering_money)
    EditText etRememberingMoney;//支出金额
    @BindView(R.id.tv_remembering_time)
    TextView tvRememberingTime;//支出时间
    @BindView(R.id.et_remembering_remarks)
    EditText etRememberingRemarks;//备注
    @BindView(R.id.tv_remembering_max_num)
    TextView tvRememberingMaxNum;//备注最大输入字数
    @BindView(R.id.rv_expenditure_classify)
    RecyclerView rvExpenditureClassify;//支出分类
    List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    private RememberingRvAdapter rvAdapter;
    private int ExpenditureClassifyType = 0;//选择的工单服务类型
    private String time;//上传的时间
    private String price;//金额
    private ArrayList<ImageItem> imageList;//添加图片的集合
    private int addPicNumMax = 8;//最大添加图片数
    private int[] delAttachInfoId = null;//删除图片的集合
    private int index = 0;
    Calendar calendar = Calendar.getInstance();
    //获取时间
    private int year;
    private int month;
    private int day;
    private DatePickerDialog dialog;//时间dialog

    private int addUpData;//判断是添加还是修改(1为添加，2为修改)
    private AccountBookInfo bookInfo;//修改传过来的实体
    private Intent alterIntent;
    @BindView(R.id.remembering_photo_add_delete)
    RYAddPictureView rememberingPhotoAddDelete;//添加图片的控件
    private InputFilter[] filter = {new EditInputFilter()};

    private RememberingBookPresenter presenter = new RememberingBookPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_remembering);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
        init();
        initData();
        setListener();
        rememberingPhotoAddDelete.setOnListeners();
        rememberingPhotoAddDelete.setOnPickResultChangedListener(this);//删除图片后的回调监听
        rememberingPhotoAddDelete.setSizeLimit(addPicNumMax);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== rememberingPhotoAddDelete.requestCode){
            if(resultCode == RESULT_OK){
                rememberingPhotoAddDelete.onImageActivityResult();
            }
        }
    }

    private void initView() {
        etRememberingMoney.setFilters(filter);
        topbar.setTttleText("记一笔")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("完成")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
    }

    private void init() {

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        tvRememberingTime.setText(year + "年" + ((month + 1) > 9 ? (month + 1 + "") : ("0" + (month + 1))) + "月" + (
                (day) > 9 ? (day + "") : ("0" + (day)))+"日");
        time = year + "-" + ((month + 1) > 9 ? (month + 1 + "") : ("0" + (month + 1))) + "-" + ((day) > 9 ? (day +
                "") : ("0" + (day)));
    }

    /**
     * 跳转过来加载数据
     */
    private void initData() {
        alterIntent = getIntent();
        addUpData = alterIntent.getIntExtra(C.IntentKey.ADDUPDATA, PAGE_ADD);// 默认添加
        stairprojectTypes = alterIntent.getParcelableArrayListExtra(C.IntentKey.STAIR_PROJECT_TYPES);
        rvExpenditureClassify.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager
                .HORIZONTAL));
        //RecyclerView设置Adapter
        rvAdapter = new RememberingRvAdapter(mContext, R.layout.item_rv_remembering_projecttype, stairprojectTypes, ExpenditureClassifyType);
        rvExpenditureClassify.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                upDataExpenditureClassifyChecked(position);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        if (addUpData == PAGE_MOD) {   //这是修改跳转过来的
            showDataOnActivity();
        }
    }

    /**
     * 修改 显示
     */
    private void showDataOnActivity() {
        bookInfo = alterIntent.getParcelableExtra(C.IntentKey.ACCOUNT_BOOK_INFO);
        BigDecimal bookPrice = bookInfo.getBookPrice();
        String text  =  bookPrice==null?"0":(bookPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        double price = Math.abs(Double.parseDouble(text));
        etRememberingMoney.setText(String.valueOf(price));
        String showTime = bookInfo.getBookDate();
        showTime = showTime.substring(0,4)+"年"+showTime.substring(5,7)+"月"+showTime.substring(8,10)+"日";
        time = showTime.substring(0,4)+"-"+showTime.substring(5,7)+"-"+showTime.substring(8,10);
        tvRememberingTime.setText(showTime);
        String remarks = bookInfo.getRemark();
        if(remarks!=null&&!"".equals(remarks)) {
            etRememberingRemarks.setText(remarks);
            tvRememberingMaxNum.setText(remarks.length()+"/20");
        }
        String projectNum = bookInfo.getProjectNum();
        setProjectNumChecked(projectNum);
        List<AttachInfo> attachInfos =  bookInfo.getAttachInfoList();
        int size = attachInfos.size();
        if (size > 0) {
            delAttachInfoId = new int[size];
        }
        imageList = new ArrayList<>();
        ImageItem imageitem;
        for (AttachInfo attachInfo : attachInfos) {
            imageitem = new ImageItem();
            imageitem.attachId = attachInfo.getAttachId();
            imageitem.path = FileUtil.getImageUrl(attachInfo.getFilePath());
            imageitem.type = ImageItem.TYPE_REMOTE;
            imageList.add(imageitem);
        }
        rememberingPhotoAddDelete.onImagePickComplete(imageList,rememberingPhotoAddDelete.requestCode);
    }

    /**
     * 计算选中的支出分类
     */
    private void setProjectNumChecked(String projectNum) {
        for(int i=0;i<stairprojectTypes.size();i++){
            if(projectNum.equals(stairprojectTypes.get(i).getProjectNum())){
                upDataExpenditureClassifyChecked(i);
                return;
            }
        }
    }

    /**
     * 刷新支出分类选中Adapter
     */
    private void upDataExpenditureClassifyChecked(int position) {
        ExpenditureClassifyType = position;
        rvAdapter.setSelectPosition(ExpenditureClassifyType);
        rvAdapter.notifyDataSetChanged();
    }


    /**
     * 备注文字长度监听
     */
    private void setListener() {
        etRememberingRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int remarksLength = (etRememberingRemarks.getText() + "").length();
                tvRememberingMaxNum.setText(remarksLength + "/20");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rememberingPhotoAddDelete.destroyListeners();
        presenter.detachView();
    }

    /**
     * 日期选择监听
     */
    @OnClick({R.id.tv_remembering_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_remembering_time:
                showDateDialog();
                break;
        }
    }

    /**
     * 显示时间Diglog
     */
    private void showDateDialog() {
        dialog = new DatePickerDialog(mContext, R.style.time_picker_style, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int monthOfYear, int dayOfMonth) {
                if (myear > calendar.get(Calendar.YEAR)) {
                    return;
                } else if (myear == calendar.get(Calendar.YEAR)) {
                    if (monthOfYear > calendar.get(Calendar.MONTH)) {
                        return;
                    } else if (monthOfYear == calendar.get(Calendar.MONTH)) {
                        if (dayOfMonth > calendar.get(Calendar.DAY_OF_MONTH)) {
                            return;
                        }
                    }
                }
                tvRememberingTime.setText(myear + "年" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1 + "") : ("0" +
                        (monthOfYear + 1))) + "月" +
                        ((dayOfMonth) > 9 ? (dayOfMonth + "") : ("0" + (dayOfMonth)))+"日");
                time = myear + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1 + "") : ("0" + (monthOfYear + 1))) +
                        "-" + ((dayOfMonth) > 9 ? (dayOfMonth + "") : ("0" + (dayOfMonth)));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialog.getDatePicker();
        Date date = new Date();
        datePicker.setMaxDate(date.getTime());
        dialog.show();
    }

    /**
     * TopBar监听
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            RememberingActivity.this.finish();
        } else if (id == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right) {
            try {
                AddUpDataAccountBook();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加修改记账本
     */
    private void AddUpDataAccountBook() throws ParseException {
        HashMap<String, RequestBody> bobyMap = new HashMap<>();
        if (TextUtils.isEmpty(etRememberingRemarks.getText().toString())) {
            AppUtility.showToastMsg("备注信息不能为空！");
            return;
        }
        if (addUpData == PAGE_MOD) {
            bobyMap.put("bookNum", RequestBody.create(MediaType.parse("text/plain"), bookInfo.getBookNum()));
            //单条编号【修改时必须传值】
        }
        bobyMap.put("bookDate", RequestBody.create(MediaType.parse("text/plain"), time));//日期时间
        price = etRememberingMoney.getText() + "";
        if ("".equals(price)) {
            AppUtility.showToastMsg("请填写金额");
            return;
        }
        BigDecimal bd = new BigDecimal(price);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        bobyMap.put("bookPrice", RequestBody.create(MediaType.parse("text/plain"), bd.toString()));//金额
        bobyMap.put("remark", RequestBody.create(MediaType.parse("text/plain"), (etRememberingRemarks.getText() + "")));//备注
        bobyMap.put("projectNum", RequestBody.create(MediaType.parse("text/plain"),
                stairprojectTypes.get(ExpenditureClassifyType).getProjectNum()));//支出类型
        //修改时用，删除图片ID int[]
        String delIds = StringUtil.getDelAttachIdtoString(delAttachInfoId);
        if (delIds != null){
            bobyMap.put("delAttachInfoId", RequestBody.create(MediaType.parse("text/plain"), delIds));
        }
        //多张图片【在修改时只传递添加的图片流】
        imageList = (ArrayList<ImageItem>) rememberingPhotoAddDelete.getImageList();
        String path;//图片地址
        for (int i=0;i<imageList.size();i++) {
            if(!imageList.get(i).isAdd){
                if(imageList.get(i).type==ImageItem.TYPE_LOCAL){
                    path = imageList.get(i).path;
                    if(imageList.get(i).name==null|"".equals(imageList.get(i).name)){
                        bobyMap.put("bookCashPic\"; filename=\"" + path.substring(path.lastIndexOf("/")+1),
                                RequestBody.create(MediaType.parse("image/jpeg"), new File(path)));
                    }else {
                        bobyMap.put("bookCashPic\"; filename=\"" + imageList.get(i).name,
                                RequestBody.create(MediaType.parse("image/jpeg"), new File(path)));
                    }
                }
            }
        }
        //添加或修改记账本
        Call<ResultBase<PageInfoBase<AccountBookInfo>>> call = app.getApiService().addUpdataAccountBook(app.getCurrentUserNum(), bobyMap);
        presenter.addAndUpdateAccountBook(call);
    }

    /**
     * 请求失败提示
     */
    private void onErrorHint(String msg) {
        if (addUpData == PAGE_MOD) AppUtility.showToastMsg("修改失败" + msg);
        else AppUtility.showToastMsg("添加失败" + msg);
    }

    /**
     * 删除图片回调
     * @param item
     */
    @Override
    public void onPicDelete(ImageItem item) {
        if(item.type==ImageItem.TYPE_REMOTE && delAttachInfoId != null){
            delAttachInfoId[index] = item.attachId;
            index++;
        }
    }

    @Override
    public void showLoadingView() {
        if(addUpData==PAGE_MOD) app.loadingDialogHelper.showLoading(this, "修改中...");
        else app.loadingDialogHelper.showLoading(this, "保存中...");
    }

    @Override
    public void dismissLoadingView() {
        app.loadingDialogHelper.dissMiss();
    }

    @Override
    public void showAddOrUpAccountBookSuccessTip(ResultBase resultBase) {
        Intent intent = new Intent();
        if(addUpData==PAGE_MOD){
            AppUtility.showToastMsg("修改成功");
            setResult(RESULT_OK, intent);
        }
        else AppUtility.showToastMsg("添加成功");
        finish();
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST,""));
    }

    @Override
    public void showAddOrUpAccountBookErrorOrFailTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

}
