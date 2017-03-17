package com.ruanyun.chezhiyi.commonlib.hxchat.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;

import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.StringUtil;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description: 司机端好友列表adapter
 * author: zhangsan on 16/8/10 下午4:13.
 */
public class ClientContactAdapter extends MultiItemTypeAdapter<HxUser> implements SectionIndexer, Filterable {
    List<String> list = new ArrayList<>();
    private SparseIntArray positionOfSection = new SparseIntArray();
    private SparseIntArray sectionOfPosition = new SparseIntArray();
    private List<HxUser> copyUserList;
    private boolean notiyfyByFilter;
    private MyFilter myFilter;
    public ClientContactDelegate clientContactDelegate;

    public ClientContactAdapter(Context context, List<HxUser> datas) {
        super(context, datas);
        copyUserList = new ArrayList<>();
        clientContactDelegate = new ClientContactDelegate(context);
        addItemViewDelegate(new ClientGroupDelegate());
        addItemViewDelegate(clientContactDelegate);
    }

    /**
     * 是否显示选择按钮
     *
     * @author zhangsan
     * @date 16/8/12 下午4:45
     */
    public void isShowChooser(boolean flag) {
        clientContactDelegate.isOpenChoose = flag;
        notifyDataSetInvalidated();
    }

    public List<HxUser> getData() {
        return mDatas;
    }

    public void setData(List<HxUser> data) {
        this.mDatas = data;
    }

    /**
     * 从服务端数据更新列表要显示的数据
     *
     * @author zhangsan
     * @date 16/8/12 下午5:35
     */
    public void updateData(List<User> users) {
        setData(getLetterList(getUserlist(users)));
        notifyDataSetChanged();
        copyUserList.clear();
        copyUserList.addAll(mDatas);
    }

    /**
     * 从数据库list 获取列表数据结构
     *
     * @author zhangsan
     * @date 16/8/12 下午5:30
     */
    public void setupDataFromDb(List<HxUser> hxUserDbs) {
        setData(getLetterList(getSortedUserList(hxUserDbs)));
        notifyDataSetChanged();
    }
    /**
      * 获取排序处理后的好友列表
      *@author zhangsan
      *@date   16/8/12 下午8:15
      */
    private List<HxUser> getSortedUserList(List<HxUser> hxUserDbs) {
        //List<HxUser> tempList = new ArrayList<>();
        for (HxUser user : hxUserDbs) {
            user.setTypeId(HxUser.TYPE_USER);
            user.setGroupName(StringUtil.getUserLetter(user.getUserNick()));
            //tempList.add(hxUser);
        }
        sortUserList(hxUserDbs);
        return hxUserDbs;
    }

    /**
     * 根据昵称拼音首字母排序好友列表
     *
     * @author zhangsan
     * @date 16/8/12 上午9:24
     */
    private void sortUserList(List<HxUser> userList) {
        Collections.sort(userList, new Comparator<HxUser>() {
            @Override
            public int compare(HxUser lhs, HxUser rhs) {
                if (lhs.getGroupName().equals(rhs.getGroupName())) {
                    return lhs.getUserNick().compareTo(rhs.getUserNick());
                } else {
                    if ("#".equals(lhs.getGroupName())) {
                        return 1;
                    } else if ("#".equals(rhs.getGroupName())) {
                        return -1;
                    }
                    return lhs.getGroupName().compareTo(rhs.getGroupName());
                }

            }
        });
    }

    /**
     * 将接口返回的值组装成列表数据类型
     *
     * @author zhangsan
     * @date 16/8/12 上午9:25
     */
    public List<HxUser> getUserlist(List<User> users) {
        List<HxUser> tempList = new ArrayList<>();
        for (User user : users) {
            if (user != null) {
                HxUser hxUser = new HxUser();
                hxUser.setGroupName(StringUtil.getUserLetter(user.getNickName()));
                hxUser.setTypeId(HxUser.TYPE_USER);
                hxUser.setUserType(user.getUserType());
                if(!TextUtils.isEmpty(user.getFriendNickName())){
                    hxUser.setUserNick(user.getFriendNickName());
                }else {
                    hxUser.setUserNick(user.getNickName());
                }
                hxUser.setUserNum(user.getUserNum().toLowerCase());
                hxUser.setUserPhoto(FileUtil.getFileUrl(user.getUserPhoto()));
                tempList.add(hxUser);
            }
        }
        sortUserList(tempList);
        //return getLetterList(tempList);
        return tempList;
    }

