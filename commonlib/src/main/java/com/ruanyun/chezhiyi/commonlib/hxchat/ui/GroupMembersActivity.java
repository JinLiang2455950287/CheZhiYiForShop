package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ClientContactAdapter;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Description: 群成员
 * author: zhangsan on 16/8/15 上午9:55.
 */
public class GroupMembersActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    CleanableEditText edtSeach;
    ListView listView;
    EaseSidebar sidebar;
    ClientContactAdapter adapter;
    String groupNum;
    List<HxUser> groupMembers = new ArrayList<>();
    Topbar topbar;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_group_members);
        groupNum = getIntent().getStringExtra(C.IntentKey.GROUP_NUM);
        init();
        getGroupMembersInfo();
    }

    private void init() {
        topbar=getView(R.id.topbar);
        listView = getView(R.id.list);
        sidebar = getView(R.id.sidebar);
        edtSeach = getView((R.id.query));
        adapter = new ClientContactAdapter(mContext, groupMembers);
        topbar.setTttleText("群成员")
              .setBackBtnEnable(true)
              .onBackBtnClick()
              .setTopbarClickListener(this);
        listView.setAdapter(adapter);
        sidebar.setListView(listView);
        listView.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                HxUser user = adapter.getItem(position);
                if (user != null  && !user.getUserNum().equals(App.getInstance().getCurrentUserNum())) {
                    showActivity(AppUtility.getChatIntent(mContext, user.getUserNum()));
                }
            }
        });
        edtSeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }

    private void getGroupMembersInfo() {
        app.getHxApiService().getGroupMember(app.getCurrentUserNum(), groupNum).enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> listResult) {
                adapter.updateData(listResult.getObj());

            }

            @Override
            public void onError(Call call, ResultBase<List<User>> listResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });


    }

    @Override
    public void onTobbarViewClick(View v) {
        if(v.getId()==R.id.img_btn_left){
            finish();
        }
    }
}
