package com.ruanyun.czy.client.view.ui.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.chezhiyi.commonlib.model.AttachInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.presenter.AccountBookDeletePresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.AccountBookDeleteMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.ParticularsPhotoAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 客户端的记账本明细界面
 * @ClassName: ${file_name}
 * @Description:
 * @date ${date}${time}
 */
public class ParticularsActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, AccountBookDeleteMvpView {

    public static final int REQ_REMEMBER = 1000;
    Topbar topbar;
    @BindView(R.id.tv_particulars_time)
    TextView tvParticularsTime;
    @BindView(R.id.tv_particulars_month)
    TextView tvParticularsMonth;
    @BindView(R.id.tv_project_type)
    TextView tvProjectType;
    @BindView(R.id.tv_particulars_remarks)
    TextView tvParticularsRemarks;
    @BindView(R.id.rv_particulars_photos)
    RecyclerView rvParticularsPhotos;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;

    AccountBookInfo bookInfo;
    ParticularsPhotoAdapter rvAdapter;

    List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    List<AttachInfo> AttachInfoList;//图片集合

    private AccountBookDeletePresenter presenter = new AccountBookDeletePresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_particulars);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        topbar = getView(com.ruanyun.chezhiyi.commonlib.R.id.topbar);
        topbar.setTttleText("明细详情")
                .setBackBtnEnable(true)
                .setRightText("修改")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .onRightTextClick()
                .setTopbarClickListener(this);
    }

    /**
     * 加载数据
     */
    private void initData() {
        Intent intent = getIntent();
        bookInfo = intent.getParcelableExtra(/*"AccountBookInfo"*/C.IntentKey.ACCOUNT_BOOK_INFO);
        stairprojectTypes = intent.getParcelableArrayListExtra(/*"StairprojectTypes"*/C.IntentKey.STAIR_PROJECT_TYPES);
        String time = bookInfo.getBookDate();
        time = time.substring(5,7)+"月"+time.substring(8,10)+"日";
        tvParticularsTime.setText(time+"");
        String bookPrice = bookInfo.getBookPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        tvParticularsMonth.setText(bookPrice);
        tvProjectType.setText(setProjectNumChecked(bookInfo.getProjectNum()));
        tvParticularsRemarks.setText(bookInfo.getRemark()+"");
        rvParticularsPhotos.setLayoutManager(new GridLayoutManager(mContext,2));
        AttachInfoList = new ArrayList<>();
        AttachInfoList = bookInfo.getAttachInfoList();
        rvAdapter = new ParticularsPhotoAdapter(mContext, R.layout.item_particulars_photo,AttachInfoList);
        rvParticularsPhotos.setAdapter(rvAdapter);
        rvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                AppUtility.showBigImage(mContext, FileUtil.getImageUrl(AttachInfoList.get(position).getFilePath()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
    }

    private String setProjectNumChecked(String projectNum) {
        for (ProjectType stairprojectType : stairprojectTypes) {
            if(projectNum.equals(stairprojectType.getProjectNum())){
               return "【"+stairprojectType.getProjectName()+"】";
            }
        }
        return "【其他】";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQ_REMEMBER){
            if (resultCode == RESULT_OK){
                this.finish();
            }
        }
    }

    /**
     * topbar监听
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        } else if (id == com.ruanyun.chezhiyi.commonlib.R.id.tv_title_right) {
            Intent intent = new Intent();
            intent.setClass(mContext,RememberingActivity.class);
            intent.putExtra(C.IntentKey.ACCOUNT_BOOK_INFO/*"AccountBookInfo"*/,bookInfo);
            intent.putExtra(/*"AddUpData"*/C.IntentKey.ADDUPDATA,RememberingActivity.PAGE_MOD);
            intent.putParcelableArrayListExtra(/*"StairprojectTypes"*/C.IntentKey.STAIR_PROJECT_TYPES,(ArrayList) stairprojectTypes);
            startActivityForResult(intent, REQ_REMEMBER);
        }
    }

    /**
     * 删除监听
     */
    @OnClick(R.id.iv_delete)
    public void onClick() {
        confirmDeleteDialog();

    }

    /**
     * 确定删除Dialog
     */
    private void confirmDeleteDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("确认删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccountBook();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 发送删除请求
     */
    private void deleteAccountBook() {
        Call<ResultBase> call = app.getApiService().deleteAccountBook(app.getCurrentUserNum(),bookInfo.getBookNum());
        presenter.deleteAccountBook(call);
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showDeleteAccountBookTip(String msg) {
        AppUtility.showToastMsg(msg);
        finish();
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST,""));
    }

}