    /**
     * 通过前后比较拼音首字母是否相同,给排序后的好友列表添加首字母分组
     *
     * @author zhangsan
     * @date 16/8/12 上午9:25
     */
    private List<HxUser> getLetterList(List<HxUser> contactList) {
        List<HxUser> tempList = new ArrayList<>();
        if (!contactList.isEmpty()) {
            for (int i = 0, size = contactList.size(); i < size; i++) {
                if (i == 0) {
                    tempList.add(getGroupHxUser(contactList.get(i).getGroupName()));
                    tempList.add(contactList.get(i));

                } else if (i > 0 && i < size - 1) {
                    HxUser hxUserl = contactList.get(i - 1);
                    HxUser hxUser = contactList.get(i);
                    HxUser hxUserN = contactList.get(i + 1);
                    boolean t2 = hxUser.getGroupName().equals(hxUserN.getGroupName());//与下面组名是否相同
                    boolean t1 = hxUser.getGroupName().equals(hxUserl.getGroupName());//与前面组名是否相同
                    if (!t1 && !t2) {
                        tempList.add(getGroupHxUser(hxUser.getGroupName()));
                    } else if (!t1 && t2) {
                        tempList.add(getGroupHxUser(hxUserN.getGroupName()));
                    }
                    tempList.add(contactList.get(i));

                }else if(i>0&&i==size-1){
                    HxUser hxUserl = contactList.get(i - 1);
                    HxUser hxUser = contactList.get(i);
                    boolean t1 = hxUser.getGroupName().equals(hxUserl.getGroupName());
                    if(!t1){
                        tempList.add(getGroupHxUser(hxUser.getGroupName()));
                    }
                    tempList.add(contactList.get(i));
                }

            }
        }
        return tempList;
    }

    /**
     * 获取选中的联系人用户编号 逗号分隔
     *
     * @author zhangsan
     * @date 16/8/15 上午9:41
     */
    public String getSelectUserNum() {
        StringBuilder sb = new StringBuilder();
        for (HxUser hxUser : mDatas) {
            if (!hxUser.isInvited() && hxUser.isSelected()) {
                sb.append(hxUser.getUserNum()).append(",");
            }
        }
        return sb.length() > 1 ? sb.substring(0, sb.length() - 1) : sb.toString();
    }

    public List<String> getSelectUserNums(){
        List<String> userNums=new ArrayList<>();
        for (HxUser hxUser : mDatas) {
            if (!hxUser.isInvited() && hxUser.isSelected()) {
                userNums.add(hxUser.getUserNum());
            }
        }
        return  userNums;
    }

    /**
     * 获取选中前三个的昵称 逗号分隔
     *
     * @author zhangsan
     * @date 16/8/22 上午9:26
     */
    public String getSelectUserNick() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (HxUser hxUser : mDatas) {
            if (!hxUser.isInvited() && hxUser.isSelected() && count <= 2) {
                count += 1;
                sb.append(hxUser.getUserNick()).append(",");
            }
        }

        return sb.length() > 1 ? sb.substring(0, sb.length() - 1) : sb.toString();
    }

    /**
     * 选择所有的user 昵称  逗号分隔
     * @return
     */
    public String getSelectAllUserNick() {
        StringBuilder sb = new StringBuilder();
        for (HxUser hxUser : mDatas) {
            if (!hxUser.isInvited() && hxUser.isSelected()) {

                sb.append(hxUser.getUserNick()).append(",");
            }
        }
        return sb.length() > 1 ? sb.substring(0, sb.length() - 1) : sb.toString();
    }


    private HxUser getGroupHxUser(String groupName) {
        HxUser hxUser = new HxUser();
        hxUser.setGroupName(groupName);
        hxUser.setTypeId(HxUser.TYPE_GROUP);
        return hxUser;
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mDatas);
        }
        return myFilter;
    }

    @Override
    public Object[] getSections() {
        positionOfSection.clear();
        sectionOfPosition.clear();
        int count = getCount();
        list.clear();
        list.add(mContext.getString(com.hyphenate.easeui.R.string.search_header));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {
            String letter = getItem(i).getGroupName();
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionOfSection.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }


    protected class MyFilter extends Filter {
        List<HxUser> mOriginalList = null;

        public MyFilter(List<HxUser> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<>();
            }

            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                List<HxUser> newValues = new ArrayList<HxUser>();
                for (HxUser user : mOriginalList) {
                    if (user.getTypeId() == HxUser.TYPE_USER && user.getUserNick().contains(prefixString)) {
                        newValues.add(user);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint,

                                                   FilterResults results) {
            if (results.values == null) {
                return;
            }
            mDatas.clear();
            mDatas.addAll((List<HxUser>) results.values);
            //    EMLog.d(TAG, "publish contacts filter results size: " + results.count);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
                notiyfyByFilter = false;
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
            copyUserList.clear();
            copyUserList.addAll(mDatas);
        }
    }
}
