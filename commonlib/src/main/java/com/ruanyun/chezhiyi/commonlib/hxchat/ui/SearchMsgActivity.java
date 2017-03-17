package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.SearchMessageAdapter;
import com.ruanyun.chezhiyi.commonlib.model.ChatMessage;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.params.MessageListParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.ChatMessageManager;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * Description:查找聊天记录
 * author: zhangsan on 16/8/20 下午4:20.
 */
public class SearchMsgActivity extends RefreshBaseActivity implements TextView.OnEditorActionListener, Topbar.onTopbarClickListener {
    //public static final int TYPE_SINGLE = Constant.;
   // public static final int TYPE_GROUP = 7;
    MessageListParams params = new MessageListParams();
    SearchMessageAdapter adapter;
    CleanableEditText seachText;
    ListView list;
    String searchStr, toUserNum;
    Topbar topbar;
    private int chatType;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_search_message);
        initView();

    }

    private void initView() {
        initRefreshLayout();
        chatType = getIntent().getIntExtra(C.IntentKey.SEARCH_MSG_TYPE, -1);
        toUserNum = getIntent().getStringExtra(C.IntentKey.SEARCH_MSG_NUM);
        topbar = getView(R.id.topbar);
        list = getView(R.id.list);
        seachText = getViewFromLayout(R.layout.ease_layout_search_edttext, topbar, false);
        seachText.setOnEditorActionListener(this);
        adapter = new SearchMessageAdapter(mContext, R.layout.ease_list_item_search_message, new ArrayList<ChatMessage>());
        topbar.addViewToTopbar(seachText, (AutoRelativeLayout.LayoutParams) seachText.getLayoutParams())
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .setBackBtnEnable(true)
                .setRightText("取消")
                .onRightTextClick()
                .onBackBtnClick()
                .setTopbarClickListener(this);
        topbar.getTvTitle().setVisibility(View.GONE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                ChatMessage chatMessage = adapter.getItem(position);
                chatMessage.setHxPageChatType(chatType);
                chatMessage.setToChatUserNum(toUserNum);
                Intent intent = new Intent(mContext, SearchMsgResultActivity.class);
                intent.putExtra(C.IntentKey.SEARCH_MSG_RESULT, chatMessage);
              //  intent.putExtra(C.IntentKey.SEARCH_MSG_TYPE, type);
                showActivity(intent);
            }
        });
    }

    /**
      * 根据消息usernum合并用户信息到聊天消息
      *@author zhangsan
      *@date   16/8/30 下午12:18
      */

    private List<ChatMessage>  setUpUserInfoToData(List<ChatMessage> chatMessages){
        for(ChatMessage chatMessage:chatMessages){
            HxUser hxUser= DbHelper.getInstance().getUserByNum(chatMessage.getReceiveUsernumGroupid());
            //chatMessage.setUserAvatar(hxUser.getUserPhoto());
            if(hxUser!=null)
            chatMessage.setUserNick(hxUser.getUserNick());
        }
        return  chatMessages;
    }

    private void  setUpInfoFromMessage(ChatMessage chatMessage){

    }

    @Override
    public Call loadData() {
        params.setReceiveUsernumGroupid(toUserNum);
        params.setHuanxinSendMsg(searchStr);
        params.setSendUserNum(app.getCurrentUserNum());
        Call<ResultBase<PageInfoBase<ChatMessage>>> call = app.getHxApiService().getMessageList(app.getCurrentUserNum(), params);
        return call;
    }
    ChatMessageManager manager=new ChatMessageManager();

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        adapter.setData(setUpUserInfoToData(result.getResult()));
        adapter.notifyDataSetChanged();
        ChatMessage chatMessage= (ChatMessage) result.getResult().get(0);
       // LogX.d("retrofit", JSON.toJSONString(manager.createTextMessage(chatMessage)));
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
       adapter.addData(result.getResult());
       adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//按下输入法搜索键
            searchStr = seachText.getText().toString().trim();
            if (!TextUtils.isEmpty(searchStr)) {
                adapter.setSearchStr(searchStr);
                refreshWithAnim();
            }
        }
        return false;
    }

    @Override
    public void onTobbarViewClick(View v) {
        if (v.getId() == R.id.tv_title_right) {
            finish();
        }else if(v.getId()==R.id.img_btn_left){
            finish();
        }
    }

}
