package com.ruanyun.chezhiyi.view.ui.workorder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ClientContactAdapter;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.AddAssistParams;
import com.ruanyun.chezhiyi.commonlib.presenter.PickAssistUserPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.PickAssistUserMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


/**
 * Description:  选择协助技师
 * author: zhangsan on 16/8/12 下午1:35.
 */
public class PickAssistUserActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener, PickAssistUserMvpView {

    static final String STR_FINISH = "完成(%s)";

    Topbar topbar;
    CleanableEditText query;
    ListView list;
    EaseSidebar sidebar;
    ClientContactAdapter clientContactAdapter;
    int  selectCount;
    PickAssistUserPresenter pickAssistUser = new PickAssistUserPresenter();
    AddAssistParams params = new AddAssistParams();
    private String projectNum;
    private String workOrderNum;
    ArrayList<String> pickAssistUserNums;//已选用户 Num 的集合

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_pick_contact);
        pickAssistUser.attachView(this);
        registerBus();
        initView();
        initData();
    }

    private void initData() {
        pickAssistUserNums = getIntent().getStringArrayListExtra(C.IntentKey.PICK_ASSIST_USER);
        projectNum = getIntent().getStringExtra(C.IntentKey.PROJECTNUM);
        workOrderNum = getIntent().getStringExtra(C.IntentKey.WORKORDER_NUM);
        pickAssistUser.getLeisureTechnician(app.getApiService().getLeisureTechnician(app.getCurrentUserNum(), projectNum));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        query = getView(R.id.query);
        list = getView(R.id.list);
        sidebar = getView(R.id.sidebar);
        clientContactAdapter = new ClientContactAdapter(mContext, new ArrayList<HxUser>());
        clientContactAdapter.isShowChooser(true);
        //clientContactAdapter.setupDataFromDb(getLocalUser());
        list.setAdapter(clientContactAdapter);
        sidebar.setListView(list);
        // TextView tvRight=new TextView(mContext);
        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //

            }

            @Override
            public void afterTextChanged(Editable s) {
                clientContactAdapter.getFilter().filter(s);
            }
        });
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        topbar.setTttleText("选择协助技师").setRightText("完成");
        topbar.getTvTitleRight().setEnabled(false);
        topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        topbar.getTvTitleRight().setAlpha(0.5f);

    }


    private void onContactPick() {
        if (selectCount > 0) {
            topbar.getTvTitleRight().setEnabled(true);
            topbar.getTvTitleRight().setAlpha(1f);
            topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
            topbar.setRightText(String.format(STR_FINISH, selectCount));
        } else if (selectCount == 0) {
            topbar.setRightText("完成");
            topbar.getTvTitleRight().setEnabled(false);
            topbar.getTvTitleRight().setAlpha(0.5f);
            topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        }

    }

    /**
     * link ClientContactDelegate 62
     *
     * @author zhangsan
     * @date 16/9/6 下午3:54
     * 点击联系人触发
     */
    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
    public void onUserSelect(String event) {
        if (!TextUtils.isEmpty(event)) {
            if (event.equals("select")) {
                selectCount++;
            } else {
                selectCount--;
                if (selectCount < 0)
                    selectCount = 0;
            }
            EventBus.getDefault().removeStickyEvent(event);
            onContactPick();
        }
    }


    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        } else if (v.getId() == R.id.tv_title_right) {//topbar完成
            String selectUserNum = clientContactAdapter.getSelectUserNum();
            String selectUserNick = clientContactAdapter.getSelectAllUserNick();
            if (!TextUtils.isEmpty(selectUserNum)) {
                // TODO: 2016/10/28 选择协助技师添加
                params.setWorkOrderNum(workOrderNum);
                params.setAssistUserNames(selectUserNick);
                params.setAssistUserNums(selectUserNum);
                pickAssistUser.addAssist(app.getApiService().addAssist(app.getCurrentUserNum(), params));
            }
        }
    }


    /**
     * 获取空闲技师
     * @param users
     */
    @Override
    public void getLeisureTechnicianSuccess(List<User> users) {
        clientContactAdapter.setupDataFromDb(getHxUserFromUser(users));
//        clientContactAdapter.updateData(users);
    }

    /**
     * 获取用户 是否已选择
     * @return
     */
    private List<HxUser> getHxUserFromUser(List<User> users) {
        List<HxUser> temp = clientContactAdapter.getUserlist(users);
        if (pickAssistUserNums != null) {
            for (String num : pickAssistUserNums) {
                for (HxUser hxUser : temp) {
                    if (num.equals(hxUser.getUserNum())) {
                        hxUser.setInvited(true);
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public void showTips(String msg) {
        AppUtility.showToastMsg(msg);
    }

    /**
     * 添加助手成功后
     */
    @Override
    public void addAssistSuccess() {
        EventBus.getDefault().post(C.EventKey.WORK_ORDER_REFRESH);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }
}
