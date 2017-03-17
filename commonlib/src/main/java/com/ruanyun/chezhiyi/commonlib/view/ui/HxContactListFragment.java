package com.ruanyun.chezhiyi.commonlib.view.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.data.api.ResponseCallback;
import com.ruanyun.chezhiyi.commonlib.hxchat.Constant;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.ChatActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.EaseGroupActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.EaseNewFriendsMsgActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.HxGroup;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.HxUserGroup;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.GroupDetailParams;
import com.ruanyun.chezhiyi.commonlib.model.params.SearchAddFirendParams;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.DefaultItemAnimator;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ContactGroupAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.Subscribe;
import retrofit2.Call;


/**
 * 技师端好友列表
 * Description: author: zhangsan on 16/7/28 上午11:37.
 */
public class HxContactListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {
    static final String GROUP_NUM_WORKMATE = "3";//分组编号同事
    static final String GROUP_NUM_CUSTOMER = "2";//分组编号客户
    static final String GROUP_NUM_FRIEND = "1";//分组编号好友

    RecyclerView rvUserList;
    BGARefreshLayout refreshLayout;
    //private List<HxGroup> groupList;
    private List<User> userList = new ArrayList<>();
    private ContactGroupAdapter contactGroupAdapter;
    private List<HxUser> contactList = new ArrayList<>();
    private List<HxUser> searchContactList = new ArrayList<>();
    //HeaderAndFooterWrapper<HxUser> hxUserHeaderAndFooterWrapper;
    // FrameLayout llHeader;
    EditText edtSearch;
    private BGABadgeTextView badgeTextView;
    // private Map<String,HxUser> contactListMap=new HashMap<>();

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.frag_hxuser_list, container, false);
        refreshLayout = getView(R.id.refresh_layout);
        rvUserList = getView(R.id.rv_user_list);
        edtSearch = getView(R.id.query);
        refreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(mContext, true));
        refreshLayout.setDelegate(this);
        return mContentView;
    }

    /**
     * 接收到刷新列表通知回调
     *
     * @author zhangsan
     * @date 16/10/29 下午4:04
     */
    @Subscribe
    public void onReciveRefresh(Event<String> msg) {
        if (msg.key.equals(C.EventKey.KEY_REFRESH_LIST))
            refreshLayout.beginRefreshing();
    }


    /**
     * 接收到好友同意通知
     * @param msg
     */
    @Subscribe
    public void onFrendsAcceptRefresh(Event<String> msg) {
        if (msg.key.equals(C.EventKey.NEW_FRENDS_ACCEPT)  && badgeTextView != null) {
            badgeTextView.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
            badgeTextView.showCirclePointBadge();
            refreshLayout.beginRefreshing();
        }
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        //getGroupList();
//        refreshList();
        refreshLayout.beginRefreshing();
    }

    private void initView() {
        contactList.addAll(getGroup());
        contactGroupAdapter = new ContactGroupAdapter(mContext, contactList);
        //  hxUserHeaderAndFooterWrapper = new HeaderAndFooterWrapper<HxUser>(contactGroupAdapter);
        // llHeader = getViewFromLayout(R.layout.ease_view_contact_shop_heaeder);
        mContentView.findViewById(R.id.tv_group).setOnClickListener(this);
        mContentView.findViewById(R.id.tv_message_room).setOnClickListener(this);
        badgeTextView = (BGABadgeTextView) mContentView.findViewById(R.id.tv_new_friend);
        badgeTextView.setOnClickListener(this);
        //  hxUserHeaderAndFooterWrapper.addHeaderView(llHeader);
        //  contactGroupAdapter.
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvUserList.setAdapter(contactGroupAdapter);
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setItemAnimator(new DefaultItemAnimator());
        contactGroupAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<HxUser>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, HxUser o, int position) {
                // LogX.d(TAG,"Adapterposition:"+Integer.toString(holder.getAdapterPosition()));
                // LogX.d(TAG,"Layoutposition:"+Integer.toString(holder.getLayoutPosition()));
                // LogX.d(TAG,"Oldposition:"+Integer.toString(holder.getOldPosition()));
                //  LogX.d(TAG,"position:"+Integer.toString(position));
//                HxUser hxUser = contactList.get(position);
                HxUser hxUser = contactGroupAdapter.getDatas().get(position);
                if (null == hxUser) {
                    return;
                }
                if (refreshLayout.getCurrentRefreshStatus() != BGARefreshLayout.RefreshStatus.IDLE) {
                    return;
                }
                if (hxUser.getTypeId() == HxUser.TYPE_GROUP) {//点击分组
                    TextView tv = (TextView) holder.itemView.findViewById(R.id.tv_group_name);
                    if (!tv.isSelected()) {//判断分组是展开还是收起 isselected true 为展开
                        List<HxUser> hxUsers = getUserByGroup(hxUser.getGroupNum());
                        if (!hxUsers.isEmpty()) {
                            contactList.addAll(position + 1, hxUsers);
                            contactGroupAdapter.notifyItemRangeInserted(position + 1, hxUsers.size());
                        }

                    } else {
                        List<HxUser> hxUsers = getDelUserByGroup(contactGroupAdapter.getDatas(), hxUser.getGroupNum());
                        if (!hxUsers.isEmpty()) {
                            contactList.removeAll(hxUsers);
                            contactGroupAdapter.notifyItemRangeRemoved(position + 1, hxUsers.size());
                        }

                    }
                    tv.setSelected(!tv.isSelected());
                    hxUser.setSelected(tv.isSelected());
                } else if (hxUser.getTypeId() == HxUser.TYPE_USER) {//点击好友跳转到聊天页面
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("userId", hxUser.getUserNum());
                    showActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, HxUser o, int position) {
                return false;
            }
        });

        rvUserList.addItemDecoration(new RecyclerView.ItemDecoration() {
           /* @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position=((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
                if(position==contactList.size()){
                    position-=1;
                }

            }*/

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
              /*  HxUser hxUser=contactList.get(itemPosition);
                if(hxUser!=null&&hxUser.getTypeId()==HxUser.TYPE_GROUP &&!TextUtils.isEmpty(hxUser.getGroupName())&&hxUser.getGroupName().equals("我的好友")){
                    outRect.set(0,30,0,0);
                }*/
               /* if(itemPosition==3){
                    outRect.set(0,30,0,0);
                }*/
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    contactGroupAdapter.setData(contactList);
                    contactGroupAdapter.notifyDataSetChanged();
                } else {
                    searchContactList.clear();
                    searchContactList.addAll(contactGroupAdapter.getSearchResult(userList, s.toString().trim()));
                    contactGroupAdapter.setData(searchContactList);
                    contactGroupAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 初始化好友列表
     *
     * @author zhangsan
     * @date 16/10/29 下午4:07
     */
    private void initContactList() {
        //filiterUserList();
        contactList.addAll(3, getUserByGroup(GROUP_NUM_FRIEND));
        contactGroupAdapter.notifyDataSetChanged();
    }

  /*  private void filiterUserList() {
        if (userList.isEmpty()) {
            return;
        }
        for (User user : userList) {
            user.setGroupNum("1");
        }
    }*/

    private void clearGroupStatus() {
        Iterator<HxUser> iterator = contactList.iterator();
        while (iterator.hasNext()) {
            HxUser hxUser = iterator.next();
            if (hxUser.getTypeId() == HxUser.TYPE_USER) {
                iterator.remove();
            } else if (hxUser.getTypeId() == HxUser.TYPE_GROUP && hxUser.getGroupName().equals("我的好友")) {
                hxUser.setSelected(true);
            }

        }
       /* for(HxUser hxUser:contactList){
            if(hxUser.getTypeId()==HxUser.TYPE_USER){
                contactList.remove(hxUser);
            }else if(hxUser.getTypeId()==HxUser.TYPE_GROUP&&hxUser.getGroupName().equals("我的好友")){
               hxUser.setSelected(true);
            }
        }*/
    }

    private void refreshList() {
        clearGroupStatus();
        getHxUserList();
    }

    /**
     * 获取好友分组
     *
     * @author zhangsan
     * @date 16/7/29 下午2:57
     */
    private void getHxUserList() {
        Call<ResultBase<List<User>>> call = app.getHxApiService().getUserList(app.getCurrentUserNum(), 1, null);
        call.enqueue(new ResponseCallback<ResultBase<List<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<List<User>> hxUserResult) {
                userList.clear();
                for (User user : hxUserResult.getObj()) {
                    user.setGroupNum(GROUP_NUM_FRIEND);
                    userList.add(user);
                }
                initContactList();
                getArtificer();
            }

            @Override
            public void onError(Call call, ResultBase<List<User>> hxUserResult, int erroCode) {
                refreshLayout.endRefreshing();

            }

            @Override
            public void onFail(Call call, String msg) {
                refreshLayout.endRefreshing();

            }

            @Override
            public void onResult() {
            }
        });
    }

    private SearchAddFirendParams params = new SearchAddFirendParams();

   /**
     * 获取我的同事
     *@author zhangsan
     *@date   16/10/29 下午6:13
     */
    private void getArtificer() {
        params.setNumPerPage(10000);
        params.setUserType(C.USETYPE_WORKMATE);
        app.getHxApiService().searchAddFriend(app.getCurrentUserNum(), params).enqueue(new ResponseCallback<ResultBase<PageInfoBase<User>>>() {
            @Override
            public void onSuccess(Call call, ResultBase<PageInfoBase<User>> result) {
                for (User user : result.getObj().getResult()) {
                    if(!user.getUserNum().equals(app.getCurrentUserNum())) {
                        user.setGroupNum(GROUP_NUM_WORKMATE);
                        userList.add(user);
                    }
                }
                getMyCostomers();
                saveUserInDb(userList);
            }

            @Override
            public void onError(Call call, ResultBase<PageInfoBase<User>> pageInfoBaseResultBase, int errorCode) {
                refreshLayout.endRefreshing();

            }

            @Override
            public void onFail(Call call, String msg) {
                refreshLayout.endRefreshing();

            }

            @Override
            public void onResult() {

            }
        });
    }

   /**
     * 获取我的客户
     *@author zhangsan
     *@date   16/10/31 上午8:55
     */
   private void getMyCostomers(){
       params.setUserType(C.USETYPE_CUSTOMER);
       app.getHxApiService().searchAddFriend(app.getCurrentUserNum(),params).enqueue(new ResponseCallback<ResultBase<PageInfoBase<User>>>() {
           @Override
           public void onSuccess(Call call, ResultBase<PageInfoBase<User>> result) {
               for (User user : result.getObj().getResult()) {
                       user.setGroupNum(GROUP_NUM_CUSTOMER);
                       userList.add(user);

               }
           }

           @Override
           public void onError(Call call, ResultBase<PageInfoBase<User>> pageInfoBaseResultBase, int errorCode) {

           }

           @Override
           public void onFail(Call call, String msg) {

           }

           @Override
           public void onResult() {
               refreshLayout.endRefreshing();
           }
       });
   }

    /**
      * 跳转到聊天室
      *@author zhangsan
      *@date   16/10/29 下午4:47
      */
    private void gotoMettingRoom(HxUserGroup groupinfo){
        Intent intent = AppUtility.getChatIntent(getActivity(), groupinfo.getHuanxinNum());
        intent.putExtra(C.IntentKey.GROUP_INFO, groupinfo);
        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
        startActivity(intent);

    }


    private GroupDetailParams groupDetailParams = new GroupDetailParams();

    /**
     * 根据环信群号获取群详情信息
     *
     * @author zhangsan
     * @date 16/10/29 下午4:10
     */
    private void getGroupDetail(final String groupNum) {
        showLoading("请稍后...");
        groupDetailParams.setHuanxinNum(groupNum);
        Call<ResultBase<HxUserGroup>> call = App.getInstance().getHxApiService().getGroupDetails(App.getInstance().getCurrentUserNum(), groupDetailParams);
        call.enqueue(new ResponseCallback<ResultBase<HxUserGroup>>() {
            @Override
            public void onSuccess(Call call, ResultBase<HxUserGroup> result) {
                HxUserGroup hxUserGroup = result.getObj();
                gotoMettingRoom(hxUserGroup);
                DbHelper.getInstance().saveGroup(hxUserGroup);
            }

            @Override
            public void onError(Call call, ResultBase<HxUserGroup> resultBase, int errorCode) {
              AppUtility.showToastMsg(resultBase.getMsg());
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

    /**
     * 获取的好友存入数据库
     *
     * @author zhangsan
     * @date 16/7/29 下午3:30
     */
    private void saveUserInDb(final List<User> users) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HxUser> hxUserList = new ArrayList<>();
                for (User user : users) {
                    HxUser hxUser = new HxUser();
                    hxUser.setGroupNum(user.getGroupNum());
                    if (!TextUtils.isEmpty(user.getFriendNickName())) {
                        hxUser.setUserNick(user.getFriendNickName());
                    } else {
                        hxUser.setUserNick(user.getNickName());
                    }
                    hxUser.setUserType(user.getUserType());
                    hxUser.setUserNum(user.getUserNum().toLowerCase());
                    hxUser.setUserPhoto(FileUtil.getFileUrl(user.getUserPhoto()));
                    hxUserList.add(hxUser);
                }
                DbHelper.getInstance().insertHxUsers(hxUserList);
            }
        }).start();
    }


    /**
     * 保存所有好友分组
     *
     * @author zhangsan
     * @date 16/7/30 下午2:17
     */
    private void saveGroups(final List<HxGroup> hxGroups) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DbHelper.getInstance().insertHxGroup(hxGroups);
            }
        }).start();
    }


    /**
     * 初始化列表并显示分组信息
     *
     * @author zhangsan
     * @date 16/7/29 下午2:58
     */
    private List<HxUser> getGroup() {
        List<HxUser> hxUsers = new ArrayList<>();
        List<ParentCodeInfo> groups = app.beanCacheHelper.getLocalParentCodes(C.ParentCode.USER_GROUP);
        if (groups != null && !groups.isEmpty()) {
            //  hxUsers.add(new HxUser());
            for (int i = groups.size() - 1; i >= 0; i--) {
                HxUser hxUser = new HxUser();
                hxUser.setTypeId(HxUser.TYPE_GROUP);
                hxUser.setGroupName(groups.get(i).getItemName());
                hxUser.setGroupNum(groups.get(i).getItemCode());
               /* if ("我的好友".equals(hxUser.getGroupName())) {
                    hxUser.setSelected(true);
                }*/
                hxUsers.add(hxUser);
            }

        }

        return hxUsers;
    }

    /**
     * 获取分组收取后要删除好友列表
     *
     * @author zhangsan
     * @date 16/7/29 下午2:58
     */
    private List<HxUser> getDelUserByGroup(List<HxUser> userList, String groupNum) {
        List<HxUser> hxUsers = new ArrayList<>();
        for (HxUser hxUser : userList) {
            if (hxUser.getTypeId() == HxUser.TYPE_USER && hxUser.getGroupNum().equals(groupNum)) {
                hxUsers.add(hxUser);
            }
        }
        return hxUsers;
    }

    /**
     * 获取所有好友列表里与分组匹配的好友列表
     *
     * @author zhangsan
     * @date 16/7/29 下午3:03
     */
    private List<HxUser> getUserByGroup(String groupNum) {
        List<HxUser> hxUsers = new ArrayList<>();
        for (User user : userList) {
            if (groupNum.equals(user.getGroupNum())) {
                HxUser hxUser = new HxUser();
                hxUser.setTypeId(HxUser.TYPE_USER);
                if (!TextUtils.isEmpty(user.getFriendNickName())) {
                    hxUser.setUserNick(user.getFriendNickName());
                } else {
                    hxUser.setUserNick(user.getNickName());
                }
                hxUser.setUserPhoto(FileUtil.getFileUrl(user.getUserPhoto()));
                hxUser.setGroupNum(user.getGroupNum());
                hxUser.setUserNum(user.getUserNum());
                hxUsers.add(hxUser);
            }
            // hxUser
        }
        return hxUsers;
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refreshList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.tv_group) {  //跳转群列表
            showActivity(EaseGroupActivity.class);
        } else if (viewId == R.id.tv_new_friend) {  //添加新朋友
            badgeTextView.hiddenBadge();
            showActivity(EaseNewFriendsMsgActivity.class);
        } else if (viewId == R.id.tv_message_room) {    //跳转到会议室
             if(app.getStoreInfo()==null){
                return;
             }
            HxUserGroup group=DbHelper.getInstance().getGroupByHxNum(app.getStoreInfo().getHuanxinNum());
            if(group==null)
            getGroupDetail(app.getStoreInfo().getHuanxinNum());
            else
            gotoMettingRoom(group);
        }
    }
}
