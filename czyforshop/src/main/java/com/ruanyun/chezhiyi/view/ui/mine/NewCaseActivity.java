package com.ruanyun.chezhiyi.view.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AttachInfo;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.presenter.CaseLibraryPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.ruanyun.chezhiyi.commonlib.view.CaseLibraryMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.RememberingRvAdapter;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.imagepicker.bean.ImageItem;
import com.ruanyun.imagepicker.widget.RYAddPictureView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 新建 修改 案例 界面
 * Created by ycw on 2016/9/8.
 */
public class NewCaseActivity extends AutoLayoutActivity
        implements /*TextWatcher,*/ RYAddPictureView.onPickResultChangedListener,
        CaseLibraryMvpView, Topbar.onTopbarClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edit_case_title)
    EditText editCaseTitle;
    @BindView(R.id.edit_case_content)
    EditText editCaseContent;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.grid_case)
    RYAddPictureView gridCase;

    private CaseInfo caseInfo = null;   //修改时显示的数据
    private static final String TYPE_NEW = "New";
    private static final String TYPE_MOD = "Mod";
    private String pageType = TYPE_NEW;
    private CaseLibraryPresenter presenter = new CaseLibraryPresenter();
    private HashMap<String, RequestBody> caseMapParams = new HashMap<>();
    private int[] delAttachInfoId = null;
    private int index = 0;
    private String libraryType;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_new_case);
        ButterKnife.bind(this);
        presenter.attachView(this);
        initView();
    }

    private void initView() {
        gridCase.setOnListeners();
        gridCase.setSizeLimit(9);
        editCaseContent.addTextChangedListener(new EmojiFiltrationTextWatcher(editCaseContent) {
            @Override
            public void emojiFiltAfterTextChanged(Editable s) {
                super.emojiFiltAfterTextChanged(s);
                String content = editCaseContent.getText().toString();
                int length = content.length();
                changeCountNum(length);
            }
        });

        caseInfo = getIntent().getParcelableExtra(C.IntentKey.CASE_INFO);
        topbar.setBackBtnEnable(true)
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onBackBtnClick()
                .setRightText("保存")
                .onRightTextClick()
                .setTopbarClickListener(this);
        changeCountNum(0);
        if (caseInfo != null) {
            intoModView();
            pageType = TYPE_MOD;
            initCaseType();
        } else {
            intoNewView();
            initCaseType();
        }
    }

    /**
     * 初始化 案例分类
     */
    private void initCaseType() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        List<ProjectType> list = getUserProjectType();

        final RememberingRvAdapter adapter = new RememberingRvAdapter(mContext, R.layout.item_rv_remembering_projecttype, list, 0);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
                libraryType = adapter.getDatas().get(position).getProjectNum();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        if (adapter.getDatas() != null && adapter.getDatas().size() > 0) {
            if (caseInfo != null)
                adapter.setSelectPosition(getProjectNumIndex(caseInfo.getLibraryType(), list));
            libraryType = (adapter.getDatas().get(adapter.getSelectPosition())).getProjectNum();
        }
    }

    /**
     * 计算选中的支出分类
     */
    private int getProjectNumIndex(String projectNum, List<ProjectType> userTypeList) {
        for (int i = 0; i < userTypeList.size(); i++) {
            if (projectNum.equals(userTypeList.get(i).getProjectNum())) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取用户的类型
     *
     * @return
     */
    private List<ProjectType> getUserProjectType() {
        List<ProjectType> list = new ArrayList<>();
        // TODO: 2016/9/13 需要修改为数据库读取数据
        List<ProjectType> allProject = DbHelper.getInstance().getAllSeviceTypes();
        if (app.getUser() == null || app.getUser().getProjectTypeList() == null) return list;
        List<ProjectType> projectTypes = app.getUser().getProjectTypeList();
        for (ProjectType type : projectTypes) {
            for (ProjectType project : allProject) {
                if (type.getProjectNum().equals(project.getProjectNum())) {
                    list.add(project);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 新建案例库
     */
    private void intoNewView() {
        topbar.setTttleText("新建案例");
    }

    /**
     * 修改案例
     */
    private void intoModView() {
        topbar.setTttleText("修改案例");
        editCaseTitle.setText(caseInfo.getLibraryName());
        editCaseContent.setText(caseInfo.getLibraryIntroduce());
        editCaseContent.setSelection(caseInfo.getLibraryIntroduce().length());
        //图片处理
        List<AttachInfo> itemList = caseInfo.getAttachInfoList();
        int size = itemList.size();
        if (size > 0) {
            delAttachInfoId = new int[size];
            gridCase.refreshImage(getImageItems(itemList));
        }
        gridCase.setOnPickResultChangedListener(this);
    }

    /**
     * 将 AttachInfo　集合转化成　　　ＩｍａｇｅＩｔｅｍ　集合
     *
     * @param imageList
     * @return
     */
    @NonNull
    private List<ImageItem> getImageItems(List<AttachInfo> imageList) {
        List<ImageItem> itemList = new ArrayList<>();
        if (imageList.size() == 0) {
            return itemList;
        }
        for (AttachInfo info : imageList) {
            ImageItem item = new ImageItem();
            item.path = FileUtil.getImageUrl(info.getFilePath());
            item.name = info.getFileName();
            item.attachId = info.getAttachId();
            item.type = ImageItem.TYPE_REMOTE;
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == gridCase.requestCode) {
                gridCase.onImageActivityResult();
            }
        }
    }


    /**
     * 改变数目
     */
    private void changeCountNum(int length) {
        tvCount.setText(new StringBuilder().append(length).append("/1000").toString());
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            String title = editCaseTitle.getText().toString();
            String content = editCaseContent.getText().toString();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                AppUtility.showToastMsg("标题和内容不能为空！");
                return;
            }
            // 请求 参数 根据type 判断
            caseMapParams.clear();
            //门店num【暂无使用】
            //caseMapParams.put("storeNum", RequestBody.create(MediaType.parse("text/plain"), ""));
            //案例名称【不变动时，需要把原值传回后台】
            caseMapParams.put("libraryName", RequestBody.create(MediaType.parse("text/plain"), title));
            //案例介绍【不变动时，需要把原值传回后台】
            caseMapParams.put("libraryIntroduce", RequestBody.create(MediaType.parse("text/plain"), content));
            //案例库分类【读取服务类型一级】
            // TODO: 2016/9/9 分类选择 处理
            if (TextUtils.isEmpty(libraryType)) {
                AppUtility.showToastMsg("案例分类不能为空");
                return;
            }
            caseMapParams.put("libraryType", RequestBody.create(MediaType.parse("text/plain"), libraryType));

            if (pageType.equals(TYPE_MOD)) {
                //主键【修改时需要传值】
                caseMapParams.put("libraryId", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(caseInfo.getLibraryId())));
                //案例编码【业务主键，修改时需要传值】
                caseMapParams.put("libraryNum", RequestBody.create(MediaType.parse("text/plain"), caseInfo.getLibraryNum()));
                //附件ID 附件数据结构（TAttachInfo） 表中的attachId【修改时用】
                String delIds = StringUtil.getDelAttachIdtoString(delAttachInfoId);
                if (delIds != null)
                    caseMapParams.put("delAttachInfoId", RequestBody.create(MediaType.parse("text/plain"), delIds));
            }
            //案例图片
            List<ImageItem> addItemList = getLocalPicParams(gridCase.getImageList());
            if (!addItemList.isEmpty()) {
                presenter.addAndUpdateCaseLibrary(caseMapParams, addItemList);
            } else {
                presenter.updateCaseInfo(caseMapParams);
            }
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

    @Override
    public void onPicDelete(ImageItem item) {
        if (item.type == ImageItem.TYPE_REMOTE && delAttachInfoId != null) {
            // TODO: 2016/9/9 添加删除的图片的   id
            delAttachInfoId[index] = item.attachId;
            index++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gridCase.destroyListeners();
        presenter.detachView();
    }


    /**
     * 请求接口的
     */
    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showAddOrUpCaseLibSuccessTip(ResultBase resultBase) {
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST, ""));// 刷新列表
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_WEB, ""));// 刷新详情
        AppUtility.showToastMsg(resultBase.getMsg());
        finish();
    }

    @Override
    public void showAddOrUpCaseLibErrorTip(ResultBase resultBase) {
        AppUtility.showToastMsg(resultBase.getMsg());
    }

    @Override
    public void showAddOrUpCaseLibFailTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

}
