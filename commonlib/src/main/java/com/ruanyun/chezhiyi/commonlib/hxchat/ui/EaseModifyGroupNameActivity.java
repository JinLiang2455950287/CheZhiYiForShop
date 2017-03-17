package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.UpdateUserMarkParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.HashMap;

import de.greenrobot.event.EventBus;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author wp
 * @ClassName: EaseModifyGroupNameActivity
 * @Description: 修改群组名称
 * @date 2016/8/25 下午 6:24
 */
public class EaseModifyGroupNameActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    Topbar topbar;
    CleanableEditText editName;
    TextView tvWordsCount;
    private String groupName;
    public static final int TYPE_UPDATE_GROUP_NAME = 1;
    public static final int TYPE_UPDATE_PERSONAL_NAME = 2;
    private int type;
    //public static final int REQUEST_GROUP_NAME = 3422;
    // public static final int REQUEST_NICK_NAME = 3211;
    HashMap<String, RequestBody> map = new HashMap<>();
    User user;
    UpdateUserMarkParams params = new UpdateUserMarkParams();
    private int max; // 最大可输入字符

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_modify_group_name);
        initView();

    }

    private void initView() {
        topbar = getView(R.id.topbar);
        editName = getView(R.id.edit_name);
        tvWordsCount = getView(R.id.tv_words_count);
        type = getIntent().getIntExtra(C.IntentKey.UPDATE_TYPE, TYPE_UPDATE_GROUP_NAME);
        if (type == TYPE_UPDATE_GROUP_NAME) {
            topbar.setTttleText("修改群组名称");
            groupName = getIntent().getStringExtra(C.IntentKey.GROUP_NAME);
            editName.setText(groupName);
            max = 20;
            editName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});

        } else {
            user = getIntent().getParcelableExtra(C.IntentKey.USER_INFO);
            topbar.setTttleText("好友备注");
            editName.setText(user.getFriendNickName());
            max = 12;
            editName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
        }
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("完成")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvWordsCount.setText(new StringBuilder().append(s.length()).append("/").append(max).toString());
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_title_right) {
            updateType();
        } else if (i == R.id.img_btn_left) {
            finish();
        }
    }

    private void updateType() {
        if (type == TYPE_UPDATE_GROUP_NAME) {
            updateGroupName();
        } else {
            updateNickName();
        }
    }

    private void updateGroupName() {
        Intent intent = new Intent();
        intent.putExtra(C.IntentKey.GROUP_NAME, editName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 好友编号friendNum
     好友备注昵称nickName

     groupNum
     分租编号【技师端传值  司机端无值】
     */

    private void updateNickName() {
        params.setGroupNum(user.getGroupNum());//分组编号
        params.setNickName(editName.getText().toString());
        params.setFriendNum(user.getUserNum());//好友编号

        Call<ResultBase> call = app.getHxApiService().updateUserMark(app.getCurrentUserNum(), params);
        call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase resultBase) {
                AppUtility.showToastMsg(resultBase.getMsg());
                EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST,""));
                EventBus.getDefault().postSticky(editName.getText().toString());
                Intent intent = new Intent();
                intent.putExtra(C.IntentKey.UPDATE_NICKNAME, editName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onError(Call call, ResultBase resultBase, int errorCode) {
                AppUtility.showToastMsg(resultBase.getMsg());
            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {

            }
        });
    }
}
