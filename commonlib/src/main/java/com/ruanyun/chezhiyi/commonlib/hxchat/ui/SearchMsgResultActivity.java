package com.ruanyun.chezhiyi.commonlib.hxchat.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.SearchMsgResultAdapter;
import com.ruanyun.chezhiyi.commonlib.model.ChatMessage;
import com.ruanyun.chezhiyi.commonlib.model.params.MessageListParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ChatMessageManager;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import java.util.List;

import retrofit2.Call;


/**
 * Description:聊天记录搜索结果页面
 * author: zhangsan on 16/8/29 下午3:49.
 */
public class SearchMsgResultActivity extends RefreshBaseActivity implements AbsListView.OnScrollListener, Topbar.onTopbarClickListener, EaseChatMessageList.MessageListItemClickListener {
    //EaseChatMessageList messageList;
    SearchMsgResultAdapter searchMsgResultAdapter;
    Topbar topbar;
    ListView list;
    ChatMessage chatMsg;
    MessageListParams msgParams = new MessageListParams();
    //EaseMessageAdapter messageAdapter;
    ChatMessageManager chatMessageManager;
    //ExecutorService singleThreadPool=Executors.newSingleThreadExecutor();
    // List<String> fetchedMessageIds=new ArrayList<>();
    //int chatType;
    boolean isScrollToBottom = false, isFirstIn = true,isLoadMoreEmpty=false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        chatMsg = getIntent().getParcelableExtra(C.IntentKey.SEARCH_MSG_RESULT);
        setContentView(R.layout.ease_activity_search_msg_result);
        initView();
        // registerBus();
        refreshWithLoading();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unRegisterBus();
    }

    private void initView() {
        initRefreshLayout();
        topbar = getView(R.id.topbar);
        list = getView(R.id.list);
        topbar.setBackBtnEnable(true)
                .setTttleText("聊天记录")
                .onBackBtnClick()
                .setTopbarClickListener(this);
        searchMsgResultAdapter = new SearchMsgResultAdapter(mContext);
        searchMsgResultAdapter.setItemClickListener(this);
        list.setAdapter(searchMsgResultAdapter);
        list.setOnScrollListener(this);
        chatMessageManager = new ChatMessageManager(app.getCurrentUserNum());
    }

  /*  @Subscribe(threadMode = ThreadMode.MainThread)
    public void onDbChatMsgResult(EMConversation conversation){
       if(conversation!=null){

       }
    }*/


    /**
     * 根据列表上啦下拉 构建请求参数
     *
     * @author zhangsan
     * @date 16/8/30 下午5:59
     */
    private void setUpParams() {
        if (isFirstIn) {
            isFirstIn = false;
            msgParams.setStartTime(chatMsg.getTimestamp());
            searchMsgResultAdapter.getmDatas().add(chatMessageManager.getTextHxChatMsg(chatMsg));
        } else if (searchMsgResultAdapter.getCount() > 0) {
            if (loadType == REFRESH) {
                msgParams.setStartTime(null);
                msgParams.setEndTime(searchMsgResultAdapter.getItem(0).getMsgTime());
            } else {
                msgParams.setStartTime(searchMsgResultAdapter.getItem(searchMsgResultAdapter.getCount() - 1).getMsgTime());
                msgParams.setEndTime(null);
            }

        }
    }

    @Override
    public void showEmpty(String tag) {
        //super.showEmpty(tag);
        AppUtility.showToastMsg("没有更多消息了");
        if(loadType==LOADMORE){
            isLoadMoreEmpty=true;
        }
    }

    @Override
    public Call loadData() {
        msgParams.setNumPerPage(15);
        msgParams.setReceiveUsernumGroupid(chatMsg.getToChatUserNum());
        msgParams.setSendUserNum(app.getCurrentUserNum());
        setUpParams();
        Call<ResultBase<List<ChatMessage>>> call = app.getHxApiService().getMessageContext(app.getCurrentUserNum(), msgParams);
        return call;
    }

  /*  private void loadMsgFromDbByIds(List<ChatMessage> messages){
        fetchedMessageIds.clear();
        for(ChatMessage message:messages){
            fetchedMessageIds.add(message.getHuanxinMsgId());
        }

    }

    private void loadMsgFromDb(){
        this.singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                chatMessageManager.importChatMessage();
               EMConversation result=chatMessageManager.loadMsgFromDb(chatMsg.getToChatUserNum(),
                      EaseCommonUtils.getConversationType(chatMsg.getHxPageChatType()),"a");
                EventBus.getDefault().post(result);
            }
        });
        singleThreadPool.shutdown();
    }*/


    @Override
    public void onRefreshNoPageResult(ResultBase result, String tag) {
        List<EMMessage> messages = chatMessageManager.getHxChatMsg((List<ChatMessage>) result.getObj());
        searchMsgResultAdapter.getmDatas().addAll(0, messages);
        searchMsgResultAdapter.notifyDataSetChanged();
        list.setSelection(messages.size());
    }

    @Override
    public void onLoadMoreNoPageResult(ResultBase result, String tag) {
        searchMsgResultAdapter.addData(chatMessageManager.getHxChatMsg((List<ChatMessage>) result.getObj()));
        searchMsgResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        // chatMessageManager.setMessageList(result.getResult());
        //loadMsgFromDb();


    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {

    }

    @Override
    protected void onStartRefresh() {
        currentPage = 1;
        loadType = REFRESH;
        listPresenter.loadDataNoPage(loadData(), loadType);
    }

    @Override
    protected boolean onStartLoadMore() {
        loadType = LOADMORE;
        if (isScrollToBottom&&!isLoadMoreEmpty) {
            listPresenter.loadDataNoPage(loadData(), loadType);
            return true;
        } else {
            refreshLayout.endRefreshing();
            return false;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            isScrollToBottom = true;
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.img_btn_left) {
            finish();
        }
    }

    @Override
    public void onResendClick(EMMessage message) {

    }

    @Override
    public boolean onBubbleClick(EMMessage message) {
        if(message.getType()== EMMessage.Type.IMAGE) {
            AppUtility.showBigImage(mContext,((EMImageMessageBody)message.getBody()).getRemoteUrl());
            return  true;
        }
        return false;
    }

    @Override
    public void onBubbleLongClick(EMMessage message) {

    }

    @Override
    public void onUserAvatarClick(String username) {

    }

    @Override
    public void onUserAvatarLongClick(String username) {

    }
}
