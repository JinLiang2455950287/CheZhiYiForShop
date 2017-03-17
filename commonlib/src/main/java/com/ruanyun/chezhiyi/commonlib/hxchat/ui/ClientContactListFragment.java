package com.ruanyun.chezhiyi.commonlib.hxchat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter.ClientContactAdapter;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.SearchAddFirendParams;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;

import java.util.ArrayList;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.Subscribe;
import retrofit2.Call;

/**
 * Description: 司机端联系人列表
 * author: zhangsan on 16/8/10 下午3:54.
 */
public class ClientContactListFragment
        extends BaseFragment
        implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    private ClientContactAdapter clientContactAdapter;
    ListView listView;
    EaseSidebar sidebar;
    BGARefreshLayout refreshLayout;
    CleanableEditText edtSeach;
    LinearLayout llContactHeader;
    // private List<HxUser> contactList=new ArrayList<>();
    // private static final String[] SELECTIONS = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    BGABadgeImageView bgaImageNewFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.ease_frag_contact_list, container, false);
        listView = getView(R.id.list);
        sidebar = getView(R.id.sidebar);
        edtSeach = getView((R.id.query));
        refreshLayout = getView(R.id.refresh_layout);
        return mContentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    @Subscribe
    public void onReciveRefresh(Event<String> msg) {
        if (msg.key.equals(C.EventKey.KEY_REFRESH_LIST))
            refreshLayout.beginRefreshing();
    }

    /**
     * 收到好友邀请通知
     *
     * @author zhangsan
     * @date 16/11/8 上午9:04
     */
    @Subscribe
    public void onReciveNewInvitation(Event<String> msg) {
        if (msg.key.equals(C.EventKey.NEW_FRENDS_INVITATION))
            bgaImageNewFriends.showCirclePointBadge();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        // getHxUserList();
        showLoading("正在获取好友...");
        getArtificer();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init() {

        llContactHeader = getViewFromLayout(R.layout.ease_view_contact_client_heaeder);
        llContactHeader.findViewById(R.id.ll_new_friend).setOnClickListener(this);
        bgaImageNewFriends = (BGABadgeImageView) llContactHeader.findViewById(R.id.bgaimg_new_friend);
        // llContactHeader.findViewById(R.id.ll_black_list).setOnClickListener(this);
        llContactHeader.findViewById(R.id.ll_my_groups).setOnClickListener(this);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        refreshLayout.setDelegate(this);
        listView.addHeaderView(llContactHeader, null, false);
        clientContactAdapter = new ClientContactAdapter(mContext, new ArrayList<HxUser>());
        listView.setAdapter(clientContactAdapter);
        sidebar.setListView(listView);
        listView.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                LogX.d(TAG, String.valueOf(position));
                HxUser hxUser = clientContactAdapter.getItem(position - 1);
                if (hxUser != null && hxUser.getTypeId() == HxUser.TYPE_USER) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("userId", hxUser.getUserNum());
                    showActivity(intent);
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
                clientContactAdapter.getFilter().filter(s);
            }
        });
    }

    /**
     * 将服务端数据转换为数据库结构数据
     *
     * @author zhangsan
     * @date 16/8/12 下午5:15
     */
//    private List<HxUser> getDbUserList(List<HxUser> hxUserList) {
//        List<HxUser> hxUserDbList = new ArrayList<>();
//        for (HxUser hxUser : hxUserList) {
//            HxUser hxUser = new HxUser();
//            hxUser.setUserNum(hxUser.getUserNum());
//            hxUser.setUserNick(hxUser.getUserNick());
//            hxUser.setUserPhoto(hxUser.getUserPhoto());
//            hxUserDbList.add(hxUser);
//        }
//        return hxUserDbList;
//    }
    private SearchAddFirendParams params = new SearchAddFirendParams();

    /**
     * 获取好友列表(所有技师)
     **/
    private void getArtificer() {
        params.setNumPerPage(10000);
        params.setUserType("2");
        app.getHxApiService().searchAddFriend(app.getCurrentUserNum(), params).enqueue(new ResponseCallback<ResultBase<PageInfoBase<User>>>() {
            @Override
            public void onSuccess(Call call, final ResultBase<PageInfoBase<User>> result) {
                refreshLayout.endRefreshing();
                clientContactAdapter.updateData(result.getObj().getResult());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DbHelper.getInstance().insertHxUsers(clientContactAdapter.getUserlist(result.getObj().getResult()));
                    }
                }).start();
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<User>> pageInfoBaseResultBase, int errorCode) {

            }

            @Override
            public void onFail(Call call, String msg) {

            }

            @Override
            public void onResult() {
                dissMissLoading();
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        //getHxUserList();
        getArtificer();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.ll_new_friend) {//新的朋友
            bgaImageNewFriends.hiddenBadge();
            showActivity(EaseNewFriendsMsgActivity.class);
        } else if (viewId == R.id.ll_my_groups) {//我的群组
            showActivity(EaseGroupActivity.class);
        }
    }
}
