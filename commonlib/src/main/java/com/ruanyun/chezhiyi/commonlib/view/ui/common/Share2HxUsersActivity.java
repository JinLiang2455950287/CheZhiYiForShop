package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;

import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ClientContactAdapter;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SearchAddFirendParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ChatMessageManager;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * Description:
 * author: zhangsan on 16/10/27 下午4:28.
 */
public class Share2HxUsersActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    private Topbar topbar;
    private CleanableEditText query;
    private ListView list;
    private EaseSidebar sidebar;
    private RYEmptyView emptyView;
    private ClientContactAdapter clientContactAdapter;
    private int pageType, selectCount;
    private static final String STR_FINISH = "完成(%s)";
    private ChatMessageManager chatMessageManager = new ChatMessageManager();
    String goodsJson,shareUrl;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_pickcontact_toshare);
        registerBus();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emptyView.unbind();
        unRegisterBus();
    }


    private void initView() {
        goodsJson=getIntent().getStringExtra(C.IntentKey.GOODS_JSON);
        shareUrl=getIntent().getStringExtra(C.IntentKey.GOODS_URL);
        topbar = getView(R.id.topbar);
        query = getView(R.id.query);
        list = getView(R.id.list);
        sidebar = getView(R.id.sidebar);
        clientContactAdapter = new ClientContactAdapter(mContext, new ArrayList<HxUser>());
        clientContactAdapter.isShowChooser(true);
        list.setAdapter(clientContactAdapter);
        sidebar.setListView(list);
        emptyView = getView(R.id.emptyview);
        emptyView.bind(list);
        emptyView.setOnReloadListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                refreshList();
            }
        });
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
        topbar.setTttleText("选择联系人").setRightText("发起");
        topbar.getTvTitleRight().setEnabled(false);
        topbar.getTvTitleRight().setTextColor(getResources().getColor(R.color.white));
        topbar.getTvTitleRight().setAlpha(0.5f);
        refreshList();
    }

    private void refreshList() {
        emptyView.showLoading();
        if(app.isClient()) {
            getContactByUserType(C.USETYPE_WORKMATE);
        }else {
            getContactByUserType(C.USETYPE_DRIVER);
        }
        //app.getHxApiService().getUserList()
    }

    SearchAddFirendParams params = new SearchAddFirendParams();

    private void getContactByUserType(String userType) {
        params.setNumPerPage(10000);
        params.setUserType(userType);
        app.getHxApiService().searchAddFriend(app.getCurrentUserNum(), params).enqueue(new ResponseCallback<ResultBase<PageInfoBase<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<User>> result) {
                clientContactAdapter.updateData(result.getObj().getResult());
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<User>> pageInfoBaseResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {
                emptyView.showLoadFail();
            }

            @Override
            public void onResult() {
                emptyView.loadSuccuss();
            }
        });
    }

    private void sendShareMsg(){
        List<String> sendUsers=clientContactAdapter.getSelectUserNums();
        if(sendUsers.size()>0){
            showLoading("处理中..");
            String [] params=new String[sendUsers.size()];
            for(int i=0,size=sendUsers.size();i<size;i++){
              params[i]=sendUsers.get(i);
            }
            new SendMsgTask().execute(params);
        }else {
            return;
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

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        } else if (v.getId() == R.id.tv_title_right) {//topbar完成
            if(selectCount>0)
            sendShareMsg();
        }
    }

    class SendMsgTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            for(int i=0,size=params.length;i<size;i++){

                chatMessageManager.sendGoodsShareMsg(chatMessageManager.creatGoodsShareMessage(shareUrl,params[i],goodsJson));
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dissMissLoading();
            AppUtility.showToastMsg("分享成功");
            finish();
        }
    }


}
