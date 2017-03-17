package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.EaseBlackListAdapter;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.ModifyBlackParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
/**
 * Description ：黑名单列表
 * <p>
 * Created by ycw on 2016/8/12.
 */
public class EaseBlackListActivity extends AutoLayoutActivity implements EaseBlackListAdapter.IDeleteBlackList, Topbar.onTopbarClickListener {

    Topbar topbar;
    ListView listBlack;
    EaseBlackListAdapter blackListAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_black_list);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        listBlack = getView(R.id.list_black);
        topbar.setTttleText("黑名单").setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

        blackListAdapter = new EaseBlackListAdapter(mContext, new ArrayList<User>());
        blackListAdapter.setDeleteBlackList(this);
        listBlack.setAdapter(blackListAdapter);

        getBlackList();
    }


    /**
     * 获取黑名单好友
     *
     * @author zhangsan
     * @date 16/7/29 下午2:57
     */
    private void getBlackList() {
        Call<ResultBase<List<User>>> call = app.getHxApiService().getUserList(app.getCurrentUserNum(), 3, null);
        call.enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> hxUserResult) {
                blackListAdapter.setData(hxUserResult.getObj());
                blackListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> hxUserResult, int erroCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.d("retrofit", msg);
            }

            @Override
            public void onResult() {

            }
        });
    }


    @Override
    public void deleteBlack(User user, int position) {
        //删除黑名单
        deleteBlackUser(user);
    }

    /**
     * 修改黑名单参数
     */
    private ModifyBlackParams params = new ModifyBlackParams();

    /**
     * 移除黑名单
     *
     * @param user
     */
    private void deleteBlackUser(User user) {
        showLoading();
        params.setFriendNum(user.getUserNum());
        params.setFriendStatus(1);
        Call<ResultBase> call = app.getHxApiService().modifyBlack(app.getCurrentUserNum(), params);
        call.enqueue(new ResponseCallback<ResultBase>() {
            @Override
            public void onSuccess(Call call, ResultBase hxUserResult) {
                AppUtility.showToastMsg(hxUserResult.getMsg());
            }

            @Override
            public void onError(Call call, ResultBase hxUserResult, int erroCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                LogX.d("retrofit", msg);
            }

            @Override
            public void onResult() {
                dissMissLoading();
            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            exit();
        }
    }
}
